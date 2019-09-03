package com.sajagjain.sajagjain.majorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

public class PlaceDescription extends AppCompatActivity {

    TextView moreDetailedDescription;
    Button seeEvenMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);

        moreDetailedDescription=(TextView)findViewById(R.id.more_detail_description);
        seeEvenMore=(Button)findViewById(R.id.see_even_more);



        List<String> list=Singleton.getList();
        Iterator i=list.iterator();
        String text="                       ";
        while(i.hasNext()){
            text=text+"\n\n\n"+i.next();
        }

        moreDetailedDescription.setText(text);

        seeEvenMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeName=getIntent().getStringExtra("placeName");
                startActivity(new Intent(PlaceDescription.this, PlaceInformation.class).putExtra("placeName", placeName));
            }
        });
    }
}
