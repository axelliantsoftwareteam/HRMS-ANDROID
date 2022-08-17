package com.example.hrm.Fragment.Payroll;

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

import com.example.hrm.Adapter.Payroll.HealthSlabsAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Payroll.HealthSlabs.AddHealthSlabs.AddHealthSlab;
import com.example.hrm.Model.Payroll.HealthSlabs.GetHealthSlab;
import com.example.hrm.Model.Payroll.HealthSlabs.GetHealthSlabData;
import com.example.hrm.UI.BasicSetupActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddhealthBinding;
import com.example.hrm.databinding.DialogHealthslabdetailsBinding;
import com.example.hrm.databinding.FragmentHealthSlabsBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HealthSlabsFragment extends Fragment {


    private FragmentHealthSlabsBinding binding;
    private DialogAddhealthBinding dialogAddhealthBinding;
    private DialogHealthslabdetailsBinding dialogHealthslabdetailsBinding;

    private RecyclerView.LayoutManager mLayoutManager;
    HealthSlabsAdapter healthSlabsAdapter;

    List<GetHealthSlabData> getHealthSlabDataList = new ArrayList<>();


    String stmini,stmax,stded,steff;
    SessionManager sessionManager;
    String token, sname,sdisply;
    Integer sidd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHealthSlabsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.healthrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.healthrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        gethealth(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AddHealthSlabs(token);

            }
        });

        return view;
    }
    private void gethealth(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetHealthSlab> getHealthSlabCall = ApiHandler.getApiInterface().gethealth("Bearer " + access_token);
            getHealthSlabCall.enqueue(new Callback<GetHealthSlab>() {
                @Override
                public void onResponse(Call<GetHealthSlab> getHealthSlabCall1, Response<GetHealthSlab> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getHealthSlabDataList = response.body().getData().getResponse();

                                if (getHealthSlabDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.healthrecycler.setVisibility(View.GONE);

                                } else if (getHealthSlabDataList != null) {

                                    healthSlabsAdapter = new HealthSlabsAdapter(getActivity(), getHealthSlabDataList);
                                    binding.healthrecycler.setAdapter(healthSlabsAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.healthrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    healthSlabsAdapter.setOnItemClickListener(new HealthSlabsAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetHealthSlabData obj, int position) {
                                            GetHealthSlabData getHealthSlabData = getHealthSlabDataList.get(position);
                                            Integer mini =getHealthSlabData.getMinimum();
                                            Integer max=getHealthSlabData.getMaximum();
                                            Integer dedamount=getHealthSlabData.getDeduction();
                                            String efform=getHealthSlabData.getEffectiveFrom();
                                            showDetails(efform,mini,max,dedamount);





                                            // Log.e("Tag", "work" + name.toString());


                                        }
                                    });

                                }

                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

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
                public void onFailure(Call<GetHealthSlab> call, Throwable t) {
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
    private void showDetails(String efform, Integer mini,Integer max,Integer ded)
    {

        dialogHealthslabdetailsBinding = DialogHealthslabdetailsBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogHealthslabdetailsBinding.getRoot());
        dialog.setCancelable(true);


//        String per= Double.toString(perct);
        dialogHealthslabdetailsBinding.txtefffrom.setText(efform);
        dialogHealthslabdetailsBinding.txtmax.setText(String.valueOf(max));
        dialogHealthslabdetailsBinding.txtmini.setText(String.valueOf(mini));
        dialogHealthslabdetailsBinding.taxded.setText(String.valueOf(ded));



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


//        dialogEditskillBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
////                sidd = id;
////                sname=dialogEditskillBinding.etname.getText().toString().trim();
////                sdisply=dialogEditskillBinding.etdescript.getText().toString().trim();
////                Log.e("value", "" + sdisply.toString());
////                ActionMembr(token);
//
//                T
//                dialog.dismiss();
//
//            }
//        });

//        dialogEditskillBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                //  AddReq(token);
//                dialog.dismiss();
//
//            }
//        });






        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void AddHealthSlabs(String token)
    {
        dialogAddhealthBinding = DialogAddhealthBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddhealthBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAddhealthBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                stmax=dialogAddhealthBinding.etmax.getText().toString();
                stmini=dialogAddhealthBinding.etmini.getText().toString();
                stded=dialogAddhealthBinding.etmini.getText().toString();
                steff=dialogAddhealthBinding.etmini.getText().toString();
                addhealth(token);
                dialog.dismiss();

            }
        });

        dialogAddhealthBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

    private void addhealth(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddHealthSlab> addHealthSlabCall = ApiHandler.getApiInterface().addHealth("Bearer " + token,Apimember());

            addHealthSlabCall.enqueue(new Callback<AddHealthSlab>() {
                @Override
                public void onResponse(Call<AddHealthSlab> addHealthSlabCall1, Response<AddHealthSlab> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(), BasicSetupActivity.class);
                                startActivity(intent);

                            }
                            else if(status==400)
                            {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),BasicSetupActivity.class);
                                startActivity(intent);

                            }
                            else if (status==500)
                            {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),BasicSetupActivity.class);
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
                public void onFailure(Call<AddHealthSlab> call, Throwable t)
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

    private JsonObject Apimember() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("maximum", stmax);
            jsonObj_.put("minimum", stmini);
            jsonObj_.put("deduction", stded);
            jsonObj_.put("effective_from", steff);


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