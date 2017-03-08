package com.example.rouge.anem.Stage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Stage;
import com.example.rouge.anem.R;

import java.util.List;

/**
 * Created by rouge on 04/01/2017.
 */

public class StageAdapter extends BaseAdapter {
    private List<Stage> listStage;
    private LayoutInflater layoutInflater;

    public StageAdapter(Context context, List<Stage> vListStage) {
        layoutInflater = LayoutInflater.from(context);
        listStage = vListStage;
    }
    @Override
    public int getCount() {
        return listStage.size();
    }

    @Override
    public Object getItem(int position) {
        return listStage.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StageAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new StageAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.liste_stage, null);
            holder.textViewNom = (TextView) convertView.findViewById(R.id.vueNom);
            holder.textViewCompetences = (TextView) convertView.findViewById(R.id.vueCompetences);
            convertView.setTag(holder);
        } else {
            holder = (StageAdapter.ViewHolder) convertView.getTag();
        }

        holder.textViewNom.setText(listStage.get(position).getIntitule());
        holder.textViewCompetences.setText(listStage.get(position).getCompetenceToString());
        return convertView;

    }
    public void setListStage(List<Stage> listStage) {
        this.listStage = listStage;
    }

    private class ViewHolder {
        TextView textViewNom;
        TextView textViewCompetences;
    }
}
