package com.example.jordi.blablalanguage.Activitys;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jordi.blablalanguage.Models.Establishment;
import com.example.jordi.blablalanguage.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeatingDetailActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private String address;
    List<Establishment> infoEst = new ArrayList<Establishment>();
    int telefon = 0;
    String telef = "";
    String language = "";
    TextView users, lan, cap, add, tel, tim;
    String estName, capacity;
    int id;
    String time;
    private String photoUrl;
    private CircleImageView circleImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        estName = getIntent().getExtras().getString("estabName");
        id = getIntent().getExtras().getInt("id");
        language = getIntent().getExtras().getString("language");
        time = getIntent().getExtras().getString("date");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meating_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");


        users = (TextView) findViewById(R.id.meetingUsers);
        users.setText("Users: ");
        tel = (TextView) findViewById(R.id.telephoneNumber);
        tel.setText("");
        cap = (TextView) findViewById(R.id.local_capacity);
        cap.setText("");
        add = (TextView) findViewById(R.id.local_address);
        add.setText("");
        lan = (TextView) findViewById(R.id.languageMeeting);
        lan.setText("Language: ");
        lan.append(language);
        tim = (TextView) findViewById(R.id.timeMeeting);
        tim.setText("");
        tim.append("Time: " + time.split(" ")[3].substring(0, 5));
        circleImageView = (CircleImageView) findViewById(R.id.profile_image_establishment);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buttonMap);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("-----", address);
                address = address +", Lleida"
                startActivity(new Intent(getApplicationContext(), MapsActivity.class).putExtra("address", address ));
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
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                startActivity(intent);


            }
        });
        new DownloadMeetingList().execute();



    }
    private class DownloadMeetingList extends AsyncTask<String,Float,String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Hi", "Download Commencing");
            pDialog = new ProgressDialog(MeatingDetailActivity.this);
            String message = "Waiting...";
            SpannableString ss2 = new SpannableString(message);
            ss2.setSpan(new RelativeSizeSpan(2f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);
            pDialog.setMessage(ss2);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            RequestQueue queue = Volley.newRequestQueue(MeatingDetailActivity.this.getApplicationContext());
            String url = null;
            try {
                url = "http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/rest/bla/json/establishments/name/" + URLEncoder.encode(estName, "UTF-8").replace("+", "%20");
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
                                    capacity =  infoEst.get(0).getPlacesAvailable()+"";
                                    if (!infoEst.get(0).getTelephone().equals("null"))
                                        telefon = Integer.parseInt(infoEst.get(0).getTelephone());
                                    else {
                                        telefon = 00000000;
                                    }

                                    tel.append("Telephone: "+telefon );
                                    cap.append("Capacity: "+capacity);
                                    add.append(address);

                                    pDialog.dismiss();


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
            getRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(getRequest);

            String url2 = null;
            String Id = String.valueOf(id);

            url2 = "http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/rest/bla/json/events/id/" + Id + "/users";
            JsonArrayRequest getRequest2 = new JsonArrayRequest(url2,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // display response
                            Log.d("ResponseUsers", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    String user = response.getJSONObject(i).getString("name").toString();


                                    users.append(user + ", ");

                                } catch (Exception e) {
                                }
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("Error.ResponseUsers", error.toString());
                        }
                    }
            );
            getRequest2.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            queue.add(getRequest2);

        }
    }


        private final Establishment convertInfo( JSONObject obj) throws JSONException {

                String name = obj.getString("name");
                String address = obj.getString("address");
                int places = obj.getInt("placesAvailable");
                String tel =obj.getString("telephone");
                String photoUrl = obj.getString("photo");


                //circleImageView = (CircleImageView) findViewById(R.id.profile_image_establishment );
                //profileImage.setImageResource(R.drawable.perfil_jordi);


                try {

                    Log.e("**---**", URLEncoder.encode("http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/img/estab/" + photoUrl, "UTF-8").toString());
                    Picasso.with(this)
                            .load(Uri.parse("http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/img/estab/" + photoUrl))
                            .placeholder(R.drawable.escala)
                            .into(circleImageView);
                }catch (Exception e){
                    Log.e("***", e.toString());
                    circleImageView.setImageResource(R.drawable.escala);
                }



                //circleImageView.setImageURI(Uri.parse(URLEncoder.encode("http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/img/" + photoUrl, "UTF-8")));
            //circleImageView.setImageURI(URLEncoder"http://alumnes-grp05.udl.cat/BlaBlaLanguageWeb/img/estab");
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

}
