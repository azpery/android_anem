package com.example.rouge.anem.Entity;

import android.content.Context;

import com.example.rouge.anem.Tools.Api;
import com.example.rouge.anem.Tools.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rouge on 23/11/2016.
 */

public class Stage implements Serializable{
    private int id;
    private String intitule;
    private String description;
    private String etat;
    private Entreprise entreprise;
    private Utilisateur etudiant;
    private ArrayList<Competence> competences;

    public Stage(){}

    public Stage(int id, String intitule, String description, String etat, Entreprise entreprise, Utilisateur etudiant, ArrayList<Competence> competences) {
        this.id = id;
        this.intitule = intitule;
        this.description = description;
        this.etat = etat;
        this.entreprise = entreprise;
        this.etudiant = etudiant;
        this.competences = competences;
    }

    public static ArrayList<Stage> getStagesFromWS(ArrayList<HashMap<String,Object>> ws){
        ArrayList<Stage> e = new ArrayList<Stage>();
        for (HashMap<String, Object> item: ws) {
            ArrayList<HashMap<String,Object>> ent = new ArrayList<>();
            ent.add((HashMap<String,Object>)item.get("identreprise"));
            ArrayList<HashMap<String,Object>> u = new ArrayList<>();
            u.add((HashMap<String,Object>)item.get("idetudiant"));
            ArrayList<HashMap<String,Object>> c = (ArrayList<HashMap<String,Object>>) item.get("competences");
            e.add(new Stage(Integer.parseInt((String)item.get("id")),
                    (String)item.get("intitule"),
                    (String)item.get("description"),
                    (String)item.get("etat"),
                    Entreprise.getEntreprisesFromWS(ent).get(0),
                    Utilisateur.getUtilisateursFromWS(u).get(0),
                    Competence.getCompetencesFromWS(c)));
        }
        return e;
    }

    public HashMap<String, String> getStageReadyForWs(){
        HashMap<String, String> vretour = new HashMap<>();
        vretour.put("id", String.valueOf(id));
        vretour.put("intitule", intitule);
        vretour.put("description", description);
        vretour.put("etat", etat);
        vretour.put("entreprise", String.valueOf(entreprise.getId()));
        vretour.put("user", String.valueOf(etudiant.getId()));
        vretour.put("comp", getCompetenceToStringWs());
        return vretour;
    }

    public void save(Api api, Context context) throws IOException {
        HashMap<String,String> param = getStageReadyForWs();
        api.setMethod("POST");
        api.setParameters(param);
        String[] url = {Util.getProperty("url.update_stage", context)};
        api.execute(url);
    }

    public String getCompetenceToStringWs(){
        String vretour = "";
        for (Competence e: this.competences){
            vretour += "&competences[]="+String.valueOf(e.getId());
        }
        return vretour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Utilisateur getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Utilisateur etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", etat='" + etat + '\'' +
                ", entreprise=" + entreprise +
                ", etudiant=" + etudiant +
                '}';
    }

    public ArrayList<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(ArrayList<Competence> competences) {
        this.competences = competences;
    }

    public String getCompetenceToString(){
        String vretour = "";
        for (Competence e: this.competences){
            vretour += e.getTitre();
            if (e != this.competences.get(competences.size() - 1)){
                vretour += "; ";
            }
        }
        return vretour;
    }
}
