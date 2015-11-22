package com.example.jordi.blablalanguage.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by richengjin on 16/11/15.
 */
public class Meeting {
    private String name;
    private String establishment;
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

    public Meeting(String name, String establishment, Date dateMeeting, String imageUrl){
        this.name = name;
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
}
