package com.khazana.hrm.Adapter.Personalinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.ClientInfo.UserExprience.GetExpByIdData;
import com.khazana.hrm.R;

import java.util.ArrayList;
import java.util.List;

public class UserExperienceAdapter extends RecyclerView.Adapter<UserExperienceAdapter.MyViewHolder> {
    Context context;

    GetExpByIdData getExpByIdData;
    private List<GetExpByIdData> getExpByIdDataList;
    private OnItemClickListener mOnItemClickListener;
    public String[] mColors = {"#fff4df","#fce1da","#e5f4ed","#e9e0e8","ddf3f7"};



    public interface OnItemClickListener {
        void onItemClick(View view, GetExpByIdData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<GetExpByIdData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        getExpByIdDataList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    public UserExperienceAdapter(Context context, List<GetExpByIdData> respons) {
        this.context = context;
        this.getExpByIdDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView design,company,resigndate,jobdescrtp,salary;
        public ImageView action;
        public CardView ly;


        public MyViewHolder(View v) {
            super(v);
            design = v.findViewById(R.id.txtdesign);
            company = v.findViewById(R.id.txtcompy);
            resigndate = v.findViewById(R.id.txtresign);
            jobdescrtp = v.findViewById(R.id.txtjobdesp);
            salary = v.findViewById(R.id.txtlatsalry);
            ly=v.findViewById(R.id.ly_details);

            action = v.findViewById(R.id.txtaction);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_expr, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getExpByIdData = getExpByIdDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getExpByIdDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.design.setText(String.valueOf(getExpByIdData.getDesignation()));
        holder.company.setText(getExpByIdData.getCompany());
        holder.salary.setText(getExpByIdData.getLastSalary());
        holder.resigndate.setText(getExpByIdData.getResignDate());
        holder.jobdescrtp.setText(getExpByIdData.getJobDescription());
        holder.ly.setCardBackgroundColor(Color.parseColor(mColors[position % 5]));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getExpByIdDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}