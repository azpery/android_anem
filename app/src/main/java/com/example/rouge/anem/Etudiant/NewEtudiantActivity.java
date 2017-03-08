package com.example.rouge.anem.Etudiant;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rouge.anem.Entity.Utilisateur;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;

import java.io.IOException;

public class NewEtudiantActivity extends Fragment {
    Utilisateur etudiant = new Utilisateur();
    public NewEtudiantActivity(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.activity_view_etudiant, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fill();
    }

    private void fill(){
        ((EditText) getView().findViewById(R.id.nomEtudiant)).setText(etudiant.getNom());
        ((EditText) getView().findViewById(R.id.prenomEtudiant)).setText(etudiant.getPrenom());
        ((EditText) getView().findViewById(R.id.adresseMel)).setText(etudiant.getEmail());
    }


}
