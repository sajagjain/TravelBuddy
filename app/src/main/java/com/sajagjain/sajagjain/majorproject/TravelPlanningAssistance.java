package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.sajagjain.sajagjain.majorproject.adapter.TravelPlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.Bus;
import com.sajagjain.sajagjain.majorproject.model.Flight;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestFirstStep;
import com.sajagjain.sajagjain.majorproject.model.SinglePlaceSavedInTPA;
import com.sajagjain.sajagjain.majorproject.model.SingleRestaurantFirstStep;
import com.sajagjain.sajagjain.majorproject.model.Train;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TravelPlanningAssistance extends AppCompatActivity implements TravelPlanAdapter.TravelPlanAdapterListener {

    Button travelPlanAdd;
    EditText planName, planSource, planDestination, planStartDate, planEndDate;
    EditText planName2, planSource2, planDestination2, planStartDate2, planEndDate2;
    RecyclerView travelPlanRecyclerView;
    EditText planCreationDate, planSizeInKb;
    LatLng source1LatLng, destination1LatLng, source2LatLng, destination2LatLng;
    List<TravelPlan> plans = new ArrayList<>();
    SharedPreferences pref;
    Calendar calendar;
    int year, month, day;
    TravelPlan init = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_travel_planning_assistance);

        //Linking values to views
        travelPlanAdd = (Button) findViewById(R.id.travel_plan_add_button);

        //Calendar Instances
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        //Initial Value from Shared Preference

        travelPlanRecyclerView = (RecyclerView) findViewById(R.id.travel_plan_recycler_view);
        travelPlanRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
//        travelPlanRecyclerView.addItemDecoration(new DividerItemDecoration(this, 1));


        pref = getApplicationContext().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
        Gson gson = new Gson();
        Set<String> planFromSharedPref = pref.getStringSet("plans", null);

        if (planFromSharedPref != null) {
            Iterator it = planFromSharedPref.iterator();
            while (it.hasNext()) {
                plans.add(gson.fromJson((String) it.next(), TravelPlan.class));
            }
        }

        travelPlanRecyclerView.setAdapter(new TravelPlanAdapter(plans, R.layout.list_item_travel_plan, getApplicationContext(), TravelPlanningAssistance.this));


        travelPlanAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder1 = new AlertDialog.Builder(TravelPlanningAssistance.this);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Create Plan",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String singlePlanName = planName.getText().toString();
                                String singlePlanSource = planSource.getText().toString();
                                String singlePlanDestination = planDestination.getText().toString();
                                String singlePlanStartDate = planStartDate.getText().toString();
                                String singlePlanEndDate = planEndDate.getText().toString();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date date1 = sdf.parse(singlePlanStartDate);
                                    Date date2 = sdf.parse(singlePlanEndDate);


                                    if (singlePlanName.equalsIgnoreCase("") || singlePlanSource.equalsIgnoreCase("")
                                            || singlePlanDestination.equalsIgnoreCase("") || date1.after(date2)) {
                                        if (date1.after(date2)) {
                                            Toast.makeText(TravelPlanningAssistance.this, "Destination Date cannot come before Start Date", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(TravelPlanningAssistance.this, "Please Enter The Required Details", Toast.LENGTH_LONG).show();
                                        }
                                    } else {

                                        SinglePlaceSavedInTPA planSourceData = new SinglePlaceSavedInTPA(source1LatLng, planSource.getText().toString(), new ArrayList<PointOfInterestFirstStep>()
                                                , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                                , new ArrayList<String>(), new ArrayList<twitter4j.Status>());

                                        SinglePlaceSavedInTPA planDestinationData = new SinglePlaceSavedInTPA(destination1LatLng, planDestination.getText().toString(), new ArrayList<PointOfInterestFirstStep>()
                                                , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                                , new ArrayList<String>(), new ArrayList<twitter4j.Status>());


                                        List<SinglePlaceSavedInTPA> placeList = new ArrayList<>();
                                        placeList.add(planSourceData);
                                        placeList.add(planDestinationData);

                                        OverAllDataForSavingInTPA overAllDataForSavingInTPA = new OverAllDataForSavingInTPA(placeList, new ArrayList<Flight>(), new ArrayList<Train>(), new ArrayList<Bus>());

                                        TravelPlan plan = new TravelPlan(singlePlanName, singlePlanSource, singlePlanDestination, singlePlanStartDate, singlePlanEndDate, Calendar.getInstance().getTimeInMillis(), overAllDataForSavingInTPA);
                                        init = plan;
                                        planName.setText("");
                                        planSource.setText("");
                                        planDestination.setText("");

                                        plans.add(plan);
                                        SharedPreferences.Editor prefsEditor = pref.edit();
                                        Iterator it = plans.iterator();


                                        travelPlanRecyclerView.getAdapter().notifyDataSetChanged();

                                        HashSet<String> set = new HashSet();
                                        while (it.hasNext()) {
                                            Gson gson = new Gson();
                                            set.add(gson.toJson(it.next()));
                                            prefsEditor.putStringSet("plans", set);

                                        }
                                        prefsEditor.commit();
                                        if (set.size() != plans.size()) {
                                            Toast.makeText(TravelPlanningAssistance.this, "Some Problem Occoured Try Changing Name or Dates", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (ParseException e) {

                                }
                            }
                        });
                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener()

                        {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setTitle("New Plan");
                builder1.setMessage("*Double Tap on Date to open Date Picker\n*Double Tap on Places to Open Place Picker");
                builder1.setView(R.layout.new_travel_plan_dialog_layout);
                final AlertDialog alert11 = builder1.create();
                alert11.show();
                planName = (EditText) alert11.findViewById(R.id.travel_plan_dialog_plan_name);
                planSource = (EditText) alert11.findViewById(R.id.travel_plan_dialog_start_city);
                planDestination = (EditText) alert11.findViewById(R.id.travel_plan_dialog_end_city);
                planStartDate = (EditText) alert11.findViewById(R.id.travel_plan_dialog_start_date);
                planEndDate = (EditText) alert11.findViewById(R.id.travel_plan_dialog_end_date);

                planSource.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                    .setCountry("IN")
                                    .build();
                            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .setFilter(typeFilter)
                                    .build(TravelPlanningAssistance.this);

                            startActivityForResult(intent, 00);
                        } catch (GooglePlayServicesRepairableException e) {
                            // TODO: Handle the error.
                        } catch (GooglePlayServicesNotAvailableException e) {
                            // TODO: Handle the error.
                        }
                    }
                });
                planDestination.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                    .setCountry("IN")
                                    .build();
                            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .setFilter(typeFilter)
                                    .build(TravelPlanningAssistance.this);

                            startActivityForResult(intent, 01);
                        } catch (GooglePlayServicesRepairableException e) {
                            // TODO: Handle the error.
                        } catch (GooglePlayServicesNotAvailableException e) {
                            // TODO: Handle the error.
                        }
                    }
                });
                planStartDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate1(v);

                    }
                });
                planEndDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate2(v);

                    }
                });
            }
        });


    }

    @Override
    public void onMoreOptionMenuClicked(final int position, View view) {

        final PopupMenu popup = new PopupMenu(TravelPlanningAssistance.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.remove_plan) {

                    plans.remove(position);

                    SharedPreferences.Editor prefsEditor = pref.edit();
                    Iterator it1 = plans.iterator();
                    Toast.makeText(TravelPlanningAssistance.this, "plansarraysize" + plans.size(), Toast.LENGTH_LONG).show();
                    HashSet<String> set = new HashSet();
                    if (plans.size() != 0) {
                        while (it1.hasNext()) {
                            Gson gson1 = new Gson();
                            set.add(gson1.toJson(it1.next()));
                            prefsEditor.putStringSet("plans", set);

                        }
                    } else {
                        prefsEditor.clear();
                    }
                    prefsEditor.commit();
                    travelPlanRecyclerView.getAdapter().notifyItemRemoved(position);
                    return true;
                }
                if (id == R.id.edit_plan) {

                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(TravelPlanningAssistance.this);
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Edit Plan",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String singlePlanName = planName2.getText().toString();
                                    String singlePlanSource = planSource2.getText().toString();
                                    String singlePlanDestination = planDestination2.getText().toString();
                                    String singlePlanStartDate = planStartDate2.getText().toString();
                                    String singlePlanEndDate = planEndDate2.getText().toString();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    try {
                                        Date date1 = sdf.parse(singlePlanStartDate);
                                        Date date2 = sdf.parse(singlePlanEndDate);


                                        if (singlePlanName.equalsIgnoreCase("") || singlePlanSource.equalsIgnoreCase("")
                                                || singlePlanDestination.equalsIgnoreCase("") || date1.after(date2)) {
                                            if (date1.after(date2)) {
                                                Toast.makeText(TravelPlanningAssistance.this, "Destination Date cannot come before Start Date", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(TravelPlanningAssistance.this, "Please Enter The Required Details", Toast.LENGTH_LONG).show();
                                            }
                                        } else {

                                            SinglePlaceSavedInTPA planSourceData;
                                            SinglePlaceSavedInTPA planDestinationData;

                                            if(source2LatLng!=null) {
                                                planSourceData = new SinglePlaceSavedInTPA(source2LatLng, planSource2.getText().toString(), new ArrayList<PointOfInterestFirstStep>()
                                                        , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                                        , new ArrayList<String>(), new ArrayList<twitter4j.Status>());
                                            }else{
                                                planSourceData = new SinglePlaceSavedInTPA(plans.get(position).getDataForSavingInTPA().getSavedPlaces().get(0).getPlaceLatLng(), planSource2.getText().toString(), new ArrayList<PointOfInterestFirstStep>()
                                                        , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                                        , new ArrayList<String>(), new ArrayList<twitter4j.Status>());
                                            }
                                            if(destination2LatLng!=null) {
                                                planDestinationData = new SinglePlaceSavedInTPA(destination2LatLng, planDestination2.getText().toString(), new ArrayList<PointOfInterestFirstStep>()
                                                        , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                                        , new ArrayList<String>(), new ArrayList<twitter4j.Status>());
                                            }else{
                                                planDestinationData = new SinglePlaceSavedInTPA(plans.get(position).getDataForSavingInTPA().getSavedPlaces().get(1).getPlaceLatLng(), planDestination2.getText().toString(), new ArrayList<PointOfInterestFirstStep>()
                                                        , new ArrayList<PointOfInterestFirstStep>(), new ArrayList<PointOfInterestFirstStep>(), new ArrayList<SingleRestaurantFirstStep>()
                                                        , new ArrayList<String>(), new ArrayList<twitter4j.Status>());
                                            }


                                            List<SinglePlaceSavedInTPA> placeList = new ArrayList<>();
                                            placeList.add(planSourceData);
                                            placeList.add(planDestinationData);

                                            OverAllDataForSavingInTPA overAllDataForSavingInTPA = new OverAllDataForSavingInTPA(placeList, new ArrayList<Flight>(), new ArrayList<Train>(), new ArrayList<Bus>());

                                            TravelPlan plan = new TravelPlan(singlePlanName, singlePlanSource, singlePlanDestination, singlePlanStartDate, singlePlanEndDate, Calendar.getInstance().getTimeInMillis(), overAllDataForSavingInTPA);
                                            init = plan;
                                            planName2.setText("");
                                            planSource2.setText("");
                                            planDestination2.setText("");
                                            plans.remove(position);
                                            plans.add(position, plan);
                                            SharedPreferences.Editor prefsEditor = pref.edit();
                                            Iterator it = plans.iterator();


                                            travelPlanRecyclerView.getAdapter().notifyDataSetChanged();


                                            HashSet<String> set = new HashSet();
                                            while (it.hasNext()) {
                                                Gson gson = new Gson();
                                                set.add(gson.toJson(it.next()));
                                                prefsEditor.putStringSet("plans", set);

                                            }
                                            prefsEditor.commit();
                                            if (set.size() != plans.size()) {
                                                Toast.makeText(TravelPlanningAssistance.this, "Some Problem Occoured Try Changing Name or Dates", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener()

                            {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder1.setTitle("Edit Plan");
                    builder1.setMessage("*Double Tap on Date to Open Date Picker\n*Double Tap on Places to Open Place Picker");
                    builder1.setView(R.layout.new_travel_plan_dialog_layout);
                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                    planName2 = (EditText) alert11.findViewById(R.id.travel_plan_dialog_plan_name);
                    planSource2 = (EditText) alert11.findViewById(R.id.travel_plan_dialog_start_city);
                    planDestination2 = (EditText) alert11.findViewById(R.id.travel_plan_dialog_end_city);
                    planStartDate2 = (EditText) alert11.findViewById(R.id.travel_plan_dialog_start_date);
                    planEndDate2 = (EditText) alert11.findViewById(R.id.travel_plan_dialog_end_date);

                    planSource2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                        .setCountry("IN")
                                        .build();
                                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .setFilter(typeFilter)
                                        .build(TravelPlanningAssistance.this);

                                startActivityForResult(intent, 10);
                            } catch (GooglePlayServicesRepairableException e) {
                                // TODO: Handle the error.
                            } catch (GooglePlayServicesNotAvailableException e) {
                                // TODO: Handle the error.
                            }
                        }
                    });
                    planDestination2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                        .setCountry("IN")
                                        .build();
                                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .setFilter(typeFilter)
                                        .build(TravelPlanningAssistance.this);

                                startActivityForResult(intent, 11);
                            } catch (GooglePlayServicesRepairableException e) {
                                // TODO: Handle the error.
                            } catch (GooglePlayServicesNotAvailableException e) {
                                // TODO: Handle the error.
                            }
                        }
                    });

                    planStartDate2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setDate3(v);

                        }
                    });
                    planEndDate2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setDate4(v);

                        }
                    });

                    TravelPlan editThis = plans.get(position);
                    planName2.setText(editThis.getPlanName());
                    planSource2.setText(editThis.getTravelSource());
                    planDestination2.setText(editThis.getTravelDestination());
                    planStartDate2.setText(editThis.getStartDate());
                    planEndDate2.setText(editThis.getEndDate());


                    return true;
                }
                if (id == R.id.details) {

                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(TravelPlanningAssistance.this);
                    builder1.setCancelable(true);
                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener()

                            {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder1.setTitle("Details");
                    builder1.setView(R.layout.new_trravel_plan_dialog_layout_for_details);
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    TravelPlan editThis = plans.get(position);
                    planCreationDate = (EditText) alert11.findViewById(R.id.travel_plan_dialog_plan_creation_date);
                    planSizeInKb = (EditText) alert11.findViewById(R.id.travel_plan_dialog_plan_size);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream out = null;
                    try {
                        out = new ObjectOutputStream(baos);
                        out.writeObject(editThis);
                        out.close();
                        int len = baos.toByteArray().length;
                        if (len < 1024) {
                            planSizeInKb.setText(len + " Bytes");
                        } else {
                            float len1 = len / 1024;
                            if (len1 < 1024) {
                                planSizeInKb.setText(len1 + " KB");
                            } else {
                                len1 = len1 / 1024;
                                planSizeInKb.setText(len1 + " MB");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    planCreationDate.setText(new Date(editThis.getCreationDate()).toString());
                    return true;
                }
                Toast.makeText(TravelPlanningAssistance.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        popup.show();//showing popup menu

    }

    @Override
    public void onEverythingElseClicked(int position, View view) {
        startActivity(new Intent(TravelPlanningAssistance.this, SinglePlanWindow.class).putExtra("position", position));
    }

    @SuppressWarnings("deprecation")
    public void setDate1(View view) {
        showDialog(110);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setDate2(View view) {
        showDialog(111);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setDate3(View view) {
        showDialog(112);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void setDate4(View view) {
        showDialog(113);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 110) {
            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener1, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dialog;
        } else if (id == 111) {
            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener2, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dialog;
        } else if (id == 112) {
            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener3, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dialog;
        } else if (id == 113) {
            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener4, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate1(arg1, arg2 + 1, arg3);
                }
            };

    private DatePickerDialog.OnDateSetListener myDateListener2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate2(arg1, arg2 + 1, arg3);
                }
            };

    private DatePickerDialog.OnDateSetListener myDateListener3 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate3(arg1, arg2 + 1, arg3);
                }
            };

    private DatePickerDialog.OnDateSetListener myDateListener4 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate4(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate1(int year, int month, int day) {

        String dayparse = day + "";
        String monthparse = month + "";
        if (day < 10) {
            dayparse = "0" + day;
        }
        if (month < 10) {
            monthparse = "0" + month;
        }
        planStartDate.setText(dayparse + "/" + monthparse + "/" + year);

    }

    private void showDate2(int year, int month, int day) {

        String dayparse = day + "";
        String monthparse = month + "";
        if (day < 10) {
            dayparse = "0" + day;
        }
        if (month < 10) {
            monthparse = "0" + month;
        }
        planEndDate.setText(dayparse + "/" + monthparse + "/" + year);
    }

    private void showDate3(int year, int month, int day) {

        String dayparse = day + "";
        String monthparse = month + "";
        if (day < 10) {
            dayparse = "0" + day;
        }
        if (month < 10) {
            monthparse = "0" + month;
        }
        planStartDate2.setText(dayparse + "/" + monthparse + "/" + year);
    }

    private void showDate4(int year, int month, int day) {

        String dayparse = day + "";
        String monthparse = month + "";
        if (day < 10) {
            dayparse = "0" + day;
        }
        if (month < 10) {
            monthparse = "0" + month;
        }
        planEndDate2.setText(dayparse + "/" + monthparse + "/" + year);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 00) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                final String placeName = place.getName().toString();
                final float placeRating = place.getRating();
                final String placeAddress = place.getAddress().toString();
                final LatLng placeLatLng = place.getLatLng();
                final String placeId = place.getId();


                planSource.setText(place.getName());
                source1LatLng = placeLatLng;

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(TravelPlanningAssistance.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
        if (requestCode == 01) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                final String placeName = place.getName().toString();
                final float placeRating = place.getRating();
                final String placeAddress = place.getAddress().toString();
                final LatLng placeLatLng = place.getLatLng();
                final String placeId = place.getId();

                destination1LatLng = placeLatLng;
                planDestination.setText(place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(TravelPlanningAssistance.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                final String placeName = place.getName().toString();
                final float placeRating = place.getRating();
                final String placeAddress = place.getAddress().toString();
                final LatLng placeLatLng = place.getLatLng();
                final String placeId = place.getId();

                planSource2.setText(place.getName());
                source2LatLng = placeLatLng;

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(TravelPlanningAssistance.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                final String placeName = place.getName().toString();
                final float placeRating = place.getRating();
                final String placeAddress = place.getAddress().toString();
                final LatLng placeLatLng = place.getLatLng();
                final String placeId = place.getId();

                planDestination2.setText(place.getName());
                destination2LatLng = placeLatLng;

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(TravelPlanningAssistance.this, status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }

    }

}


