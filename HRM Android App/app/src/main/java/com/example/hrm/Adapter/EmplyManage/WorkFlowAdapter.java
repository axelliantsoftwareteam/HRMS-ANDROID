package com.example.hrm.Adapter.EmplyManage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.BasicSetup.Skills.GetSkillsData;
import com.example.hrm.Model.EmployeeManagement.WorkFlow.GetWorkFlowData;
import com.example.hrm.R;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowAdapter extends RecyclerView.Adapter<WorkFlowAdapter.MyViewHolder> {
    Context context;

    GetWorkFlowData getWorkFlowData;
    private List<GetWorkFlowData> getWorkFlowDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetWorkFlowData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<GetWorkFlowData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        getWorkFlowDataList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    public WorkFlowAdapter(Context context, List<GetWorkFlowData> respons) {
        this.context = context;
        this.getWorkFlowDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name,descript,addr,action;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtname);
            descript = v.findViewById(R.id.txtdescrpt);
            action = v.findViewById(R.id.txtaction);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_workflow, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getWorkFlowData = getWorkFlowDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getWorkFlowDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.name.setText(getWorkFlowData.getName());
        holder.descript.setText(Integer.toString(getWorkFlowData.getTier()));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getWorkFlowDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}