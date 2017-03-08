package com.example.rouge.anem.Entreprise;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.Etudiant.EtudiantActivity;
import com.example.rouge.anem.Main.MainActivity;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class EntrepriseActivity extends AppCompatActivity {
    private ArrayList<Entreprise> listeEntreprise;
    private Api myModel;
    private ListView listView;
    private Callback callback;
    private EntrepriseAdapter patientAdapter;
    private Boolean isSelecting = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entreprise);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("isSelecting")) {
                this.isSelecting = b.getBoolean("isSelecting");
            }
        }
        this.callback = new Callback<Void>() {
            public Void call() {
                didReceivedData();
                return null;
            }
        };
        myModel = new Api(this.callback, this);
        listeEntreprise = new ArrayList<Entreprise>();
        listView = (ListView)findViewById(R.id.lvListe);
        patientAdapter = new EntrepriseAdapter(getBaseContext(), listeEntreprise);
        listView.setAdapter(patientAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isSelecting){
                    Intent myIntent = new Intent(EntrepriseActivity.this, TabEntrepriseActivity.class);
                    myIntent.putExtra("entreprise", listeEntreprise.get(position));
                    startActivity(myIntent);
                }else{
                    setResult(Activity.RESULT_OK, new Intent().putExtra("entreprise", listeEntreprise.get(position)));
                    finish();
                }
            }
        });

        EditText rechercher = (EditText) findViewById(R.id.rechercher);

        TextWatcher fieldValidatorTextWatcher = rechercher();
        rechercher.addTextChangedListener(fieldValidatorTextWatcher);


    }

    public void refresh(){
        try {
            String[] mesparams = {Util.getProperty("url.entreprise", getBaseContext())};
            myModel.execute(mesparams);
            myModel = new Api(this.callback, this);
        }catch(IOException i ){
            Log.d("Erreur de propriété", i.toString());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
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

    public void didReceivedData(){
        ArrayList<HashMap<String,Object>> result = this.callback.getResult();
        listeEntreprise = Entreprise.getEntreprisesFromWS(result);
        patientAdapter.setInitialListEntreprise(listeEntreprise);
        patientAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_plus) {
            startActivity(new Intent(this, NewEntrepriseContainer.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
