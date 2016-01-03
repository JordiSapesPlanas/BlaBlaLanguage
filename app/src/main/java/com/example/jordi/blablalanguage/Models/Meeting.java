package com.example.jordi.blablalanguage.Models;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by vitor on 21/12/15.
 */
public class Meeting extends BlaBlaLanguageObject {
    DBUtils dbutils; //internal uses
    private Context context;
    private String name;
    private String establishment;
    private String language;
    private Date dateMeeting;
    private String imageUrl;
    private MeetingsList meet = new  MeetingsList();


    public Meeting(){
        String s= "12/12/2015 23:34:23";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            dateMeeting =formatter.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Meeting(Context context){
        this.setContext(context);
    }

    public Meeting(String name, String establishment, Date dateMeeting, String imageUrl){
        this.setName(name);
        this.establishment = establishment;
        this.dateMeeting = dateMeeting;
        this.imageUrl = imageUrl;
    }

    public void load(){
        this.meet.getList().add(this);
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateMeeting() {

        return dateMeeting;
    }

    public String getFechaString(){
        SimpleDateFormat formatter = new SimpleDateFormat("E dd/MM/yyyy");
        String fecha = formatter.format(this.dateMeeting);
        return fecha;
    }

    public String getTimeString(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String time= formatter.format(this.dateMeeting);
        return time;
    }
    public void setDateMeeting(Date dateMeeting) {
        this.dateMeeting = dateMeeting;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String InsertCommand(){

                 /*
            "CREATE TABLE Events(" +
                    "   Idm INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "   Id INTEGER DEFAULT 0," +
                    "   Name TEXT," +
                    "   Language TEXT," +
                    "   LanguageId Integer default 0," +
                    "   Establishment TEXT," +
                    "   EstablishmentId Integer default 0," +
                    "   EventDate DATETIME," +
                    "   Photo TEXT," +
                    "   DateInclude DATETIME," +
                    "   DateUpdate DATETIME," +
                    "   Active bool INTEGER DEFAULT 1" +
                    ")";
            */


        String value = "INSERT INTO Events(Name,Language,Establishment,Photo) " +
                "VALUES " +
                "("
                +"\""+this.getName().trim().concat("\",")
                +"\""+this.language.trim().concat("\",")
                +"\""+this.establishment.trim().concat("\",")
                +"\""+this.imageUrl.trim().concat("\"")
                +")";

        return value;
    }

    public boolean Save(Activity a){

        try {

            if (getContext() == null) {
                dbutils = new DBUtils(a.getApplicationContext());
            } else {
                dbutils = new DBUtils(context);
            }

            SQLiteDatabase db = dbutils.getWritableDatabase();

            String sql ="";

            //TODO(vitor): Verify if exist another one equal
            sql =  InsertCommand();

            if(db!=null){
                db.execSQL(sql);

                db.close();

                return  true;
            }else{
                return  false;
            }

        }
        catch (Exception e){
            throw  e;
            //return  false;
        }
    }


    public boolean DeleteAll(Activity a){

        try {

            if (getContext() == null) {
                dbutils = new DBUtils(a.getApplicationContext());
            } else {
                dbutils = new DBUtils(context);
            }

            SQLiteDatabase db = dbutils.getWritableDatabase();

            String sql ="DELETE FROM Events";

            if(db!=null){
                db.execSQL(sql);

                db.close();

                return  true;
            }else{
                return  false;
            }

        }
        catch (Exception e){
            throw  e;
            //return  false;
        }
    }


    public List<Meeting> getAll(Activity a){

        try {

            if (getContext() == null) {
                dbutils = new DBUtils(a.getApplicationContext());
            } else {
                dbutils = new DBUtils(context);
            }
            SQLiteDatabase db = dbutils.getReadableDatabase();

            String[] campos =  {"Idm","Id","Name","Language","LanguageId"
                    ,"Establishment","EstablishmentId","DateMeeting","Photo"};


            if(db!=null){
                List<Meeting> lst = new ArrayList<Meeting>();

                Cursor cursor =  db.query("Events",campos,null, null, null, null, null, null);
                while (cursor!=null && cursor.moveToNext()){

                    Meeting m = new Meeting();

                    int Id = cursor.getInt(cursor.getColumnIndex("Id"));
                    String Name = cursor.getString(cursor.getColumnIndex("Name"));
                    String Establishment = cursor.getString(cursor.getColumnIndex("Establishment"));
                    String Language = cursor.getString(cursor.getColumnIndex("Language"));
                    // String dateMeeting = cursor.getString(cursor.getColumnIndex("dateMeeting"));
                    String Photo = cursor.getString(cursor.getColumnIndex("Photo"));

                    m.setId(Id);
                    m.setName(Name);
                    m.setEstablishment(Establishment);
                    m.setLanguage(Language);
                    m.setDateMeeting(new Date()); //TODO(vitor): Try to use a real date
                    m.setImageUrl(Photo);

                    lst.add(m);
                }

                db.close();
                return lst;

            }else{
                return  null;
            }

        }
        catch (Exception e){
            throw  e;
            //return  false;
        }
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
