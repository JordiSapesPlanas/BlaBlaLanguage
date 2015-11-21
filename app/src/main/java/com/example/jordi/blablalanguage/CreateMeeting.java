package com.example.jordi.blablalanguage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateMeeting extends Activity implements NumberPicker.OnValueChangeListener
{

    NumberPicker np;
    private TextView tv;
    private TextView myText = null;
    int people;
    String language="";
    TimePicker tp;
    DatePicker dp;
    String establishment;




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
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Meeting Created", Toast.LENGTH_SHORT);

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