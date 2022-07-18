package com.example.hrm.Adapter.Approval;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Approval.AttendApproval.AttendApproval;
import com.example.hrm.Model.Approval.LeavesApproval.LeaveApproval;
import com.example.hrm.R;

import java.util.List;

public class ApprovalLeaveAdapter extends RecyclerView.Adapter<ApprovalLeaveAdapter.MyViewHolder> {
    Context context;

    LeaveApproval leaveApproval;
    private List<LeaveApproval> leaveApprovalList;
    private ApprovalLeaveAdapter.OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, LeaveApproval obj, int position);
    }

    public void setOnItemClickListener(final ApprovalLeaveAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (ApprovalLeaveAdapter.OnItemClickListener) mItemClickListener;
    }


    public ApprovalLeaveAdapter(Context context, List<LeaveApproval> respons) {
        this.context = context;
        this.leaveApprovalList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date,name,endate,comment,status,leavetype;
        public TextView ly_details;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txt_name);
            date = v.findViewById(R.id.txt_date);
            endate = v.findViewById(R.id.txt_enddate);
            comment = v.findViewById(R.id.txt_comment);
            status = v.findViewById(R.id.txtstatus);
            leavetype =v.findViewById(R.id.txt_leavetype);
            ly_details =v.findViewById(R.id.txt_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_leaveappr, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        leaveApproval = leaveApprovalList.get(position);

        int staus = leaveApproval.getStatus();
        if (staus==1)
        {
            holder.status.setText("Approved");
            holder.status.setBackgroundResource(R.color.green_800);
        }
        if (staus==0)
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
                        mOnItemClickListener.onItemClick(v, leaveApprovalList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.name.setText(leaveApproval.getName());
        holder.leavetype.setText(leaveApproval.getLeaveType());
        holder.date.setText(leaveApproval.getDate());
        holder.endate.setText(leaveApproval.getEndDate());
        holder.comment.setText(leaveApproval.getComment());


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return leaveApprovalList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}