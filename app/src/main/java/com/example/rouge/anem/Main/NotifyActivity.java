package com.example.rouge.anem.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rouge.anem.Entity.AuthenticatedUser;
import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.util.HashMap;

public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        Button button = (Button) findViewById(R.id.envoyer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sent();
            }
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (!b.isEmpty()) {
               // this.entreprise = (Entreprise) b.getSerializable("entreprise");
            }
        }
    }

    private void sent(){
        Callback callback= new Callback<Void>() {
            public Void call() {
                finish();
                return null;
            }
        };
        try {
            Api api = new Api(callback, this);
            HashMap<String,String> param = new HashMap<>();
            String prenom = AuthenticatedUser.getInstance().getPrenom();
            String nom = AuthenticatedUser.getInstance().getNom();
            param.put("allUsers","");
            param.put("message",prenom+" "+nom+" a dit "+((EditText) findViewById(R.id.message)).getText().toString());
            ((EditText) findViewById(R.id.message)).setText("");
            api.setMethod("POST");
            api.setParameters(param);
            String[] url = {Util.getProperty("url.notification", getBaseContext())};
            api.execute(url);
            Toast toast = Toast.makeText(getBaseContext(), "Message envoyé", Toast.LENGTH_SHORT);
            toast.show();
        }catch(IOException i ){
            Log.d("Erreur de propriété", i.toString());
        }
    }
}
