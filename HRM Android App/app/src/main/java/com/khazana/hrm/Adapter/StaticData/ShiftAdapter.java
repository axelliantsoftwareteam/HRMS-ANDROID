package com.khazana.hrm.Adapter.StaticData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.BasicSetup.Shifts.GetShiftData;
import com.khazana.hrm.R;

import java.util.List;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.MyViewHolder> {
    Context context;

    GetShiftData getShiftData;
    private List<GetShiftData> getShiftDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetShiftData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public ShiftAdapter(Context context, List<GetShiftData> respons) {
        this.context = context;
        this.getShiftDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name,descript,sttime,endtime,action;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtname);
            descript = v.findViewById(R.id.txtdescrpt);
            sttime = v.findViewById(R.id.txt_start);
            endtime = v.findViewById(R.id.txt_enddate);
            action = v.findViewById(R.id.txtaction);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_shift, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getShiftData = getShiftDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getShiftDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.name.setText(getShiftData.getName());
        holder.descript.setText(getShiftData.getDescription());
        holder.sttime.setText(getShiftData.getStartTime());
        holder.endtime.setText(getShiftData.getEndTime());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getShiftDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}