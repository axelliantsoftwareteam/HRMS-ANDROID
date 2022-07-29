package com.example.hrm.Adapter.LeaveBalance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.LeaveBalanceModel.LeaveBalanceData;
import com.example.hrm.R;

import org.eazegraph.lib.charts.PieChart;

import java.util.List;

public class LeavebalanceAdapter extends RecyclerView.Adapter<LeavebalanceAdapter.MyViewHolder> {
    Context context;
    LeaveBalanceData leaveBalanceData;
    private List<LeaveBalanceData> leaveBalanceDataList;

    public LeavebalanceAdapter(Context context, List<LeaveBalanceData> respons) {
        this.context = context;
        this.leaveBalanceDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView leavetype;
        public TextView remainday,usedday;
        PieChart pieChart;


        public MyViewHolder(View v) {
            super(v);
            leavetype = v.findViewById(R.id.txt_leavetype);
            usedday = v.findViewById(R.id.txt_usedleave);
            remainday = v.findViewById(R.id.txt_total);


        }
    }


    @NonNull
    @Override
    public LeavebalanceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leavebal_layout, parent, false);

        LeavebalanceAdapter.MyViewHolder vh = new LeavebalanceAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final LeavebalanceAdapter.MyViewHolder holder, int position) {

        leaveBalanceData = leaveBalanceDataList.get(position);

        //binding the data with the viewholder views
        holder.leavetype.setText(leaveBalanceData.getName());
        holder.usedday.setText(leaveBalanceData.getUsed());
        holder.remainday.setText(leaveBalanceData.getTotal());

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return leaveBalanceDataList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}