package com.example.jordi.blablalanguage.Activitys;


import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jordi.blablalanguage.R;
import com.example.jordi.blablalanguage.Utils.GeocodeResponse;
import com.example.jordi.blablalanguage.Utils.GeoCoder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        AsyncTask async = new AsyncTask<String, Integer, LatLng>() {
            @Override
            protected LatLng doInBackground(String... params) {
                GeoCoder gc = new GeoCoder();


                try {
                    GeocodeResponse gcr = gc.getLocation(params);
                    List<GeocodeResponse.Result> results = gcr.getResults();
                    for(GeocodeResponse.Result r : results){
                        Log.e("---L---", r.getGeometry().getLocation().getLat() + " : "+r.getGeometry().getLocation().getLng());

                    }
                    return new LatLng(results.get(0).getGeometry().getLocation().getLat(), results.get(0).getGeometry().getLocation().getLng());

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }


            }

            protected void onPostExecute(LatLng result) {
                mMap.addMarker(new MarkerOptions().position(result).title("Marker in address"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(result));
                float zoomLevel = (float)16.0; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(result, zoomLevel));
            }

        };


        String[] addr= new String[1];
        String s = getIntent().getStringExtra("address");
        if( s == null){
             addr[0]= "C/Urquinaona 8, Terrassa";
        }else{
            addr[0]= s;
        }

        async.execute(addr);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera



/*
        LatLng sydney = new LatLng(-34, 151);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=101+main+street");

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}
