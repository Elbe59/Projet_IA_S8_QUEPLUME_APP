package fr.hei.que_plume_app;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import fr.hei.que_plume_app.entity.ActualData;
import fr.hei.que_plume_app.entity.AjoutData;

public class Singleton {

    public RecyclerView.Adapter adapter_historique;
    public RecyclerView.Adapter adapter_statistique;

    private static final String TAG = "Singleton: ";
    private static Singleton singleton;
    private ArrayList<AjoutData> listeErreurs = new ArrayList<AjoutData>();
    private ArrayList<AjoutData> listeTotal = new ArrayList<>();
    private ActualData dataActuel = new ActualData(); // Va contenir le nombre d'objet par bac.
    private DatabaseReference mDatabase;

    public Singleton()
    { }

    public static Singleton getInstance()
    {
        if (singleton == null)
            singleton = new Singleton();
        return singleton;
    }

    public ArrayList<String> getTypePred()
    {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < this.listeErreurs.size() ; i++) {
            res.add(this.listeErreurs.get(i).getType_trouve()+"_"+this.listeErreurs.get(i).getCouleur_trouvee());
        }
        return res;
    }

    public ArrayList<String> getTypeReel()
    {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < this.listeErreurs.size() ; i++) {
            res.add(this.listeErreurs.get(i).getType_reel()+"_"+this.listeErreurs.get(i).getCouleur_reelle());
        }
        return res;
    }

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
                Log.d("SINGLETON", "SINGLETON: rank "+rank);
                Log.d("SINGLETON", "SINGLETON: type pred "+typePred.get(rank)+" type reel "+typeReel.get(rank));
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
        Log.d("SINGLETON ","SINGLETON: taille erreurs "+nbErreurs.size()+" Pred "+typeFinalPred.size()+" Reel "+typeFinalReel.size());
        Log.d("SINGLETON", "SINGLETON: res "+res);
        Log.d("SINGLETON", "SINGLETON: taille res "+res.size());
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
        Log.d("STAT", "STATS: res "+res);
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
        return listeErreurs;
    }

    public ArrayList<AjoutData> getListErreursInOrder(){  // Les erreurs sont stockés dans l'ordre de la plus ancienne a la plus récente. Il faut donc inverser la liste
        ArrayList<AjoutData> newListeErreursInOrder = getErreurs();
        Collections.reverse(newListeErreursInOrder);
        return newListeErreursInOrder;
    }

    public void setErreurs(ArrayList<AjoutData> erreur) {
        this.listeErreurs = erreur;
    }

    public AjoutData getErreurAtPosition(int position){
        return listeErreurs.get(position);
    }

    public AjoutData getErreurInOrderAtPosition(int position){
        return getListErreursInOrder().get(position);
    }

    public void fetchFromDatabase(Context c, boolean showToasts){

        DatabaseReference zonesRefErreurs = FirebaseDatabase.getInstance().getReference("erreurs");
        DatabaseReference zonesRefActuel = FirebaseDatabase.getInstance().getReference("resultat");


        Toast toast_db = Toast.makeText(c,"Trying to connect to the database...", Toast.LENGTH_SHORT);

        zonesRefErreurs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listeErreurs = new ArrayList<AjoutData>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    AjoutData ajoutData = ds.getValue(AjoutData.class);
                    Log.i(TAG, ajoutData.toString());
                    String reel = ajoutData.getType_reel()+'-'+ajoutData.getCouleur_reelle();
                    String predis = ajoutData.getType_trouve()+'-'+ajoutData.getCouleur_trouvee();
                    listeTotal.add(ajoutData); // Dans tous les cas on l'ajoute a la liste total
                    if(!reel.equals(predis))  // Si le reel != trouvé alors on l'ajoute dans la liste des erreurs.
                        listeErreurs.add(ajoutData);
                    if(adapter_historique != null){
                        adapter_historique.notifyDataSetChanged();
                    }
                    if(adapter_statistique != null){
                        adapter_statistique.notifyDataSetChanged();
                    }
                }
                toast_db.setText("Database synced.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        // Creer un objet
        zonesRefActuel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataActuel = new ActualData();
                dataActuel = dataSnapshot.getValue(ActualData.class); // A modifier en fct de ce qui est écrit dans la database
                Log.i(TAG, dataActuel.toString());
                /*for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    dataActuel = ds.getValue(ActualData.class); // A modifier en fct de ce qui est écrit dans la database
                    Log.i(TAG, dataActuel.toString());
                }*/
                toast_db.setText("Database synced.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        /*if(showToasts) toast_db.show();
        ArrayList<String> nameSubErreurs = new ArrayList<>();

        zonesRefErreurs.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                toast_db.cancel();

                if(task.isSuccessful()){
                    task.getResult().getChildren().forEach(t -> {
                        if(t != null) {
                            System.out.println(t.getKey()+" POUR LE MAIN DE TEST");
                            nameSubErreurs.add(t.getKey());
                        }
                    });
                    toast_db.setText("Database synced.");
                }else{
                    toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                }

                //if(showToasts) toast_db.show();
            }
        });*/
    }

    public Map<String,Integer> getHashMapDataActuel() {
        return dataActuel.getMaHashMapDataActuel();
    }

    public void getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        String currentDateandTime = sdf.format(Calendar.getInstance().getTime());
        Log.d("DATE LE FRUIT: ","DATE ET HEURE : "+currentDateandTime);
    }
}

