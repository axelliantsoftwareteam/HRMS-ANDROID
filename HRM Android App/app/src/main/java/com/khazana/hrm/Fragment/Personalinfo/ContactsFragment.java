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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.khazana.hrm.Adapter.Personalinfo.UserContactsAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.ClientInfo.UserContacts.AddContact;
import com.khazana.hrm.Model.ClientInfo.UserContacts.GetUserContactData;
import com.khazana.hrm.Model.ClientInfo.UserExprience.AddExpr.AddExpById;
import com.khazana.hrm.Model.ClientInfo.UserQualification.Deletitem;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.Config;
import com.khazana.hrm.Utility.SessionManager;
import com.khazana.hrm.databinding.DialogAddcontactsBinding;
import com.khazana.hrm.databinding.FragmentContactsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactsFragment extends Fragment {


    private FragmentContactsBinding binding;
    private DialogAddcontactsBinding dialogAddcontactsBinding;


    private RecyclerView.LayoutManager mLayoutManager;
    UserContactsAdapter userContactsAdapter;

    List<GetUserContactData> getUserContactDataList = new ArrayList<>();


    String stskill, stdecrpt;
    SessionManager sessionManager;
    String token, sname, sdisply, sidd;
    int userid;

    AddContact addContact =new AddContact();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.exprrecycler.setHasFixedSize(true);


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
        binding.exprrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
//        sidd=sessionManager.getUserID();
//

        userid=Integer.parseInt(sessionManager.getUserID());




        getexpr(token);



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
        dialogAddcontactsBinding = DialogAddcontactsBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddcontactsBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogAddcontactsBinding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(dialogAddcontactsBinding.etaddr.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogAddcontactsBinding.etaddr.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addContact.setAddress(dialogAddcontactsBinding.etaddr.getText().toString().trim());
                    // editText is not empty
                }
                if(dialogAddcontactsBinding.etphone.getText().toString().isEmpty())
                {


                    // editText is empty
                    dialogAddcontactsBinding.etphone.setError("The item cannot be empty ");
//                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addContact.setPhone(dialogAddcontactsBinding.etphone.getText().toString().trim());
                    // editText is not empty
                    Log.e("Tag", "addr="+addContact.getAddress());
                    Log.e("Tag", "phone="+addContact.getPhone());

                    addContact.setUserId(userid);


                    addexpr(token,addContact);


                    dialog.dismiss();
                }



            }
        });



        dialog.show();
        dialog.getWindow().setAttributes(lp);
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
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, Config.USR_CONT+userid, jsonobject, new com.android.volley.Response.Listener<JSONObject>() {
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

                       // Toast.makeText(getActivity(), "" + jsonArrays.getString("message"), Toast.LENGTH_SHORT).show();
                        getUserContactDataList.clear();

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
                            getUserContactDataList.add(new GetUserContactData(resultsObj.getInt("id"), resultsObj.getString("address"), resultsObj.getString("phone")));

                        }
                    }
                    else if (status==400)
                    {
                        binding.txtno.setVisibility(View.VISIBLE);
                        binding.exprrecycler.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                    else if (status == 401) {
                        binding.txtno.setVisibility(View.VISIBLE);
                        binding.exprrecycler.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);

                    }
                    else {

                        Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);

                    }



                    userContactsAdapter= new UserContactsAdapter(getContext(), getUserContactDataList);
                    binding.exprrecycler.setAdapter(userContactsAdapter);

                    dialog.dismiss();
                    userContactsAdapter.setOnItemClickListener(new UserContactsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, GetUserContactData obj, int position) {
                            GetUserContactData getUserContactData = getUserContactDataList.get(position);
                            Deletitem deletitem =new Deletitem();
//                                            String name =getQualifyData.getName();
//                                            String val=getQualifyData.getDescription();
                            deletitem.setId(getUserContactData.getId());



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
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
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


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<GetUserContactData> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (GetUserContactData item : getUserContactDataList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getAddress().toLowerCase().contains(text.toLowerCase())) {
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
            userContactsAdapter.filterList(filteredlist);
        }
    }
    private void addexpr(String token, AddContact addContact) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
//            Log.d("Tag",""+addQualification.toString());

            Call<AddExpById> addExpByIdCall = ApiHandler.getApiInterface().addcontact("Bearer " + token, addContact);
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
                                binding.lyCreate.setVisibility(GONE);
                                getexpr(token);
                                dialog.dismiss();


                            }
                            else if (status == 400)
                            {
                                //    Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                                Toast.makeText(getActivity(), "Your session may expired please login again!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
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

            Call<AddExpById> addExpByIdCall = ApiHandler.getApiInterface().deletecontact("Bearer " + token, deletitem);
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