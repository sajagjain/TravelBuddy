package com.sajagjain.sajagjain.majorproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.AirportResponse;

import java.util.List;

/**
 * Created by sajag jain on 29-12-2017.
 */

public class AirportNearAdapter extends RecyclerView.Adapter<AirportNearAdapter.AirportViewHolder> {

    private List<AirportResponse> airports;
    private int rowLayout;
    private Context context;


    public static class AirportViewHolder extends RecyclerView.ViewHolder {
        LinearLayout nearMeAirportLayout;
        TextView airportNearMeName;

        public AirportViewHolder(View v) {
            super(v);
            nearMeAirportLayout = (LinearLayout) v.findViewById(R.id.single_airport_layout);
            airportNearMeName = (TextView) v.findViewById(R.id.airport_near_me_textview);
        }
    }

    public AirportNearAdapter(List<AirportResponse> airports, int rowLayout, Context context) {
        this.airports = airports;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public AirportNearAdapter.AirportViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AirportNearAdapter.AirportViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AirportNearAdapter.AirportViewHolder holder, final int position) {

        holder.airportNearMeName.setText(airports.get(position).getName()+"("+airports.get(position).getIata()+")");
    }

    @Override
    public int getItemCount() {
        return airports.size();
    }
}

