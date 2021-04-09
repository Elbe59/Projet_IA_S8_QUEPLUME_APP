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

public class ParaFragment extends Fragment {
    public ParaFragment(){}

    private String TAG = "parameter_fragment";

    private TextView Tboite_blanc;
    private TextView Tboite_noir;
    private TextView Tcouvercle_noir;
    private TextView Tcouvercle_blanc;
    private TextView Tgoupille_gris;
    private TextView Tgoupille_rouge;

    private Button Bboite_blanc_plus;
    private Button Bboite_noir_plus;
    private Button Bcouvercle_noir_plus;
    private Button Bcouvercle_blanc_plus;
    private Button Bgoupille_gris_plus;
    private Button Bgoupille_rouge_plus;

    private Button Bboite_blanc_minus;
    private Button Bboite_noir_minus;
    private Button Bcouvercle_noir_minus;
    private Button Bcouvercle_blanc_minus;
    private Button Bgoupille_gris_minus;
    private Button Bgoupille_rouge_minus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_parameter, container, false);

        Tboite_blanc = view.findViewById(R.id.textview_boite_blanc_para);
        Tboite_noir = view.findViewById(R.id.textview_boite_noir_para);
        Tcouvercle_blanc = view.findViewById(R.id.textview_couvercle_blanc_para);
        Tcouvercle_noir = view.findViewById(R.id.textview_couvercle_noir_para);
        Tgoupille_gris = view.findViewById(R.id.textview_goupille_gris_para);
        Tgoupille_rouge = view.findViewById(R.id.textview_goupille_rouge_para);

        Bboite_blanc_plus = view.findViewById(R.id.button_boite_blanc_plus);
        Bboite_noir_plus = view.findViewById(R.id.button_boite_noir_plus);
        Bcouvercle_blanc_plus = view.findViewById(R.id.button_couvercle_blanc_plus);
        Bcouvercle_noir_plus = view.findViewById(R.id.button_couvercle_noir_plus);
        Bgoupille_gris_plus = view.findViewById(R.id.button_goupille_gris_plus);
        Bgoupille_rouge_plus = view.findViewById(R.id.button_goupille_rouge_plus);

        Bboite_blanc_minus = view.findViewById(R.id.button_boite_blanc_minus);
        Bboite_noir_minus = view.findViewById(R.id.button_boite_noir_minus);
        Bcouvercle_blanc_minus = view.findViewById(R.id.button_couvercle_blanc_minus);
        Bcouvercle_noir_minus = view.findViewById(R.id.button_couvercle_noir_minus);
        Bgoupille_gris_minus = view.findViewById(R.id.button_goupille_gris_minus);
        Bgoupille_rouge_minus = view.findViewById(R.id.button_goupille_rouge_minus);

        Bboite_blanc_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //updateDB("boite_blanc",1,Singleton.getInstance().getMa);
            }
        });
        Bboite_noir_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("boite_noir",1,Singleton.getInstance().getPlaceInBox_Boite_Noir());
            }
        });
        Bcouvercle_blanc_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDB("couvercle_blanc",1,Singleton.getInstance().getPlaceInBox_Couvercle_Blanc());
            }
        });
        Bcouvercle_noir_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Couvercle_Noir(Singleton.getInstance().getPlaceInBox_Couvercle_Noir()+1);
            }
        });
        Bgoupille_gris_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Goupille_Gris(Singleton.getInstance().getPlaceInBox_Goupille_Gris()+1);
            }
        });
        Bgoupille_rouge_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Goupille_Rouge(Singleton.getInstance().getPlaceInBox_Goupille_Rouge()+1);
            }
        });

        Bboite_blanc_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Singleton.getInstance().setPlaceInBox_Boite_Blanc(Singleton.getInstance().getPlaceInBox_Boite_Blanc()-1);
                //MAJplaces();
            }
        });
        Bboite_noir_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Boite_Noir(Singleton.getInstance().getPlaceInBox_Boite_Noir()-1);
            }
        });
        Bcouvercle_blanc_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Couvercle_Blanc(Singleton.getInstance().getPlaceInBox_Couvercle_Blanc()-1);
            }
        });
        Bcouvercle_noir_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Couvercle_Noir(Singleton.getInstance().getPlaceInBox_Couvercle_Noir()-1);
            }
        });
        Bgoupille_gris_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Goupille_Gris(Singleton.getInstance().getPlaceInBox_Goupille_Gris()-1);
            }
        });
        Bgoupille_rouge_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Singleton.getInstance().setPlaceInBox_Goupille_Rouge(Singleton.getInstance().getPlaceInBox_Goupille_Rouge()-1);
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

    public void MAJtexts(){
        Tboite_blanc.setText(""+Singleton.getInstance().getPlaceInBox_Boite_Blanc());
        Tboite_noir.setText(""+Singleton.getInstance().getPlaceInBox_Boite_Noir());
        Tcouvercle_blanc.setText(""+Singleton.getInstance().getPlaceInBox_Couvercle_Blanc());
        Tcouvercle_noir.setText(""+Singleton.getInstance().getPlaceInBox_Couvercle_Noir());
        Tgoupille_gris.setText(""+Singleton.getInstance().getPlaceInBox_Goupille_Gris());
        Tgoupille_rouge.setText(""+Singleton.getInstance().getPlaceInBox_Goupille_Rouge());
    }

    public void updateDB(String type, int ope, int lastValue)
    {
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
