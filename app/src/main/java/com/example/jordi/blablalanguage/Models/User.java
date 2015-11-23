package com.example.jordi.blablalanguage.Models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Date;

/**
 * Created by vitor on 21/11/15.
 * Implement that using BlaBlaLanguageObject
 */
public class User extends BlaBlaLanguageObject {

    Utils utils = new Utils(); //internal uses
    private UserType userType;
    private String name;
    private String login;
    private String pass;
    private String facebookProfile;
    private String sex;
    private String birthday;
    private String photo;

    public User(){}

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

    public boolean Save(Activity a){

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
                                .split(",");


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

            User n = (User) parseJson(utils.getObject(a, login, this));

            return  n;
        }
        catch (Exception e){
            throw  e;
            //return  false;
        }
    }

}
