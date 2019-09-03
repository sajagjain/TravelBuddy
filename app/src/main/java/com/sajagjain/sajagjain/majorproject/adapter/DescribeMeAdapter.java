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
import com.sajagjain.sajagjain.majorproject.model.DescribeMeEverythingThirdStep;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sajag jain on 08-01-2018.
 */

public class DescribeMeAdapter extends RecyclerView.Adapter<DescribeMeAdapter.DescribeViewHolder> {

    private List<DescribeMeEverythingThirdStep> describeMe;
    private int rowLayout;
    private Context context;


    public static class DescribeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout describeMeReviewerLayout;
        TextView describeMeReviewerName;
        TextView describeMeReviewersReview;
        TextView describeMeReviewTime;
        TextView describeMeReviewerRating;
        ImageView describeMeReviewersImage;

        public DescribeViewHolder(View v) {
            super(v);
            describeMeReviewerLayout = (LinearLayout) v.findViewById(R.id.describe_me_linear_layout);
            describeMeReviewerName = (TextView) v.findViewById(R.id.describe_me_reviewer_name);
            describeMeReviewerRating=(TextView) v.findViewById(R.id.describe_me_reviewer_rating);
            describeMeReviewersReview=(TextView) v.findViewById(R.id.describe_me_reviewer_review);
            describeMeReviewTime=(TextView) v.findViewById(R.id.describe_me_reviewer_review_time);
            describeMeReviewersImage=(ImageView) v.findViewById(R.id.describe_me_reviewer_photo);
        }
    }

    public DescribeMeAdapter(List<DescribeMeEverythingThirdStep> describeMe, int rowLayout, Context context) {
        this.describeMe = describeMe;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public DescribeMeAdapter.DescribeViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new DescribeMeAdapter.DescribeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DescribeMeAdapter.DescribeViewHolder holder, final int position) {


        holder.describeMeReviewerName.setText(describeMe.get(position).getReviewAuthorName());
        holder.describeMeReviewersReview.setText(describeMe.get(position).getReviewAuthorReview());
        holder.describeMeReviewTime.setText(describeMe.get(position).getReviewAuthorRelativeTimeDescription());
        holder.describeMeReviewerRating.setText(describeMe.get(position).getReviewAuthorRating());
        Picasso.with(context).load(describeMe.get(position).getReviewAuthorProfilePhotoUrl())
                .error(R.drawable.image_not_found).into(holder.describeMeReviewersImage);


    }

    @Override
    public int getItemCount() {
        return describeMe.size();
    }
}

