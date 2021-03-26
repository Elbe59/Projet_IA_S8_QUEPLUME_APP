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

public class HistoriqueFragment extends Fragment {

    private RecyclerView recyclerView;
    private String TAG = "Histo_activity";

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter_stat;

    public HistoriqueFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_historique, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_historique);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapter_stat = new HistoAdapter();
        recyclerView.setAdapter(adapter_stat);
        return view;
    }

    public class HistoAdapter  extends RecyclerView.Adapter<HistoAdapter.MyviewHolder> {

        @NonNull
        @Override
        public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MyviewHolder(inflater.inflate(R.layout.layoutline_historique,parent,false));
        }

        @Override
        public void onBindViewHolder(MyviewHolder holder, int position) {
            holder.setLine();
            Log.d(TAG,"line : " + position);
        }

        @Override
        public int getItemCount() {
            return 8;
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView image_pred;
            private TextView image_piece;
            private TextView date;

            public MyviewHolder(@NonNull View itemView) {
                super(itemView);
                image_pred = itemView.findViewById(R.id.textview_image_prediction_histo);
                image_piece = itemView.findViewById(R.id.textview_image_piece_histo);
                date = itemView.findViewById(R.id.textview_dateheure_erreur);
            }

            public void setLine() {
                image_pred.setText("vide");
                image_piece.setText("empty");
                date.setText("jour/mois/an heure/minute");

                /*
                Picasso.get().load(prdt.getImage()).into(this.img_prdt, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Success : " + prdt.getNom());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, e.getMessage() + " " + prdt.getNom());
                    }
                });*/
            }
        }
    }


}