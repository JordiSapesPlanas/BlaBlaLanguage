package com.example.jordi.blablalanguage.Models;

import java.io.Serializable;

/**
 * Created by vitor on 21/11/15.
 */
public class IdiomLevel implements Serializable {
    private String name;
    private Integer rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
