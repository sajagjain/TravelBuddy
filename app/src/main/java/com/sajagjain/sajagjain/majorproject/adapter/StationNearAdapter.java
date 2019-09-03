package com.sajagjain.sajagjain.majorproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.TrainStationFirstStep;

import java.util.List;

/**
 * Created by sajag jain on 29-12-2017.
 */

public class StationNearAdapter extends RecyclerView.Adapter<StationNearAdapter.StationViewHolder> {

    private List<TrainStationFirstStep> stations;
    private int rowLayout;
    private Context context;


    public static class StationViewHolder extends RecyclerView.ViewHolder {
        LinearLayout nearMeAirportLayout;
        TextView airportNearMeName;

        public StationViewHolder(View v) {
            super(v);
            nearMeAirportLayout = (LinearLayout) v.findViewById(R.id.single_airport_layout);
            airportNearMeName = (TextView) v.findViewById(R.id.airport_near_me_textview);
        }
    }

    public StationNearAdapter(List<TrainStationFirstStep> stations, int rowLayout, Context context) {
        this.stations = stations;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public StationNearAdapter.StationViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new StationNearAdapter.StationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StationNearAdapter.StationViewHolder holder, final int position) {

        holder.airportNearMeName.setText(stations.get(position).getStation()
                +"("+stations.get(position).getStationCode()+") - "+stations.get(position).getStationState());
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }
}

