package com.example.rouge.anem.Entreprise;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;

import java.io.IOException;

public class NewEntrepriseActivity extends Fragment {
    private Entreprise entreprise = new Entreprise();
    public NewEntrepriseActivity(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.activity_new_entreprise, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button button = (Button) getView().findViewById(R.id.valider);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
        fill();
    }

    private void fill(){
        ((EditText) getView().findViewById(R.id.tel)).setText(getEntreprise().getNumTel());
        ((EditText) getView().findViewById(R.id.nom)).setText(getEntreprise().getNom());
        ((EditText) getView().findViewById(R.id.adresse)).setText(getEntreprise().getAdresse());
    }

    private void save(){
        getEntreprise().setNumTel(((EditText) getView().findViewById(R.id.tel)).getText().toString());
        getEntreprise().setNom(((EditText) getView().findViewById(R.id.nom)).getText().toString());
        getEntreprise().setAdresse(((EditText) getView().findViewById(R.id.adresse)).getText().toString());
        Callback callback= new Callback<Void>() {
            public Void call() {
                getActivity().finish();
                return null;
            }
        };
        try {
            Api api = new Api(callback, getView().getContext());
            getEntreprise().save(api,getView().getContext());
        }catch(IOException i ){
            Log.d("Erreur de propriété", i.toString());
        }
    }


    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}
