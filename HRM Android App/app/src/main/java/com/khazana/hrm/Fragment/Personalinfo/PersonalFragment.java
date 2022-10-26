package com.khazana.hrm.Fragment.Personalinfo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.ClientInfo.AddPresonalInfo;
import com.khazana.hrm.Model.ClientInfo.GetUserById;
import com.khazana.hrm.Model.ClientInfo.GetUserByIdData;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.User;
import com.khazana.hrm.Model.UserProfileModel.Gender.GetGenderData;
import com.khazana.hrm.Model.UserProfileModel.Salutation.GetSalutationData;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.Config;
import com.khazana.hrm.Utility.SessionManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.khazana.hrm.databinding.FragmentPersonalBinding;

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


public class PersonalFragment extends Fragment {


    private FragmentPersonalBinding binding;

    SessionManager sessionManager;
    AddPresonalInfo addPresonalInfo = new AddPresonalInfo();
    GetUserByIdData getUserByIdData = new GetUserByIdData();


    String access_token;
    int userid;


    List<GetGenderData> getGenderDataList = new ArrayList<>();

    List<GetSalutationData> getSalutationDataList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPersonalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sessionManager = new SessionManager(getActivity());
        access_token = sessionManager.getToken();

        userid = Integer.parseInt(sessionManager.getUserID());

        loadsalutation();
        loadgender();
        getinfo(access_token);

        binding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etname.getText().toString().isEmpty()) {
                    // editText is empty
                    binding.etname.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    addPresonalInfo.setName(binding.etname.getText().toString().trim());
                    // editText is not empty
                }
                if (binding.etetcnic.getText().toString().isEmpty()) {

                    // editText is empty
                    binding.etetcnic.setError("The item cannot be empty ");
//                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    addPresonalInfo.setCnic(binding.etetcnic.getText().toString().trim());
                    // editText is not empty
                }

//                if (binding.etdob.getText().toString().isEmpty()) {
//
//                    // editText is empty
//                    binding.etdob.setError("The item cannot be empty ");
////                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
//                } else {
//                    addPresonalInfo.setDob(binding.etdob.getText().toString().trim());
//                    // editText is not empty
//                }

                if (binding.etemail.getText().toString().isEmpty()) {
                    binding.etemail.setError("The item cannot be empty ");
                } else {
                    addPresonalInfo.setPrimaryEmail(binding.etemail.getText().toString().trim());

                    addPresonalInfo.setDob(binding.etdob.getText().toString().trim());
                    addPresonalInfo.setLinkedin(binding.etlinked.getText().toString().trim());


                    addPresonalInfo.setId(userid);

                    Log.e("Tag", "name=" + addPresonalInfo.getName());
                    Log.e("Tag", "email=" + addPresonalInfo.getPrimaryEmail());
                    Log.e("Tag", "cnic=" + addPresonalInfo.getCnic());
                    Log.e("Tag", "dob=" + addPresonalInfo.getDob());
                    Log.e("Tag", "gender=" + addPresonalInfo.getGender());
                    Log.e("Tag", "id=" + addPresonalInfo.getId());
                    Log.e("Tag", "sal=" + addPresonalInfo.getSalutation());
                    Log.e("Tag", "linkend=" + addPresonalInfo.getLinkedin());
                    addpersonalinfo(access_token, addPresonalInfo);

                }


//                Addqualify();

            }

        });


        return view;

    }

    private void getinfo(final String access_token) {

        final CustomProgressDialogue dialog;
        dialog = new CustomProgressDialogue(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        JSONObject jsonobject = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                Config.TOKEN + userid, jsonobject, new Response.Listener<JSONObject>() {
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

                    if (status == 200)
                    {

                        JSONObject jsonObject = jObj.getJSONObject("data");
                        JSONObject jsonArray = jsonObject.getJSONObject("Response");
                        User user = new User(jsonArray.getInt("id"), jsonArray.getString("primaryEmail"), jsonArray.getString("linkedin"), jsonArray.getString("cnic"), jsonArray.getString("dob"), jsonArray.getString("email"), jsonArray.getString("name"));


//                    sessionManager.saveUserID(String.valueOf(user.getId()));

                        binding.etname.setText(user.getName());
                        binding.etemail.setText(user.getEmail());
                        binding.etdob.setText(user.getDob());
                        binding.etlinked.setText(user.getLinkedin());
                        binding.etetcnic.setText(user.getCnic());


                        sessionManager.saveFirstName(user.getName());
                        sessionManager.saveCnicNumber(user.getCnic());
                        sessionManager.saveUserEmail(user.getEmail());
                        sessionManager.saveOfdob(user.getDob());


                        dialog.dismiss();
                    }
                    else if (status == 401)
                    {
                        Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);

                    } else {
                        String msg = jsonArrays.getString("message");
                        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myTag", error.toString());
                        Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }) {

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


    private void addpersonalinfo(String token, AddPresonalInfo addPresonalInfo) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

//            Log.d("Tag",""+addQualification.toString());

            Call<GetUserById> getUserByIdCall = ApiHandler.getApiInterface().addpersioninfo("Bearer " + token, addPresonalInfo);
            getUserByIdCall.enqueue(new Callback<GetUserById>() {

                @Override
                public void onResponse(Call<GetUserById> getUserByIdCall1, retrofit2.Response<GetUserById> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();

                            if (status == 200) {

                                getUserByIdData = response.body().getData().getResponse();


                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                getinfo(access_token);
//                                Intent intent = new Intent(getActivity(), More.class);
//                                startActivity(intent);
                                //   getqualify(token, userid);


                            } else if (status == 400) {
                                //    Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                                Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
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
                            }


                        } else {
//                            Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                            Toast.makeText(getActivity(), "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            dialog.dismiss();

                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetUserById> call, Throwable t) {
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

    public void loadgender() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("gender", null);
//        Log.e("Tag", "" + json.toString());
//        Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<GetGenderData>>() {
        }.getType();
        getGenderDataList = gson.fromJson(json, type);

        if (getGenderDataList == null) {

            getGenderDataList = new ArrayList<>();
        }
        // setspiner(getGenderDataList);
        //String array to store all the book names
        String[] items = new String[getGenderDataList.size()];

        //Traversing through the whole list to get all the names
        for (int i = 0; i < getGenderDataList.size(); i++) {
            //Storing names to string array
            items[i] = getGenderDataList.get(i).getValue();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        binding.spGender.setAdapter(adapter);
        //  binding.spGender.setSelection(adapter.getPosition(getGenderDataList.get(0).getValue()));//set selected value in spinner


        binding.spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //   addPresonalInfo.setGender(getGenderDataList.get(position).getId());

//                if (iCurrentSelection == position) {
//                    return;
//                } else {
                addPresonalInfo.setGender(getGenderDataList.get(position).getId());
//                    iCurrentSelection = 0;
//
//                }
//                // Your code here
//                iCurrentSelection = position;
                String sname = parent.getItemAtPosition(position).toString();
//                Log.e("Tag", "member=" + sname.toString());
//                Log.e("Tag", "idd=" + idd.toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


    }

    public void loadsalutation() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("salutation", null);
//        Log.e("Tag", "" + json.toString());
//        Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<GetSalutationData>>() {
        }.getType();
        getSalutationDataList = gson.fromJson(json, type);

        if (getSalutationDataList == null) {

            getSalutationDataList = new ArrayList<>();
        }
        String[] items = new String[getSalutationDataList.size()];


        for (int i = 0; i < getSalutationDataList.size(); i++) {

            items[i] = getSalutationDataList.get(i).getValue();
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        binding.spSalu.setAdapter(adapter);
//        binding.spSalu.setSelection(getSalutationDataList.indexOf(getSalutationDataList.lastIndexOf(obj.getSalutation())));//set selected value in spinner

        //  binding.spSalu.setSelection(adapter.getPosition(getSalutationDataList.get(0).getValue()));//set selected value in spinner


        binding.spSalu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //       String sname = parent.getItemAtPosition(position).toString();
//                if (iCurrentSelection == position) {
//                    return;
//                } else {
                addPresonalInfo.setSalutation(getSalutationDataList.get(position).getId());
                // iCurrentSelection = 0;

//                }
//                // Your code here
//                iCurrentSelection = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

    }

}