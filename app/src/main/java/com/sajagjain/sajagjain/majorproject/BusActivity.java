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

import com.sajagjain.sajagjain.majorproject.adapter.BusAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.Bus;
import com.sajagjain.sajagjain.majorproject.model.BusFirstStep;
import com.sajagjain.sajagjain.majorproject.model.BusResponse;
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

public class BusActivity extends AppCompatActivity implements BusAdapter.BusAdapterListener, TravelPlanAdapter.TravelPlanAdapterListener {


    private final static String GO_APP_ID = "fd2f5410";
    private final static String GO_APP_KEYS = "c9b94d5eb6c118d4b0c4c13a242c0fcc";
    private final static String GO_FORMAT_DATA = "json";
    private static final String TAG = MainActivity.class.getSimpleName();
    public static String flightClass = "E";
    List<Bus> bus = new ArrayList<>();
    String busSource, busDestination, busDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        if (GO_APP_ID.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }
        //Take Data from Places

        Bundle busDataFromIntent = getIntent().getExtras();
        busSource = (String) busDataFromIntent.get("busSource");
        busDestination = (String) busDataFromIntent.get("busDestination");
        busDate = (String) busDataFromIntent.get("busDate");


        final RecyclerView busRecyclerView = (RecyclerView) findViewById(R.id.bus_recycler_view);
        SnapHelper helper=new LinearSnapHelper();
        helper.attachToRecyclerView(busRecyclerView);

        busRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this));

        GoIbiboApiInterface apiService2 =
                GoIbiboApiClient.getClient().create(GoIbiboApiInterface.class);


        Call<BusResponse> busCall = apiService2.getBusesGoIbibo(GO_APP_ID, GO_APP_KEYS, GO_FORMAT_DATA, busSource.toLowerCase(), busDestination.toLowerCase(), busDate);
        busCall.enqueue(new Callback<BusResponse>() {
            @Override
            public void onResponse(Call<BusResponse> call, Response<BusResponse> response) {
                String statusCode = response.message();
                Log.d("status", response.message());
                if (response.body() == null) {
                    Toast.makeText(BusActivity.this, "The Bus Service is Currently Unavailable", Toast.LENGTH_LONG).show();
                } else {
                    BusFirstStep step = response.body().getData();
                    List<Bus> bus1 = step.getBusinterimdata();
                    busRecyclerView.setAdapter(new BusAdapter(bus1, R.layout.list_item_bus, getApplicationContext(), BusActivity.this));
                    bus.addAll(bus1);
//                        busRecyclerView.addOnItemTouchListener(
//                                new RecyclerItemClickListener(getApplicationContext(), busRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(View view, int position) {
//                                        Log.d("clicked","clicked");
//                                        String cpan= busSource+"-"+busDestination+"-"+busDate+"---0-0-"
//                                                +lista.get(position).getSrcVoyagerID()+"-"+lista.get(position).getDestVogayerID();
//                                        Uri uri=Uri.parse("https://www.goibibo.com/bus/#bus-"+cpan);
//
////                                        https://www.goibibo.com/bus/#bus-Delhi-Manali-20180106---0-0-2820046943342890302-2242315224277940906
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
            public void onFailure(Call<BusResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public void onBusSaveButtonClick(final int position, View view) {
        boolean on = ((ToggleButton) view).isChecked();
        ToggleButton v = (ToggleButton) view;
        Drawable draw1 = getResources().getDrawable(R.drawable.save_that_activated_on_click);
        Drawable draw2 = getResources().getDrawable(R.drawable.save_that_deactivated);

        if (on) {
            // Enable vibrate
            v.setBackground(draw1);

            //Setting Bus Source and Destination
            bus.get(position).setBusSource(busSource);
            bus.get(position).setBusDestination(busDestination);

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(BusActivity.this);
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
            travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(BusActivity.this, LinearLayoutManager.HORIZONTAL, false));


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
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, BusActivity.this, BusActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                alert11.dismiss();
                                startActivity(new Intent(BusActivity.this, TravelPlanningAssistance.class));

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

            } else {
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, BusActivity.this, BusActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int pos) {

                                alert11.dismiss();


                                int gotIt = 0;

                                TravelPlan plan = plans.get(pos);
                                List<Bus> list = plan.getDataForSavingInTPA().getSelectedBuses();
                                if (list != null) {
                                    Log.d("kamyaab", "list isnt null annd list size is" + list.size());

                                    String currentBusSource = bus.get(position).getBusSource(), currentBusDestination = bus.get(position).getBusDestination();
                                    int count = 0;
                                    for (int i = 0; i < list.size(); i++) {
                                        Bus busTemp = list.get(i);
                                        String busSource = busTemp.getBusSource(), busDestination = busTemp.getBusDestination();
                                        if (busSource != currentBusSource || busDestination != currentBusDestination) {
                                            count++;
                                        }

                                    }
                                    if (count == list.size()) {
                                        list.add(bus.get(position));
                                        Toast.makeText(BusActivity.this, "Bus added to Plan" + plan.getPlanName(), Toast.LENGTH_LONG).show();

                                        plan.getDataForSavingInTPA().setSelectedBuses(list);
                                        plans.remove(pos);
                                        plans.add(pos, plan);

                                        for(Bus bus:plans.get(pos).getDataForSavingInTPA().getSelectedBuses()){
                                            Log.d("buslist",bus.getBusSource()+"---"+bus.getBusDestination());
                                        }
                                    } else {
                                        Toast.makeText(BusActivity.this, "There is a bus which has same source or destination", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    plan.getDataForSavingInTPA().setSelectedBuses(new ArrayList<Bus>());
                                    Bus busTemp = bus.get(position);

                                    List<Bus> busTempList=plan.getDataForSavingInTPA().getSelectedBuses();
                                    busTempList.add(bus.get(position));
                                    plan.getDataForSavingInTPA().setSelectedBuses(busTempList);

                                    Toast.makeText(BusActivity.this, "Bus added in Plan" + plan.getPlanName(), Toast.LENGTH_LONG).show();


                                    plans.remove(position);
                                    plans.add(position, plan);

                                    for(Bus bus:plans.get(pos).getDataForSavingInTPA().getSelectedBuses()){
                                        Log.d("buslist",bus.getBusSource()+"---"+bus.getBusDestination());
                                    }
                                }


                                Log.d("kamyaab", plans.get(pos).getDataForSavingInTPA().getSelectedBuses().get(0).getBusSource() + " " + plans.get(pos).getDataForSavingInTPA().getSelectedBuses().get(0).getBusDestination());
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
        Log.d("clicked", "clicked");
        String cpan = busSource + "-" + busDestination + "-" + busDate + "---0-0-"
                + bus.get(position).getSrcVoyagerID() + "-" + bus.get(position).getDestVogayerID();
        Uri uri = Uri.parse("https://www.goibibo.com/bus/#bus-" + cpan);

//                                        https://www.goibibo.com/bus/#bus-Delhi-Manali-20180106---0-0-2820046943342890302-2242315224277940906

        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
