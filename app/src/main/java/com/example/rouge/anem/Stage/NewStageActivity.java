package com.example.rouge.anem.Stage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.rouge.anem.Entity.AuthenticatedUser;
import com.example.rouge.anem.Entity.Competence;
import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.Entity.Stage;
import com.example.rouge.anem.Entreprise.EntrepriseActivity;
import com.example.rouge.anem.Entreprise.NewEntrepriseContainer;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;
import com.example.rouge.anem.View.SearchCompetenceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NewStageActivity extends AppCompatActivity{
    private Stage stage = new Stage();
    private EditText textEntreprise;
    private String[] etats = {"En attente","Validé", "Terminé"};
    private RadioGroup group;
    private ArrayList<Button> buttons = new ArrayList<>();
    private SearchCompetenceView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stage);
        group = (RadioGroup) findViewById(R.id.choixEtat);
        RadioButton button;
        for(int i = 0; i < etats.length; i++) {
            button = new RadioButton(this);
            button.setText(etats[i]);
            buttons.add(button);
            group.addView(button);
        }
        textEntreprise = (EditText) findViewById(R.id.textEntreprise);
        textEntreprise.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent myIntent = new Intent(NewStageActivity.this, EntrepriseActivity.class);
                    myIntent.putExtra("isSelecting", true);
                    startActivityForResult(myIntent, 0);
                }
            }
        });
        searchView = (SearchCompetenceView) findViewById(R.id.searchView);

    }


    public boolean onQueryTextSubmit(String query) {
        return false;
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
            save();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0){
            Bundle b = data.getExtras();
            if (b.containsKey("entreprise")){
                stage.setEntreprise((Entreprise) b.getSerializable("entreprise"));
                textEntreprise.setText(stage.getEntreprise().getNom());
                Util.hideSoftKeyboard(this, textEntreprise);
            }

        }
    }


    public void save(){
        if (!checkFields()) {
            EditText intitule = (EditText) findViewById(R.id.intitule);
            stage.setIntitule(intitule.getText().toString());
            EditText desc = (EditText) findViewById(R.id.textDesc);
            stage.setDescription(desc.getText().toString());
            stage.setEtat(etats[getCheckedEtat()]);
            stage.setCompetences(searchView.getmCompetences());
            stage.setEtudiant(AuthenticatedUser.getInstance());
            Callback callback = new Callback<Void>() {
                public Void call() {
                    finish();
                    return null;
                }
            };
            Api api = new Api(callback, this);
            try {
                stage.save(api, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkFields(){
        boolean error = false;
        EditText intitule = (EditText) findViewById(R.id.intitule);
        EditText desc = (EditText) findViewById(R.id.textDesc);
        EditText entreprise = (EditText) findViewById(R.id.textEntreprise);
        TextView comp = (TextView) findViewById(R.id.textComp);
        if (TextUtils.isEmpty(intitule.getText().toString())) {
            intitule.setError("Veuillez saisir un intitulé");
            error = true;
        }
        if (TextUtils.isEmpty(desc.getText().toString())) {
            desc.setError("Veuillez saisir une courte description");
            error = true;
        }
        if (TextUtils.isEmpty(entreprise.getText().toString())) {
            entreprise.setError("Veuillez sélectionner une entreprise d'accueil");
            error = true;
        }

        error = searchView.checkComp() || error;



        return error;

    }

    private int getCheckedEtat(){
        int checked = group.getCheckedRadioButtonId();
        for(int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getId() == checked){
                return i;
            }
        }
        return -1;
    }

}
