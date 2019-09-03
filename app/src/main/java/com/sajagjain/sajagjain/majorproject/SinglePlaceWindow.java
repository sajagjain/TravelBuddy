package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sajagjain.sajagjain.majorproject.adapter.PointOfInterestAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.SingleRestaurantAdapter;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SinglePlaceWindow extends AppCompatActivity implements SingleRestaurantAdapter.SingleRestaurantAdapterListener {

    SharedPreferences pref;
    List<TravelPlan> plans = new ArrayList<>();
    TextView singlePlaceDescription;
    RecyclerView singlePlaceRecyclerViewForPOI, singlePlaceRecyclerViewForHotels, singlePlaceRecyclerViewForOyoRooms, singlePlaceRecyclerViewForRestaurants;
    int planPosition, placePosition;
    TextView singlePlacePlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_place_window);

        singlePlacePlaceName = (TextView) findViewById(R.id.single_place_window_place_name);
        singlePlaceDescription = (TextView) findViewById(R.id.single_place_window_description);

        planPosition = getIntent().getIntExtra("planPosition", -1);
        placePosition = getIntent().getIntExtra("placePosition", -1);

        pref = getApplicationContext().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
        Gson gson = new Gson();
        Set<String> planFromSharedPref = pref.getStringSet("plans", null);

        if (planFromSharedPref != null) {
            Iterator it = planFromSharedPref.iterator();
            while (it.hasNext()) {
                plans.add(gson.fromJson((String) it.next(), TravelPlan.class));
            }
        }
        singlePlacePlaceName.setText(plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getPlaceName().toString());
        List<String> desc = plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getWikiDescribe();
        if (desc.isEmpty()) {
            singlePlaceDescription.setText("No Description Available");
        } else {
            singlePlaceDescription.setText(desc.get(0).toString());

        }
        singlePlaceRecyclerViewForPOI = (RecyclerView) findViewById(R.id.single_place_window_point_of_interest);
        singlePlaceRecyclerViewForPOI.setLayoutManager(new CenterZoomLayoutManager(SinglePlaceWindow.this, LinearLayoutManager.HORIZONTAL, false));
        if (placePosition != -1) {
            singlePlaceRecyclerViewForPOI.setAdapter(new PointOfInterestAdapter(plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedPointOfInterest(), R.layout.list_item_poi, SinglePlaceWindow.this));
            singlePlaceRecyclerViewForPOI.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), singlePlaceRecyclerViewForPOI, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position) {
                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(SinglePlaceWindow.this);
                            builder1.setCancelable(true);
                            builder1.setNegativeButton(
                                    "Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            builder1.setTitle("Options");
                            builder1.setView(R.layout.single_place_window_dialog);
                            final AlertDialog alert11 = builder1.create();

                            alert11.show();
                            alert11.findViewById(R.id.single_place_window_dialog_add_to_calender).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(SinglePlaceWindow.this, "Select Date", Toast.LENGTH_LONG).show();
                                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                    calIntent.setType("vnd.android.cursor.item/event");
                                    calIntent.putExtra(CalendarContract.Events.TITLE, "Visit At " + plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedPointOfInterest().get(position).getPoiName());
                                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getPlaceName());
                                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, plans.get(planPosition).getPlanName() + " (" + plans.get(planPosition).getStartDate()
                                            + " - " + plans.get(planPosition).getEndDate() + ") ");
                                    startActivity(calIntent);
                                }
                            });
                            alert11.findViewById(R.id.single_place_window_dialog_remove).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert11.dismiss();
                                    plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedPointOfInterest().remove(position);
                                    SharedPreferences.Editor prefsEditor = pref.edit();
                                    Iterator it = plans.iterator();
                                    HashSet<String> set = new HashSet();
                                    while (it.hasNext()) {
                                        Gson gson = new Gson();
                                        set.add(gson.toJson(it.next()));
                                        prefsEditor.putStringSet("plans", set);
                                        prefsEditor.commit();
                                        singlePlaceRecyclerViewForPOI.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }

        singlePlaceRecyclerViewForHotels = (RecyclerView) findViewById(R.id.single_place_window_hotels);
        singlePlaceRecyclerViewForHotels.setLayoutManager(new CenterZoomLayoutManager(SinglePlaceWindow.this, LinearLayoutManager.HORIZONTAL, false));
        if (placePosition != -1) {
            singlePlaceRecyclerViewForHotels.setAdapter(new PointOfInterestAdapter(plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedHotels(), R.layout.list_item_poi, SinglePlaceWindow.this));
            singlePlaceRecyclerViewForHotels.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), singlePlaceRecyclerViewForHotels, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position) {

                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(SinglePlaceWindow.this);
                            builder1.setCancelable(true);
                            builder1.setNegativeButton(
                                    "Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            builder1.setTitle("Options");
                            builder1.setView(R.layout.single_place_window_dialog);
                            final AlertDialog alert11 = builder1.create();

                            alert11.show();
                            alert11.findViewById(R.id.single_place_window_dialog_add_to_calender).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(SinglePlaceWindow.this, "Select Date", Toast.LENGTH_LONG).show();
                                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                    calIntent.setType("vnd.android.cursor.item/event");
                                    calIntent.putExtra(CalendarContract.Events.TITLE, "Stay At " + plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedHotels().get(position).getPoiName());
                                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getPlaceName());
                                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, plans.get(planPosition).getPlanName() + " (" + plans.get(planPosition).getStartDate()
                                            + " - " + plans.get(planPosition).getEndDate() + ") ");
                                    startActivity(calIntent);
                                }
                            });
                            alert11.findViewById(R.id.single_place_window_dialog_remove).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert11.dismiss();
                                    plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedHotels().remove(position);
                                    SharedPreferences.Editor prefsEditor = pref.edit();
                                    Iterator it = plans.iterator();
                                    HashSet<String> set = new HashSet();
                                    while (it.hasNext()) {
                                        Gson gson = new Gson();
                                        set.add(gson.toJson(it.next()));
                                        prefsEditor.putStringSet("plans", set);
                                        prefsEditor.commit();
                                        singlePlaceRecyclerViewForHotels.getAdapter().notifyDataSetChanged();
                                    }
                                }
                            });


                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }

        singlePlaceRecyclerViewForOyoRooms = (RecyclerView) findViewById(R.id.single_place_window_oyo_rooms);
        singlePlaceRecyclerViewForOyoRooms.setLayoutManager(new CenterZoomLayoutManager(SinglePlaceWindow.this, LinearLayoutManager.HORIZONTAL, false));
        if (placePosition != -1) {
            singlePlaceRecyclerViewForOyoRooms.setAdapter(new PointOfInterestAdapter(plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedOyoRooms(), R.layout.list_item_poi, SinglePlaceWindow.this));
            singlePlaceRecyclerViewForOyoRooms.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), singlePlaceRecyclerViewForOyoRooms, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position) {

                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(SinglePlaceWindow.this);
                            builder1.setCancelable(true);
                            builder1.setNegativeButton(
                                    "Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            builder1.setTitle("Options");
                            builder1.setView(R.layout.single_place_window_dialog);
                            final AlertDialog alert11 = builder1.create();

                            alert11.show();
                            alert11.findViewById(R.id.single_place_window_dialog_add_to_calender).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(SinglePlaceWindow.this, "Select Date", Toast.LENGTH_LONG).show();
                                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                    calIntent.setType("vnd.android.cursor.item/event");
                                    calIntent.putExtra(CalendarContract.Events.TITLE, "Stay At " + plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedOyoRooms().get(position).getPoiName());
                                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getPlaceName());
                                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, plans.get(planPosition).getPlanName() + " (" + plans.get(planPosition).getStartDate()
                                            + " - " + plans.get(planPosition).getEndDate() + ") ");
                                    startActivity(calIntent);
                                }
                            });
                            alert11.findViewById(R.id.single_place_window_dialog_remove).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert11.dismiss();
                                    plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedOyoRooms().remove(position);
                                    SharedPreferences.Editor prefsEditor = pref.edit();
                                    Iterator it = plans.iterator();
                                    HashSet<String> set = new HashSet();
                                    while (it.hasNext()) {
                                        Gson gson = new Gson();
                                        set.add(gson.toJson(it.next()));
                                        prefsEditor.putStringSet("plans", set);
                                        prefsEditor.commit();
                                        singlePlaceRecyclerViewForOyoRooms.getAdapter().notifyDataSetChanged();
                                    }

                                }
                            });


                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }

        singlePlaceRecyclerViewForRestaurants = (RecyclerView) findViewById(R.id.single_place_window_restaurants);
        singlePlaceRecyclerViewForRestaurants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (placePosition != -1) {
            singlePlaceRecyclerViewForRestaurants.setAdapter(new SingleRestaurantAdapter(plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedRestaurants(), R.layout.list_item_single_restaurant, getApplicationContext(), SinglePlaceWindow.this));
        }

    }


    @Override
    public void onOpenButtonClicked(int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedRestaurants().get(position)
                .getRestaurant().getRedirectURL().toString())));
    }

    @Override
    public void onSaveToggleButtonClicked(final int position, View view) {

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(SinglePlaceWindow.this);
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setTitle("Options");
        builder1.setView(R.layout.single_place_window_dialog);
        final AlertDialog alert11 = builder1.create();

        alert11.show();
        alert11.findViewById(R.id.single_place_window_dialog_add_to_calender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SinglePlaceWindow.this, "Select Date", Toast.LENGTH_LONG).show();
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.Events.TITLE, "Dine At " + plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedRestaurants().get(position)
                        .getRestaurant().getName().toString());
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getPlaceName());
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, plans.get(planPosition).getPlanName() + " (" + plans.get(planPosition).getStartDate()
                        + " - " + plans.get(planPosition).getEndDate() + ") ");
                startActivity(calIntent);
            }
        });
        alert11.findViewById(R.id.single_place_window_dialog_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert11.dismiss();
                plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(placePosition).getSelectedRestaurants().remove(position);
                SharedPreferences.Editor prefsEditor = pref.edit();
                Iterator it = plans.iterator();
                HashSet<String> set = new HashSet();
                while (it.hasNext()) {
                    Gson gson = new Gson();
                    set.add(gson.toJson(it.next()));
                    prefsEditor.putStringSet("plans", set);
                    prefsEditor.commit();
                    singlePlaceRecyclerViewForRestaurants.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }
}
