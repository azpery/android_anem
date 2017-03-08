package com.example.rouge.anem.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rouge on 23/11/2016.
 */

public class Competence implements Serializable{
    private int id;
    private String titre;

    public Competence(int id, String titre) {
        this.id = id;
        this.titre = titre;
    }

    public static ArrayList<Competence> getCompetencesFromWS(ArrayList<HashMap<String,Object>> ws){
        ArrayList<Competence> e = new ArrayList<>();
        for (HashMap<String, Object> item: ws) {
            e.add(new Competence(Integer.parseInt(
                    (String)item.get("id")),
                    (String)item.get("titre")
            ));
        }
        return e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    @Override
    public String toString() {
        return titre;
    }
}
