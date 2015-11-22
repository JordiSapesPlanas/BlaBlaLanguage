package com.example.jordi.blablalanguage.Models;

/**
 * Created by vitor on 21/11/15.
 */
public class UserDefinition extends BlaBlaLanguageObject  {

    private User user;
    private boolean eventNotification;
    private Language defaultLanguage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEventNotification() {
        return eventNotification;
    }

    public void setEventNotification(boolean eventNotification) {
        this.eventNotification = eventNotification;
    }

    public Language getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(Language defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }
}
