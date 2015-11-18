package com.example.jordi.blablalanguage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jordi.blablalanguage.Adapters.meetingAdapter;
import com.example.jordi.blablalanguage.Models.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        /*Meetings para prueba*/
        Meeting meeting1 = new Meeting();
        meeting1.setName("Let's talk English");
        meeting1.setEstablishment("NyamNyam");
        meeting1.setImageUrl("english");

        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting1);

        ListView listView = (ListView)findViewById(R.id.listView_my_meetings);
        meetingAdapter myAdapter = new meetingAdapter(this,meetings,R.layout.customer_meeting_list);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(new Intent(getApplicationContext(), MeatingDetailActivity.class), Bundle.EMPTY);
                }
            }
        });

    }

}
