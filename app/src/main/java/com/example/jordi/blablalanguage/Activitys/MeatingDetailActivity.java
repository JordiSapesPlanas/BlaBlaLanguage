package com.example.jordi.blablalanguage.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jordi.blablalanguage.Models.Establishment;
import com.example.jordi.blablalanguage.R;
import com.google.android.gms.appindexing.Action;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MeatingDetailActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private String address;
    List<Establishment> infoEst= new ArrayList<Establishment>();
    int telefon=0;
    String cap="";
    String telef="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meating_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        String estName = getIntent().getExtras().getString("estabName");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/rest/bla/json/getEstablishmentsByName/" + URLEncoder.encode(estName, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("URL", url);
        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                infoEst.add(convertInfo(response.getJSONObject(i)));
                                address = infoEst.get(0).getAddress();
                                cap=" Capacity :"+infoEst.get(0).getPlacesAvailable();
                                if (!infoEst.get(0).getTelephone().equals("null"))
                                    telefon = Integer.parseInt(infoEst.get(0).getTelephone());
                                else {
                                    telefon =00000000;
                                }
                                TextView tel = (TextView)findViewById(R.id.telephoneNumber);
                                tel.setText("");
                                tel.append(telefon + "");
                                TextView add = (TextView )findViewById(R.id.local_capacity);
                                add.setText("");
                                add.append(cap);
                                TextView tv = (TextView)findViewById(R.id.local_address);
                                tv.setText("");
                                tv.append(address);

                            } catch (Exception e) {
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        queue.add(getRequest);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buttonMap);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("-----", address);

                startActivity(new Intent(getApplicationContext(), MapsActivity.class).putExtra("address", address));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        final ImageButton but = (ImageButton) findViewById(R.id.imageButton);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telefon));
                startActivity(intent);


            }
        });



    }

        private final Establishment convertInfo( JSONObject obj) throws JSONException {

                String name = obj.getString("name");
                String address = obj.getString("address");
                int places = obj.getInt("placesAvailable");
                String tel =obj.getString("telephone");

                Establishment m = new Establishment();
                m.setName(name);
                m.setAddress(address);
                m.setPlacesAvailable(places);
                m.setTelephone(tel);
                return m;


        }

        public void onClick(View view){

            address = infoEst.get(0).getAddress();
            Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+address));
            startActivity(searchAddress);

    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MeatingDetail Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.jordi.blablalanguage.Activitys/http/host/path")
        );

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MeatingDetail Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.jordi.blablalanguage.Activitys/http/host/path")
        );

    }
}
