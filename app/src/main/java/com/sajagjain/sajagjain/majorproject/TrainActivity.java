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

import com.sajagjain.sajagjain.majorproject.adapter.TrainAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.Train;
import com.sajagjain.sajagjain.majorproject.model.TrainResponse;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.sajagjain.sajagjain.majorproject.rest.RailwayApiClient;
import com.sajagjain.sajagjain.majorproject.rest.RailwayApiInterface;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainActivity extends AppCompatActivity implements TrainAdapter.TrainAdapterListener, TravelPlanAdapter.TravelPlanAdapterListener {

    List<Train> train=new ArrayList<>();
    String trainSource,trainDestination,trainDate;
    String dFATrainSource,dFATrainDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        dFATrainSource=getIntent().getStringExtra("trainSourceForTPA");
        dFATrainDestination=getIntent().getStringExtra("trainDestinationForTPA");

        if(dFATrainSource.contains("Junction")){
            int index=dFATrainSource.indexOf(" Junction");
            String temp=dFATrainSource.substring(index,dFATrainSource.length());
            dFATrainSource=dFATrainSource.replace(temp,"");
            dFATrainSource=dFATrainSource.trim();
        }
        if(dFATrainDestination.contains("Junction")){
            int index=dFATrainDestination.indexOf(" Junction");
            String temp=dFATrainDestination.substring(index,dFATrainDestination.length());
            dFATrainDestination=dFATrainDestination.replace(temp,"");
            dFATrainDestination=dFATrainDestination.trim();
        }
        //Data from Places
        Bundle trainDataFromIntent=getIntent().getExtras();
        trainSource=(String)trainDataFromIntent.get("trainSource");
        trainDestination=(String)trainDataFromIntent.get("trainDestination");
        trainDate=(String)trainDataFromIntent.get("trainDate");


        final RecyclerView trainRecyclerView = (RecyclerView) findViewById(R.id.train_recycler_view);
        SnapHelper helper=new LinearSnapHelper();
        helper.attachToRecyclerView(trainRecyclerView);
        trainRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this));

        RailwayApiInterface apiService3 =
                RailwayApiClient.getClient().create(RailwayApiInterface.class);

            Call<TrainResponse> trainCall = apiService3.getTrainInfo(trainSource, trainDestination, trainDate);
            trainCall.enqueue(new Callback<TrainResponse>() {
                @Override
                public void onResponse(Call<TrainResponse> call, Response<TrainResponse> response) {
                    int statusCode = response.code();

                    if (response.body() == null) {
                        Toast.makeText(TrainActivity.this, "The Train Service is Currently Unavailable", Toast.LENGTH_LONG);
                    } else {
                        List<Train> train1 = response.body().getTrains();
                        trainRecyclerView.setAdapter(new TrainAdapter(train1, R.layout.list_item_train, getApplicationContext(),TrainActivity.this));
                        train.addAll(train1);
//                        trainRecyclerView.addOnItemTouchListener(
//                                new RecyclerItemClickListener(getApplicationContext(), trainRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(View view, int position) {
//                                        Log.d("clicked","clicked");
//                                        final String trainDate1=trainDate.replace("-","");
//                                        String cpan= trainSource+"/"+trainDestination+"/"+trainDate1+"//1/0/0/0/ALL";
//                                        Uri uri=Uri.parse("https://www.ixigo.com/search/result/train/"+cpan);
//
////                                        NDLS/BINA/11012018//1/0/0/0/ALL
//
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
                public void onFailure(Call<TrainResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("TrainResultFetchError", t.toString());
                }
            });


        }


    @Override
    public void onTrainSaveButtonClick(final int position, View view) {
        boolean on = ((ToggleButton) view).isChecked();
        ToggleButton v=(ToggleButton) view;
        Drawable draw1=getResources().getDrawable(R.drawable.save_that_activated_on_click);
        Drawable draw2= getResources().getDrawable(R.drawable.save_that_deactivated);

        if (on) {
            // Enable vibrate
            v.setBackground(draw1);
            //Set source and destination to train
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
            train.get(position).setTrainSource(dFATrainSource);
            train.get(position).setTrainDestination(dFATrainDestination);
            try {
                train.get(position).setTrainDate(sdf.parse(trainDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(TrainActivity.this);
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
            travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(TrainActivity.this, LinearLayoutManager.HORIZONTAL, false));


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
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, TrainActivity.this, TrainActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                alert11.dismiss();
                                startActivity(new Intent(TrainActivity.this, TravelPlanningAssistance.class));

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

            } else {
                travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, TrainActivity.this, TrainActivity.this));

                travelPlanRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int pos) {

                                alert11.dismiss();


                                int gotIt = 0;

                                TravelPlan plan = plans.get(pos);
                                List<Train> list = plan.getDataForSavingInTPA().getSelectedTrains();
                                if (list != null) {
                                    Log.d("kamyaab", "list isnt null annd list size is" + list.size());

                                    String currentTrainSource = train.get(position).getTrainSource(), currentTrainDestination = train.get(position).getTrainDestination();
                                    int count = 0;
                                    for (int i = 0; i < list.size(); i++) {
                                        Train trainTemp = list.get(i);
                                        String trainSource = trainTemp.getTrainSource(), trainDestination = trainTemp.getTrainDestination();
                                        if (trainSource != currentTrainSource || trainDestination != currentTrainDestination) {
                                            count++;
                                        }

                                    }
                                    if (count == list.size()) {
                                        list.add(train.get(position));
                                        Toast.makeText(TrainActivity.this, "Train added to Plan" + plan.getPlanName(), Toast.LENGTH_LONG).show();

                                        plan.getDataForSavingInTPA().setSelectedTrains(list);
                                        plans.remove(pos);
                                        plans.add(pos, plan);

                                        for(Train train:plans.get(pos).getDataForSavingInTPA().getSelectedTrains()){
                                            Log.d("trainlist",train.getTrainSource()+"---"+train.getTrainDestination());
                                        }
                                    } else {
                                        Toast.makeText(TrainActivity.this, "There is a train which has same source or destination", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    plan.getDataForSavingInTPA().setSelectedTrains(new ArrayList<Train>());
                                    Train trainTemp = train.get(position);

                                    List<Train> trainTempList=plan.getDataForSavingInTPA().getSelectedTrains();
                                    trainTempList.add(train.get(position));
                                    plan.getDataForSavingInTPA().setSelectedTrains(trainTempList);

                                    Toast.makeText(TrainActivity.this, "Train added in Plan" + plan.getPlanName(), Toast.LENGTH_LONG).show();


                                    plans.remove(position);
                                    plans.add(position, plan);

                                    for(Train train:plans.get(pos).getDataForSavingInTPA().getSelectedTrains()){
                                        Log.d("trainlist",train.getTrainSource()+"---"+train.getTrainDestination());
                                    }
                                }


                                Log.d("kamyaab", plans.get(pos).getDataForSavingInTPA().getSelectedTrains().get(0).getTrainSource() + " " + plans.get(pos).getDataForSavingInTPA().getSelectedTrains().get(0).getTrainDestination());
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
        Log.d("clicked","clicked");
        final String trainDate1=trainDate.replace("-","");
        String cpan= trainSource+"/"+trainDestination+"/"+trainDate1+"//1/0/0/0/ALL";
        Uri uri=Uri.parse("https://www.ixigo.com/search/result/train/"+cpan);

//                                        NDLS/BINA/11012018//1/0/0/0/ALL


        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
