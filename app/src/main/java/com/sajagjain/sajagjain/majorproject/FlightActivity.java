package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sajagjain.sajagjain.majorproject.adapter.FlightAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.Flight;
import com.sajagjain.sajagjain.majorproject.model.FlightFirstStep;
import com.sajagjain.sajagjain.majorproject.model.FlightResponse;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.sajagjain.sajagjain.majorproject.rest.GoIbiboApiClient;
import com.sajagjain.sajagjain.majorproject.rest.GoIbiboApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightActivity extends AppCompatActivity implements FlightAdapter.FlightAdapterListener, TravelPlanAdapter.TravelPlanAdapterListener {

    private final static String GO_APP_ID = "fd2f5410";
    private final static String GO_APP_KEYS = "c9b94d5eb6c118d4b0c4c13a242c0fcc";
    private final static String GO_FORMAT_DATA = "json";
    private static final String TAG = MainActivity.class.getSimpleName();
    public static String flightClass="E";
    final List<Flight> flight=new ArrayList<>();
    String flightSource,flightDestination,flightDate;
    String dFAFlightSource,dFAFlightDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);

        if (GO_APP_ID.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        dFAFlightSource=getIntent().getStringExtra("dFAFlightSource");
        dFAFlightDestination=getIntent().getStringExtra("dFAFlightDestination");

        //Take Data from Places

        Bundle flightDataFromIntent=getIntent().getExtras();
        flightSource=(String)flightDataFromIntent.get("flightSource");
        flightDestination=(String)flightDataFromIntent.get("flightDestination");
        flightDate=(String)flightDataFromIntent.get("flightDate");
        boolean economyFlight=(boolean)flightDataFromIntent.get("economyClass");
        boolean businessFlight=(boolean)flightDataFromIntent.get("businessClass");
        String adults=(String)flightDataFromIntent.get("flightAdult");
        String children=(String)flightDataFromIntent.get("flightChildren");

        if(businessFlight==true){
            flightClass="B";
        }
        //Flight Api GoIbibo

        final RecyclerView flightRecyclerView = (RecyclerView) findViewById(R.id.flight_recycler_view);
        flightRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this));

        GoIbiboApiInterface apiService1 =
                GoIbiboApiClient.getClient().create(GoIbiboApiInterface.class);



        if(flightSource.equals("")||flightDestination.equals("")||flightDate.equals("")||adults.equals("")
                ||children.equals("")||(economyFlight==false&&businessFlight==false)){

            Call<FlightResponse> flightCall = apiService1.getFlightGoIbibo(GO_APP_ID,GO_APP_KEYS,GO_FORMAT_DATA,"IDR","BHO","20171026",flightClass,1,0,"100");
            flightCall.enqueue(new Callback<FlightResponse>() {
                @Override
                public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                    int statusCode = response.code();
                    Log.d("status", response.body().getData().toString());
                    if (response.body() == null) {
                        Toast.makeText(FlightActivity.this,"The Flight Service is Currently Unavailable",Toast.LENGTH_LONG).show();
                    } else {
                        FlightFirstStep step = response.body().getData();
                        List<Flight> flight1 = step.getFlightinterimdata();

                        SnapHelper helper=new LinearSnapHelper();
                        helper.attachToRecyclerView(flightRecyclerView);

                        flightRecyclerView.setAdapter(new FlightAdapter(flight, R.layout.list_item_flight, getApplicationContext(),FlightActivity.this));
                        flight.addAll(flight1);
//                        flightRecyclerView.addOnItemTouchListener(
//                                new RecyclerItemClickListener(getApplicationContext(), flightRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(View view, int position) {
//                                        Log.d("clicked","clicked");
//                                        String cpan=lista.get(position).getRedirectRequest();
//                                        cpan=cpan.replace("-100--","-D");
//                                        Uri uri=Uri.parse("https://www.goibibo.com/flights/"+cpan);
//
//                                        startActivity(new Intent(Intent.ACTION_VIEW,uri));
//                                    }
//
//                                    @Override
//                                    public void onLongItemClick(View view, int position) {
//                                        // do whatever
//                                    }
//                                })
//                        );
                    }
                }

                @Override
                public void onFailure(Call<FlightResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });

        }else{

            Call<FlightResponse> flightCall = apiService1.getFlightGoIbibo(GO_APP_ID,GO_APP_KEYS,GO_FORMAT_DATA,flightSource,flightDestination,flightDate,flightClass,Integer.parseInt(adults),Integer.parseInt(children),"100");
            flightCall.enqueue(new Callback<FlightResponse>() {
                @Override
                public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                    int statusCode = response.code();
//                    Log.d("status", response.body().getData().toString());
                    if (statusCode != 200) {
                        Toast.makeText(FlightActivity.this,"The Flight Service is Currently Unavailable",Toast.LENGTH_LONG).show();
                    } else {
                        FlightFirstStep step = response.body().getData();
                        List<Flight> flight1 = step.getFlightinterimdata();

                        SnapHelper helper=new LinearSnapHelper();
                        helper.attachToRecyclerView(flightRecyclerView);

                        flightRecyclerView.setAdapter(new FlightAdapter(flight1, R.layout.list_item_flight, getApplicationContext(),FlightActivity.this));
                        flight.addAll(flight1);
                    }
                }

                @Override
                public void onFailure(Call<FlightResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }


    }

    @Override
    public void onFlightSaveButtonClick(final int position, View view) {
        boolean on = ((ToggleButton) view).isChecked();
        ToggleButton v=(ToggleButton) view;
        Drawable draw1=getResources().getDrawable(R.drawable.save_that_activated_on_click);
        Drawable draw2= getResources().getDrawable(R.drawable.save_that_deactivated);

        if (on) {
            // Enable vibrate
            v.setBackground(draw1);

            //Setting Flight source and destination
            flight.get(position).setFlightSource(dFAFlightSource);
            flight.get(position).setFlightDestination(dFAFlightDestination);
            flight.get(position).setFightDepartureDate(flightDate);

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(FlightActivity.this);
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
            travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(FlightActivity.this, LinearLayoutManager.HORIZONTAL, false));


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
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, FlightActivity.this, FlightActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                alert11.dismiss();
                                startActivity(new Intent(FlightActivity.this, TravelPlanningAssistance.class));

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

            } else {
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, FlightActivity.this, FlightActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int pos) {

                                alert11.dismiss();


                                TravelPlan plan = plans.get(pos);
                                List<Flight> list = plan.getDataForSavingInTPA().getSelectedFlights();
                                if (list != null) {
                                    Log.d("kamyaab", "list isnt null and list size is" + list.size());

                                    String currentFlightSource = flight.get(position).getFlightSource(), currentFlightDestination = flight.get(position).getFlightDestination();
                                    int count = 0;
                                    for (int i = 0; i < list.size(); i++) {
                                        Flight flightTemp = list.get(i);
                                        String flightSource = flightTemp.getFlightSource(), flightDestination = flightTemp.getFlightDestination();
                                        if (flightSource!=currentFlightSource || flightDestination!=currentFlightDestination) {
                                            count++;
                                        }

                                    }
                                    if (count == list.size()) {
                                        list.add(flight.get(position));
                                        Toast.makeText(FlightActivity.this, "Flight added to Plan" + plan.getPlanName(), Toast.LENGTH_LONG).show();

                                        plan.getDataForSavingInTPA().setSelectedFlights(list);
                                        plans.remove(pos);
                                        plans.add(pos, plan);

                                        for(Flight flight:plans.get(pos).getDataForSavingInTPA().getSelectedFlights()){
                                            Log.d("flightlist",flight.getFlightSource()+"---"+flight.getFlightDestination());
                                        }
                                    } else {
                                        Toast.makeText(FlightActivity.this, "There is a flight which has same source or destination", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    plan.getDataForSavingInTPA().setSelectedFlights(new ArrayList<Flight>());
                                    Flight flightTemp = flight.get(position);

                                    List<Flight> flightTempList=plan.getDataForSavingInTPA().getSelectedFlights();
                                    flightTempList.add(flight.get(position));
                                    plan.getDataForSavingInTPA().setSelectedFlights(flightTempList);

                                    Toast.makeText(FlightActivity.this, "Flight added in Plan" + plan.getPlanName(), Toast.LENGTH_LONG).show();


                                    plans.remove(position);
                                    plans.add(position, plan);

                                    for(Flight flight:plans.get(pos).getDataForSavingInTPA().getSelectedFlights()){
                                        Log.d("flightlist",flight.getFlightSource()+"---"+flight.getFlightDestination());
                                    }
                                }



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
        String cpan=flight.get(position).getRedirectRequest();
        cpan=cpan.replace("-100--","-D");
        Log.d("clicked",cpan);
        //air-DEL-BOM-20171231--1-0-0-E-100--
        Uri uri=Uri.parse("https://www.goibibo.com/flights/"+cpan+"/");
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
