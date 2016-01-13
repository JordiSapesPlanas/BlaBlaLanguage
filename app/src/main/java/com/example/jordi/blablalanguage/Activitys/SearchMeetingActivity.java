package com.example.jordi.blablalanguage.Activitys;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jordi.blablalanguage.Adapters.Receiver;
import com.example.jordi.blablalanguage.Adapters.meetingAdapter;
import com.example.jordi.blablalanguage.Models.Meeting;
import com.example.jordi.blablalanguage.Models.MeetingsList;
import com.example.jordi.blablalanguage.Models.Utils;
import com.example.jordi.blablalanguage.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchMeetingActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Meeting> met;
    private String[] nameMeetings;
    private String[] nameEstablishments;
    private String[] imageName;
    private MeetingsList listOfMeetings;
    Meeting m=null;
    private CircleImageView profileImage;
    List<Meeting> meet= null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meeting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RequestQueue queue= Volley.newRequestQueue(this);
        final String url = "http://alumnes-grp05.udl.cat/rest/bla/json/allEvents";

        JsonArrayRequest getRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());
                        meet = new ArrayList<Meeting>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                meet.add(convertMeeting(response
                                        .getJSONObject(i)));
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

        queue.add(getRequest);

        m = new Meeting(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), CreateMeeting.class);
                startActivityForResult(i, 0);
                SearchMeetingActivity.this.finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Utils utils = new Utils();

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_search_meeting, null);
        navigationView.addHeaderView(header);
        TextView text = (TextView) header.findViewById(R.id.tv_mail_user);
        text.setText(utils.getKey(this, "USER_LOGGED"));

        profileImage = (CircleImageView) header.findViewById(R.id.profile_image);
        //profileImage.setImageResource(R.drawable.perfil_jordi);
        try {
            Picasso.with(this)
                    .load(Uri.parse(utils.getKey(this, "IMAGE")))
                    .placeholder(R.drawable.icon_user)
                    .into(profileImage);
        }catch (Exception e){
            Log.e("***", e.toString());
            profileImage.setImageResource(R.drawable.icon_user);
        }


        new DownloadMeetingList().execute();
    }

    private class DownloadMeetingList extends AsyncTask<String,Float,String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Hi", "Download Commencing");
            pDialog = new ProgressDialog(SearchMeetingActivity.this);
            String message = "Waiting...";
            SpannableString ss2 = new SpannableString(message);
            ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);
            pDialog.setMessage(ss2);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            met = new ArrayList<>();

            nameMeetings = new String[]{"Let's talk","how are you?","NiHao?","BonJour","viva Español","Conocer y hablar","waaaaaa","More language"};
            nameEstablishments=new String[]{"Escala","BonGust","Restaurante WOK","NyamNyam","GOGO","Prat","Mercadona","AC Hotel"};
            imageName=new String[]{"english","english","chinese","france","spain","spain","english","international"};
            listOfMeetings = new MeetingsList();
           // met = listOfMeetings.getList();
            met = m.getAll(null);


            /*

            */

            //datosDePrueba();
            try {
                Log.e("----", "ueeeueueeueueue");

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e("--***--", "ueeeueu111eeueueue");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            ListView listView = (ListView) findViewById(R.id.listView);
            meetingAdapter myAdapter = new meetingAdapter(SearchMeetingActivity.this, meet, R.layout.customer_meeting_list);
            listView.setAdapter(myAdapter);
            pDialog.dismiss();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    m = (Meeting) parent.getAdapter().getItem(position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchMeetingActivity.this);

                    builder.setTitle("Register");
                    builder.setMessage("Do you want to go?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            dialog.dismiss();
                            int hour = m.getDateMeeting().getHours();

                            int minute = m.getDateMeeting().getMinutes();
                            String s = "Today at "+m.getDateMeeting().toString().split(" ")[3].substring(0,5);

                            long future = System.currentTimeMillis() + 10000;
                            String title = m.getLanguage()+ " Meeting";
                            showNotification(getNotification(title, s), future);
                            Intent intent = new Intent(SearchMeetingActivity.this, MeatingDetailActivity.class);
                            intent.putExtra("estabName", m.getEstablishment());
                            startActivity(intent);

                        }

                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
            });
        }
    }


    private void datosDePrueba(){

        for(int i=0;i<nameMeetings.length;i++){
            Meeting m= new Meeting();
            m.setName(nameMeetings[i]);
            m.setEstablishment(nameEstablishments[i]);
            m.setImageUrl(imageName[i]);
            met.add(m);
        }
    }

    @Override
    public void onBackPressed() {

        SearchMeetingActivity.this.finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_meetings_list) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(new Intent(getApplicationContext(), MeetingsListActivity.class), Bundle.EMPTY);
                //this.finish();
            }
            // Handle the camera action
        } else if (id == R.id.nav_preferences) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(new Intent(getApplicationContext(), PreferencesActivity.class), Bundle.EMPTY);
                this.finish();
            }
        } else if (id == R.id.nav_profile) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class), Bundle.EMPTY);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showNotification(Notification notification, long future) {

        Intent notificationIntent = new Intent(this, Receiver.class);
        notificationIntent.putExtra(Receiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(Receiver.NOTIFICATION, notification);
        int i = (int)SystemClock.elapsedRealtime()%99999999;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, future, pendingIntent);
    }

    private Notification getNotification(String title,String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notification);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setLights(Color.MAGENTA, 3000, 3000);

        return builder.build();
    }
    private final Meeting convertMeeting(JSONObject obj) throws JSONException {
        String name = obj.getString("name");
        String estab = obj.getString("estab");
        String time = obj.getString("tim");
        String lang = obj.getString("lang");

        Meeting m = new Meeting();
        m.setName(name);
        m.setEstablishment(estab);
        m.setDateMeeting(convert(time)) ;
        m.setLanguage(lang);
        m.setImageUrl(lang);
        return m;

    }
    private Date convert(String s) {
        Date date = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



}
