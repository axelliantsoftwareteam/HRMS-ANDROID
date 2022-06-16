package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.hrm.databinding.ActivityTasksBinding;

public class TasksActivity extends AppCompatActivity {

    private ActivityTasksBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_tasks);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        transparentStatusAndNavigation();



        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TasksActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TasksActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TasksActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });


        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TasksActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });



    }






    //navigation transparent
    //start

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
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS ,false);
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
    //navigation transparent
    //end


    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(TasksActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }

}