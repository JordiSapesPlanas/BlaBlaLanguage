package com.example.jordi.blablalanguage.Models;

/**
 * Created by vitorlui on 22/11/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.json.JSONObject;

public class DBUtils extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blablalanguage.db";
    private static final int VERSION = 3;

    private final String CT_Languages = "CREATE TABLE Languages(" +
            "   Id INTEGER PRIMARY KEY," +
            "   Name TEXT," +
            "   DateInclude DATETIME," +
            "   DateUpdate DATETIME" +
            ")";

    private final String CT_Users = "CREATE TABLE Users(" +
            "   Idm INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   Id INTEGER DEFAULT 0," +
            "   Type INTEGER," +
            "   Name TEXT," +
            "   Login TEXT," +
            "   Pass TEXT," +
            "   FacebookProfile TEXT," +
            "   Sex TEXT," +
            "   Birthday DATETIME," +
            "   Photo TEXT," +
            "   DateInclude DATETIME," +
            "   DateUpdate DATETIME," +
            "   Active bool INTEGER DEFAULT 1" +
            ")";

    private final String CT_Events = "CREATE TABLE Events(" +
            "   Idm INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   Id INTEGER DEFAULT 0," +
            "   Name TEXT," +
            "   Language TEXT," +
            "   LanguageId Integer default 0," +
            "   Establishment TEXT," +
            "   EstablishmentId Integer default 0," +
            "   DateMeeting DATETIME," +
            "   Photo TEXT," +
            "   DateInclude DATETIME," +
            "   DateUpdate DATETIME," +
            "   Active bool INTEGER DEFAULT 1" +
            ")";


    /*
    private final String CT_Establishments = "CREATE TABLE Establishments(" +
                                        "   Idm INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "   Id INTEGER DEFAULT 0," +
                                        "   Name TEXT," +
                                        "   Establishment INTEGER," +
                                        "   EventDate DATETIME," +
                                        "   Photo TEXT," +
                                        "   DateInclude DATETIME," +
                                        "   DateUpdate DATETIME," +
                                        "   Active bool INTEGER DEFAULT 1" +
                                        ")";
    */


    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //db.execSQL(CT_Languages); //table Languages
            db.execSQL(CT_Users); //table Users
            db.execSQL(CT_Events); //events
            //db.execSQL(CT_Establishments); //table establishments
        }catch (Exception ex){
            throw  ex;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // db.execSQL("DROP TABLE IF EXISTS Languages");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Events");
        // db.execSQL("DROP TABLE IF EXISTS Establishments");

        onCreate(db);
    }
}
