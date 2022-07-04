package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.hrm.R;
import com.example.hrm.databinding.ActivityEditprofBinding;
import com.example.hrm.databinding.ActivityMoreBinding;

public class EditprofActivity extends AppCompatActivity {


    private ActivityEditprofBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


//        setContentView(R.layout.activity_editprof);








    }
}