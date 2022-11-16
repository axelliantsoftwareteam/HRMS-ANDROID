package com.khazana.hrm.Adapter.Attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.CheckIn.CheckOutDetail;
import com.khazana.hrm.R;

import java.util.List;

public class ClockOutAdapter extends RecyclerView.Adapter<ClockOutAdapter.MyViewHolder> {
    Context context;
    CheckOutDetail checkOutDetail;
    private List<CheckOutDetail> checkOutDetailList;
    private ClockOutAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, CheckOutDetail obj, int position);
    }

    public void setOnItemClickListener(final ClockOutAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (ClockOutAdapter.OnItemClickListener) mItemClickListener;
    }
    public ClockOutAdapter(Context context, List<CheckOutDetail> checkOutDetailList) {
        this.context = context;
        this.checkOutDetailList = checkOutDetailList;
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
    public ClockOutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clockin_layout, parent, false);

        ClockOutAdapter.MyViewHolder vh = new ClockOutAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockOutAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        checkOutDetail = checkOutDetailList.get(position);

        //binding the data with the viewholder views
        holder.day.setText(checkOutDetail.getDay());

        if(checkOutDetail.getType().equals("1"))
        {
            holder.date.setTextColor(Color.parseColor("#b38e34"));
            holder.date.setText(checkOutDetail.getHours());
        }
        if(checkOutDetail.getType().equals("2"))
        {
            holder.date.setTextColor(Color.parseColor("#FF658BE6"));
            holder.date.setText("Request Check-Out");

            holder.date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(v, checkOutDetailList.get(position), position);
                        }
                        //showCustomDialog();
                    }
                }
            });
        }
        if(checkOutDetail.getType().equals("4"))
        {
            holder.date.setTextColor(Color.parseColor("#666666"));
            holder.date.setText("Weekend");
        }
        if(checkOutDetail.getType().equals("10"))
        {
            holder.date.setTextColor(Color.parseColor("#b38e34"));
            holder.date.setText("Request Pending");
        }


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return checkOutDetailList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}