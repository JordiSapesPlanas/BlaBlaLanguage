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
    private static final int VERSION = 1;

    private final String CT_USERS = "CREATE TABLE Users(" +
                                    "   Id INTEGER PRIMARY KEY AUTOINCREMENT," +
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

    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CT_USERS); //create table for users table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS USERS");
        onCreate(db);
    }
}
