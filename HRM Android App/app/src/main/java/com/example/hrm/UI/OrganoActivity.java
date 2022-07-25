package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Model.Organo.OrganoData;
import com.example.hrm.Model.Organo.OrganoModel;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganoActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String token;
    OrganizationChart organizationChart = OrganizationChart.getInstance(OrganoActivity.this);

    List<OrganoData> organoDataList = new ArrayList<>();
    OrganoData organoData = new OrganoData();
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_organo);
        isNetworkConnectionAvailable();
        webView = findViewById(R.id.webview);
        sessionManager = new SessionManager(OrganoActivity.this);
        token = sessionManager.getToken();
        getmember(token);


    }

    public void getmember(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(OrganoActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<OrganoModel> organoModelCall = ApiHandler.getApiInterface().getorgano("Bearer " + access_token);
            Log.e("Tag", "response" + organoModelCall.toString());

            organoModelCall.enqueue(new Callback<OrganoModel>() {
                @Override
                public void onResponse(Call<OrganoModel> organoModelCall1, Response<OrganoModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                organoDataList = response.body().getData().getResponse();

                                for (int i = 0; i < organoDataList.size(); i++) {
                                    //Storing names to string array
                                    organoData = organoDataList.get(i);

                                    String id = organoData.getId();
                                    String pid = organoData.getPid();



                                    if (pid.equals("9PID") && id.equals("0"))
                                    {
                                        String name = organoData.getName();
                                        organizationChart.addChildToParent(id,name);
                                        webView.getSettings().setJavaScriptEnabled(true);
                                        webView.loadData(organizationChart.getChart(), "text/html", "UTF-8");


                                    }
//                                    String name = organoData.getName();
//                                    organizationChart.addChildToParent(id,name);
//                                    webView.getSettings().setJavaScriptEnabled(true);
//                                    webView.loadData(organizationChart.getChart(), "text/html", "UTF-8");





                                }


                                Log.e("Tag", "respone" + organoDataList.toString());

                                dialog.dismiss();
                            }


                        } else {

                            Toast.makeText(OrganoActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {

                            Log.e("Tag", "error=" + e.toString());
                            dialog.dismiss();

                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                            dialog.dismiss();
                        }

                    }
                }

                @Override
                public void onFailure(Call<OrganoModel> call, Throwable t) {
                    try {

                        Log.e("Tag", "error" + t.toString());
                        dialog.dismiss();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                }


            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(OrganoActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }

}