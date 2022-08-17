package com.example.hrm.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.hrm.Model.Calender.GetCalenderData;
import com.example.hrm.Model.CalenderEventObjects;
import com.example.hrm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akumar1 on 11/21/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private List<CalenderEventObjects> eventList;
    SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd,yyyy");
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details, date;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            details = (TextView) view.findViewById(R.id.tv_details);
            date = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    public CustomAdapter(Context context) {
        this.context = context;
        eventList = new ArrayList<>();
    }

    public void clearItem() {
        this.eventList.clear();
        notifyDataSetChanged();
    }

    public void addItem(CalenderEventObjects item) {
        this.eventList.add(item);
        Log.e("addItem", "" + item);
        notifyItemInserted(eventList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_items_listview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CalenderEventObjects events = eventList.get(position);
        holder.title.setText(events.getTitle());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = dateFormat.format(events.getDate());


//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime localDateTime = LocalDateTime.parse(events.getDate());
//        String localdate = localDateTime.format(formatter);

        holder.details.setText(dateTime);

//        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime localDateTimes = LocalDateTime.parse(events.getStart());
//        String localdates = localDateTimes.format(formatters);

        SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd");
        String dateTimes = dateFormats.format(events.getEdate());

        //   String date = newFormat.format(events.getDate().getTime());
        holder.date.setText(dateTimes);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}