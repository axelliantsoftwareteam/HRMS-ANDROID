package com.example.hrm.Fragment.Tasks;

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
import android.widget.Toast;

import com.example.hrm.Adapter.Task.AllTaskAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.GetAllTask.Alltask.GetAlltask;
import com.example.hrm.Model.GetAllTask.Alltask.GetAlltaskData;
import com.example.hrm.Model.GetAllTask.Alltask.MarkComplet.MarkComplet;
import com.example.hrm.UI.TasksActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogTaskdetailsBinding;
import com.example.hrm.databinding.FragmentAlltaskBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlltaskFragment extends Fragment {




    private RecyclerView.LayoutManager mLayoutManager;
    AllTaskAdapter allTaskAdapter;


    List<GetAlltaskData> getAllTaskDataList = new ArrayList<>();

    private FragmentAlltaskBinding binding;
    private DialogTaskdetailsBinding dialogTaskdetailsBinding;
    SessionManager sessionManager;
    String token;
    Integer idd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAlltaskBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.alltaskrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        binding.alltaskrecycler.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
     //   Toast.makeText(getActivity(), "All task", Toast.LENGTH_SHORT).show();
        GetAllTask(token);
        return view;
    }

    private void GetAllTask(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAlltask> getAlltaskCall = ApiHandler.getApiInterface().getAllList("Bearer " + access_token);
            getAlltaskCall.enqueue(new Callback<GetAlltask>()
            {
                @Override
                public void onResponse(Call<GetAlltask> getAlltaskCall1, Response<GetAlltask> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {
                                getAllTaskDataList = response.body().getData().getResponse();
                                if (getAllTaskDataList.size()==0)
                                {
                                    dialog.dismiss();
                                    binding.alltaskrecycler.setVisibility(View.GONE);
                                }
                                else if (getAllTaskDataList !=null)
                                {


                                    allTaskAdapter = new AllTaskAdapter(getActivity(), getAllTaskDataList);
                                    binding.alltaskrecycler.setAdapter(allTaskAdapter);
                                    binding.alltaskrecycler.setVisibility(View.VISIBLE);
//                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    // on item list clicked
                                    allTaskAdapter.setOnItemClickListener(new AllTaskAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetAlltaskData obj, int position)
                                        {
                                            GetAlltaskData getAlltaskData =new GetAlltaskData();
                                            getAlltaskData = getAllTaskDataList.get(position);




                                            showCustomDialog(getAlltaskData);

                                         //   Toast.makeText(getActivity(), "details", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }



                            }

                        } else {
//                            binding.txtno.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<GetAlltask> call, Throwable t) {
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

    private void showCustomDialog(GetAlltaskData getAllTaskData)
    {

        dialogTaskdetailsBinding = DialogTaskdetailsBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogTaskdetailsBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        String name= getAllTaskData.getName();
        String notes= getAllTaskData.getNotes();
        String duedate= getAllTaskData.getDueDate();
        idd = getAllTaskData.getId();

        int status= getAllTaskData.getStatus();
        if (status==1)
        {

            dialogTaskdetailsBinding.txtMark.setVisibility(View.GONE);

            dialogTaskdetailsBinding.btnComplet.setVisibility(View.VISIBLE);
        }
        else {
            dialogTaskdetailsBinding.txtMark.setVisibility(View.VISIBLE);
            dialogTaskdetailsBinding.btnPend.setVisibility(View.VISIBLE);
        }

        int tag= getAllTaskData.getTag();
        if (tag==1)
        {

            dialogTaskdetailsBinding.tasktag.setText("Normal");
        }
        else if (tag==0)
        {

            dialogTaskdetailsBinding.tasktag.setText("Low");
        }
        else if (tag==2)
        {

            dialogTaskdetailsBinding.tasktag.setText("High");
        }


        dialogTaskdetailsBinding.taskname.setText(name);
        dialogTaskdetailsBinding.tasknotes.setText(notes);
        dialogTaskdetailsBinding.txtdate.setText(duedate);

        dialogTaskdetailsBinding.txtMark.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                markdetails(token);
            }

        });



        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void markdetails(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<MarkComplet> markCompletCall = ApiHandler.getApiInterface().addmark("Bearer " + token,Apimember());

            markCompletCall.enqueue(new Callback<MarkComplet>() {
                @Override
                public void onResponse(Call<MarkComplet> markCompletCall1, Response<MarkComplet> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(), TasksActivity.class);
                                startActivity(intent);

                            }
                            else if(status==400)
                            {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),TasksActivity.class);
                                startActivity(intent);

                            }
                            else if (status==500)
                            {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),TasksActivity.class);
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
                public void onFailure(Call<MarkComplet> call, Throwable t)
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
            jsonObj_.put("task_id", idd);


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