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

import com.example.hrm.Adapter.Payroll.TaxSlabsAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.BasicSetup.Skills.AddSkill.Addskill;
import com.example.hrm.Model.Payroll.TaxSlab.GetTaxSlabs;
import com.example.hrm.Model.Payroll.TaxSlab.GetTaxSlabsData;
import com.example.hrm.UI.BasicSetupActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddtaxBinding;
import com.example.hrm.databinding.DialogTaxslabdetailsBinding;
import com.example.hrm.databinding.FragmentTaxSlabsBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TaxSlabsFragment extends Fragment {


    private FragmentTaxSlabsBinding binding;
    private DialogAddtaxBinding dialogAddtaxBinding;
    private DialogTaxslabdetailsBinding dialogTaxslabdetailsBinding;


    private RecyclerView.LayoutManager mLayoutManager;
    TaxSlabsAdapter taxSlabsAdapter;

    List<GetTaxSlabsData> getTaxSlabsDataList = new ArrayList<>();



    String start,intmini,intmax,intded,dperct,dpremini;





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
        binding = FragmentTaxSlabsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.taxsrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.taxsrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        gettax(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AddTax();

            }
        });

        return view;
    }
    private void gettax(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetTaxSlabs> getTaxSlabsCall = ApiHandler.getApiInterface().gettax("Bearer " + access_token);
            getTaxSlabsCall.enqueue(new Callback<GetTaxSlabs>() {
                @Override
                public void onResponse(Call<GetTaxSlabs> getTaxSlabsCall1, Response<GetTaxSlabs> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getTaxSlabsDataList = response.body().getData().getResponse();

                                if (getTaxSlabsDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.taxsrecycler.setVisibility(View.GONE);

                                } else if (getTaxSlabsDataList != null) {

                                    taxSlabsAdapter = new TaxSlabsAdapter(getActivity(), getTaxSlabsDataList);
                                    binding.taxsrecycler.setAdapter(taxSlabsAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.taxsrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    taxSlabsAdapter.setOnItemClickListener(new TaxSlabsAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetTaxSlabsData obj, int position) {
                                            GetTaxSlabsData getTaxSlabsData = getTaxSlabsDataList.get(position);
                                            String start =getTaxSlabsData.getEffectiveFrom();
                                            Integer mini=getTaxSlabsData.getMinimum();
                                            Integer max=getTaxSlabsData.getMaximum();
                                            Double perct=getTaxSlabsData.getPercantage();
                                            Integer ded=getTaxSlabsData.getDeduction();
                                            Double premini=getTaxSlabsData.getPercentageOfMinimum();
                                            showDetails(start,mini,max,perct,ded,premini);





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
                public void onFailure(Call<GetTaxSlabs> call, Throwable t) {
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

    private void AddTax()
    {
        dialogAddtaxBinding = DialogAddtaxBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddtaxBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAddtaxBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);



                start=dialogAddtaxBinding.eteffform.getText().toString();
                intmini=dialogAddtaxBinding.etmini.getText().toString();
                intmax=dialogAddtaxBinding.etmini.getText().toString();
                intded=dialogAddtaxBinding.etmini.getText().toString();
                dperct=dialogAddtaxBinding.etmini.getText().toString();
                dpremini=dialogAddtaxBinding.etmini.getText().toString();
                addtax(token);
                dialog.dismiss();

            }
        });

        dialogAddtaxBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

    private void addtax(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<Addskill> addskillCall = ApiHandler.getApiInterface().addtax("Bearer " + token,Apimember());

            addskillCall.enqueue(new Callback<Addskill>() {
                @Override
                public void onResponse(Call<Addskill> addskillCall1, Response<Addskill> response ) {

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
                public void onFailure(Call<Addskill> call, Throwable t)
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
            jsonObj_.put("effective_from", start);
            jsonObj_.put("minimum", intmini);
            jsonObj_.put("maximum", intmax);
            jsonObj_.put("deduction", intded);
            jsonObj_.put("percantage", dperct);
            jsonObj_.put("percentage_of_minimum", dpremini);


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

    private void showDetails(String start, Integer mini,Integer max,Double perct,Integer ded,Double premini)
    {

        dialogTaxslabdetailsBinding = DialogTaxslabdetailsBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogTaxslabdetailsBinding.getRoot());
        dialog.setCancelable(true);


//        String per= Double.toString(perct);
        dialogTaxslabdetailsBinding.txtefffrom.setText(start);
        dialogTaxslabdetailsBinding.txtmax.setText(String.valueOf(max));
        dialogTaxslabdetailsBinding.txtmini.setText(String.valueOf(mini));
        dialogTaxslabdetailsBinding.txtpre.setText(String.valueOf(perct));
        dialogTaxslabdetailsBinding.txtminiprec.setText(String.valueOf(premini));
        dialogTaxslabdetailsBinding.taxded.setText(String.valueOf(ded));
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
}