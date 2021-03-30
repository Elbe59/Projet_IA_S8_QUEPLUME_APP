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

    public ArrayList<ErreurIA> getErreurs() {
        return listeErreurs;
    }

    public void setErreurs(ArrayList<ErreurIA> erreur) {
        this.listeErreurs = erreur;
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

