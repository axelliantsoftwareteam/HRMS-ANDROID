package com.khazana.hrm.Adapter.Evaluation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.Evalution.GetEvaluationData;
import com.khazana.hrm.R;

import java.util.List;

public class SelfEvalutionAdapter extends RecyclerView.Adapter<SelfEvalutionAdapter.MyViewHolder> {
    Context context;

    boolean status;


    GetEvaluationData getEvalutionData;
    private List<GetEvaluationData> getEvalutionDataList;
    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, GetEvaluationData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }


    public SelfEvalutionAdapter(Context context, List<GetEvaluationData> respons) {
        this.context = context;
        this.getEvalutionDataList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView employ,generate,status,action;


        public MyViewHolder(View v) {
            super(v);
            employ = v.findViewById(R.id.txtemploy);
            generate = v.findViewById(R.id.txtgenerate);
            status = v.findViewById(R.id.txtstatus);
            action = v.findViewById(R.id.txtaction);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_evalution, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getEvalutionData = getEvalutionDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getEvalutionDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });



        holder.employ.setText(getEvalutionData.getEmployeeName());
        holder.generate.setText(getEvalutionData.getGeneratedBy());

      status = getEvalutionData.getIsCompleted();
      if (status==false)
      {
          holder.status.setText("In progress");
          holder.status.setBackgroundResource(R.color.red_500);
          holder.action.setVisibility(View.INVISIBLE);

      }
      else
      {
          holder.status.setText("Completed");
          holder.status.setBackgroundResource(R.color.green_900);
          holder.action.setText("View Detail");
          holder.action.setBackgroundResource(R.color.colorApp);
      }




    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getEvalutionDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}