package com.sajagjain.sajagjain.majorproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.RestaurantFirstStep;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sajag jain on 19-12-2017.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<RestaurantFirstStep> restaurant;
    private int rowLayout;
    private Context context;
    private RestaurantAdapterListener listener;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        LinearLayout restaurantLayout;
        TextView restaurantName;
        TextView restaurantDescription;
        ImageView restaurantShare,restaurantPhoto;

        public RestaurantViewHolder(View v) {
            super(v);
            restaurantLayout = (LinearLayout) v.findViewById(R.id.restaurant_layout);
            restaurantName = (TextView) v.findViewById(R.id.restaurant_name);
            restaurantDescription = (TextView) v.findViewById(R.id.restaurant_description);
            restaurantShare = (ImageView) v.findViewById(R.id.restaurant_share);
            restaurantPhoto = (ImageView) v.findViewById(R.id.restaurant_photo);
        }
    }

    public RestaurantAdapter(List<RestaurantFirstStep> restaurant, int rowLayout, Context context,RestaurantAdapterListener listener) {
        this.restaurant = restaurant;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RestaurantAdapter.RestaurantViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RestaurantAdapter.RestaurantViewHolder holder, final int position) {

        holder.restaurantName.setText(restaurant.get(position).getRestaurant().getTitle());
        holder.restaurantDescription.setText(restaurant.get(position).getRestaurant().getDescription());
        Picasso.with(context).load(restaurant.get(position).getRestaurant().getImageURL()).fit().centerCrop()
                .error(R.drawable.image_not_found).into(holder.restaurantPhoto);

        applyClickEvents(holder,position);
    }

    private void applyClickEvents(RestaurantAdapter.RestaurantViewHolder holder, final int position) {

        holder.restaurantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClickedInRestaurant(position,view);
            }
        });
        holder.restaurantPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClickedInRestaurant(position,view);
            }
        });
        holder.restaurantDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClickedInRestaurant(position,view);
            }
        });
        holder.restaurantShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRestaurantShareButtonClick(position,view);
            }
        });

    }
    public interface RestaurantAdapterListener{
        void onRestaurantShareButtonClick(int position,View view);
        void onEverythingElseClickedInRestaurant(int position,View view);
    }

    @Override
    public int getItemCount() {
        return restaurant.size();
    }

}

