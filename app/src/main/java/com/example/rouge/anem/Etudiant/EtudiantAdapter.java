package com.example.rouge.anem.Etudiant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Utilisateur;
import com.example.rouge.anem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rouge on 04/01/2017.
 */

public class EtudiantAdapter extends BaseAdapter {
    private List<Utilisateur> listEtudiant;
    private LayoutInflater layoutInflater;
    private List<Utilisateur> initialListEtudiant;


    public EtudiantAdapter(Context context, List<Utilisateur> vListEtudiant) {
        layoutInflater = LayoutInflater.from(context);
        listEtudiant = vListEtudiant;
        setInitialListEtudiant(vListEtudiant);
    }
    public void rechercher(String search){
        this.listEtudiant = new ArrayList<>() ;
        if(!search.isEmpty()) {
            for (Utilisateur u : initialListEtudiant) {
                if (u.getNom() != null && u.getNom().toUpperCase().contains(search.toUpperCase()) || u.getPrenom() != null && u.getPrenom().toUpperCase().contains(search.toUpperCase()) || u.getEmail() != null && u.getEmail().toUpperCase().contains(search.toUpperCase())) {
                    listEtudiant.add(u);
                }
            }
        }else{
            listEtudiant = initialListEtudiant;
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return listEtudiant.size();
    }

    @Override
    public Object getItem(int position) {
        return listEtudiant.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EtudiantAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new EtudiantAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.liste_etudiant, null);
            holder.textViewNom = (TextView) convertView.findViewById(R.id.vueNom);
            convertView.setTag(holder);
        } else {
            holder = (EtudiantAdapter.ViewHolder) convertView.getTag();
        }

        holder.textViewNom.setText(listEtudiant.get(position).getNom());
        return convertView;
    }
    public void setListEtudiant(List<Utilisateur> listEtudiant) {
        this.listEtudiant = listEtudiant;
    }

    public List<Utilisateur> getInitialListEtudiant() {
        return initialListEtudiant;
    }

    public void setInitialListEtudiant(List<Utilisateur> initialListEtudiant) {
        this.initialListEtudiant = initialListEtudiant;
        setListEtudiant(initialListEtudiant);
    }

    private class ViewHolder {
        TextView textViewNom;
    }
}
