package com.example.hrm.Adapter.Calender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Calender.GetCalenderData;
import com.example.hrm.Model.LeavesModel.Leaves;
import com.example.hrm.Model.LeavesModel.WorkFlow;
import com.example.hrm.R;

import java.util.List;

import javax.ws.rs.GET;

public class CalenderEventAdapter extends RecyclerView.Adapter<CalenderEventAdapter.MyViewHolder> {
    Context context;
    GetCalenderData getCalenderData;

    private List<GetCalenderData> getCalenderDataList;


    public CalenderEventAdapter(Context context, List<GetCalenderData> getCalenderData) {
        this.context = context;
        this.getCalenderDataList= getCalenderData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView name;
        public TextView start;
        public TextView end;



        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txt_eventname);
            start = v.findViewById(R.id.txt_start);
            end = v.findViewById(R.id.txt_end);

//            imageView = v.findViewById(R.id.approved);
//            edit = v.findViewById(R.id.edit);
        }
    }


    @NonNull
    @Override
    public CalenderEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_event, parent, false);

        CalenderEventAdapter.MyViewHolder vh = new CalenderEventAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CalenderEventAdapter.MyViewHolder holder, int position) {

        getCalenderData = getCalenderDataList.get(position);



        //binding the data with the viewholder views
        holder.name.setText(getCalenderData.getTitle());
        holder.start.setText((CharSequence) getCalenderData.getStart());
        holder.end.setText((CharSequence) getCalenderData.getEnd());

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getCalenderDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}