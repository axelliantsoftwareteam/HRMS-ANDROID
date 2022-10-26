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

import com.khazana.hrm.Model.Payroll.HealthSlabs.GetHealthSlabData;
import com.khazana.hrm.R;

import java.util.List;

public class HealthSlabsAdapter extends RecyclerView.Adapter<HealthSlabsAdapter.MyViewHolder> {
    Context context;

    GetHealthSlabData getHealthSlabData;
    private List<GetHealthSlabData> getHealthSlabDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetHealthSlabData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public HealthSlabsAdapter(Context context, List<GetHealthSlabData> respons) {
        this.context = context;
        this.getHealthSlabDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView mini,max,efftform,dedamount,action;
        LinearLayout linearLayout;


        public MyViewHolder(View v) {
            super(v);
            mini = v.findViewById(R.id.txtmini);
            max = v.findViewById(R.id.txtmaxim);
            efftform = v.findViewById(R.id.txtefft);
//            dedamount = v.findViewById(R.id.txtded);
            action = v.findViewById(R.id.txtaction);
            linearLayout=v.findViewById(R.id.ly_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_healthslab, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getHealthSlabData = getHealthSlabDataList.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getHealthSlabDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.efftform.setText(getHealthSlabData.getEffectiveFrom());
        holder.mini.setText(String.valueOf(getHealthSlabData.getMinimum()));
        holder.max.setText(String.valueOf(getHealthSlabData.getMaximum()));
//        holder.dedamount.setText(String.valueOf(getHealthSlabData.getDeduction()));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getHealthSlabDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}