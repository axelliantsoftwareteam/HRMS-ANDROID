 package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.hrm.R;

 public class OrganoActivity extends AppCompatActivity {


     WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organo);
        isNetworkConnectionAvailable();
        webView = findViewById(R.id.webview);

        OrganizationChart organizationChart = OrganizationChart.getInstance(this);
        organizationChart.addChildToParent("Jacob","<div style=\\\"color:red; font-style:italic\\\">President</div>","Mike");

        organizationChart.addChildToParent("Jacob1","Mike");
        organizationChart.addChildToParent("Jacob2","Mike");
        organizationChart.addChildToParent("Calson1","Jacob1");
        organizationChart.addChildToParent("Calson2","Jacob1");
        organizationChart.addChildToParent("Calson3","Jacob1");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(organizationChart.getChart(), "text/html", "UTF-8");


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