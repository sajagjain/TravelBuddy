package com.sajagjain.sajagjain.majorproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sajagjain.sajagjain.majorproject.BusActivity;
import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.Bus;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sajag jain on 08-10-2017.
 */

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private List<Bus> buses;
    private int rowLayout;
    private Context context;
    BusAdapterListener listener;


    public static class BusViewHolder extends RecyclerView.ViewHolder {
        LinearLayout busLayout;
        LinearLayout busClickLayoutLeftHorizontalInside,busClickLayoutRight,busClickLayoutBottom;
        TextView busPrice;
        TextView busDuration;
        TextView busTravelsName,busSeatAvailability;
        TextView busRating;
        TextView busDepartureTimeAndDate,busArrivalTimeAndDate;
        TextView busAmenities,busType,busSeatType;
        ImageView busIcon,busAC,busNonAC;
        ToggleButton busSaveThat;

        public BusViewHolder(View v) {
            super(v);
            busLayout = (LinearLayout) v.findViewById(R.id.bus_layout);
            busClickLayoutLeftHorizontalInside=(LinearLayout)v.findViewById(R.id.bus_click_layout_left_horizontal_inside);
            busClickLayoutRight=(LinearLayout)v.findViewById(R.id.bus_click_layout_right);
            busClickLayoutBottom=(LinearLayout)v.findViewById(R.id.bus_click_layout_bottom);
            busPrice = (TextView) v.findViewById(R.id.bus_price);
            busDuration = (TextView) v.findViewById(R.id.bus_duration);
            busTravelsName = (TextView) v.findViewById(R.id.bus_company_name);
            busRating = (TextView) v.findViewById(R.id.bus_rating);
            busDepartureTimeAndDate = (TextView) v.findViewById(R.id.bus_departure);
            busArrivalTimeAndDate = (TextView) v.findViewById(R.id.bus_arrival);
            busSeatAvailability = (TextView) v.findViewById(R.id.bus_seats_available);
            busAmenities = (TextView) v.findViewById(R.id.bus_amenities);
            busType = (TextView) v.findViewById(R.id.bus_type);
            busSeatType = (TextView) v.findViewById(R.id.bus_seat_type);
            busIcon = (ImageView) v.findViewById(R.id.bus_main_icon);
            busAC = (ImageView) v.findViewById(R.id.bus_ac);
            busNonAC = (ImageView) v.findViewById(R.id.bus_nonac);
            busSaveThat=(ToggleButton)v.findViewById(R.id.bus_save_that);
        }
    }

    public BusAdapter(List<Bus> buses, int rowLayout, Context context, BusAdapterListener listener) {
        this.buses = buses;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public BusAdapter.BusViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BusViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BusViewHolder holder, final int position) {

        ImageView busAC,busNonAC;
        Log.d("rating",buses.get(position).getBusRating());
        String busACNonAC=buses.get(position).getBusSubDetail().getBusList().get(0).getBusCondition();
        String busArrival=buses.get(position).getBusArrivalDate();
        busArrival=busArrival.replace("T"," ");
        busArrival=busArrival.replace("Z"," ");
        String busDeparture=buses.get(position).getBusDepartureTime();
        busDeparture=busDeparture.replace("T"," ");
        busDeparture=busDeparture.replace("Z"," ");
        holder.busPrice.setText(buses.get(position).getBusSubDetail().getBusList().get(0).getBusFare());
        holder.busDuration.setText(buses.get(position).getBusDuration());
        holder.busTravelsName.setText(buses.get(position).getBusTravelsName());
        holder.busSeatAvailability.setText(buses.get(position).getBusSubDetail().getBusList().get(0).getBusSeatsAvailablity());
        holder.busRating.setText(buses.get(position).getBusRating());
        holder.busDepartureTimeAndDate.setText(busDeparture);
        holder.busArrivalTimeAndDate.setText(busArrival);
        holder.busAmenities.setText(buses.get(position).getBusAmenities());
        holder.busType.setText(buses.get(position).getBusType());
        holder.busSeatType.setText(buses.get(position).getBusSubDetail().getBusList().get(0).getBusSeatType());


        if(busACNonAC.equals("ac"))
        {
            Picasso.with(context).load(R.drawable.ac_icon_activated).into(holder.busAC);
        }else if(BusActivity.flightClass.equals("nonac"))
        {
            Picasso.with(context).load(R.drawable.non_ac_icon_activated).into(holder.busNonAC);
        }

        applyClickEvents(holder,position);
    }

    private void applyClickEvents(BusAdapter.BusViewHolder holder, final int position) {

        holder.busClickLayoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.busClickLayoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.busClickLayoutLeftHorizontalInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.busIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.busTravelsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.busSaveThat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBusSaveButtonClick(position,view);
            }
        });

    }


    @Override
    public int getItemCount() {
        return buses.size();
    }

    public interface BusAdapterListener{
        void onBusSaveButtonClick(int position,View view);
        void onEverythingElseClicked(int position,View view);
    }

}
