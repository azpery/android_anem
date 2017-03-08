package com.example.rouge.anem.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by r0b on 04/01/17.
 */

public class Api extends AsyncTask<String, String, Boolean> {
    private Callback<Void> callback;
    private JsonParser jsonParser;
    private HashMap<String,String> parameters;
    private String method;
    ArrayList<HashMap<String,Object>> result;
    ProgressDialog progress;

    public Api(Callback<Void> callback, Context context){
        this.setMethod("GET");
        this.callback = callback;
        this.jsonParser = new JsonParser();
        this.progress = new ProgressDialog(context);
    }

    public Api(Callback<Void> callback, HashMap<String,String> parameters, String method, Context context){

        this.callback = callback;
        this.setParameters(parameters);
        this.setMethod(method);
        this.jsonParser = new JsonParser();
        this.progress = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setMessage("Chargement...");
        progress.show();
    }
    @Override
    protected Boolean doInBackground(String... urls) {
        URL url;
        StringBuilder bodyBuilder = new StringBuilder();
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + urls[0]);
        }

        String body = "";

        if (this.getMethod().equals("POST")) {

            for(Map.Entry<String, String> entry : this.getParameters().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                bodyBuilder.append(key).append('=').append(value).append('&');
            }
            body = bodyBuilder.toString();

        }


        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setRequestMethod(this.getMethod());
            byte[] bytes = new byte[0];
            if (this.getMethod().equals("POST")) {
                Log.v("Sending", "Posting '" + body + "' to " + url);
                conn.setDoOutput(true);
                bytes = body.getBytes();
                conn.setFixedLengthStreamingMode(bytes.length);
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                // post the request
                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();
            }

            // handle the response
            int HttpResult = conn.getResponseCode();
            if (HttpResult == 200) {
                String pouet = Util.convertStreamToString(conn.getInputStream());
                Log.v("Reponse serveur",pouet);
                InputStream stream = new ByteArrayInputStream(pouet.getBytes(StandardCharsets.UTF_8));
                this.result = jsonParser.readJsonStream(stream);
                return true;
            }else {
                String pouet = Util.convertStreamToString(conn.getErrorStream());
                Log.v("Reponse serveur",pouet);
                throw new IOException("Request failed with error code " + HttpResult);
            }
        }
        catch (MalformedURLException e) {
            Log.e("Erreur url malformée", e.toString());
        } catch (java.net.SocketTimeoutException e) {
            Log.e("Erreur erreur timeout", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Erreur io", e.toString());

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        progress.hide();
        if (result){
            this.callback.setResult(this.result);
            this.callback.call();
        }
    }

    @Override
    protected void onProgressUpdate(String... param) {

    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
