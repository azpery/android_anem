package com.example.rouge.anem.Main;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.Entity.Stage;
import com.example.rouge.anem.Entity.Utilisateur;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Stage.StageAdapter;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rouge on 04/01/2017.
 */

public class WelcomePageActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.content_main, container, false);
    }
}
