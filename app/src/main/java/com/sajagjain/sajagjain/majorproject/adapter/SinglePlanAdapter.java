package com.sajagjain.sajagjain.majorproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.Bus;
import com.sajagjain.sajagjain.majorproject.model.Flight;
import com.sajagjain.sajagjain.majorproject.model.OverAllDataForSavingInTPA;
import com.sajagjain.sajagjain.majorproject.model.Train;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

/**
 * Created by sajag jain on 07-02-2018.
 */

public class SinglePlanAdapter extends RecyclerView.Adapter<SinglePlanAdapter.SinglePlanViewHolder> {

    private OverAllDataForSavingInTPA list;
    private int rowLayout;
    private Context context;
    SinglePlanAdapter.SinglePlanAdapterListener listener;


    public static class SinglePlanViewHolder extends RecyclerView.ViewHolder {
        LinearLayout singlePlanLayout, singlePlanPlacesLayout, singlePlanTravelOptionOne, singlePlanTravelOptionTwo, singlePlanTravelOptionThree;
        TextView singlePlanTravelOptionOneName, singlePlanTravelOptionTwoName, singlePlanTravelOptionThreeName;
        TextView singlePlanTravelOptionOneDate, singlePlanTravelOptionTwoDate, singlePlanTravelOptionThreeDate;
        TextView singlePlanDate;
        TextView singlePlanPlaceName;
        ImageView singlePlanOptionMoreButton;
        ImageView singlePlanPlaceImage;

        public SinglePlanViewHolder(View v) {
            super(v);
            singlePlanLayout = (LinearLayout) v.findViewById(R.id.single_plan_linear_layout);
            singlePlanPlacesLayout = (LinearLayout) v.findViewById(R.id.single_plan_place_layout);
            singlePlanTravelOptionOne = (LinearLayout) v.findViewById(R.id.single_plan_travel_option_one_layout);
            singlePlanTravelOptionTwo = (LinearLayout) v.findViewById(R.id.single_plan_travel_option_two_layout);
            singlePlanTravelOptionThree = (LinearLayout) v.findViewById(R.id.single_plan_travel_option_three_layout);
            singlePlanTravelOptionOneName = (TextView) v.findViewById(R.id.travel_option_one_name);
            singlePlanTravelOptionOneDate = (TextView) v.findViewById(R.id.travel_option_one_date);
            singlePlanTravelOptionTwoName = (TextView) v.findViewById(R.id.travel_option_two_name);
            singlePlanTravelOptionTwoDate = (TextView) v.findViewById(R.id.travel_option_two_date);
            singlePlanTravelOptionThreeName = (TextView) v.findViewById(R.id.travel_option_three_name);
            singlePlanTravelOptionThreeDate = (TextView) v.findViewById(R.id.travel_option_three_date);
            singlePlanDate = (TextView) v.findViewById(R.id.single_plan_date);
            singlePlanPlaceName = (TextView) v.findViewById(R.id.single_plan_place_name);
            singlePlanOptionMoreButton = (ImageView) v.findViewById(R.id.single_plan_save_plan_more_menu);
            singlePlanPlaceImage = (ImageView) v.findViewById(R.id.single_plan_place_image);

        }
    }

    public SinglePlanAdapter(OverAllDataForSavingInTPA list, int rowLayout, Context context, SinglePlanAdapter.SinglePlanAdapterListener listener) {
        this.list = list;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public SinglePlanAdapter.SinglePlanViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SinglePlanAdapter.SinglePlanViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SinglePlanAdapter.SinglePlanViewHolder holder, int position) {

        if ((position % 2) == 0) {


            holder.singlePlanPlaceName.setText(list.getSavedPlaces().get(position / 2).getPlaceName());
//        holder.singlePlanDate.setText(plans.get(position).getTravelSource() + " - " + plans.get(position).getTravelDestination());

//        Picasso.with(context).load(R.drawable.darjeeling).into(holder.travelPlanImage);
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
            int color1 = generator.getColor(holder.singlePlanPlaceName.getText().toString());
            TextDrawable drawable = TextDrawable.builder().beginConfig()
                    .textColor(Color.BLACK)
                    .useFont(Typeface.DEFAULT)
                    .fontSize(30) /* size in px */
                    .bold()
                    .toUpperCase()
                    .endConfig()
                    .buildRect(holder.singlePlanPlaceName.getText().toString() + "", color1);
            holder.singlePlanPlaceImage.setImageDrawable(drawable);
            applyClickEvents(holder, position);


            holder.singlePlanTravelOptionOneName.setVisibility(View.GONE);
            holder.singlePlanTravelOptionOneDate.setVisibility(View.GONE);
            holder.singlePlanTravelOptionTwoName.setVisibility(View.GONE);
            holder.singlePlanTravelOptionTwoDate.setVisibility(View.GONE);
            holder.singlePlanTravelOptionThreeName.setVisibility(View.GONE);
            holder.singlePlanTravelOptionThreeDate.setVisibility(View.GONE);


        } else {

            String currentSource, currentDestination;
            currentSource = list.getSavedPlaces().get((position - 1) / 2).getPlaceName();
            currentDestination = list.getSavedPlaces().get((position + 1) / 2).getPlaceName();


            int count = 0;

            ArrayList<KeyValueForCountPosition> countPositions = new ArrayList<>();

            ListIterator it1 = list.getSelectedBuses().listIterator();
            while (it1.hasNext()) {
                Bus bus = (Bus) it1.next();
                if (bus.getBusSource().equalsIgnoreCase(currentSource) && bus.getBusDestination().equalsIgnoreCase(currentDestination)) {
                    count = count + 1;
                    countPositions.add(new KeyValueForCountPosition("bus", it1.previousIndex()));
                }
            }
            ListIterator it2 = list.getSelectedTrains().listIterator();
            while (it2.hasNext()) {
                Train train = (Train) it2.next();
                if (train.getTrainSource().equalsIgnoreCase(currentSource) && train.getTrainDestination().equalsIgnoreCase(currentDestination)) {
                    count = count + 1;
                    countPositions.add(new KeyValueForCountPosition("train", it2.previousIndex()));
                }
            }
            ListIterator it3 = list.getSelectedFlights().listIterator();
            while (it3.hasNext()) {
                Flight flight = (Flight) it3.next();
                if (flight.getFlightSource().equalsIgnoreCase(currentSource) && flight.getFlightDestination().equalsIgnoreCase(currentDestination)) {
                    count = count + 1;
                    countPositions.add(new KeyValueForCountPosition("flight", it3.previousIndex()));
                }
            }
            if(count==0){
                holder.singlePlanTravelOptionOneName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionOneDate.setVisibility(View.GONE);
                holder.singlePlanTravelOptionTwoName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionTwoDate.setVisibility(View.GONE);
                holder.singlePlanTravelOptionThreeName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionThreeDate.setVisibility(View.GONE);
                holder.singlePlanOptionMoreButton.setVisibility(View.GONE);

                holder.singlePlanPlaceName.setText("No Convince b/w Cities Selected");
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color1 = generator.getColor(holder.singlePlanPlaceName.getText().toString());
                TextDrawable drawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.BLACK)
                        .useFont(Typeface.DEFAULT)
                        .fontSize(30) /* size in px */
                        .bold().useFont(Typeface.SERIF)
                        .endConfig()
                        .buildRect("No Convince b/w Cities Selected", color1);
                holder.singlePlanPlaceImage.setImageDrawable(drawable);
                holder.singlePlanPlaceName.setVisibility(View.GONE);

            }
            else if (count == 1) {
                holder.singlePlanTravelOptionOneName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionOneDate.setVisibility(View.GONE);
                holder.singlePlanTravelOptionTwoName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionTwoDate.setVisibility(View.GONE);
                holder.singlePlanTravelOptionThreeName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionThreeDate.setVisibility(View.GONE);

                String travelName = "";
                String travelDate = "";
                String type = countPositions.get(0).getType();
                int laaoPositionDo = countPositions.get(0).getPosition();
                if (type.equalsIgnoreCase("bus")) {

                    travelName = list.getSelectedBuses().get(laaoPositionDo).getBusTravelsName();

                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

                    Date sirfDateInput = null;
                    try {
                        sirfDateInput = input.parse(list.getSelectedBuses().get(laaoPositionDo).getBusDepartureTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String sirfDateOutput = output.format(sirfDateInput);

                    travelDate =sirfDateOutput ;

                } else if (type.equalsIgnoreCase("train")) {
                    travelName = list.getSelectedTrains().get(laaoPositionDo).getTrainName();

                    String startDate = list.getSelectedTrains().get(laaoPositionDo).getTrainDate().toString();
                    String startHour = list.getSelectedTrains().get(laaoPositionDo).getDepartureTime();
                    Date fulldate=null;
                    try {

                        SimpleDateFormat input = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
                        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

                        Date sirfDateInput = input.parse(startDate);
                        String sirfDateOutput = output.format(sirfDateInput);
                        fulldate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(sirfDateOutput + " " + startHour);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    travelDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(fulldate);

                } else if (type.equalsIgnoreCase("flight")) {
                    travelName = list.getSelectedFlights().get(laaoPositionDo).getAirline();

                    String startDate = list
                            .getSelectedFlights().get(laaoPositionDo).getFightDepartureDate().toString();
                    String startHour = list
                            .getSelectedFlights().get(laaoPositionDo).getDepartureTime();
                    Date fulldate=null;
                    try {

                        fulldate = new SimpleDateFormat("HH:mm yyyyMMdd").parse(startHour+" "+startDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    travelDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(fulldate);
                }
                holder.singlePlanPlaceName.setText(travelName);
                holder.singlePlanDate.setText(travelDate);

                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color1 = generator.getColor(holder.singlePlanPlaceName.getText().toString());
                TextDrawable drawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.BLACK)
                        .useFont(Typeface.DEFAULT)
                        .fontSize(30) /* size in px */
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRect(holder.singlePlanPlaceName.getText().toString() + "", color1);
                holder.singlePlanPlaceImage.setImageDrawable(drawable);
                applyClickEventsForTravel(holder,position);


            }else{
                holder.singlePlanTravelOptionOneName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionOneDate.setVisibility(View.GONE);
                holder.singlePlanTravelOptionTwoName.setVisibility(View.GONE);
                holder.singlePlanTravelOptionTwoDate.setVisibility(View.GONE);
                holder.singlePlanTravelOptionThreeName.setVisibility(View.GONE);
                holder.singlePlanOptionMoreButton.setVisibility(View.GONE);
                holder.singlePlanTravelOptionThreeDate.setVisibility(View.GONE);


                holder.singlePlanPlaceName.setText("More Than One Option Available\nClick to view all");
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color1 = generator.getColor(holder.singlePlanPlaceName.getText().toString());
                TextDrawable drawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.BLACK)
                        .useFont(Typeface.DEFAULT)
                        .fontSize(20) /* size in px */
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRect("More Than Three Option Available\nClick to view all", color1);

                holder.singlePlanPlaceName.setSingleLine(true);
                holder.singlePlanPlaceName.setVisibility(View.INVISIBLE);
                holder.singlePlanPlaceImage.setImageDrawable(drawable);
                applyClickEventsForTravel(holder,position);
            }

        }
    }

    private void applyClickEvents(SinglePlanAdapter.SinglePlanViewHolder holder, final int position) {

        holder.singlePlanPlaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.singlePlanPlaceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.singlePlanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.singlePlanOptionMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMoreOptionMenuClicked(position, view);
            }
        });

    }


    private void applyClickEventsForTravel(SinglePlanAdapter.SinglePlanViewHolder holder, final int position) {

        holder.singlePlanPlaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTravelClicked(position, view);
            }
        });
        holder.singlePlanPlaceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTravelClicked(position, view);
            }
        });
        holder.singlePlanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTravelClicked(position, view);
            }
        });
        holder.singlePlanOptionMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTravelClicked(position, view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ((list.getSavedPlaces().size()) * 2) - 1;
    }

    public interface SinglePlanAdapterListener {
        void onMoreOptionMenuClicked(int position, View view);
        void onTravelClicked(int position,View view);
        void onEverythingElseClicked(int position, View view);
    }

    public class KeyValueForCountPosition {
        String type;
        int position;

        public KeyValueForCountPosition(String type, int position) {
            this.type = type;
            this.position = position;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}

// } else if (count > 1 && count <= 3) {
//
//                holder.singlePlanTravelOptionOneName.setVisibility(View.VISIBLE);
//                holder.singlePlanTravelOptionOneDate.setVisibility(View.VISIBLE);
//                holder.singlePlanTravelOptionTwoName.setVisibility(View.VISIBLE);
//                holder.singlePlanTravelOptionTwoDate.setVisibility(View.VISIBLE);
//                holder.singlePlanTravelOptionThreeName.setVisibility(View.VISIBLE);
//                holder.singlePlanTravelOptionThreeDate.setVisibility(View.VISIBLE);
//
//
//                String travelName = "";
//                String travelDate = "";
//                for (int i = 0; i < count; i++) {
//                    String type = countPositions.get(i).getType();
//                    int laaoPositionDo = countPositions.get(i).getPosition();
//                    if (type.equalsIgnoreCase("bus")) {
//
//                        travelName = list.getSelectedBuses().get(laaoPositionDo).getBusTravelsName();
//                        travelDate = list.getSelectedBuses().get(laaoPositionDo).getBusDepartureTime();
//
//                        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//                        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm");
//                        String valDate=null;
//                        try {
//                            Date tempDate=sdf1.parse(travelDate);
//                            valDate=sdf2.format(tempDate);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        if(valDate!=null)
//                            travelDate=valDate;
//
//
//                    } else if (type.equalsIgnoreCase("train")) {
//                        travelName = list.getSelectedTrains().get(laaoPositionDo).getTrainName();
//                        travelDate = list.getSelectedTrains().get(laaoPositionDo).getTrainDate().toString();
//
//                        SimpleDateFormat sdf1=new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
//                        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm");
//                        String valDate=null;
//                        try {
//                            Date tempDate=sdf1.parse(travelDate);
//                            valDate=sdf2.format(tempDate);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
////                        if(valDate!=null)
//                            travelDate=valDate;
//
//                    } else if (type.equalsIgnoreCase("flight")) {
//                        travelName = list.getSelectedFlights().get(laaoPositionDo).getAirline();
//                        travelDate = list.getSelectedFlights().get(laaoPositionDo).getDepartureDate();
//
//                        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd't'HHmm");
//                        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm");
//                        String valDate=null;
//                        try {
//                            Date tempDate=sdf1.parse(travelDate);
//                            valDate=sdf2.format(tempDate);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
////                        if(valDate!=null)
//                             travelDate=valDate;
//
//
//                    }
//                    if (i == 0) {
//                        holder.singlePlanTravelOptionOneName.setText(travelName);
//                        holder.singlePlanTravelOptionOneDate.setText(travelDate);
//                    } else if (i == 1) {
//                        holder.singlePlanTravelOptionTwoName.setText(travelName);
//                        holder.singlePlanTravelOptionTwoDate.setText(travelDate);
//                    } else if (i == 2) {
//                        holder.singlePlanTravelOptionThreeName.setText(travelName);
//                        holder.singlePlanTravelOptionThreeDate.setText(travelDate);
//                    }
//                }
//                holder.singlePlanPlaceImage.setVisibility(View.GONE);
//                holder.singlePlanOptionMoreButton.setVisibility(View.GONE);
//                holder.singlePlanPlaceName.setVisibility(View.GONE);
//                holder.singlePlanDate.setVisibility(View.GONE);
//
//            } else {