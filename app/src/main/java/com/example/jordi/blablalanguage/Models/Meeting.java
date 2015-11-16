package com.example.jordi.blablalanguage.Models;

import java.util.Date;

/**
 * Created by richengjin on 16/11/15.
 */
public class Meeting {
    private String name;
    private String establishment;
    private Date dateMeeting;
    private String imageUrl;

    public Meeting(){

    }

    public Meeting(String name, String establishment, Date dateMeeting, String imageUrl){
        this.name = name;
        this.establishment = establishment;
        this.dateMeeting = dateMeeting;
        this.imageUrl = imageUrl;
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

    public void setDateMeeting(Date dateMeeting) {
        this.dateMeeting = dateMeeting;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
