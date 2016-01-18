package com.example.jordi.blablalanguage.Activitys;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jordi.blablalanguage.Adapters.NotificationService;
import com.example.jordi.blablalanguage.Models.Establishment;
import com.example.jordi.blablalanguage.Models.EventServer;
import com.example.jordi.blablalanguage.Models.Language;
import com.example.jordi.blablalanguage.Models.Meeting;
import com.example.jordi.blablalanguage.Models.Utils;
import com.example.jordi.blablalanguage.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * By Oscar Ujaque
 * Created 26/11/15
 * Modificated 18/12/15
 * Re-modificated 11-01-16
 */
public class CreateMeeting extends Activity  {

    //Attributes
    NumberPicker np;
    TextView myText1 = null, myText2 = null, myText3 = null, myText4 = null, myTextName = null;
    TimePicker tp;
    DatePicker dp;
    
    boolean field1 = false, field2 = false, field3 = false, field4 = false;
    Date date;
    String capacity, dataString, establishment, language;
    int people;

    static final String field1s = "field1s", field2s = "field2s", field3s = "field3s", field4s = "field4s";
    static final String estab = "establishment", lang = "language", capac = "capacity", data = "date";

    RequestQueue queue = null;
    List<String> establis = null;
    List<String> languag=null;


    // Jordi Variables
    private List <Language> languageList = null;
    private List <Establishment> establishmentsList = null;
    private Meeting meeting = null;
    boolean updateEvent;
    private EventServer eventServer = null;
    private Utils utils = new Utils();
    private Activity activity;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {

        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeeting);

        //put the text for showing in blank
        myTextName = (TextView) findViewById(R.id.name);
        myTextName.setText("");
        myText1 = (TextView) findViewById(R.id.textViewShowLan);
        myText1.setText("");
        myText2 = (TextView) findViewById(R.id.textViewShowEst);
        myText2.setText("");
        myText3 = (TextView) findViewById(R.id.textViewShowCapacity);
        myText3.setText("");
        myText4 = (TextView) findViewById(R.id.textViewshowDate);
        myText4.setText("");



        //Create button
        final Button button5 = (Button) findViewById(R.id.button5);

        if(getIntent().getExtras() != null){
            updateEvent = true;
            Bundle b= getIntent().getExtras();
            meeting = (Meeting) b.getSerializable("meeting");
            myTextName.setText(meeting.getName());
            myText1.setText(meeting.getLanguage());
            myText2.setText(meeting.getEstablishment());
            myText4.setText(meeting.getDateMeeting().toString());
            button5.setText("edit");
            field1 = true;
            field2 = true;
            field3 = true;
            field4 = true;

        }

        // For Update Meeting







        //making the acces to server for establishments
        queue= Volley.newRequestQueue(this);


        //making the acces to server for languages


        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }




        // select establishments
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEstablishmentDialog();
            }
        });
        // select languages
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLanguageDialog();
            }

        });

        //select  date and hour
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogDate();

            }
        });

        //select capacity
        final Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCapacityDialog();
            }
        });
        establishmentsList = new ArrayList<>();
        languageList = new ArrayList<>();
        doRestRequest();




        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (field1 && field2 && field3 && field4) {

                    // we update values to false to restore if necessary when changing orientation
                    field1 = false;
                    field2 = false;
                    field3 = false;
                    field4 = false;



                    /**String title = "Remember the " + language + " meeting";
                    String min = "";
                    if (tp.getCurrentMinute() < 10) {
                        min = "0" + tp.getCurrentMinute();
                    } else {
                        min = tp.getCurrentMinute().toString();
                     }
                    String subtitle = "Today at " + tp.getCurrentHour() + ":" + min + "h";
                     **/


                    // we create the meeting
                    if(!updateEvent) // SO create new Event
                    {
                        meeting = new Meeting(v.getContext());
                    }

                    String s = myTextName.getText().toString();
                    meeting.setName(s);
                    meeting.setEstablishment(establishment);
                    meeting.setDateMeeting(date);
                    meeting.setLanguage(language);
                    meeting.setImageUrl(language);

                    if(updateEvent){
                        meeting.modifiyMeeting(getApplicationContext());
                    }else{
                        String email = utils.getKey(activity, "USER_LOGGED");
                        if(email != null){
                            meeting.setUserEmail(email);
                        }else{
                            meeting.setUserEmail("");
                        }

                        meeting.Save(null);
                    }

                    saveMeeting(meeting);

                    //Intent i = new Intent(v.getContext(), MeetingsListActivity.class);
                    //startActivityForResult(i, 0);


//                    Intent i = new Intent(v.getContext(), SearchMeetingActivity.class);
//                    startActivity(i);
//                    CreateMeeting.this.finish();

                } else {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    getApplicationContext().getText(R.string.Toast_EmptyField), Toast.LENGTH_SHORT);
                    toast1.show();

                }

            }
        });



    }


    private void doRestRequest() {
        final String url2 = "http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/rest/bla/json/allLanguages";

        JsonArrayRequest getRequest2 = new JsonArrayRequest( url2,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());
                        languag = new ArrayList<String>();
                        //languageList = new ArrayList<>();
                        Language l;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                l = new Language();
                                l.setName(response.getJSONObject(i).getString("name").toString());
                                l.setId(Integer.parseInt(response.getJSONObject(i).getString("id").toString()));
                                languageList.add(l);
                                languag.add(response.getJSONObject(i).getString("name").toString());

                            } catch (JSONException e) {
                            }
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        getRequest2.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(getRequest2);


        final String url = "http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/rest/bla/json/allEstablishments";

        JsonArrayRequest getRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());
                        establis = new ArrayList<String>();
                        Establishment e;
                        //establishmentsList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                e =new Establishment();

                                e.setName(response.getJSONObject(i).getString("name").toString());
                                e.setId(Integer.parseInt(response.getJSONObject(i).getString("id").toString()));
                                establishmentsList.add(e);
                                establis.add(response.getJSONObject(i).getString("name").toString());

                            } catch (JSONException err) {
                            }
                        }
                        //startEstablishmentDialog();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        getRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(getRequest);


    }


    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, SearchMeetingActivity.class));
        this.finish();
    }

    /**
     *Method for restoring data when screen rotation
     *
     * @param savedInstanceState
     */
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

    /**
     * Method for saving data before screen rotation
     *
     * @param savedInstanceState
     */
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

    /**
     * method for showing the capacity dialog
     */
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

    private void saveMeeting(final Meeting m) {
        Integer languageId = null;
        Integer establishId = null;




        for(Language l: this.languageList){

            if(l.getName().equals(m.getLanguage())){
                languageId = l.getId();
            }
        }
        for(Establishment e: this.establishmentsList){

            if(e.getName().equals(m.getEstablishment())){
                establishId = e.getId();
            }
        }
        if(languageId != null && establishId != null){
            eventServer = new EventServer(establishId, languageId, m.getName(), m.getTimeString(), m.getName());
            new AsyncTask<Void, Void, String>(){

                @Override
                protected String doInBackground(Void... params) {
                    if(updateEvent){
                        return Utils.soapUpdateEvent(m.getIdM(), eventServer);
                    }else{
                        return Utils.soapCreateEvent(eventServer);

                    }
                }
                @Override
                protected void onPostExecute(String result) {
                    if(result != null && result.equals("Sucess")){
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        getApplicationContext().getText(R.string.Toast_MeetingCreated), Toast.LENGTH_SHORT);
                        toast1.show();
                        // computing the time when the notification is shown

                        long future = System.currentTimeMillis() + 10000;
                        // getting the service for showing the notification with an Alarm Manager
                        Intent intent = new Intent(CreateMeeting.this, NotificationService.class);
                        intent.putExtra("language",language);
                        intent.putExtra("date",date.toString());
                        PendingIntent pendingIntent = PendingIntent.getService(CreateMeeting.this, 001, intent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);



                        Intent i = new Intent(CreateMeeting.this, SearchMeetingActivity.class);
                        startActivity(i);
                        CreateMeeting.this.finish();
                    }else{
                        myTextName.setText("");
                        myText1.setText("");
                        myText2.setText("");
                        myText3.setText("");
                        myText4.setText("");


                        Toast.makeText(getApplicationContext(), "Problems with server. Try again later", Toast.LENGTH_SHORT).show();
                    }

                }
            }.execute();
        }




    }


    /**
     * method for showing the time dialog
     */
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

    /**
     * method for showing  the date dialog
     */
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

    /**
     * method for showing the language dialog
     */
    public void startLanguageDialog() {
        if(languageList.isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle("Problems Comunicating with server, try it again later")
                    .setPositiveButton(getResources().getText(R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                        }
                    })
                    .show();


        }else{
            new AlertDialog.Builder(this)
                    .setSingleChoiceItems(languag.toArray(new String[languag.size()]), 0, null)
                    .setPositiveButton(getResources().getText(R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            language = languag.get(selectedPosition);
                            showTextLanguage();
                            field2 = true;

                        }
                    })
                    .show();

        }


    }

    /**
     * method for showing the establishment dialog
     */
    public void startEstablishmentDialog() {
        Log.e("***1**", " +  + "+ establishmentsList.isEmpty());
        if(establishmentsList.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Problems Comunicating with server, try it again later")
                    .setPositiveButton(getResources().getText(R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                        }
                    })
                    .show();
        }else{
            new AlertDialog.Builder(this)
                    .setSingleChoiceItems(establis.toArray(new String[establis.size()]), 0, null)
                    .setPositiveButton(getResources().getText(R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            establishment = establis.get(selectedPosition);
                            showTextEstablishment();
                            field1 = true;

                        }
                    })
                    .show();
        }



    }

    // methods for showing the data selected

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
        String min = tp.getCurrentMinute()+"";
        if (tp.getCurrentMinute() < 10) {
            min = "0" + tp.getCurrentMinute();
        }

        if (myText4 != null) {
            myText4.append(dp.getDayOfMonth() + "/" + (dp.getMonth()+1) + "/" + dp.getYear()+ "   " + tp.getCurrentHour() + ":" + min);

        }
    }




}
