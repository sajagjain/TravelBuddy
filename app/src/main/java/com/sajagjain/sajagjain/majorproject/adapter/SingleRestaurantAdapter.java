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
import com.sajagjain.sajagjain.majorproject.model.SingleRestaurantFirstStep;

import java.util.List;

/**
 * Created by sajag jain on 21-12-2017.
 */

public class SingleRestaurantAdapter extends RecyclerView.Adapter<SingleRestaurantAdapter.SingleRestaurantViewHolder> {

    private List<SingleRestaurantFirstStep> restaurant;
    private int rowLayout;
    private Context context;
    private SingleRestaurantAdapterListener listener;

    public static class SingleRestaurantViewHolder extends RecyclerView.ViewHolder{
        LinearLayout singleRestaurantLayout;
        TextView singleRestaurantName,singleRestaurantRating;
        TextView singleRestaurantCuisine,singleRestaurantCost;
        TextView openZomato;
        ToggleButton singleRestaurantSaveThat;
        ImageView singleRestaurantImage;


        public SingleRestaurantViewHolder(View v) {
            super(v);
            singleRestaurantLayout = (LinearLayout) v.findViewById(R.id.single_res_vertical_layout);
            singleRestaurantName = (TextView) v.findViewById(R.id.single_res_name);
            singleRestaurantCuisine = (TextView) v.findViewById(R.id.single_res_cuisines);
//            singleRestaurantImage = (ImageView) v.findViewById(R.id.single_restaurant_image);
            singleRestaurantRating = (TextView) v.findViewById(R.id.single_res_ratings);
            singleRestaurantCost = (TextView) v.findViewById(R.id.single_res_avg_price_for_two);
            singleRestaurantSaveThat=(ToggleButton)v.findViewById(R.id.single_restaurant_save_that);
            openZomato = (TextView) v.findViewById(R.id.single_res_visit_zomato);
        }

    }

    public SingleRestaurantAdapter(List<SingleRestaurantFirstStep> restaurant, int rowLayout, Context context,SingleRestaurantAdapterListener listener) {
        this.restaurant = restaurant;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public SingleRestaurantAdapter.SingleRestaurantViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SingleRestaurantAdapter.SingleRestaurantViewHolder(view);
    }




    @Override
    public void onBindViewHolder(SingleRestaurantAdapter.SingleRestaurantViewHolder holder, final int position) {

        holder.singleRestaurantCuisine.setText(restaurant.get(position).getRestaurant().getCuisines().toString());
        holder.singleRestaurantName.setText(restaurant.get(position).getRestaurant().getName().toString());
        holder.singleRestaurantCost.setText(restaurant.get(position).getRestaurant().getAvgPriceForTwo().toString()+"INR");
        holder.singleRestaurantRating.setText(restaurant.get(position).getRestaurant().getRatings().getAggregateRating()+"("
                +restaurant.get(position).getRestaurant().getRatings().getVotes()+"votes)");

        applyClickEvents(holder,position);
    }

    @Override
    public int getItemCount() {
        return restaurant.size();
    }

    private void applyClickEvents(SingleRestaurantViewHolder holder, final int position) {

        holder.singleRestaurantSaveThat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSaveToggleButtonClicked(position,view);
            }
        });
        holder.openZomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOpenButtonClicked(position);
            }
        });


    }

    public interface SingleRestaurantAdapterListener {

        void onOpenButtonClicked(int position);

        void onSaveToggleButtonClicked(int position,View view);


    }
}


