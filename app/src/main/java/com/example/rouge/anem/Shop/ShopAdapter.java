package com.example.rouge.anem.Shop;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Produit;
import com.example.rouge.anem.R;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends BaseAdapter{

    private List<Produit> listProduit;
    private List<Produit> initialListProduit;
    private LayoutInflater layoutInflater;

    public ShopAdapter(Context context, List<Produit> vListProduit) {
        layoutInflater = LayoutInflater.from(context);
        setInitialListProduit(vListProduit);
    }

    @Override
    public int getCount() {
        return getListProduit().size();
    }

    @Override
    public Object getItem(int i) {
        return getListProduit().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.liste_produit, null);
            holder.textViewNomProduit = (TextView) view.findViewById(R.id.vueNomProduit);
            holder.textViewPrixProduit = (TextView) view.findViewById(R.id.vuePrixProduit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textViewNomProduit.setText(getListProduit().get(i).getNom());
        holder.textViewPrixProduit.setText((int) getListProduit().get(i).getPrix());
        return view;    }

    public List<Produit> getListProduit() {
        return listProduit;
    }


    public void setListProduit(List<Produit> listEntreprise) {
        this.listProduit = new ArrayList<>();
        this.listProduit.addAll(listEntreprise);
        notifyDataSetChanged();
    }

    public List<Produit> getInitialListProduit() {
        return initialListProduit;
    }


    public void setInitialListProduit(List<Produit> initialListEntreprise) {
        this.initialListProduit = initialListEntreprise;
        setListProduit(initialListEntreprise);
    }

    private class ViewHolder {
        TextView textViewNomProduit;
        TextView textViewPrixProduit;
    }
}
