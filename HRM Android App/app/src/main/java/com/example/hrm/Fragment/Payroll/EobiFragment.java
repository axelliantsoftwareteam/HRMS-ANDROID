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

import com.example.hrm.Adapter.Payroll.EobiSlabsAdapter;
import com.example.hrm.Adapter.Payroll.HealthSlabsAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Payroll.EobiSlab.AddEobiSlabs.AddEobiSlab;
import com.example.hrm.Model.Payroll.EobiSlab.GetEobiSlab;
import com.example.hrm.Model.Payroll.EobiSlab.GetEobiSlabData;
import com.example.hrm.Model.Payroll.HealthSlabs.AddHealthSlabs.AddHealthSlab;
import com.example.hrm.Model.Payroll.HealthSlabs.GetHealthSlab;
import com.example.hrm.Model.Payroll.HealthSlabs.GetHealthSlabData;
import com.example.hrm.R;
import com.example.hrm.UI.BasicSetupActivity;
import com.example.hrm.UI.PayrollActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddeobislabBinding;
import com.example.hrm.databinding.DialogAddhealthBinding;
import com.example.hrm.databinding.DialogEobislabdetailsBinding;
import com.example.hrm.databinding.DialogHealthslabdetailsBinding;
import com.example.hrm.databinding.FragmentEobiBinding;
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


public class EobiFragment extends Fragment {


    private FragmentEobiBinding binding;
    private DialogAddeobislabBinding dialogAddeobislabBinding;
    private DialogEobislabdetailsBinding dialogEobislabdetailsBinding;


    private RecyclerView.LayoutManager mLayoutManager;
    EobiSlabsAdapter eobiSlabsAdapter;

    List<GetEobiSlabData> getEobiSlabDataList = new ArrayList<>();


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
        binding = FragmentEobiBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.eobirecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.eobirecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        geteobi(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AddEobiSlabs(token);
            }
        });

        return view;
    }

    private void geteobi(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEobiSlab> getEobiSlabCall = ApiHandler.getApiInterface().geteobi("Bearer " + access_token);
            getEobiSlabCall.enqueue(new Callback<GetEobiSlab>() {
                @Override
                public void onResponse(Call<GetEobiSlab> getEobiSlabCall1, Response<GetEobiSlab> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getEobiSlabDataList = response.body().getData().getResponse();

                                if (getEobiSlabDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.eobirecycler.setVisibility(View.GONE);

                                } else if (getEobiSlabDataList != null) {

                                    eobiSlabsAdapter = new EobiSlabsAdapter(getActivity(), getEobiSlabDataList);
                                    binding.eobirecycler.setAdapter(eobiSlabsAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.eobirecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    eobiSlabsAdapter.setOnItemClickListener(new EobiSlabsAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetEobiSlabData obj, int position) {
                                            GetEobiSlabData getEobiSlabData = getEobiSlabDataList.get(position);
                                            Integer mini =getEobiSlabData.getMinimumAge();
                                            Integer max=getEobiSlabData.getMaximumAge();
                                            String efform=getEobiSlabData.getEffectiveFrom();
                                            Integer dedamount=getEobiSlabData.getDeduction();
                                            showDetails(efform,mini,max,dedamount);


//                                            showEditDialog(name,val,sid);





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
                public void onFailure(Call<GetEobiSlab> call, Throwable t) {
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

        dialogEobislabdetailsBinding = DialogEobislabdetailsBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEobislabdetailsBinding.getRoot());
        dialog.setCancelable(true);


//        String per= Double.toString(perct);
        dialogEobislabdetailsBinding.txtefffrom.setText(efform);
        dialogEobislabdetailsBinding.txtmax.setText(String.valueOf(max));
        dialogEobislabdetailsBinding.txtmini.setText(String.valueOf(mini));
        dialogEobislabdetailsBinding.taxded.setText(String.valueOf(ded));



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

    private void AddEobiSlabs(String token)
    {
        dialogAddeobislabBinding = DialogAddeobislabBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddeobislabBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAddeobislabBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                stmax=dialogAddeobislabBinding.etmax.getText().toString();
                stmini=dialogAddeobislabBinding.etmini.getText().toString();
                stded=dialogAddeobislabBinding.etmini.getText().toString();
                steff=dialogAddeobislabBinding.etmini.getText().toString();
                addhealth(token);
                dialog.dismiss();

            }
        });

        dialogAddeobislabBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

            Call<AddEobiSlab> addEobiSlabCall = ApiHandler.getApiInterface().addEobi("Bearer " + token,Apimember());

            addEobiSlabCall.enqueue(new Callback<AddEobiSlab>() {
                @Override
                public void onResponse(Call<AddEobiSlab> addEobiSlabCall1, Response<AddEobiSlab> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(), PayrollActivity.class);
                                startActivity(intent);

                            }
                            else if(status==400)
                            {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),PayrollActivity.class);
                                startActivity(intent);

                            }
                            else if (status==500)
                            {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),PayrollActivity.class);
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
                public void onFailure(Call<AddEobiSlab> call, Throwable t)
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
            jsonObj_.put("maximum_age", stmax);
            jsonObj_.put("minimum_age", stmini);
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