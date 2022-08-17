package com.example.hrm.Fragment.Evaluation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrm.Adapter.Evaluation.EvalutionAdapter;
import com.example.hrm.Adapter.Evaluation.QuestionAdapter;
import com.example.hrm.Adapter.Evaluation.QuestionDetailsAdapter;
import com.example.hrm.Adapter.Evaluation.SelfEvalutionAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Evalution.Detail;
import com.example.hrm.Model.Evalution.GetEvaluation;
import com.example.hrm.Model.Evalution.GetEvaluationData;
import com.example.hrm.Model.Evalution.Questions.GetEvalQuesData;
import com.example.hrm.Model.Evalution.Questions.GetEvalQuestion;
import com.example.hrm.Model.UserEvaluations.post.AddUserEvaluations;
import com.example.hrm.Model.UserEvaluations.post.Question;
import com.example.hrm.Model.UserEvaluations.response.UserEvaluationsResponse;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogDetailsevalquestionBinding;
import com.example.hrm.databinding.DialogEvalquestionBinding;
import com.example.hrm.databinding.FragmentEvaluationBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EvaluationFragment extends Fragment {


    private FragmentEvaluationBinding binding;


    private DialogEvalquestionBinding dialogEvalquestionBinding;

    private  DialogDetailsevalquestionBinding dialogDetailsevalquestionBinding;
    boolean status = false;



    private RecyclerView.LayoutManager mLayoutManager;
    EvalutionAdapter evalutionAdapter;
    SelfEvalutionAdapter selfEvalutionAdapter;

    private RecyclerView.LayoutManager mqLayoutManager;
    private RecyclerView.LayoutManager mqdLayoutManager;
    QuestionAdapter questionAdapter;
    QuestionDetailsAdapter questionDetailsAdapter;

    SessionManager sessionManager;
    String token, sname;

    List<GetEvaluationData> getevaluationlist = new ArrayList<>();


    List<Detail> detailList = new ArrayList<>();

    List<GetEvalQuesData> getEvalQuestionList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEvaluationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.evalurecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.evalurecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getEavulation(token);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        binding.mucheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mucheck.isChecked())
                {

                    getEavulationtrue(token);
                    // Toast.makeText(getActivity(), "" + status, Toast.LENGTH_SHORT).show();

                }
                else
                {
                    getEavulation(token);
                    // Toast.makeText(getActivity(), "" + status, Toast.LENGTH_SHORT).show();


                }

            }
        });


        return view;


    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String ItemName = intent.getStringExtra("rating");
            String qty = intent.getStringExtra("comment");
            Toast.makeText(getActivity(),ItemName +" "+qty ,Toast.LENGTH_SHORT).show();
        }
    };

    private void getEavulationtrue(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEvaluation> getEvalutionCall = ApiHandler.getApiInterface().getEvalution("Bearer " + access_token, true);
            getEvalutionCall.enqueue(new Callback<GetEvaluation>() {
                @Override
                public void onResponse(Call<GetEvaluation> getEvalutionCall1, Response<GetEvaluation> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                getevaluationlist = response.body().getData().getResponse();

                                if (getevaluationlist.size() == 0)
                                {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.evalurecycler.setVisibility(View.GONE);


                                } else if (getevaluationlist != null)
                                {

                                    selfEvalutionAdapter = new SelfEvalutionAdapter(getActivity(), getevaluationlist);
                                    binding.evalurecycler.setAdapter(selfEvalutionAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.evalurecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

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
                public void onFailure(Call<GetEvaluation> call, Throwable t) {
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

    private void getEavulation(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEvaluation> getEvalutionCall = ApiHandler.getApiInterface().getEvalution("Bearer " + access_token, status);
            getEvalutionCall.enqueue(new Callback<GetEvaluation>() {
                @Override
                public void onResponse(Call<GetEvaluation> getEvalutionCall1, Response<GetEvaluation> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                getevaluationlist = response.body().getData().getResponse();

                                if (getevaluationlist.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.evalurecycler.setVisibility(View.GONE);

                                } else if (getevaluationlist != null) {

                                    evalutionAdapter = new EvalutionAdapter(getActivity(), getevaluationlist);
                                    binding.evalurecycler.setAdapter(evalutionAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.evalurecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                }

                                evalutionAdapter.setOnItemClickListener(new EvalutionAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, GetEvaluationData obj, int position) {

                                        GetEvaluationData getEvaluationData = new GetEvaluationData();
                                        getEvaluationData = getevaluationlist.get(position);
                                        detailList = getEvaluationData.getDetails();

//                                        for(int i =0 ; i<detailList.size();i++)
//                                        {
//                                            String name =detailList.get(i).getComments();
//                                            Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show();
//
//                                        }



                                        boolean status = getEvaluationData.getIsCompleted();
                                        if (status == false)
                                        {
                                            showEditDialog(position);
                                        }
                                        else
                                        {

                                            if(detailList.size()==0)
                                            {
                                                Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();

                                            }
                                            else
                                            {
                                                ShowDetailsDialog(detailList,position);
                                            }



                                           // Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });

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
                public void onFailure(Call<GetEvaluation> call, Throwable t) {
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

    private void showEditDialog(int position) {

        dialogEvalquestionBinding = DialogEvalquestionBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEvalquestionBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEvalquestionBinding.quesrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mqLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        dialogEvalquestionBinding.quesrecycler.setLayoutManager(mqLayoutManager);

        getQuestion(token);


        dialogEvalquestionBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                // AddReq(token);


//                List<ValueModel> valueModels = new ArrayList<>();
//
//                for (int i =0 ; i<valueModels.size();i++) {
//                    String comment = valueModels.get(i).getComemnt();
//                    Toast.makeText(getActivity(), ""+comment, Toast.LENGTH_SHORT).show();
//                }

                AddUserEvaluations addUserEvaluations = new AddUserEvaluations();
                addUserEvaluations.setEvaluationId(getevaluationlist.get(position).getEvaluationId());
                List<Question> questionList = new ArrayList<>();

                for (int i = 0; i < getEvalQuestionList.size(); i++)
                {
                    View view = dialogEvalquestionBinding.quesrecycler.getChildAt(i);
                    TextView questionTv = (TextView) view.findViewById(R.id.txtques);
                    String questionStr = questionTv.getText().toString().trim();
                    EditText commentsEdt = (EditText) view.findViewById(R.id.et_post);
                    String commentsStr = commentsEdt.getText().toString().trim();

                    AppCompatRatingBar ratingRatingBar = (AppCompatRatingBar) view.findViewById(R.id.rating_bar);
                    Float rating = ratingRatingBar.getRating();

                    Question question = new Question();
                    question.setQuestionId(getEvalQuestionList.get(i).getId());
                    question.setQuestion(questionStr);
                    question.setComments(commentsStr);
                    int ratingInt = rating.intValue();
                    question.setRating(ratingInt);
                    questionList.add(question);
                }


                addUserEvaluations.setQuestions(questionList);


                addUserEvaluations(addUserEvaluations, dialog);




                dialog.dismiss();

            }
        });

        dialogEvalquestionBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void ShowDetailsDialog(List<Detail> detailList, int position)
    {


        dialogDetailsevalquestionBinding = DialogDetailsevalquestionBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogDetailsevalquestionBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //Toast.makeText(getActivity(), ""+detailList, Toast.LENGTH_SHORT).show();


        dialogDetailsevalquestionBinding.quesrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mqdLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        dialogDetailsevalquestionBinding.quesrecycler.setLayoutManager(mqdLayoutManager);


        getquesdetails(detailList);





        dialogDetailsevalquestionBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void getquesdetails(List<Detail> detailList)
    {




//        for(int i =0 ; i<detailList.size();i++)
//        {
//            String name =detailList.get(i).getComments();
//            Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show();
//
//        }
        questionDetailsAdapter = new QuestionDetailsAdapter(getActivity(), detailList);
        dialogDetailsevalquestionBinding.quesrecycler.setAdapter(questionDetailsAdapter);


    }


    private void getQuestion(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEvalQuestion> getEvalutionCall = ApiHandler.getApiInterface().getEvalQuest("Bearer " + access_token);
            getEvalutionCall.enqueue(new Callback<GetEvalQuestion>() {
                @Override
                public void onResponse(Call<GetEvalQuestion> getEvalutionCall1, Response<GetEvalQuestion> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                                getEvalQuestionList = response.body().getData().getResponse();

                                if (getEvalQuestionList.size() == 0) {
                                    dialog.dismiss();
                                    dialogEvalquestionBinding.txtno.setVisibility(View.VISIBLE);
                                    dialogEvalquestionBinding.quesrecycler.setVisibility(View.GONE);

                                } else if (getEvalQuestionList != null) {

                                    questionAdapter = new QuestionAdapter(getActivity(), getEvalQuestionList);
                                    dialogEvalquestionBinding.quesrecycler.setAdapter(questionAdapter);


                                    dialogEvalquestionBinding.txtno.setVisibility(View.GONE);
                                    dialogEvalquestionBinding.quesrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                }


                            }

                        } else {
                            dialogEvalquestionBinding.txtno.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<GetEvalQuestion> call, Throwable t) {
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

    private void addUserEvaluations(AddUserEvaluations addUserEvaluations, Dialog pDialog) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<UserEvaluationsResponse> getEvalutionCall = ApiHandler.getApiInterface().addUserEvaluations("Bearer " + token, addUserEvaluations);
            getEvalutionCall.enqueue(new Callback<UserEvaluationsResponse>() {
                @Override
                public void onResponse(Call<UserEvaluationsResponse> getEvalutionCall1, Response<UserEvaluationsResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                String responseStr = response.body().getData().getResponse();
                                Toast.makeText(getActivity(), "" + responseStr, Toast.LENGTH_SHORT).show();

                                pDialog.dismiss();
                            }

                        } else {
                            dialogEvalquestionBinding.txtno.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<UserEvaluationsResponse> call, Throwable t) {
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