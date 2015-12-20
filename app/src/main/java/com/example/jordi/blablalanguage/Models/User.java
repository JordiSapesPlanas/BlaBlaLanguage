package com.example.jordi.blablalanguage.Models;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.database.Cursor;

import java.util.Date;

/**
 * Created by vitor on 21/11/15.
 * Implement that using BlaBlaLanguageObject
 */
public class User extends BlaBlaLanguageObject {

    Utils utils = new Utils(); //internal uses
    DBUtils dbutils; //internal uses
    private UserType userType;
    private String name;
    private String login;
    private String pass;
    private String facebookProfile;
    private String sex;
    private String birthday;
    private String photo;
    //fake
    private Context context;

    public User(String name, String login, String password, String facebookProfile, String sex, String birthday){
        setDateInclude(new Date());
        setActive(true);
        //this.setUserType(ut);
        this.setName(name);
        this.setLogin(login);
        this.setPass(password);
        this.setFacebookProfile(facebookProfile);
        this.setSex(sex);
        this.setBirthday(birthday);
        this.setPhoto("");
    }

    public User(){

    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFacebookProfile() {
        return facebookProfile;
    }

    public void setFacebookProfile(String facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Find user at database
     * @param login user login
     * @param pass user pass
     * @return The user founded or null
     */
    public User findUser(String login, String pass){
        //Todo: Implement logic to search in the database
        return  null;
    }

    public String FormatString(){
        return "{"
                +"login:".concat(this.login.trim()).concat(";")
                +"pass:".concat(this.pass.trim()).concat(";")
                +"name:".concat(this.name.trim()).concat(";")
                +"email:".concat(this.login.trim()).concat(";")
                +"facebookProfile:".concat(this.facebookProfile.trim()).concat(";")
                +"photo:".concat(this.login.trim()).concat(";")
                +"sex:".concat(this.sex.trim()).concat(";")
                +"birthday:".concat(this.birthday.trim()).concat("}");
    }

    public String InsertCommand(){
         String value = "INSERT INTO USERS(Login,Pass,Name,FacebookProfile,Photo,Sex,Birthday) " +
                "VALUES " +
                "("
                    +"\""+this.login.trim().concat("\",")
                    +"\""+this.pass.trim().concat("\",")
                    +"\""+this.name.trim().concat("\",")
                    +"\""+this.facebookProfile.trim().concat("\",")
                    +"\""+this.photo.trim().concat("\",")
                    +"\""+this.sex.trim().concat("\",")
                    +"\""+this.birthday.trim().concat("\"")
                +")";

        return value;
    }

    public String UpdateCommand(){

        String value = "UPDATE USERS "
                +" set "
                +" Name = \""+this.name.trim()+"\""
                +" ,FacebookProfile = \""+this.facebookProfile.trim()+"\""
                +" ,Photo = \""+this.photo.trim()+"\""
                +" ,Sex = \""+this.sex.trim()+"\""
                +" ,Birthday = \""+this.birthday.trim()+"\""
                +" where LOGIN = \""+this.login.trim()+"\"";

        return value;
    }


    public boolean Save(Activity a){

        try {

            dbutils = new DBUtils(a.getApplicationContext());
            SQLiteDatabase db = dbutils.getWritableDatabase();

            String sql ="";

            if(getUserById(a,this.login)!=null){
                sql =  UpdateCommand();
            }else {
               sql =  InsertCommand();
            }

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


    public boolean Save2(Activity a){

        try {
            utils.saveObject(a, this.login, this);//Saving in preferences
            return  true;
        }
        catch (Exception e){
            throw  e;
            //return  false;
        }
    }

    public User parseJson(String json){

        User u = new User();

        String[] jsStrings =  json
                                .replace("{", "").replace("}", "")
                                .replace("\"", "")
                                .replace("\\", "")
                                .split(";");


        for (String s: jsStrings){
            String[] tmp = s.split(":");

            if(tmp.length>1) {

                if (tmp[1].length() > 0 && tmp[0].contains("name")) u.setName(tmp[1]);
                if (tmp[1].length() > 0 && tmp[0].contains("login")) u.setLogin(tmp[1].replace("\\",""));
                if (tmp[1].length() > 0 && tmp[0].contains("pass")) u.setPass(tmp[1].replace("\\",""));
                if (tmp[1].length() > 0 && tmp[0].contains("facebookProfile")) u.setFacebookProfile(tmp[1]);
                if (tmp[1].length() > 0 && tmp[0].contains("sex")) u.setSex(tmp[1]);
                if (tmp[1].length() > 0 && tmp[0].contains("birthday")) u.setBirthday(tmp[1]);
                if (tmp[1].length() > 0 && tmp[0].contains("photo")) u.setPhoto(tmp[1]);
            }
          }

        return u;
    }

    public User getUserById(Activity a, String login){

        try {

            dbutils = new DBUtils(a.getApplicationContext());
            SQLiteDatabase db = dbutils.getReadableDatabase();

            String[] campos =  {"Id","Name","Login","Pass","Type","FacebookProfile","Sex","Birthday","Photo"};
            String where = " Login = " + "\""+login.trim()+"\"";

            if(db!=null){

               Cursor cursor =  db.query("USERS",campos,where, null, null, null, null, null);
                if(cursor!=null && cursor.moveToFirst()){

                    User u = new User();

                    int Id = cursor.getInt(cursor.getColumnIndex("Id"));
                    String Login = cursor.getString(cursor.getColumnIndex("Login"));
                    String Name = cursor.getString(cursor.getColumnIndex("Name"));
                    String Pass = cursor.getString(cursor.getColumnIndex("Pass"));
                    int Type = cursor.getInt(cursor.getColumnIndex("Type"));
                    String FacebookProfile = cursor.getString(cursor.getColumnIndex("FacebookProfile"));
                    String Sex = cursor.getString(cursor.getColumnIndex("Sex"));
                    String Birthday = cursor.getString(cursor.getColumnIndex("Birthday"));
                    String Photo = cursor.getString(cursor.getColumnIndex("Photo"));

                    u.setId(Id);
                    u.setLogin(Login);
                    u.setName(Name);
                    u.setPass(Pass);
                    u.setFacebookProfile(FacebookProfile);
                    u.setSex(Sex);
                    u.setPhoto(Photo);

                    db.close();

                    return u;
                }
                else{
                    db.close();
                    return null;
                }

            }else{
                return  null;
            }

        }
        catch (Exception e){
            throw  e;
            //return  false;
        }
    }

    public boolean deleteByLogin(Activity a, String login){

        try {

            dbutils = new DBUtils(a.getApplicationContext());
            SQLiteDatabase db = dbutils.getWritableDatabase();
            String sql = "delete from USERS where login=\""+login+"\"";

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

    public User getUserById2(Activity a, String login){

        try {

            User n = (User) parseJson(utils.getObject(a, login, this));

            return  n;
        }
        catch (Exception e){
            throw  e;
            //return  false;
        }
    }

}
