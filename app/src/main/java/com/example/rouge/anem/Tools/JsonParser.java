package com.example.rouge.anem.Tools;

import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by r0b on 04/01/17.
 */

public class JsonParser {
    public JsonParser(){

    }

    public ArrayList<HashMap<String,String>> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList<HashMap<String,String>> readMessagesArray(JsonReader reader) throws IOException {
        ArrayList<HashMap<String,String>> vreturn = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            vreturn.add(readMessage(reader));
        }
        reader.endArray();
        return vreturn;
    }

    public HashMap<String,String> readMessage(JsonReader reader) throws IOException {
        long id = -1;
        String text = null;
        List<Double> geo = null;
        //ArrayList<HashMap<String,String>> vreturn = new ArrayList<>();
        HashMap<String,String> val = new HashMap<>();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            try{
                val.put(name, reader.nextString());
            }
            catch(IllegalStateException i){
                try{
                val.put(name, String.valueOf(reader.nextBoolean()));}
                catch (IllegalStateException e){
                    try{
                        reader.beginArray();
                        reader.endArray();
                    }catch(IllegalStateException f){
                        reader.beginObject();
                        reader.endObject();
                    }
                }
            }
        }
        reader.endObject();
        return val;
    }

    public List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }
}
