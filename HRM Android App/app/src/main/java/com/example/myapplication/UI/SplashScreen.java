package com.example.myapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Utility.SessionManager;
import com.example.myapplication.Utility.Utility;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 3000;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_splash_screen);
        isNetworkConnectionAvailable();
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        android.net.NetworkInfo wifi = cm
//                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        android.net.NetworkInfo datac = cm
//                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if ((wifi != null & datac != null)
//                && (wifi.isConnected() | datac.isConnected())) {
//            //connection is avlilable
//        }else{
//            //no connection
//            Toast toast = Toast.makeText(SplashScreen.this, "No Internet Connection",
//                    Toast.LENGTH_LONG);
//            toast.show();
//        }
        sessionManager = new SessionManager(SplashScreen.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.checkLogin()) {
                    if (Utility.checkInternetConnection(SplashScreen.this)) {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Utility.InternetDailog(SplashScreen.this);
                    }

                }
                else
                {
                    if (Utility.checkInternetConnection(SplashScreen.this)) {
                        Intent intent = new Intent(SplashScreen.this, AppIntro.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Utility.InternetDailog(SplashScreen.this);
                    }

                }
            }
        }, SPLASH_TIMEOUT);
      //  transparentStatusAndNavigation();


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            // closeKeyBoard();
            changeStatusBarTextColor(window,true);
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
    public void changeStatusBarTextColor(Window window, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags = 0;
            if (isBlack) {
                //Change the text color to dark black
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            else {
                //Change text color to light
                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decor.setSystemUiVisibility(flags);
        }
    }
//    private void transparentStatusAndNavigation() {
//        //make full transparent statusBar
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            );
//        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//        }
//    }
//
//    private void setWindowFlag(final int bits, boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }


}