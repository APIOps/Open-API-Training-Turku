package com.github.apiops.open_api_training_turku.turkustreetmaintenance;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public final URL mTurkuApiUrl =
            new URL("https://apinf.io:3002/turku_street_maintenance_v1/vehicles/?limit=10&since=2017&api_key=TURKU_API_KEY");

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

        TextView link = (TextView) findViewById(R.id.textView);
        String linkText = "Visit the <a href='http://apinf.com'>apinf</a> web page.";
        link.setText(Html.fromHtml(linkText));
        link.setMovementMethod(LinkMovementMethod.getInstance());




        try {
            CallTask taski = new CallTask();
            taski.execute(mTurkuApiUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void addMarksFromJson(String jsonStr) {

        Toast.makeText(getApplicationContext(),"JSON: " + jsonStr,
                Toast.LENGTH_LONG).show();

        if (jsonStr.length() == 0 ) {
            Toast.makeText(getApplicationContext(),
                    "JSON was empty. Most likely API key was missing. Get yours from apinf.io." + jsonStr,
                    Toast.LENGTH_LONG).show();
            return;
        }

        JSONArray turkuAll = null;
        try {
            turkuAll = new JSONArray(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i< turkuAll.length(); i++) {
            try {
                String lastLocationStr = turkuAll.getJSONObject(i).getString("last_location").toString();
                JSONObject lastLocationObj = new JSONObject(lastLocationStr);
                String coordsJsonArrayStr = lastLocationObj.getString("coords").toString() + "\n";
                String eventsJsonArrayStr = lastLocationObj.getString("events").toString() + "\n";
                JSONArray coordsJsonArray = null;
                JSONArray eventsJsonArray = null;
                try {
                    coordsJsonArray = new JSONArray(coordsJsonArrayStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    eventsJsonArray = new JSONArray(eventsJsonArrayStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String longitude = coordsJsonArray.getString(0);
                String latitude = coordsJsonArray.getString(1);
                double lon = Double.parseDouble(longitude);
                double lat = Double.parseDouble(latitude);
                LatLng uusin = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(uusin).title(eventsJsonArray.getString(0)));



                //Toast.makeText(this,Double.toString(lon) + "\n" + Double.toString(lat),Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final int DEFAULT_ZOOM = 10;
        LatLng turku = new LatLng(60.454510, 22.264824);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(turku, DEFAULT_ZOOM));


        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng Tampere = new LatLng(61.503007, 23.764052);
                //mMap.addMarker(new MarkerOptions().position(Tampere).title("Marker in Tampere"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(Tampere));

                mMap.clear();

                try {
                    CallTask taski = new CallTask();
                    taski.execute(mTurkuApiUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

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

