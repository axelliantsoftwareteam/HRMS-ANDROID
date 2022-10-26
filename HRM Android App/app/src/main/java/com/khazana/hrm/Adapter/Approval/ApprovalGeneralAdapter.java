package com.khazana.hrm.Adapter.Approval;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.Approval.GenaralApproval.GetGeneralData;
import com.khazana.hrm.R;

import java.util.List;

public class ApprovalGeneralAdapter extends RecyclerView.Adapter<ApprovalGeneralAdapter.MyViewHolder>
{
    Context context;

    GetGeneralData attendApproval;
    private List<GetGeneralData> attendApprovalList;
    private ApprovalGeneralAdapter.OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetGeneralData obj, int position);
    }

    public void setOnItemClickListener(final ApprovalGeneralAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (ApprovalGeneralAdapter.OnItemClickListener) mItemClickListener;
    }


    public ApprovalGeneralAdapter(Context context, List<GetGeneralData> respons) {
        this.context = context;
        this.attendApprovalList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date,name,time,comment,status;
        public TextView ly_details;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txt_name);
            comment = v.findViewById(R.id.txt_comment);
            status = v.findViewById(R.id.txtstatus);
            ly_details =v.findViewById(R.id.txt_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_generalappr, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        attendApproval = attendApprovalList.get(position);

        int staus = attendApproval.getStatus();
        if (staus==1)
        {
            holder.status.setText("Approved");
            holder.status.setBackgroundResource(R.color.green_800);
        }
        else if (staus==2)
        {
            holder.status.setText("Rejected");
            holder.status.setBackgroundResource(R.color.red_500);
        }
        else if (staus==0)
        {
            holder.status.setText("Pending");
            holder.status.setBackgroundResource(R.color.amber_700);
        }

        holder.ly_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, attendApprovalList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.name.setText(attendApproval.getItemName());
        holder.comment.setText(attendApproval.getComment());


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return attendApprovalList.size();
        // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }

}