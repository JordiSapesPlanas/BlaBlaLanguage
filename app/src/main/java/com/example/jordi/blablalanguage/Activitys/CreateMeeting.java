package com.example.jordi.blablalanguage.Activitys;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * By Oscar Ujaque
 * Created 26/11/15
 * Modificated 18/12/15
 */
public class CreateMeeting extends Activity implements NumberPicker.OnValueChangeListener {

    NumberPicker np;
    TextView myText1 = null, myText2 = null, myText3 = null, myText4 = null;
    int people;
    String language = "";
    TimePicker tp;
    DatePicker dp;
    String establishment;
    boolean field1 = false, field2 = false, field3 = false, field4 = false;
    Date date;
    static final String estab = "establishment", lang = "language", capac = "capacity", data = "date";
    String capacity, dataString;
    static final String field1s = "field1s", field2s = "field2s", field3s = "field3s", field4s = "field4s";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    /**
     * Called when the activity is first created.
     */
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


        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.NewMeeting));


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
                                    getApplicationContext().getText(R.string.Toast_MeetingCreated), Toast.LENGTH_SHORT);
                    field1 = false;
                    field2 = false;
                    field3 = false;
                    field4 = false;
                    long future = System.currentTimeMillis() + 12000;
                    String title = "Remember the " + language + " meeting";
                    String min = "";
                    if (tp.getCurrentMinute() < 10) {
                        min = "0" + tp.getCurrentMinute();
                    } else {
                        min = tp.getCurrentMinute().toString();
                    }
                    String subtitle = "Today at " + tp.getCurrentHour() + ":" + min + "h";

                    showNotification(getNotification(title, subtitle), future);

                    Intent i = new Intent(v.getContext(), MeetingsListActivity.class);
                    startActivityForResult(i, 0);


                    Meeting m = new Meeting("Let's Talk", establishment, date, language.toLowerCase());

                    m.load();
                    finish();

                } else {
                    toast1 =
                            Toast.makeText(getApplicationContext(),
                                    getApplicationContext().getText(R.string.Toast_EmptyField), Toast.LENGTH_SHORT);

                }

                toast1.show();
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view h ierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance

        field1 = savedInstanceState.getBoolean(field1s);
        field2 = savedInstanceState.getBoolean(field2s);
        field3 = savedInstanceState.getBoolean(field3s);
        field4 = savedInstanceState.getBoolean(field4s);

        if (field1) {
            establishment = savedInstanceState.getCharSequence(estab).toString();
            showTextEstablishment();

        }
        if (field2) {
            language = savedInstanceState.getCharSequence(lang).toString();
            showTextLanguage();


        }
        if (field3) {
            dataString = savedInstanceState.getCharSequence(data).toString();

            myText4 = (TextView) findViewById(R.id.textViewshowDate);
            myText4.setText(dataString);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                date = (Date) formatter.parse(dataString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (field4) {
            people = savedInstanceState.getInt(capac);

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
        savedInstanceState.putBoolean(field2s, field2);
        savedInstanceState.putBoolean(field3s, field3);
        savedInstanceState.putBoolean(field4s, field4);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void startCapacityDialog() {

        final Dialog d = new Dialog(CreateMeeting.this);
        d.setTitle(getResources().getString(R.string.ChoosePeople));
        d.setContentView(R.layout.numberpicker);
        Button b1 = (Button) d.findViewById(R.id.button10);
        Button b2 = (Button) d.findViewById(R.id.button20);
        np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(20);
        np.setMinValue(3);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people = np.getValue();
                d.dismiss();
                showTextCapacity();
                field4 = true;


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }

    public void startDialogTime() {

        final Dialog dialog = new Dialog(CreateMeeting.this);

        dialog.setContentView(R.layout.time);
        dialog.setTitle(getResources().getString(R.string.ChooseHour));
        tp = (TimePicker) dialog.findViewById(R.id.timePicker1);
        tp.setIs24HourView(true);


        Button b1 = (Button) dialog.findViewById(R.id.buttonTimeSet);
        Button b2 = (Button) dialog.findViewById(R.id.buttonTimeCancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showTextDate();
                field3 = true;
                date = new Date(dp.getYear() - 1900, dp.getMonth(), dp.getDayOfMonth(), tp.getCurrentHour(), tp.getCurrentMinute());


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

    public void startDialogDate() {
        final Dialog dialog = new Dialog(CreateMeeting.this);

        dialog.setContentView(R.layout.date);
        dialog.setTitle(getResources().getString(R.string.ChooseDate));
        dp = (DatePicker) dialog.findViewById(R.id.datePicker1);
        dp.setCalendarViewShown(false);
        dp.setMinDate(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
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

    public void startLanguageDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(R.array.Idioms, 0, null)
                .setPositiveButton(getResources().getText(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        String[] s = getResources().getStringArray(R.array.Idioms);
                        language = s[selectedPosition];
                        showTextLanguage();
                        field2 = true;

                    }
                })
                .show();


    }

    public void startEstablishmentDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(R.array.array_estabishments, 0, null)
                .setPositiveButton(getResources().getText(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        String[] s = getResources().getStringArray(R.array.array_estabishments);
                        establishment = s[selectedPosition];
                        showTextEstablishment();
                        field1 = true;

                    }
                })
                .show();


    }


    public void showTextLanguage() {
        myText1 = (TextView) findViewById(R.id.textViewShowLan);
        myText1.setText("");

        if (myText1 != null)
            myText1.append(language);


    }

    public void showTextEstablishment() {
        myText2 = (TextView) findViewById(R.id.textViewShowEst);
        myText2.setText("");

        if (myText2 != null)
            myText2.append(establishment);
    }

    public void showTextCapacity() {
        myText3 = (TextView) findViewById(R.id.textViewShowCapacity);
        myText3.setText("");

        if (myText3 != null)
            myText3.append("" + people);


    }

    public void showTextDate() {
        myText4 = (TextView) findViewById(R.id.textViewshowDate);
        myText4.setText("");
        String min = "00";
        if (tp.getCurrentMinute() < 10) {
            min = "0" + tp.getCurrentMinute();
        }

        if (myText4 != null) {
            myText4.append(dp.getDayOfMonth() + "/" + dp.getMonth() + "/" + dp.getYear() + "   " + tp.getCurrentHour() + ":" + min + ":00");

        }
    }

    private void showNotification(Notification notification, long future) {

        Intent notificationIntent = new Intent(this, Receiver.class);
        //notificationIntent.putExtra(Receiver.NOTIFICATION_ID, 1);
        //notificationIntent.putExtra(Receiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, future, pendingIntent);
    }

    private Notification getNotification(String title, String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.international);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setLights(Color.MAGENTA, 3000, 3000);

        return builder.build();
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateMeeting Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.jordi.blablalanguage.Activitys/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateMeeting Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.jordi.blablalanguage.Activitys/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
