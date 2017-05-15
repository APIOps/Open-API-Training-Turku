package com.example.frostedfin.test;

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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public final URL mTurkuApiUrl = new URL("https://apinf.io:3002/turku_street_maintenance_v1/vehicles/?limit=10&since=2017&api_key=your_apinf_io_api_key_here");

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

        try {
            CallTask taski = new CallTask();
            taski.execute(mTurkuApiUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void addMarksFromJson(String jsonStr) {
        // tbd: parse json
        Toast.makeText(this,jsonStr,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Tampere and move the camera


        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng Tampere = new LatLng(61.503007, 23.764052);
                mMap.addMarker(new MarkerOptions().position(Tampere).title("Marker in Tampere"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Tampere));


            }
        });
        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });
    }


    private class CallTask extends AsyncTask<URL, Void, String> {
        HttpURLConnection mUrlConnection;

        public CallTask() throws MalformedURLException {
        }


        @Override
        protected String doInBackground(URL... urlStr) {

            StringBuilder result = new StringBuilder();
            try {
                mUrlConnection = (HttpURLConnection) urlStr[0].openConnection();
                InputStream in = new BufferedInputStream(mUrlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mUrlConnection.disconnect();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            addMarksFromJson(result);
        }

    }
}

