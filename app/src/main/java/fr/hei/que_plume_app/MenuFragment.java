package fr.hei.que_plume_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    private TextView mTextViewBoiteNoir;
    private TextView mTextViewBoiteBlanche;
    private TextView mTextViewCouvercleNoir;
    private TextView mTextViewCouvercleBlanc;
    private TextView mTextViewGoupilleRouge;
    private TextView mTextViewGoupilleGrise;
    private TextView mTextViewNbrErreurs;
    private TextView mTextViewNbrTraiter;

    private String TAG = "Menu_activity";

    public MenuFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_menu, container, false);
        return view;
    }
}
