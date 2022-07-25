package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hrm.Adapter.Evaluation.EvalutionAdapter;
import com.example.hrm.Adapter.StaticData.BuildingAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Building.GetBuilding;
import com.example.hrm.Model.Building.GetBuildingData;
import com.example.hrm.Model.Evalution.GetEvalution;
import com.example.hrm.Model.Evalution.GetEvalutionData;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityEvalutionBinding;
import com.example.hrm.databinding.ActivityTasksBinding;
import com.example.hrm.databinding.DialogEditbuildBinding;
import com.example.hrm.databinding.DialogEditevalutionBinding;
import com.example.hrm.databinding.DialogSavedBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvalutionActivity extends AppCompatActivity {


    private ActivityEvalutionBinding binding;
    private DialogEditevalutionBinding dialogEditevalutionBinding;
    private DialogSavedBinding dialogSavedBinding;
    String token;
    SessionManager sessionManager;


    private RecyclerView.LayoutManager mLayoutManager;
    EvalutionAdapter evalutionAdapter;

    List<GetEvalutionData> getEvalutionDataList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // setContentView(R.layout.activity_tasks);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        isNetworkConnectionAvailable();

        sessionManager = new SessionManager(EvalutionActivity.this);
        token = sessionManager.getToken();
        getEvaluation(token);


        binding.evalrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(EvalutionActivity.this);
        binding.evalrecycler.setLayoutManager(mLayoutManager);

        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvalutionActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EvalutionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvalutionActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EvalutionActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    private void getEvaluation(final String access_token)
    {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(EvalutionActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEvalution> getEvalutionCall = ApiHandler.getApiInterface().getEvalution("Bearer " + access_token);
            getEvalutionCall.enqueue(new Callback<GetEvalution>() {
                @Override
                public void onResponse(Call<GetEvalution> getEvalutionCall1, Response<GetEvalution> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getEvalutionDataList = response.body().getData().getResponse();
                                Log.e("Tag", "response" + getEvalutionDataList.toString());

                                if (getEvalutionDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.evalrecycler.setVisibility(View.GONE);

                                } else if (getEvalutionDataList != null) {

                                    evalutionAdapter = new EvalutionAdapter(EvalutionActivity.this, getEvalutionDataList);
                                    binding.evalrecycler.setAdapter(evalutionAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.evalrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    evalutionAdapter.setOnItemClickListener(new EvalutionAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetEvalutionData obj, int position) {
                                            GetEvalutionData getEvalutionData = getEvalutionDataList.get(position);
//                                            String name =getHolidayData.getType();
//                                            String val=getHolidayData.getStartDate();



//
//                                             Log.e("Tag", "work" + name.toString());

                                            showEditDialog();
                                        }
                                    });



                                }

                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(EvalutionActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<GetEvalution> call, Throwable t) {
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

    private void showEditDialog()
    {

        dialogEditevalutionBinding = DialogEditevalutionBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(EvalutionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditevalutionBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditevalutionBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);

                ShowSaveDialog();
                dialog.dismiss();

            }
        });

        dialogEditevalutionBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });






        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void ShowSaveDialog()
    {
        dialogSavedBinding = DialogSavedBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(EvalutionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogSavedBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;






        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }


    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(EvalutionActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }
    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}