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

import com.khazana.hrm.Model.Payroll.TaxSlab.GetTaxSlabsData;
import com.khazana.hrm.R;

import java.util.List;

public class TaxSlabsAdapter extends RecyclerView.Adapter<TaxSlabsAdapter.MyViewHolder> {
    Context context;

    GetTaxSlabsData getTaxSlabsData;
    private List<GetTaxSlabsData> getTaxSlabsDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetTaxSlabsData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public TaxSlabsAdapter(Context context, List<GetTaxSlabsData> respons) {
        this.context = context;
        this.getTaxSlabsDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView effdate,max,mini;
        LinearLayout linearLayout;


        public MyViewHolder(View v) {
            super(v);
            effdate = v.findViewById(R.id.txtdate);
            max = v.findViewById(R.id.txtmax);
            mini = v.findViewById(R.id.txtmini);
            linearLayout=v.findViewById(R.id.ly_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_tax, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getTaxSlabsData = getTaxSlabsDataList.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getTaxSlabsDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

//        holder.name.setText(getTaxSlabsData.g);
        holder.effdate.setText(getTaxSlabsData.getEffectiveFrom());
        holder.max.setText(String.valueOf(getTaxSlabsData.getMaximum()));
        holder.mini.setText(String.valueOf(getTaxSlabsData.getMinimum()));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getTaxSlabsDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}