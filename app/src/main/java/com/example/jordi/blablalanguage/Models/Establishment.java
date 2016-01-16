package com.example.jordi.blablalanguage.Models;

import java.io.Serializable;

/**
 * Created by vitor on 21/11/15.
 * Establishment
 */
public class Establishment extends BlaBlaLanguageObject  implements Serializable {


    private String name;
    private User owner;
    private String address;
    private Integer longitude;
    private Integer latitude;
    private Integer placesAvailable;
    private String  telephone;
    private String date;


    public Establishment(){}

    public Establishment(Integer id, String name, User owner, String address,  Integer longitude, Integer latitude, String telephone){
        this.setId(id);
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.telephone = telephone;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getPlacesAvailable() {
        return placesAvailable;
    }

    public void setPlacesAvailable(Integer placesAvailable) {
        this.placesAvailable = placesAvailable;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDate (String date){
        this.date= date;
    }
    public String getDate(){
        return date;
    }
}
