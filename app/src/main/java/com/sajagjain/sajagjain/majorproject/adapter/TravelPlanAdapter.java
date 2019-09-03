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
import com.sajagjain.sajagjain.majorproject.model.TravelPlan;

import java.util.List;

/**
 * Created by sajag jain on 27-01-2018.
 */

public class TravelPlanAdapter extends RecyclerView.Adapter<TravelPlanAdapter.TravelPlanViewHolder> {

    private List<TravelPlan> plans;
    private int rowLayout;
    private Context context;
    TravelPlanAdapterListener listener;


    public static class TravelPlanViewHolder extends RecyclerView.ViewHolder {
        LinearLayout travelPlanLayout;
        TextView travelPlanDate;
        TextView travelPlanName;
        TextView travelPlanSourceDestination;
        ImageView travelPlanOptionMoreButton;
        ImageView travelPlanImage;

        public TravelPlanViewHolder(View v) {
            super(v);
            travelPlanLayout = (LinearLayout) v.findViewById(R.id.travel_plan_linear_layout);
            travelPlanDate = (TextView) v.findViewById(R.id.travel_plan_dates);
            travelPlanName = (TextView) v.findViewById(R.id.travel_plan_name);
            travelPlanSourceDestination = (TextView) v.findViewById(R.id.travel_plan_places);
            travelPlanOptionMoreButton = (ImageView) v.findViewById(R.id.travel_plan_save_plan_more_menu);
            travelPlanImage = (ImageView) v.findViewById(R.id.travel_plan_image);

        }
    }

    public TravelPlanAdapter(List<TravelPlan> plans, int rowLayout, Context context, TravelPlanAdapterListener listener) {
        this.plans = plans;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public TravelPlanAdapter.TravelPlanViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TravelPlanViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TravelPlanViewHolder holder, final int position) {

        holder.travelPlanName.setText(plans.get(position).getPlanName());
        holder.travelPlanSourceDestination.setText(plans.get(position).getTravelSource() + " - " + plans.get(position).getTravelDestination());
        holder.travelPlanDate.setText(plans.get(position).getStartDate() + " - " + plans.get(position).getEndDate());
//        Picasso.with(context).load(R.drawable.darjeeling).into(holder.travelPlanImage);
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
        int color1 = generator.getColor(holder.travelPlanName.getText().toString());
        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.DEFAULT)
                .fontSize(70) /* size in px */
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(holder.travelPlanName.getText().toString() + "", color1);
        holder.travelPlanImage.setImageDrawable(drawable);
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(TravelPlanAdapter.TravelPlanViewHolder holder, final int position) {

        holder.travelPlanName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.travelPlanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.travelPlanSourceDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.travelPlanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.travelPlanOptionMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMoreOptionMenuClicked(position, view);
            }
        });

    }


    @Override
    public int getItemCount() {
        return plans.size();
    }

    public interface TravelPlanAdapterListener {
        void onMoreOptionMenuClicked(int position, View view);

        void onEverythingElseClicked(int position, View view);
    }

}

