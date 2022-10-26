package com.khazana.hrm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.khazana.hrm.Model.Calender.GetCalenderInfoData;
import com.khazana.hrm.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by  on 11/21/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {
    private Context context;
    private List<GetCalenderInfoData> eventList;
    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

//    private String color_array[] = {
//            String.valueOf(R.color.deep_orange_800),
//            String.valueOf(R.color.blue_500),
//            String.valueOf(R.color.amber_800),
//            String.valueOf(R.color.deep_purple_500)
//    };

   // public String[] mColors = {"#FF7043","#42A5F5","#FFB300","#9575CD"};
   public String[] mColors = {"#fff4df","#fce1da","#e5f4ed","#e9e0e8","ddf3f7"};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details, date,stime,etime,edate;
        public CardView ly;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            details = (TextView) view.findViewById(R.id.tv_details);
//            date = (TextView) view.findViewById(R.id.tv_date);
            edate = (TextView) view.findViewById(R.id.txt_end);
//            stime = (TextView) view.findViewById(R.id.tv_stime);
//            etime = (TextView) view.findViewById(R.id.txt_etime);
            ly=(CardView) view.findViewById(R.id.ly_details);

        }
    }

//    public CustomAdapter(Context context)
//    {
//        this.context = context;
//        eventList = new ArrayList<>();
//    }
    public CustomAdapter(Context context,List<GetCalenderInfoData> getCalenderInfoDataList) {
        this.context = context;
        this.eventList= getCalenderInfoDataList;
    }


    public void clearItem() {
        this.eventList.clear();
        notifyDataSetChanged();
    }



//    public void addItem(GetCalenderInfoData item) {
//        this.eventList.add(item);
//        Log.e("addItem", "" + item);
//        notifyItemInserted(eventList.size());
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_items_listview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetCalenderInfoData events = eventList.get(position);
        holder.title.setText(events.getTitle());
        holder.details.setText(events.getMeta());
//        holder.date.setText(events.getStart().getDate());
//        holder.stime.setText(events.getStart().getTime());
//        holder.etime.setText(events.getEnd().getTime());
        holder.ly.setCardBackgroundColor(Color.parseColor(mColors[position % 4]));



//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date end = dateFormat.parse(date);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        String date = newFormat.format(events.getStart().getDate());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}