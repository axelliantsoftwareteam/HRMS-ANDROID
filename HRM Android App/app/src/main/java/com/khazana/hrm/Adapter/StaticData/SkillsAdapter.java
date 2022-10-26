package com.khazana.hrm.Adapter.StaticData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.BasicSetup.Skills.GetSkillsData;
import com.khazana.hrm.R;

import java.util.ArrayList;
import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyViewHolder> {
    Context context;

    GetSkillsData getSkillsData;
    private List<GetSkillsData> getSkillsDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetSkillsData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<GetSkillsData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        getSkillsDataList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    public SkillsAdapter(Context context, List<GetSkillsData> respons) {
        this.context = context;
        this.getSkillsDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name,descript,addr,action;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtname);
            descript = v.findViewById(R.id.txtdescrpt);
            addr = v.findViewById(R.id.txtaddr);
            action = v.findViewById(R.id.txtaction);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_skills, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getSkillsData = getSkillsDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getSkillsDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.name.setText(getSkillsData.getName());
        holder.descript.setText(getSkillsData.getDescription());

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getSkillsDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}