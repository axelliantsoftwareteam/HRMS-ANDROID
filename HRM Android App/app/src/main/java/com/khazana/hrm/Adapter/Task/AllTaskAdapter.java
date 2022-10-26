package com.khazana.hrm.Adapter.Task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.GetAllTask.Alltask.GetAlltaskData;
import com.khazana.hrm.R;

import java.util.List;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.MyViewHolder> {
    Context context;
    GetAlltaskData getAllTaskData;
    private List<GetAlltaskData> getAllTaskDataList;
    private AllTaskAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, GetAlltaskData obj, int position);
    }

    public void setOnItemClickListener(final AllTaskAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (AllTaskAdapter.OnItemClickListener) mItemClickListener;
    }
    public AllTaskAdapter(Context context, List<GetAlltaskData> respons) {
        this.context = context;
        this.getAllTaskDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView name;
        public TextView reason;
        public TextView duedate,details;

        public LinearLayout high,normal,low,complet,pend;
//        public TextView imageView;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txt_name);
            reason = v.findViewById(R.id.txt_notes);
            duedate = v.findViewById(R.id.txt_date);
            details = v.findViewById(R.id.txt_details);

            high =v.findViewById(R.id.ly_high);
            normal =v.findViewById(R.id.ly_normal);
            low =v.findViewById(R.id.ly_low);
            complet =v.findViewById(R.id.ly_complete);
            pend =v.findViewById(R.id.ly_pend);



//            imageView = v.findViewById(R.id.approved);
//            edit = v.findViewById(R.id.edit);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_tasks, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getAllTaskData = getAllTaskDataList.get(position);



        //binding the data with the viewholder views

        int type = getAllTaskData.getTag();
        if (type==1)
        {
            holder.normal.setVisibility(View.VISIBLE);

        }
        if (type==2)
        {
            holder.high.setVisibility(View.VISIBLE);

        }
        if (type==0)
        {
            holder.low.setVisibility(View.VISIBLE);

        }



        int status = getAllTaskData.getStatus();

//        String types= leaf.getLeaveType();

        if (status==1)
        {
            holder.complet.setVisibility(View.VISIBLE);
        }
        if (status==0)
        {

            holder.pend.setVisibility(View.VISIBLE);
        }

        holder.name.setText(getAllTaskData.getName());
        holder.reason.setText(getAllTaskData.getNotes());
        holder.duedate.setText(getAllTaskData.getDueDate());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getAllTaskDataList.get(position), position);

                       // showCustomDialog();
                    }

                }
            }
        });


//        Picasso.get().load(myChild_model.getImage_url()).placeholder(R.drawable.ic_launcher_background).into(holder.image);

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = holder.getAdapterPosition();
//                // check if item still exists
//                if (pos != RecyclerView.NO_POSITION) {
//
//                    AdminLeaveModel events = adminLeaveModelList.get(pos);
//                    Intent intent = new Intent(context, ActionLeaveActivity.class);
//                    intent.putExtra("id", events.getId());
//                    intent.putExtra("name", events.getName());
//                    intent.putExtra("start", events.getStart());
//                    intent.putExtra("end", events.getEnd());
//                    intent.putExtra("reason", events.getReason());
//                    context.startActivity(intent);
//                    // Toast.makeText(context, "Postion", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
//

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getAllTaskDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}