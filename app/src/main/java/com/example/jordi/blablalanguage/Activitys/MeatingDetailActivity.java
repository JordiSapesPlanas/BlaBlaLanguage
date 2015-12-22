package com.example.jordi.blablalanguage.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jordi.blablalanguage.R;
import com.google.android.gms.appindexing.Action;

public class MeatingDetailActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meating_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView tv = (TextView)findViewById(R.id.local_address);

        address = tv.getText().toString();
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
                TextView text =(TextView)findViewById(R.id.telephoneNumber);
                String telefon = text.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telefon));
                startActivity(intent);

            }
        });

    }



        public void onClick(View view){
            TextView text = (TextView) findViewById(R.id.local_address);
            address = text.getText().toString();

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
