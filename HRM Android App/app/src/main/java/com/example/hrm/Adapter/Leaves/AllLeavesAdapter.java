package com.example.hrm.Adapter.Leaves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.LeavesModel.Leaves;
import com.example.hrm.R;

import java.util.List;

public class AllLeavesAdapter extends RecyclerView.Adapter<AllLeavesAdapter.MyViewHolder> {
    Context context;
    Leaves leaves;

    private List<Leaves> leavesList;

    public AllLeavesAdapter(Context context, List<Leaves> leaves) {
        this.context = context;
        this.leavesList= leaves;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView name;
        public TextView type;

        public TextView start;
        public TextView end;
        public TextView comment;
        public TextView approved;
        public TextView pending;
//        public TextView imageView;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txt_name);
            type = v.findViewById(R.id.txt_leavetype);
            start = v.findViewById(R.id.txt_start);
            end = v.findViewById(R.id.txt_end);
            comment = v.findViewById(R.id.txt_comment);
            approved =v.findViewById(R.id.txt_approved);
            pending =v.findViewById(R.id.txt_pending);


//            imageView = v.findViewById(R.id.approved);
//            edit = v.findViewById(R.id.edit);
        }
    }


    @NonNull
    @Override
    public AllLeavesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_allleaves, parent, false);

        AllLeavesAdapter.MyViewHolder vh = new AllLeavesAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AllLeavesAdapter.MyViewHolder holder, int position) {

        leaves = leavesList.get(position);




        //binding the data with the viewholder views
        holder.type.setText(leaves.getLeaveType());
//        String types= leaf.getLeaveType();
//        if (types== "Casual")
//        {
//            holder.type.setTextColor(Color.parseColor("#b38e34"));
//        }
        holder.name.setText(leaves.getName());
        holder.start.setText(leaves.getDate());
        holder.end.setText(leaves.getEndDate());
        holder.comment.setText(leaves.getComment());



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
        return leavesList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}