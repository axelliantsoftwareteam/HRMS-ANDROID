package com.example.hrm.Adapter.Evaluation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Evalution.Detail;
import com.example.hrm.R;

import java.util.List;


public class QuestionDetailsAdapter extends RecyclerView.Adapter<QuestionDetailsAdapter.MyViewHolder> {
    Context context;
    Detail detail;
    private List<Detail> getDetails;

    public QuestionDetailsAdapter(Context context, List<Detail> respons) {
        this.context = context;
        this.getDetails = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView ques;
        public TextView post;
        public RatingBar ratingBar;


        public MyViewHolder(View v) {
            super(v);
            ques = v.findViewById(R.id.txtques);
            post = v.findViewById(R.id.et_post);
            ratingBar = v.findViewById(R.id.rating_bar);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_evaldetailsquest, parent, false);

        MyViewHolder vh = new MyViewHolder(v);


        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        detail = getDetails.get(position);

        holder.ques.setText(detail.getQuestion());
        holder.post.setText(detail.getComments());
        // holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());

        if (detail.getRating().equals(""))
        {
            holder.ratingBar.setRating(Float.parseFloat("0"));
        }
        else
        {
            holder.ratingBar.setRating(Float.parseFloat(detail.getRating()));
            holder.ratingBar.setClickable(false);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getDetails.size();
        // return leavesList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}