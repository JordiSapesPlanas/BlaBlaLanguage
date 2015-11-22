package com.example.jordi.blablalanguage.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jordi.blablalanguage.Adapters.meetingAdapter;
import com.example.jordi.blablalanguage.Models.Meeting;
import com.example.jordi.blablalanguage.R;
import com.example.jordi.blablalanguage.Models.MeetingsList;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeetingsListActivity extends AppCompatActivity {

    private List<Meeting> met;
    private String[] nameMeetings;
    private String[] nameEstablishments;
    private String[] imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My meeting list");
        setSupportActionBar(toolbar);

       /* MeetingsList listOfMeetings = new MeetingsList();
        met = listOfMeetings.getList();
        */
        new DownloadMeetingList().execute();

    }



    private class DownloadMeetingList extends AsyncTask<String,Float,String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Hi", "Download Commencing");
            pDialog = new ProgressDialog(MeetingsListActivity.this);
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
            datosDePrueba();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            ListView listView = (ListView) findViewById(R.id.listView_my_meetings);
            meetingAdapter myAdapter = new meetingAdapter(MeetingsListActivity.this, met, R.layout.customer_meeting_list);
            listView.setAdapter(myAdapter);

            pDialog.dismiss();

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

}
