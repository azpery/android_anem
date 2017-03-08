package com.example.rouge.anem.Entreprise;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rouge.anem.R;
import com.example.rouge.anem.Stage.StageActivity;

public class NewEntrepriseContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entreprise_container);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_new_entreprise_container, new NewEntrepriseActivity())
                .commit();
    }
}
