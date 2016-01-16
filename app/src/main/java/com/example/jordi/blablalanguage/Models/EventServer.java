package com.example.jordi.blablalanguage.Models;

/**
 * Created by jordi on 16/01/2016.
 */
public class EventServer  {

    private int establishmentId;
    private int languageId;
    private String eventName;
    private String dateEvent;
    private String description;

    public EventServer(int establishmentId, int languageId, String eventName, String dateEvent,String description){

        this.establishmentId = establishmentId;
        this.languageId = languageId;
        this.eventName = eventName;
        this.dateEvent = dateEvent;
        this.description = description;
    }

    public int getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(int establishmentId) {
        this.establishmentId = establishmentId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
