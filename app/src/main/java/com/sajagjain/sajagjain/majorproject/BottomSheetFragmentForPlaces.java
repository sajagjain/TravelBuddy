package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestFirstStep;
import com.sajagjain.sajagjain.majorproject.model.SinglePlaceSavedInTPA;
import com.sajagjain.sajagjain.majorproject.model.SingleRestaurantFirstStep;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sajag jain on 02-02-2018.
 */

public class BottomSheetFragmentForPlaces extends BottomSheetDialogFragment implements TravelPlanAdapter.TravelPlanAdapterListener {

    Switch addToPlan, addToSavedPlaces, addToBucketList;
    TextView addToPlanNameTextView;
    String placeName;
    LatLng placeLatLng;
    SharedPreferences pref;
    Button addToCollectionButton;
    SharedPreferences.Editor prefsEditor;

    public BottomSheetFragmentForPlaces() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.bottom_sheet_for_places, container, false);
        pref = getActivity().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
        placeName = getArguments().getString("placeName");
        placeLatLng = getArguments().getParcelable("placeLatLng");
        // Inflate the layout for this fragment
        addToPlan = (Switch) rootView.findViewById(R.id.bottom_sheet_for_places_add_to_plan);
        addToSavedPlaces = (Switch) rootView.findViewById(R.id.bottom_sheet_for_places_add_to_saved_places);
        addToBucketList = (Switch) rootView.findViewById(R.id.bottom_sheet_for_places_add_to_bucket_list);
        addToPlanNameTextView = (TextView) rootView.findViewById(R.id.bottom_sheet_for_places_add_to_plan_name_text_view);
        addToCollectionButton = (Button) rootView.findViewById(R.id.bottom_sheet_for_places_add_to_collections);
        addToPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(BottomSheetFragmentForPlaces.this.getContext());
                    builder1.setCancelable(false);
                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder1.setTitle("Select the Plan you want to add place to");
                    builder1.setView(R.layout.bottom_sheet_fragment_select_plan_layout);
                    final AlertDialog alert11 = builder1.create();

                    alert11.show();
                    RecyclerView travelPlanRecyclerView;
                    final List<TravelPlan> plans = new ArrayList<>();
                    final SharedPreferences pref;

                    travelPlanRecyclerView = (RecyclerView) alert11.findViewById(R.id.bottom_sheet_fragment_for_places_recycler_view);
                    travelPlanRecyclerView.setLayoutManager(new LinearLayoutManager(BottomSheetFragmentForPlaces.this.getContext(), LinearLayoutManager.HORIZONTAL, false));


                    pref = getActivity().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
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
                        travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, BottomSheetFragmentForPlaces.this.getContext(), BottomSheetFragmentForPlaces.this));

                        travelPlanRecyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        alert11.dismiss();
                                        startActivity(new Intent(BottomSheetFragmentForPlaces.this.getContext(), TravelPlanningAssistance.class));

                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );

                    } else {
                        travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, BottomSheetFragmentForPlaces.this.getContext(), BottomSheetFragmentForPlaces.this));

                        travelPlanRecyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(alert11.getContext(), travelPlanRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        addToPlanNameTextView.setBackgroundColor(Color.WHITE);
                                        addToPlanNameTextView.setText("Selected Plan Is " + plans.get(position).getPlanName());
                                        alert11.dismiss();


                                        TravelPlan plan = plans.get(position);
                                        List<SinglePlaceSavedInTPA> list = plan.getDataForSavingInTPA().getSavedPlaces();
                                        if (list != null) {
                                            Log.d("kamyaab", "list isnt null annd list size is" + list.size());
                                        } else {
                                            plan.getDataForSavingInTPA().setSavedPlaces(new ArrayList<SinglePlaceSavedInTPA>());
                                        }
                                        SinglePlaceSavedInTPA singlePlaceSavedInTPA = new SinglePlaceSavedInTPA(placeLatLng,placeName, new ArrayList<PointOfInterestFirstStep>()
                                                , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                                , new ArrayList<String>(), new ArrayList<twitter4j.Status>());

                                        plan.getDataForSavingInTPA().getSavedPlaces().add((list.size()-1),singlePlaceSavedInTPA);
                                        Log.d("kamyaab", "silsila chalta rahe");

                                        plans.remove(position);
                                        plans.add(position, plan);

                                        Log.d("kamyaab", plans.get(position).getDataForSavingInTPA().getSavedPlaces().get(0).getPlaceName() + " " + plans.get(position).getDataForSavingInTPA().getSavedPlaces().size());
                                        prefsEditor = pref.edit();
                                        Iterator it = plans.iterator();
                                        HashSet<String> set = new HashSet();
                                        while (it.hasNext()) {
                                            Gson gson = new Gson();
                                            set.add(gson.toJson(it.next()));
                                            prefsEditor.putStringSet("plans", set);
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
                    //do something when unchecked

                }
            }
        });

        addToSavedPlaces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {


                } else {

                }
            }
        });

        addToCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefsEditor.commit();
                getDialog().dismiss();
                Toast.makeText(getContext(), "Added to collections specified", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;


    }

    @Override
    public void onMoreOptionMenuClicked(int position, View view) {

    }

    @Override
    public void onEverythingElseClicked(int position, View view) {

    }


}
