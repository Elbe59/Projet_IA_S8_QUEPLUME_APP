package fr.hei.que_plume_app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MenuActivity extends AppCompatActivity {

    private TextView mTextViewBoiteNoir;
    private TextView mTextViewBoiteBlanche;
    private TextView mTextViewCouvercleNoir;
    private TextView mTextViewCouvercleBlanc;
    private TextView mTextViewGoupilleRouge;
    private TextView mTextViewGoupilleGrise;
    private TextView mTextViewNbrErreurs;
    private TextView mTextViewNbrTraiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_que);
        mTextViewBoiteNoir = (TextView) findViewById(R.id.textview_boite_noir);


    }
}
