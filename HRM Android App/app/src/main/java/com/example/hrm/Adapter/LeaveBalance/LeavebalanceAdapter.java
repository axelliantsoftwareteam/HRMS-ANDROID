package com.example.hrm.Adapter.LeaveBalance;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.CheckIn.CheckOutDetail;
import com.example.hrm.Model.LeaveBalanceModel.Response;
import com.example.hrm.R;

import java.util.List;

public class LeavebalanceAdapter extends RecyclerView.Adapter<LeavebalanceAdapter.MyViewHolder> {
    Context context;
    Response response;
    private List<Response> responseList;

    public LeavebalanceAdapter(Context context, List<Response> responses) {
        this.context = context;
        this.responseList = responses;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView leavetype;
        public TextView remainday,usedday;


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

        response = responseList.get(position);

        //binding the data with the viewholder views
        holder.leavetype.setText(response.getName());
        holder.usedday.setText(response.getUsed());
        holder.remainday.setText(response.getTotal());

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}