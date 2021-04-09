package fr.hei.que_plume_app;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
    private HashMap<TextView,String> listTextViewToDatabase = new HashMap<>(); // Contient une hashMap avec pour clé la textview et comme valeur le string dans la base


    // (Nuance , R , G , B )
    int rouge = 255*256*256*256+255*256*256+0*256+0;
    int blanc = 255*256*256*256+255*256*256+255*256+255;
    int vert = Color.GREEN;

    private String TAG = "Fragment Accueil";

    public AccueilFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Création de la fenêtre");
        View view =  inflater.inflate(R.layout.activity_menu, container, false);

        mTextViewBoiteNoir = (TextView) view.findViewById(R.id.textview_boite_noir);
        mTextViewBoiteBlanc = (TextView) view.findViewById(R.id.textview_boite_blanc);
        mTextViewCouvercleBlanc = (TextView) view.findViewById(R.id.textview_couvercle_blanc);
        mTextViewCouvercleNoir = (TextView) view.findViewById(R.id.textview_couvercle_noir);
        mTextViewGoupilleGris = (TextView) view.findViewById(R.id.textview_goupille_gris);
        mTextViewGoupilleRouge = (TextView) view.findViewById(R.id.textview_goupille_rouge);
        mTextViewNbrTraiter = (TextView) view.findViewById(R.id.textview_nbr_pieces_24h);
        mTextViewNbrErreurs = (TextView) view.findViewById(R.id.textview_nbr_erreurs_24h);

        HashMap<TextView,String> listTextViewToDatabase = new HashMap<>();
        listTextViewToDatabase.put(mTextViewBoiteNoir,"boite_noir");listTextViewToDatabase.put(mTextViewBoiteBlanc,"boite_blanc");
        listTextViewToDatabase.put(mTextViewCouvercleNoir,"couvercle_noir");listTextViewToDatabase.put(mTextViewCouvercleBlanc,"couvercle_blanc");
        listTextViewToDatabase.put(mTextViewGoupilleGris,"goupille_gris");listTextViewToDatabase.put(mTextViewGoupilleRouge,"goupille_rouge");

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference();
        zonesRef.addValueEventListener(new ValueEventListener() {

            Toast toast_db = Toast.makeText(getContext(),"Trying to connect to the database...", Toast.LENGTH_SHORT);
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toast_db.setText("Database synced");
                miseAJourTextView(listTextViewToDatabase);
                Log.d(TAG, "onDataChange");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toast_db.setText("Error: Couldn't connect to the database.\nPlease restart the app and check your internet connexion.");
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        return view;
    }

    public void onClickRemoveError(View view, TextView textView, String nameInDatabase) {
        Log.d(TAG, "CLick pour enlever l'erreur, élément "+nameInDatabase);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Avez-vous vérifié l'erreur ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OUI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String falseDatabase = "false_";
                        DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("resultat");
                        String childToModify = falseDatabase+nameInDatabase;
                        updateData.child(childToModify).setValue(0);
                        System.out.println(childToModify);
                        //
                        dialog.cancel();
                        Log.i(TAG, "Validation click, suppression erreur envoyé dans firebase");
                    }
                });

        builder1.setNegativeButton(
                "NON",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Log.d(TAG,"Click abandonné");
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void onClickRemoveBac(View view, TextView textView, String nameInDatabase, Integer valeurCorrespondante) {

        Log.d(TAG,"Click pour vider back "+nameInDatabase);

        if(valeurCorrespondante != 0) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Voulez-vous vraiment vider ce bac ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OUI",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Fonction de base
                            String trueDatabase = "true_";
                            //textView.setBackgroundColor(blanc);
                            DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("resultat");
                            String childToModify = trueDatabase + nameInDatabase;
                            updateData.child(childToModify).setValue(0);
                            System.out.println(childToModify);
                            //
                            dialog.cancel();
                            Log.i(TAG, "Validation click, back vide envoyé dans firebase");
                        }
                    });

            builder1.setNegativeButton(
                    "NON",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Log.d(TAG,"Click annulé");
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public void miseAJourTextView(Map<TextView,String> listTextViewToDatabase){

        Log.d(TAG,"Update des textview (chgmt ds firebase)");

        // Met à jour le nombre de piéces traitées et le nbr d'erreur en 24h
        String percentErreur = Singleton.getInstance().getNbErreurs()+"";
        percentErreur = percentErreur.charAt(0)+""+percentErreur.charAt(1)+"%";
        mTextViewNbrErreurs.setText(percentErreur);
        mTextViewNbrTraiter.setText(Singleton.getInstance().getNbPieceTraitee()+"");

        // Met à jour le taux de remplissage de chaque bac
        Map<String,Integer> hashMapActualData = new HashMap<>();
        hashMapActualData = Singleton.getInstance().getHashMapDataActuel();
        Map<String,Integer> hashMapNbrMaxParBac = new HashMap<>();
        hashMapNbrMaxParBac = Singleton.getInstance().getHashMapNbrMaxParBacActuel();

        for(Map.Entry<TextView, String> entry : listTextViewToDatabase.entrySet()) { // Parcours tous les TextView avec leur nom dans la database
            String falseDatabase = "false_";
            String trueDatabase = "true_";
            Integer valeurCorrespondante = hashMapActualData.get(trueDatabase+entry.getValue());
            Integer valeurMaxCorrespondante = hashMapNbrMaxParBac.get(entry.getValue());
            System.out.println(entry.getKey() + " - " + entry.getValue());

            if(hashMapActualData.get(falseDatabase+entry.getValue()) >= 1) {
                entry.getKey().setText("Erreur");
                entry.getKey().setBackgroundColor(rouge);
                entry.getKey().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickRemoveError(view,entry.getKey(),entry.getValue());
                    }
                });
            } else {
                entry.getKey().setText(valeurCorrespondante.toString() + " / " + valeurMaxCorrespondante);
                if(hashMapActualData.get(trueDatabase+entry.getValue()) >= valeurMaxCorrespondante){
                    entry.getKey().setBackgroundColor(vert);
                    entry.getKey().setText("Plein");
                }
                else {
                    entry.getKey().setBackgroundColor(blanc);
                }
                entry.getKey().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickRemoveBac(view,entry.getKey(),entry.getValue(),valeurCorrespondante);
                    }
                });

                if(valeurMaxCorrespondante == 0){
                    entry.getKey().setText("Inactif");
                    entry.getKey().setBackgroundColor(rouge);
                    entry.getKey().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast toast = Toast.makeText(getContext(),"Erreur: Quantité réglée sur 0", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
        }

    }
}
