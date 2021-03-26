package fr.hei.que_plume_app;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoriqueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String TAG = "Histo_activity";

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter_stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        recyclerView = findViewById(R.id.recyclerView_historique);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter_stat = new HistoAdapter();
        recyclerView.setAdapter(adapter_stat);
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