package com.example.rouge.anem.Entreprise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Entreprise;
import com.example.rouge.anem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r0b on 04/01/17.
 */

public class EntrepriseAdapter extends BaseAdapter {
    private List<Entreprise> listEntreprise;
    private List<Entreprise> initialListEntreprise;
    private LayoutInflater layoutInflater;

    public EntrepriseAdapter(Context context, List<Entreprise> vListEntreprise) {
        layoutInflater = LayoutInflater.from(context);
        setInitialListEntreprise(vListEntreprise);
    }

    public void rechercher(String search){
        this.listEntreprise = new ArrayList<>() ;
        if(!search.isEmpty()) {
            for (Entreprise e : initialListEntreprise) {
                if (e.getNom() != null && e.getNom().toUpperCase().contains(search.toUpperCase()) || e.getAdresse() != null && e.getAdresse().toUpperCase().contains(search.toUpperCase())) {
                    listEntreprise.add(e);
                }
            }
        }else{
                listEntreprise = initialListEntreprise;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return getListEntreprise().size();
    }

    @Override
    public Object getItem(int position) {
        return getListEntreprise().get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.liste_entreprise, null);
            holder.textViewNom = (TextView) convertView.findViewById(R.id.vueNom);
            holder.textViewAdresse = (TextView) convertView.findViewById(R.id.vueAdresse);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewNom.setText(getListEntreprise().get(position).getNom());
        holder.textViewAdresse.setText(getListEntreprise().get(position).getAdresse());
        return convertView;
    }

    public List<Entreprise> getListEntreprise() {
        return listEntreprise;
    }

    public void setListEntreprise(List<Entreprise> listEntreprise) {
        this.listEntreprise = new ArrayList<>();
        this.listEntreprise.addAll(listEntreprise);
        notifyDataSetChanged();
    }

    public List<Entreprise> getInitialListEntreprise() {
        return initialListEntreprise;
    }

    public void setInitialListEntreprise(List<Entreprise> initialListEntreprise) {
        this.initialListEntreprise = initialListEntreprise;
        setListEntreprise(initialListEntreprise);
    }

    private class ViewHolder {
        TextView textViewNom;
        TextView textViewAdresse;
    }
}
