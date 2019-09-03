package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sajagjain.sajagjain.majorproject.adapter.BusAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.FlightAdapter;
import com.sajagjain.sajagjain.majorproject.adapter.TrainAdapter;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MoreThanOneOptionDescription extends AppCompatActivity implements FlightAdapter.FlightAdapterListener, TrainAdapter.TrainAdapterListener, BusAdapter.BusAdapterListener {

    List<TravelPlan> plans = new ArrayList<>();
    SharedPreferences pref;
    int planPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_more_than_one_option_description);

        planPosition = getIntent().getIntExtra("planposition", -1);

        pref = getApplicationContext().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
        Gson gson = new Gson();
        final Set<String> planFromSharedPref = pref.getStringSet("plans", null);

        if (planFromSharedPref != null) {
            Iterator it = planFromSharedPref.iterator();
            while (it.hasNext()) {
                plans.add(gson.fromJson((String) it.next(), TravelPlan.class));
            }
        }


        final RecyclerView recyclerViewForFlights = (RecyclerView) findViewById(R.id.list_item_more_than_one_option_description_recycler_view_for_flights);
        recyclerViewForFlights.setLayoutManager(new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewForFlights.setAdapter(new FlightAdapter(plans.get(planPosition).getDataForSavingInTPA().getSelectedFlights(), R.layout.list_item_flight, MoreThanOneOptionDescription.this, MoreThanOneOptionDescription.this));
        recyclerViewForFlights.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForFlights, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MoreThanOneOptionDescription.this);
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
                                Toast.makeText(MoreThanOneOptionDescription.this, "Just Confirm Event Save", Toast.LENGTH_LONG).show();
                                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                calIntent.setType("vnd.android.cursor.item/event");
                                calIntent.putExtra(CalendarContract.Events.TITLE, "Your have a Flight to catch " + plans.get(planPosition).getDataForSavingInTPA().getSelectedFlights().get(position).getAirline());
                                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, plans.get(planPosition).getDataForSavingInTPA().getSelectedFlights().get(position).getFlightSource());
                                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, plans.get(planPosition).getPlanName() + " (" + plans.get(planPosition).getStartDate()
                                        + " - " + plans.get(planPosition).getEndDate() + ") ");

                                String startDate = plans.get(planPosition).getDataForSavingInTPA()
                                        .getSelectedFlights().get(position).getFightDepartureDate().toString();
                                String startHour = plans.get(planPosition).getDataForSavingInTPA()
                                        .getSelectedFlights().get(position).getDepartureTime();
                                Toast.makeText(MoreThanOneOptionDescription.this, startHour+" "+ startDate, Toast.LENGTH_LONG).show();
                                Date fulldate=null;
                                try {

                                    fulldate = new SimpleDateFormat("HH:mm yyyyMMdd").parse(startHour+" "+startDate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(fulldate!=null)
                                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fulldate.getTime());

                                startActivity(calIntent);
                            }
                        });
                        alert11.findViewById(R.id.single_place_window_dialog_remove).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alert11.dismiss();
                                plans.get(planPosition).getDataForSavingInTPA().getSelectedFlights().remove(position);
                                SharedPreferences.Editor prefsEditor = pref.edit();
                                Iterator it = plans.iterator();
                                HashSet<String> set = new HashSet();
                                while (it.hasNext()) {
                                    Gson gson = new Gson();
                                    set.add(gson.toJson(it.next()));
                                    prefsEditor.putStringSet("plans", set);
                                    prefsEditor.commit();
                                    recyclerViewForFlights.getAdapter().notifyDataSetChanged();
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

        final RecyclerView recyclerViewForTrains = (RecyclerView) findViewById(R.id.list_item_more_than_one_option_description_recycler_view_for_trains);
        recyclerViewForTrains.setLayoutManager(new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewForTrains.setAdapter(new TrainAdapter(plans.get(planPosition).getDataForSavingInTPA().getSelectedTrains(), R.layout.list_item_train, MoreThanOneOptionDescription.this, MoreThanOneOptionDescription.this));
        recyclerViewForTrains.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForTrains, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MoreThanOneOptionDescription.this);
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
                                Toast.makeText(MoreThanOneOptionDescription.this, "Just Confirm Event Save", Toast.LENGTH_LONG).show();
                                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                calIntent.setType("vnd.android.cursor.item/event");
                                calIntent.putExtra(CalendarContract.Events.TITLE, "You Have a Train to catch " + plans.get(planPosition).getDataForSavingInTPA().getSelectedTrains().get(position).getTrainName());
                                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, plans.get(planPosition).getDataForSavingInTPA().getSelectedTrains().get(position).getFromStation().getStationName());
                                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, plans.get(planPosition).getPlanName() + " (" + plans.get(planPosition).getStartDate()
                                        + " - " + plans.get(planPosition).getEndDate() + ") ");

                                String startDate = plans.get(planPosition).getDataForSavingInTPA()
                                        .getSelectedTrains().get(position).getTrainDate().toString();
                                String startHour = plans.get(planPosition).getDataForSavingInTPA()
                                        .getSelectedTrains().get(position).getDepartureTime();
                                Date fulldate=null;
                                try {

                                    SimpleDateFormat input = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
                                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

                                    Date sirfDateInput = input.parse(startDate);
                                    String sirfDateOutput = output.format(sirfDateInput);
                                    fulldate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(sirfDateOutput + " " + startHour);
                                    Toast.makeText(MoreThanOneOptionDescription.this, sirfDateOutput + " " + startHour, Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(fulldate!=null)
                                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fulldate.getTime());

                                startActivity(calIntent);
                            }
                        });
                        alert11.findViewById(R.id.single_place_window_dialog_remove).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alert11.dismiss();
                                plans.get(planPosition).getDataForSavingInTPA().getSelectedTrains().remove(position);
                                SharedPreferences.Editor prefsEditor = pref.edit();
                                Iterator it = plans.iterator();
                                HashSet<String> set = new HashSet();
                                while (it.hasNext()) {
                                    Gson gson = new Gson();
                                    set.add(gson.toJson(it.next()));
                                    prefsEditor.putStringSet("plans", set);
                                    prefsEditor.commit();
                                    recyclerViewForTrains.getAdapter().notifyDataSetChanged();
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

        final RecyclerView recyclerViewForBuses = (RecyclerView) findViewById(R.id.list_item_more_than_one_option_description_recycler_view_for_buses);
        recyclerViewForBuses.setLayoutManager(new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewForBuses.setAdapter(new BusAdapter(plans.get(planPosition).getDataForSavingInTPA().getSelectedBuses(), R.layout.list_item_bus, MoreThanOneOptionDescription.this, MoreThanOneOptionDescription.this));
        recyclerViewForBuses.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewForBuses, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MoreThanOneOptionDescription.this);
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
                                Toast.makeText(MoreThanOneOptionDescription.this, "Just Confirm Event Save", Toast.LENGTH_LONG).show();
                                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                calIntent.setType("vnd.android.cursor.item/event");
                                calIntent.putExtra(CalendarContract.Events.TITLE, "You have a bus to catch " + plans.get(planPosition).getDataForSavingInTPA().getSelectedBuses().get(position).getBusTravelsName());
                                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, plans.get(planPosition).getDataForSavingInTPA().getSelectedBuses().get(position).getBusSource());
                                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, plans.get(planPosition).getPlanName() + " (" + plans.get(planPosition).getStartDate()
                                        + " - " + plans.get(planPosition).getEndDate() + ") ");

                                Toast.makeText(MoreThanOneOptionDescription.this, plans.get(planPosition).getDataForSavingInTPA().getSelectedBuses().get(position).getBusDepartureTime(), Toast.LENGTH_LONG).show();

                                String startDate = plans.get(planPosition).getDataForSavingInTPA()
                                        .getSelectedBuses().get(position).getBusDepartureTime().toString();
                                Date fulldate=null;
                                try {

                                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

                                    Date sirfDateInput = input.parse(startDate);
                                    String sirfDateOutput = output.format(sirfDateInput);
                                    fulldate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(sirfDateOutput);
                                    Toast.makeText(MoreThanOneOptionDescription.this, sirfDateOutput, Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(fulldate!=null)
                                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fulldate.getTime());


                                startActivity(calIntent);
                            }
                        });
                        alert11.findViewById(R.id.single_place_window_dialog_remove).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alert11.dismiss();
                                plans.get(planPosition).getDataForSavingInTPA().getSelectedBuses().remove(position);
                                SharedPreferences.Editor prefsEditor = pref.edit();
                                Iterator it = plans.iterator();
                                HashSet<String> set = new HashSet();
                                while (it.hasNext()) {
                                    Gson gson = new Gson();
                                    set.add(gson.toJson(it.next()));
                                    prefsEditor.putStringSet("plans", set);
                                    prefsEditor.commit();
                                    recyclerViewForBuses.getAdapter().notifyDataSetChanged();
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

    @Override
    public void onFlightSaveButtonClick(int position, View view) {

    }

    @Override
    public void onTrainSaveButtonClick(int position, View view) {

    }

    @Override
    public void onBusSaveButtonClick(int position, View view) {

    }

    @Override
    public void onEverythingElseClicked(int position, View view) {

    }
}
