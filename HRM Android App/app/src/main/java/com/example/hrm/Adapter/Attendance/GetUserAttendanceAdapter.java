package com.example.hrm.Adapter.Attendance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Attendance.Attendance;
import com.example.hrm.Model.Attendance.Attendance_list;
import com.example.hrm.R;

import java.util.List;

public class GetUserAttendanceAdapter extends RecyclerView.Adapter<GetUserAttendanceAdapter.MyViewHolder> {
    Context context;
    Attendance_list attendance_list;
    private List<Attendance_list> attendance_lists;


    public GetUserAttendanceAdapter(Context context, List<Attendance_list> attendance_list) {
        this.context = context;
        this.attendance_lists = attendance_list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //        // each data item is just a string in this case
        public ImageView day;


        public MyViewHolder(View v) {
            super(v);
           // day = v.findViewById(R.id.img_day);
            day = v.findViewById(R.id.img_day);


        }
    }


    @NonNull
    @Override
    public GetUserAttendanceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_days, parent, false);

        GetUserAttendanceAdapter.MyViewHolder vh = new GetUserAttendanceAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final GetUserAttendanceAdapter.MyViewHolder holder, int position) {

        attendance_list = attendance_lists.get(position);


        Log.e("Tag", "response" + attendance_list);

        if (attendance_list.getStatus().equals("Absent"))
        {
            holder.day.setImageResource(R.drawable.ic_absent);


        }
        if (attendance_list.getStatus().equals("Checkout is missing"))
        {
            holder.day.setImageResource(R.drawable.earlyout);


        }
        if (attendance_list.getStatus().equals("Weekend"))
        {
            holder.day.setImageResource(R.drawable.ic_late);


        }
        if (attendance_list.getStatus().equals("Present"))
        {
            holder.day.setImageResource(R.drawable.present);


        }

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return attendance_lists.size();

    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}