package com.example.rouge.anem.Etudiant;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rouge.anem.Etudiant.NewEtudiantActivity;
import com.example.rouge.anem.R;

public class NewEtudiantContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_etudiant_container);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_new_etudiant_container, new NewEtudiantActivity())
                .commit();
    }
}
