package com.sajagjain.sajagjain.majorproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    TextView delhi1,goa1,mumbai1,kerela1,shimla1,lastViewedPlaceName;
    TextView lastViewedFlightPrice,lastViewedTrainPrice,lastViewedBusPrice,lastViewedMoreDetailsButton;
    TextView appUserName,appUserEmailID;
    CircleImageView appUserImageView;
    DrawerLayout dr;
    TextView travelPlanningAssistanceStartButton;
    int height;
    NavigationView nv;
    LinearLayout navHeaderBackground;
    View navHeader;
    String userName=null,userEmail=null;
    Uri userPhotoUrl=null;
    CircleImageView delhi,goa,mumbai,kerela,shimla;
    ImageView lastViewedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.backgroundpossible));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        height=getWindowManager().getDefaultDisplay().getHeight();
        dr=(DrawerLayout)findViewById(R.id.drawer_layout);
        nv=(NavigationView)findViewById(R.id.nav_view);
        navHeader=nv.getHeaderView(0);
        navHeaderBackground=(LinearLayout)navHeader.findViewById(R.id.nav_view_background);
        appUserEmailID=(TextView)navHeader.findViewById(R.id.app_user_email);
        appUserName=(TextView)navHeader.findViewById(R.id.app_user_name);
        appUserImageView=(CircleImageView)navHeader.findViewById(R.id.app_user_profile_picture);


        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height/3);
        navHeaderBackground.setLayoutParams(params);
        navHeaderBackground.setBackground(getDrawable(R.drawable.navdrawer));

        //get username and email of user from settings
        userName=getIntent().getStringExtra("username");
        userEmail=getIntent().getStringExtra("userid");
        userPhotoUrl=getIntent().getParcelableExtra("userphoto");

        if(userEmail!=null) {
            appUserEmailID.setText(userEmail);
        }
        if(userName!=null){
            appUserName.setText(userName);
        }
        if(userPhotoUrl!=null){
            Picasso.with(getApplicationContext()).load(userPhotoUrl).error(R.drawable.aboutus24x24).into(appUserImageView);
        }else{
            Picasso.with(getApplicationContext()).load(R.drawable.user_icon).error(R.drawable.aboutus24x24).into(appUserImageView);
        }

//        SharedPreferences prefUser=getApplicationContext().getSharedPreferences("UsernameAndEmail",MODE_PRIVATE);
//        SharedPreferences.Editor editorUser=prefUser.edit();
//        editorUser.putString("username",userName);
//        editorUser.putString("userid",userEmail);
//        editorUser.putString("userphotourl",userPhotoUrl.toString());
//        editorUser.commit();


        //setting device location in shared prefrence
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref1.edit();
        editor.putString("whatisyourcurrentcity","");//Khurai
        editor.putString("whatisyourcurrentcitylat","22.7196");
        editor.putString("whatisyourcurrentcitylng","75.8577");
        editor.commit();



        //linking views
        delhi=(CircleImageView) findViewById(R.id.delhi);
        mumbai=(CircleImageView) findViewById(R.id.mumbai);
        goa=(CircleImageView) findViewById(R.id.goa);
        kerela=(CircleImageView) findViewById(R.id.kerela);
        shimla=(CircleImageView) findViewById(R.id.shimla);
        delhi1=(TextView) findViewById(R.id.delhi1);
        mumbai1=(TextView) findViewById(R.id.mumbai1);
        goa1=(TextView) findViewById(R.id.goa1);
        kerela1=(TextView) findViewById(R.id.kerela1);
        shimla1=(TextView) findViewById(R.id.shimla1);
        lastViewedPlaceName=(TextView)findViewById(R.id.last_viewed_place_name);
//        lastViewedBusPrice=(TextView)findViewById(R.id.last_viewed_bus_price);
//        lastViewedFlightPrice=(TextView)findViewById(R.id.last_viewed_flight_price);
        lastViewedImage=(ImageView)findViewById(R.id.last_viewed_image);
        lastViewedMoreDetailsButton=(TextView)findViewById(R.id.last_viewed_more_details);
//        lastViewedTrainPrice=(TextView)findViewById(R.id.last_viewed_train_price);
        travelPlanningAssistanceStartButton=(TextView) findViewById(R.id.travel_planning_assistance_start_button);

        //Data from Shared Preferences

        SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
        String previouslyEncodedImage = pref.getString("lastviewedimage","");
        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            lastViewedImage.setImageBitmap(bitmap);
        }else{
            Picasso.with(MainActivity.this).load(R.drawable.darjeeling).into(lastViewedImage);
        }

        String lastViewedNameData=pref.getString("lastviewedname","Darjeeling");

        //Set Data from Preference to Views
        lastViewedPlaceName.setText(lastViewedNameData);



        //LAST VIEWED BUTTON CLICK
        lastViewedMoreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("Loading...");
                dialog.setCancelable(false);
                dialog.show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
                String lastViewedNameData=pref.getString("lastviewedname","Darjeeling");
                float lastViewedRatingData=pref.getFloat("lastviewedrating",(float)4.0);
                String lastViewedAddressData=pref.getString("lastviewedaddress","Darjeeling, West Bengal, India");
                String lastViewedLatitudeData=pref.getString("lastviewedlatitude","27.0360");
                String lastViewedLongitudeData=pref.getString("lastviewedlongitude","88.2627");
                String lastViewedPlaceIdData=pref.getString("lastviewedplaceid","ChIJuwH1TGUu5DkRKnDUeVlVdUE");
                LatLng lastViewedLatLng=new LatLng(Double.parseDouble(lastViewedLatitudeData)
                        ,Double.parseDouble(lastViewedLongitudeData));
                Toast.makeText(MainActivity.this,"Opening "+lastViewedNameData,Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",lastViewedNameData)
                        .putExtra("placeRating",lastViewedRatingData)
                        .putExtra("placeAddress",lastViewedAddressData)
                        .putExtra("placeLatLng",lastViewedLatLng)
                        .putExtra("placeId",lastViewedPlaceIdData));
                dialog.dismiss();
            }
        });




        //Make static places and link them

        delhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Delhi",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Delhi";
                float placeRating=(float)4.3;
                String placeAddress="Delhi, India";
                LatLng placeLatLng=new LatLng(28.7041,77.1025);
                String placeId="ChIJL_P_CXMEDTkRw0ZdG-0GVvw";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();
            }
        });
        delhi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Delhi",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Delhi";
                float placeRating=(float)4.3;
                String placeAddress="Delhi, India";
                LatLng placeLatLng=new LatLng(28.7041,77.1025);
                String placeId="ChIJL_P_CXMEDTkRw0ZdG-0GVvw";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();

            }
        });
        goa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Goa",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Calangute";
                float placeRating=(float)3.8;
                String placeAddress="Calangute,Goa 403519, India";
                LatLng placeLatLng=new LatLng(15.5311,73.7625);
                String placeId="ChIJ9Vm7fePBvzsRhscxtxGXD50";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();
            }
        });
        goa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Goa",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Calangute";
                float placeRating=(float)3.8;
                String placeAddress="Calangute,Goa 403519, India";
                LatLng placeLatLng=new LatLng(15.5311,73.7625);
                String placeId="ChIJ9Vm7fePBvzsRhscxtxGXD50";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();
            }
        });
        mumbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Mumbai",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Mumbai";
                float placeRating=(float)5;
                String placeAddress="Mumbai, Maharashtra, India";
                LatLng placeLatLng=new LatLng(19.0760,72.8777);
                String placeId="ChIJwe1EZjDG5zsRaYxkjY_tpF0";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();
            }
        });
        mumbai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Mumbai",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Mumbai";
                float placeRating=(float)5;
                String placeAddress="Mumbai, Maharashtra, India";
                LatLng placeLatLng=new LatLng(19.0760,72.8777);
                String placeId="ChIJwe1EZjDG5zsRaYxkjY_tpF0";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();

            }
        });
        kerela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Kerela",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Munnar";
                float placeRating=(float)3.2;
                String placeAddress="Munnar, Kerela 685612, India";
                LatLng placeLatLng=new LatLng(10.8505,76.2711);
                String placeId="ChIJbZoJTXmZBzsRDH48VeVQMgY";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();
            }
        });
        kerela1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Kerela",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Munnar";
                float placeRating=(float)3.2;
                String placeAddress="Munnar, Kerela 685612, India";
                LatLng placeLatLng=new LatLng(10.8505,76.2711);
                String placeId="ChIJbZoJTXmZBzsRDH48VeVQMgY";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();
            }
        });
        shimla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Shimla",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Shimla";
                float placeRating=(float)3.5;
                String placeAddress="Shimla, Himachal Pradesh, India";
                LatLng placeLatLng=new LatLng(31.1048,77.1734);
                String placeId="ChIJZ25d4-N4BTkRt1Sf__Z_fh8";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();
            }
        });
        shimla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Opening Shimla",Toast.LENGTH_LONG).show();
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.setCancelable(false);
                dialog.show();
                String placeName="Shimla";
                float placeRating=(float)3.5;
                String placeAddress="Shimla, Himachal Pradesh, India";
                LatLng placeLatLng=new LatLng(31.1048,77.1734);
                String placeId="ChIJZ25d4-N4BTkRt1Sf__Z_fh8";
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();

            }
        });

        travelPlanningAssistanceStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TravelPlanningAssistance.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                try{
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .setCountry("IN")
                            .build();
                    Intent intent=new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(MainActivity.this);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {

                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LastViewed", MODE_PRIVATE);
        String previouslyEncodedImage = pref.getString("lastviewedimage","");
        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            lastViewedImage.setImageBitmap(bitmap);
        }else{
            Picasso.with(MainActivity.this).load(R.drawable.darjeeling).into(lastViewedImage);
        }

        String lastViewedNameData=pref.getString("lastviewedname","Darjeeling");

        //Set Data from Preference to Views
        lastViewedPlaceName.setText(lastViewedNameData);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("loading");
                dialog.show();
                Place place = PlaceAutocomplete.getPlace(this, data);
                String placeName=place.getName().toString();
                float placeRating=place.getRating();
                String placeAddress=place.getAddress().toString();
                LatLng placeLatLng=place.getLatLng();
                String placeId=place.getId();
                Toast.makeText(MainActivity.this,"Opening "+placeName ,Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,Places.class).putExtra("placeName",placeName).putExtra("placeRating",placeRating)
                        .putExtra("placeAddress",placeAddress).putExtra("placeLatLng",placeLatLng).putExtra("placeId",placeId));
                dialog.dismiss();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(MainActivity.this,status.getStatusMessage(),Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Toast.makeText(MainActivity.this,"User Cancelled the Operation",Toast.LENGTH_LONG).show();
            }

        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent startMain=new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            Toast.makeText(this,"replace this with your own action",Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_sign_out) {
            FirebaseAuth.getInstance().signOut();
            MainActivity.this.finishAfterTransition();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_places) {
            // Handle the camera action
            try{
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                        .setCountry("IN")
                        .build();
                Intent intent=new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .setFilter(typeFilter)
                        .build(MainActivity.this);

                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }
        } else if (id == R.id.nav_my_bucket_list) {

        } else if (id == R.id.nav_my_saved_places) {

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(MainActivity.this,AboutUs.class));
        } else if (id == R.id.nav_contact_us) {
            startActivity(new Intent(MainActivity.this,ContactUs.class));
        }else if(id== R.id.nav_share_app){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
