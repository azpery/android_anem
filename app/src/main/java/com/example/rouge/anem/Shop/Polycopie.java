package com.example.rouge.anem.Shop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.widget.Toast;


import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by laureduchemin on 11/01/2017.
 */

public class Polycopie extends AppCompatActivity{

    private Api myModel;
    private Callback callback;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpoly);
        this.callback = new Callback<Void>() {
            public Void call() {
                didSendData();
                return null;
            }


        };

        //myModel = new Api(this.callback);

        try {
            String[] mesparams = {Util.getProperty("url.shop", getBaseContext())};
            myModel.execute(mesparams);
        }catch(IOException i ){
            Log.d("Erreur de propriété", i.toString());
        }


        Spinner spinner = (Spinner) findViewById(R.id.spinnerCours);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();


                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button = (Button) findViewById(R.id.ButtonPolyAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_commande_valide);


            }
        });

    }

    private void didSendData() {
    }

}