package fr.hei.que_plume_app;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.google.firebase.database.FirebaseDatabase;

/*
    On crée un menu de navigation qui permet à l'utilisateur de naviguer rapidement entre les différents
    fragments de l'application:
        - AccueilFragment: Sur ce fragment l'utilisateur peut visualiser un tableau de bord avec les informations en direct
                            concernant le remplissage des bacs et les erreurs.
        - HistoriqueFragment: Ce fragment permet de voir l'historique des erreurs, cet à dire lorsqu'une pièce a été placée dans un mauvais bac
        - StatistiqueFragment: Ce fragment permet de voir quelques statistiques à propos des erreurs (Quels sont les erreurs avec la plus grande occurrence).
        - ParametresFragment: Fragment permettant à l'utilisateur de modifier certain paramètre afin de configuerer l'application selon son besoin.
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "Main_Act";

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "création du menu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu_principal, R.id.nav_menu_historique, R.id.nav_menu_statistiques, R.id.nav_menu_para)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FirebaseDatabase.getInstance().goOnline();
        Singleton.getInstance().fetchFromDatabase(this, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}