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
import com.example.hrm.Adapter.StaticData.HolidayAdapter;
import com.example.hrm.Adapter.StaticData.StaticDataAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Building.GetBuilding;
import com.example.hrm.Model.Building.GetBuildingData;
import com.example.hrm.Model.HolidayModel.GetHolidayData;
import com.example.hrm.Model.HolidayModel.GetHolidayModel;
import com.example.hrm.Model.StaticDataModel.GetDataMember.GetMemberList;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddbuildBinding;
import com.example.hrm.databinding.DialogAdddispmemberstdtBinding;
import com.example.hrm.databinding.DialogEditbuildBinding;
import com.example.hrm.databinding.DialogEditholidayBinding;
import com.example.hrm.databinding.FragmentBuildingsBinding;
import com.example.hrm.databinding.FragmentStaticDataBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuildingsFragment extends Fragment {



    private FragmentBuildingsBinding binding;
    private DialogAddbuildBinding dialogAddbuildBinding;
    private DialogEditbuildBinding dialogEditbuildBinding;




    private RecyclerView.LayoutManager mLayoutManager;
    BuildingAdapter buildingAdapter;

    SessionManager sessionManager;
    String token, sname;

    List<GetBuildingData> getBuildingDataList = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBuildingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.buildrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.buildrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getbuild(token);

        binding.btnaddstdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Addbuild();

            }
        });


        return view;
    }

    private void getbuild(final String access_token)
    {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetBuilding> getBuildingCall = ApiHandler.getApiInterface().getBuilding("Bearer " + access_token);
            getBuildingCall.enqueue(new Callback<GetBuilding>() {
                @Override
                public void onResponse(Call<GetBuilding> getBuildingCall1, Response<GetBuilding> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getBuildingDataList = response.body().getData().getResponse();

                                if (getBuildingDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.buildrecycler.setVisibility(View.GONE);

                                } else if (getBuildingDataList != null) {

                                    buildingAdapter = new BuildingAdapter(getActivity(), getBuildingDataList);
                                    binding.buildrecycler.setAdapter(buildingAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.buildrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    buildingAdapter.setOnItemClickListener(new BuildingAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetBuildingData obj, int position) {
                                            GetBuildingData getHolidayData = getBuildingDataList.get(position);
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
                public void onFailure(Call<GetBuilding> call, Throwable t) {
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

    private void Addbuild()
    {
        dialogAddbuildBinding = DialogAddbuildBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddbuildBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAddbuildBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogAddbuildBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

        dialogEditbuildBinding = DialogEditbuildBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditbuildBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditbuildBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogEditbuildBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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