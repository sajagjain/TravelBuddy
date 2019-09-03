package com.sajagjain.sajagjain.majorproject;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sajagjain.sajagjain.majorproject.model.CityGuideModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import static com.sajagjain.sajagjain.majorproject.R.id.activity_guide_entry_form_map;


public class GuideEntryForm extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    EditText guideEntryName,guideEntryAddress,guideEntryPhnNumber;
    MaterialSpinner guideEntrySince;
    Button guideEntrySubmit;
    String name,address,phnNumber,entrySince,city;
    LatLng latlng;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_entry_form);


        mDatabase= FirebaseDatabase.getInstance().getReference("cityguides");
        //Linking Views
        guideEntryName=(EditText)findViewById(R.id.activity_guide_entry_form_name);
        guideEntryAddress=(EditText)findViewById(R.id.activity_guide_entry_form_address);
        guideEntryPhnNumber=(EditText)findViewById(R.id.activity_guide_entry_form_phn_number);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(activity_guide_entry_form_map);
        mapFragment.getMapAsync(this);
        guideEntrySince = (MaterialSpinner) findViewById(R.id.activity_guide_entry_form_spinner);
        guideEntrySince.setItems("<1 Year","1 Year", "2 Years", "3 years", "4 years", "5 Years",">5 Years");
        guideEntrySince.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        guideEntrySubmit=(Button)findViewById(R.id.activity_guide_entry_form_submit);

        guideEntrySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=guideEntryName.getText().toString();
                address=guideEntryAddress.getText().toString();
                phnNumber=guideEntryPhnNumber.getText().toString();
                entrySince=guideEntrySince.getText().toString();
                city=getIntent().getStringExtra("cityName");


                if(name.trim().equalsIgnoreCase("")){
                    guideEntryName.setError("Please Enter A Name");
                }else if(phnNumber.equalsIgnoreCase("")|| !TextUtils.isDigitsOnly(phnNumber)||phnNumber.length()!=10){
                    guideEntryPhnNumber.setError("Please Enter a Valid Phone Number");
                }else if(address.trim().equalsIgnoreCase("")){
                    guideEntryAddress.setError("Please Select Address");
                }else if(entrySince.trim().equalsIgnoreCase("In City Since")){
                    guideEntrySince.setError("Please Select A Time Frame");
                }else if(latlng==null){
                    Toast.makeText(GuideEntryForm.this,"Please Choose a valid location on Map",Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.child(mDatabase.push().getKey()).setValue(new CityGuideModel(latlng.latitude,latlng.longitude,city,name, phnNumber, address, entrySince));
                    Toast.makeText(GuideEntryForm.this,"Congrats You Are Now City Guide Of "+city,Toast.LENGTH_LONG).show();
                    GuideEntryForm.this.finish();
                }
            }
        });


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;
        CameraPosition cameraPosition = new CameraPosition.Builder().target((LatLng) getIntent().getParcelableExtra("cityLatLng")).zoom(10.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(point));
                LatLng cityLatLng=getIntent().getParcelableExtra("cityLatLng");
                double distance=distance(cityLatLng.latitude,cityLatLng.longitude,point.latitude,point.longitude,"K");
                if(distance<25){
                    latlng=point;
                }else{
                    latlng=null;
                    Toast.makeText(GuideEntryForm.this,"Please select address within 25 km range",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit.equalsIgnoreCase("K")) {
            dist = dist * 1.609344;
        } else if (unit.equalsIgnoreCase( "N")) {
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

}
