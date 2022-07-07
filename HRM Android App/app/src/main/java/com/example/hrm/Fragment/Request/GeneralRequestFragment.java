
package com.example.hrm.Fragment.Request;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.hrm.Adapter.Request.RequestEquipmentsAdapter;
import com.example.hrm.Adapter.Request.RequestGeneralAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.AddGeneralReq.AddGeneralModel;
import com.example.hrm.Model.EquipementRequestModel.EquipmentListModel;
import com.example.hrm.Model.GeneralRequestModel.Equipment;
import com.example.hrm.Model.GeneralRequestModel.GeneralListModel;
import com.example.hrm.R;
import com.example.hrm.UI.RequestActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogReqequiptBinding;
import com.example.hrm.databinding.DialogReqgeneralBinding;
import com.example.hrm.databinding.FragmentEquipmentsBinding;
import com.example.hrm.databinding.FragmentGeneralRequestBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralRequestFragment extends Fragment {


    private RecyclerView.LayoutManager mLayoutManager;
    RequestGeneralAdapter requestGeneralAdapter;



    private FragmentGeneralRequestBinding binding;
    private DialogReqgeneralBinding dialogReqgeneralBinding;
    String token;
    String name,mssg;


    List<Equipment> equipment= new ArrayList<>();


    SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGeneralRequestBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.allgenralrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.allgenralrecycler.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        GetAllAttend(token);


        binding.btnaddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();

            }
        });
        return view;
    }
    private void GetAllAttend(final String access_token) {
        try {

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            Call<GeneralListModel> generalListModelCall = ApiHandler.getApiInterface().getAllGeneralList("Bearer " + access_token);
            generalListModelCall.enqueue(new Callback<GeneralListModel>() {
                @Override
                public void onResponse(Call<GeneralListModel> generalListModelCall1Call1, Response<GeneralListModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                equipment = response.body().getData().getEquipment();
                                if (equipment.size()==0)
                                {
                                    progressDialog.dismiss();

                                    binding.allgenralrecycler.setVisibility(View.GONE);
                                    binding.txtNo.setVisibility(View.VISIBLE);

                                }
                                else if (equipment!=null){

                                    requestGeneralAdapter = new RequestGeneralAdapter(getActivity(), equipment);

                                    binding.allgenralrecycler.setAdapter(requestGeneralAdapter);
                                    binding.allgenralrecycler.setVisibility(View.VISIBLE);
                                    binding.txtNo.setVisibility(View.GONE);


                                    progressDialog.dismiss();
                                }

                            }

                        } else {

                            binding.txtNo.setVisibility(View.VISIBLE);

                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            progressDialog.dismiss();



                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GeneralListModel> call, Throwable t) {
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


    private void showCustomDialog()
    {

        dialogReqgeneralBinding = DialogReqgeneralBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogReqgeneralBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;








        dialogReqgeneralBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(getActivity(), "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogReqgeneralBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                mssg=dialogReqgeneralBinding.txtDecrpt.getText().toString();
                name=dialogReqgeneralBinding.txtName.getText().toString();
                 AddReq(token);
                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void AddReq(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddGeneralModel> addGeneralModelCall = ApiHandler.getApiInterface().AddGeneralReq("Bearer " + token,ApiMap());

            addGeneralModelCall.enqueue(new Callback<AddGeneralModel>() {
                @Override
                public void onResponse(Call<AddGeneralModel> addGeneralModelCall1, Response<AddGeneralModel> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                GetAllAttend(token);
//                                Intent intent =new Intent(getActivity(), RequestActivity.class);
//                                startActivity(intent);

                            }
                            else if(status==400)
                            {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+b, Toast.LENGTH_SHORT).show();


                            }
                            else if (status==500)
                            {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),RequestActivity.class);
                                startActivity(intent);

                            }
                            else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

                            }
                        }

                        else
                        {
                            String msg = response.body().getMeta().getMessage();
                            Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }


                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AddGeneralModel> call, Throwable t)
                {
                    try {
                        Log.e("Tag", "error" + t.toString());

                        dialog.dismiss();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }

                }



            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private JsonObject ApiMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("itemName", name);
            jsonObj_.put("description", mssg);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return gsonObject;
    }



}