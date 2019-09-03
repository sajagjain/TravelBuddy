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


import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.Train;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sajag jain on 09-10-2017.
 */

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {

    private List<Train> trains;
    private int rowLayout;
    private Context context;
    private TrainAdapterListener listener;


    public static class TrainViewHolder extends RecyclerView.ViewHolder {
        LinearLayout trainLayout;
        LinearLayout trainClickLayoutRight;
        //        TextView trainPrice;
        TextView trainDuration;
        TextView trainArrival;
        TextView trainDeparture;
        TextView trainNumber;
        TextView trainName;
        ImageView trainIcon;
        TextView train2s;
        TextView traincc, trainfc, trainsl, train2a, train1a, train3a;
        TextView monday, tuesday, wednesday, thursday, friday, saturday, sunday;
        ToggleButton trainSaveThat;


        public TrainViewHolder(View v) {
            super(v);
            trainLayout = (LinearLayout) v.findViewById(R.id.train_layout);
            trainClickLayoutRight = (LinearLayout) v.findViewById(R.id.train_click_layout_right);
            trainArrival = (TextView) v.findViewById(R.id.train_arrival);
            trainIcon = (ImageView) v.findViewById(R.id.train_main_icon);
            trainDeparture = (TextView) v.findViewById(R.id.train_departure);
            trainDuration = (TextView) v.findViewById(R.id.train_duration);
            trainNumber = (TextView) v.findViewById(R.id.train_number);
//            trainPrice = (TextView) v.findViewById(R.id.flight_price);
            trainName = (TextView) v.findViewById(R.id.trainName);
            trainSaveThat = (ToggleButton) v.findViewById(R.id.train_save_that);
//            train2s=(TextView) v.findViewById(R.id.train2s);
//            train3a=(TextView) v.findViewById(R.id.train3a);
//            train1a=(TextView) v.findViewById(R.id.train1a);
//            train2a=(TextView) v.findViewById(R.id.train2a);
//            traincc=(TextView) v.findViewById(R.id.traincc);
//            trainsl=(TextView) v.findViewById(R.id.trainsl);
//            trainfc=(TextView) v.findViewById(R.id.trainfc);
            monday = (TextView) v.findViewById(R.id.monday);
            tuesday = (TextView) v.findViewById(R.id.tuesday);
            wednesday = (TextView) v.findViewById(R.id.wednesday);
            thursday = (TextView) v.findViewById(R.id.thursday);
            friday = (TextView) v.findViewById(R.id.friday);
            saturday = (TextView) v.findViewById(R.id.saturday);
            sunday = (TextView) v.findViewById(R.id.sunday);


        }
    }

    public TrainAdapter(List<Train> trains, int rowLayout, Context context, TrainAdapterListener listener) {
        this.trains = trains;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public TrainAdapter.TrainViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TrainViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TrainViewHolder holder, final int position) {

//        holder.trainPrice.setText(trains.get(position).getTrainNumber());
        holder.trainNumber.setText(trains.get(position).getTrainNumber());
        holder.trainArrival.setText(trains.get(position).getArrivalTime());

        try {

            SimpleDateFormat input = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
            if(trains.get(position).getTrainDate()!=null) {
                Date tempInDate = input.parse(trains.get(position).getTrainDate().toString());
                String tempOutDate = output.format(tempInDate);
                holder.trainDeparture.setText(trains.get(position).getDepartureTime() + " " + tempOutDate);
            }else{
                holder.trainDeparture.setText(trains.get(position).getDepartureTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.trainName.setText(trains.get(position).getTrainName());
        holder.trainDuration.setText(trains.get(position).getTravelTime());
//        holder.train2s.setText(trains.get(position).getTrainClassList().get(0).getTrainClassAvailability());
//        holder.traincc.setText(trains.get(position).getTrainClassList().get(1).getTrainClassAvailability());
//        holder.trainfc.setText(trains.get(position).getTrainClassList().get(2).getTrainClassAvailability());
//        holder.trainsl.setText(trains.get(position).getTrainClassList().get(3).getTrainClassAvailability());
//        holder.train2a.setText(trains.get(position).getTrainClassList().get(4).getTrainClassAvailability());
//        holder.train1a.setText(trains.get(position).getTrainClassList().get(5).getTrainClassAvailability());
//        holder.train3a.setText(trains.get(position).getTrainClassList().get(7).getTrainClassAvailability());
        holder.monday.setText(trains.get(position).getTrainRunningDaysList().get(0).getTrainRunsOnDay());
        holder.tuesday.setText(trains.get(position).getTrainRunningDaysList().get(1).getTrainRunsOnDay());
        holder.wednesday.setText(trains.get(position).getTrainRunningDaysList().get(2).getTrainRunsOnDay());
        holder.thursday.setText(trains.get(position).getTrainRunningDaysList().get(3).getTrainRunsOnDay());
        holder.friday.setText(trains.get(position).getTrainRunningDaysList().get(4).getTrainRunsOnDay());
        holder.saturday.setText(trains.get(position).getTrainRunningDaysList().get(5).getTrainRunsOnDay());
        holder.sunday.setText(trains.get(position).getTrainRunningDaysList().get(6).getTrainRunsOnDay());

        applyClickEvents(holder, position);


    }

    private void applyClickEvents(TrainAdapter.TrainViewHolder holder, final int position) {

        holder.trainClickLayoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.trainName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.trainNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.trainIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.trainSaveThat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTrainSaveButtonClick(position, view);
            }
        });

    }


    @Override
    public int getItemCount() {
        return trains.size();
    }

    public interface TrainAdapterListener {
        void onTrainSaveButtonClick(int position, View view);

        void onEverythingElseClicked(int position, View view);
    }
}
