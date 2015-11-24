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

import com.example.jordi.blablalanguage.Models.Meeting;
import com.example.jordi.blablalanguage.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateMeeting extends Activity implements NumberPicker.OnValueChangeListener
{

    NumberPicker np;
    TextView myText1 =null,myText2=null,myText3=null,myText4=null;
    int people;
    String language="";
    TimePicker tp;
    DatePicker dp;
    String establishment;
    boolean field1=false,field2=false,field3=false, field4=false;
    Date date;
    static final String estab="establishment", lang="language", capac="capacity", data="date";
    String capacity, dataString;
    static final String field1s="field1s",field2s="field2s",field3s="field3s", field4s="field4s";



    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeeting);

        myText1 = (TextView) findViewById(R.id.textViewShowLan);
        myText1.setText("");
        myText2 = (TextView) findViewById(R.id.textViewShowEst);
        myText2.setText("");
        myText3 = (TextView) findViewById(R.id.textViewShowCapacity);
        myText3.setText("");
        myText4 = (TextView) findViewById(R.id.textViewshowDate);
        myText4.setText("");


        myText1.setText("");

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }


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
                    field1 = false;
                    field2 = false;
                    field3 = false;
                    field4 = false;
                    Intent i = new Intent(v.getContext(), MeetingsListActivity.class);
                    startActivityForResult(i, 0);


                    Meeting m = new Meeting("Let's Talk", establishment, date, language.toLowerCase());
                    m.load();
                    finish();

                } else {
                    toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Empty field", Toast.LENGTH_SHORT);

                }

                toast1.show();
            }
        });


    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view h ierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance

        field1=savedInstanceState.getBoolean(field1s);
        field2=savedInstanceState.getBoolean(field2s);
        field3=savedInstanceState.getBoolean(field3s);
        field4=savedInstanceState.getBoolean(field4s);

        if (field1){
            establishment = savedInstanceState.getCharSequence(estab).toString();
            showTextEstablishment();

        }
        if (field2){
            language = savedInstanceState.getCharSequence(lang).toString();
            showTextLanguage();


        }
        if (field3){
            dataString=savedInstanceState.getCharSequence(data).toString();

            myText4=(TextView)findViewById(R.id.textViewshowDate);
            myText4.setText(dataString);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                date = (Date)formatter.parse(dataString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (field4){
            people=savedInstanceState.getInt(capac);

            showTextCapacity();

        }

    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putCharSequence(lang, myText1.getText());
        savedInstanceState.putCharSequence(estab, myText2.getText());
        savedInstanceState.putInt(capac, people);
        savedInstanceState.putCharSequence(data, myText4.getText());
        savedInstanceState.putBoolean(field1s, field1);
        savedInstanceState.putBoolean(field2s,field2);
        savedInstanceState.putBoolean(field3s,field3);
        savedInstanceState.putBoolean(field4s,field4);





        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
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
        myText1 = (TextView) findViewById(R.id.textViewShowLan);
        myText1.setText("");

        if (myText1!=null)
            myText1.append(language);


    }
    public void showTextEstablishment() {
        myText2 = (TextView) findViewById(R.id.textViewShowEst);
        myText2.setText("");

        if (myText2 != null)
            myText2.append(  establishment);
    }

    public void showTextCapacity(){
        myText3 = (TextView) findViewById(R.id.textViewShowCapacity);
        myText3.setText("");

        if (myText3!=null)
            myText3.append( ""+people);


    }
    public void showTextDate(){
        myText4=(TextView)findViewById(R.id.textViewshowDate);
        myText4.setText("");

        if (myText4!=null) {
            myText4.append(dp.getDayOfMonth() + "/" + dp.getMonth() + "/" + dp.getYear() + "   " + tp.getCurrentHour() + ":" + tp.getCurrentMinute()+":00");

        }
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}