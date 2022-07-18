package com.example.hrm.Fragment.BasicSetup;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hrm.Adapter.Approval.ApprovalAttendanceAdapter;
import com.example.hrm.Adapter.StaticData.StaticDataAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Approval.AttendApproval.AttendApproval;
import com.example.hrm.Model.Approval.AttendApproval.GetAttendApprolModel;
import com.example.hrm.Model.Approval.AttendApproval.WorkFlow;
import com.example.hrm.Model.StaticDataModel.Data;
import com.example.hrm.Model.StaticDataModel.GetDataMember.GetMemberList;
import com.example.hrm.Model.StaticDataModel.GetDataMember.GetStDataMemberModel;
import com.example.hrm.Model.StaticDataModel.GetStaticDataModel;
import com.example.hrm.R;
import com.example.hrm.UI.EditprofActivity;
import com.example.hrm.UI.More;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAdddispmemberstdtBinding;
import com.example.hrm.databinding.DialogAddnewvaluestdtBinding;
import com.example.hrm.databinding.DialogAttendapprovBinding;
import com.example.hrm.databinding.DialogEditstaticdataBinding;
import com.example.hrm.databinding.FragmentStaticDataBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StaticDataFragment extends Fragment {



    private FragmentStaticDataBinding binding;

    private DialogEditstaticdataBinding dialogEditstaticdataBinding;

    private DialogAddnewvaluestdtBinding dialogAddnewvaluestdtBinding;
    private DialogAdddispmemberstdtBinding dialogAdddispmemberstdtBinding;

    private RecyclerView.LayoutManager mLayoutManager;
    StaticDataAdapter staticDataAdapter;


    List<String> data = new ArrayList<>();
    List<GetMemberList> getMemberLists = new ArrayList<>();
    GetMemberList getMemberList= new GetMemberList();

    SessionManager sessionManager;
    String token, sname;
    int iCurrentSelection = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStaticDataBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.staticdatarecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.staticdatarecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
        getmember(token);

        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                showAddnewValueDialog(sname);

            }
        });
        binding.btnaddstdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                showAddnewDialog();

            }
        });

        return view;


    }


    public void getmember(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<GetStaticDataModel> getstaticdata = ApiHandler.getApiInterface().getstaticdata("Bearer " + access_token);
            Log.e("Tag", "response" + getstaticdata.toString());

            getstaticdata.enqueue(new Callback<GetStaticDataModel>() {
                @Override
                public void onResponse(Call<GetStaticDataModel> getStaticDataModelCall, Response<GetStaticDataModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                data = response.body().getData().getResponse();

                                Log.e("Tag", "respone" + data.toString());

                                // setspiner(data);
                                //String array to store all the book names
                                String[] items = new String[data.size()];

                                //Traversing through the whole list to get all the names
                                for (int i = 0; i < data.size(); i++) {
                                    //Storing names to string array
                                    items[i] = data.get(i).toString();
                                }

                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                                //setting adapter to spinner
                                binding.spinnerlist.setAdapter(adapter);


                                dialog.dismiss();


                                //   Toast.makeText(AddLeave.this, ""+data, Toast.LENGTH_SHORT).show();
                            }

                            binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (iCurrentSelection == position) {
                                        return;
                                    } else {

                                        sname = parent.getItemAtPosition(position).toString();
                                        Log.e("Tag", "member=" + sname.toString());
                                        GetAllAttendByID(token, sname);
                                        iCurrentSelection = 0;

                                    }
                                    // Your code here
                                    iCurrentSelection = position;


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
//                                        Toast.makeText(getActivity(), "no selected", Toast.LENGTH_SHORT).show();
//                                        Log.e("Tag", "memberno=" + sname.toString());
                                    // sometimes you need nothing here
                                }
                            });


                        } else {

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
                public void onFailure(Call<GetStaticDataModel> call, Throwable t) {
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

    private void GetAllAttendByID(final String access_token, String member) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetStDataMemberModel> getStDataMemberModelCall = ApiHandler.getApiInterface().getSelectMember("Bearer " + access_token, member);
            getStDataMemberModelCall.enqueue(new Callback<GetStDataMemberModel>() {
                @Override
                public void onResponse(Call<GetStDataMemberModel> getStDataMemberModelCall1, Response<GetStDataMemberModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getMemberLists = response.body().getData().getResponse();

                                if (getMemberLists.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.lymember.setVisibility(View.GONE);

                                } else if (getMemberLists != null) {

                                    staticDataAdapter = new StaticDataAdapter(getActivity(), getMemberLists);
                                    binding.staticdatarecycler.setAdapter(staticDataAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.lymember.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    staticDataAdapter.setOnItemClickListener(new StaticDataAdapter.OnItemClickListener(){
                                        @Override
                                        public void onItemClick(View view, GetMemberList obj, int position) {


                                            GetMemberList getMemberList = getMemberLists.get(position);
                                            String name =getMemberList.getDisplayMember();
                                            String val=getMemberList.getValue();


                                            // Log.e("Tag", "work" + name.toString());

                                            showCustomDialog(name,val);

                                        }
                                    });
                                }

                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<GetStDataMemberModel> call, Throwable t) {
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

    private void showCustomDialog(String name,String val) {

        dialogEditstaticdataBinding = DialogEditstaticdataBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditstaticdataBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogEditstaticdataBinding.txtDisplayname.setText(name);
        dialogEditstaticdataBinding.etvalue.setText(val);
        dialogEditstaticdataBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogEditstaticdataBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

    private void showAddnewValueDialog(String name)
    {

        dialogAddnewvaluestdtBinding = DialogAddnewvaluestdtBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddnewvaluestdtBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogAddnewvaluestdtBinding.txtDisplayname.setText(name);
     //   dialogAddnewvaluestdtBinding.etvalue.setText(name);

        dialogAddnewvaluestdtBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogAddnewvaluestdtBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

    private void showAddnewDialog()
    {

        dialogAdddispmemberstdtBinding = DialogAdddispmemberstdtBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAdddispmemberstdtBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAdddispmemberstdtBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogAdddispmemberstdtBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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





}