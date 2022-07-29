package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityEditprofBinding;
import com.example.hrm.databinding.ActivityMoreBinding;

public class EditprofActivity extends AppCompatActivity {


    private ActivityEditprofBinding binding;
    SessionManager sessionManage;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(EditprofActivity.this, More.class);
        startActivity(i);
        finish();

    }
}