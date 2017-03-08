package com.example.rouge.anem.Shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Produit;
import com.example.rouge.anem.Main.MainActivity;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by laureduchemin on 04/01/2017.
 */

public class ShopActivity extends AppCompatActivity {

    private ArrayList<Produit> listeProduit;
    private Api myModel;
    private ListView listView;
    private Callback callback;
    private ShopAdapter patientAdapter;
    private Boolean isSelecting = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_bis);
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
        listeProduit = new ArrayList<Produit>();
        listView = (ListView)findViewById(R.id.lvListeShop);
        patientAdapter = new ShopAdapter(getBaseContext(), listeProduit);
        listView.setAdapter(patientAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isSelecting){
                    Intent myIntent = new Intent(ShopActivity.this, TabShopActivity.class);
                    myIntent.putExtra("produit", (Parcelable) listeProduit.get(position));
                    startActivity(myIntent);
                }else{
                    setResult(Activity.RESULT_OK, new Intent().putExtra("produit", (Parcelable) listeProduit.get(position)));
                    finish();
                }
            }
        });



        /**************************************************************
        Start Button
         */

        Button addPoly = (Button) findViewById(R.id.AddPoly);
        addPoly.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_addpoly);
            }
        });

        Button addDecap = (Button) findViewById(R.id.AddDecap);
        addDecap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_decap);
            }
        });

        Button addStylo = (Button) findViewById(R.id.AddStylo);
        addStylo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_stylo);
            }
        });

        Button addTShirt = (Button) findViewById(R.id.AddTshirt);
        addTShirt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_tshirt);
            }
        });

        Button addecocup = (Button) findViewById(R.id.AddEcoCup);
        addecocup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_ecocup);
            }
        });

        Button addregle = (Button) findViewById(R.id.AddRegle);
        addregle.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_regle);
            }
        });

        Button addsticker = (Button) findViewById(R.id.AddSticker);
        addsticker.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_sticker);
            }
        });

        /******************************************************************************
        End Button
         */


    }

    public void refresh(){
        try {
            String[] mesparams = {Util.getProperty("url.shop", getBaseContext())};
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

    public void didReceivedData(){
        ArrayList<HashMap<String,Object>> result = this.callback.getResult();
        listeProduit = Produit.getProduitsFromWS(result);
        patientAdapter.setInitialListProduit(listeProduit);
        patientAdapter.notifyDataSetChanged();
    }

}
