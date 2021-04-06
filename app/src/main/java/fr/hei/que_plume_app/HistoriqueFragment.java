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

import fr.hei.que_plume_app.entity.ErreurIA;

public class HistoriqueFragment extends Fragment {

    private RecyclerView recyclerView;
    private String TAG = "Histo_activity";

    private RecyclerView.LayoutManager layoutManager;


    public HistoriqueFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_historique, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_historique);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        Singleton.getInstance().adapter_historique = new HistoriqueAdapter();
        recyclerView.setAdapter(Singleton.getInstance().adapter_historique);
        return view;
    }

    public class HistoriqueAdapter  extends RecyclerView.Adapter<HistoriqueAdapter.MyviewHolder> {
        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MyviewHolder(inflater.inflate(R.layout.layoutline_historique,parent,false));
        }

        @Override
        public void onBindViewHolder(MyviewHolder holder, int position) {
            holder.setErreur(Singleton.getInstance().getErreurInOrderAtPosition(position));
        }

        @Override
        public int getItemCount() {
            return Singleton.getInstance().getErreurs().size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView mTextViewPrediction;
            private TextView mTextViewRealite;
            private TextView mTextViewDate;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewPrediction = (TextView) itemView.findViewById(R.id.textview_historique_prediction_IA);
                mTextViewRealite = (TextView) itemView.findViewById(R.id.textview_historique_realite_piece);
                mTextViewDate = (TextView) itemView.findViewById(R.id.textview_historique_dateheure_erreur);
            }

            public void setErreur(ErreurIA erreurIA) {
                String prediction = erreurIA.getType_trouve() + " - " + erreurIA.getCouleur_trouvee();
                String realite = erreurIA.getType_reel() + " - " + erreurIA.getCouleur_reelle();
                String date = erreurIA.getDate();
                mTextViewPrediction.setText(prediction);
                mTextViewRealite.setText(realite);
                mTextViewDate.setText(date);
            }
        }
    }


}