package com.khazana.hrm.Fragment.BasicSetup;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Adapter.StaticData.DesignationAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.BasicSetup.Designation.GetAllDesign.GetAllDesignation;
import com.khazana.hrm.Model.BasicSetup.Designation.GetAllDesign.GetAllDesignationData;
import com.khazana.hrm.Model.BasicSetup.Designation.GetDesig;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.UI.BasicSetupActivity;
import com.khazana.hrm.UI.MicCalendarActivity;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khazana.hrm.databinding.DialogAdddesigBinding;
import com.khazana.hrm.databinding.DialogEditdesigBinding;
import com.khazana.hrm.databinding.FragmentDesignationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DesignationFragment extends Fragment {


    private FragmentDesignationBinding binding;

    String stdesg, stdecrpt;
    String stadddesg, stadddecrpt;

    private DialogAdddesigBinding dialogAdddesigBinding;
    private DialogEditdesigBinding dialogEditdesigBinding;

    private RecyclerView.LayoutManager mLayoutManager;
    DesignationAdapter designationAdapter;

    List<GetAllDesignationData> getAllDesignationDataList = new ArrayList<>();
    Integer idd, sidd;
    String desgname, descrpt;


    SessionManager sessionManager;
    String token;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDesignationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.desigrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.desigrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getdesignation(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddDesDialog();


            }
        });

        return view;


    }

    private void getdesignation(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAllDesignation> getAllDesignationCall = ApiHandler.getApiInterface().getDesignation("Bearer " + access_token);
            getAllDesignationCall.enqueue(new Callback<GetAllDesignation>() {
                @Override
                public void onResponse(Call<GetAllDesignation> getAllDesignationCall1, Response<GetAllDesignation> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getAllDesignationDataList = response.body().getData().getResponse();

                                if (getAllDesignationDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.desigrecycler.setVisibility(View.GONE);

                                } else if (getAllDesignationDataList != null) {

                                    designationAdapter = new DesignationAdapter(getActivity(), getAllDesignationDataList);
                                    binding.desigrecycler.setAdapter(designationAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.desigrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    designationAdapter.setOnItemClickListener(new DesignationAdapter.OnItemClickListener() {


                                        @Override
                                        public void onItemClick(View view, GetAllDesignationData obj, int position) {

                                            GetAllDesignationData getAllDesignationData = getAllDesignationDataList.get(position);
                                            idd = getAllDesignationData.getId();
                                            desgname = getAllDesignationData.getName();
                                            descrpt = getAllDesignationData.getDescription();

                                            // Log.e("Tag", "work" + name.toString());

                                            showEditDialog(desgname, descrpt, idd);
                                        }
                                    });


                                }

                            } else if (status == 401) {
                                binding.txtno.setVisibility(View.VISIBLE);
                                binding.desigrecycler.setVisibility(View.GONE);
//                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "Your session will be expired please login again! ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else if (status == 400) {
                                binding.txtno.setVisibility(View.VISIBLE);
                                binding.desigrecycler.setVisibility(View.GONE);
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            } else {
                                binding.txtno.setVisibility(View.VISIBLE);
                                binding.desigrecycler.setVisibility(View.GONE);
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }

                        }
                        else {
                            binding.txtno.setVisibility(View.VISIBLE);
                            binding.desigrecycler.setVisibility(View.GONE);
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
                            Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetAllDesignation> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
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

    private void AddDesDialog() {
        dialogAdddesigBinding = DialogAdddesigBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAdddesigBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAdddesigBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stadddesg = dialogAdddesigBinding.etdesg.getText().toString().trim();
                stadddecrpt = dialogAdddesigBinding.etdescrpt.getText().toString().trim();

                AddDesig(token);
                dialog.dismiss();

            }
        });

        dialogAdddesigBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showEditDialog(String desg, String descrpt, Integer id) {

        dialogEditdesigBinding = DialogEditdesigBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditdesigBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogEditdesigBinding.etname.setText(desg);
        dialogEditdesigBinding.etdescript.setText(descrpt);

        dialogEditdesigBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidd = id;
                stdesg = dialogEditdesigBinding.etname.getText().toString().trim();
                stdecrpt = dialogEditdesigBinding.etdescript.getText().toString().trim();
                ActionMembr(token);
                dialog.dismiss();

            }
        });

        dialogEditdesigBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void AddDesig(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<GetDesig> getDesigCall = ApiHandler.getApiInterface().addDesignation("Bearer " + token, Apimember());

            getDesigCall.enqueue(new Callback<GetDesig>() {
                @Override
                public void onResponse(Call<GetDesig> getDesigCall1, Response<GetDesig> response) {

                    try {

                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), BasicSetupActivity.class);
                                startActivity(intent);

                            } else if (status == 400) {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), BasicSetupActivity.class);
                                startActivity(intent);

                            } else if (status == 401) {
                                //String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "Your session will be expired please login again! ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else if (status == 500) {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), BasicSetupActivity.class);
                                startActivity(intent);

                            } else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            String msg = response.body().getMeta().getMessage();
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
                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetDesig> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
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
            jsonObj_.put("name", stadddesg);
            jsonObj_.put("description", stadddecrpt);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


    private void ActionMembr(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<GetDesig> getDesigCall = ApiHandler.getApiInterface().addDesignation("Bearer " + token, ApiAction());

            getDesigCall.enqueue(new Callback<GetDesig>() {
                @Override
                public void onResponse(Call<GetDesig> getDesigCall1, Response<GetDesig> response) {

                    try {

                        if (response.isSuccessful()) {


                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
//                                Intent intent =new Intent(getActivity(), BasicSetupActivity.class);
//                                startActivity(intent);

                            } else if (status == 400) {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
//                                Intent intent =new Intent(getActivity(),BasicSetupActivity.class);
//                                startActivity(intent);

                            } else if (status == 401) {
                                //String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "Your session will be expired please login again! ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else if (status == 500) {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
//                                Intent intent =new Intent(getActivity(),BasicSetupActivity.class);
//                                startActivity(intent);

                            } else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            String msg = response.body().getMeta().getMessage();
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
                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetDesig> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
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

    private JsonObject ApiAction() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("id", sidd);
            jsonObj_.put("name", stdesg);
            jsonObj_.put("description", stdecrpt);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }

}