package com.khazana.hrm.Adapter.Personalinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Model.ClientInfo.UserQualification.Deletitem;
import com.khazana.hrm.Model.ClientInfo.UserQualification.GetQualifyData;
import com.khazana.hrm.R;
import com.khazana.hrm.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class QualificationAdapter extends RecyclerView.Adapter<QualificationAdapter.MyViewHolder> {
    Context context;
Deletitem deletitem;
    SessionManager sessionManager;
    GetQualifyData getQualifyData;
    private List<GetQualifyData> getQualifyDataList;
    private OnItemClickListener mOnItemClickListener;
    public String[] mColors = {"#fff4df","#fce1da","#e5f4ed","#e9e0e8","ddf3f7"};


    public interface OnItemClickListener {
        void onItemClick(View view, GetQualifyData obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<GetQualifyData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        getQualifyDataList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    public QualificationAdapter(Context context, List<GetQualifyData> respons) {
        this.context = context;
        this.getQualifyDataList = respons;
    }
    public void clearItem() {
        this.getQualifyDataList.clear();
        notifyDataSetChanged();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView name,descript,gradu;
        public ImageView action;
        public CardView ly;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtname);
            descript = v.findViewById(R.id.txtdescrpt);
            gradu = v.findViewById(R.id.txtgradua);
            action = v.findViewById(R.id.txtaction);
            ly=v.findViewById(R.id.ly_details);



        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_qualify, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        getQualifyData = getQualifyDataList.get(position);

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
//                    GetQualifyData events = getQualifyDataList.get(pos);
//                    sessionManager=new SessionManager(context);
//                    Deletitem deletitem =new Deletitem();
//                    Integer id =events.getId();
//                    deletitem.setId(id);
//
//                    String access_token=sessionManager.getToken();
//
//                    deletitem(access_token,deletitem);




                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getQualifyDataList.get(position), position);

                        // showCustomDialog();
                    }

                }
            }
        });

        holder.gradu.setText(String.valueOf(getQualifyData.getGraduationYear()));
        holder.name.setText(getQualifyData.getName());
        holder.descript.setText(getQualifyData.getInstitute());
        holder.ly.setCardBackgroundColor(Color.parseColor(mColors[position % 5]));

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getQualifyDataList.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }

//    private void deletitem(String token,Deletitem deletitem) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
////            Log.d("Tag",""+addQualification.toString());
//
//            Call<AddExpById> addExpByIdCall = ApiHandler.getApiInterface().deletqualifyitem("Bearer " + token,deletitem);
//            addExpByIdCall.enqueue(new Callback<AddExpById>()
//            {
//
//                @Override
//                public void onResponse(Call<AddExpById> addExpByIdCall1, retrofit2.GetAllUsrSkillData<AddExpById> response) {
//
//                    try {
//                        if (response.isSuccessful())
//                        {
//
//                            int status = response.body().getMeta().getStatus();
//
//
//                            if (status == 200)
//                            {
//
//
//                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//
//
//
//                            }
//                            else if (status == 400)
//                            {
//                                //    Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
//                                Toast.makeText(context, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//
//
//                        }
//
//                        else
//                        {
////                            Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
//                            Toast.makeText(context, "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        try {
//                            Log.e("Tag", "error=" + e.toString());
//
//
//                        } catch (Resources.NotFoundException e1) {
//                            e1.printStackTrace();
//                        }
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<AddExpById> call, Throwable t) {
//                    try {
//                        Log.e("Tag", "error" + t.toString());
//                        dialog.dismiss();
//
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//            });
//
//
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }

}