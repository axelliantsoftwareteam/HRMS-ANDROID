package com.example.myapplication.Fragment.Leaves;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Adapter.AllLeavesAdapter;
import com.example.myapplication.Hundler.ApiHandler;
import com.example.myapplication.Model.LeavesModel.AllLeavesModel;
import com.example.myapplication.Model.LeavesModel.Leaves;
import com.example.myapplication.Model.LeavesModel.WorkFlow;
import com.example.myapplication.R;
import com.example.myapplication.Utility.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllLeavesFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    AllLeavesAdapter allLeavesAdapter;

    SessionManager sessionManager;
    String token;


    List<Leaves> leaves= new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_all_leaves, container, false);

        mRecyclerView = view.findViewById(R.id.allleaves_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        GetAllLeaves(token);



        return view;
    }

    private void GetAllLeaves(final String access_token) {
        try {

//            Call<Leaf> leafCall = ApiHandler.getApiInterface().getClock("Bearer " + access_token);

//            Map<String, String> params = new HashMap<String, String>();
//            params.put("start", "0");
//            params.put("limit", "5");
//            params.put("sort", " ");
//            params.put("order", "Desc");
//            params.put("search", "nullli");

            Call<AllLeavesModel> allleaves = ApiHandler.getApiInterface().getAllleaves("Bearer " + access_token);
            allleaves.enqueue(new Callback<AllLeavesModel>() {
                @Override
                public void onResponse(Call<AllLeavesModel> allLeavesModelCall, Response<AllLeavesModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                leaves = response.body().getData().getLeaves();
                                allLeavesAdapter = new AllLeavesAdapter(getActivity(), leaves);
                                mRecyclerView.setAdapter(allLeavesAdapter);

                            }

                        } else {

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
                public void onFailure(Call<AllLeavesModel> call, Throwable t) {
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
}