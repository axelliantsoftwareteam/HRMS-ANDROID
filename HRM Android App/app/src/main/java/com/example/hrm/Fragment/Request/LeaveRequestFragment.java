package com.example.hrm.Fragment.Request;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;
import com.example.hrm.Adapter.Request.RequestLeavesAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.AddLeaveRequestModel.AddLeaveReq;
import com.example.hrm.Model.LeavesModel.AllLeavesModel;
import com.example.hrm.Model.LeavesModel.Leaves;
import com.example.hrm.Model.SpinerModel.SpinerLeaves;
import com.example.hrm.Model.SpinerModel.SpinerResponse;
import com.example.hrm.UI.RequestActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAttendBinding;
import com.example.hrm.databinding.FragmentRequestBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.TextView;
import android.widget.Toast;

import com.example.hrm.Adapter.Leaves.AllLeavesAdapter;
import com.example.hrm.Adapter.Request.RequestLeavesAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.LeavesModel.AllLeavesModel;
import com.example.hrm.Model.LeavesModel.Leaves;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;

import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveRequestFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    RequestLeavesAdapter requestLeavesAdapter;


    private FragmentRequestBinding binding;
    private DialogAttendBinding dialogAttendBinding;
    DatePickerDialog picker;

    SessionManager sessionManager;

    List<SpinerResponse> spinerResponses= new ArrayList<>();

    String token;
    String msg;
    String satttype,sdate,sstartdate;

    List<Leaves> leaves= new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
 
        // Inflate the layout for this fragment
        binding = FragmentRequestBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        binding.allreqleavesRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.allreqleavesRecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

 
        binding.btnaddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();

            }
        });

  
   
        GetAllLeaves(token);

        return view;

    }
 


    private void showCustomDialog()
    {



        dialogAttendBinding = DialogAttendBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAttendBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getspiner(token);



        dialogAttendBinding.etStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogAttendBinding.etStartdate.setText(dateString);


                                sstartdate = dialogAttendBinding.etStartdate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });

        dialogAttendBinding.etEnddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogAttendBinding.etEnddate.setText(dateString);
                                sdate = dialogAttendBinding.etEnddate.getText().toString().trim();
                            }
                        }, year, month, day);
                picker.show();

            }
        });

        dialogAttendBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(getActivity(), "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogAttendBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                msg=dialogAttendBinding.msg.getText().toString();
                AddReq(token);
                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void getspiner(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Call<SpinerLeaves> spinerLeavesCall = ApiHandler.getApiInterface().getspiner("Bearer " + access_token);
            spinerLeavesCall.enqueue(new Callback<SpinerLeaves>() {
                @Override
                public void onResponse(Call<SpinerLeaves> spinerLeavesCall1, Response<SpinerLeaves> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                spinerResponses = response.body().getData().getResponse();
                                // setspiner(spinerResponses);
                                //String array to store all the book names
                                String[] items = new String[spinerResponses.size()];

                                //Traversing through the whole list to get all the names
                                for(int i=0; i<spinerResponses.size(); i++){
                                    //Storing names to string array
                                    items[i] = spinerResponses.get(i).getValue();
                                }

                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                                //setting adapter to spinner
                                dialogAttendBinding.spinner.setAdapter(adapter);
                                dialog.dismiss();
                                dialogAttendBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        satttype = parent.getItemAtPosition(position).toString();

//                                        Toast.makeText(parent.getContext(),
//                                                "" +stype,
//                                                Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                        // sometimes you need nothing here
                                    }
                                });
                                //   Toast.makeText(getActivity(), ""+spinerResponses, Toast.LENGTH_SHORT).show();
                            }


                        } else {

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
                public void onFailure(Call<SpinerLeaves> call, Throwable t) {
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


  
   
    private void GetAllLeaves(final String access_token) {
        try {

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            Call<AllLeavesModel> allleaves = ApiHandler.getApiInterface().getAllleaves("Bearer " + access_token);
            allleaves.enqueue(new Callback<AllLeavesModel>() {
                @Override
                public void onResponse(Call<AllLeavesModel> allLeavesModelCall, Response<AllLeavesModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                leaves = response.body().getData().getLeaves();
                                if (leaves.size()==0)
                                {
                                    progressDialog.dismiss();
 
                                    binding.allreqleavesRecycler.setVisibility(View.GONE);

                                }
                                else if (leaves!=null){

                                    requestLeavesAdapter = new RequestLeavesAdapter(getActivity(), leaves);
 
                                    binding.allreqleavesRecycler.setAdapter(requestLeavesAdapter);
                                    binding.allreqleavesRecycler.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<AllLeavesModel> call, Throwable t) {
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
 

    private void AddReq(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddLeaveReq> addLeaveReqCall = ApiHandler.getApiInterface().AddRequest("Bearer " + token,ApiMap());

            addLeaveReqCall.enqueue(new Callback<AddLeaveReq>() {
                @Override
                public void onResponse(Call<AddLeaveReq> addLeaveReqCall1Call, Response<AddLeaveReq> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(), RequestActivity.class);
                                startActivity(intent);

                            }
                            else if(status==400)
                            {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),RequestActivity.class);
                                startActivity(intent);

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
                public void onFailure(Call<AddLeaveReq> call, Throwable t)
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
            jsonObj_.put("type", satttype);
            jsonObj_.put("date", sstartdate);
            jsonObj_.put("end_date", sdate);
            jsonObj_.put("comment", msg);


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

  
   
//    @Override
//    public void onResume(){
//        GetAllLeaves(token);
//        super.onResume();
//
//    }

}