package com.khazana.hrm.Fragment.Personalinfo;

import static android.view.View.GONE;

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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.khazana.hrm.Adapter.Personalinfo.QualificationAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.ClientInfo.UserExprience.AddExpr.AddExpById;
import com.khazana.hrm.Model.ClientInfo.UserQualification.AddQualification;
import com.khazana.hrm.Model.ClientInfo.UserQualification.Deletitem;
import com.khazana.hrm.Model.ClientInfo.UserQualification.GetQualifyData;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.Config;
import com.khazana.hrm.Utility.SessionManager;
import com.khazana.hrm.databinding.DialogAddqualificationBinding;
import com.khazana.hrm.databinding.FragmentQualificationBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class QualificationFragment extends Fragment {


    SessionManager sessionManager;
    String access_token;
    private FragmentQualificationBinding binding;
    private DialogAddqualificationBinding dialogAddqualificationBinding;


    private RecyclerView.LayoutManager mLayoutManager;
    QualificationAdapter qualificationAdapter;

    List<GetQualifyData> getQualifyDataList = new ArrayList<>();

    int userid;

    AddQualification addQualification = new AddQualification();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_qualification, container, false);

        binding = FragmentQualificationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sessionManager = new SessionManager(getActivity());
        access_token = sessionManager.getToken();

        userid = Integer.parseInt(sessionManager.getUserID());


        binding.qualifyrecycler.setHasFixedSize(true);

//
//        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter(newText);
//                return false;
//            }
//        });

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.qualifyrecycler.setLayoutManager(mLayoutManager);

        getqualify(access_token);


        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.lyCreate.setVisibility(GONE);
                addqualify();
            }
        });


        return view;


    }

    private void getqualify(final String access_token) {

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
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, Config.QUALIFY + userid, jsonobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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

                    getQualifyDataList.clear();

                    JSONObject jsonObject = jObj.getJSONObject("data");
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    JSONArray arr = new JSONArray(jsonArray);
                    //  Toast.makeText(getActivity(), ""+jsonArray, Toast.LENGTH_SHORT).show();
                    if (jsonArray == null) {
                        binding.qualifyrecycler.setVisibility(GONE);
                        binding.txtno.setVisibility(View.VISIBLE);
                    }
                    for (int n = 0; n < jsonArray.length(); n++) {
                        binding.qualifyrecycler.setVisibility(View.VISIBLE);
                        binding.txtno.setVisibility(GONE);

                        JSONObject resultsObj = jsonArray.getJSONObject(n);

//                            String statuss = resultsObj.getString("status");
//                            if (statuss == "1") {
//                                approved.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
//
//                            }
//
                        getQualifyDataList.add(new GetQualifyData(resultsObj.getInt("id"), resultsObj.getString("name"), resultsObj.getString("institute"), resultsObj.getInt("graduationYear")));

                    }

                    qualificationAdapter = new QualificationAdapter(getContext(), getQualifyDataList);
                    binding.qualifyrecycler.setAdapter(qualificationAdapter);


                    dialog.dismiss();
                    qualificationAdapter.setOnItemClickListener(new QualificationAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, GetQualifyData obj, int position) {
                            GetQualifyData getQualifyData = getQualifyDataList.get(position);
                            Deletitem deletitem = new Deletitem();
//                                            String name =getQualifyData.getName();
//                                            String val=getQualifyData.getDescription();
                            deletitem.setId(getQualifyData.getId());


                            deletitem(access_token, deletitem);


                            // Log.e("Tag", "work" + name.toString());


                        }
                    });


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
                        dialog.dismiss();
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
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


    private void addqualify() {
        dialogAddqualificationBinding = DialogAddqualificationBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddqualificationBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAddqualificationBinding.btnaddqly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogAddqualificationBinding.etname.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogAddqualificationBinding.etname.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    addQualification.setName(dialogAddqualificationBinding.etname.getText().toString().trim());
                    // editText is not empty
                }
                if (dialogAddqualificationBinding.etinstitute.getText().toString().isEmpty()) {

                    // editText is empty
                    dialogAddqualificationBinding.etinstitute.setError("The item cannot be empty ");
//                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    addQualification.setInstitute(dialogAddqualificationBinding.etinstitute.getText().toString().trim());
                    // editText is not empty
                }

                if (dialogAddqualificationBinding.etgraduat.getText().toString().isEmpty()) {

                    // editText is empty
                    dialogAddqualificationBinding.etgraduat.setError("The item cannot be empty ");
//                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    addQualification.setGraduationYear(dialogAddqualificationBinding.etgraduat.getText().toString().trim());
                    // editText is not empty
                    addQualification.setUserId(userid);


                    Log.e("Tag", "name=" + addQualification.getName());
                    Log.e("Tag", "inst=" + addQualification.getInstitute());
                    Log.e("Tag", "graduation=" + addQualification.getGraduationYear());


                    addqualfy(access_token, addQualification);


                    dialog.dismiss();
                }


            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void addqualfy(String token, AddQualification addQualification) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

//            Log.d("Tag",""+addQualification.toString());

            Call<AddExpById> addExpByIdCall = ApiHandler.getApiInterface().addqualify("Bearer " + token, addQualification);
            addExpByIdCall.enqueue(new Callback<AddExpById>() {

                @Override
                public void onResponse(Call<AddExpById> addExpByIdCall1, retrofit2.Response<AddExpById> response) {

                    try {
                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200) {


                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //  Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getActivity(), More.class);
//                                startActivity(intent);
                                getqualify(token);


                            } else if (status == 400) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
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


    private void deletitem(String token, Deletitem deletitem) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

//            Log.d("Tag",""+addQualification.toString());

            Call<AddExpById> addExpByIdCall = ApiHandler.getApiInterface().deletqualifyitem("Bearer " + token, deletitem);
            addExpByIdCall.enqueue(new Callback<AddExpById>() {

                @Override
                public void onResponse(Call<AddExpById> addExpByIdCall1, retrofit2.Response<AddExpById> response) {

                    try {
                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200) {


                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //  Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getActivity(), More.class);
//                                startActivity(intent);
                                getqualify(token);


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


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<GetQualifyData> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (GetQualifyData item : getQualifyDataList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();

        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            qualificationAdapter.filterList(filteredlist);
        }
    }

}