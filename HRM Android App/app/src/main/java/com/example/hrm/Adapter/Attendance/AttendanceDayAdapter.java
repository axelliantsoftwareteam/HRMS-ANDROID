package com.example.hrm.Adapter.Attendance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Attendance.Attendance;
import com.example.hrm.Model.Attendance.Attendance_list;
import com.example.hrm.R;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDayAdapter extends RecyclerView.Adapter<AttendanceDayAdapter.MyViewHolder> {
    Context context;
    Attendance attendancelist;
    private List<Attendance> attendancelists;
    Attendance_list attendance_list;


    private List<Attendance_list> attendance_listArrayList;

    public AttendanceDayAdapter(Context context, List<Attendance> attendancelist) {
        this.context = context;
        this.attendancelists = attendancelist;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //        // each data item is just a string in this case
        public ImageView day;
        public TextView name;


        public MyViewHolder(View v) {
            super(v);
           // day = v.findViewById(R.id.img_day);
            name = v.findViewById(R.id.username);


        }
    }


    @NonNull
    @Override
    public AttendanceDayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_leave, parent, false);

        AttendanceDayAdapter.MyViewHolder vh = new AttendanceDayAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceDayAdapter.MyViewHolder holder, int position) {

        attendancelist = attendancelists.get(position);
        Log.e("Tag", "response" + attendancelist);

//        List arrayList = new ArrayList<>(attendancelists);
//        for (int i = 0; i < arrayList.size(); i++) {
//            //Storing names to string array
            //   Attendance emlent= (Attendance) arrayList.set(i, arrayList.get(i).toString());

        attendance_listArrayList = attendancelist.getAttendanceList();

        holder.name.setText(attendancelist.getName());

//
//            for (int j = 0; j < attendance_listArrayList.size(); j++)
//            {
//                attendance_list=attendance_listArrayList.get(j);
//                holder.name.setText(attendance_list.getStatus());
//            //    Toast.makeText(context, ""+attendance_list.getStatus(), Toast.LENGTH_SHORT).show();
//
//            }


       // }


        //  holder.name.setText(attendancelist.getName());


//        holder.type.setText(attendancelist.getWeekDay());
//        //binding the data with the viewholder views
//        holder.day.setText(Integer.toString(attendancelist.getDay()));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return attendancelists.size();

    }
//    @Override
//    public int getItemCount() {
//        return attendancelists == null ? 0 : attendancelists.size();
//    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}