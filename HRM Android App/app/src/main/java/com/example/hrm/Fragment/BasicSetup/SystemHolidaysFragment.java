
package com.example.hrm.Fragment.BasicSetup;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.hrm.Adapter.StaticData.HolidayAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.BasicSetup.HolidayModel.GetHolidayData;
import com.example.hrm.Model.BasicSetup.HolidayModel.GetHolidayModel;
import com.example.hrm.Model.BasicSetup.HolidayModel.TypeHoilday.GetHoildayListData;
import com.example.hrm.Model.BasicSetup.HolidayModel.TypeHoilday.GetHolidayList;
import com.example.hrm.Model.BasicSetup.Skills.AddSkill.Addskill;
import com.example.hrm.Model.BasicSetup.StaticDataModel.GetDataMember.GetMemberList;
import com.example.hrm.Model.BasicSetup.StaticDataModel.GetDataMember.GetStDataMemberModel;
import com.example.hrm.Model.BasicSetup.StaticDataModel.GetStaticDataModel;
import com.example.hrm.Model.UserProfileModel.Salutation.GetSalutation;
import com.example.hrm.Model.UserProfileModel.Salutation.GetSalutationData;
import com.example.hrm.UI.AddLeave;
import com.example.hrm.UI.BasicSetupActivity;
import com.example.hrm.UI.EditprofActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddholidayBinding;
import com.example.hrm.databinding.DialogEditholidayBinding;
import com.example.hrm.databinding.FragmentSystemHolidaysBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SystemHolidaysFragment extends Fragment {


    private FragmentSystemHolidaysBinding binding;
    private DialogAddholidayBinding dialogAddholidayBinding;
    private DialogEditholidayBinding dialogEditholidayBinding;


    private RecyclerView.LayoutManager mLayoutManager;
    HolidayAdapter holidayAdapter;
    DatePickerDialog picker;
    SessionManager sessionManager;
    String token;
    String ssdate, senddate,stype,sdecrpt;

    List<GetHoildayListData> memberResponses= new ArrayList<>();


    int iCurrentSelection = 0;

    List<GetHoildayListData> holiy = new ArrayList<>();

    List<GetHolidayData> getHolidayDataList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSystemHolidaysBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.holidayrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.holidayrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getholiday(token);

        binding.btnaddstdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddnewDialog(token);

            }
        });


        return view;


    }

    private void getholiday(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetHolidayModel> getHolidayModelCall = ApiHandler.getApiInterface().getHoliday("Bearer " + access_token);
            getHolidayModelCall.enqueue(new Callback<GetHolidayModel>() {
                @Override
                public void onResponse(Call<GetHolidayModel> getHolidayModelCall1, Response<GetHolidayModel> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                getHolidayDataList = response.body().getData().getResponse();

                                if (getHolidayDataList.size() == 0)
                                {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.holidayrecycler.setVisibility(View.GONE);

                                }
                                else if (getHolidayDataList != null) {

                                    holidayAdapter = new HolidayAdapter(getActivity(), getHolidayDataList);
                                    binding.holidayrecycler.setAdapter(holidayAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.holidayrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    holidayAdapter.setOnItemClickListener(new HolidayAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetHolidayData obj, int position) {
                                            GetHolidayData getHolidayData = getHolidayDataList.get(position);
                                            String name = getHolidayData.getType();
                                            String val = getHolidayData.getStartDate();


                                            // Log.e("Tag", "work" + name.toString());

                                            showEditDialog();


                                        }
                                    });


                                }

                            }

                        }
                        else
                        {
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Internal Server Error" , Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<GetHolidayModel> call, Throwable t) {
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


    private void showAddnewDialog(String token)
    {



        dialogAddholidayBinding = DialogAddholidayBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddholidayBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getdialogmember(token);


        dialogAddholidayBinding.etstrtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogAddholidayBinding.etstrtdate.setText(dateString);


                                ssdate = dialogAddholidayBinding.etstrtdate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });
        dialogAddholidayBinding.etenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogAddholidayBinding.etenddate.setText(dateString);


                                senddate = dialogAddholidayBinding.etenddate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });
        dialogAddholidayBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddHoliday(token);
                dialog.dismiss();

            }
        });

        dialogAddholidayBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    public void getdialogmember(final String access_token)
    {
        try
        {

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Call<GetHolidayList> getHolidayListCall = ApiHandler.getApiInterface().getholiy("Bearer " + access_token);
            Log.e("Tag", "response" + getHolidayListCall.toString());

            getHolidayListCall.enqueue(new Callback<GetHolidayList>() {
                @Override
                public void onResponse(Call<GetHolidayList> getSalutationCall1, Response<GetHolidayList> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                memberResponses = response.body().getData().getResponse();

                                Log.e("Tag", "respone" +memberResponses.toString());

                                // setspiner(memberResponses);
                                //String array to store all the book names
                                String[] items = new String[memberResponses.size()];

                                //Traversing through the whole list to get all the names
                                for(int i=0; i<memberResponses.size(); i++){
                                    //Storing names to string array
                                    items[i] = memberResponses.get(i).getValue();
                                }

                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                                //setting adapter to spinner
                                dialogAddholidayBinding.spinnerlist.setAdapter(adapter);


                                progressDialog.dismiss();

                                dialogAddholidayBinding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        Integer idd = memberResponses.get(position).getId();
                                        String sname = parent.getItemAtPosition(position).toString();
                                        Log.e("Tag", "member=" + sname.toString());
                                        Log.e("Tag", "idd=" + idd.toString());


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                        // sometimes you need nothing here
                                    }
                                });
                                //   Toast.makeText(AddLeave.this, ""+memberResponses, Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {

                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
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
                public void onFailure(Call<GetHolidayList> call, Throwable t) {
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

    private void showEditDialog() {

        dialogEditholidayBinding = DialogEditholidayBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditholidayBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditholidayBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogEditholidayBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void AddHoliday(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<GetHolidayModel> getHolidayModelCall = ApiHandler.getApiInterface().addhoilday("Bearer " + token,Apimember());

            getHolidayModelCall.enqueue(new Callback<GetHolidayModel>() {
                @Override
                public void onResponse(Call<GetHolidayModel> getHolidayModelCall1, Response<GetHolidayModel> response ) {

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
                public void onFailure(Call<GetHolidayModel> call, Throwable t)
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
            jsonObj_.put("type", stype);
            jsonObj_.put("start_date", ssdate);
            jsonObj_.put("end_date", senddate);
            jsonObj_.put("description", sdecrpt);


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

}