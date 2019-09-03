package com.sajagjain.sajagjain.majorproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sajagjain.sajagjain.majorproject.adapter.AirportNearAdapter;
import com.sajagjain.sajagjain.majorproject.model.AirportResponse;
import com.sajagjain.sajagjain.majorproject.model.AirportResponseResponse;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityFlight extends AppCompatActivity {

    MaterialSearchView searchView;
    ListView listview;
    List<String> list = new ArrayList<>();
    RecyclerView recyclerViewAirport;
    List<AirportResponse> responses;
    TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flight);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        responses = this.getAirports();
        if (responses.size() > 0) {
            list.clear();
            for (AirportResponse res : responses) {
                if (res.getName() != null || !res.getName().equals(""))
                    list.add(res.getName() + "(" + res.getIata() + ") - "+res.getCity());
            }
        }

        //Set NearBy Airports based on GPS or city Location

        recyclerViewAirport = (RecyclerView) findViewById(R.id.airport_recycler_view);
        recyclerViewAirport.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //coordinate find
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
        String previouslyEncodedCity = pref.getString("whatisyourcurrentcity", "");

        if (!previouslyEncodedCity.equals("")) {
            String previouslyEncodedCityLat = pref.getString("whatisyourcurrentcitylat", ""); //22.7196
            String previouslyEncodedCityLng = pref.getString("whatisyourcurrentcitylng", ""); //75.8577


            //find coordinates of the user then perform the steps below

            List<AirportResponse> airportResponse = new ArrayList<>();
            airportResponse.clear();
            LatLng user = new LatLng(Double.parseDouble(previouslyEncodedCityLat)
                    , Double.parseDouble(previouslyEncodedCityLng));

            for (AirportResponse a : responses) {
                Double value = distance(user.latitude, user.longitude, Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()), "Km");
                if (value < 200) {
                    airportResponse.add(a);
                    Log.d("airportnamewithvalue", a.getName() + "      " + value);
                }
            }


            recyclerViewAirport.setAdapter(new AirportNearAdapter(airportResponse, R.layout.list_item_near_by_airports, getApplicationContext()));
            final List<AirportResponse> list1 = airportResponse;
            recyclerViewAirport.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), recyclerViewAirport, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String item = list1.get(position).getIata();
                            Intent intent = new Intent();
                            intent.putExtra("datasourcevalue1", item);
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Please Select Current City");
            builder1.setCancelable(true);
            builder1.setView(R.layout.currentcitydialog);
            builder1.setPositiveButton(
                    "Set City",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
                            String previouslyEncodedCity = pref.getString("whatisyourcurrentcity", "");

                            if (!previouslyEncodedCity.equals("")) {
                                String previouslyEncodedCityLat = pref.getString("whatisyourcurrentcitylat", ""); //22.7196
                                String previouslyEncodedCityLng = pref.getString("whatisyourcurrentcitylng", ""); //75.8577


                                //find coordinates of the user then perform the steps below

                                List<AirportResponse> airportResponse = new ArrayList<>();
                                airportResponse.clear();
                                LatLng user = new LatLng(Double.parseDouble(previouslyEncodedCityLat)
                                        , Double.parseDouble(previouslyEncodedCityLng));

                                for (AirportResponse a : responses) {
                                    Double value = distance(user.latitude, user.longitude, Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()), "Km");
                                    if (value < 200) {
                                        airportResponse.add(a);
                                        Log.d("airportnamewithvalue", a.getName() + "      " + value);
                                    }
                                }


                                recyclerViewAirport.setAdapter(new AirportNearAdapter(airportResponse, R.layout.list_item_near_by_airports, getApplicationContext()));
                                final List<AirportResponse> list1 = airportResponse;
                                recyclerViewAirport.addOnItemTouchListener(
                                        new RecyclerItemClickListener(getApplicationContext(), recyclerViewAirport, new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                String item = list1.get(position).getIata();
                                                String city=list1.get(position).getCity();

                                                Intent intent = new Intent();
                                                intent.putExtra("datasourcevalue1", item);
                                                intent.putExtra("datasourcevaluecity1",city);
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {
                                                // do whatever
                                            }
                                        })
                                );
                            }
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            dialogText=(TextView)alert11.findViewById(R.id.dialog_text);
            dialogText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                .setCountry("IN")
                                .build();
                        Intent intent=new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .setFilter(typeFilter)
                                .build(SearchActivityFlight.this);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivityForResult(intent, 1);
                    } catch (GooglePlayServicesRepairableException e) {

                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }
                }
            });
            Toast.makeText(SearchActivityFlight.this,"Put your current city in settings to see nearby airports",Toast.LENGTH_LONG).show();
        }


        listview = (ListView) findViewById(R.id.listviewflight);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        searchView = (MaterialSearchView) findViewById(R.id.searchview);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                listview = (ListView) findViewById(R.id.listviewflight);
                ArrayAdapter adapter = new ArrayAdapter(SearchActivityFlight.this, android.R.layout.simple_list_item_1, list);
                listview.setAdapter(adapter);

            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null || !newText.isEmpty()) {
                    List<String> listFnd = new ArrayList<String>();

                    for (String item : list) {
                        if (item.toLowerCase().contains(newText.toLowerCase())) {
                            listFnd.add(item);
                        }
                    }

                    ArrayAdapter adapter = new ArrayAdapter<>(SearchActivityFlight.this
                            , android.R.layout.simple_list_item_1, listFnd);
                    listview.setAdapter(adapter);

                } else {

                    ArrayAdapter adapter = new ArrayAdapter<>(SearchActivityFlight.this
                            , android.R.layout.simple_list_item_1, list);
                    listview.setAdapter(adapter);


                }
                return true;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                String city=(String)parent.getAdapter().getItem(position);

                for (AirportResponse i : responses) {
                    if (item.equals(i.getName() + "(" + i.getIata() + ") - "+i.getCity())) {
                        item = i.getIata();
                        city=i.getCity();
                    }
                }
                Log.d("item", item);
                Intent intent = new Intent();
                intent.putExtra("datasourcevalue1", item);
                intent.putExtra("datasourcevaluecity1",city);
                Toast.makeText(SearchActivityFlight.this, item, Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    public static List<AirportResponse> getAirports() {
        String json = null;
        try {
            json = Jsoup.connect("https://firebasestorage.googleapis.com/v0/b/sajag-travel-buddy.appspot.com/o/IATADatabaseIndia.json?alt=media&token=9b447973-85ca-4d0d-9fd2-4a2522bedf10")
                    .ignoreContentType(true).execute().body();

            JSONObject jsonObject = new JSONObject(json);
            AirportResponseResponse response = AirportResponseResponse.fromJson(jsonObject);
            return response.getResults();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                String placeName=place.getName().toString();
                float placeRating=place.getRating();
                String placeAddress=place.getAddress().toString();
                LatLng placeLatLng=place.getLatLng();
                String placeId=place.getId();
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref1.edit();
                editor.putString("whatisyourcurrentcity",placeName.toString());//Khurai
                editor.putString("whatisyourcurrentcitylat",placeLatLng.latitude+"");
                editor.putString("whatisyourcurrentcitylng",placeLatLng.longitude+"");
                editor.commit();
                dialogText.setText(placeName.toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(SearchActivityFlight.this,status.getStatusMessage(),Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
//rasoma chauraha medanta hospital orange lily hotel thoda aage cp plaza