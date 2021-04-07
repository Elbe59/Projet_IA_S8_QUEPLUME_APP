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


        mTextViewBoiteNoir = (TextView) view.findViewById(R.id.textview_boite_noir);
        mTextViewBoiteBlanc = (TextView) view.findViewById(R.id.textview_boite_blanc);
        mTextViewCouvercleBlanc = (TextView) view.findViewById(R.id.textview_couvercle_blanc);
        mTextViewCouvercleNoir = (TextView) view.findViewById(R.id.textview_couvercle_noir);
        mTextViewGoupilleGris = (TextView) view.findViewById(R.id.textview_goupille_gris);
        mTextViewGoupilleRouge = (TextView) view.findViewById(R.id.textview_goupille_rouge);
        mTextViewNbrTraiter = (TextView) view.findViewById(R.id.textview_nbr_pieces_24h);
        mTextViewNbrErreurs = (TextView) view.findViewById(R.id.textview_nbr_erreurs_24h);

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference();
        zonesRef.addValueEventListener(new ValueEventListener() {

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


        return view;
    }


    public void miseAJourTextView(){
        // Met à jour le nombre de piéces traitées et le nbr d'erreur en 24h
        mTextViewNbrErreurs.setText(Singleton.getInstance().getNbErreurs()+"");
        mTextViewNbrTraiter.setText(Singleton.getInstance().getNbPieceTraitee()+"");

        // Met à jour le taux de remplissage de chaque bac
        Map<String,Integer> hashMapActualData = new HashMap<>();
        hashMapActualData = Singleton.getInstance().getHashMapDataActuel();

        if(hashMapActualData.get("false_boite_noir") >= 1) {
            mTextViewBoiteNoir.setText("Erreur");
        } else {
            mTextViewBoiteNoir.setText(hashMapActualData.get("true_boite_noir").toString());
        }

        if(hashMapActualData.get("false_boite_blanc") >= 1) {
            mTextViewBoiteBlanc.setText("Erreur");
        } else {
            mTextViewBoiteBlanc.setText(hashMapActualData.get("true_boite_blanc").toString());
        }

        if(hashMapActualData.get("false_couvercle_noir") >= 1) {
            mTextViewCouvercleNoir.setText("Erreur");
        } else {
            mTextViewCouvercleNoir.setText(hashMapActualData.get("true_couvercle_noir").toString());
        }

        if(hashMapActualData.get("false_couvercle_blanc") >= 1) {
            mTextViewCouvercleBlanc.setText("Erreur");
        } else {
            mTextViewCouvercleBlanc.setText(hashMapActualData.get("true_couvercle_blanc").toString());
        }

        if(hashMapActualData.get("false_goupille_rouge") >= 1) {
            mTextViewGoupilleRouge.setText("Erreur");
        } else {
            mTextViewGoupilleRouge.setText(hashMapActualData.get("true_goupille_rouge").toString());
        }

        if(hashMapActualData.get("false_goupille_gris") >= 1) {
            mTextViewGoupilleGris.setText("Erreur");
        } else {
            mTextViewGoupilleGris.setText(hashMapActualData.get("true_goupille_gris").toString());
        }
    }
}
