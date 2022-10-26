package com.khazana.hrm.Fragment.BasicSetup;

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

import com.khazana.hrm.Adapter.StaticData.DepartmentAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.BasicSetup.Department.GetDepartment;
import com.khazana.hrm.Model.BasicSetup.Department.GetDepartmentData;
import com.khazana.hrm.Model.Building.AddBuild.AddBuild;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.UI.AddLeave;
import com.khazana.hrm.UI.BasicSetupActivity;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khazana.hrm.databinding.DialogAdddepartBinding;
import com.khazana.hrm.databinding.DialogEditdepartBinding;
import com.khazana.hrm.databinding.FragmentDepartmentBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentFragment extends Fragment {

    private FragmentDepartmentBinding binding;
    private DialogAdddepartBinding dialogAdddepartBinding;
    private DialogEditdepartBinding dialogEditdepartBinding;


    private RecyclerView.LayoutManager mLayoutManager;
    DepartmentAdapter departmentAdapter;

    List<GetDepartmentData> getDepartmentDataList = new ArrayList<>();


    String stskill, stdecrpt;
    SessionManager sessionManager;
    String token;
    String stname, stdisply;
    Integer sidd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDepartmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.departrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.departrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getdepart(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddDepart();

            }
        });


        return view;

    }

    private void getdepart(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetDepartment> getDepartmentCall = ApiHandler.getApiInterface().getDepart("Bearer " + access_token);
            getDepartmentCall.enqueue(new Callback<GetDepartment>() {
                @Override
                public void onResponse(Call<GetDepartment> getDepartmentCall1, Response<GetDepartment> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getDepartmentDataList = response.body().getData().getResponse();

                                if (getDepartmentDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.departrecycler.setVisibility(View.GONE);

                                } else if (getDepartmentDataList != null) {

                                    departmentAdapter = new DepartmentAdapter(getActivity(), getDepartmentDataList);
                                    binding.departrecycler.setAdapter(departmentAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.departrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    departmentAdapter.setOnItemClickListener(new DepartmentAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetDepartmentData obj, int position) {
                                            GetDepartmentData getDepartmentData = getDepartmentDataList.get(position);
                                            String name = getDepartmentData.getName();
                                            String val = getDepartmentData.getDescription();
                                            Integer sid = getDepartmentData.getId();
                                            showEditDialog(name, val, sid);

                                        }
                                    });


                                }

                            } else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);


                            } else {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
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


                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetDepartment> call, Throwable t) {
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

    private void AddDepart() {
        dialogAdddepartBinding = DialogAdddepartBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAdddepartBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAdddepartBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                stskill = dialogAdddepartBinding.etname.getText().toString();
                stdecrpt = dialogAdddepartBinding.etdescrpt.getText().toString();
                AddDepart(token);
                dialog.dismiss();

            }
        });

        dialogAdddepartBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void AddDepart(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddBuild> addBuildCall = ApiHandler.getApiInterface().addDepart("Bearer " + token, Apimember());

            addBuildCall.enqueue(new Callback<AddBuild>() {
                @Override
                public void onResponse(Call<AddBuild> addBuildCall1, Response<AddBuild> response) {

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
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<AddBuild> call, Throwable t) {
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
            jsonObj_.put("name", stskill);
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

    private void showEditDialog(String name, String decrpt, Integer id) {

        dialogEditdepartBinding = DialogEditdepartBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditdepartBinding.getRoot());
        dialog.setCancelable(true);
        dialogEditdepartBinding.etname.setText(name);
        dialogEditdepartBinding.etdescript.setText(decrpt);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditdepartBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidd = id;
                stname = dialogEditdepartBinding.etname.getText().toString().trim();
                stdisply = dialogEditdepartBinding.etdescript.getText().toString().trim();
                ActionMembr(token);
                dialog.dismiss();

            }
        });

        dialogEditdepartBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void ActionMembr(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddBuild> addBuildCall = ApiHandler.getApiInterface().addDepart("Bearer " + token, ApiAction());

            addBuildCall.enqueue(new Callback<AddBuild>() {
                @Override
                public void onResponse(Call<AddBuild> addBuildCall1, Response<AddBuild> response) {

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
//                                Intent intent =new Intent(getActivity(),BasicSetupActivity.class);
//                                startActivity(intent);

                            } else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<AddBuild> call, Throwable t) {
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
            jsonObj_.put("name", stname);
            jsonObj_.put("description", stdisply);


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