package fr.hei.que_plume_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatistiqueFragment extends Fragment {

    private RecyclerView recyclerView;
    private String TAG = "Stat_activity";

    private RecyclerView.LayoutManager layoutManager;

    public StatistiqueFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "Création de la fenêtre");

        View view = inflater.inflate(R.layout.activity_statistique, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_statistique);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        Singleton.getInstance().adapter_statistique = new StatistiqueAdapter();
        recyclerView.setAdapter(Singleton.getInstance().adapter_statistique);
        return view;
    }

    public class StatistiqueAdapter  extends RecyclerView.Adapter<StatistiqueAdapter.MyviewHolder> {
        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MyviewHolder(inflater.inflate(R.layout.layoutline_statistique,parent,false));
        }

        @Override
        public void onBindViewHolder(MyviewHolder holder, int position) {
            holder.setLine(Singleton.getInstance().decode(Singleton.getInstance().getTotalErreur().get(position)));
            Log.d(TAG,"line : " + position);
        }

        @Override
        public int getItemCount() {
            return Singleton.getInstance().getTotalErreur().size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView mTextView_predictionIA;
            private TextView mTextView_realite;
            private TextView mTextView_total_erreur;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                mTextView_predictionIA = (TextView) itemView.findViewById(R.id.textview_statistique_prediction_ia);
                mTextView_realite = (TextView) itemView.findViewById(R.id.textview_statistique_realite);
                mTextView_total_erreur = (TextView) itemView.findViewById(R.id.textview_statistique_total_erreur);
            }

            public void setLine(ArrayList<String> line) {
                mTextView_predictionIA.setText(line.get(1));
                mTextView_realite.setText(line.get(0));
                mTextView_total_erreur.setText(line.get(2));

                Log.d(TAG, "Chargement ligne: "+line.toString());
            }
        }
    }
}
