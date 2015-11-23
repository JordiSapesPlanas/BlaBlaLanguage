package com.example.jordi.blablalanguage.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jordi.blablalanguage.R;
import com.example.jordi.blablalanguage.Models.Meeting;

import java.util.Date;

public class CreateMeeting extends Activity implements NumberPicker.OnValueChangeListener
{

    NumberPicker np;
    TextView myText = null;
    int people;
    String language="";
    TimePicker tp;
    DatePicker dp;
    String establishment;
    boolean field1=false,field2=false,field3=false, field4=false;
    Date date;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeeting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" NEW MEETING");


        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEstablishmentDialog();
            }
        });

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLanguageDialog();

            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogDate();

            }
        });
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCapacityDialog();
            }
        });
        final Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1;
                if (field1 && field2 && field3 && field4) {
                     toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Meeting Created", Toast.LENGTH_SHORT);
                    field1=false;
                    field2=false;
                    field3=false;
                    field4=false;
                    Intent i = new Intent(v.getContext(),MeetingsListActivity.class);
                    startActivityForResult(i, 0);
                    Meeting m = new Meeting("Let's Talk", establishment,date,"english");
                    m.load();
                    finish();

                 }
                else{
                     toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Empty field", Toast.LENGTH_SHORT);

                }

                toast1.show();
            }
        });


    }
    public void startCapacityDialog()
    {

        final Dialog d = new Dialog(CreateMeeting.this);
        d.setTitle("Choose People");
        d.setContentView(R.layout.numberpicker);
        Button b1 = (Button) d.findViewById(R.id.button10);
        Button b2 = (Button) d.findViewById(R.id.button20);
        np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(20);
        np.setMinValue(3);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                people=np.getValue();
                d.dismiss();
                showTextCapacity();
                field4=true;


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                showTextDate();
            }
        });
        d.show();



    }
    public void startDialogTime(){

        final Dialog dialog = new Dialog(CreateMeeting.this);

        dialog.setContentView(R.layout.time);
        dialog.setTitle("Choose Hour");
        tp = (TimePicker)dialog.findViewById(R.id.timePicker1);
        tp.setIs24HourView(true);


        Button b1 = (Button) dialog.findViewById(R.id.buttonTimeSet);
        Button b2 = (Button) dialog.findViewById(R.id.buttonTimeCancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showTextDate();
                field3=true;
                date=new Date(dp.getYear()-1900,dp.getMonth(),dp.getDayOfMonth(),tp.getCurrentHour(),tp.getCurrentMinute());


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void startDialogDate(){
        final Dialog dialog = new Dialog(CreateMeeting.this);

        dialog.setContentView(R.layout.date);
        dialog.setTitle("Choose Date");
        dp = (DatePicker)dialog.findViewById(R.id.datePicker1);
        dp.setCalendarViewShown(false);
        dp.setMinDate(System.currentTimeMillis()+(1000 * 60 * 60 * 24));
        dp.setSpinnersShown(true);
        Button b1 = (Button) dialog.findViewById(R.id.buttonDateSet);
        Button b2 = (Button) dialog.findViewById(R.id.buttonDateCancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    dialog.dismiss();
                    startDialogTime();
                    date=new Date(dp.getYear()-1900,dp.getMonth(),dp.getDayOfMonth());

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void startLanguageDialog(){
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(R.array.Idioms, 0, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        String [] s= getResources().getStringArray(R.array.Idioms);
                        language=s[selectedPosition];
                        showTextLanguage();
                        field2=true;

                    }
                })
                .show();
    }

    public void startEstablishmentDialog(){
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(R.array.array_estabishments, 0, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        String [] s= getResources().getStringArray(R.array.array_estabishments);
                        establishment=s[selectedPosition];
                        showTextEstablishment();
                        field1=true;

                    }
                })
                .show();



    }


    public void showTextLanguage(){
        myText = (TextView) findViewById(R.id.textViewShowLan);
        myText.setText("");

        if (myText!=null)
            myText.append("  You have selected "+language);


    }
    public void showTextEstablishment() {
        myText = (TextView) findViewById(R.id.textViewShowEst);
        myText.setText("");

        if (myText != null)
            myText.append("  Your establishment " + establishment);
    }

    public void showTextCapacity(){
        myText = (TextView) findViewById(R.id.textViewShowCapacity);
        myText.setText("");

        if (myText!=null)
            myText.append("  Capacity = " + people + " people");


    }
    public void showTextDate(){
        myText=(TextView)findViewById(R.id.textViewshowDate);
        myText.setText("");
        if (myText!=null)
            myText.append("Date: "+dp.getDayOfMonth()+"/"+dp.getMonth()+"/"+dp.getYear()+"   "+tp.getCurrentHour()+"."+tp.getCurrentMinute()+"h");
    }

    /*
     * Parameters:
        adapter - The AdapterView where the click happened.
        view - The view within the AdapterView that was clicked
        position - The position of the view in the adapter.
        id - The row id of the item that was clicked.
     */


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}