
package com.example.hrm.Fragment.BasicSetup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hrm.R;
import com.example.hrm.databinding.FragmentStaticDataBinding;
import com.example.hrm.databinding.FragmentSystemHolidaysBinding;


public class SystemHolidaysFragment extends Fragment {




    private FragmentSystemHolidaysBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system_holidays, container, false);
    }
}