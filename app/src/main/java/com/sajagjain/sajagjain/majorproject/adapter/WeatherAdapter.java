package com.sajagjain.sajagjain.majorproject.adapter;

/**
 * Created by sajag jain on 02-10-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sajagjain.sajagjain.majorproject.R;
import com.sajagjain.sajagjain.majorproject.model.Weather;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.squareup.picasso.Picasso.with;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<Weather> weather;
    private int rowLayout;
    private Context context;


    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        LinearLayout weatherTemplate;
        TextView date;
        TextView time;
        TextView currentTemperature;
        TextView minimumTemperature,maximumTemperature;
        ImageView weatherIcon;
        TextView weatherMessage;

        public WeatherViewHolder(View v) {
            super(v);
            weatherTemplate = (LinearLayout) v.findViewById(R.id.weather_template);
            date = (TextView) v.findViewById(R.id.date_temperature_indicator);
            time = (TextView) v.findViewById(R.id.time_temperature_indicator);
            currentTemperature = (TextView) v.findViewById(R.id.current_temprature_indicator);
            minimumTemperature = (TextView) v.findViewById(R.id.min_temprature_indicator);
            maximumTemperature = (TextView) v.findViewById(R.id.max_temprature_indicator);
            weatherIcon = (ImageView) v.findViewById(R.id.weather_icon);
            weatherMessage = (TextView) v.findViewById(R.id.weather_message);
        }
    }

    public WeatherAdapter(List<Weather> weather, int rowLayout, Context context) {
        this.weather = weather;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new WeatherViewHolder(view);
    }


    @Override
    public void onBindViewHolder(WeatherViewHolder holder, final int position) {
        String date_s = weather.get(position).getDate();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dt.parse(date_s);
        } catch (ParseException e) {
            Log.d("Parsing error",e.getMessage());
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MMM/yyyy");
        SimpleDateFormat dt2 = new SimpleDateFormat("HH:mm:ss");

        holder.date.setText(dt1.format(date));
        holder.time.setText(dt2.format(date));
        String forecast=weather.get(position).getDescription().get(0).getWeatherDescription();
        holder.weatherMessage.setText(forecast);
        holder.currentTemperature.setText(new DecimalFormat("#.00").format(Double.parseDouble(weather.get(position).getMain().getTemperature())-273.15D)+"°C");
        holder.minimumTemperature.setText(new DecimalFormat("#.00").format(Double.parseDouble(weather.get(position).getMain().getMinTemperature())-273.15D)+"°C");
        holder.maximumTemperature.setText(new DecimalFormat("#.00").format(Double.parseDouble(weather.get(position).getMain().getMaxTemperature())-273.15D)+"°C");
        if(forecast.equalsIgnoreCase("clear sky")) {
            with(context).load(R.drawable.sunny_weather_icon).into(holder.weatherIcon);
        }else if(forecast.equalsIgnoreCase("scattered cloud")||forecast.equalsIgnoreCase("overcast clouds")||forecast.equalsIgnoreCase("scattered clouds")||forecast.equalsIgnoreCase("broken clouds")||forecast.equalsIgnoreCase("few clouds")){
            with(context).load(R.drawable.partly_cloudy_weather_icon).into(holder.weatherIcon);
        }else if (forecast.equalsIgnoreCase("heavy rain")||forecast.equalsIgnoreCase("light rain")||forecast.equalsIgnoreCase("slight rain")||forecast
                .equalsIgnoreCase("rain")||forecast.equalsIgnoreCase("moderate rain")){
            with(context).load(R.drawable.rain_weather_icon).into(holder.weatherIcon);
        }
    }

    @Override
    public int getItemCount() {
        return weather.size();
    }
}
