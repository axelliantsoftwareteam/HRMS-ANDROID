package com.example.hrm.Adapter.StaticData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Approval.AttendApproval.AttendApproval;
import com.example.hrm.Model.StaticDataModel.GetDataMember.GetMemberList;
import com.example.hrm.R;

import java.util.List;

public class StaticDataAdapter extends RecyclerView.Adapter<StaticDataAdapter.MyViewHolder> {
    Context context;

    GetMemberList getMemberList;
    private List<GetMemberList> getMemberLists;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetMemberList obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public StaticDataAdapter(Context context, List<GetMemberList> respons) {
        this.context = context;
        this.getMemberLists = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date,displayname,action;


        public MyViewHolder(View v) {
            super(v);
            displayname = v.findViewById(R.id.txt_name);
            date = v.findViewById(R.id.txt_value);
            action = v.findViewById(R.id.txt_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_member, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getMemberList = getMemberLists.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getMemberLists.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.displayname.setText(getMemberList.getDisplayMember());
        holder.date.setText(getMemberList.getValue());


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getMemberLists.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}