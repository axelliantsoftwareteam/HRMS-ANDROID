package com.khazana.hrm.Adapter.Payroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.Payroll.EobiSlab.GetEobiSlabData;
import com.khazana.hrm.R;

import java.util.List;

public class EobiSlabsAdapter extends RecyclerView.Adapter<EobiSlabsAdapter.MyViewHolder> {
    Context context;

    GetEobiSlabData getEobiSlabData;
    private List<GetEobiSlabData> getEobiSlabDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetEobiSlabData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public EobiSlabsAdapter(Context context, List<GetEobiSlabData> respons) {
        this.context = context;
        this.getEobiSlabDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView miniage,maxage,effform,dedform,action;
        LinearLayout linearLayout;


        public MyViewHolder(View v) {
            super(v);
            miniage = v.findViewById(R.id.txtminiage);
            maxage = v.findViewById(R.id.txtmaximage);
            effform = v.findViewById(R.id.txtefft);
//            dedform = v.findViewById(R.id.txtded);
//            action = v.findViewById(R.id.txtaction);
            linearLayout=v.findViewById(R.id.ly_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_eobislab, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getEobiSlabData = getEobiSlabDataList.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getEobiSlabDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.effform.setText(getEobiSlabData.getEffectiveFrom());
        holder.miniage.setText(String.valueOf(getEobiSlabData.getMinimumAge()));
        holder.maxage.setText(String.valueOf(getEobiSlabData.getMaximumAge()));
//        holder.dedform.setText(String.valueOf(getEobiSlabData.getDeduction()));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getEobiSlabDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}