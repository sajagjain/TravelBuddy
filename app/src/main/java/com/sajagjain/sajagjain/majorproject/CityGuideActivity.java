package com.sajagjain.sajagjain.majorproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sajagjain.sajagjain.majorproject.R.id.map_for_city_guide_activity;

public class CityGuideActivity extends AppCompatActivity implements OnMapReadyCallback {

    String name, address, city, phnNumber, residentSince;
    LatLng latlng;
    TextView guideName, guideAddress, guidePhoneNumber, guideResidentSince;
    ImageView guidePhoto;
    GoogleMap mMap;
    Button bookCab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_guide);

        //User Data From Intent
        name = getIntent().getStringExtra("cityGuideName");
        address = getIntent().getStringExtra("cityGuideAddress");
        city = getIntent().getStringExtra("cityGuideCity");
        phnNumber = getIntent().getStringExtra("cityGuidePhoneNumber");
        residentSince = getIntent().getStringExtra("cityGuideResidentSince");
        latlng = getIntent().getParcelableExtra("cityGuideLatLng");

        //Linking Views
        guideName = (TextView) findViewById(R.id.activity_city_guide_name);
        guideAddress = (TextView) findViewById(R.id.activity_city_guide_address);
        guidePhoneNumber = (TextView) findViewById(R.id.activity_city_guide_phone_number);
        guideResidentSince = (TextView) findViewById(R.id.activity_city_guide_resident_since);
        guidePhoto = (ImageView) findViewById(R.id.activity_city_guide_photo);
        bookCab=(Button)findViewById(R.id.book_a_cab);

        bookCab.setVisibility(View.INVISIBLE);
        //Setting Data to Views
        guideName.setText(name);
        guideAddress.setText(address);
        guidePhoneNumber.setText(phnNumber);
        guideResidentSince.setText(residentSince);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getColor(name);
        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(100) /* size in px */
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRound(name.charAt(0) + "", color1);
        guidePhoto.setImageDrawable(drawable);

        //Maps Add
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map_for_city_guide_activity);
        mapFragment.getMapAsync(this);



    }

    public static final int LOCATION_REQUEST = 500;

    @Override
    public void onMapReady(final GoogleMap mMap) {

        this.mMap = mMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.places24x24));
        markerOptions.position(latlng).title(name);
        mMap.addMarker(markerOptions);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(10.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);


        mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull final Location location) {

                double diffrence = distance(location.getLatitude(), location.getLongitude(), latlng.latitude, latlng.longitude, "K");
                if (diffrence < 50) {
                    //Adjust Camera
                    Toast.makeText(getApplicationContext(), "Please Wait While We Create Route", Toast.LENGTH_LONG).show();
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(location.getLatitude(),location.getLongitude()));
                    builder.include(latlng);
                    LatLngBounds bounds = builder.build();
                    int padding = 0; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.moveCamera(cu);

                    //Check Route
                    List<LatLng> latLngList = new ArrayList<>();
                    latLngList.add(new LatLng(location.getLatitude(), location.getLongitude()));
                    latLngList.add(latlng);
                    bookCab.setVisibility(View.VISIBLE);
                    bookCab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uri="http://book.olacabs.com/?lat="+location.getLatitude()+"&lng="+location.getLongitude()+"&category=compact&utm_source=12343&landing_page=bk&bk_act=rn&drop_lat="+latlng.latitude+"&drop_lng="+latlng.longitude+"&dsw=yes";
                            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                            startActivity(intent);
                        }
                    });
                    if (latLngList.size() >= 2) {
                        String url = getRequestUrl(latLngList);
                        TaskRequestDirection taskRequestDirection = new TaskRequestDirection();
                        taskRequestDirection.execute(url);
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Enough Points" + latLngList.size(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "You are more than 50kms Away", Toast.LENGTH_LONG).show();
                }
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                Toast.makeText(CityGuideActivity.this, "Waiting For Exact Location, Please Check if the GPS is on", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private String getRequestUrl(List<LatLng> points) {
        String str_origin = "origin=" + points.get(0).latitude + "," + points.get(0).longitude;
        String str_dest = "destination=" + points.get(points.size() - 1).latitude + "," + points.get(points.size() - 1).longitude;
        String sensor = "sensor=false";
        String mode = "mode=road";

        String params = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + params;

        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {

            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            responseString = buffer.toString();
            reader.close();
            isr.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public class TaskRequestDirection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);

        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsJSONParser djp = new DirectionsJSONParser();
                routes = djp.parse(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for (HashMap<String, String> point : path) {

                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lng"));

                    points.add(new LatLng(lat, lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(Color.BLACK);
                polylineOptions.geodesic(true);
                if (polylineOptions != null) {
                    mMap.addPolyline(polylineOptions);
                } else {
                    Toast.makeText(getApplicationContext(), "Directions not found", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
