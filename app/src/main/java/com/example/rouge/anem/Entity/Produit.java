package com.example.rouge.anem.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by rouge on 23/11/2016.
 */

public class Produit {
    private int id;
    private String nom;
    private float prix;
    private float prixAdherent;
    private String urlImange;

    public Produit(int id, String nom, float prix, float prixAdherent, String urlImange) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAdherent = prixAdherent;
        this.urlImange = urlImange;
    }

    public static ArrayList<Produit> getProduitsFromWS(ArrayList<HashMap<String,Object>> ws){
        ArrayList<Produit> e = new ArrayList<Produit>();
        for (HashMap<String, Object> item: ws) {
            e.add(new Produit(Integer.parseInt((String)item.get("id")), (String)item.get("nom"), (float)item.get("prix"), (float)item.get("prixAdherent"), (String)item.get("urlImange")));
        }
        return e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrixAdherent() {
        return prixAdherent;
    }

    public void setPrixAdherent(float prixAdherent) {
        this.prixAdherent = prixAdherent;
    }

    public String getUrlImange() {
        return urlImange;
    }

    public void setUrlImange(String urlImange) {
        this.urlImange = urlImange;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", prixAdherent=" + prixAdherent +
                ", urlImange='" + urlImange + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit)) return false;

        Produit produit = (Produit) o;

        if (id != produit.id) return false;
        if (Float.compare(produit.prix, prix) != 0) return false;
        if (Float.compare(produit.prixAdherent, prixAdherent) != 0) return false;
        if (!nom.equals(produit.nom)) return false;
        return urlImange != null ? urlImange.equals(produit.urlImange) : produit.urlImange == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nom.hashCode();
        result = 31 * result + (prix != +0.0f ? Float.floatToIntBits(prix) : 0);
        result = 31 * result + (prixAdherent != +0.0f ? Float.floatToIntBits(prixAdherent) : 0);
        result = 31 * result + (urlImange != null ? urlImange.hashCode() : 0);
        return result;
    }
}
