package com.example.rouge.anem.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Competence;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Callback;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: document your custom view class.
 */
public class SearchCompetenceView extends LinearLayout implements SearchView.OnQueryTextListener{
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private Callback callback;
    private SearchView mSearchView;
    private ListView mListView;
    private TextView listComp;
    private Context context;
    private ArrayAdapter<Competence> arrayAdapter;
    private ArrayList<Competence> competences;
    private ArrayList<Competence> mCompetences = new ArrayList<>();
    private Api api;

    public SearchCompetenceView(Context context) {
        super(context);
        init(null, 0);
    }

    public SearchCompetenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setFocusable(true);
        setVisibility(VISIBLE);

        //setOnClickListener(listenerAdapter);
        setClickable(true);
        init(attrs, 0);
    }

    public SearchCompetenceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);

    }

    private void init(AttributeSet attrs, int defStyle) {

        inflate(context, R.layout.sample_search_competence_view, this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        listComp = (TextView) this.findViewById(R.id.textComp);
        this.setOrientation(VERTICAL);
        if(!this.isInEditMode()) {
            refreshComp();
        }
    }

    public void handleSearch(){
        mSearchView = (SearchView) findViewById(R.id.search);
        mListView = (ListView) findViewById(R.id.competences);
        arrayAdapter = new ArrayAdapter<Competence>(this.getContext(),
                android.R.layout.simple_list_item_1,
                competences);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Competence c = arrayAdapter.getItem(position);
                if (!mCompetences.contains(c)){
                    mCompetences.add(c);
                    didAddComp();
                }
            }
        });
        mListView.setTextFilterEnabled(true);
        setupSearchView();
    }

    public void didAddComp(){
        String s="";
        for (Competence c:mCompetences){
            s += c.getTitre() + "; ";
        }
        listComp.setText(s);
    }

    public void refreshComp(){
        this.callback = new Callback<Void>() {
            public Void call() {
                didReceivedData();
                return null;
            }
        };
        try {
            api = new Api(this.callback, getContext());
            String[] mesparams = {Util.getProperty("url.competences", getContext())};
            api.execute(mesparams);
        }catch(IOException i ){
            Log.d("Erreur de propriété", i.toString());
        }
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Rechercher");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public void didReceivedData(){
        ArrayList<HashMap<String,Object>> result = this.callback.getResult();
        competences = Competence.getCompetencesFromWS(result);
        handleSearch();
    }

    private void invalidateTextPaintAndMeasurements() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    public ArrayList<Competence> getmCompetences(){
        return mCompetences;
    }

    public boolean checkComp(){
        TextView comp = (TextView) findViewById(R.id.textComp);
        boolean error = false;
        if (mCompetences.size() == 0){
            comp.setError("Sélectionner au moins une compétence");
            error = true;
        }
        return error;
    }



}
