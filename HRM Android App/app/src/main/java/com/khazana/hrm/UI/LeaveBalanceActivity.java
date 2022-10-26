package com.khazana.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.library.CurveBottomBar;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khazana.hrm.Adapter.LeaveBalance.LeavebalanceAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.LeaveBalanceModel.Leavebalance;
import com.khazana.hrm.Model.LeaveBalanceModel.LeaveBalanceData;

import com.khazana.hrm.R;
import com.khazana.hrm.Utility.SessionManager;
import com.khazana.hrm.databinding.ActivityLeaveBalanceBinding;

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
//    PieChart pieChart;
    private PieChart pieChart;

    private CurveBottomBar cbb;
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.menu_me:
                Intent intent = new Intent(LeaveBalanceActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_more:
                //  item.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.grey_10));
                Intent intentt = new Intent(LeaveBalanceActivity.this, More.class);
                startActivity(intentt);
                finish();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        noleave = findViewById(R.id.txt_no);
///        pieChart = findViewById(R.id.piechart);
        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();
        loadPieChartData();

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
        binding.icMe.setOnClickListener(new View.OnClickListener() {
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
        binding.icFlthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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

        cbb = findViewById(R.id.nav_view);
        cbb.setBottomBarColor(getResources().getColor(R.color.colorApp));
        cbb.setCurveRadius(90);
        cbb.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setEntryLabelColor(R.color.colorAppDark);
        pieChart.setCenterText("Leaves Balance");
        pieChart.setCenterTextSize(15);
        pieChart.getDescription().setEnabled(false);

//        Legend l = pieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.10f, "Annual"));
        entries.add(new PieEntry(0.15f, "Casual"));
        entries.add(new PieEntry(0.10f, "Sick"));
        entries.add(new PieEntry(0.15f, "Marriage"));
        entries.add(new PieEntry(0.3f, "Bereavement"));
        entries.add(new PieEntry(0.3f, "Maternity"));
        entries.add(new PieEntry(0.25f, "Hajj"));
//        entries.add(new PieEntry(0.0f, "Compensatory Leaves"));
//        entries.add(new PieEntry(0.0f, "Unpaid Leaves"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Leaves Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(R.color.colorAppDark);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
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
       // pieChart.startAnimation();
    }


    private void GetleaveBalance(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(LeaveBalanceActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                //    pieChart.addPieSlice(new PieModel("All leaves", 8, Color.parseColor("#FFA726")));

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
                            else if (status == 401) {
                                Toast.makeText(LeaveBalanceActivity.this, "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(LeaveBalanceActivity.this, SignIn.class);
                                startActivity(intent);

                            }
                            else{
                                mRecyclerView.setVisibility(View.GONE);
                                noleave.setVisibility(View.VISIBLE);
                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(LeaveBalanceActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        } else {
                            noleave.setVisibility(View.VISIBLE);
                            sessionManager.logoutUser();
                            Intent intent = new Intent(LeaveBalanceActivity.this, SignIn.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            dialog.dismiss();

                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Leavebalance> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        dialog.dismiss();
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