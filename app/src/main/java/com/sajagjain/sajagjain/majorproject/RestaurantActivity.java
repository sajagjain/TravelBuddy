package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sajagjain.sajagjain.majorproject.adapter.SingleRestaurantAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestFirstStep;
import com.sajagjain.sajagjain.majorproject.model.SinglePlaceSavedInTPA;
import com.sajagjain.sajagjain.majorproject.model.SingleRestaurantFirstStep;
import com.sajagjain.sajagjain.majorproject.model.SingleRestaurantResponse;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RestaurantActivity extends AppCompatActivity implements SingleRestaurantAdapter.SingleRestaurantAdapterListener,TravelPlanAdapter.TravelPlanAdapterListener{

    List<SingleRestaurantFirstStep> singleRestaurant = null;
    String cityIn;
    LatLng placeLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lon");
        cityIn=getIntent().getStringExtra("cityIn");
        placeLatLng=new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));

        RecyclerView recyclerViewForRestaurant = (RecyclerView) findViewById(R.id.restaurant_view_all_recycler_view);

        try {


            recyclerViewForRestaurant.setLayoutManager(new CenterZoomLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            SnapHelper helper=new LinearSnapHelper();
            helper.attachToRecyclerView(recyclerViewForRestaurant);

            String doc = Jsoup.connect("https://developers.zomato.com/api/v2.1/search?count=50&lat=" + lat + "&lon=" + lon)
                    .header("user-key", "1714b7ed91caa915f9ddbb1785618226").ignoreContentType(true).execute().body();
            Log.d("singlerestaurantkilist", doc);

            JSONObject obj1 = new JSONObject(doc);
            SingleRestaurantResponse response = SingleRestaurantResponse.fromJson(obj1);
            if (response.getList().size() > 0) {
                singleRestaurant = response.getList();
                Log.d("jsonstring", singleRestaurant.get(0).getRestaurant().getName());
                recyclerViewForRestaurant.setAdapter(new SingleRestaurantAdapter(singleRestaurant, R.layout.list_item_single_restaurant, getApplicationContext(),this));


            } else {
                //setVisible false for all the components of zomato


            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

//        final List<SingleRestaurantFirstStep> restaurant1 = singleRestaurant;


//        recyclerViewForRestaurant.addOnItemTouchListener(
//                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForRestaurant, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant1.get(position)
//                                    .getRestaurant().getRedirectURL().toString())));
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );

    }

    @Override
    public void onOpenButtonClicked(int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(singleRestaurant.get(position)
                .getRestaurant().getRedirectURL().toString())));
    }

    @Override
    public void onSaveToggleButtonClicked(final int position, View view) {
        boolean on = ((ToggleButton) view).isChecked();
        ToggleButton v=(ToggleButton) view;
        Drawable draw1=getResources().getDrawable(R.drawable.save_that_activated_on_click);
        Drawable draw2= getResources().getDrawable(R.drawable.save_that_deactivated);

        if (on) {
            // Enable vibrate
            v.setBackground(draw1);

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(RestaurantActivity.this);
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setTitle("Select the Plan you want to Add Restaurant to");
            builder1.setView(R.layout.bottom_sheet_fragment_select_plan_layout);
            final AlertDialog alert11 = builder1.create();

            alert11.show();
            RecyclerView travelPlanRecyclerView;
            final List<TravelPlan> plans = new ArrayList<>();
            final SharedPreferences pref;

            travelPlanRecyclerView = (RecyclerView) alert11.findViewById(R.id.bottom_sheet_fragment_for_places_recycler_view);
            travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(RestaurantActivity.this, LinearLayoutManager.HORIZONTAL, false));


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
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, RestaurantActivity.this, RestaurantActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                alert11.dismiss();
                                startActivity(new Intent(RestaurantActivity.this, TravelPlanningAssistance.class));

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

            } else {
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, RestaurantActivity.this, RestaurantActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int pos) {

                                alert11.dismiss();


                                int gotIt = 0;

                                TravelPlan plan = plans.get(pos);
                                List<SinglePlaceSavedInTPA> list = plan.getDataForSavingInTPA().getSavedPlaces();
                                if (list != null) {
                                    Log.d("kamyaab", "list isnt null annd list size is" + list.size());

                                    for(int i=0;i<list.size();i++) {
                                        SinglePlaceSavedInTPA place = list.get(i);
                                        String placeName = place.getPlaceName();
//                                            Log.d("kamyaab", placeName + "another name" + cityIn);
                                        if (placeName.equalsIgnoreCase(cityIn)) {

                                            List<SingleRestaurantFirstStep> step = list.get(i).getSelectedRestaurants();
                                            step.add(singleRestaurant.get(position));
                                            list.get(i).setSelectedRestaurants(step);
                                            Toast.makeText(RestaurantActivity.this, "Restaurant added in plan" + plan.getPlanName() + "under city" + cityIn, Toast.LENGTH_LONG).show();
                                            gotIt = 1;

                                        }
                                    }
                                    plan.getDataForSavingInTPA().setSavedPlaces(list);
                                    plans.remove(pos);
                                    plans.add(pos,plan);

                                } else {
                                    plan.getDataForSavingInTPA().setSavedPlaces(new ArrayList<SinglePlaceSavedInTPA>());
                                }
                                if (gotIt == 0) {


                                    SinglePlaceSavedInTPA singlePlaceSavedInTPA = new SinglePlaceSavedInTPA(placeLatLng,cityIn, new ArrayList<PointOfInterestFirstStep>()
                                            , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                            , new ArrayList<String>(), new ArrayList<twitter4j.Status>());


                                        List<SingleRestaurantFirstStep> step = singlePlaceSavedInTPA.getSelectedRestaurants();
                                        step.add(singleRestaurant.get(position));
                                        Toast.makeText(RestaurantActivity.this,"Restaurant added in plan"+plan.getPlanName()+"under city"+cityIn,Toast.LENGTH_LONG).show();



                                    plan.getDataForSavingInTPA().getSavedPlaces().add(list.size()-1,singlePlaceSavedInTPA);
                                    Log.d("kamyaab", "silsila chalta rahe");

                                    plans.remove(pos);
                                    plans.add(pos, plan);

                                }


//                                Log.d("kamyaab", plans.get(pos).getDataForSavingInTPA().getSavedPlaces().get(0).getPlaceName() + " " + plans.get(position).getDataForSavingInTPA().getSavedPlaces().size());
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
    public void onEverythingElseClicked(int position, View view) {

    }
}
