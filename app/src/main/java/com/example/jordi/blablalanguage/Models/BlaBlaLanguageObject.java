package com.example.jordi.blablalanguage.Models;

import android.app.Activity;

import java.util.Date;

/**
 * Created by VitorLui on 21/11/15.
 * This abstract class is responsable to implement all classes of the System
 * making obligatory fields necessary to all tables in the database
 */
public abstract class BlaBlaLanguageObject {

        private Integer id;
    private Date dateInclude;
    private Date dateUpdate;
    private boolean active;

    public BlaBlaLanguageObject(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateInclude() {
        return dateInclude;
    }

    public void setDateInclude(Date dateInclude) {
        this.dateInclude = dateInclude;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
