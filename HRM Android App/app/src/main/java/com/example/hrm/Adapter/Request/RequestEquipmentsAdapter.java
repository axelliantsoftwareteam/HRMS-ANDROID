package com.example.hrm.Adapter.Request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.EquipementRequestModel.Equipment;
import com.example.hrm.Model.LeavesModel.Leaves;
import com.example.hrm.R;

import java.util.List;

public class RequestEquipmentsAdapter extends RecyclerView.Adapter<RequestEquipmentsAdapter.MyViewHolder> {
    Context context;


    Equipment equipment;
    private List<Equipment> equipmentList;


    public RequestEquipmentsAdapter(Context context, List<Equipment> equipment) {
        this.context = context;
        this.equipmentList= equipment;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView name;
        public TextView comment;

//
//        public TextView approved;
        public LinearLayout status,await,approv,reject,pend;
//        public TextView imageView;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txt_name);
//            type = v.findViewById(R.id.txt_leavetype);
//            start = v.findViewById(R.id.txt_start);
//            end = v.findViewById(R.id.txt_end);
            comment = v.findViewById(R.id.txt_comment);
//            approved =v.findViewById(R.id.txt_approved);
//
            await =v.findViewById(R.id.ly_await);
            approv =v.findViewById(R.id.ly_appr);
            reject =v.findViewById(R.id.ly_reject);
            pend =v.findViewById(R.id.ly_pend);



//            imageView = v.findViewById(R.id.approved);
//            edit = v.findViewById(R.id.edit);


        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_equiptreq, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        equipment = equipmentList.get(position);



        //binding the data with the viewholder views
        holder.name.setText(equipment.getItemName());
        int staus = equipment.getStatus();

//        String types= leaf.getLeaveType();

        if (staus==1)
        {
            holder.approv.setVisibility(View.VISIBLE);
        }
        if (staus==5)
        {
            holder.await.setVisibility(View.VISIBLE);
        }

        holder.comment.setText(equipment.getComment());



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
        return equipmentList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}