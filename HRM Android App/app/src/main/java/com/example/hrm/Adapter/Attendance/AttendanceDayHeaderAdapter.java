package com.example.hrm.Adapter.Attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Attendance.MonthHeader;
import com.example.hrm.R;

import java.util.List;

public class AttendanceDayHeaderAdapter extends RecyclerView.Adapter<AttendanceDayHeaderAdapter.MyViewHolder> {
    Context context;
    MonthHeader monthHeader;

    private List<MonthHeader> monthHeaderList;


    public AttendanceDayHeaderAdapter(Context context, List<MonthHeader> monthHeader) {
        this.context = context;
        this.monthHeaderList= monthHeader;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView day;
        public TextView type;


        public MyViewHolder(View v) {
            super(v);
            day = v.findViewById(R.id.day_head);
            type = v.findViewById(R.id.day);

        }
    }


    @NonNull
    @Override
    public AttendanceDayHeaderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.monthheader, parent, false);

        AttendanceDayHeaderAdapter.MyViewHolder vh = new AttendanceDayHeaderAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceDayHeaderAdapter.MyViewHolder holder, int position) {

        monthHeader = monthHeaderList.get(position);


        holder.type.setText(monthHeader.getWeekDay());
        //binding the data with the viewholder views
        holder.day.setText(Integer.toString(monthHeader.getDay()));





    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return monthHeaderList.size();

    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}