package com.example.hrm.Fragment.BasicSetup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hrm.Adapter.StaticData.BuildingAdapter;
import com.example.hrm.Adapter.StaticData.SkillsAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Building.GetBuilding;
import com.example.hrm.Model.Building.GetBuildingData;
import com.example.hrm.Model.Skills.GetSkills;
import com.example.hrm.Model.Skills.GetSkillsData;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddbuildBinding;
import com.example.hrm.databinding.DialogAddskillBinding;
import com.example.hrm.databinding.DialogEditbuildBinding;
import com.example.hrm.databinding.DialogEditskillBinding;
import com.example.hrm.databinding.FragmentBuildingsBinding;
import com.example.hrm.databinding.FragmentSkillsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillsFragment extends Fragment {





    private FragmentSkillsBinding binding;
    private DialogAddskillBinding dialogAddskillBinding;
    private DialogEditskillBinding dialogEditskillBinding;



    private RecyclerView.LayoutManager mLayoutManager;
    SkillsAdapter skillsAdapter;

    List<GetSkillsData> getSkillsDataList = new ArrayList<>();



    SessionManager sessionManager;
    String token, sname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSkillsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.skillsrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.skillsrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getskill(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Addskill();

            }
        });

        return view;

    }

    private void getskill(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetSkills> getSkillsCall = ApiHandler.getApiInterface().getSkill("Bearer " + access_token);
            getSkillsCall.enqueue(new Callback<GetSkills>() {
                @Override
                public void onResponse(Call<GetSkills> getSkillsCall1, Response<GetSkills> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getSkillsDataList = response.body().getData().getResponse();

                                if (getSkillsDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.skillsrecycler.setVisibility(View.GONE);

                                } else if (getSkillsDataList != null) {

                                    skillsAdapter = new SkillsAdapter(getActivity(), getSkillsDataList);
                                    binding.skillsrecycler.setAdapter(skillsAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.skillsrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    skillsAdapter.setOnItemClickListener(new SkillsAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetSkillsData obj, int position) {
                                            GetSkillsData getSkillsData = getSkillsDataList.get(position);
//                                            String name =getHolidayData.getType();
//                                            String val=getHolidayData.getStartDate();




                                            // Log.e("Tag", "work" + name.toString());

                                            showEditDialog();
                                        }
                                    });

                                }

                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<GetSkills> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }


            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }
    private void Addskill()
    {
        dialogAddskillBinding = DialogAddskillBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddskillBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAddskillBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogAddskillBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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
    private void showEditDialog()
    {

        dialogEditskillBinding = DialogEditskillBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditskillBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditskillBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogEditskillBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

}