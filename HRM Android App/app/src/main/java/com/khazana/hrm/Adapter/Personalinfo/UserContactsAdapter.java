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

import com.khazana.hrm.Model.ClientInfo.UserContacts.GetUserContactData;
import com.khazana.hrm.R;

import java.util.ArrayList;
import java.util.List;

public class UserContactsAdapter extends RecyclerView.Adapter<UserContactsAdapter.MyViewHolder> {
    Context context;

    GetUserContactData getUserContactData;
    private List<GetUserContactData> getUserContactDataList;
    private OnItemClickListener mOnItemClickListener;

    public String[] mColors = {"#fff4df","#fce1da","#e5f4ed","#e9e0e8","ddf3f7"};



    public interface OnItemClickListener {
        void onItemClick(View view, GetUserContactData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<GetUserContactData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        getUserContactDataList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    public UserContactsAdapter(Context context, List<GetUserContactData> respons) {
        this.context = context;
        this.getUserContactDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView phone,addr;
        public ImageView action;
        public CardView ly;

        public MyViewHolder(View v) {
            super(v);
            addr = v.findViewById(R.id.txtaddress);
            phone = v.findViewById(R.id.txtphone);
            action = v.findViewById(R.id.txtaction);
            ly=v.findViewById(R.id.ly_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_contact, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getUserContactData = getUserContactDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getUserContactDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.addr.setText(String.valueOf(getUserContactData.getAddress()));
        holder.phone.setText(getUserContactData.getPhone());
        holder.ly.setCardBackgroundColor(Color.parseColor(mColors[position % 5]));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getUserContactDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}