package com.khazana.hrm.Adapter.StaticData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.BasicSetup.HolidayModel.GetHolidayData;
import com.khazana.hrm.R;

import java.util.List;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.MyViewHolder> {
    Context context;

    GetHolidayData getHolidayData;
    private List<GetHolidayData> getHolidayDataList;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, GetHolidayData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public HolidayAdapter(Context context, List<GetHolidayData> respons) {
        this.context = context;
        this.getHolidayDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView type, stdate, eddate, descpt, action;


        public MyViewHolder(View v) {
            super(v);
            type = v.findViewById(R.id.txt_type);
            stdate = v.findViewById(R.id.txt_stdate);
            eddate = v.findViewById(R.id.txt_enddate);
            descpt = v.findViewById(R.id.txt_value);
            action = v.findViewById(R.id.txt_action);


        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_holiday, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getHolidayData = getHolidayDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getHolidayDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.type.setText(getHolidayData.getType());
        holder.stdate.setText(getHolidayData.getStartDate());
        holder.eddate.setText(getHolidayData.getEndDate());
        holder.descpt.setText(getHolidayData.getDescription());


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getHolidayDataList.size();
        // return leavesList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}