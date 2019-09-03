package com.sajagjain.sajagjain.majorproject;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.ArcMotion;
import android.transition.Explode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sajagjain.sajagjain.majorproject.adapter.NewsAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.PointOfInterestAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.RestaurantAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.WeatherAdapter;
import com.sajagjain.sajagjain.majorproject.model.CityGuideModel;
import com.sajagjain.sajagjain.majorproject.model.DescribeMeEverythingResponse;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestFirstStep;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestResponse;
import com.sajagjain.sajagjain.majorproject.model.RestaurantFirstStep;
import com.sajagjain.sajagjain.majorproject.model.RestaurantResponse;
import com.sajagjain.sajagjain.majorproject.model.RestaurantSecondStep;
import com.sajagjain.sajagjain.majorproject.model.SinglePlaceSavedInTPA;
import com.sajagjain.sajagjain.majorproject.model.SingleRestaurantFirstStep;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.sajagjain.sajagjain.majorproject.model.Weather;
import com.sajagjain.sajagjain.majorproject.model.WeatherResponse;
import com.sajagjain.sajagjain.majorproject.rest.ApiClient;
import com.sajagjain.sajagjain.majorproject.rest.ApiInterface;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.UncheckedIOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import static com.sajagjain.sajagjain.majorproject.R.id.map;

public class Places extends AppCompatActivity implements RestaurantAdapter.RestaurantAdapterListener, TravelPlanAdapter.TravelPlanAdapterListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    //Declarations
    private DatabaseReference mDatabase;
    private TextView moreDescription;
    private TextView placeName, placeAddress, description, guideName,guideAddress;
    private TextView guideKnowMore, openFullMap;
    private TextView flightDestination, flightDate;
    private TextView trainSource, trainDestination, trainDate;
    private TextView busSource, busDestination, busDate;
    private TextView buttonShowFlights, buttonShowTrains, buttonShowBuses;
    private TextView flightSource;
    private RadioButton economyClass, businessClass;
    private RadioGroup classGroup;
    private ImageView cityGuidePhoto, placeMainImage;
    private MaterialSpinner flightAdults, flightChildren;
    private ToggleButton descriptionSave, tweetsSave;
    private CheckBox trainLoadSaveData, busLoadSaveData, flightLoadSaveData;
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private String placeId = "ChIJhSxoJzyuEmsR9gBDBR09ZrE";
    private final static String TAG = "MapsActivity";
    private final static String API_KEY_WEATHER = "5372003f5937e4ca5ce14af7d1c3261e";
    private static final String API_KEY_GOOGLE_PLACES = "AIzaSyCmUWom-QiewbU2KnwiL_Gxx-W8fvdU40g";
    private static String flightClass = "economy";
    private LatLng latLng;
    private Calendar calendar;
    int year, month, day;
    Dialog dialog;
    String dFABusSource, dFABusDestination, dFATrainSource, dFATrainDestination, dFAFlightSource, dFAFlightDestination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        AwesomeBar toolbar = (AwesomeBar) findViewById(R.id.bar);
        toolbar.addAction(R.drawable.awsb_ic_edit_animated, "Save Place");

        toolbar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
                Toast.makeText(getBaseContext(), actionItem.getText() + " clicked", Toast.LENGTH_LONG).show();
                showBottomSheetDialogFragment();
            }

        });
        toolbar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Places.this, "Place Rating Feature Coming Soon", Toast.LENGTH_LONG).show();
            }
        });
        toolbar.displayHomeAsUpEnabled(false);


        setupWindowAnimations();

        dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading...");
        dialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference("cityguides");
        //Declarations Linking
        placeName = (TextView) findViewById(R.id.placename);
        description = (TextView) findViewById(R.id.place_description);
        guideName = (TextView) findViewById(R.id.placecityguidename);
        guideKnowMore = (TextView) findViewById(R.id.placecityguideknowmore);
        guideAddress=(TextView)findViewById(R.id.placecityguideaddress);
        cityGuidePhoto = (ImageView) findViewById(R.id.placecityguidephoto);
        placeMainImage = (ImageView) findViewById(R.id.placedestinationphoto);
        placeAddress = (TextView) findViewById(R.id.placeaddress);
        openFullMap = (TextView) findViewById(R.id.open_full_map_view);
        flightSource = (TextView) findViewById(R.id.flight_source_edittext);
        flightDestination = (TextView) findViewById(R.id.flight_destination_edittext);
        flightDate = (TextView) findViewById(R.id.flight_date_edittext);
        flightAdults = (MaterialSpinner) findViewById(R.id.flight_number_of_adult_edittext);
        flightChildren = (MaterialSpinner) findViewById(R.id.flight_number_of_children_editText);
        trainSource = (TextView) findViewById(R.id.train_source_edittext);
        trainDestination = (TextView) findViewById(R.id.train_destination_edittext);
        trainDate = (TextView) findViewById(R.id.train_date_edittext);
        busSource = (TextView) findViewById(R.id.bus_source_edittext);
        busDestination = (TextView) findViewById(R.id.bus_destination_edittext);
        busDate = (TextView) findViewById(R.id.bus_date_edittext);
        buttonShowFlights = (TextView) findViewById(R.id.showFlight);
        buttonShowBuses = (TextView) findViewById(R.id.showBus);
        buttonShowTrains = (TextView) findViewById(R.id.showTrain);
        economyClass = (RadioButton) findViewById(R.id.economyClass);
        businessClass = (RadioButton) findViewById(R.id.businessClass);
        moreDescription = (TextView) findViewById(R.id.more_description);
        flightLoadSaveData = (CheckBox) findViewById(R.id.flight_load_saved_data);
        trainLoadSaveData = (CheckBox) findViewById(R.id.train_load_saved_data);
        busLoadSaveData = (CheckBox) findViewById(R.id.bus_load_saved_data);
        descriptionSave = (ToggleButton) findViewById(R.id.description_save);
        tweetsSave = (ToggleButton) findViewById(R.id.tweets_save);


//        classGroup=(RadioGroup)findViewById(R.id.radioGroup);
//
//        classGroup.addView(economyClass);
//        classGroup.addView(businessClass);
//        classGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                if(checkedId==R.id.economyClass)
//                {
//                    businessClass.setChecked(false);
//                    flightClass="economy";
//                }else if(checkedId==R.id.businessClass)
//                {
//                    economyClass.setChecked(false);
//                    flightClass="business";
//                }
//            }
//        });


        //Setting values to spinners adult and children
        flightAdults.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9");
        flightAdults.setHint("No of Adults");
        flightChildren.setItems("0", "1", "2", "3", "4", "5");
        flightChildren.setHint("No of Children");

        //Setting onclick listeners to adult and children
        final String[] flightAdultsnumber = new String[1];
        final String[] flightChildrenNumber = new String[1];
        flightAdultsnumber[0] = "1";
        flightChildrenNumber[0] = "0";

        flightAdults.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                flightAdultsnumber[0] = item;
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        flightChildren.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                flightChildrenNumber[0] = item;
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        //City Guide Fiasco

        com.google.firebase.database.Query query = mDatabase;

        final List<CityGuideModel> models=new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener()
         {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //TODO get the data here
                    CityGuideModel model=(CityGuideModel)postSnapshot.getValue(CityGuideModel.class);
                    if(model.getGuideCity().equalsIgnoreCase(placeName.getText().toString())) {
                        Log.d("modelguide", model.getGuideName());
                        guideName.setText(model.getGuideName());
                        guideAddress.setText(model.getGuideAddress());
                        guideKnowMore.setText("Know More");

                        ColorGenerator generator = ColorGenerator.MATERIAL;
                        int color1 = generator.getColor(model.getGuideName());
                        TextDrawable drawable = TextDrawable.builder().beginConfig()
                                .textColor(Color.BLACK)
                                .useFont(Typeface.DEFAULT)
                                .fontSize(70) /* size in px */
                                .bold()
                                .toUpperCase()
                                .endConfig()
                                .buildRound(model.getGuideName().charAt(0)+"", color1);

                        cityGuidePhoto.setImageDrawable(drawable);

                        models.clear();
                        models.add(0,model);
                        Log.d("beforesending",models.get(0).getGuideName());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        query.addValueEventListener(valueEventListener);

        guideKnowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guideKnowMore.getText().toString().equalsIgnoreCase("Be The One To Lead")) {
                    Toast.makeText(getApplicationContext(), "New Guide Form Redirect", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Places.this,GuideEntryForm.class)
                            .putExtra("cityName",placeName.getText().toString())
                            .putExtra("cityLatLng",latLng));
                } else if (guideKnowMore.getText().toString().equalsIgnoreCase("Know More")) {
                    Log.d("beforesending",models.get(0).getGuideName());
                    startActivity(new Intent(Places.this, CityGuideActivity.class)
                            .putExtra("cityGuideName",models.get(0).getGuideName())
                            .putExtra("cityGuideAddress",models.get(0).getGuideAddress())
                            .putExtra("cityGuideCity",models.get(0).getGuideCity())
                            .putExtra("cityGuidePhoneNumber",models.get(0).getGuidePhoneNumber())
                            .putExtra("cityGuideResidentSince",models.get(0).getResidentSince())
                            .putExtra("cityGuideLatLng",new LatLng(models.get(0).getLat(),models.get(0).getLon())));
                }
            }
        });

        //Calendar Instances
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        //Load saved data on checkbox click
        flightLoadSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adultnumber = flightAdultsnumber[0], childrenNum = flightChildrenNumber[0];
                if (flightLoadSaveData.isChecked()) {
                    flightSource.setText("IDR");
                    flightDestination.setText("DEL");
                    flightDate.setText("20180126");
                    flightAdults.setText("1");
                    flightChildren.setText("0");

                } else {

                    flightSource.setText("");
                    flightDestination.setText("");
                    flightDate.setText("");
                    flightAdults.setText(adultnumber);
                    flightChildren.setText(childrenNum);

                }
            }
        });

        trainLoadSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trainLoadSaveData.isChecked()) {
                    trainSource.setText("IDR");
                    trainDestination.setText("BPL");
                    trainDate.setText("10-12-2018");

                } else {

                    trainSource.setText("");
                    trainDestination.setText("");
                    trainDate.setText("");

                }

            }
        });

        busLoadSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (busLoadSaveData.isChecked()) {
                    busSource.setText("Indore");
                    busDestination.setText("Bhopal");
                    busDate.setText("20181026");

                } else {

                    busSource.setText("");
                    busDestination.setText("");
                    busDate.setText("");

                }
            }
        });

        //flight intent list
        flightSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Places.this, "Flight Source", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(Places.this, SearchActivityFlight.class), 103);
            }
        });

        flightDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Places.this, "Flight Destination", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(Places.this, SearchFlightActivity2.class), 103);
            }
        });
        showDate(year, month + 1, day);

        //train intent list
        trainSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Places.this, "Train Source", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(Places.this, SearchActivityTrain.class), 104);
            }
        });
        trainDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Places.this, "Train Destination", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(Places.this, SearchActivityTrain2.class), 104);
            }
        });
        showDate(year, month + 1, day);

        //bus intent list
        busSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Places.this, "Bus Source", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(Places.this, SearchActivityBus.class), 105);
            }
        });
        busDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Places.this, "Bus Destination", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(Places.this, SearchActivityBus2.class), 105);
            }
        });
        showDate(year, month + 1, day);


        //Intent Call on Button Click For Flight,Bus,Train Results
        buttonShowFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String source = flightSource.getText().toString();
                String destination = flightDestination.getText().toString();
                String date = flightDate.getText().toString();
                boolean economyflight = false, businessflight = false;
                if (economyClass.isChecked()) {
                    economyflight = true;
                }
                if (businessClass.isChecked()) {
                    businessflight = true;
                }
//                String adults = flightAdults.getText().toString();
//                String children = flightChildren.getText().toString();
                String adults = flightAdultsnumber[0];
                String children = flightChildrenNumber[0];
                if (children.equals("")) {
                    children = 0 + "";
                }
                if ((source.equalsIgnoreCase("Source") || destination.equalsIgnoreCase("Destination")
                        || date.equalsIgnoreCase("") || !(Integer.parseInt(adults) > 0) || !(Integer.parseInt(children) >= 0))) {
                    Toast.makeText(Places.this, "Please Enter The Remaining Field", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(Places.this, FlightActivity.class).putExtra("flightSource", source)
                            .putExtra("flightDestination", destination).putExtra("flightDate", date)
                            .putExtra("economyClass", economyflight).putExtra("businessClass", businessflight)
                            .putExtra("flightAdult", adults).putExtra("flightChildren", children)
                            .putExtra("dFAFlightSource", dFAFlightSource)
                            .putExtra("dFAFlightDestination", dFAFlightDestination));

                }
            }
//            }
        });

        buttonShowTrains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String source = trainSource.getText().toString();
                String destination = trainDestination.getText().toString();
                String date = trainDate.getText().toString();

                if ((source.equalsIgnoreCase("Source") || destination.equalsIgnoreCase("Destination") || date.equalsIgnoreCase(""))) {
                    Toast.makeText(Places.this, "Please Enter The Remaining Field", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(Places.this, TrainActivity.class).putExtra("trainSource", trainSource.getText().toString())
                            .putExtra("trainDestination", trainDestination.getText().toString()).putExtra("trainDate", trainDate.getText().toString())
                            .putExtra("trainSourceForTPA", dFATrainSource).putExtra("trainDestinationForTPA", dFATrainDestination));
                }
            }
        });

        buttonShowBuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String source = busSource.getText().toString();
                String destination = busDestination.getText().toString();
                String date = busDate.getText().toString();

                if ((source.equalsIgnoreCase("Source") || destination.equalsIgnoreCase("Destination"))) {
                    Toast.makeText(Places.this, "Please Enter The Remaining Field", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(Places.this, BusActivity.class)
                            .putExtra("busSource", busSource.getText().toString())
                            .putExtra("busDestination", busDestination.getText().toString())
                            .putExtra("busDate", busDate.getText().toString()));

                }
            }
        });

        moreDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Places.this, PlaceDescription.class).putExtra("placeName", placeName.getText().toString()));
            }
        });


        //Intent Data from MainActivity Search
        String placeId = "123456789", nameFromIntent = "No Description Available", addressFromIntent = "No Description Available";
        float ratingFromIntent = 0;
        LatLng latlngFromIntent = new LatLng(21.7679, 78.8718);
        Bundle placeDataFromIntent = getIntent().getExtras();
        if (placeDataFromIntent != null) {
            nameFromIntent = (String) placeDataFromIntent.get("placeName");
            ratingFromIntent = (float) placeDataFromIntent.get("placeRating");
            addressFromIntent = (String) placeDataFromIntent.get("placeAddress");
            latlngFromIntent = (LatLng) placeDataFromIntent.get("placeLatLng");
            placeId = (String) placeDataFromIntent.get("placeId");

            Log.d("yrwhatisthis", placeId.toString());

        }


        //Setting shared preference
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lastviewedname", nameFromIntent.toString());
        editor.putFloat("lastviewedrating", ratingFromIntent);
        editor.putString("lastviewedaddress", addressFromIntent.toString());
        editor.putString("lastviewedlatitude", latlngFromIntent.latitude + "");
        editor.putString("lastviewedlongitude", latlngFromIntent.longitude + "");
        editor.putString("lastviewedplaceid", placeId.toString());
        editor.commit(); // commit changes

        //Setting Data To Views
        placeName.setText(nameFromIntent);
        placeAddress.setText(addressFromIntent);
        String lat = "" + latlngFromIntent.latitude;
        String lng = "" + latlngFromIntent.longitude;
        latLng = latlngFromIntent;
        this.placeId = placeId;


        //Weather API call using Retrofit

        if (API_KEY_WEATHER.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> weatherCall = apiService.getWeatherReport(API_KEY_WEATHER, lat, lng, 35, "accurate");
        weatherCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                int statusCode = response.code();
                Log.d("statusMessageWeather", response.message());
                List<Weather> weather = response.body().getResults();
//                Log.d("kitteaaye",weather.toString());
                recyclerView.setAdapter(new WeatherAdapter(weather, R.layout.list_weather_icon, getApplicationContext()));
                SnapHelper helper = new LinearSnapHelper();
                helper.attachToRecyclerView(recyclerView);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(Places.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.toString());
            }
        });


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        //Points Of Interest API call using JSON Parsing Manual

        new PointOfInterestAsyncTask().execute();

//        RecyclerView recyclerViewForPOI = (RecyclerView) findViewById(R.id.pointofinterestrecyclerview);
//        PointOfInterestResponse response = null;
//        try {
//
//
//            String json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=point_of_interest_" + placeName.getText().toString() + "&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE").ignoreContentType(true).execute().body();
//            JSONObject obj = new JSONObject(json);
//            response = PointOfInterestResponse.fromJson(obj);
//            Log.d("jsonstring", json);
//
//            recyclerViewForPOI.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//            if (response.getStatus().equalsIgnoreCase("ZERO_RESULTS")) {
//
//                json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=point_of_interest_"
//                        + placeName.getText().toString() + "_madhya_pradesh&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE")
//                        .ignoreContentType(true).execute().body();
//
//                obj = new JSONObject(json);
//                response = PointOfInterestResponse.fromJson(obj);
//                Log.d("jsonstring", json);
//                recyclerViewForPOI = (RecyclerView) findViewById(R.id.pointofinterestrecyclerview);
//                recyclerViewForPOI.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//
//            }
//            List<PointOfInterestFirstStep> point = response.getList();
//            recyclerViewForPOI.setAdapter(new PointOfInterestAdapter(point, R.layout.list_item_poi, getApplicationContext()));
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//        final List<PointOfInterestFirstStep> responsible = response.getList();
//        recyclerViewForPOI.addOnItemTouchListener(
//                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForPOI, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        // do whatever
//                        ImageView img = (ImageView) view.findViewById(R.id.poi_photo);
//                        Bitmap draw = ((BitmapDrawable) img.getDrawable()).getBitmap();
//                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                        draw.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//                        byte[] ba = bao.toByteArray();
//                        String imageImage = Base64.encodeToString(ba, Base64.DEFAULT);
//                        new DescribeAllAsyncTask().execute(responsible.get(position).getPoiPlaceID(), imageImage
//                                , responsible.get(position).getPoiRating(), "poi");
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

        /*dum*/

        //Hotels API Using JSoup

        new HotelAsyncTask().execute();

//        PointOfInterestResponse response2 = null;
//        RecyclerView recyclerViewForHotels = (RecyclerView) findViewById(R.id.hotel_recycler_view);
//        try {
//
//
//            String json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=hotels_in_" + placeName.getText().toString() + "&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE").ignoreContentType(true).execute().body();
//            JSONObject obj = new JSONObject(json);
//            response2 = PointOfInterestResponse.fromJson(obj);
//            Log.d("jsonstring", json);
//
//            recyclerViewForHotels.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//            if (response2.getStatus().equalsIgnoreCase("ZERO_RESULTS")) {
//
//                json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=hotels_in_"
//                        + placeName.getText().toString() + "_madhya_pradesh&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE")
//                        .ignoreContentType(true).execute().body();
//
//                obj = new JSONObject(json);
//                response2 = PointOfInterestResponse.fromJson(obj);
//                Log.d("jsonstring", json);
//                recyclerViewForHotels = (RecyclerView) findViewById(R.id.pointofinterestrecyclerview);
//                recyclerViewForHotels.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//
//            }
//            List<PointOfInterestFirstStep> point = response2.getList();
//            recyclerViewForHotels.setAdapter(new PointOfInterestAdapter(point, R.layout.list_item_poi, getApplicationContext()));
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//        final List<PointOfInterestFirstStep> responsible2 = response2.getList();
//        recyclerViewForHotels.addOnItemTouchListener(
//                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForHotels, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        // do whatever
//                        ImageView img = (ImageView) view.findViewById(R.id.poi_photo);
//                        Bitmap draw = ((BitmapDrawable) img.getDrawable()).getBitmap();
//                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                        draw.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//                        byte[] ba = bao.toByteArray();
//                        String imageImage = Base64.encodeToString(ba, Base64.DEFAULT);
//                        new DescribeAllAsyncTask().execute(responsible2.get(position).getPoiPlaceID(), imageImage, responsible2.get(position).getPoiRating(), "hotel");
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

        /*dum*/

        //Oyo Rooms from normal JSON fetch

        new OyoRoomsAsyncTask().execute();
//        PointOfInterestResponse response3 = null;
//        RecyclerView recyclerViewForOyoRooms = (RecyclerView) findViewById(R.id.oyo_room_recycler_view);
//        try {
//
//
//            String json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=oyo_rooms_in_" + placeName.getText().toString() + "&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE").ignoreContentType(true).execute().body();
//            JSONObject obj = new JSONObject(json);
//            response3 = PointOfInterestResponse.fromJson(obj);
//            Log.d("jsonstring", json);
//
//            recyclerViewForOyoRooms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//            if (response3.getStatus().equalsIgnoreCase("ZERO_RESULTS")) {
//
//                json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=oyo_rooms_in_"
//                        + placeName.getText().toString() + "_madhya_pradesh&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE")
//                        .ignoreContentType(true).execute().body();
//
//                obj = new JSONObject(json);
//                response3 = PointOfInterestResponse.fromJson(obj);
//                Log.d("jsonstring", json);
//                recyclerViewForOyoRooms = (RecyclerView) findViewById(R.id.pointofinterestrecyclerview);
//                recyclerViewForOyoRooms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//
//            }
//            List<PointOfInterestFirstStep> point = response3.getList();
//            recyclerViewForOyoRooms.setAdapter(new PointOfInterestAdapter(point, R.layout.list_item_poi, getApplicationContext()));
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        final List<PointOfInterestFirstStep> responsible3 = response3.getList();
//        recyclerViewForOyoRooms.addOnItemTouchListener(
//                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForOyoRooms, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        ImageView img = (ImageView) view.findViewById(R.id.poi_photo);
//                        Bitmap draw = ((BitmapDrawable) img.getDrawable()).getBitmap();
//                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//                        draw.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//                        byte[] ba = bao.toByteArray();
//                        String imageImage = Base64.encodeToString(ba, Base64.DEFAULT);
//                        new DescribeAllAsyncTask().execute(responsible3.get(position).getPoiPlaceID(), imageImage, responsible3.get(position).getPoiRating(), "oyo");
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

        /*dum*/

        //DescriptionApiCall using JSoup

        new DescriptionAsyncTask().execute();
//        try {
//            Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + placeName.getText().toString()).get();
//            Elements paragraphs = doc.select("p");
//            ArrayList<String> descript = new ArrayList<>();
//            descript = cleanDescription(descript);
//            for (Element p : paragraphs)
//                descript.add(p.text());
//            description.setText(descript.get(0));
//            Singleton.getInstanceSingleton().setList(descript);
//        } catch (Exception ex) {
//            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
//        }

        /*dum*/

        //Zomato API call using Retrofit

        new RestaurantAsyncTask().execute();
//        RestaurantResponse response4 = null;
//        RecyclerView recyclerViewForRestaurant = (RecyclerView) findViewById(R.id.restaurant_recycler_view);
//        List<RestaurantFirstStep> restaurant = null;
//        try {
//
//
//            recyclerViewForRestaurant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//            String doc = Jsoup.connect("https://developers.zomato.com/api/v2.1/collections?count=25&lat=" + latLng.latitude + "&lon=" + latLng.longitude)
//                    .header("user-key", "1714b7ed91caa915f9ddbb1785618226").ignoreContentType(true).execute().body();
//            Log.d("restaurantkilist", doc);
//
//            JSONObject obj1 = new JSONObject(doc);
//            response4 = RestaurantResponse.fromJson(obj1);
//            if (response4.getList().size() > 0) {
//                restaurant = response4.getList();
//                RestaurantFirstStep restaurantList = new RestaurantFirstStep();
//                restaurantList.setRestaurant(new RestaurantSecondStep());
//                restaurantList.getRestaurant().setCollectionID("bogus");
//                restaurantList.getRestaurant().setDescription("");
//                restaurantList.getRestaurant().setImageURL("https://firebasestorage.googleapis.com/v0/b/sajag-travel-buddy.appspot.com/o/customRestaurant.png?alt=media&token=4fbd4e6d-5f40-4965-b0b9-59779c935157");
//                restaurantList.getRestaurant().setTitle("");
//                restaurantList.getRestaurant().setRedirectURL("#");
//                restaurant.add(0, restaurantList);
//                Log.d("jsonstring", restaurant.get(0).getRestaurant().getDescription());
//                recyclerViewForRestaurant.setAdapter(new RestaurantAdapter(restaurant, R.layout.list_item_restaurant, getApplicationContext()));
//
//
//            } else {
//                //setVisible false for all the components of zomato
//
//
//            }
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//        final List<RestaurantFirstStep> restaurant1 = restaurant;
//        recyclerViewForRestaurant.addOnItemTouchListener(
//                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForRestaurant, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        if (position == 0) {
//                            startActivity(new Intent(Places.this, RestaurantActivity.class).putExtra("lat", latLng.latitude + "")
//                                    .putExtra("lon", latLng.longitude + ""));
//                        } else {
//                            startActivity(new Intent(Places.this, RestaurantInformation.class)
//                                    .putExtra("shareURL", restaurant1.get(position).getRestaurant()
//                                            .getShareURL()));
//                        }
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

        /*dum*/

        //Twitter API
        new TwitterAsyncTask().execute();

//        TwitterFactory factory = new TwitterFactory();
//        AccessToken accessToken = loadAccessToken(0);
//        Twitter twitter = factory.getInstance();
//        twitter.setOAuthConsumer("3e8s3EPGRlmPZ7QuvR9vq1MOK", "DlHO3dRhYqxdWZYdVncjbVNPzKq5dSLuST4eO9MgSvZzrpzohj");
//        twitter.setOAuthAccessToken(accessToken);
//        RecyclerView recyclerViewForTwitter = (RecyclerView) findViewById(R.id.news_recycler_view);
//        List<Status> news = new ArrayList<>();
//        try {
//            recyclerViewForTwitter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//            Query query = new Query(placeName.getText().toString())
//                    .geoCode(new GeoLocation(latLng.latitude, latLng.longitude), 10, "km");
//            QueryResult result = twitter.search(query);
//            if (result.getTweets().size() != 0) {
//                for (Status status : result.getTweets()) {
//                    Log.d("ichcrude", "@" + status.getUser().getScreenName() + ":" + status.getText());
//                    news.add(status);
//                }
//                news = cleanNews(news);
//            } else {
//
//                query = new Query("#news");
//                result = twitter.search(query);
//                for (Status status : result.getTweets()) {
//                    Log.d("ichcrude", "@" + status.getUser().getScreenName() + ":" + status.getText());
//                    news.add(status);
//                }
//                news = cleanNews(news);
//
//            }
//            recyclerViewForTwitter.setAdapter(new NewsAdapter(news, R.layout.list_item_news, getApplicationContext()));
//        } catch (TwitterException | NullPointerException e) {
//            e.printStackTrace();
//        }


        //Google Place Images
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        //Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
        //calling full map funtion
        openFullMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Places.this, MapActivity.class).putExtra("latlng", latLng));
            }
        });


        //Bottom Sheet Code
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        ArcMotion arcMotion = new ArcMotion();
        Explode slideTransition = new Explode();
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }


    public void showBottomSheetDialogFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("placeName", placeName.getText().toString());
        bundle.putParcelable("placeLatLng", latLng);
        BottomSheetFragmentForPlaces bottomSheetFragment = new BottomSheetFragmentForPlaces();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 103) {
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("datasourcevalue1") != null) {
                    String strEditText = data.getStringExtra("datasourcevalue1");
                    Log.d("isitwhatiordered", strEditText);
                    dFAFlightSource = data.getStringExtra("datasourcevaluecity1");
                    flightSource.setText(strEditText);
                }
                if (data.getStringExtra("datasourcevalue2") != null) {
                    String strEditText = data.getStringExtra("datasourcevalue2");
                    Log.d("isitwhatiorderedagain", strEditText);
                    dFAFlightDestination = data.getStringExtra("datasourcevaluecity2");
                    flightDestination.setText(strEditText);
                }
            }
        }
        if (requestCode == 104) {
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("datasourcevalue3") != null) {

                    String strEditText = data.getStringExtra("datasourcevalue3");
                    String trainSourceFromIntent = data.getStringExtra("trainSource");
                    Log.d("isitwhatiordered", strEditText);
                    dFATrainSource = trainSourceFromIntent;
                    trainSource.setText(strEditText);

                }
                if (data.getStringExtra("datasourcevalue4") != null) {

                    String strEditText = data.getStringExtra("datasourcevalue4");
                    String trainDestinationFromIntent = data.getStringExtra("trainDestination");
                    Log.d("isitwhatiordered", strEditText);
                    dFATrainDestination = trainDestinationFromIntent;
                    trainDestination.setText(strEditText);

                }
            }
        }
        if (requestCode == 105) {
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("datasourcevalue5") != null) {

                    String strEditText = data.getStringExtra("datasourcevalue5");
                    String busSourceFromIntent = data.getStringExtra("busSource");
                    Log.d("isitwhatiordered", strEditText);
                    dFABusSource = busSourceFromIntent;
                    busSource.setText(strEditText);
                }
                if (data.getStringExtra("datasourcevalue6") != null) {
                    String strEditText = data.getStringExtra("datasourcevalue6");
                    String busDestinationFromIntent = data.getStringExtra("busDestination");
                    Log.d("isitwhatiordered", strEditText);
                    dFABusDestination = busDestinationFromIntent;
                    busDestination.setText(strEditText);
                }

            }
        }
    }


    private static AccessToken loadAccessToken(int useId) {
        String token = "855121612286435328-hZQ85juOSFARyxz3HhiokKxFTvjcxHa";
        String tokenSecret = "gDDDo59RsgX9VupMTnfGHepDONnIpKEDuN8HXc6SYBja4";
        return new AccessToken(token, tokenSecret);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        //Test Me
        googleMap.addMarker(new MarkerOptions().position(latLng).title(placeName.getText().toString()));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(10.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);

        try {
            com.google.android.gms.location.places.Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId)
                    .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
                        @Override
                        public void onResult(PlacePhotoMetadataResult placePhotoMetadataResult) {
                            if (placePhotoMetadataResult.getStatus().isSuccess()) {
                                PlacePhotoMetadataBuffer photoMetadata = placePhotoMetadataResult.getPhotoMetadata();
                                int photoCount = photoMetadata.getCount();
//                     for (int i = 0; i<photoCount; i++) {
                                try {
                                    final PlacePhotoMetadata placePhotoMetadata = photoMetadata.get(0);
                                    final String photoDetail = placePhotoMetadata.toString();
                                    placePhotoMetadata.getScaledPhoto(mGoogleApiClient, 500, 500)
                                            .setResultCallback(new ResultCallback<PlacePhotoResult>() {
                                                @Override
                                                public void onResult(PlacePhotoResult placePhotoResult) {
                                                    if (placePhotoResult.getStatus().isSuccess()) {
                                                        Bitmap placePhoto = placePhotoResult.getBitmap();
                                                        placeMainImage.setImageBitmap(placePhoto);


                                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                        placePhoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                                        byte[] b = baos.toByteArray();

                                                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = pref.edit();
                                                        editor.putString("lastviewedimage", encodedImage);
                                                        editor.commit();

                                                        Log.d(TAG, photoDetail);
                                                    } else {
                                                        Log.e(TAG, "Photo " + photoDetail + " failed to load");
                                                    }
                                                }
                                            });
                                } catch (Exception ex) {
                                    Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
                                }
//                    }
                                photoMetadata.release();
                            } else {
                                Log.e(TAG, "No photos returned");
                            }
                        }
                    });

        } catch (Exception ex) {
            Picasso.with(Places.this).load(R.drawable.image_not_found).fit().centerCrop().into(placeMainImage);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public static ArrayList<String> cleanDescription(ArrayList<String> list) {
        String desc = "";
        for (int i = 0; i < list.size(); i++) {
            String status = list.get(i);
            if (status.length() < 50) {
                list.remove(i);
            }
        }

        return list;
    }

    public static List<Status> cleanNews(List<Status> list) {
        String news = "";
        for (int i = 0; i < list.size(); i++) {
            Status status = list.get(i);
            news = status.getText();
            Log.d("language", status.getLang());
            if (status.getLang().equalsIgnoreCase("it") || status.getLang().equalsIgnoreCase("ru") || status.getLang().equalsIgnoreCase("ja") || status.getLang().equalsIgnoreCase("fr") || status.getLang().equalsIgnoreCase("zh-cn") || status.getLang().equalsIgnoreCase("zh-tw") || news.contains("escort") || news.contains("porn") || (news.contains("hot") && news.contains("sex"))) {
                list.remove(i);
            }
        }

        return list;
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setDate2(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setDate3(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        String dayparse = day + "";
        String monthparse = month + "";
        if (day < 10) {
            dayparse = "0" + day;
        }
        if (month < 10) {
            monthparse = "0" + month;
        }
        Log.d("datevalue", day + "  " + month);
        flightDate.setText(new StringBuilder().append(year).append(monthparse).append(dayparse));
        trainDate.setText(new StringBuilder().append(dayparse + "-").append(monthparse + "-").append(year));
        busDate.setText(new StringBuilder().append(year).append(monthparse).append(dayparse));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onToggleClickedOnDescription(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        ToggleButton v = (ToggleButton) view;
        Drawable draw1 = getResources().getDrawable(R.drawable.save_that_activated_on_click);
        Drawable draw2 = getResources().getDrawable(R.drawable.save_that_deactivated);

        if (on) {
            // Enable vibrate
            v.setBackground(getDrawable(R.drawable.rounded_border_button));
            v.setTextColor(Color.WHITE);

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(Places.this);
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setTitle("Select the Plan you want to Add Place to");
            builder1.setView(R.layout.bottom_sheet_fragment_select_plan_layout);
            final AlertDialog alert11 = builder1.create();

            alert11.show();
            RecyclerView travelPlanRecyclerView;
            final List<TravelPlan> plans = new ArrayList<>();
            final SharedPreferences pref;

            travelPlanRecyclerView = (RecyclerView) alert11.findViewById(R.id.bottom_sheet_fragment_for_places_recycler_view);
            travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));


            pref = getApplicationContext().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
            Gson gson = new Gson();
            Set<String> planFromSharedPref = pref.getStringSet("plans", null);

            if (planFromSharedPref != null) {
                Iterator it = planFromSharedPref.iterator();
                while (it.hasNext()) {
                    plans.add(gson.fromJson((String) it.next(), TravelPlan.class));
                    Log.d("plans", plans.size() + "");
                }
            }

            if (plans.isEmpty()) {

                plans.add(new TravelPlan("No Available Plan", "Add", "New", "", "", 123456789, new OverAllDataForSavingInTPA()));
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, Places.this, Places.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                alert11.dismiss();
                                startActivity(new Intent(Places.this, TravelPlanningAssistance.class));

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

            } else {
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, Places.this, Places.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                alert11.dismiss();


                                int gotIt = 0;

                                TravelPlan plan = plans.get(position);
                                List<SinglePlaceSavedInTPA> list = plan.getDataForSavingInTPA().getSavedPlaces();
                                if (list != null) {
                                    Log.d("kamyaab", "list isnt null annd list size is" + list.size());

                                    for (int i = 0; i < list.size(); i++) {
                                        SinglePlaceSavedInTPA place = list.get(i);
                                        String placeNameTemp = place.getPlaceName();
//                                            Log.d("kamyaab", placeName + "another name" + cityIn);
                                        if (placeNameTemp.equalsIgnoreCase(placeName.getText().toString())) {

                                            ArrayList<String> step = new ArrayList<>();
                                            step.addAll(Singleton.getList());
                                            list.get(i).setWikiDescribe(step);
                                            Toast.makeText(Places.this, "Description added to Plan" + plan.getPlanName() + "under city" + placeName.getText().toString(), Toast.LENGTH_LONG).show();
                                            gotIt = 1;

                                        }
                                    }
                                    plan.getDataForSavingInTPA().setSavedPlaces(list);
                                    plans.remove(position);
                                    plans.add(position, plan);

                                } else {
                                    plan.getDataForSavingInTPA().setSavedPlaces(new ArrayList<SinglePlaceSavedInTPA>());
                                }
                                if (gotIt == 0) {


                                    SinglePlaceSavedInTPA singlePlaceSavedInTPA = new SinglePlaceSavedInTPA(
                                            latLng,
                                            placeName.getText().toString()
                                            , new ArrayList<PointOfInterestFirstStep>()
                                            , new ArrayList<PointOfInterestFirstStep>()
                                            , new ArrayList<PointOfInterestFirstStep>()
                                            , new ArrayList<SingleRestaurantFirstStep>()
                                            , new ArrayList<String>()
                                            , new ArrayList<Status>()
                                    );

                                    ArrayList<String> step = singlePlaceSavedInTPA.getWikiDescribe();
                                    step.addAll(Singleton.getList());
                                    Toast.makeText(Places.this, "Description added in Plan" + plan.getPlanName() + "under city" + placeName.getText().toString()
                                            , Toast.LENGTH_LONG).show();

                                    singlePlaceSavedInTPA.setWikiDescribe(step);

                                    plan.getDataForSavingInTPA().getSavedPlaces().add((list.size() - 1), singlePlaceSavedInTPA);
                                    Log.d("kamyaab", "silsila chalta rahe");

                                    plans.remove(position);
                                    plans.add(position, plan);

                                }


                                Log.d("kamyaab", plans.get(position).getDataForSavingInTPA().getSavedPlaces().get(0).getPlaceName() + " " + plans.get(position).getDataForSavingInTPA().getSavedPlaces().size());
                                SharedPreferences.Editor prefsEditor = pref.edit();
                                Iterator it = plans.iterator();
                                HashSet<String> set = new HashSet();
                                while (it.hasNext()) {
                                    Gson gson = new Gson();
                                    set.add(gson.toJson(it.next()));
                                    prefsEditor.putStringSet("plans", set);
                                    prefsEditor.commit();
                                }

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

            }

        } else {
            // Disable vibrate
            v.setBackground(getDrawable(R.drawable.rounded_border_edittext));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                v.setTextColor(getColor(R.color.colorPrimaryDark));
            }
        }
    }

    @Override
    public void onMoreOptionMenuClicked(int position, View view) {

    }

    @Override
    public void onEverythingElseClicked(int position, View view) {

    }


    public class DescribeAllAsyncTask extends AsyncTask<String, String, String> {

        Dialog dialog = new Dialog(Places.this);
        View v;

        public DescribeAllAsyncTask(View v) {
            this.v = v;
        }

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setTitle("Loading...");
            dialog.show();
            super.onPreExecute();
        }

        DescribeMeEverythingResponse response;
        String imageUrl;
        String describeAllPlaceID, placeRating;
        String typeOfEstablishment;
        String lat, lng;
        String locationAddress, poiName;
        String photoReference;

        @Override
        protected String doInBackground(String... strings) {
            try {
                imageUrl = strings[1];
                describeAllPlaceID = strings[0];
                placeRating = strings[2];
                typeOfEstablishment = strings[3];
                lat = strings[4];
                lng = strings[5];
                poiName = strings[6];
                locationAddress = strings[7];
                photoReference = strings[8];

                String json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + describeAllPlaceID + "&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE").ignoreContentType(true).execute().body();////AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE
                JSONObject obj = null;
                obj = new JSONObject(json);
                response = DescribeMeEverythingResponse.fromJson(obj);
                Log.d("jsonstring", json);
                Log.d("imageurl", imageUrl);
            } catch (NullPointerException | JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            if (response.getDescribe() != null) {
                Log.d("postexecute", response.getDescribe().getName());

                View view = v.findViewById(R.id.poi_photo);
                ActivityCompat.startActivity(Places.this,
                        new Intent(Places.this, DescribeMeEverything.class)
                                .putExtra("myobject", response).putExtra("imageurl", imageUrl)
                                .putExtra("placerating", placeRating).putExtra("typeOfEstablishment", typeOfEstablishment)
                                .putExtra("cityIn", placeName.getText().toString()).putExtra("locationAddress", locationAddress)
                                .putExtra("poiName", poiName).putExtra("describeAllPlaceId", describeAllPlaceID)
                                .putExtra("latlng", new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                                .putExtra("photoReference", photoReference)
                                .putExtra("placeLatLng", latLng)
                        ,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                Places.this,
                                new Pair<>(view, getString(R.string.transition_image)))
                                .toBundle());
//
//                startActivity(new Intent(Places.this, DescribeMeEverything.class)
//                        .putExtra("myobject", response).putExtra("imageurl", imageUrl)
//                        .putExtra("placerating", placeRating).putExtra("typeOfEstablishment", typeOfEstablishment)
//                        .putExtra("cityIn", placeName.getText().toString()).putExtra("locationAddress", locationAddress)
//                        .putExtra("poiName", poiName).putExtra("describeAllPlaceId", describeAllPlaceID)
//                        .putExtra("latlng", new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));

                super.onPostExecute(s);
            } else {
                Toast.makeText(Places.this, "API Not Working" + response.getStatus(), Toast.LENGTH_LONG).show();
            }
        }
    }

    List<Status> news = null;

    public class TwitterAsyncTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }


        RecyclerView recyclerViewForTwitter = (RecyclerView) findViewById(R.id.news_recycler_view);


        @Override
        protected String doInBackground(String... strings) {
            try {
                TwitterFactory factory = new TwitterFactory();
                AccessToken accessToken = loadAccessToken(0);
                Twitter twitter = factory.getInstance();
                twitter.setOAuthConsumer("3e8s3EPGRlmPZ7QuvR9vq1MOK", "DlHO3dRhYqxdWZYdVncjbVNPzKq5dSLuST4eO9MgSvZzrpzohj");
                twitter.setOAuthAccessToken(accessToken);

                news = new ArrayList<>();
//                recyclerViewForTwitter.setLayoutManager(new LinearLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));
                Query query = new Query(placeName.getText().toString())
                        .geoCode(new GeoLocation(latLng.latitude, latLng.longitude), 10, "km");
                QueryResult result = twitter.search(query);
                if (result.getTweets().size() != 0) {
                    for (twitter4j.Status status : result.getTweets()) {
                        Log.d("ichcrude", "@" + status.getUser().getScreenName() + ":" + status.getText());
                        news.add(status);
                    }
                    news = cleanNews(news);
                } else {

                    query = new Query("#news");
                    result = twitter.search(query);
                    for (twitter4j.Status status : result.getTweets()) {
                        Log.d("ichcrude", "@" + status.getUser().getScreenName() + ":" + status.getText());
                        news.add(status);
                    }
                    news = cleanNews(news);
                }

            } catch (TwitterException | NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
//            recyclerViewForTwitter.setLayoutManager(new LinearLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewForTwitter.setLayoutManager(new CenterZoomLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewForTwitter.setAdapter(new NewsAdapter(news, R.layout.list_item_news, getApplicationContext()));
//            if(news==null){
            tweetsSave.setVisibility(View.INVISIBLE);
//            }
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyclerViewForTwitter);
        }
    }

    public class DescriptionAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        ArrayList<String> descript = new ArrayList<>();

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + placeName.getText().toString()).get();
                Elements paragraphs = doc.select("p");

                for (Element p : paragraphs)
                    descript.add(p.text());

                descript = cleanDescription(descript);

            } catch (IOException | UncheckedIOException ex) {
                descript.add("No Description Available");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (descript.isEmpty()) {
                descript.add(0, "No Description Available");
                description.setText(descript.get(0));
            } else {
                description.setText(descript.get(0));
            }
            Singleton.getInstanceSingleton().setList(descript);
            dialog.dismiss();

        }
    }

    public class OyoRoomsAsyncTask extends AsyncTask<String, String, String> {

        PointOfInterestResponse response3 = null;
        RecyclerView recyclerViewForOyoRooms = (RecyclerView) findViewById(R.id.oyo_room_recycler_view);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {
            try {


                String json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=oyo+rooms+in+" + placeName.getText().toString() + "&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE").ignoreContentType(true).execute().body();
                JSONObject obj = new JSONObject(json);
                response3 = PointOfInterestResponse.fromJson(obj);
                Log.d("jsonstring", json);


            } catch (IOException | JSONException e) {
                Toast.makeText(Places.this, "Trying Hard but Internet is really slow", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerViewForOyoRooms.setLayoutManager(new CenterZoomLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));

            List<PointOfInterestFirstStep> point = response3.getList();
            recyclerViewForOyoRooms.setAdapter(new PointOfInterestAdapter(point, R.layout.list_item_poi, getApplicationContext()));

            final List<PointOfInterestFirstStep> responsible3 = response3.getList();
            recyclerViewForOyoRooms.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), recyclerViewForOyoRooms, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ImageView img = (ImageView) view.findViewById(R.id.poi_photo);
                            Bitmap draw = ((BitmapDrawable) img.getDrawable()).getBitmap();
                            ByteArrayOutputStream bao = new ByteArrayOutputStream();
                            draw.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                            byte[] ba = bao.toByteArray();
                            String imageImage = Base64.encodeToString(ba, Base64.DEFAULT);
                            new DescribeAllAsyncTask(view).execute(responsible3.get(position).getPoiPlaceID(), imageImage
                                    , responsible3.get(position).getPoiRating()
                                    , "oyo"
                                    , responsible3.get(position).getLoc().getLocation().getLat()
                                    , responsible3.get(position).getLoc().getLocation().getLng()
                                    , responsible3.get(position).getPoiName()
                                    , responsible3.get(position).getLocationAddress()
                                    , responsible3.get(position).getPhotos().get(0).getPhotoReference());

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyclerViewForOyoRooms);

        }
    }

    public class HotelAsyncTask extends AsyncTask<String, String, String> {

        PointOfInterestResponse response2 = null;
        RecyclerView recyclerViewForHotels = (RecyclerView) findViewById(R.id.hotel_recycler_view);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {
            try {


                String json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=hotels+in+" + placeName.getText().toString() + "&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE").ignoreContentType(true).execute().body();
                JSONObject obj = new JSONObject(json);
                response2 = PointOfInterestResponse.fromJson(obj);
                Log.d("jsonstring", json);


            } catch (IOException | JSONException e) {
                Toast.makeText(Places.this, "Trying Hard but Internet is really slow", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerViewForHotels.setLayoutManager(new CenterZoomLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));

            List<PointOfInterestFirstStep> point = response2.getList();
            recyclerViewForHotels.setAdapter(new PointOfInterestAdapter(point, R.layout.list_item_poi, getApplicationContext()));

            final List<PointOfInterestFirstStep> responsible2 = response2.getList();
            recyclerViewForHotels.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), recyclerViewForHotels, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever
                            ImageView img = (ImageView) view.findViewById(R.id.poi_photo);
                            Bitmap draw = ((BitmapDrawable) img.getDrawable()).getBitmap();
                            ByteArrayOutputStream bao = new ByteArrayOutputStream();
                            draw.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                            byte[] ba = bao.toByteArray();
                            String imageImage = Base64.encodeToString(ba, Base64.DEFAULT);
                            new DescribeAllAsyncTask(view).execute(responsible2.get(position).getPoiPlaceID(), imageImage
                                    , responsible2.get(position).getPoiRating()
                                    , "hotel"
                                    , responsible2.get(position).getLoc().getLocation().getLat()
                                    , responsible2.get(position).getLoc().getLocation().getLng()
                                    , responsible2.get(position).getPoiName()
                                    , responsible2.get(position).getLocationAddress()
                                    , responsible2.get(position).getPhotos().get(0).getPhotoReference());


                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyclerViewForHotels);

        }
    }

    public class PointOfInterestAsyncTask extends AsyncTask<String, String, String> {

        RecyclerView recyclerViewForPOI = (RecyclerView) findViewById(R.id.pointofinterestrecyclerview);
        PointOfInterestResponse response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {
            try {


                String json = Jsoup.connect("https://maps.googleapis.com/maps/api/place/textsearch/json?query=point+of+interest+" + placeName.getText().toString() + "&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE").ignoreContentType(true).execute().body();
                JSONObject obj = new JSONObject(json);
                response = PointOfInterestResponse.fromJson(obj);
                Log.d("jsonstring", json);


            } catch (IOException | JSONException e) {
                Toast.makeText(Places.this, "Trying Hard but Internet is really slow", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerViewForPOI.setLayoutManager(new CenterZoomLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));

            List<PointOfInterestFirstStep> point = response.getList();
            recyclerViewForPOI.setAdapter(new PointOfInterestAdapter(point, R.layout.list_item_poi, getApplicationContext()));

            final List<PointOfInterestFirstStep> responsible = response.getList();


            recyclerViewForPOI.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), recyclerViewForPOI, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever
                            ImageView img = (ImageView) view.findViewById(R.id.poi_photo);
                            Bitmap draw = ((BitmapDrawable) img.getDrawable()).getBitmap();
                            ByteArrayOutputStream bao = new ByteArrayOutputStream();
                            draw.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                            byte[] ba = bao.toByteArray();
                            String imageImage = Base64.encodeToString(ba, Base64.DEFAULT);

                            new DescribeAllAsyncTask(view).execute(responsible.get(position).getPoiPlaceID(), imageImage
                                    , responsible.get(position).getPoiRating()
                                    , "poi"
                                    , responsible.get(position).getLoc().getLocation().getLat()
                                    , responsible.get(position).getLoc().getLocation().getLng()
                                    , responsible.get(position).getPoiName()
                                    , responsible.get(position).getLocationAddress()
                                    , responsible.get(position).getPhotos().get(0).getPhotoReference());

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyclerViewForPOI);

        }
    }

    RestaurantResponse response4 = null;

    public class RestaurantAsyncTask extends AsyncTask<String, String, String> {

        RecyclerView recyclerViewForRestaurant = (RecyclerView) findViewById(R.id.restaurant_recycler_view);
        List<RestaurantFirstStep> restaurant = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerViewForRestaurant.setLayoutManager(new CenterZoomLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));

        }

        @Override
        protected String doInBackground(String... strings) {
            try {


                String doc = Jsoup.connect("https://developers.zomato.com/api/v2.1/collections?count=25&lat=" + latLng.latitude + "&lon=" + latLng.longitude)
                        .header("user-key", "1714b7ed91caa915f9ddbb1785618226").ignoreContentType(true).execute().body();
                Log.d("restaurantkilist", doc);

                JSONObject obj1 = new JSONObject(doc);
                response4 = RestaurantResponse.fromJson(obj1);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (response4 != null) {
                restaurant = response4.getList();
                RestaurantFirstStep restaurantList = new RestaurantFirstStep();
                restaurantList.setRestaurant(new RestaurantSecondStep());
                restaurantList.getRestaurant().setCollectionID("bogus");
                restaurantList.getRestaurant().setDescription("");
                restaurantList.getRestaurant().setImageURL("https://firebasestorage.googleapis.com/v0/b/sajag-travel-buddy.appspot.com/o/customRestaurant.png?alt=media&token=4fbd4e6d-5f40-4965-b0b9-59779c935157");
                restaurantList.getRestaurant().setTitle("");
                restaurantList.getRestaurant().setRedirectURL("#");
                restaurant.add(0, restaurantList);
                Log.d("jsonstring", restaurant.get(0).getRestaurant().getDescription());
                recyclerViewForRestaurant.setAdapter(new RestaurantAdapter(restaurant, R.layout.list_item_restaurant, getApplicationContext(), Places.this));


            } else {
                //setVisible false for all the components of zomato

            }

//            final List<RestaurantFirstStep> restaurant1 = restaurant;
//            recyclerViewForRestaurant.addOnItemTouchListener(
//                    new RecyclerItemClickListener(getApplicationContext(), recyclerViewForRestaurant, new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//
//                        }
//
//                        @Override
//                        public void onLongItemClick(View view, int position) {
//                            // do whatever
//                        }
//                    })
//            );
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(recyclerViewForRestaurant);

            super.onPostExecute(s);
        }
    }

    @Override
    public void onRestaurantShareButtonClick(int position, View view) {
        if (response4 != null) {
            if (position == 0) {
                startActivity(new Intent(Places.this, RestaurantActivity.class).putExtra("lat", latLng.latitude + "")
                        .putExtra("lon", latLng.longitude + "").putExtra("cityIn", placeName.getText().toString()));
            } else {
                RestaurantSecondStep res = response4.getList().get(position).getRestaurant();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this Zomato Collection " + res.getTitle().toUpperCase() + " - " + res.getDescription() + " " + res.getShareURL().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        }
    }

    @Override
    public void onEverythingElseClickedInRestaurant(int position, View view) {
        if (response4 != null) {
            if (position == 0) {
                startActivity(new Intent(Places.this, RestaurantActivity.class).putExtra("lat", latLng.latitude + "")
                        .putExtra("lon", latLng.longitude + "").putExtra("cityIn", placeName.getText().toString()));
            } else {
                startActivity(new Intent(Places.this, RestaurantInformation.class)
                        .putExtra("shareURL", response4.getList().get(position).getRestaurant()
                                .getShareURL()));
            }
        }
    }


//    public void onToggleClickedOnTweets(View view) {
//        boolean on = ((ToggleButton) view).isChecked();
//        ToggleButton v = (ToggleButton) view;
//        Drawable draw1 = getResources().getDrawable(R.drawable.save_that_activated_on_click);
//        Drawable draw2 = getResources().getDrawable(R.drawable.save_that_deactivated);
//
//        if (on) {
//            // Enable vibrate
//            v.setBackground(draw1);
//
//            final AlertDialog.Builder builder1 = new AlertDialog.Builder(Places.this);
//            builder1.setCancelable(true);
//            builder1.setNegativeButton(
//                    "Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//
//            builder1.setTitle("Select the Plan you want to Add Place to");
//            builder1.setView(R.layout.bottom_sheet_fragment_select_plan_layout);
//            final AlertDialog alert11 = builder1.create();
//
//            alert11.show();
//            RecyclerView travelPlanRecyclerView;
//            final List<TravelPlan> plans = new ArrayList<>();
//            final SharedPreferences pref;
//
//            travelPlanRecyclerView = (RecyclerView) alert11.findViewById(R.id.bottom_sheet_fragment_for_places_recycler_view);
//            travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(Places.this, LinearLayoutManager.HORIZONTAL, false));
//
//
//            pref = getApplicationContext().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
//            Gson gson = new Gson();
//            Set<String> planFromSharedPref = pref.getStringSet("plans", null);
//
//            if (planFromSharedPref != null) {
//                Iterator it = planFromSharedPref.iterator();
//                while (it.hasNext()) {
//                    plans.add(gson.fromJson((String) it.next(), TravelPlan.class));
//                    Log.d("plans", plans.size() + "");
//                }
//            }
//
//            if (plans.isEmpty()) {
//
//                plans.add(new TravelPlan("No Available Plan", "Add", "New", "", "", 123456789, new OverAllDataForSavingInTPA()));
//                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, Places.this, Places.this));
//
//                travelPlanRecyclerView.addOnItemTouchListener(
//                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//                                alert11.dismiss();
//                                startActivity(new Intent(Places.this, TravelPlanningAssistance.class));
//
//                            }
//
//                            @Override
//                            public void onLongItemClick(View view, int position) {
//                                // do whatever
//                            }
//                        })
//                );
//
//            } else {
//                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, Places.this, Places.this));
//
//                travelPlanRecyclerView.addOnItemTouchListener(
//                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int position) {
//
//                                alert11.dismiss();
//
//
//                                int gotIt = 0;
//
//                                TravelPlan plan = plans.get(position);
//                                List<SinglePlaceSavedInTPA> list = plan.getDataForSavingInTPA().getSavedPlaces();
//                                if (list != null) {
//                                    Log.d("kamyaab", "list isnt null annd list size is" + list.size());
//
//                                    for (int i = 0; i < list.size(); i++) {
//                                        SinglePlaceSavedInTPA place = list.get(i);
//                                        String placeNameTemp = place.getPlaceName();
////                                            Log.d("kamyaab", placeName + "another name" + cityIn);
//                                        if (placeNameTemp.equalsIgnoreCase(placeName.getText().toString())) {
//
//                                            List<Status> step = new ArrayList<>();
//                                            step.addAll(news);
//                                            list.get(i).setTweets(step);
//                                            Toast.makeText(Places.this, "Tweets added to Plan" + plan.getPlanName() + "under city" + placeName.getText().toString(), Toast.LENGTH_LONG).show();
//                                            gotIt = 1;
//
//                                        }
//                                    }
//                                    plan.getDataForSavingInTPA().setSavedPlaces(list);
//                                    plans.remove(position);
//                                    plans.add(position, plan);
//
//                                } else {
//                                    plan.getDataForSavingInTPA().setSavedPlaces(new ArrayList<SinglePlaceSavedInTPA>());
//                                }
//                                if (gotIt == 0) {
//
//
//                                    SinglePlaceSavedInTPA singlePlaceSavedInTPA = new SinglePlaceSavedInTPA(
//                                            placeName.getText().toString()
//                                            , new ArrayList<PointOfInterestFirstStep>()
//                                            , new ArrayList<PointOfInterestFirstStep>()
//                                            , new ArrayList<PointOfInterestFirstStep>()
//                                            , new ArrayList<SingleRestaurantFirstStep>()
//                                            , new ArrayList<String>()
//                                            , new ArrayList<Status>()
//                                    );
//
//                                    List<Status> step = singlePlaceSavedInTPA.getTweets();
//                                    step.addAll(news);
//                                    Toast.makeText(Places.this, "Tweets added in Plan" + plan.getPlanName() + "under city" + placeName.getText().toString()
//                                            , Toast.LENGTH_LONG).show();
//
//                                    singlePlaceSavedInTPA.setTweets(step);
//
//                                    plan.getDataForSavingInTPA().getSavedPlaces().add(singlePlaceSavedInTPA);
//                                    Log.d("kamyaab", "silsila chalta rahe");
//
//                                    plans.remove(position);
//                                    plans.add(position, plan);
//
//                                }
//
//
//                                Log.d("kamyaab", plans.get(position).getDataForSavingInTPA().getSavedPlaces().get(0).getPlaceName() + " " + plans.get(position).getDataForSavingInTPA().getSavedPlaces().size());
//                                SharedPreferences.Editor prefsEditor = pref.edit();
//                                Iterator it = plans.iterator();
//                                HashSet<String> set = new HashSet();
//                                while (it.hasNext()) {
//                                    Gson gson = new Gson();
//                                    set.add(gson.toJson(it.next()));
//                                    prefsEditor.putStringSet("plans", set);
//                                    prefsEditor.commit();
//                                }
//
//                            }
//
//                            @Override
//                            public void onLongItemClick(View view, int position) {
//                                // do whatever
//                            }
//                        })
//                );
//
//            }
//
//        } else {
//            // Disable vibrate
//            v.setBackground(draw2);
//        }
//    }
}

