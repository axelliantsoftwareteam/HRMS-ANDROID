package com.khazana.hrm.Fragment.Personalinfo;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.khazana.hrm.Adapter.Personalinfo.UserSkillsAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.ClientInfo.UserContacts.AddContact;
import com.khazana.hrm.Model.ClientInfo.UserExprience.AddExpr.AddExpById;
import com.khazana.hrm.Model.ClientInfo.UserQualification.Deletitem;
import com.khazana.hrm.Model.ClientInfo.UserSkills.AddUserSkills;
import com.khazana.hrm.Model.ClientInfo.UserSkills.GetAllSkill;
import com.khazana.hrm.Model.ClientInfo.UserSkills.GetUserSkillByIdData;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.Config;
import com.khazana.hrm.Utility.SessionManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.khazana.hrm.databinding.DialogAdduserskillBinding;
import com.khazana.hrm.databinding.DialogSavedBinding;
import com.khazana.hrm.databinding.FragmentUserSkillsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserSkillsFragment extends Fragment {

    private FragmentUserSkillsBinding binding;
    private DialogAdduserskillBinding dialogAdduserskillBinding;
    private DialogSavedBinding dialogSavedBinding;


    AddUserSkills addUserSkills=new AddUserSkills();

    private RecyclerView.LayoutManager mLayoutManager;
    UserSkillsAdapter userSkillsAdapter;

    List<GetUserSkillByIdData> getUserSkillByIdDataList = new ArrayList<>();
    List<GetAllSkill> getAllSkillList = new ArrayList<>();

    GetAllSkill obj;

    String stskill, stdecrpt;
    SessionManager sessionManager;
    String token, sname, sdisply, sidd;
    int userid;

    AddContact addContact =new AddContact();
    private int iCurrentSelection=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserSkillsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.exprrecycler.setHasFixedSize(true);



        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.exprrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
//        sidd=sessionManager.getUserID();
//

        userid=Integer.parseInt(sessionManager.getUserID());




        getexpr(token);
//        getallskill(token);


        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.lyCreate.setVisibility(GONE);
                add();
            }
        });
        return view;



    }
    private void add()
    {
        dialogAdduserskillBinding = DialogAdduserskillBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAdduserskillBinding.getRoot());
        dialog.setCancelable(true);



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("userskills", null);
//        Log.e("Tag", "" + json.toString());
//        Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<GetAllSkill>>() {
        }.getType();
        getAllSkillList = gson.fromJson(json, type);

        if (getAllSkillList == null) {

            getAllSkillList = new ArrayList<>();
        }
        String[] items = new String[getAllSkillList.size()];


        for (int i = 0; i < getAllSkillList.size(); i++) {

            items[i] = getAllSkillList.get(i).getSkill();

        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        dialogAdduserskillBinding.spinnerlisttask.setAdapter(adapter);
//        dialogAdduserskillBinding.spinnerlisttask.setSelection(getAllSkillList.indexOf(getAllSkillList.lastIndexOf(obj.getSkill())));//set selected value in spinner


        dialogAdduserskillBinding.spinnerlisttask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                       String sname = parent.getItemAtPosition(position).toString();
//                if (iCurrentSelection == position) {
//                    return;
//                } else {
                    addUserSkills.setSkill(getAllSkillList.get(position).getId());
//                    iCurrentSelection = 0;
//
//                }
//                // Your code here
//                iCurrentSelection = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        dialogAdduserskillBinding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addUserSkills.setUserId(userid);
                adduserskill(token,addUserSkills);

                Log.e("Tag", "userid="+addUserSkills.getUserId());
                Log.e("Tag", "skill="+addUserSkills.getSkill());

                getexpr(token);
                dialog.dismiss();
                savedailog();


            }
        });



        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void savedailog()
    {
        dialogSavedBinding = DialogSavedBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogSavedBinding.getRoot());
        dialog.setCancelable(true);



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogSavedBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void adduserskill(String token, AddUserSkills addUserSkills) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

//            Log.d("Tag",""+addQualification.toString());

            Call<AddExpById> addExpByIdCall = ApiHandler.getApiInterface().addskill("Bearer " + token, addUserSkills);
            addExpByIdCall.enqueue(new Callback<AddExpById>()
            {

                @Override
                public void onResponse(Call<AddExpById> addExpByIdCall1, Response<AddExpById> response) {

                    try {
                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200)
                            {


                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();

                                //     Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getActivity(), More.class);
//                                startActivity(intent);


                                dialog.dismiss();


                            }
                            else if (status == 400)
                            {
                                //    Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                                Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if (status == 401) {

                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            }
                            else{

                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }



                        }

                        else
                        {
//                            Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                            Toast.makeText(getActivity(), "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<AddExpById> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
//                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Internal server error", Toast.LENGTH_SHORT).show();
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

    private void getexpr(final String access_token)
    {
        final CustomProgressDialogue dialog;
        dialog = new CustomProgressDialogue(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        JSONObject jsonobject = new JSONObject();
//        try {
//            jsonobject.put("userId",529);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, Config.USR_SKILL+userid, jsonobject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {

                // mMailDialog.dismiss();

                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(String.valueOf(response));
                    // Toast.makeText(SignIn.this, "" + jObj, Toast.LENGTH_SHORT).show();


                    JSONObject jsonArrays = jObj.getJSONObject("meta");
                    int status = jsonArrays.getInt("status");

//                    if (status == 200) {
//
//                        Toast.makeText(getActivity(), "" + jsonArrays.getString("message"), Toast.LENGTH_SHORT).show();
//
//                    }

                    getUserSkillByIdDataList.clear();

                    JSONObject jsonObject = jObj.getJSONObject("data");
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    JSONArray arr = new JSONArray(jsonArray);
                    //  Toast.makeText(getActivity(), ""+jsonArray, Toast.LENGTH_SHORT).show();
                    if (jsonArray == null) {
                        binding.exprrecycler.setVisibility(GONE);
                        binding.txtno.setVisibility(View.VISIBLE);
                    }
                    for (int n = 0; n < jsonArray.length(); n++) {
                        binding.exprrecycler.setVisibility(View.VISIBLE);
                        binding.txtno.setVisibility(GONE);

                        JSONObject resultsObj = jsonArray.getJSONObject(n);

//                            String statuss = resultsObj.getString("status");
//                            if (statuss == "1") {
//                                approved.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
//
//                            }
//
                        getUserSkillByIdDataList.add(new GetUserSkillByIdData(resultsObj.getInt("id"), resultsObj.getString("skill")));

                    }

                    userSkillsAdapter= new UserSkillsAdapter(getContext(), getUserSkillByIdDataList);
                    binding.exprrecycler.setAdapter(userSkillsAdapter);

                    dialog.dismiss();
                    userSkillsAdapter.setOnItemClickListener(new UserSkillsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, GetUserSkillByIdData obj, int position) {
                            GetUserSkillByIdData getUserSkillByIdData = getUserSkillByIdDataList.get(position);
                            Deletitem deletitem =new Deletitem();
//                                            String name =getQualifyData.getName();
//                                            String val=getQualifyData.getDescription();
                            deletitem.setId(getUserSkillByIdData.getId());



                            deletitem(access_token,deletitem);


                            // Log.e("Tag", "work" + name.toString());


                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myTag", error.toString());
                        Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
        {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + access_token);


                return headers;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        myReq.setShouldCache(false);
        queue.add(myReq);


    }

    private void deletitem(String token, Deletitem deletitem) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

//            Log.d("Tag",""+addQualification.toString());

            Call<AddExpById> addExpByIdCall = ApiHandler.getApiInterface().deletskillitem("Bearer " + token, deletitem);
            addExpByIdCall.enqueue(new Callback<AddExpById>()
            {

                @Override
                public void onResponse(Call<AddExpById> addExpByIdCall1, Response<AddExpById> response) {

                    try {
                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200)
                            {


                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //  Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getActivity(), More.class);
//                                startActivity(intent);
                                getexpr(token);


                            }
                            else if (status == 400)
                            {
                                //    Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                                Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if (status == 401) {

                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            }
                            else{

                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        }

                        else
                        {
//                            Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                            Toast.makeText(getActivity(), "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<AddExpById> call, Throwable t) {
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


}