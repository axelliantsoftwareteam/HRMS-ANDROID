package com.khazana.hrm.Adapter.Request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.ReqAttendList.Response;
import com.khazana.hrm.R;

import java.util.List;

public class RequestAttendanceAdapter extends RecyclerView.Adapter<RequestAttendanceAdapter.MyViewHolder> {
    Context context;

    Response response;
    private List<Response> responseList;


    public RequestAttendanceAdapter(Context context, List<Response> responses) {
        this.context = context;
        this.responseList= responses;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView type;

        public TextView date;
        public TextView time;
        public TextView comment;

        public LinearLayout status,await,approv,reject,pend;
//        public TextView imageView;

        public MyViewHolder(View v) {
            super(v);
            type = v.findViewById(R.id.txt_leavetype);
            date = v.findViewById(R.id.txt_date);
            time = v.findViewById(R.id.txt_time);
            comment = v.findViewById(R.id.txt_comment);

//            await =v.findViewById(R.id.ly_await);
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
                .inflate(R.layout.lay_attendreq, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        response = responseList.get(position);



        //binding the data with the viewholder views

        int type =response.getType();
        if (type==1)
        {
            holder.type.setText("Check In");

        }
       else if (type==2)
        {
            holder.type.setText("Check Out");

        }

        int staus = response.getStatus();

//        String types= leaf.getLeaveType();

        if (staus==1)
        {
            holder.approv.setVisibility(View.VISIBLE);
            holder.pend.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }
        else if (staus==4)
        {
            holder.approv.setVisibility(View.VISIBLE);
            holder.pend.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }
        else if (staus==5)
        {
            holder.pend.setVisibility(View.VISIBLE);
            holder.approv.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }
        else if (staus==0)
        {
            holder.approv.setVisibility(View.GONE);
            holder.pend.setVisibility(View.VISIBLE);
            holder.reject.setVisibility(View.GONE);
        }
        else
        {
            holder.reject.setVisibility(View.VISIBLE);
            holder.approv.setVisibility(View.GONE);
            holder.pend.setVisibility(View.GONE);
        }


        holder.date.setText(response.getDate());
        holder.time.setText(response.getTime());
        holder.comment.setText(response.getReason());



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
        return responseList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}