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

    private TextView mTextViewBoiteNoir;
    private TextView mTextViewBoiteBlanc;
    private TextView mTextViewCouvercleNoir;
    private TextView mTextViewCouvercleBlanc;
    private TextView mTextViewGoupilleRouge;
    private TextView mTextViewGoupilleGris;
    private TextView mTextViewNbrErreurs;
    private TextView mTextViewNbrTraiter;


    private String TAG = "Menu_activity";

    public AccueilFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_menu, container, false);


        //Singleton.getInstance().getDateActual();

        mTextViewBoiteNoir = (TextView) view.findViewById(R.id.textview_boite_noir);
        mTextViewBoiteBlanc = (TextView) view.findViewById(R.id.textview_boite_blanc);
        mTextViewCouvercleBlanc = (TextView) view.findViewById(R.id.textview_couvercle_blanc);
        mTextViewCouvercleNoir = (TextView) view.findViewById(R.id.textview_couvercle_noir);
        mTextViewGoupilleGris = (TextView) view.findViewById(R.id.textview_goupille_gris);
        mTextViewGoupilleRouge = (TextView) view.findViewById(R.id.textview_goupille_rouge);
        mTextViewNbrTraiter = (TextView) view.findViewById(R.id.textview_nbr_pieces_24h);
        mTextViewNbrErreurs = (TextView) view.findViewById(R.id.textview_nbr_erreurs_24h);

        DatabaseReference zonesRefActuel = FirebaseDatabase.getInstance().getReference("resultat");
        zonesRefActuel.addValueEventListener(new ValueEventListener() {

            Toast toast_db = Toast.makeText(getContext(),"Trying to connect to the database...", Toast.LENGTH_SHORT);
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toast_db.setText("Database synced");
                miseAJourTextView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
        mTextViewNbrErreurs.setText(Singleton.getInstance().getNbErreurs()+"");
        mTextViewNbrTraiter.setText(Singleton.getInstance().getNbPieceTraitee()+"");

        return view;
    }

    public void miseAJourTextView(){
        Map<String,Integer> hashMapActualData = new HashMap<>();
        hashMapActualData = Singleton.getInstance().getHashMapDataActuel();
        for (Map.Entry<String, Integer> entry : hashMapActualData.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
            if(entry.getKey().equals("true_goupille_gris")){
                mTextViewGoupilleGris.setText(entry.getValue().toString());
            }
            if(entry.getKey().equals("true_goupille_rouge")){
                mTextViewGoupilleRouge.setText(entry.getValue().toString());
            }
            if(entry.getKey().equals("true_couvercle_noir")){
                mTextViewCouvercleNoir.setText(entry.getValue().toString());
            }
            if(entry.getKey().equals("true_couvercle_blanc")){
                mTextViewCouvercleBlanc.setText(entry.getValue().toString());
            }
            if(entry.getKey().equals("true_boite_noir")){
                mTextViewBoiteNoir.setText(entry.getValue().toString());
            }
            if(entry.getKey().equals("true_boite_blanc")){
                mTextViewBoiteBlanc.setText(entry.getValue().toString());
            }
            if(entry.getKey().equals("false_goupille_gris")){
                if(entry.getValue() != 0){
                    mTextViewGoupilleGris.setText("Erreur");
                }
            }
            if(entry.getKey().equals("false_goupille_rouge")){
                System.out.println("COUCOU");
                System.out.println(entry.getValue());
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
            if(entry.getKey().equals("false_boite_noir")){
                if(entry.getValue() != 0){
                    mTextViewBoiteNoir.setText("Erreur");
                }
            }
            if(entry.getKey().equals("false_boite_blanc")){
                if(entry.getValue() != 0){
                    mTextViewBoiteBlanc.setText("Erreur");
                }
            }
        }

    }
}
