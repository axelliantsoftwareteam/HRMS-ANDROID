package com.khazana.hrm.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.akshay.library.CurveBottomBar;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khazana.hrm.Adapter.SideMenu.SideMenuAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.ClientInfo.UserSkills.GetAllSkill;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.SideMenu.Getsidemenu;
import com.khazana.hrm.Model.SideMenu.Menu;
import com.khazana.hrm.Model.SideMenu.SubMenu;
import com.khazana.hrm.R;
import com.khazana.hrm.Utility.Config;
import com.khazana.hrm.Utility.SessionManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.khazana.hrm.databinding.ActivityMoreBinding;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;

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

public class More extends AppCompatActivity {


    private ActivityMoreBinding binding;

    //new changes
    private RecyclerView.LayoutManager mLayoutManager;
    SideMenuAdapter sideMenuAdapter;


    List<Menu> getscreenlist = new ArrayList<>();
    List<SubMenu> subMenuList = new ArrayList<>();
    List<GetAllSkill> getAllSkillList = new ArrayList<>();
    private View parent_view;


    String token;


    // close new changes


    Context context;
    SessionManager session;
    final static String AUTHORITY = "https://login.microsoftonline.com/common";
    private ISingleAccountPublicClientApplication mSingleAccountApp;


    private CurveBottomBar cbb;

    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {

            case R.id.menu_more:
//                  item.getIcon().setTint(ContextCompat.getColor(UserProfActivity.this, R.color.white));
//                Intent intentt = new Intent(More.this, More.class);
//                startActivity(intentt);
//                finish();
                return true;
            case R.id.menu_me:
                Intent intent = new Intent(More.this, UserProfActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //  transparentStatusAndNavigation();

        isNetworkConnectionAvailable();

        session = new SessionManager(More.this);


        binding.todayrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(More.this, LinearLayoutManager.VERTICAL, false);

        binding.todayrecycler.setLayoutManager(mLayoutManager);

        session = new SessionManager(More.this);
        token = session.getToken();
        ///   Toast.makeText(More.this, "Today", Toast.LENGTH_SHORT).show();
        //  GetAllTask(token);getallskill


        cbb = findViewById(R.id.nav_view);
        cbb.setBottomBarColor(getResources().getColor(R.color.colorApp));
        cbb.setCurveRadius(90);
        cbb.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        getallskill(token);



        PublicClientApplication.createSingleAccountPublicClientApplication(getApplicationContext(),
                R.raw.auth_config_single_account, new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        mSingleAccountApp = application;
                        loadAccount();
                    }

                    @Override
                    public void onError(MsalException exception) {
                        //  displayError(exception);
                    }
                });

        binding.leaveBalnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, LeaveBalanceActivity.class);
                startActivity(intent);
                finish();


            }
        });

        binding.employmang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, EmplyManagActivity.class);
                startActivity(intent);
                finish();

            }
        });
        binding.payroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, PayrollActivity.class);
                startActivity(intent);
                finish();

            }
        });

        binding.txtedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, EditprofActivity.class);
                startActivity(intent);
                finish();

            }
        });


        binding.basicsetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, BasicSetupActivity.class);
                startActivity(intent);
                finish();


            }
        });
//        binding.me.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(More.this, UserProfActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

//        binding.icFlthome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(More.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
//        binding.more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(More.this, More.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
//        binding.icMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(More.this, UserProfActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
        binding.txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSingleAccountApp == null)
                {

                    session.logoutUser();
                   // Toast.makeText(More.this, "logout simple", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(More.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
                else {


                    mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {

                        @Override
                        public void onSignOut() {
                            session.logoutUser();
                           // Toast.makeText(More.this, "logout mic", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(More.this, SignIn.class);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onError(@NonNull MsalException exception) {
                            displayError(exception);

                        }
                    });
                }
                session.logoutUser();
              //  Toast.makeText(More.this, "logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(More.this, SignIn.class);
                startActivity(intent);
                finish();

            }
        });

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(More.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        binding.txtresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, ResumeActivity.class);
                startActivity(intent);
                finish();


            }
        });

        binding.txtapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(More.this, ApprovalActivity.class);
                startActivity(i);
                finish();
            }
        });

        loadmemberlist();


    }

    public void loadmemberlist() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("sidemenu", null);
        //    Log.e("Tag", "" + json.toString());
//        Toast.makeText(getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<Menu>>() {
        }.getType();
        getscreenlist = gson.fromJson(json, type);

        if (getscreenlist == null) {

            getscreenlist = new ArrayList<>();
        }
        sideMenuAdapter = new SideMenuAdapter(More.this, getscreenlist);
        binding.todayrecycler.setAdapter(sideMenuAdapter);

        // on item list clicked
        sideMenuAdapter.setOnItemClickListener(new SideMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Menu obj, int position) {

                obj = getscreenlist.get(position);
                subMenuList = obj.getSubMenu();
                String scren = obj.getCaption();
                //   Toast.makeText(More.this, ""+scren, Toast.LENGTH_SHORT).show();
                if (scren.equals("Static Data")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("Payroll")) {
                    Intent intent = new Intent(More.this, PayrollActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("Employee Management")) {
                    Intent intent = new Intent(More.this, EmplyManagActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("System Holidays")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("Basic Setup")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("Approvals")) {
                    Intent intent = new Intent(More.this, ApprovalActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("Resume")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Skills")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();

                } else if (scren.equals("Shifts")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Designation")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Buildings")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("EOBI Slabs")) {
                    Intent intent = new Intent(More.this, PayrollActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Department")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Groups")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Workflow")) {
                    Intent intent = new Intent(More.this, EmplyManagActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Employee")) {
                    Intent intent = new Intent(More.this, EmplyManagActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Organogram")) {
                    Intent intent = new Intent(More.this, OrganogramActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Tasks")) {
                    Intent intent = new Intent(More.this, TasksActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Roles")) {
                    Intent intent = new Intent(More.this, BasicSetupActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Attendance Approval")) {
                    Intent intent = new Intent(More.this, ApprovalActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Leave Approval")) {
                    Intent intent = new Intent(More.this, ApprovalActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("General Request Approval")) {
                    Intent intent = new Intent(More.this, ApprovalActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Equipment Approval")) {
                    Intent intent = new Intent(More.this, ApprovalActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("Attendance")) {
                    Intent intent = new Intent(More.this, AttendanceActivity.class);
                    startActivity(intent);
                    finish();


                }
                else if (scren.equals("Time Rule")) 
                {
                    Toast.makeText(More.this, "Please link with your web url", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(More.this, .class);
//                    startActivity(intent);
//                    finish();


                }
                else if (scren.equals("Calendar")) {
                    Intent intent = new Intent(More.this, MicCalendarActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Evaluation")) {
                    Intent intent = new Intent(More.this, EvalutionActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Requests")) {
                    Intent intent = new Intent(More.this, RequestActivity.class);
                    startActivity(intent);
                    finish();


                } else if (scren.equals("Profile")) {
                    Intent intent = new Intent(More.this, EditprofActivity.class);
                    startActivity(intent);
                    finish();


                }
            }
        });

    }


    //When app comes to the foreground, load existing account to determine if user is signed in
    private void loadAccount() {
        if (mSingleAccountApp == null) {
            return;
        }

        mSingleAccountApp.getCurrentAccountAsync(new ISingleAccountPublicClientApplication.CurrentAccountCallback() {
            @Override
            public void onAccountLoaded(@Nullable IAccount activeAccount) {
                // You can use the account data to update your UI or your app database.
                /// updateUI(activeAccount);
            }

            @Override
            public void onAccountChanged(@Nullable IAccount priorAccount, @Nullable IAccount currentAccount) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    //   performOperationOnSignOut();
                }
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                //displayError(exception);
            }
        });
    }

    private void displayError(@NonNull final Exception exception) {
        session.logoutUser();

        Intent intent = new Intent(More.this, SignIn.class);
        startActivity(intent);
        finish();
    }

    //Navigation transparent
    //start

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //Navigation transparent
    //end


    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(More.this, MainActivity.class);
        startActivity(i);
        finish();

    }

    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void GetAllTask(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(More.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<Getsidemenu> getsidemenuCall = ApiHandler.getApiInterface().getsidemenu("Bearer " + access_token);
            getsidemenuCall.enqueue(new Callback<Getsidemenu>() {
                @Override
                public void onResponse(Call<Getsidemenu> getsidemenuCall1, Response<Getsidemenu> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                getscreenlist = response.body().getData().getMenu();
                                if (getscreenlist.size() == 0) {
                                    dialog.dismiss();
                                    binding.todayrecycler.setVisibility(View.GONE);
                                } else if (getscreenlist != null) {

                                    getscreenlist.subList(2, getscreenlist.size() - 1);


                                    sideMenuAdapter = new SideMenuAdapter(More.this, getscreenlist);
                                    binding.todayrecycler.setAdapter(sideMenuAdapter);
//                                    binding.todayrecycler.setVisibility(View.VISIBLE);
//                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    // on item list clicked
                                    sideMenuAdapter.setOnItemClickListener(new SideMenuAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, Menu obj, int position) {
                                            Menu screen = new Menu();
                                            screen = getscreenlist.get(position);
                                            String scren = screen.getCaption();


//                                            GetTodayData getTodayData =new GetTodayData();
//                                            getTodayData = getscreenlist.get(position);
////                                            showCustomDialog(getTodayData);

                                            //  Toast.makeText(More.this, "Screen="+scren, Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }


                            }

                        } else {
                            Toast.makeText(More.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<Getsidemenu> call, Throwable t) {
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
    private void getallskill(final String access_token)
    {

        JSONObject jsonobject = new JSONObject();

        RequestQueue queue = Volley.newRequestQueue(More.this);
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, Config.ALL_SKILL, jsonobject, new com.android.volley.Response.Listener<JSONObject>() {
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

                      //  Toast.makeText(More.this, "" + jsonArrays.getString("message"), Toast.LENGTH_SHORT).show();
                        getAllSkillList.clear();

                        JSONObject jsonObject = jObj.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
//
                        for (int n = 0; n < jsonArray.length(); n++)
                        {
                            JSONObject resultsObj = jsonArray.getJSONObject(n);

                            getAllSkillList.add(new GetAllSkill(resultsObj.getInt("id"), resultsObj.getString("name")));

                        }
                        SharedPreferences sharedPreferences =More.this.getSharedPreferences("shared preferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(getAllSkillList);
                        editor.putString("userskills", json);
                        editor.apply();
                    }
                    else if (status ==401)
                    {
                        Toast.makeText(More.this, "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                        session.logoutUser();
                        Intent intent = new Intent(More.this, SignIn.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(More.this, ""+jsonArrays.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(More.this, SignIn.class);
                        startActivity(intent);
                        finish();

                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myTag", error.toString());
                      //  Toast.makeText(More.this, "Your session is expired please login again", Toast.LENGTH_SHORT).show();


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


}