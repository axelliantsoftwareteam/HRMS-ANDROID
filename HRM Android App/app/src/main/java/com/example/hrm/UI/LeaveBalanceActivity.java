package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrm.Adapter.LeaveBalance.LeavebalanceAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.LeaveBalanceModel.Leavebalance;
import com.example.hrm.Model.LeaveBalanceModel.LeaveBalanceData;

import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityLeaveBalanceBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LeaveBalanceActivity extends AppCompatActivity {

    private ActivityLeaveBalanceBinding binding;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    LeavebalanceAdapter leavebalanceAdapter;

    SessionManager sessionManager;
    String token;

    TextView noleave;

    List<LeaveBalanceData> leaveBalanceDataList = new ArrayList<>();


    TextView tvR, tvPython, tvCPP, tvJava;
    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        noleave = findViewById(R.id.txt_no);
        pieChart = findViewById(R.id.piechart);

        mRecyclerView = findViewById(R.id.leavesbalnce_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(LeaveBalanceActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(LeaveBalanceActivity.this);
        token = sessionManager.getToken();

        GetleaveBalance(token);


        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaveBalanceActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LeaveBalanceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaveBalanceActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeaveBalanceActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setData(List<LeaveBalanceData> leavebalance) {

        String[] items = new String[leavebalance.size()];


//        // Set the data and color to the pie chart
//        pieChart.addPieSlice(new PieModel("All leaves", Integer.parseInt("40"), Color.parseColor("#FFA726")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "Python", Integer.parseInt("12"), Color.parseColor("#66BB6A")));
//        pieChart.addPieSlice(
//                new PieModel(
//                        "C++", Integer.parseInt("12"), Color.parseColor("#EF5350")));
//        pieChart.addPieSlice(
//                new PieModel("Java", Integer.parseInt("12"), Color.parseColor("#29B6F6")));

        // To animate the pie chart
        pieChart.startAnimation();
    }


    private void GetleaveBalance(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(LeaveBalanceActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<Leavebalance> leaves = ApiHandler.getApiInterface().getleavebalance("Bearer " + access_token);
            leaves.enqueue(new Callback<Leavebalance>() {
                @Override
                public void onResponse(Call<Leavebalance> leavebalanceCall, retrofit2.Response<Leavebalance> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                leaveBalanceDataList = response.body().getData().getResponse();
//                                setData(leaveBalanceDataList);
                                //Traversing through the whole list to get all the names
                                for (int i = 0; i < leaveBalanceDataList.size(); i++) {
                                    //Storing names to string array

                                    LeaveBalanceData leaveBalanceData = new LeaveBalanceData();

                                    leaveBalanceData=leaveBalanceDataList.get(i);
                                    leaveBalanceData.setName(leaveBalanceDataList.get(i).getName());
                                    leaveBalanceData.setUsed(leaveBalanceDataList.get(i).getTotal());


//                                    String name = leaveBalanceDataList.get(i).getName();
//                                    String used = leaveBalanceDataList.get(i).getTotal();
                                    String value = leaveBalanceData.getUsed();


                                   // pieChart.addPieSlice(new PieModel("hh",Integer.parseInt(value)), Color.parseColor("#FFA726"));
                                    pieChart.addPieSlice(new PieModel("All leaves", 8, Color.parseColor("#FFA726")));

                                }

                                if (leaveBalanceDataList.size() == 0)
                                {
                                    dialog.dismiss();
                                    mRecyclerView.setVisibility(View.GONE);
                                } else if (leaveBalanceDataList != null) {

                                    leavebalanceAdapter = new LeavebalanceAdapter(LeaveBalanceActivity.this, leaveBalanceDataList);
                                    mRecyclerView.setAdapter(leavebalanceAdapter);


                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    noleave.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }

                            }

                        } else {
                            noleave.setVisibility(View.VISIBLE);
                            Toast.makeText(LeaveBalanceActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());


                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Leavebalance> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }


            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(LeaveBalanceActivity.this, More.class);
        startActivity(i);
        finish();

    }

}