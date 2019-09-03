package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sajagjain.sajagjain.majorproject.adapter.DescribeMeAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.DescribeMeEverythingResponse;
import com.sajagjain.sajagjain.majorproject.model.DescribeMeEverythingThirdStep;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestFirstStep;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestLocation;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestLocationSecond;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestPhotos;
import com.sajagjain.sajagjain.majorproject.model.SinglePlaceSavedInTPA;
import com.sajagjain.sajagjain.majorproject.model.SingleRestaurantFirstStep;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DescribeMeEverything extends AppCompatActivity implements TravelPlanAdapter.TravelPlanAdapterListener {

    TextView describeMeMonday, describeMeRating, describeMeName, describeMeAddress, describeMePhoneNo, describeMeOpenNow;
    TextView describeMeTuesday, describeMeWednesday, describeMeThursday, describeMeFriday, describeMeSunday, describeMeSaturday;
    ImageView describeMeImage;
    ToggleButton describeMeSaveThat;
    Button describeMeOpenWebSite;

    LatLng placeLatLng;
    String imageUrl;
    String placeRating;
    String typeOfEstablishment;
    String cityIn;
    String locationAddress, describeAllPlaceId;
    String placeNameWhichHasThisPoint;
    LatLng latLng;
    String photoReference;

    DescribeMeEverythingResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_me_everything);

        //Linking xml with java
        describeMeAddress = (TextView) findViewById(R.id.describe_me_address);
        describeMeImage = (ImageView) findViewById(R.id.poi_photo);
        describeMeSaveThat = (ToggleButton) findViewById(R.id.describe_me_save_that);
        describeMeName = (TextView) findViewById(R.id.describe_me_name);
        describeMePhoneNo = (TextView) findViewById(R.id.describe_me_phn_no);
        describeMeRating = (TextView) findViewById(R.id.describe_me_rating);
        describeMeMonday = (TextView) findViewById(R.id.describe_me_openinng_hours_monday);
        describeMeTuesday = (TextView) findViewById(R.id.describe_me_openinng_hours_tuesday);
        describeMeWednesday = (TextView) findViewById(R.id.describe_me_openinng_hours_wednesday);
        describeMeThursday = (TextView) findViewById(R.id.describe_me_openinng_hours_thursday);
        describeMeFriday = (TextView) findViewById(R.id.describe_me_openinng_hours_friday);
        describeMeSaturday = (TextView) findViewById(R.id.describe_me_openinng_hours_saturday);
        describeMeSunday = (TextView) findViewById(R.id.describe_me_openinng_hours_sunday);
        describeMeOpenNow = (TextView) findViewById(R.id.describe_me_open_now);
        describeMeOpenWebSite = (Button) findViewById(R.id.describe_me_open_website);

        //Value from Intent
        imageUrl = getIntent().getStringExtra("imageurl");
        placeRating = getIntent().getStringExtra("placerating");
        response = (DescribeMeEverythingResponse) getIntent().getSerializableExtra("myobject");
        typeOfEstablishment = getIntent().getStringExtra("typeOfEstablishment");
        cityIn = getIntent().getStringExtra("cityIn");
        locationAddress = getIntent().getStringExtra("locationAddress");
        latLng = getIntent().getParcelableExtra("latlng");
        placeNameWhichHasThisPoint = getIntent().getStringExtra("poiName");
        describeAllPlaceId = getIntent().getStringExtra("describeAllPlaceId");
        photoReference=getIntent().getStringExtra("photoReference");
        placeLatLng=getIntent().getParcelableExtra("placeLatLng");

        //Converting String to Bitmap
        if (!imageUrl.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(imageUrl, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            describeMeImage.setImageBitmap(bitmap);
            describeMeImage.setTag(0);
        } else {
            Picasso.with(DescribeMeEverything.this).load(R.drawable.no_image_found).into(describeMeImage);
            describeMeImage.setTag(0);
        }


        //Get Open Now Value
        String openNow = "( NA )";
        String monday = null, tuesday = null, wednesday = null, thursday = null, friday = null, saturday = null, sunday = null;
        if (response.getDescribe().getSecondStep() != null) {
            String openNowStatus = response.getDescribe().getSecondStep().getOpenNow();

            if (openNowStatus.equals("true")) {
                openNow = "( Open Now )";
            } else {
                openNow = "( Close Now )";
            }

            monday = response.getDescribe().getSecondStep().getWeekdaysInfoMonday();
            tuesday = response.getDescribe().getSecondStep().getWeekdaysInfoTuesday();
            wednesday = response.getDescribe().getSecondStep().getWeekdaysInfoWednesday();
            thursday = response.getDescribe().getSecondStep().getWeekdaysInfoThursday();
            friday = response.getDescribe().getSecondStep().getWeekdaysInfoFriday();
            saturday = response.getDescribe().getSecondStep().getWeekdaysInfoSaturday();
            sunday = response.getDescribe().getSecondStep().getWeekdaysInfoSunday();

        }
        //Assign values to Views
        describeMeRating.setText(placeRating);
        describeMeName.setText(response.getDescribe().getName());
        describeMeAddress.setText(response.getDescribe().getAddress());
        describeMePhoneNo.setText(response.getDescribe().getPhn_number());
        describeMeOpenNow.setText("Opening Hours  " + openNow);
        if (monday != null)
            describeMeMonday.setText(response.getDescribe().getSecondStep().getWeekdaysInfoMonday());
        if (tuesday != null)
            describeMeTuesday.setText(response.getDescribe().getSecondStep().getWeekdaysInfoTuesday());
        if (wednesday != null)
            describeMeWednesday.setText(response.getDescribe().getSecondStep().getWeekdaysInfoWednesday());
        if (thursday != null)
            describeMeThursday.setText(response.getDescribe().getSecondStep().getWeekdaysInfoThursday());
        if (friday != null)
            describeMeFriday.setText(response.getDescribe().getSecondStep().getWeekdaysInfoFriday());
        if (saturday != null)
            describeMeSaturday.setText(response.getDescribe().getSecondStep().getWeekdaysInfoSaturday());
        if (sunday != null)
            describeMeSunday.setText(response.getDescribe().getSecondStep().getWeekdaysInfoSunday());


        RecyclerView describeMeRecyclerView = (RecyclerView) findViewById(R.id.describe_me_recycler_view);
        describeMeRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SnapHelper helper=new LinearSnapHelper();
        helper.attachToRecyclerView(describeMeRecyclerView);
        List<DescribeMeEverythingThirdStep> describeAll = response.getDescribe().getRatingModel();

        if (describeAll != null) {
            describeMeRecyclerView.setAdapter(new DescribeMeAdapter(describeAll, R.layout.list_item_describe_me_reviews, getApplicationContext()));
        }

        //Intent Listeners
        describeMePhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callForwardString = describeMePhoneNo.getText().toString();//"**21*1234567890#";
                Intent intentCallForward = new Intent(Intent.ACTION_DIAL); // ACTION_CALL
                Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
                intentCallForward.setData(uri2);
                startActivity(intentCallForward);
            }
        });
        describeMeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapUrl = null;
                mapUrl = response.getDescribe().getMapUrl();
                if (mapUrl != null) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(mapUrl));
                    startActivity(intent);
                } else {
                    Toast.makeText(DescribeMeEverything.this, "No Recognizable Address", Toast.LENGTH_LONG).show();
                }
            }
        });

        describeMeOpenWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = null;
                websiteUrl = response.getDescribe().getWebsite();
                if (websiteUrl != null) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                    startActivity(intent);
                } else {
                    Toast.makeText(DescribeMeEverything.this, "No Website Available", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        ToggleButton v = (ToggleButton) view;
        Drawable draw1 = getResources().getDrawable(R.drawable.save_that_activated_on_click);
        Drawable draw2 = getResources().getDrawable(R.drawable.save_that_deactivated);

        if (on) {
            // Enable vibrate
            v.setBackground(draw1);

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(DescribeMeEverything.this);
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
            travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(DescribeMeEverything.this, LinearLayoutManager.HORIZONTAL, false));


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
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, DescribeMeEverything.this, DescribeMeEverything.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                alert11.dismiss();
                                startActivity(new Intent(DescribeMeEverything.this, TravelPlanningAssistance.class));

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

            } else {
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, DescribeMeEverything.this, DescribeMeEverything.this));

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

                                    for(int i=0;i<list.size();i++) {
                                            SinglePlaceSavedInTPA place = list.get(i);
                                            String placeName = place.getPlaceName();
//                                            Log.d("kamyaab", placeName + "another name" + cityIn);
                                            if (placeName.equalsIgnoreCase(cityIn)) {
                                                if (typeOfEstablishment.equalsIgnoreCase("poi")) {
                                                    List<PointOfInterestFirstStep> step = list.get(i).getSelectedPointOfInterest();
                                                    List<PointOfInterestPhotos> photos=new ArrayList<>();
                                                    photos.add(new PointOfInterestPhotos());
                                                    photos.get(0).setPhotoReference(photoReference);
                                                    step.add(new PointOfInterestFirstStep(photos, locationAddress, imageUrl, placeNameWhichHasThisPoint
                                                            , describeMeRating.getText().toString(), describeAllPlaceId
                                                            , new PointOfInterestLocation(new PointOfInterestLocationSecond(latLng.latitude + "", latLng.longitude + ""))));
                                                    list.get(i).setSelectedPointOfInterest(step);
                                                    Toast.makeText(DescribeMeEverything.this,"Point of Interest added in plan"+plan.getPlanName()+"under city"+cityIn,Toast.LENGTH_LONG).show();
                                                } else if (typeOfEstablishment.equalsIgnoreCase("hotel")) {
                                                    List<PointOfInterestFirstStep> step = list.get(i).getSelectedHotels();
                                                    List<PointOfInterestPhotos> photos=new ArrayList<>();
                                                    photos.add(new PointOfInterestPhotos());
                                                    photos.get(0).setPhotoReference(photoReference);
                                                    step.add(new PointOfInterestFirstStep(photos, locationAddress, imageUrl, placeNameWhichHasThisPoint
                                                            , describeMeRating.getText().toString(), describeAllPlaceId
                                                            , new PointOfInterestLocation(new PointOfInterestLocationSecond(latLng.latitude + "", latLng.longitude + ""))));
                                                    list.get(i).setSelectedHotels(step);
                                                    Toast.makeText(DescribeMeEverything.this,"Hotel added in plan"+plan.getPlanName()+"under city"+cityIn,Toast.LENGTH_LONG).show();
                                                } else if (typeOfEstablishment.equalsIgnoreCase("oyo")) {
                                                    List<PointOfInterestFirstStep> step = list.get(i).getSelectedOyoRooms();
                                                    List<PointOfInterestPhotos> photos=new ArrayList<>();
                                                    photos.add(new PointOfInterestPhotos());
                                                    photos.get(0).setPhotoReference(photoReference);
                                                    step.add(new PointOfInterestFirstStep(photos, locationAddress, imageUrl, placeNameWhichHasThisPoint
                                                            , describeMeRating.getText().toString(), describeAllPlaceId
                                                            , new PointOfInterestLocation(new PointOfInterestLocationSecond(latLng.latitude + "", latLng.longitude + ""))));
                                                    list.get(i).setSelectedOyoRooms(step);
                                                    Toast.makeText(DescribeMeEverything.this,"Oyo Room added in plan"+plan.getPlanName()+"under city"+cityIn,Toast.LENGTH_LONG).show();
                                                }
                                                gotIt = 1;
                                            }
                                    }
                                    plan.getDataForSavingInTPA().setSavedPlaces(list);
                                    plans.remove(position);
                                    plans.add(position,plan);

                                } else {
                                    plan.getDataForSavingInTPA().setSavedPlaces(new ArrayList<SinglePlaceSavedInTPA>());
                                }
                                if (gotIt == 0) {


                                    SinglePlaceSavedInTPA singlePlaceSavedInTPA = new SinglePlaceSavedInTPA(placeLatLng,cityIn, new ArrayList<PointOfInterestFirstStep>()
                                            , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                            , new ArrayList<String>(), new ArrayList<twitter4j.Status>());

                                    if (typeOfEstablishment.equalsIgnoreCase("poi")) {
                                        List<PointOfInterestFirstStep> step = singlePlaceSavedInTPA.getSelectedPointOfInterest();
                                        List<PointOfInterestPhotos> photos=new ArrayList<>();
                                        photos.add(new PointOfInterestPhotos());
                                        photos.get(0).setPhotoReference(photoReference);
                                        step.add(new PointOfInterestFirstStep(photos, locationAddress, imageUrl, placeNameWhichHasThisPoint
                                                , describeMeRating.getText().toString(), describeAllPlaceId
                                                , new PointOfInterestLocation(new PointOfInterestLocationSecond(latLng.latitude + "", latLng.longitude + ""))));
                                        singlePlaceSavedInTPA.setSelectedPointOfInterest(step);
                                        Toast.makeText(DescribeMeEverything.this,"Point of Interest added in plan"+plan.getPlanName()+"under city"+cityIn,Toast.LENGTH_LONG).show();
                                    } else if (typeOfEstablishment.equalsIgnoreCase("hotel")) {
                                        List<PointOfInterestFirstStep> step = singlePlaceSavedInTPA.getSelectedHotels();
                                        List<PointOfInterestPhotos> photos=new ArrayList<>();
                                        photos.add(new PointOfInterestPhotos());
                                        photos.get(0).setPhotoReference(photoReference);
                                        step.add(new PointOfInterestFirstStep(photos, locationAddress, imageUrl, placeNameWhichHasThisPoint
                                                , describeMeRating.getText().toString(), describeAllPlaceId
                                                , new PointOfInterestLocation(new PointOfInterestLocationSecond(latLng.latitude + "", latLng.longitude + ""))));
                                        singlePlaceSavedInTPA.setSelectedHotels(step);
                                        Toast.makeText(DescribeMeEverything.this,"Hotel added in plan"+plan.getPlanName()+"under city"+cityIn,Toast.LENGTH_LONG).show();
                                    } else if (typeOfEstablishment.equalsIgnoreCase("oyo")) {
                                        List<PointOfInterestFirstStep> step = singlePlaceSavedInTPA.getSelectedOyoRooms();
                                        List<PointOfInterestPhotos> photos=new ArrayList<>();
                                        photos.add(new PointOfInterestPhotos());
                                        photos.get(0).setPhotoReference(photoReference);
                                        step.add(new PointOfInterestFirstStep(photos, locationAddress, imageUrl, placeNameWhichHasThisPoint
                                                , describeMeRating.getText().toString(), describeAllPlaceId
                                                , new PointOfInterestLocation(new PointOfInterestLocationSecond(latLng.latitude + "", latLng.longitude + ""))));
                                        singlePlaceSavedInTPA.setSelectedOyoRooms(step);
                                        Toast.makeText(DescribeMeEverything.this,"Oyo Room added in plan"+plan.getPlanName()+"under city"+cityIn,Toast.LENGTH_LONG).show();
                                    }


                                    plan.getDataForSavingInTPA().getSavedPlaces().add(plan.getDataForSavingInTPA().getSavedPlaces().size()-1,singlePlaceSavedInTPA);
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
            v.setBackground(draw2);
        }
    }

    @Override
    public void onMoreOptionMenuClicked(int position, View view) {

    }

    @Override
    public void onEverythingElseClicked(int position , View view) {

    }
}
