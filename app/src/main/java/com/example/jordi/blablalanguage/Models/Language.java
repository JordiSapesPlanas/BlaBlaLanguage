package com.example.jordi.blablalanguage.Models;

import java.io.Serializable;

/**
 * Created by vitor on 21/11/15.
 */
public class Language extends BlaBlaLanguageObject  implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
