package com.khazana.hrm.Adapter.Leaves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.SpinerModel.SpinerResponse;
import com.khazana.hrm.R;

import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.MyViewHolder> {
    Context context;
    SpinerResponse responses;

    private List<SpinerResponse> spinerResponseList;

    public SpinnerAdapter(Context context, List<SpinerResponse> respons) {
        this.context = context;
        this.spinerResponseList = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView type;



        public MyViewHolder(View v) {
            super(v);
            type = v.findViewById(R.id.txt_leavetype);

        }
    }


    @NonNull
    @Override
    public SpinnerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_spinner, parent, false);

        SpinnerAdapter.MyViewHolder vh = new SpinnerAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SpinnerAdapter.MyViewHolder holder, int position) {

        responses = spinerResponseList.get(position);

        holder.type.setText(responses.getValue());



    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return spinerResponseList.size();

    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}