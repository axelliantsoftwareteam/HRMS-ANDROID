package com.khazana.hrm.Fragment.Approval;

import static android.content.Context.MODE_PRIVATE;

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
import com.khazana.hrm.Adapter.Approval.ApprovalAttendanceAdapter;
import com.khazana.hrm.Adapter.Approval.ApprovalEquipmentAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.Approval.AttendApproval.AttendApproval;
import com.khazana.hrm.Model.Approval.AttendApproval.GetAttendApprolModel;
import com.khazana.hrm.Model.Approval.EquipmentApproval.AddEquiptReq;
import com.khazana.hrm.Model.Approval.EquipmentApproval.GetEquipmentData;
import com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow;
import com.khazana.hrm.Model.Approval.EquipmentApproval.model;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.Memberlist.MemberResponse;
import com.khazana.hrm.Model.UserProfileModel.UserData.EditProfile.EditProf;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.Config;
import com.khazana.hrm.Utility.SessionManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.khazana.hrm.databinding.DialogAttendapprovBinding;
import com.khazana.hrm.databinding.DialogSaveequiptmentBinding;
import com.khazana.hrm.databinding.FragmentEquipmentApprovalBinding;

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

public class EquipmentApprovalFragment extends Fragment {


    private FragmentEquipmentApprovalBinding binding;



    SessionManager sessionManager;
    String token, sname;
    int iCurrentSelection = 0;
    List<MemberResponse> memberResponses = new ArrayList<>();

    AddEquiptReq addEquiptReq =new AddEquiptReq();



    private RecyclerView.LayoutManager mLayoutManager;
    ApprovalEquipmentAdapter approvalEquipmentAdapter;
    List<GetEquipmentData> getEquipmentDataList = new ArrayList<>();
    List<WorkFlow> workFlowList = new ArrayList<>();

    private DialogAttendapprovBinding dialogAttendapprovBinding;

    private DialogSaveequiptmentBinding dialogSaveequiptmentBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEquipmentApprovalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        binding.equapprrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.equapprrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
     //   getmember(token);
        GetAllAttend(token);
        loadmemberlist();
        return view;


    }

    public void loadmemberlist()
    {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("member", null);
        //    Log.e("Tag", "" + json.toString());
//        Toast.makeText(getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<MemberResponse>>() {}.getType();
        memberResponses = gson.fromJson(json, type);

        if (memberResponses == null)
        {

            memberResponses = new ArrayList<>();
        }
        // setspiner(memberResponses);
        //String array to store all the book names
        String[] items = new String[memberResponses.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<memberResponses.size(); i++){
            //Storing names to string array
            items[i] = memberResponses.get(i).getName();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        binding.spinnerlist.setAdapter(adapter);

        binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (iCurrentSelection == position) {
                    return;
                } else {
                    Integer idd = memberResponses.get(position).getId();
                    Log.e("Tag", "idd=" + idd.toString());
                    sname = parent.getItemAtPosition(position).toString();
                    Log.e("Tag", "member=" + sname.toString());
                    GetAllAttendByID(token, idd);
                    iCurrentSelection = 0;

                }
                // Your code here
                iCurrentSelection = position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

//        for (int i = 0; i<memberResponseList.size(); i++ )
//        {
//            memberResponse = memberResponseList.get(i);
//            String name = memberResponse.getName();
//            Toast.makeText(EditprofActivity.this, ""+name, Toast.LENGTH_SHORT).show();
//
//        }
    }


//    public void getmember(final String access_token) {
//        try {
//
//            final CustomProgressDialogue dialog;
//            dialog = new CustomProgressDialogue(getActivity());
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//            Call<Members> membersCall = ApiHandler.getApiInterface().getlistMember("Bearer " + access_token);
//            Log.e("Tag", "response" + membersCall.toString());
//
//            membersCall.enqueue(new Callback<Members>() {
//                @Override
//                public void onResponse(Call<Members> membersCall1, Response<Members> response) {
//
//                    try {
//                        if (response.isSuccessful()) {
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200) {
//                                memberResponses = response.body().getData().getResponse();
//
//                                Log.e("Tag", "respone" + memberResponses.toString());
//
//                                // setspiner(memberResponses);
//                                //String array to store all the book names
//                                String[] items = new String[memberResponses.size()];
//
//                                //Traversing through the whole list to get all the names
//                                for (int i = 0; i < memberResponses.size(); i++) {
//                                    //Storing names to string array
//                                    items[i] = memberResponses.get(i).getName();
//                                }
//
//                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//                                ArrayAdapter<String> adapter;
//                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
//                                //setting adapter to spinner
//                                binding.spinnerlist.setAdapter(adapter);
//
//
//                                dialog.dismiss();
//
//
//                                //   Toast.makeText(AddLeave.this, ""+memberResponses, Toast.LENGTH_SHORT).show();
//                            }
//
//                            binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    if (iCurrentSelection == position) {
//                                        return;
//                                    } else {
//                                        Integer idd = memberResponses.get(position).getId();
//                                        Log.e("Tag", "idd=" + idd.toString());
//                                        sname = parent.getItemAtPosition(position).toString();
//                                        Log.e("Tag", "member=" + sname.toString());
//                                        GetAllAttendByID(token, idd);
//                                        iCurrentSelection = 0;
//
//                                    }
//                                    // Your code here
//                                    iCurrentSelection = position;
//
//
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
////                                        Toast.makeText(getActivity(), "no selected", Toast.LENGTH_SHORT).show();
////                                        Log.e("Tag", "memberno=" + sname.toString());
//                                    // sometimes you need nothing here
//                                }
//                            });
//
//
//                        } else {
//
//                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            dialog.dismiss();
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        try {
//                            Log.e("Tag", "error=" + e.toString());
//
//
//                        } catch (Resources.NotFoundException e1) {
//                            e1.printStackTrace();
//                        }
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Members> call, Throwable t) {
//                    try {
//                        Log.e("Tag", "error" + t.toString());
//
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//            });
//
//
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    private void GetAllAttend(final String access_token)
    {
        final CustomProgressDialogue dialog;
        dialog = new CustomProgressDialogue(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        JSONObject jsonobject = new JSONObject();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, Config.ALL_EQUIPT, jsonobject, new com.android.volley.Response.Listener<JSONObject>() {
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

                    if (status == 200) {

                        /// Toast.makeText(getActivity(), "" + jsonArrays.getString("message"), Toast.LENGTH_SHORT).show();
                        getEquipmentDataList.clear();

                        JSONObject jsonObject = jObj.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("leaves");
                        int totalrecord=jsonObject.getInt("recordsTotal");
//
                        for (int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject resultsObj = jsonArray.getJSONObject(n);
                            JSONArray jArray1 = resultsObj.getJSONArray("work_flow");
                            JSONObject obj = jArray1.getJSONObject(0);
                            workFlowList.add(new com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow(obj.getInt("Type"),obj.getBoolean("Status"),obj.getString("DisplayName"), obj.getString("ProcessName")));
                            getEquipmentDataList.add(new GetEquipmentData(resultsObj.getInt("id"),workFlowList,resultsObj.getInt("status"),resultsObj.getString("item_name"),resultsObj.getString("comment")));

                        }
                        approvalEquipmentAdapter= new ApprovalEquipmentAdapter(getActivity(), getEquipmentDataList);
                        binding.equapprrecycler.setAdapter(approvalEquipmentAdapter);


                        if (totalrecord==0)
                        {
                            binding.equapprrecycler.setVisibility(View.GONE);
                            binding.txtno.setVisibility(View.VISIBLE);
                            dialog.dismiss();

                        } else {
                            binding.equapprrecycler.setVisibility(View.VISIBLE);
                            binding.txtno.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    }
                    else if (status==400)
                    {

                        binding.equapprrecycler.setVisibility(View.GONE);
                        binding.txtno.setVisibility(View.VISIBLE);
                        dialog.dismiss();


                    }
                    else if (status==401)
                    {

                        binding.equapprrecycler.setVisibility(View.GONE);
                        binding.txtno.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        sessionManager.logoutUser();
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);

                    }
                    approvalEquipmentAdapter.setOnItemClickListener(new ApprovalEquipmentAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, GetEquipmentData obj, int position)
                        {
                            GetEquipmentData getEquipmentData=new GetEquipmentData();
                            WorkFlow workFlow = new WorkFlow();

                            getEquipmentData = getEquipmentDataList.get(position);
                            //  Log.e("Tag", "work" + response1.toString());
                            Integer id = obj.getId();
                            int status=obj.getStatus();

                            workFlowList = getEquipmentData.getWorkFlow();

//                            for (int m =0 ; m<workFlowList.size();m++)
//                            {
                            workFlow = workFlowList.get(position);

                            showCustomDialog(workFlow,id,status);

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


    // end


//  start   Base on Id


    private void GetAllAttendByID(final String access_token, Integer empid) {
        final CustomProgressDialogue dialog;
        dialog = new CustomProgressDialogue(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        JSONObject jsonobject = new JSONObject();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,"https://hris.khazanapk.com/api/admin/getEmployeeEquipmentRequest?employee_id="+empid+"&sort=&order=Desc&search=null", jsonobject, new com.android.volley.Response.Listener<JSONObject>() {
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

                    if (status == 200) {
                        getEquipmentDataList.clear();

                        JSONObject jsonObject = jObj.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("leaves");
                        int totalrecord=jsonObject.getInt("recordsTotal");
//
                        for (int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject resultsObj = jsonArray.getJSONObject(n);
                            JSONArray jArray1 = resultsObj.getJSONArray("work_flow");
                            JSONObject obj = jArray1.getJSONObject(0);
                            workFlowList.add(new com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow(obj.getInt("Type"),obj.getBoolean("Status"),obj.getString("DisplayName"), obj.getString("ProcessName")));
                            getEquipmentDataList.add(new GetEquipmentData(resultsObj.getInt("id"),workFlowList,resultsObj.getInt("status"),resultsObj.getString("item_name"),resultsObj.getString("comment")));

                        }
                        approvalEquipmentAdapter= new ApprovalEquipmentAdapter(getActivity(), getEquipmentDataList);
                        binding.equapprrecycler.setAdapter(approvalEquipmentAdapter);


                        approvalEquipmentAdapter= new ApprovalEquipmentAdapter(getActivity(), getEquipmentDataList);
                        binding.equapprrecycler.setAdapter(approvalEquipmentAdapter);


                        if (totalrecord==0)
                        {
                            binding.equapprrecycler.setVisibility(View.GONE);
                            binding.txtno.setVisibility(View.VISIBLE);
                            dialog.dismiss();

                        } else {
                            binding.equapprrecycler.setVisibility(View.VISIBLE);
                            binding.txtno.setVisibility(View.GONE);
                            dialog.dismiss();
                        }

                    }

                     else {

                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();


                    }


                    approvalEquipmentAdapter.setOnItemClickListener(new ApprovalEquipmentAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, GetEquipmentData obj, int position)
                        {
                            GetEquipmentData getEquipmentData=new GetEquipmentData();
                            com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow workFlow = new com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow();

                            Integer id = obj.getId();
                            int status=obj.getStatus();
                            getEquipmentData = getEquipmentDataList.get(position);
                            //  Log.e("Tag", "work" + response1.toString());

                            workFlowList = getEquipmentData.getWorkFlow();

//                            for (int m =0 ; m<workFlowList.size();m++)
//                            {
                            workFlow = workFlowList.get(position);

                            showCustomDialog(workFlow,id,status);

                        }
                    });

                }


                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }

        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myTag", error.toString());
                        Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
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

    //
    private void showCustomDialog(com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow workFlow, int id, int sstatus) {

        dialogAttendapprovBinding = DialogAttendapprovBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAttendapprovBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Integer type = workFlow.getType();

        if (type==0)
        {
            dialogAttendapprovBinding.apprtask.setVisibility(View.GONE);

        }
        else
        {
            dialogAttendapprovBinding.apprtask.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprtask.setText(Integer.toString(type));
        }
        String procname = workFlow.getProcessName();

        if (procname==null)
        {
            dialogAttendapprovBinding.apprproname.setVisibility(View.GONE);

        }
        else
        {
            dialogAttendapprovBinding.apprproname.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprproname.setText(procname);
        }
        String displayName = workFlow.getDisplayName();
        if (displayName==null)
        {
            dialogAttendapprovBinding.apprdate.setVisibility(View.GONE);

        }
        else
        {
            dialogAttendapprovBinding.apprdate.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprdate.setText(displayName);
        }
        String comments = workFlow.getComments();

        if (comments==null)
        {
            dialogAttendapprovBinding.apprdisplyname.setVisibility(View.GONE);

        }
        else
        {
            dialogAttendapprovBinding.apprdisplyname.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprdisplyname.setText(comments);
        }


        int idd = id;
        int sta=sstatus;

        Boolean status = workFlow.getStatus();
        if (status && sta==1)
        {

            dialogAttendapprovBinding.approved.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //  AddReq(token);
                    dialog.dismiss();

                }
            });
        }
        else if (!status && sta==2)
        {

            dialogAttendapprovBinding.rejected.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.rejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  AddReq(token);

                    dialog.dismiss();

                }
            });
        }
        else
        {

            dialogAttendapprovBinding.approve.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.reject.setVisibility(View.VISIBLE);

            dialogAttendapprovBinding.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //  AddReq(token);
                    //  Toast.makeText(getActivity(), "Please wait", Toast.LENGTH_SHORT).show();
                    showCustom(idd,workFlow);
                    dialog.dismiss();

                }
            });

            dialogAttendapprovBinding.reject.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();

                    showCustomReject(idd,workFlow);
                    dialog.dismiss();

                }
            });

        }
        dialogAttendapprovBinding.imgclose.setOnClickListener(new View.OnClickListener() {
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


    private void showCustom(int sid, com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow workFlow)
    {



        dialogSaveequiptmentBinding = DialogSaveequiptmentBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogSaveequiptmentBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


//
//       int iid=workFlow.getUserId();
//       Toast.makeText(getActivity(), ""+iid, Toast.LENGTH_SHORT).show();
////
        dialogSaveequiptmentBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(dialogSaveequiptmentBinding.etdescrpt.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogSaveequiptmentBinding.etdescrpt.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    model model =new model();

                    int ssid =sid;
                    addEquiptReq.setId(ssid);

                    model.setType(1);
                    model.setStatus(true);
                    model.setTimeStamp(null);
                    model.setDisplayName("EquipmentApprovalProcess");
                    model.setProcessName("EquipmentApprovalProcess");
//        int use=workFlow.getUserId();
//        Toast.makeText(getActivity(), ""+use, Toast.LENGTH_SHORT).show();
//
                    model.setIsAddedtoTree(false);
                    model.setProcessStatus(false);
                    model.setIsCompletelyRejected(false);
                    model.setComments(dialogSaveequiptmentBinding.etdescrpt.getText().toString().trim());

                    addEquiptReq.setModel(model);


                    addexpr(token,addEquiptReq);
                    dialog.dismiss();

                }


            }
        });

        dialogSaveequiptmentBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void addexpr(String token, AddEquiptReq addEquiptReq) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
//            Log.d("Tag",""+addQualification.toString());
            Call<EditProf> editProfCall = ApiHandler.getApiInterface().addequiptmt("Bearer " + token, addEquiptReq);
            editProfCall.enqueue(new Callback<EditProf>()
            {

                @Override
                public void onResponse(Call<EditProf> editProfCall1, Response<EditProf> response) {

                    try {
                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200)
                            {

                                GetAllAttend(token);
                                dialog.dismiss();


                            }
                            else if (status == 400)
                            {
                                String msg =response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
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
                            Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                            Toast.makeText(getActivity(), "Your session is expired please login again!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            sessionManager.logoutUser();
                            Intent intent = new Intent(getActivity(), SignIn.class);
                            startActivity(intent);


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
                public void onFailure(Call<EditProf> call, Throwable t) {
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



    private void showCustomReject(int id, com.khazana.hrm.Model.Approval.EquipmentApproval.WorkFlow workFlow)
    {



        dialogSaveequiptmentBinding = DialogSaveequiptmentBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogSaveequiptmentBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


//
//       int iid=workFlow.getUserId();
//       Toast.makeText(getActivity(), ""+iid, Toast.LENGTH_SHORT).show();
////
        dialogSaveequiptmentBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(dialogSaveequiptmentBinding.etdescrpt.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogSaveequiptmentBinding.etdescrpt.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    model model =new model();

                    int sid =id;
                    addEquiptReq.setId(sid);

                    model.setType(1);
                    model.setStatus(false);
                    model.setTimeStamp(null);
                    model.setDisplayName("EquipmentApprovalProcess");
                    model.setProcessName("EquipmentApprovalProcess");
//        int use=workFlow.getUserId();
//        Toast.makeText(getActivity(), ""+use, Toast.LENGTH_SHORT).show();
//
                    model.setIsAddedtoTree(false);
                    model.setProcessStatus(false);
                    model.setIsCompletelyRejected(false);
                    model.setComments(dialogSaveequiptmentBinding.etdescrpt.getText().toString().trim());

                    addEquiptReq.setModel(model);


                    addrej(token,addEquiptReq);
                    dialog.dismiss();

                }


            }
        });

        dialogSaveequiptmentBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void addrej(String token, AddEquiptReq addEquiptReq) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
//            Log.d("Tag",""+addQualification.toString());
            int sid=addEquiptReq.getModel().getRoleId();

         //   Toast.makeText(getActivity(), ""+sid, Toast.LENGTH_SHORT).show();

            Call<EditProf> editProfCall = ApiHandler.getApiInterface().rejectequiptmt("Bearer " + token, addEquiptReq);
            editProfCall.enqueue(new Callback<EditProf>()
            {

                @Override
                public void onResponse(Call<EditProf> editProfCall1, Response<EditProf> response) {

                    try {
                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200)
                            {

                                GetAllAttend(token);
                                dialog.dismiss();


                            }
                            else if (status == 400)
                            {
                                String msg =response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
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
//                            Toast.makeText(getActivity(), "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            sessionManager.logoutUser();
                            Intent intent = new Intent(getActivity(), SignIn.class);
                            startActivity(intent);


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
                public void onFailure(Call<EditProf> call, Throwable t) {
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


}