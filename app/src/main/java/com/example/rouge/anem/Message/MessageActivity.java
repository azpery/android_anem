package com.example.rouge.anem.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rouge.anem.Entity.AuthenticatedUser;
import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.Entity.Message;
import com.example.rouge.anem.Entreprise.NewEntrepriseActivity;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {
    private ArrayList<Message> listeMessage;
    private Api myModel;
    private ListView listView;
    private Callback callback;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        this.callback = new Callback<Void>() {
            public Void call() {
                didReceivedData();
                return null;
            }
        };
        myModel = new Api(this.callback, this);
        listeMessage = new ArrayList<Message>();
        listView = (ListView)findViewById(R.id.lvListe);
        messageAdapter = new MessageAdapter(getBaseContext(), listeMessage);
        listView.setAdapter(messageAdapter);
        Button button = (Button) findViewById(R.id.envoyer);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sent();
            }
        });
    }

    public void refresh(){
        try {
            String[] mesparams = {Util.getProperty("url.message", getBaseContext())};
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
        listeMessage = Message.getMessagesFromWS(result);
        messageAdapter.setInitialListMessage(listeMessage);
        messageAdapter.notifyDataSetChanged();
    }

    private void sent(){
        Callback callback= new Callback<Void>() {
            public Void call() {
                Toast toast = Toast.makeText(getBaseContext(), "Message envoyé", Toast.LENGTH_SHORT);
                toast.show();
                refresh();
                //finish();
                return null;
            }
        };
        try {
            Api api = new Api(callback, this);
            HashMap<String,String> param = new HashMap<>();
            String prenom = AuthenticatedUser.getInstance().getPrenom();
            String nom = AuthenticatedUser.getInstance().getNom();
            String text = ((EditText) findViewById(R.id.message)).getText().toString();
            Message m = new Message(text, new Date(),AuthenticatedUser.getInstance(), 0);
            param.put("allUsers","");
            param.put("message", prenom+" "+nom+" a dit "+text);
            param.put("idutilisateur",String.valueOf(AuthenticatedUser.getInstance().getId()));
            ((EditText) findViewById(R.id.message)).setText("");
            api.setMethod("POST");
            api.setParameters(param);
            String[] url = {Util.getProperty("url.notification", getBaseContext())};
            api.execute(url);
            api = new Api(callback, this);
            param.put("message", text);
            api.setMethod("POST");
            api.setParameters(param);
            String[] u = {Util.getProperty("url.send_message", getBaseContext())};
            api.execute(u);

        }catch(IOException i ){
            Log.d("Erreur de propriété", i.toString());
        }
    }

}
