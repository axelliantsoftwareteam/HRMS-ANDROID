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

import com.khazana.hrm.Adapter.StaticData.BuildingAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.Building.AddBuild.AddBuild;
import com.khazana.hrm.Model.Building.GetBuilding;
import com.khazana.hrm.Model.Building.GetBuildingData;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.UI.BasicSetupActivity;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khazana.hrm.databinding.DialogAddbuildBinding;
import com.khazana.hrm.databinding.DialogEditbuildBinding;
import com.khazana.hrm.databinding.FragmentBuildingsBinding;

import org.json.JSONException;
import org.json.JSONObject;

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
    String token, sname, sdescrpt, saddr, namebulid, sdescrptbuild, saddrbuild;
    Integer sbulidid;

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
            public void onClick(View v) {

                Addbuild(token);

            }
        });


        return view;
    }

    private void getbuild(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetBuilding> getBuildingCall = ApiHandler.getApiInterface().getBuilding("Bearer " + access_token);
            getBuildingCall.enqueue(new Callback<GetBuilding>() {
                @Override
                public void onResponse(Call<GetBuilding> getBuildingCall1, Response<GetBuilding> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

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
                                            GetBuildingData getBuildingData = getBuildingDataList.get(position);
                                            namebulid = getBuildingData.getName();
                                            sdescrptbuild = getBuildingData.getDescription();
                                            saddrbuild = getBuildingData.getAddress();
                                            sbulidid = getBuildingData.getId();


                                            // Log.e("Tag", "work" + name.toString());

                                            showEditDialog(token, namebulid, sdescrptbuild, saddrbuild, sbulidid);
                                        }
                                    });


                                }

                            } else if (status == 401) {
//                                String b = response.body().getMeta().getMessage();
                                binding.buildrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else {
                                String b = response.body().getMeta().getMessage();
                                binding.buildrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
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
                public void onFailure(Call<GetBuilding> call, Throwable t) {
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

    private void Addbuild(String token) {
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
            public void onClick(View v) {
                sname = dialogAddbuildBinding.etname.getText().toString().trim();
                sdescrpt = dialogAddbuildBinding.etdescrpt.getText().toString().trim();
                saddr = dialogAddbuildBinding.etaddr.getText().toString().trim();

                AddBuild(token);

                dialog.dismiss();

            }
        });

        dialogAddbuildBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showEditDialog(String token, String snamebul, String sdescrt, String sbuildaddr, Integer iid) {

        dialogEditbuildBinding = DialogEditbuildBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditbuildBinding.getRoot());
        dialog.setCancelable(true);

        dialogEditbuildBinding.etname.setText(snamebul);
        dialogEditbuildBinding.etdescript.setText(sdescrt);
        dialogEditbuildBinding.etaddr.setText(sbuildaddr);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditbuildBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbulidid = iid;
                namebulid = dialogEditbuildBinding.etname.getText().toString().trim();
                sdescrptbuild = dialogEditbuildBinding.etdescript.getText().toString().trim();
                saddrbuild = dialogEditbuildBinding.etaddr.getText().toString().trim();
                ActionMembr(token);
                dialog.dismiss();

            }
        });

        dialogEditbuildBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void AddBuild(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddBuild> addBuildCall = ApiHandler.getApiInterface().addbuild("Bearer " + token, Apimember());

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
//                                String b = response.body().getMeta().getMessage();
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
            jsonObj_.put("name", sname);
            jsonObj_.put("description", sdescrpt);
            jsonObj_.put("address", saddr);


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

            Call<AddBuild> addBuildCall = ApiHandler.getApiInterface().addbuild("Bearer " + token, ApiAction());

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
//                                String b = response.body().getMeta().getMessage();
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
                        }
                        else
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
            jsonObj_.put("id", sbulidid);
            jsonObj_.put("name", namebulid);
            jsonObj_.put("description", sdescrptbuild);
            jsonObj_.put("address", saddrbuild);


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