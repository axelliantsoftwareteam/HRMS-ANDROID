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

import com.khazana.hrm.Model.ClientInfo.UserSkills.GetUserSkillByIdData;
import com.khazana.hrm.R;

import java.util.ArrayList;
import java.util.List;

public class UserSkillsAdapter extends RecyclerView.Adapter<UserSkillsAdapter.MyViewHolder> {
    Context context;

    GetUserSkillByIdData getUserSkillByIdData;
    private List<GetUserSkillByIdData> getUserSkillByIdDataList;
    private OnItemClickListener mOnItemClickListener;


    public String[] mColors = {"#fff4df","#fce1da","#e5f4ed","#e9e0e8","ddf3f7"};


    public interface OnItemClickListener {
        void onItemClick(View view, GetUserSkillByIdData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<GetUserSkillByIdData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        getUserSkillByIdDataList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    public UserSkillsAdapter(Context context, List<GetUserSkillByIdData> respons) {
        this.context = context;
        this.getUserSkillByIdDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView gradu;
        public ImageView action;
        public CardView ly;



        public MyViewHolder(View v) {
            super(v);
            gradu = v.findViewById(R.id.txtskills);
            action = v.findViewById(R.id.txtactiondelet);
            ly=v.findViewById(R.id.ly_details);


        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_skillls, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getUserSkillByIdData = getUserSkillByIdDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getUserSkillByIdDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.gradu.setText(String.valueOf(getUserSkillByIdData.getSkill()));
        holder.ly.setCardBackgroundColor(Color.parseColor(mColors[position % 5]));


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getUserSkillByIdDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}