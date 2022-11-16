package com.khazana.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.khazana.hrm.R;
import com.khazana.hrm.Utility.SessionManager;
import com.khazana.hrm.Utility.Utility;
import com.khazana.hrm.databinding.ActivitySigninBinding;
import com.khazana.hrm.databinding.ActivitySplashScreenBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 5000;

    private ActivitySplashScreenBinding binding;

    SessionManager sessionManager;
    String expdate, exprietime, currentdate=null;

    private int animationCounter = 1;
    private Handler imageSwitcherHandler;

    // Declare globally
    private int position = -1;

    private int imageArray[] = {
            R.drawable.sp_bg,
            R.drawable.logo_bgonen,
            R.drawable.logo_bgtwo,
            R.drawable.lo_bg
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        setContentView(R.layout.activity_splash_screen);
        transparentStatusAndNavigation();
        isNetworkConnectionAvailable();


//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_anim);
//        binding.spLogo.startAnimation(animation);


        AnimationDrawable animationDrawable = (AnimationDrawable) binding.spLogo.getDrawable();
        animationDrawable.setOneShot(true);
        animationDrawable.start();

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, android.R.anim.fade_in);

        binding.spLogo.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeOut = AnimationUtils.loadAnimation(SplashScreen.this, android.R.anim.fade_out);
                binding.spLogo.startAnimation(fadeOut);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
/**
 * This timer will call each of the seconds.
 */
//        Timer mTimer = new Timer();
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // As timer is not a Main/UI thread need to do all UI task on runOnUiThread
//                SplashScreen.this.runOnUiThread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        // increase your position so new image will show
//                        position++;
//                        // check whether position increased to length then set it to 0
//                        // so it will show images in circuler
//                        if (position >= imageArray.length) {
//                            position = 0;
//                            // Set Image
//
//                        }
//                        binding.spLogo.setImageResource(imageArray[position]);
//
//                    }
//                });
//
//            }
//        }, 0, 1500);


//        imageSwitcherHandler = new Handler(Looper.getMainLooper());
//        imageSwitcherHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                switch (animationCounter++) {
//                    case 1:
//                        binding.spLogo.setImageResource(R.drawable.img_one);
//                        break;
//                    case 2:
//                        binding.spLogo.setImageResource(R.drawable.imge_two);
//                        break;
//                    case 3:
//                        binding.spLogo.setImageResource(R.drawable.khaz_logo);
//                        break;
//                }
//                animationCounter %= 4;
//                if(animationCounter == 0 ) animationCounter = 1;
//
//                imageSwitcherHandler.postDelayed(this, 3000);
//            }
//        });

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

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date currenTimeZone = (Date) calendar.getTime();
//        currentdate = sdf.format(currenTimeZone);
//
//        Log.d("currentdate", "" + currentdate.toString());


//        if (exprietime == null)
//        {

            sessionManager = new SessionManager(SplashScreen.this);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sessionManager.checkLogin())
                    {
                        if (Utility.checkInternetConnection(SplashScreen.this)) {
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Utility.InternetDailog(SplashScreen.this);
                        }

                    } else {
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
                changeStatusBarTextColor(window, true);
            }
        //    Toast.makeText(SplashScreen.this, ""+exprietime, Toast.LENGTH_SHORT).show();


//        } else {


//        }


//
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = df.format(Calendar.getInstance().getTime());
//


    }
    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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

    public void changeStatusBarTextColor(Window window, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags = 0;
            if (isBlack) {
                //Change the text color to dark black
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
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