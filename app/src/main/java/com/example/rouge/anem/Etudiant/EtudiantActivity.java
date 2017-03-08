package com.example.rouge.anem.Etudiant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rouge.anem.Entity.Utilisateur;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rouge on 04/01/2017.
 */

public class EtudiantActivity extends AppCompatActivity {
    private ArrayList<Utilisateur> listeEtudiant;
    private Api myModel;
    private ListView listView;
    private Callback callback;
    private EtudiantAdapter patientAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        this.callback = new Callback<Void>() {
            public Void call() {
                didReceivedData();
                return null;
            }
        };
        myModel = new Api(this.callback, this);
        listeEtudiant = new ArrayList<Utilisateur>();
        listView = (ListView)findViewById(R.id.eListe);
        patientAdapter = new EtudiantAdapter(getBaseContext(), listeEtudiant);
        listView.setAdapter(patientAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(EtudiantActivity.this, TabEtudiantActivity.class);
                myIntent.putExtra("etudiant", (Serializable) listeEtudiant.get(position));
                startActivity(myIntent);
            }
        });
        try {
            String[] mesparams = {Util.getProperty("url.etudiant", getBaseContext())};
            AsyncTask<String, String, Boolean> mThreadCon = new Api(this.callback, this).execute(mesparams);
        }catch(IOException i ){
            Log.d("Erreur de propriété", i.toString());
        }

        EditText rechercher = (EditText) findViewById(R.id.rechercherEtu);

        TextWatcher fieldValidatorTextWatcher = rechercher();
        rechercher.addTextChangedListener(fieldValidatorTextWatcher);
    }

    public void didReceivedData(){
        ArrayList<HashMap<String,Object>> result = this.callback.getResult();
        listeEtudiant = Utilisateur.getUtilisateursFromWS(result);
        patientAdapter.setListEtudiant(listeEtudiant);
        patientAdapter.notifyDataSetChanged();
        result = result;
    }
    public TextWatcher rechercher(){
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                patientAdapter.rechercher(s.toString());
            }

        };
    }
}
