package com.sajagjain.sajagjain.majorproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sajagjain.sajagjain.majorproject.FlightActivity;
import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.Flight;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<Flight> flights;
    private int rowLayout;
    private Context context;
    private FlightAdapterListener listener;


    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        LinearLayout flightLayout;
        LinearLayout flightClickLayoutRight;
        LinearLayout flightClickLayoutLeftHorizontalLayoutInside;
        TextView flightPrice;
        TextView flightRefundable;
        TextView flightArrival;
        TextView flightDeparture;
        TextView flightDuration;
        TextView airlineName;
        TextView flightSeatsAvailable;
        ImageView airlineIcon,businessClass,economyClass;
        ToggleButton flightSaveToggle;

        public FlightViewHolder(View v) {
            super(v);
            flightLayout = (LinearLayout) v.findViewById(R.id.flight_layout);
            flightClickLayoutRight=(LinearLayout)v.findViewById(R.id.flight_click_layout_right);
            flightClickLayoutLeftHorizontalLayoutInside=(LinearLayout)v.findViewById(R.id.flight_click_layout_left_horizontal_layout);
            flightArrival = (TextView) v.findViewById(R.id.flight_arrival);
            flightDeparture = (TextView) v.findViewById(R.id.flight_departure);
            flightDuration = (TextView) v.findViewById(R.id.flight_duration);
            flightRefundable = (TextView) v.findViewById(R.id.flight_warnings);
            flightPrice = (TextView) v.findViewById(R.id.flight_price);
            airlineName = (TextView) v.findViewById(R.id.flight_airline_name);
            airlineIcon = (ImageView) v.findViewById(R.id.flight_airline_icon);
            businessClass = (ImageView) v.findViewById(R.id.business_class);
            economyClass = (ImageView) v.findViewById(R.id.economy_class);
            flightSeatsAvailable=(TextView) v.findViewById(R.id.flight_seats_available);
            flightSaveToggle=(ToggleButton)v.findViewById(R.id.flight_save_that);
        }
    }

    public FlightAdapter(List<Flight> flights, int rowLayout, Context context, FlightAdapterListener listener) {
        this.flights = flights;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public FlightAdapter.FlightViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FlightViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FlightViewHolder holder, final int position) {
        String arrivalDate=flights.get(position).getArrivalDate();
        arrivalDate=arrivalDate.substring(0,10);
        String departureDate=flights.get(position).getDepartureDate();
        departureDate=departureDate.substring(0,10);
        String arrivalTime=flights.get(position).getArrivalTime();
        String departureTime=flights.get(position).getDepartureTime();
        String arrival=arrivalTime+" "+arrivalDate;
        String departure=departureTime+" "+departureDate;

        String airline=flights.get(position).getAirline();
        holder.flightPrice.setText(flights.get(position).getFightFareClass().getFlightFare());
        holder.flightRefundable.setText(flights.get(position).getRefundable());
        holder.flightArrival.setText(arrival);
        holder.flightDeparture.setText(departure);
        holder.airlineName.setText(airline);
        holder.flightDuration.setText(flights.get(position).getDuration());

        String seatsAvailable=flights.get(position).getSeatsAvailable();
        if(Integer.parseInt(seatsAvailable)!=9999) {
            holder.flightSeatsAvailable.setText(seatsAvailable);
        }else{
            holder.flightSeatsAvailable.setText("Plenty");
        }


        if(FlightActivity.flightClass.equals("E"))
        {
            Picasso.with(context).load(R.drawable.economy_class_activated).into(holder.economyClass);
        }else if(FlightActivity.flightClass.equals("B"))
        {
            Picasso.with(context).load(R.drawable.business_class_activated).into(holder.businessClass);
        }

        if(airline.equalsIgnoreCase("Air India")) {
            Picasso.with(context).load(R.drawable.ai).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Spicejet")) {
            Picasso.with(context).load(R.drawable.spice_jett).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Vistara")) {
            Picasso.with(context).load(R.drawable.vistara).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Air Asia")||airline.equalsIgnoreCase("AirAsia")||airline.equalsIgnoreCase("AirAsia India")) {
            Picasso.with(context).load(R.drawable.air_asiaa).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Indigo")) {
            Picasso.with(context).load(R.drawable.indigo).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("JetLite")) {
            Picasso.with(context).load(R.drawable.jetlite).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Air India Express")) {
            Picasso.with(context).load(R.drawable.air_india_express).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Air Costa")) {
            Picasso.with(context).load(R.drawable.air_costa).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Blue Dart Aviation")) {
            Picasso.with(context).load(R.drawable.bluedart_aviation).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Air Odisha")) {
            Picasso.with(context).load(R.drawable.air_odisha).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("TajAir")||airline.equalsIgnoreCase("Taj Air")) {
            Picasso.with(context).load(R.drawable.tajair).into(holder.airlineIcon);
        }else if(airline.equalsIgnoreCase("Jet Airways")) {
            Picasso.with(context).load(R.drawable.jet_airwayss).into(holder.airlineIcon);
        }else{
            Picasso.with(context).load(R.drawable.flight_icon).into(holder.airlineIcon);
        }

        applyClickEvents(holder,position);



    }

    private void applyClickEvents(FlightAdapter.FlightViewHolder holder, final int position) {

        holder.flightClickLayoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.airlineIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.airlineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.flightClickLayoutLeftHorizontalLayoutInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.flightSaveToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFlightSaveButtonClick(position,view);
            }
        });

    }


    @Override
    public int getItemCount() {
        return flights.size();
    }


    public interface FlightAdapterListener{
        void onFlightSaveButtonClick(int position,View view);
        void onEverythingElseClicked(int position,View view);
    }


}