package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrm.Adapter.LeaveBalance.LeavebalanceAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.LeaveBalanceModel.Leavebalance;
import com.example.hrm.Model.LeaveBalanceModel.Response;

import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LeaveBalanceActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    LeavebalanceAdapter leavebalanceAdapter;

    SessionManager sessionManager;
    String token;

    TextView noleave;

    List<Response> responseList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_leave_balance);

        noleave = findViewById(R.id.txt_no);

        mRecyclerView = findViewById(R.id.leavesbalnce_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(LeaveBalanceActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(LeaveBalanceActivity.this);
        token = sessionManager.getToken();

        GetleaveBalance(token);
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
                            if (status == 200)
                            {

                                responseList = response.body().getData().getResponse();
                                if (responseList.size()==0)
                                {
                                    dialog.dismiss();
                                    mRecyclerView.setVisibility(View.GONE);
                                }
                                else if (responseList!=null){

                                    leavebalanceAdapter = new LeavebalanceAdapter(LeaveBalanceActivity.this, responseList);
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