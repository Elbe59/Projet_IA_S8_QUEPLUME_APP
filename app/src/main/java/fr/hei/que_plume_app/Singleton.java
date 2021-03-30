package fr.hei.que_plume_app;

import android.content.Context;
import android.hardware.usb.UsbInterface;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import fr.hei.que_plume_app.entity.ErreurIA;

public class Singleton {

    private static final String TAG = "Singleton: ";
    private static Singleton singleton;
    private ArrayList<ErreurIA> listeErreurs = new ArrayList<ErreurIA>();
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
            res.add(typeFinalReel.get(j) + ":" + typeFinalPred.get(j) + ":" + nbErreurs.get(j));
        }
        Log.d("SINGLETON ","SINGLETON: taille erreurs "+nbErreurs.size()+" Pred "+typeFinalPred.size()+" Reel "+typeFinalReel.size());
        Log.d("SINGLETON", "SINGLETON: res "+res);
        Log.d("SINGLETON", "SINGLETON: taille res "+res.size());
        return res;
    }

    public ArrayList<ErreurIA> getErreurs() {
        return listeErreurs;
    }

    public ArrayList<ErreurIA> getListErreursInOrder(){  // Les erreurs sont stockés dans l'ordre de la plus ancienne a la plus récente. Il faut donc inverser la liste
        ArrayList<ErreurIA> newListeErreursInOrder = getErreurs();
        Collections.reverse(newListeErreursInOrder);
        return newListeErreursInOrder;
    }

    public void setErreurs(ArrayList<ErreurIA> erreur) {
        this.listeErreurs = erreur;
    }

    public ErreurIA getErreurAtPosition(int position){
        return listeErreurs.get(position);
    }

    public ErreurIA getErreurInOrderAtPosition(int position){
        return getListErreursInOrder().get(position);
    }

    public void fetchFromDatabase(Context c, boolean showToasts){

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("erreurs");
        //DatabaseReference zone1Ref = zonesRef.child("Truc");
        //DatabaseReference zone1NameRef = zone1Ref.child("couleur");
        Toast toast_db = Toast.makeText(c,"Trying to connect to the database...", Toast.LENGTH_SHORT);

        zonesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listeErreurs = new ArrayList<ErreurIA>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ErreurIA erreurIA = ds.getValue(ErreurIA.class);
                    Log.i(TAG, erreurIA.toString());
                    listeErreurs.add(erreurIA);
                }
                toast_db.setText("Database synced.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });


        if(showToasts) toast_db.show();
        ArrayList<String> nameSubErreurs = new ArrayList<>();

        zonesRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
        });
    }
}

