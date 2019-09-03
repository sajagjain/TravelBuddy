package com.sajagjain.sajagjain.majorproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.sajagjain.sajagjain.majorproject.model.TrainStationFirstStep;
import com.sajagjain.sajagjain.majorproject.model.TrainStationResponse;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityTrain2 extends AppCompatActivity {

    MaterialSearchView searchView;
    ListView listview;
    List<String> list = new ArrayList<>();
    RecyclerView recyclerViewTrain;
    List<TrainStationFirstStep> responses;
    TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_train2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        responses = this.getStations();
        if (responses.size() > 0) {
            list.clear();
            for (TrainStationFirstStep res : responses) {
                if (res.getStation() != null || !res.getStationCode().equals(""))
                    list.add(res.getStation() + "(" + res.getStationCode() + ") - " + res.getStationState());
            }
        }



//        //Set NearBy Airports based on GPS or city Location
//
//        recyclerViewTrain = (RecyclerView) findViewById(R.id.station_recycler_view);
//        recyclerViewTrain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//
//
//        //coordinate find
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
//        String previouslyEncodedCity = pref.getString("whatisyourcurrentcityfortrain", "");
//
//        if (!previouslyEncodedCity.equals("")) {
//            String previouslyEncodedCityLat = pref.getString("whatisyourcurrentcitylatfortrain", ""); //22.7196
//            String previouslyEncodedCityLng = pref.getString("whatisyourcurrentcitylngfortrain", ""); //75.8577
//
//
//            //find coordinates of the user then perform the steps below
//
//            List<TrainStationFirstStep> stationResponse = new ArrayList<>();
//            stationResponse.clear();
//            LatLng user = new LatLng(Double.parseDouble(previouslyEncodedCityLat)
//                    , Double.parseDouble(previouslyEncodedCityLng));
//
//            for (TrainStationFirstStep a : responses) {
//                Double value = distance(user.latitude, user.longitude, Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()), "Km");
//                if (value < 200) {
//                    airportResponse.add(a);
//                    Log.d("airportnamewithvalue", a.getName() + "      " + value);
//                }
//            }
//
//
//            recyclerViewTrain.setAdapter(new AirportNearAdapter(airportResponse, R.layout.list_item_near_by_airports, getApplicationContext()));
//            final List<AirportResponse> list1 = airportResponse;
//            recyclerViewTrain.addOnItemTouchListener(
//                    new RecyclerItemClickListener(getApplicationContext(), recyclerViewTrain, new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            String item = list1.get(position).getIata();
//                            Intent intent = new Intent();
//                            intent.putExtra("datasourcevalue1", item);
//                            setResult(RESULT_OK, intent);
//                            finish();
//                        }
//
//                        @Override
//                        public void onLongItemClick(View view, int position) {
//                            // do whatever
//                        }
//                    })
//            );
//        }else{
//            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//            builder1.setMessage("Please Select Current City");
//            builder1.setCancelable(true);
//            builder1.setView(R.layout.currentcitydialog);
//            builder1.setPositiveButton(
//                    "Set City",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
//                            String previouslyEncodedCity = pref.getString("whatisyourcurrentcity", "");
//
//                            if (!previouslyEncodedCity.equals("")) {
//                                String previouslyEncodedCityLat = pref.getString("whatisyourcurrentcitylat", ""); //22.7196
//                                String previouslyEncodedCityLng = pref.getString("whatisyourcurrentcitylng", ""); //75.8577
//
//
//                                //find coordinates of the user then perform the steps below
//
//                                List<AirportResponse> airportResponse = new ArrayList<>();
//                                airportResponse.clear();
//                                LatLng user = new LatLng(Double.parseDouble(previouslyEncodedCityLat)
//                                        , Double.parseDouble(previouslyEncodedCityLng));
//
//                                for (AirportResponse a : responses) {
//                                    Double value = distance(user.latitude, user.longitude, Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()), "Km");
//                                    if (value < 200) {
//                                        airportResponse.add(a);
//                                        Log.d("airportnamewithvalue", a.getName() + "      " + value);
//                                    }
//                                }
//
//
//                                recyclerViewAirport.setAdapter(new AirportNearAdapter(airportResponse, R.layout.list_item_near_by_airports, getApplicationContext()));
//                                final List<AirportResponse> list1 = airportResponse;
//                                recyclerViewAirport.addOnItemTouchListener(
//                                        new RecyclerItemClickListener(getApplicationContext(), recyclerViewAirport, new RecyclerItemClickListener.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(View view, int position) {
//                                                String item = list1.get(position).getIata();
//                                                Intent intent = new Intent();
//                                                intent.putExtra("datasourcevalue1", item);
//                                                setResult(RESULT_OK, intent);
//                                                finish();
//                                            }
//
//                                            @Override
//                                            public void onLongItemClick(View view, int position) {
//                                                // do whatever
//                                            }
//                                        })
//                                );
//                            }
//                        }
//                    });
//
//            builder1.setNegativeButton(
//                    "Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//
//            AlertDialog alert11 = builder1.create();
//            alert11.show();
//            dialogText=(TextView)alert11.findViewById(R.id.dialog_text);
//            dialogText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try{
//                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
//                                .setCountry("IN")
//                                .build();
//                        Intent intent=new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                                .setFilter(typeFilter)
//                                .build(SearchActivityTrain.this);
//
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//
//                        startActivityForResult(intent, 1);
//                    } catch (GooglePlayServicesRepairableException e) {
//
//                        // TODO: Handle the error.
//                    } catch (GooglePlayServicesNotAvailableException e) {
//                        // TODO: Handle the error.
//                    }
//                }
//            });
//            Toast.makeText(SearchActivityTrain.this,"Put your current city in settings to see nearby airports",Toast.LENGTH_LONG).show();
//        }
//

        listview = (ListView) findViewById(R.id.listviewtrain);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        searchView = (MaterialSearchView) findViewById(R.id.searchview);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                listview = (ListView) findViewById(R.id.listviewtrain);
                ArrayAdapter adapter = new ArrayAdapter(SearchActivityTrain2.this, android.R.layout.simple_list_item_1, list);
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

                    ArrayAdapter adapter = new ArrayAdapter<>(SearchActivityTrain2.this
                            , android.R.layout.simple_list_item_1, listFnd);
                    listview.setAdapter(adapter);

                } else {

                    ArrayAdapter adapter = new ArrayAdapter<>(SearchActivityTrain2.this
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
                String destinationName="";
                for (TrainStationFirstStep i : responses) {
                    if (item.equals(i.getStation() + "(" + i.getStationCode() + ") - " + i.getStationState())) {
                        item = i.getStationCode();
                        destinationName=i.getStation();
                    }
                }
                Log.d("item", item);
                Intent intent = new Intent();
                intent.putExtra("datasourcevalue4", item).putExtra("trainDestination",destinationName);
                Toast.makeText(SearchActivityTrain2.this, item, Toast.LENGTH_LONG).show();
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

    public List<TrainStationFirstStep> getStations() {
        String json = null;

        //getting data from shared preferences instead of api call

        SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
        String jsonData = pref.getString("trainstationsjsondata", "");

        try {
            if(!jsonData.equals("")){

                Log.d("stationdatacomingfrom","fromsharedpref");
                JSONObject jsonObject = new JSONObject(jsonData);
                TrainStationResponse response = TrainStationResponse.fromJson(jsonObject);
                return response.getStations();

            }else {
                json = Jsoup.connect("https://firebasestorage.googleapis.com/v0/b/sajag-travel-buddy.appspot.com/o/station_list.json?alt=media&token=f53e65b6-0546-484d-9346-9ca27b2e1508")
                        .ignoreContentType(true).execute().body();

                JSONObject jsonObject = new JSONObject(json);
                TrainStationResponse response = TrainStationResponse.fromJson(jsonObject);

                Log.d("stationdatacomingfrom","fromtrainapi");

                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref1.edit();
                editor.putString("trainstationsjsondata", json);//train ka json
                editor.commit();

                return response.getStations();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        if (unit == "K") {
//            dist = dist * 1.609344;
//        } else if (unit == "N") {
//            dist = dist * 0.8684;
//        }
//
//        return (dist);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlaceAutocomplete.getPlace(this, data);
//                String placeName=place.getName().toString();
//                float placeRating=place.getRating();
//                String placeAddress=place.getAddress().toString();
//                LatLng placeLatLng=place.getLatLng();
//                String placeId=place.getId();
//                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref1.edit();
//                editor.putString("whatisyourcurrentcityfortrain",placeName.toString());//Khurai
//                editor.putString("whatisyourcurrentcitylatfortrain",placeLatLng.latitude+"");
//                editor.putString("whatisyourcurrentcitylngfortrain",placeLatLng.longitude+"");
//                editor.commit();
//                dialogText.setText(placeName.toString());
//
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(this, data);
//                // TODO: Handle the error.
//                Toast.makeText(SearchActivityTrain.this,status.getStatusMessage(),Toast.LENGTH_LONG).show();
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//
//        }
//    }
//
//    private static double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private static double rad2deg(double rad) {
//        return (rad * 180 / Math.PI);
//    }

}
