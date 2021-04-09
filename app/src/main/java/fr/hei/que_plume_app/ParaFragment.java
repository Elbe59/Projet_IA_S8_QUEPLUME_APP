package fr.hei.que_plume_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ParaFragment extends Fragment {
    public ParaFragment(){}

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
            }
        });
        Bboite_noir_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bcouvercle_blanc_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bcouvercle_noir_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bgoupille_gris_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bgoupille_rouge_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        Bboite_blanc_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bboite_noir_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bcouvercle_blanc_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bcouvercle_noir_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bgoupille_gris_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        Bgoupille_rouge_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        return view;
    }
}
