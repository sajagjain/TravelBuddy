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
import com.sajagjain.sajagjain.majorproject.model.PointOfInterestFirstStep;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sajag jain on 16-12-2017.
 */

public class PointOfInterestAdapter extends RecyclerView.Adapter<PointOfInterestAdapter.PointOfInterestViewHolder> {

    private List<PointOfInterestFirstStep> points;
    private int rowLayout;
    private Context context;


    public static class PointOfInterestViewHolder extends RecyclerView.ViewHolder {
        LinearLayout poiLayout;
        TextView poiName;
        TextView poiAddress,poiRating;
        ImageView poiImage;


        public PointOfInterestViewHolder(View v) {
            super(v);
            poiLayout = (LinearLayout) v.findViewById(R.id.poi_layout);
            poiName = (TextView) v.findViewById(R.id.poi_name);
            poiAddress = (TextView) v.findViewById(R.id.poi_address);
            poiImage = (ImageView) v.findViewById(R.id.poi_photo);
            poiRating = (TextView) v.findViewById(R.id.poi_rating);
        }
    }

    public PointOfInterestAdapter(List<PointOfInterestFirstStep> points, int rowLayout, Context context) {
        this.points = points;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PointOfInterestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PointOfInterestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PointOfInterestViewHolder holder, final int position) {

        holder.poiName.setText(points.get(position).getPoiName().toString());
        holder.poiAddress.setText(points.get(position).getLocationAddress().toString());
        Picasso.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+points.get(position).getPhotos().get(0).getPhotoReference()+"&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE")
                .centerCrop().fit().error(R.drawable.image_not_found).into(holder.poiImage);
        holder.poiRating.setText(points.get(position).getPoiRating().toString());
    }

    @Override
    public int getItemCount() {
        return points.size();
    }
}
