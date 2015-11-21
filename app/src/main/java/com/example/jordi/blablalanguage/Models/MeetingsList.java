package com.example.jordi.blablalanguage.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscar on 21/11/15.
 */
public class MeetingsList {

    private static List<Meeting> list1=new ArrayList<Meeting>();


    public MeetingsList(){

    }

    public List<Meeting> getList(){
        return list1;
    }
}
