package com.sajagjain.sajagjain.majorproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sajagjain.sajagjain.majorproject.adapter.SinglePlanAdapter;
import com.sajagjain.sajagjain.majorproject.model.SinglePlaceSavedInTPA;
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

public class SinglePlanWindow extends AppCompatActivity implements SinglePlanAdapter.SinglePlanAdapterListener, OnMapReadyCallback {

    RecyclerView singlePlanRecyclerView;
    List<TravelPlan> plans = new ArrayList<>();
    SharedPreferences pref;
    int planPosition;
    TextView planName;
    private GoogleMap mMap;
    private LatLng latLng;
    ArrayList<LatLng> listPoints;
    int stateVal=1;


    LinearLayout bottomSheetForSinglePlan;
    TextView bottomSheetForSinglePlanOpenMap;

    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_plan_window);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_for_single_plan_window);

        mapFragment.getMapAsync(this);


        listPoints = new ArrayList<>();

        planPosition = getIntent().getIntExtra("position", -1);

        bottomSheetForSinglePlan = (LinearLayout) findViewById(R.id.bottom_sheet_for_single_plan);
        planName = (TextView) findViewById(R.id.single_plan_name);
        bottomSheetForSinglePlanOpenMap = (TextView) findViewById(R.id.bottom_sheet_for_single_plan_open_map);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetForSinglePlan);

        bottomSheetForSinglePlanOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                    stateVal = 0;
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheetForSinglePlanOpenMap.setText("Open Maps");
                    stateVal=1;
                }
            }
        });
        //Map Related Work
        LatLng latLng = new LatLng(22.7196, 75.8577);
        this.latLng = latLng;


        //Plan related work
        pref = getApplicationContext().getSharedPreferences("TRAVEL_PLANS", MODE_PRIVATE);
        Gson gson = new Gson();
        Set<String> planFromSharedPref = pref.getStringSet("plans", null);

        if (planFromSharedPref != null) {
            Iterator it = planFromSharedPref.iterator();
            while (it.hasNext()) {
                plans.add(gson.fromJson((String) it.next(), TravelPlan.class));
            }
        }

        planName.setText(plans.get(planPosition).getPlanName().toUpperCase());
        singlePlanRecyclerView = (RecyclerView) findViewById(R.id.single_plan_recycler_view);
        singlePlanRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (planPosition != -1) {
            singlePlanRecyclerView.setAdapter(new SinglePlanAdapter(plans.get(planPosition).getDataForSavingInTPA(), R.layout.list_item_single_plan_window, getApplicationContext(), SinglePlanWindow.this));
            SnapHelper helper = new LinearSnapHelper();
            helper.attachToRecyclerView(singlePlanRecyclerView);
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                    getResources().getDisplayMetrics());
            singlePlanRecyclerView.addItemDecoration(new SpacesItemDecoration(space));
            singlePlanRecyclerView.getAdapter().notifyDataSetChanged();

        }

        //bottom sheet work

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        break;
//                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        Toast.makeText(getApplicationContext(),"Sheet Opened",Toast.LENGTH_LONG).show();
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        Toast.makeText(getApplicationContext(),"Sheet Closed",Toast.LENGTH_LONG).show();
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        break;
//                }
                if (newState == BottomSheetBehavior.STATE_DRAGGING&&stateVal==1) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheetForSinglePlanOpenMap.setText("Close Maps");
                }else if(newState == BottomSheetBehavior.STATE_DRAGGING&&stateVal==0){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                BottomSheetBehavior.from(bottomSheet).setHideable(false);
            }
        });

    }



    @Override
    public void onBackPressed() {

        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            stateVal = 0;
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetForSinglePlanOpenMap.setText("Open Maps");
            stateVal=1;
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(true);

        List<MarkerOptions> markerOptionsList = new ArrayList<>();
        List<CircleOptions> circleOptionsList = new ArrayList<>();
        List<LatLng> latLngList = new ArrayList<>();

        Iterator it = plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces()
                .iterator();
        while (it.hasNext()) {

            LatLng latlng = ((SinglePlaceSavedInTPA) it.next()).getPlaceLatLng();

            if (latlng != null) {
                latLngList.add(latlng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latlng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.places24x24));
                markerOptionsList.add(markerOptions);
                Log.d("markersoption", latlng + "");

                CircleOptions circleOptions = new CircleOptions()
                        .center(latlng)
                        .radius(1000)
                        .strokeWidth(10)
                        .strokeColor(Color.BLACK); // In meters
                circleOptionsList.add(circleOptions);
            }
        }

        Iterator it1 = markerOptionsList.iterator();
        while (it1.hasNext()) {
            MarkerOptions options = (MarkerOptions) it1.next();
            mMap.addMarker(options);
        }
        Iterator it2 = circleOptionsList.iterator();
        while (it2.hasNext()) {
            CircleOptions options = (CircleOptions) it2.next();
            mMap.addCircle(options);
        }

        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map_for_single_plan_window).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressLint("NewApi")
                @Override
                public void onGlobalLayout() {
                    LatLngBounds.Builder bld = new LatLngBounds.Builder();
                    for (int i = 0; i < plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().size(); i++) {
                        LatLng ll = new LatLng(plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(i).getPlaceLatLng().latitude, plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(i).getPlaceLatLng().longitude);
                        bld.include(ll);
                    }
                    LatLngBounds bounds = bld.build();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));
                    mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                }
            });
        }
        if (latLngList.size() >= 2) {
            String url = getRequestUrl(latLngList);
            TaskRequestDirection taskRequestDirection = new TaskRequestDirection();
            taskRequestDirection.execute(url);
        } else {
            Toast.makeText(getApplicationContext(), "Not Enough Points", Toast.LENGTH_LONG).show();
        }

    }

    private String getRequestUrl(List<LatLng> points) {
        String str_origin = "origin=" + points.get(0).latitude + "," + points.get(0).longitude;
        String str_dest = "destination=" + points.get(points.size() - 1).latitude + "," + points.get(points.size() - 1).longitude;
        String sensor = "sensor=false";
        String mode = "mode=road";
        String waypoints = "waypoints=";

        for (int i = 1; i < points.size() - 1; i++) {
            waypoints = waypoints + points.get(i).latitude + "," + points.get(i).longitude + "|";
        }

        String params = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + waypoints;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + params;

        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {

            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            responseString = buffer.toString();
            reader.close();
            isr.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
                outRect.right = space;
                outRect.left = 0; //don't forget about recycling...
            }
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = space;
                outRect.right = 0;
            }
        }
    }

    @Override
    public void onMoreOptionMenuClicked(final int position, View view) {
        PopupMenu popup = new PopupMenu(SinglePlanWindow.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu_single_place, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.remove_plan) {

                    if ((position / 2) > 0 && (position / 2) < (plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().size() - 1)) {
                        plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().remove(position / 2);

                        SharedPreferences.Editor prefsEditor = pref.edit();
                        Iterator it1 = plans.iterator();

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
                        singlePlanRecyclerView.setAdapter(new SinglePlanAdapter(plans.get(planPosition).getDataForSavingInTPA(), R.layout.list_item_single_plan_window, getApplicationContext(), SinglePlanWindow.this));
                    } else {
                        Toast.makeText(SinglePlanWindow.this, "Initial Cities Cannot Be Removed", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                if (id == R.id.details) {

                    EditText planCreationDate, planSizeInKb;
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(SinglePlanWindow.this);
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
                    SinglePlaceSavedInTPA singlePlace = plans.get(planPosition).getDataForSavingInTPA().getSavedPlaces().get(position / 2);
                    planCreationDate = (EditText) alert11.findViewById(R.id.travel_plan_dialog_plan_creation_date);
                    planSizeInKb = (EditText) alert11.findViewById(R.id.travel_plan_dialog_plan_size);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream out = null;
                    try {
                        out = new ObjectOutputStream(baos);
                        out.writeObject(singlePlace);
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
                    planCreationDate.setVisibility(View.GONE);
                    return true;
                }
                Toast.makeText(SinglePlanWindow.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        popup.show();//showing popup menu

    }

    @Override
    public void onTravelClicked(int position, View view) {
        startActivity(new Intent(SinglePlanWindow.this,MoreThanOneOptionDescription.class).putExtra("planposition",planPosition));
    }

    @Override
    public void onEverythingElseClicked(int pos, View view) {
        startActivity(new Intent(SinglePlanWindow.this, SinglePlaceWindow.class).putExtra("planPosition", planPosition)
                .putExtra("placePosition", pos / 2));
    }

    public class TaskRequestDirection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);

        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsJSONParser djp = new DirectionsJSONParser();
                routes = djp.parse(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for (HashMap<String, String> point : path) {

                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lng"));

                    points.add(new LatLng(lat, lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(Color.BLACK);
                polylineOptions.geodesic(true);
                if (polylineOptions != null) {
                    mMap.addPolyline(polylineOptions);
                } else {
                    Toast.makeText(getApplicationContext(), "Directions not found", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
