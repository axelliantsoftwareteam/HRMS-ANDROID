package com.khazana.hrm.Adapter.Approval;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.Approval.AttendApproval.AttendApproval;
import com.khazana.hrm.R;

import java.util.List;

public class ApprovalAttendanceAdapter extends RecyclerView.Adapter<ApprovalAttendanceAdapter.MyViewHolder> {
    Context context;

    AttendApproval attendApproval;
    private List<AttendApproval> attendApprovalList;
    private ApprovalAttendanceAdapter.OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, AttendApproval obj, int position);
    }

    public void setOnItemClickListener(final ApprovalAttendanceAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (ApprovalAttendanceAdapter.OnItemClickListener) mItemClickListener;
    }


    public ApprovalAttendanceAdapter(Context context, List<AttendApproval> respons) {
        this.context = context;
        this.attendApprovalList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView date,name,time,comment,status;
        public TextView ly_details;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txt_name);
            date = v.findViewById(R.id.txt_date);
            time = v.findViewById(R.id.txt_time);
            comment = v.findViewById(R.id.txt_comment);
            status = v.findViewById(R.id.txtstatus);
            ly_details =v.findViewById(R.id.txt_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_attendappr, parent, false);

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
        else if (staus==4)
        {
            holder.status.setText("Approved");
            holder.status.setBackgroundResource(R.color.green_800);
        }
        else
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

        holder.name.setText(attendApproval.getName());
        holder.date.setText(attendApproval.getDate());
        holder.time.setText(attendApproval.getTime());
        holder.comment.setText(attendApproval.getReason());


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