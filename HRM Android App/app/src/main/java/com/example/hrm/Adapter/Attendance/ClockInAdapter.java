package com.example.hrm.Adapter.Attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.CheckIn.CheckInDetail;
import com.example.hrm.R;

import java.util.List;

public class ClockInAdapter extends RecyclerView.Adapter<ClockInAdapter.MyViewHolder> {
    Context context;
    CheckInDetail checkInDetail;
    private List<CheckInDetail> checkInDetailList;

    public ClockInAdapter(Context context, List<CheckInDetail> checkInDetailList) {
        this.context = context;
        this.checkInDetailList = checkInDetailList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView day;
        public TextView date;


        public MyViewHolder(View v) {
            super(v);
            day = v.findViewById(R.id.txt_twoin);
            date = v.findViewById(R.id.txt_reqtwoin);

        }
    }


    @NonNull
    @Override
    public ClockInAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clockin_layout, parent, false);

        ClockInAdapter.MyViewHolder vh = new ClockInAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockInAdapter.MyViewHolder holder, int position) {

        checkInDetail = checkInDetailList.get(position);

        //binding the data with the viewholder views
        holder.day.setText(checkInDetail.getDay());

//        if(checkInDetail.getType()=="4")
//        {
//            holder.date.setTextColor(Color.parseColor("#b38e34"));
//            holder.date.setText("Weekend");
//
//        }
//
//        if(checkInDetail.getType()==1)
//        {
//            holder.date.setTextColor(Color.parseColor("#b38e34"));
//            holder.date.setText(checkInDetail.getDate());
//        }
        if(checkInDetail.getType()==2)
        {
            holder.date.setText("Request CheckIn");
        }
        if(checkInDetail.getType()==4)
        {
            holder.date.setTextColor(Color.parseColor("#b38e34"));
            holder.date.setText("Weekend");
        }
//        if(checkInDetail.getType()==5)
//        {
//            holder.date.setText("Early Out");
//        }
//        if(checkInDetail.getType()==6)
//        {
//            holder.date.setText("Late");
//        }
//        if(checkInDetail.getType()==7)
//        {
//            holder.date.setText("Weekend");
//        }



    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return checkInDetailList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}