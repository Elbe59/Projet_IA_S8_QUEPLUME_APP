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

import fr.hei.que_plume_app.entity.ErreurIA;

public class StatistiqueFragment extends Fragment {

    private RecyclerView recyclerView;
    private String TAG = "Stat_activity";

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter_stat;

    private ArrayList<ErreurIA> listErreur;

    public StatistiqueFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_statistique, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_statistique);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter_stat = new StatAdapter();
        recyclerView.setAdapter(adapter_stat);
        listErreur = Singleton.getInstance().getErreurs();
        return view;
    }

    public class StatAdapter  extends RecyclerView.Adapter<StatAdapter.MyviewHolder> {

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MyviewHolder(inflater.inflate(R.layout.layoutline_statistique,parent,false));
        }

        @Override
        public void onBindViewHolder(MyviewHolder holder, int position) {
            holder.setLine(position);
            Log.d(TAG,"line : " + position);
        }

        @Override
        public int getItemCount() {
            return Singleton.getInstance().getTotalErreur().size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView image_pred;
            private TextView image_piece;
            private TextView total_erreur;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                image_pred = itemView.findViewById(R.id.textview_image_prediction);
                image_piece = itemView.findViewById(R.id.textview_image_piece);
                total_erreur = itemView.findViewById(R.id.textview_total_erreur);
            }

            public void setLine(int pos) {
                ArrayList<String> line = Singleton.getInstance().decode(Singleton.getInstance().getTotalErreur().get(pos));
                image_pred.setText(line.get(1));
                image_piece.setText(line.get(0));
                total_erreur.setText(line.get(2));
            }
        }
    }
}
