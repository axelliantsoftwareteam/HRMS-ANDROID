package com.khazana.hrm.Fragment.Request;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.khazana.hrm.Adapter.Request.RequestEquipmentsAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.AddEuipmentReq.AddEquipmentModel;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.EquipementRequestModel.Equipment;
import com.khazana.hrm.Model.EquipementRequestModel.EquipmentListModel;
import com.khazana.hrm.UI.RequestActivity;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khazana.hrm.databinding.DialogReqequiptBinding;
import com.khazana.hrm.databinding.FragmentEquipmentsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EquipmentsFragment extends Fragment {




    private RecyclerView.LayoutManager mLayoutManager;
    RequestEquipmentsAdapter requestEquipmentsAdapter;



    private FragmentEquipmentsBinding binding;
    private DialogReqequiptBinding dialogReqequiptBinding;
    DatePickerDialog picker;

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
        binding = FragmentEquipmentsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.allequiptrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.allequiptrecycler.setLayoutManager(mLayoutManager);

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

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<EquipmentListModel> equipmentListModelCall = ApiHandler.getApiInterface().getAllEquiptList("Bearer " + access_token);
            equipmentListModelCall.enqueue(new Callback<EquipmentListModel>() {
                @Override
                public void onResponse(Call<EquipmentListModel> equipmentListModelCall1, Response<EquipmentListModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                equipment = response.body().getData().getEquipment();
                                if (equipment.size()==0)
                                {
                                    dialog.dismiss();

                                    binding.allequiptrecycler.setVisibility(View.GONE);
                                    binding.txtNo.setVisibility(View.VISIBLE);

                                }
                                else if (equipment!=null){

                                    requestEquipmentsAdapter = new RequestEquipmentsAdapter(getActivity(), equipment);

                                    binding.allequiptrecycler.setAdapter(requestEquipmentsAdapter);
                                    binding.allequiptrecycler.setVisibility(View.VISIBLE);
                                    binding.txtNo.setVisibility(View.GONE);


                                    dialog.dismiss();
                                }

                            }
                            else if (status==400)
                            {
                                binding.txtNo.setVisibility(View.VISIBLE);
                                binding.allequiptrecycler.setVisibility(View.GONE);
                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "Your session will be expired please login again!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                            else if (status==401)
                            {
                                binding.txtNo.setVisibility(View.VISIBLE);
                                binding.allequiptrecycler.setVisibility(View.GONE);
                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }


                        } else
                        {
                            Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                            sessionManager.logoutUser();
                            Intent intent = new Intent(getActivity(), SignIn.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), "Internal server error! ", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();



                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<EquipmentListModel> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error! ", Toast.LENGTH_SHORT).show();
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


    private void showCustomDialog()
    {



        dialogReqequiptBinding = DialogReqequiptBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogReqequiptBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;








        dialogReqequiptBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(getActivity(), "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogReqequiptBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(dialogReqequiptBinding.txtName.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogReqequiptBinding.txtName.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    name=dialogReqequiptBinding.txtName.getText().toString();


                }
                if(dialogReqequiptBinding.txtDecrpt.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogReqequiptBinding.txtDecrpt.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mssg=dialogReqequiptBinding.txtDecrpt.getText().toString();
                    AddReq(token);
                    dialog.dismiss();
                }



            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void AddReq(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddEquipmentModel> addEquipmentModelCall = ApiHandler.getApiInterface().AddEquiptReq("Bearer " + token,ApiMap());

            addEquipmentModelCall.enqueue(new Callback<AddEquipmentModel>() {
                @Override
                public void onResponse(Call<AddEquipmentModel> addEquipmentModelCall1, Response<AddEquipmentModel> response ) {

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
                            else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            }
                            else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

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
                public void onFailure(Call<AddEquipmentModel> call, Throwable t)
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