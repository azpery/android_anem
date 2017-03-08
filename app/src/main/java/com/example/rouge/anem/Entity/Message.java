package com.example.rouge.anem.Entity;


import com.example.rouge.anem.Tools.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by r0b on 22/01/17.
 */

public class Message {
    private String message;
    private Date date;
    private Utilisateur utilisateur;
    private int id;

    public Message(String message, Date date, Utilisateur utilisateur, int id) {
        this.message = message;
        this.date = date;
        this.utilisateur = utilisateur;
        this.id = id;
    }

    public static ArrayList<Message> getMessagesFromWS(ArrayList<HashMap<String,Object>> ws){
        ArrayList<Message> e = new ArrayList<>();
        for (HashMap<String, Object> item: ws) {
            ArrayList<HashMap<String,Object>> u = new ArrayList<>();
            u.add((HashMap<String,Object>)item.get("idetudiant"));
            e.add(new Message((String)item.get("message"),Util.getDateFromString((String)item.get("date")), Utilisateur.getUtilisateursFromWS(u).get(0), Integer.parseInt((String)item.get("id"))));
        }
        return e;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
