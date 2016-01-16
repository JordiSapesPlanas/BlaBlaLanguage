package com.example.jordi.blablalanguage.Models;

import java.io.Serializable;

/**
 * Created by vitor on 21/11/15.
 * Language of users
 */
public class UserLanguage extends BlaBlaLanguageObject  implements Serializable {
    private User user;
    private IdiomLevel idomLevel;
    private Language language;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IdiomLevel getIdomLevel() {
        return idomLevel;
    }

    public void setIdomLevel(IdiomLevel idomLevel) {
        this.idomLevel = idomLevel;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
