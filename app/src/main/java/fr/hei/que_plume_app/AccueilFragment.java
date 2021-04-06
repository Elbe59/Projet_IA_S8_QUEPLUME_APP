package fr.hei.que_plume_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import fr.hei.que_plume_app.entity.ActualData;

public class AccueilFragment extends Fragment {

    private TextView mTextViewBoiteNoire;
    private TextView mTextViewBoiteBlanche;
    private TextView mTextViewCouvercleNoir;
    private TextView mTextViewCouvercleBlanc;
    private TextView mTextViewGoupilleRouge;
    private TextView mTextViewGoupilleGrise;
    private TextView mTextViewNbrErreurs;
    private TextView mTextViewNbrTraiter;

    private String TAG = "Menu_activity";

    public AccueilFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_menu, container, false);
        mTextViewBoiteNoire = (TextView) view.findViewById(R.id.textview_boite_noire);
        mTextViewBoiteBlanche = (TextView) view.findViewById(R.id.textview_boite_blanche);
        mTextViewCouvercleBlanc = (TextView) view.findViewById(R.id.textview_couvercle_blanc);
        mTextViewCouvercleNoir = (TextView) view.findViewById(R.id.textview_couvercle_noir);
        mTextViewGoupilleGrise = (TextView) view.findViewById(R.id.textview_goupille_grise);
        mTextViewGoupilleRouge = (TextView) view.findViewById(R.id.textview_goupille_rouge);

        DatabaseReference zonesRefActuel = FirebaseDatabase.getInstance().getReference("resultat");
        zonesRefActuel.addValueEventListener(new ValueEventListener() {
            Toast toast_db = Toast.makeText(getContext(),"Trying to connect to the database...", Toast.LENGTH_SHORT);
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toast_db.setText("Database synced");
                //miseAJourTextView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        return view;

    }

    public void miseAJourTextView(){
        Map<String,Integer> hashMapActualData = new HashMap<>();
        hashMapActualData = Singleton.getInstance().getHashMapDataActuel();
        for (Map.Entry<String, Integer> entry : hashMapActualData.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
            if(entry.getKey().equals("true_goupille_grise")){
                mTextViewGoupilleGrise.setText(entry.getValue());
            }
            if(entry.getKey().equals("true_goupille_rouge")){
                mTextViewGoupilleRouge.setText(entry.getValue());
            }
            if(entry.getKey().equals("true_couvercle_noir")){
                mTextViewCouvercleNoir.setText(entry.getValue());
            }
            if(entry.getKey().equals("true_couvercle_blanc")){
                mTextViewCouvercleBlanc.setText(entry.getValue());
            }
            if(entry.getKey().equals("true_boite_noire")){
                mTextViewBoiteNoire.setText(entry.getValue());
            }
            if(entry.getKey().equals("true_boite_blanche")){
                mTextViewBoiteBlanche.setText(entry.getValue());
            }
            if(entry.getKey().equals("false_goupille_grise")){
                if(entry.getValue() != 0){
                    mTextViewGoupilleGrise.setText("Erreur");

                }
            }
            if(entry.getKey().equals("false_goupille_rouge")){
                if(entry.getValue() != 0){
                    mTextViewGoupilleRouge.setText("Erreur");

                }
            }
            if(entry.getKey().equals("false_couvercle_noir")){
                if(entry.getValue() != 0){
                    mTextViewCouvercleNoir.setText("Erreur");
                }
            }
            if(entry.getKey().equals("false_couvercle_blanc")){
                if(entry.getValue() != 0){
                    mTextViewCouvercleBlanc.setText("Erreur");
                }
            }
            if(entry.getKey().equals("false_boite_noire")){
                if(entry.getValue() != 0){
                    mTextViewBoiteNoire.setText("Erreur");
                }
            }
            if(entry.getKey().equals("false_boite_blanche")){
                if(entry.getValue() != 0){
                    mTextViewBoiteBlanche.setText("Erreur");
                }
            }
        }

    }
}
