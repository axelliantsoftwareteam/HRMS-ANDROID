package com.khazana.hrm.Adapter.Evaluation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Interface.RecyclerViewDataPass;
import com.khazana.hrm.Model.Evalution.Questions.GetEvalQuesData;
import com.khazana.hrm.Model.Evalution.ValueModel;
import com.khazana.hrm.R;

import java.util.ArrayList;
import java.util.List;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder>  {
    Context context;

    boolean status;

    int ratestar;
    String ItemName;

    GetEvalQuesData getEvalQuesData;
    private List<GetEvalQuesData> getEvalQuesDataList;
//
//    private List<ValueModel> valueModels;
RecyclerViewDataPass recyclerViewDataPass; //here is our data pass object

    ArrayList<ValueModel> valueModelArrayList = new ArrayList<>();
//    private OnItemClickListener mOnItemClickListener;
//

  //  public OnItemClick mCallback;

//    public interface OnItemClickListener
//    {
//        void onItemClick(View view, GetEvalQuesData obj, int position);
//    }
//

//    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
//    }

//
//    OnItemClick listener

    public QuestionAdapter(Context context, List<GetEvalQuesData> respons) {
        this.context = context;
        this.getEvalQuesDataList = respons;
//        this.mCallback=listener;

      //  this.recyclerViewDataPass=recyclerViewDataPass; //get data pass object from activity
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView ques;
        public EditText post;
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
                .inflate(R.layout.ly_evalquest, parent, false);

       MyViewHolder vh = new MyViewHolder(v);


        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position)  {

        getEvalQuesData = getEvalQuesDataList.get(position);

//        holder.post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = holder.getAdapterPosition();
//                // check if item still exists
//                if (pos != RecyclerView.NO_POSITION)
//                {
//
////                    if (mOnItemClickListener != null) {
////                        mOnItemClickListener.onItemClick(v, getEvalQuesDataList.get(position), position);
////
////                        // showCustomDialog();
////                    }
//
//                }
//            }
//        });
//
       holder.ques.setText(getEvalQuesData.getQuestion());
       // holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());

       // holder.generate.setText(getEvalQuesData.getGeneratedBy());
        holder.post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // String post =holder.post.getText().toString();
                 ItemName = holder.post.getText().toString();
               // mCallback.onClick(ItemName);

//                ValueModel valueModel=new ValueModel(getEvalQuesDataList.get(position).getComments());
//                valueModel.add();

               // Toast.makeText(context, ""+post, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }




        });
      holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
          @Override
          public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
          {
              final float numStars = ratingBar.getRating();
              ratestar =(int)numStars;
              Intent intent = new Intent("custom-message");
              //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
              intent.putExtra("rating",String.valueOf(ratestar));
              intent.putExtra("comment",ItemName);
              LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

              // mCallback.onClick(String.valueOf(ratestar));

            //  Toast.makeText(context, ""+ratestar, Toast.LENGTH_SHORT).show();

          }
      });

      //
//      Intent intent =new Intent();
//        Bundle args = new Bundle();
//        args.putSerializable("comment_list",(Serializable) valueModelArrayList);
//        intent.putExtra("BUNDLE_COMMENT",args);
//
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getEvalQuesDataList.size();
       // return leavesList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}