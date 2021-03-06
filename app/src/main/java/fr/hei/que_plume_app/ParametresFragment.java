package fr.hei.que_plume_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// ParametresFragment: Fragment permettant à l'utilisateur de modifier certain paramètre afin de configurer l'application selon son besoin

public class ParametresFragment extends Fragment {
    public ParametresFragment(){}

    private String TAG = "parameter_fragment";

    private TextView Tboite_blanc;
    private TextView Tboite_noir;
    private TextView Tcouvercle_noir;
    private TextView Tcouvercle_blanc;
    private TextView Tgoupille_blanc;
    private TextView Tgoupille_rouge;

    private Button Bboite_blanc_plus;
    private Button Bboite_noir_plus;
    private Button Bcouvercle_noir_plus;
    private Button Bcouvercle_blanc_plus;
    private Button Bgoupille_blanc_plus;
    private Button Bgoupille_rouge_plus;

    private Button Bboite_blanc_minus;
    private Button Bboite_noir_minus;
    private Button Bcouvercle_noir_minus;
    private Button Bcouvercle_blanc_minus;
    private Button Bgoupille_blanc_minus;
    private Button Bgoupille_rouge_minus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_parameter, container, false);

        Log.d(TAG, "création de la fenêtre");

        Tboite_blanc = view.findViewById(R.id.textview_boite_blanc_para);
        Tboite_noir = view.findViewById(R.id.textview_boite_noir_para);
        Tcouvercle_blanc = view.findViewById(R.id.textview_couvercle_blanc_para);
        Tcouvercle_noir = view.findViewById(R.id.textview_couvercle_noir_para);
        Tgoupille_blanc = view.findViewById(R.id.textview_goupille_blanc_para);
        Tgoupille_rouge = view.findViewById(R.id.textview_goupille_rouge_para);

        Bboite_blanc_plus = view.findViewById(R.id.button_boite_blanc_plus);
        Bboite_noir_plus = view.findViewById(R.id.button_boite_noir_plus);
        Bcouvercle_blanc_plus = view.findViewById(R.id.button_couvercle_blanc_plus);
        Bcouvercle_noir_plus = view.findViewById(R.id.button_couvercle_noir_plus);
        Bgoupille_blanc_plus = view.findViewById(R.id.button_goupille_blanc_plus);
        Bgoupille_rouge_plus = view.findViewById(R.id.button_goupille_rouge_plus);

        Bboite_blanc_minus = view.findViewById(R.id.button_boite_blanc_minus);
        Bboite_noir_minus = view.findViewById(R.id.button_boite_noir_minus);
        Bcouvercle_blanc_minus = view.findViewById(R.id.button_couvercle_blanc_minus);
        Bcouvercle_noir_minus = view.findViewById(R.id.button_couvercle_noir_minus);
        Bgoupille_blanc_minus = view.findViewById(R.id.button_goupille_blanc_minus);
        Bgoupille_rouge_minus = view.findViewById(R.id.button_goupille_rouge_minus);

        // Pour chacun des bacs on crée deux boutons qui permettent d'augmenter ou bien de diminuer le nombre maximal de pièce dans le bac
        Bboite_blanc_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("boite_blanc",1,Singleton.getInstance().getNbrMaxObj().getNbrMax_boite_blanc());
            }
        });
        Bboite_noir_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("boite_noir",1,Singleton.getInstance().getNbrMaxObj().getNbrMax_boite_noir());
            }
        });
        Bcouvercle_blanc_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("couvercle_blanc",1,Singleton.getInstance().getNbrMaxObj().getNbrMax_couvercle_blanc());
            }
        });
        Bcouvercle_noir_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("couvercle_noir",1,Singleton.getInstance().getNbrMaxObj().getNbrMax_couvercle_noir());
            }
        });
        Bgoupille_blanc_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("goupille_blanc",1,Singleton.getInstance().getNbrMaxObj().getNbrMax_goupille_blanc());
            }
        });
        Bgoupille_rouge_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("goupille_rouge",1,Singleton.getInstance().getNbrMaxObj().getNbrMax_goupille_rouge());;
            }
        });

        Bboite_blanc_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("boite_blanc",-1,Singleton.getInstance().getNbrMaxObj().getNbrMax_boite_blanc());
            }
        });
        Bboite_noir_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("boite_noir",-1,Singleton.getInstance().getNbrMaxObj().getNbrMax_boite_noir());
            }
        });
        Bcouvercle_blanc_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("couvercle_blanc",-1,Singleton.getInstance().getNbrMaxObj().getNbrMax_couvercle_blanc());
            }
        });
        Bcouvercle_noir_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("couvercle_noir",-1,Singleton.getInstance().getNbrMaxObj().getNbrMax_couvercle_noir());
            }
        });
        Bgoupille_blanc_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("goupille_blanc",-1,Singleton.getInstance().getNbrMaxObj().getNbrMax_goupille_blanc());
            }
        });
        Bgoupille_rouge_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("goupille_rouge",-1,Singleton.getInstance().getNbrMaxObj().getNbrMax_goupille_rouge());
            }
        });

        MAJtexts();

        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference();
        zonesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MAJtexts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        return view;
    }

    // Met à jour les textview en fonction du nombre max de pièce autorisé dans le bac
    public void MAJtexts(){
        Log.d(TAG, "Maj des textviews");
        Tboite_blanc.setText(""+Singleton.getInstance().getNbrMaxObj().getNbrMax_boite_blanc());
        Tboite_noir.setText(""+Singleton.getInstance().getNbrMaxObj().getNbrMax_boite_noir());
        Tcouvercle_blanc.setText(""+Singleton.getInstance().getNbrMaxObj().getNbrMax_couvercle_blanc());
        Tcouvercle_noir.setText(""+Singleton.getInstance().getNbrMaxObj().getNbrMax_couvercle_noir());
        Tgoupille_blanc.setText(""+Singleton.getInstance().getNbrMaxObj().getNbrMax_goupille_blanc());
        Tgoupille_rouge.setText(""+Singleton.getInstance().getNbrMaxObj().getNbrMax_goupille_rouge());
    }

    // Met à jour instantannément la base de donnée.
    public void updateDB(String type, int ope, int lastValue)
    {
        Log.d(TAG, ope+" une place sur "+type);
        String childName = "nbrMax_"+type;
        if (ope==-1 && lastValue >0)
        {
            DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("nbrMaxParBac");
            updateData.child(childName).setValue(lastValue-1);
        }
        if(ope == 1)
        {
            DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("nbrMaxParBac");
            updateData.child(childName).setValue(lastValue+1);
        }
    }
}
