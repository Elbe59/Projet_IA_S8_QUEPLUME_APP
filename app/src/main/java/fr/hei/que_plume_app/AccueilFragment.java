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


/*
    Cette page est la première sur laquelle l’utilisateur arrive en ouvrant l’application.
    Il peut y voir un tableau de bord sur lequel est représenté les différents bacs
    ainsi que le nombre de pièces traitées pendant les dernières 24h et le pourcentage d’erreur correspondant.

    Pour chacun des bacs, on récupère les informations de notre base de données Firebase.
    Ainsi, dans chaque case (bac) sera affiché le nombre de pièces présentes dans la réalité par rapport à la place totale du bac
    afin que l’utilisateur sache lorsqu’il est fortement rempli et ainsi l’informer qu’il va falloir le vider.

    Lorsqu’il y a une erreur dans un bac, un message d’erreur s’affiche et l’utilisateur peut confirmer qu’il a corrigé l’erreur en cliquant sur le bac.

    De plus, à n’importe quel moment, l’utilisateur peut vider un bac en cliquant dessus.
    Lorsqu’un bac est plein, le message « Plein » s’affiche sur le bac concerné, l’utilisateur peut alors le vider en cliquant dessus.

    Chaque action de l’utilisateur va modifier en temps réel la base de données
 */
public class AccueilFragment extends Fragment {


    private TextView mTextViewBoiteNoir;
    private TextView mTextViewBoiteBlanc;
    private TextView mTextViewCouvercleNoir;
    private TextView mTextViewCouvercleBlanc;
    private TextView mTextViewGoupilleRouge;
    private TextView mTextViewGoupilleGris;
    private TextView mTextViewNbrErreurs;
    private TextView mTextViewNbrTraiter;
    private HashMap<TextView,String> listTextViewToDatabase = new HashMap<>(); // Va contenir une hashMap avec pour clé la textview et comme valeur le string dans la base de la base de donnée.


    // On défini quelque couleurs pour nos bacs
    int rouge = 255*256*256*256+255*256*256+0*256+0;
    int blanc = 255*256*256*256+255*256*256+255*256+255;
    int vert = Color.GREEN;
    int gris = 255*256*256*256+178*256*256+178*256+178;

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

        // Cette variable va permettre de lier les textView par rapport au nom dans la base de donnée.
        // Exemple, dans la base de donnée on identifie les boîte noir par l'enfant: boite_noir. Et dans le layout, le textView correspondant à la boîte noir
        // est mTextViewBoiteNoir  donc dans la hashMap on lie les deux : listTextViewToDatabase.put(mTextViewBoiteNoir,"boite_noir")
        HashMap<TextView,String> listTextViewToDatabase = new HashMap<>();
        listTextViewToDatabase.put(mTextViewBoiteNoir,"boite_noir");listTextViewToDatabase.put(mTextViewBoiteBlanc,"boite_blanc");
        listTextViewToDatabase.put(mTextViewCouvercleNoir,"couvercle_noir");listTextViewToDatabase.put(mTextViewCouvercleBlanc,"couvercle_blanc");
        listTextViewToDatabase.put(mTextViewGoupilleGris,"goupille_gris");listTextViewToDatabase.put(mTextViewGoupilleRouge,"goupille_rouge");

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference(); // Fait une référence au début de l'arborescence de la base de donnée

        // Lorsque la base de donnée est modifié, on appelle la méthode miseAJourTextView qui met à jour tous les textView du layout ( les différents bacs).
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

    // Corrige une erreur aprés confirmation de l'utilisateur (met à jour l'application)
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

    // Vide un bac aprés confirmation de l'utilisateur (met à jour l'application)
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

    // Cette méthode est nécessaire pour mettre à jour les textview lorsque qu'il y a un changement dans la base de donnée.
    public void miseAJourTextView(Map<TextView,String> listTextViewToDatabase){

        Log.d(TAG,"Update des textview (chgmt ds firebase)");

        // Met à jour le nombre de piéces traitées et le nbr d'erreur en 24h
        String percentErreur = Singleton.getInstance().getNbErreurs()+""; // Récupère le pourcentage d'erreur sur les dernière 24h
        percentErreur = percentErreur.charAt(0)+""+percentErreur.charAt(1)+"%";
        mTextViewNbrErreurs.setText(percentErreur);
        mTextViewNbrTraiter.setText(Singleton.getInstance().getNbPieceTraitee()+""); // Nombre de pièce traitée sur les dernières 24h

        // Met à jour le taux de remplissage de chaque bac
        Map<String,Integer> hashMapActualData = new HashMap<>();
        hashMapActualData = Singleton.getInstance().getHashMapDataActuel(); // HashMap qui contient, le remplissage actuel du  bac
        Map<String,Integer> hashMapNbrMaxParBac = new HashMap<>();
        hashMapNbrMaxParBac = Singleton.getInstance().getHashMapNbrMaxParBacActuel();  // HashMap qui contient, le remplissage maximum du bac

        for(Map.Entry<TextView, String> entry : listTextViewToDatabase.entrySet()) { // Parcours tous les TextView avec leur nom dans la database
            String falseDatabase = "false_";
            String trueDatabase = "true_";
            Integer valeurCorrespondante = hashMapActualData.get(trueDatabase+entry.getValue()); // Nombre de pièce actuelle dans le bac
            Integer valeurMaxCorrespondante = hashMapNbrMaxParBac.get(entry.getValue()); // Nombre de pièce max dans le bac
            System.out.println(entry.getKey() + " - " + entry.getValue());

            if(hashMapActualData.get(falseDatabase+entry.getValue()) >= 1) { // Si il y a une erreur dans le bac correspondant au textView
                entry.getKey().setText("Erreur");
                entry.getKey().setBackgroundColor(rouge);
                entry.getKey().setOnClickListener(new View.OnClickListener() { // Lorsque l'utilisateur clique sur un bac avec une erreur il peut informer le programme qu'il a corriger l'erreur en cliquant dessus.
                    @Override
                    public void onClick(View view) {
                        onClickRemoveError(view,entry.getKey(),entry.getValue());
                    }
                });
            } else {
                entry.getKey().setText(valeurCorrespondante.toString() + " / " + valeurMaxCorrespondante);
                if(hashMapActualData.get(trueDatabase+entry.getValue()) >= valeurMaxCorrespondante){ // Si il n'y a pas d'erreur dans le bac correspondant au textView mais que le bac est plein
                    entry.getKey().setBackgroundColor(vert);
                    entry.getKey().setText("Plein");
                }
                else { // Si il n'y a pas d'erreur dans le bac correspondant au textView et que le bac n'est pas plein
                    entry.getKey().setBackgroundColor(blanc);
                }
                entry.getKey().setOnClickListener(new View.OnClickListener() { // Lorsque l'utilisateur clique sur un bac il peut informer le programme qu'il a vider le bac en cliquant sur le bac correspondant.
                    @Override
                    public void onClick(View view) {
                        onClickRemoveBac(view,entry.getKey(),entry.getValue(),valeurCorrespondante);
                    }
                });

                if(valeurMaxCorrespondante == 0){ // Si dans les paramètres, l'utilisateur défini un bac comme étant incatif
                    entry.getKey().setText("Inactif");
                    entry.getKey().setBackgroundColor(gris);
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
