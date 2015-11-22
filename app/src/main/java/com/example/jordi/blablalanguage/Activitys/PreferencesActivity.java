package com.example.jordi.blablalanguage.Activitys;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.Switch;
import android.widget.Toast;

import com.example.jordi.blablalanguage.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PreferencesActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener , View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private Switch swNotificationNewMeeting;
    private Switch swNotificationNewUser;
    private Switch swNotificationTime;
    private Button btnIdioms;
    private CheckBox cbNotifications;

    private ArrayList<String> selectedIdiomsList;
    boolean idiomsSelectedBooleanArray[] ;//= {false, false, false, false, false, false};
    private String[] idiomsArray;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.swNotificationNewMeeting = (Switch)findViewById(R.id.sw_new_meeting);
        this.swNotificationTime = (Switch)findViewById(R.id.sw_meeting_time);
        this.swNotificationNewUser = (Switch)findViewById(R.id.sw_new_user);
        this.btnIdioms = (Button) findViewById(R.id.btn_select_idioms);
        this.cbNotifications = (CheckBox)findViewById(R.id.cb_notifications);
        this.swNotificationNewMeeting.setOnCheckedChangeListener(this);
        this.swNotificationTime.setOnCheckedChangeListener(this);
        this.swNotificationNewUser.setOnCheckedChangeListener(this);
        this.btnIdioms.setOnClickListener(this);
        this.cbNotifications.setOnClickListener(this);

        this.sharedPreferences = getPreferences(getApplicationContext().MODE_PRIVATE);
        this.idiomsArray = getResources().getStringArray(R.array.Idioms);
        this.idiomsSelectedBooleanArray = new boolean[idiomsArray.length];
        this.selectedIdiomsList = new ArrayList<>();
        String idioms = sharedPreferences.getString("Idioms", "DEFAULT");
        if(idioms.equals("DEFAULT")){
            for(int i = 0; i < idiomsArray.length; i++){
                idiomsSelectedBooleanArray[i]=false;
            }
        }else{
            Log.e("-1-", "recovery info from shared preferences");
            String[] idiomsRecoveryArray = idioms.split(";");
            //Collections.addAll(selectedIdiomsList, idiomsRecoveryArray);
            this.selectedIdiomsList = new ArrayList<>(Arrays.asList(idiomsRecoveryArray));
            for(int i = 0; i < idiomsArray.length; i++){
                if(selectedIdiomsList.contains(idiomsArray[i])){
                    idiomsSelectedBooleanArray[i]=true;
                }else{
                    idiomsSelectedBooleanArray[i]=false;
                }
            }
        }
    }

    public void emitNotification(String title, String text) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, SearchMeetingActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification n = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                n = new Notification.Builder(this)
                        .setContentTitle(title)
                        .setContentText(text)
                        //.setColor(R.color.colorPrimary)
                        .setSmallIcon(R.drawable.icon_bla_bla_language)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true).build();
            //}
        }
        notificationManager.notify(0, n);
    }
    @Override
    public void onResume(){
        super.onResume();
        this.emitNotification("Hello user", "bla bla language");

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.sw_meeting_time:
                Toast.makeText(this, "Meeting time action selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sw_new_meeting:
                Toast.makeText(this, "New meeting selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sw_new_user:
                Toast.makeText(this, "New meeting user selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Defaule case on ckecked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btn_select_idioms:
                this.showDialog();
                break;
            case R.id.cb_notifications:
                if(this.cbNotifications.isChecked()){
                    this.swNotificationNewMeeting.setEnabled(true);
                    this.swNotificationTime.setEnabled(true);
                    this.swNotificationNewUser.setEnabled(true);
                }else{
                    this.swNotificationNewMeeting.setEnabled(false);
                    this.swNotificationTime.setEnabled(false);
                    this.swNotificationNewUser.setEnabled(false);
                }
                break;
            default:
                Toast.makeText(this, "Default case on click", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select languages")
                .setMultiChoiceItems(R.array.Idioms, idiomsSelectedBooleanArray,
                        new DialogInterface.OnMultiChoiceClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                    selectedIdiomsList.add(idiomsArray[which]);
                                    Toast.makeText(getApplicationContext(), "added "+ idiomsArray[which], Toast.LENGTH_SHORT).show();

                                }else{

                                    int index = selectedIdiomsList.indexOf(idiomsArray[which]);
                                    if(index>= 0){
                                        selectedIdiomsList.remove(index);
                                        Toast.makeText(getApplicationContext(), "removed  "+ idiomsArray[which], Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder sb = new StringBuilder();
                        for (String idiom : selectedIdiomsList) {
                            sb.append(idiom).append(";");
                        }
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("Idioms", sb.toString());
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"saved to shared preferences", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

