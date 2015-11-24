package com.example.jordi.blablalanguage.Models;

/**
 * Created by vitorlui on 22/11/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Objects;

public class Utils {


    SharedPreferences mPrefs;
    SharedPreferences sharedPreferences;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public boolean removeKey(Activity a, String key) {

        try {

            sharedPreferences =  a.getSharedPreferences("bblPreferences", a.getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.commit();

            return true;

        }catch (Exception e) {
            return false;
        }
    }

    public boolean saveKey(Activity a, String key, String value) {

        try {

            sharedPreferences = a.getSharedPreferences("bblPreferences", a.getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();

            return true;

        }catch (Exception e) {
            return false;
        }
    }


    public boolean saveObject(Activity a, String identification, Object c) {

        try {

            //System.out.println("Entrando Save Object");

            // shared preferences get information
            mPrefs = a.getSharedPreferences("bblPreferences", a.getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();

            JSONObject json = new JSONObject();
            //String text = gson.toJson(c.toString());
            String text = ((User)c).FormatString();
            //String id = c.getClass().getName().concat("_").concat(identification.trim()); // ClassName_Identification
            String id = identification.trim(); // ClassName_Identification
            //String id = "USERT";
            //Log.d("USERT:", text);
            prefsEditor.putString(id, text);
            prefsEditor.commit();

            return true;

        }catch (Exception e) {
            Log.d("Excep: ",e.getMessage());
            //return null;
            return false;
        }
    }


    public String getObject(Activity a, String identification, Object c){
        try {
            Gson gson = new Gson();
            mPrefs = a.getSharedPreferences("bblPreferences", a.getApplicationContext().MODE_PRIVATE);
            //String id = "USERT";
            String id = identification.trim(); // ClassName_Identification
            String json = mPrefs.getString(id, "");
            //Log.d("getObj: ", json);
           // User u = gson.fromJson(json, User.class);
            //Log.d("Login:", u.getLogin());
            return json;

        }catch (Exception e){
            Log.d("Excep: ",e.getMessage());
            return null;
        }
    }


    public String getKey(Activity a, String identification){
        try {

            mPrefs = a.getSharedPreferences("bblPreferences", a.getApplicationContext().MODE_PRIVATE);

            String id = identification.trim(); // ClassName_Identification
            String json = mPrefs.getString(id, "");
            return json;

        }catch (Exception e){
            Log.d("Excep: ",e.getMessage());
            return null;
        }
    }
}
