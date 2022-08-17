package com.example.hrm.Adapter.Tags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.GetAllTask.Alltask.CountsInfo;
import com.example.hrm.R;

import java.util.List;

public class AllTagsAdapter extends RecyclerView.Adapter<AllTagsAdapter.MyViewHolder> {
    Context context;



    CountsInfo countsInfo;
    private List<CountsInfo> countsInfoList;


    public AllTagsAdapter(Context context, List<CountsInfo> countsInfo) {
        this.context = context;
        this.countsInfoList= countsInfo;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView name;
        public TextView number;

//
//        public TextView approved;
        public LinearLayout status,await,approv,reject,pend;
//        public TextView imageView;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txttags);
            number = v.findViewById(R.id.txtnumber);


        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_tags, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        countsInfo = countsInfoList.get(position);



        //binding the data with the viewholder views
        holder.name.setText(countsInfo.getAllTasks());




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
        return countsInfoList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}