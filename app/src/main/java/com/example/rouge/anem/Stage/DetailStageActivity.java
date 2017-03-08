package com.example.rouge.anem.Stage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Stage;
import com.example.rouge.anem.R;

public class DetailStageActivity extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Stage stage = new Stage();

    public DetailStageActivity() {
    }

    public static DetailStageActivity newInstance() {
        DetailStageActivity fragment = new DetailStageActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        fill();
    }

    private void fill() {
        ((TextView) getView().findViewById(R.id.textEtat)).setText(stage.getEtat());
        ((TextView) getView().findViewById(R.id.intitule)).setText(stage.getIntitule());
        ((TextView) getView().findViewById(R.id.textDesc)).setText(stage.getDescription());
        ((TextView) getView().findViewById(R.id.textComp)).setText(stage.getCompetenceToString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_stage, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
