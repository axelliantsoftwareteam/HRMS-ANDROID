package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.hrm.Adapter.Evaluation.EvalutionAdapter;
import com.example.hrm.Fragment.Evaluation.EvaluationFragment;
import com.example.hrm.Model.Evalution.GetEvaluationData;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityEvalutionBinding;
import com.example.hrm.databinding.DialogEditevalutionBinding;
import com.example.hrm.databinding.DialogSavedBinding;

import java.util.ArrayList;
import java.util.List;

public class EvalutionActivity extends AppCompatActivity {


    private ActivityEvalutionBinding binding;
    private DialogEditevalutionBinding dialogEditevalutionBinding;
    private DialogSavedBinding dialogSavedBinding;
    String token;
    SessionManager sessionManager;


    private RecyclerView.LayoutManager mLayoutManager;
    EvalutionAdapter evalutionAdapter;

    List<GetEvaluationData> getEvalutionDataList = new ArrayList<>();




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

//        sessionManager = new SessionManager(EvalutionActivity.this);
//        token = sessionManager.getToken();
    //    getEvaluation(token);


//        binding.evalrecycler.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(EvalutionActivity.this);
//        binding.evalrecycler.setLayoutManager(mLayoutManager);
//


        EvaluationFragment evaluationFragment =new EvaluationFragment();
        moveToFragment(evaluationFragment);





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
//
//    private void getEvaluation(final String access_token) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(EvalutionActivity.this);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//            Call<GetEvalution> getEvalutionCall = ApiHandler.getApiInterface().getEvalution("Bearer " + access_token);
//            getEvalutionCall.enqueue(new Callback<GetEvalution>() {
//                @Override
//                public void onResponse(Call<GetEvalution> getEvalutionCall1, Response<GetEvalution> response) {
//
//                    try {
//                        if (response.isSuccessful()) {
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200) {
//
//                                getEvalutionDataList = response.body().getData().getResponse();
//
//
//                                if (attendApprovalList.size() == 0) {
//                                    dialog.dismiss();
//                                    binding.genapprrecycler.setVisibility(View.GONE);
//                                } else if (attendApprovalList != null) {
//
//                                    approvalAttendanceAdapter = new ApprovalAttendanceAdapter(EvalutionActivity.thi, attendApprovalList);
//                                    binding.genapprrecycler.setAdapter(approvalAttendanceAdapter);
//                                    binding.genapprrecycler.setVisibility(View.VISIBLE);
//                                    binding.txtno.setVisibility(View.GONE);
//                                    dialog.dismiss();
//
//                                    approvalAttendanceAdapter.setOnItemClickListener(new ApprovalAttendanceAdapter.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(View view, AttendApproval obj, int position) {
//                                            AttendApproval attendApproval1 = new AttendApproval();
//                                            WorkFlow workFlow = new WorkFlow();
//                                            attendApproval1 = attendApprovalList.get(position);
//                                            //  Log.e("Tag", "work" + response1.toString());
//
//                                            workFlowList = attendApproval1.getWorkFlow();
//
//
//
//                                            workFlow = workFlowList.get(0);
//
//                                            showCustomDialog(workFlow);
//
//                                        }
//                                    });
//                                }
//
//                            }
//
//                        } else {
//                            binding.txtno.setVisibility(View.VISIBLE);
//                            Toast.makeText(EvalutionActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
//                public void onFailure(Call<GetEvalution> call, Throwable t) {
//                    try {
//                        Log.e("Tag", "error" + t.toString());
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
//    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

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