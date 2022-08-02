package com.example.hrm.Adapter.StaticData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.BasicSetup.Roles.GetRolesData;
import com.example.hrm.Model.BasicSetup.Skills.GetSkillsData;
import com.example.hrm.R;

import java.util.List;

public class RolesAdapter extends RecyclerView.Adapter<RolesAdapter.MyViewHolder> {
    Context context;

    GetRolesData getRolesData;
    private List<GetRolesData> getRolesDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetRolesData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public RolesAdapter(Context context, List<GetRolesData> respons) {
        this.context = context;
        this.getRolesDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name,descript,displyname,action,associate;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtname);
            descript = v.findViewById(R.id.txtdescrpt);
            displyname = v.findViewById(R.id.txtdisplyname);
            action = v.findViewById(R.id.txtaction);
            associate = v.findViewById(R.id.txtassociate);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_roles, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getRolesData = getRolesDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getRolesDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });
//        holder.associate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = holder.getAdapterPosition();
//                // check if item still exists
//                if (pos != RecyclerView.NO_POSITION)
//                {
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.onItemClick(v, getRolesDataList.get(position), position);
//
//                        // showCustomDialog();
//                    }
//
//                }
//            }
//        });

        holder.name.setText(getRolesData.getName());
        holder.descript.setText(getRolesData.getDescription());
        holder.displyname.setText(getRolesData.getDisplayName());

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getRolesDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}