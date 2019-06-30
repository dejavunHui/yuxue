package com.example.androidmvp.mvp.wealth.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidmvp.R;
import com.example.androidmvp.mvp.entity.weather.WealthResultReal;

import java.util.List;

public class CityWealthAdapter extends RecyclerView.Adapter<CityWealthAdapter.MyViewHolder> {

    private Context context;
    private List<WealthResultReal.ForeCast> datas;


    public CityWealthAdapter(Context context, List<WealthResultReal.ForeCast> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.week_wealth_item, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        WealthResultReal.ForeCast foreCast = datas.get(i);
        holder.week.setText(foreCast.date);
        holder.week.setTextSize(17);
        int iconid = getWealthIcon(foreCast.weather);
        if (-1 != iconid)
            Glide.with(context).load(iconid).into(holder.icon);
        holder.wealth_info.setText(foreCast.weather);
        holder.wealth_info.setTextSize(17);
        holder.tempture.setText(foreCast.lowTemp.substring(2,foreCast.lowTemp.length()) + "~"
                +foreCast.highTemperature.substring(2,foreCast.highTemperature.length()));
        holder.tempture.setTextSize(17);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<WealthResultReal.ForeCast> getDatas() {
        return datas;
    }

    public void setDatas(List<WealthResultReal.ForeCast> datas) {
        this.datas = datas;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView week;
        private ImageView icon;
        private TextView wealth_info;
        private TextView tempture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            week = itemView.findViewById(R.id.week);
            icon = itemView.findViewById(R.id.wealt_icon);
            wealth_info = itemView.findViewById(R.id.wealth_info);
            tempture = itemView.findViewById(R.id.tempture);
        }
    }

    public int getWealthIcon(String type) {
        int id = -1;
        if (type == "晴") {

        }
        return id;
    }
}
