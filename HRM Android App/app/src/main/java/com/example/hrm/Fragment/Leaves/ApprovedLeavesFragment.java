package com.example.hrm.Fragment.Leaves;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrm.Adapter.Leaves.AllLeavesAdapter;
import com.example.hrm.Adapter.Leaves.ApprovLeavesAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.LeavesModel.AllLeavesModel;
import com.example.hrm.Model.LeavesModel.Leaves;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApprovedLeavesFragment extends Fragment {



    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ApprovLeavesAdapter approvLeavesAdapter;

    SessionManager sessionManager;
    String token;

    TextView noleave;

    List<Leaves> leaves= new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_approvedleaves, container, false);
        noleave = view.findViewById(R.id.txt_no);

        mRecyclerView = view.findViewById(R.id.appr_recycler);
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

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();




//            Call<Leaf> leafCall = ApiHandler.getApiInterface().getClock("Bearer " + access_token);

//            Map<String, String> params = new HashMap<String, String>();
//            params.put("start", "0");
//            params.put("limit", "5");
//            params.put("sort", " ");
//            params.put("order", "Desc");
//            params.put("search", "nullli");

            Call<AllLeavesModel> allleaves = ApiHandler.getApiInterface().getapprov("Bearer " + access_token);
            allleaves.enqueue(new Callback<AllLeavesModel>() {
                @Override
                public void onResponse(Call<AllLeavesModel> allLeavesModelCall, Response<AllLeavesModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                leaves = response.body().getData().getLeaves();
                                if (leaves.size()==0)
                                {
                                    dialog.dismiss();
                                    mRecyclerView.setVisibility(View.GONE);
                                }
                                else if (leaves!=null){

                                    approvLeavesAdapter = new ApprovLeavesAdapter(getActivity(), leaves);
                                    mRecyclerView.setAdapter(approvLeavesAdapter);
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    noleave.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }

                            }

                        } else {
                            noleave.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<AllLeavesModel> call, Throwable t) {
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