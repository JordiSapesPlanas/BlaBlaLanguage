package com.example.jordi.blablalanguage.Models;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by VitorLui on 21/11/15.
 * This abstract class is responsable to implement all classes of the System
 * making obligatory fields necessary to all tables in the database
 */
public abstract class BlaBlaLanguageObject implements Serializable {

    private static final String NOME_BANCO = "blablalanguage.db";
    private static final int VERSAO = 1;


    private Integer id;
    private Date dateInclude;
    private Date dateUpdate;
    private boolean active;

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
