package com.example.frostedfin.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public final URL apiAdd = new URL ("https://apinf.io:3002/turku_street_maintenance_v1/vehicles/?limit=10&since=2017&api_key=Turku_api_key");

    public MapsActivity() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Tampere and move the camera


        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng Tampere = new LatLng(61.503007, 23.764052);
                mMap.addMarker(new MarkerOptions().position(Tampere).title("Marker in Tampere"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Tampere));


                /*try {
                    HttpsURLConnection myConnection = (HttpsURLConnection) apiAdd.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                JSONArray turkuAll = null;
                try {
                    turkuAll = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String koira="";
                String kissa="";
                for (int i = 0; i< turkuAll.length(); i++){
                    try {
                        String lastLocationStr = turkuAll.getJSONObject(i).getString("last_location").toString();
                        JSONObject lastLocationObj = new JSONObject(lastLocationStr);
                        //koira = koira + lastLocationObj.toString()+"\n";

                        //kissa = kissa + lastLocationObj.getJSONObject("coords").toString() + "\n";
                        String coordsJsonArrayStr = lastLocationObj.getString("coords").toString() + "\n";
                        JSONArray coordsJsonArray = null;
                        try {
                            coordsJsonArray = new JSONArray(coordsJsonArrayStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String longitude = coordsJsonArray.getString(0);
                        String latitude = coordsJsonArray.getString(1);
                        double lon= Double.parseDouble(longitude);
                        double lat= Double.parseDouble(latitude);
                        kissa = kissa + " " + "Kierros " + Integer.toString(i) +"long =" + longitude + "\n";
                        koira = koira + " " + "Kierros " + Integer.toString(i) +"lat =" + latitude + "\n";
                        LatLng uusin = new LatLng(lon,lat);
                        mMap.addMarker(new MarkerOptions().position(uusin).title("Marker" + i));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }*/
            }
        });
        ((Button)findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });
    }
}
