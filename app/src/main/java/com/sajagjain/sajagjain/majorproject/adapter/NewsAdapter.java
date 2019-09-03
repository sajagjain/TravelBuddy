package com.sajagjain.sajagjain.majorproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sajagjain.sajagjain.majorproject.R;

import java.util.List;

import twitter4j.Status;

/**
 * Created by sajag jain on 23-12-2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

private List<Status> news;
private int rowLayout;
private static Context context;


public static class NewsViewHolder extends RecyclerView.ViewHolder {
    LinearLayout newsLayout;
    TextView newsName;
    TextView newsText;
    TextView newsDated,newsLikes;
    TextView newsRetweets;

    public NewsViewHolder(View v) {
        super(v);
        newsLayout = (LinearLayout) v.findViewById(R.id.news_layout);
        newsName = (TextView) v.findViewById(R.id.news_name);
        newsText = (TextView) v.findViewById(R.id.news_text);
        newsDated=(TextView)v.findViewById(R.id.news_dated);
        newsLikes=(TextView)v.findViewById(R.id.news_likes);
        newsRetweets=(TextView)v.findViewById(R.id.news_retweets);
//        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // run scale animation and make it bigger
//                    Toast.makeText(context,"ho bhi raha call 1",Toast.LENGTH_LONG).show();
//                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_tv);
//                    v.startAnimation(anim);
//                    anim.setFillAfter(true);
//                } else {
//                    // run scale animation and make it smaller
//                    Toast.makeText(context,"ho bhi raha call 2",Toast.LENGTH_LONG).show();
//                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv);
//                    v.startAnimation(anim);
//                    anim.setFillAfter(true);
//                }
//            }
//        });
    }
}

    public NewsAdapter(List<Status> news, int rowLayout, Context context) {
        this.news = news;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NewsAdapter.NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, final int position) {

        holder.newsName.setText("@"+news.get(position).getUser().getScreenName());
        holder.newsText.setText(news.get(position).getText());
        holder.newsRetweets.setText(news.get(position).getRetweetCount()+"");
        holder.newsDated.setText(news.get(position).getCreatedAt().toString());
        holder.newsLikes.setText(news.get(position).getFavoriteCount()+"");
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
