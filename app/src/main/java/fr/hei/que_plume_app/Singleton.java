package fr.hei.que_plume_app;

import android.app.Application;
import android.content.Context;

import android.hardware.usb.UsbInterface;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;


import fr.hei.que_plume_app.entity.ActualData;
import fr.hei.que_plume_app.entity.AjoutData;
import fr.hei.que_plume_app.entity.NbrMaxObjetParBac;

public class Singleton {

    // Les deux adapters pour les deux recycler view sont défini dans le Singleton afin de les notifier lorsqu'il y a un changement de donnée dans la base de donnée.
    public RecyclerView.Adapter adapter_historique;
    public RecyclerView.Adapter adapter_statistique;

    private static final String TAG = "Singleton: ";
    private static Singleton singleton;
    private ArrayList<AjoutData> listeErreurs = new ArrayList<AjoutData>(); // Va contenir la liste des erreurs
    private ArrayList<AjoutData> listeTotal = new ArrayList<AjoutData>(); // Va contenir la liste de toute les observation de l'IA
    private ActualData dataActuel = new ActualData(); // Va contenir le nombre d'objet par bac.
    private NbrMaxObjetParBac maxObjetParBac = new NbrMaxObjetParBac(); // Va contenir le nombre de pièce maximum par bac.
    private DatabaseReference mDatabase;

    public Singleton()
    { }

    public static Singleton getInstance()
    {
        Log.d(TAG, "instance du Singleton");
        if (singleton == null)
            singleton = new Singleton();
        return singleton;
    }

    // Meilleur affichage de la prédiction:  type-couleur
    public ArrayList<String> getTypePred()
    {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < this.listeErreurs.size() ; i++) {
            res.add(this.listeErreurs.get(i).getType_trouve()+" - "+this.listeErreurs.get(i).getCouleur_trouvee());
        }
        return res;
    }

    public NbrMaxObjetParBac getNbrMaxObj(){
        Log.d(TAG, "quantité dans les bacs récupéré");
        return this.maxObjetParBac;
    }

    // Meilleur affichage du bac dans lequel est placé l'objet:  type-couleur
    public ArrayList<String> getTypeReel()
    {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < this.listeErreurs.size() ; i++) {
            res.add(this.listeErreurs.get(i).getType_reel()+" - "+this.listeErreurs.get(i).getCouleur_reelle());
        }
        return res;
    }

    // Permet de récupérer la liste des erreurs à partir de la liste totale contenant toutes les observation de l'IA
    public ArrayList<String> getTotalErreur(){

        ArrayList<String> typePred = getTypePred();
        ArrayList<String> typeReel = getTypeReel();
        ArrayList<String> typeFinalPred = new ArrayList<String>();
        ArrayList<String> typeFinalReel = new ArrayList<String>();
        ArrayList<Integer> nbErreurs = new ArrayList<Integer>();

        for (int i = 0; i < typePred.size() ; i++) {
            boolean exists = false;
            int rank = -1;
            for (int j = 0; j < typeFinalPred.size() ; j++) {
                if(typePred.get(i).equals(typeFinalPred.get(j)) && typeReel.get(i).equals(typeFinalReel.get(j))) {
                    exists = true;
                    rank = j;
                }
            }
            if (exists && rank != -1){
                nbErreurs.set(rank, (nbErreurs.get(rank))+1);

            }else
            {
                typeFinalPred.add(typePred.get(i));
                typeFinalReel.add(typeReel.get(i));
                nbErreurs.add(1);
            }
        }
        ArrayList<String> res = new ArrayList<String>();
        for (int j = 0; j < typeFinalPred.size(); j++) {
            int imax = getRankOfMaxFromList(nbErreurs);
            res.add(typeFinalReel.get(imax) + ":" + typeFinalPred.get(imax) + ":" + nbErreurs.get(imax));
            nbErreurs.set(imax, 0);
        }
        Log.d(TAG,"Statistiques des erreurs: "+res);
        return res;
    }

    public ArrayList<String> decode(String str)
    {
        String actuel = "";
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < str.length() ; i++) {
            if (str.charAt(i) == ':')
            {
                res.add(actuel);
                actuel = "";
            }else{
                actuel = actuel.concat(str.charAt(i)+"");
            }
        }
        res.add(actuel);
        return res;
    }

    public int getRankOfMaxFromList(ArrayList<Integer> lst)
    {
        int rank = 0;
        for (int i = 0; i < lst.size() ; i++) {
            int actuel = lst.get(i);
            boolean ismax = true;
            for (int j = 0; j < lst.size() ; j++) {
                if (actuel < lst.get(j))
                {
                    ismax = false;
                }
            }
            if (ismax) rank = i;
        }
        return rank;
    }

    public ArrayList<AjoutData> getErreurs() {
        Log.d(TAG,"Liste d'erreurs récupérée");
        return listeErreurs;
    }

    public void setErreurs(ArrayList<AjoutData> erreur) {
        this.listeErreurs = erreur;
    }

    public AjoutData getErreurAtPosition(int position){
        return listeErreurs.get(position);
    }


    // Méthode la plus importante du Singleton qui permet de récupèrer toutes les données de la base de donnée lorsque celle ci est modifiée.
    public void fetchFromDatabase(Context c, boolean showToasts){

        Log.i(TAG, "Fetch from database");
        // On fait des référenceces sur les trois branches de notre arborescence.
        DatabaseReference zonesRefTraite = FirebaseDatabase.getInstance().getReference("traites");
        DatabaseReference zonesRefActuel = FirebaseDatabase.getInstance().getReference("resultat");
        DatabaseReference zonesRefMaxParBac = FirebaseDatabase.getInstance().getReference("nbrMaxParBac");

        Toast toast_db = Toast.makeText(c,"Trying to connect to the database...", Toast.LENGTH_SHORT);

        // Lorsque qu'on observation de l'IA est rajoutée à la base de donnée, la liste totale ainsi que la liste d'erreur sont modifiées
        // De plus les deux adapters (Historique et Statistique) sont mis à jour.
        zonesRefTraite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listeErreurs = new ArrayList<AjoutData>();
                listeTotal = new ArrayList<AjoutData>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    AjoutData ajoutData = ds.getValue(AjoutData.class);
                    String reel = ajoutData.getType_reel()+'-'+ajoutData.getCouleur_reelle();
                    String predis = ajoutData.getType_trouve()+'-'+ajoutData.getCouleur_trouvee();
                    //Log.e(TAG, "fetch test: "+ajoutData.toString());
                    listeTotal.add(ajoutData); // Dans tous les cas on l'ajoute a la liste total
                    if(!reel.equals(predis)) {  // Si le reel != trouvé alors on l'ajoute dans la liste des erreurs.
                        listeErreurs.add(ajoutData);
                        Log.i(TAG, ajoutData.toString());
                    }
                    if(adapter_historique != null){
                        adapter_historique.notifyDataSetChanged();
                    }
                    if(adapter_statistique != null){
                        adapter_statistique.notifyDataSetChanged();
                    }
                }
                Collections.reverse(listeErreurs); // Il faut inverser la liste des erreurs car lorsque l'on récupère les erreurs, on récupère en premier les plus anciennes alors que l'on veut les plus récentes.
                toast_db.setText("Database synced.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        // Lorsque un bac se remplit ou bien qu'il y a une erreur, dataActuel est redéfini.
        zonesRefActuel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataActuel = new ActualData();
                dataActuel = dataSnapshot.getValue(ActualData.class); // Les enfants dans la base de données ont le même nom que les propriété de l'objet ActualData, on peut donc directement récupérer un objet.
                Log.i(TAG, dataActuel.actualDataToString());
                toast_db.setText("Database synced.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
        // Si l'utilisateur modifie le nombre de places disponible par bac alors maxObjetParBac est redéfini.
        zonesRefMaxParBac.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maxObjetParBac = new NbrMaxObjetParBac();
                maxObjetParBac = dataSnapshot.getValue(NbrMaxObjetParBac.class); // Les enfants dans la base de données ont le même nom que les propriété de l'objet NbrMaxObjetParBac, on peut donc directement récupérer un objet.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public Map<String,Integer> getHashMapDataActuel() {
        return dataActuel.getMaHashMapDataActuel();
    }

    public Map<String,Integer> getHashMapNbrMaxParBacActuel() {
        return maxObjetParBac.getHashMapNbrObjetParBac();
    }

    public Date getDateActual(){
        Date date = new Date();
        return date;
    }

    public Date dateConverter(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sssssssss");
        Date date = sdf.parse(str);
        return date;
    }

    // Récupère la différence de temps entre deux date pour ne garder que les dernières 24h lors du calcul du taux d'erreur
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillies = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // return true, si l'observation a eu lieu il y a moins de 24h
    public boolean isDateLessThanADayBefore(String strDate)
    {
        Date oldDate = null;
        Date actualDate = getDateActual();
        try{
            oldDate = dateConverter(strDate);
        }catch (Exception e){
            Log.e(TAG, "error converter");
            e.printStackTrace();
        }
        long hours = getDateDiff(oldDate, actualDate, TimeUnit.HOURS);
        if (hours <= 24) return true;
        else return false;
    }

    public float getNbErreurs()
    {
        int sum = 0;
        for(int i = 0; i<listeErreurs.size(); i++)
        {
            if(isDateLessThanADayBefore(listeErreurs.get(i).getDate())) sum++;
        }
        int div = getNbPieceTraitee();
        Log.d(TAG, "nb d'erreur en 24h: "+sum);
        float res = (float) sum/div;
        Log.d(TAG,"Taux d'erreur en 24h: "+res);
        return res*100;
    }

    public int getNbPieceTraitee()
    {
        int sum = 0;
        Log.e("PIECE TRAITE: ",listeTotal.size()+"");
        for(int i = 0; i<listeTotal.size(); i++)
        {
            if(isDateLessThanADayBefore(listeTotal.get(i).getDate())) sum++;
        }
        Log.d(TAG,"nb pièces traitées en 24h: "+sum);
        return sum;
    }

}

