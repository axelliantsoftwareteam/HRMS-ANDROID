package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.BasicSetup.StaticDataModel.GetDataMember.GetMemberList;
import com.example.hrm.Model.GetAllTask.Alltask.GetAlltaskData;
import com.example.hrm.Model.LoginModel.UserModel;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Model.MicLogin.TokenModel;
import com.example.hrm.Model.SideMenu.Getsidemenu;
import com.example.hrm.Model.SideMenu.Menu;
import com.example.hrm.Model.UserProfileModel.Buildings.GetBuildingDataList;
import com.example.hrm.Model.UserProfileModel.Buildings.GetBuildingList;
import com.example.hrm.Model.UserProfileModel.Gender.GetGender;
import com.example.hrm.Model.UserProfileModel.Gender.GetGenderData;
import com.example.hrm.Model.UserProfileModel.ReportingTo;
import com.example.hrm.Model.UserProfileModel.Salutation.GetSalutation;
import com.example.hrm.Model.UserProfileModel.Salutation.GetSalutationData;
import com.example.hrm.Model.UserProfileModel.UserData.GetUser;
import com.example.hrm.Model.UserProfileModel.UserData.GetUserData;
import com.example.hrm.Model.UserProfileModel.UserDataModel;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivitySigninBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.microsoft.graph.authentication.IAuthenticationProvider; //Imports the Graph sdk Auth interface
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.http.IHttpRequest;
import com.microsoft.graph.models.extensions.*;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.identity.client.AuthenticationCallback; // Imports MSAL auth methods
import com.microsoft.identity.client.*;
import com.microsoft.identity.client.exception.*;

public class SignIn extends AppCompatActivity  {

    String access_token;
    String semail, spassword;
    SessionManager session;
    private ActivitySigninBinding binding;

    ReportingTo reportingTo;

    List<Object> designation = new ArrayList<>();

    List<MemberResponse> memberResponses= new ArrayList<>();
    List<Menu> getscreenlist = new ArrayList<>();
    List<GetSalutationData> getSalutationDataList= new ArrayList<>();
    List<GetGenderData> getGenderDataList= new ArrayList<>();
    List<GetBuildingDataList> getBuildingDataListList= new ArrayList<>();
    GetUserData getUserData =new GetUserData();

    Pattern pattern_pwd = Pattern.compile("^[a-zA-Z0-9]+$");
    String mictoken;
    private final static String[] SCOPES = {"Files.Read"};
    /* Azure AD v2 Configs */
    final static String AUTHORITY = "https://login.microsoftonline.com/common";
    private ISingleAccountPublicClientApplication mSingleAccountApp;

    private static final String TAG = SignIn.class.getSimpleName();

    /* UI & Debugging Variables */
    Button signInButton;
    Button signOutButton;
    Button callGraphApiInteractiveButton;
    Button callGraphApiSilentButton;
    TextView logTextView;
    TextView currentUserTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        setContentView(R.layout.activity_signin);

        isNetworkConnectionAvailable();


        initializeUI();

        PublicClientApplication.createSingleAccountPublicClientApplication(getApplicationContext(),
                R.raw.auth_config_single_account, new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        mSingleAccountApp = application;
                        loadAccount();
                    }
                    @Override
                    public void onError(MsalException exception) {
                        displayError(exception);
                    }
                });


        session = new SessionManager(SignIn.this);

       binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semail = binding.etemail.getText().toString().trim();
                spassword = binding.etpassword.getText().toString().trim();

                Log.d("userdata", "onClick: " + semail + spassword);

                if (!semail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {

                    if (!spassword.isEmpty() && pattern_pwd.matcher(spassword).matches())
                    {
                        if (validate())
                        {

                            UserLogin();
                        }
                    } else {
//                        Snackbar.make(rl_pwd, "Enter the Valid Password", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(SignIn.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignIn.this, "Your entered email pattern are not match!", Toast.LENGTH_SHORT).show();
                    //  Snackbar.make(ll_lay, "Enter the Valid Email", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

       printHashKey(SignIn.this);


      //  transparentStatusAndNavigation();

//        binding.signmic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SignIn.this, "Functionality Invalid ", Toast.LENGTH_SHORT).show();
//            }
//        });


        if (binding.etemail.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        binding.rely.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            // closeKeyBoard();
            changeStatusBarTextColor(window,true);
        }


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
                updateUI(activeAccount);
            }

            @Override
            public void onAccountChanged(@Nullable IAccount priorAccount, @Nullable IAccount currentAccount) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    performOperationOnSignOut();
                }
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                displayError(exception);
            }
        });
    }


    private void initializeUI(){
        signInButton = findViewById(R.id.btn_signIn);
//        callGraphApiSilentButton = findViewById(R.id.btn_callGraphSilently);
//        callGraphApiInteractiveButton = findViewById(R.id.btn_callGraphInteractively);
        signOutButton = findViewById(R.id.btn_removeAccount);
        //logTextView = findViewById(R.id.txt_log);
        //currentUserTextView = findViewById(R.id.current_user);

        //Sign in user
        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (mSingleAccountApp == null) {
                    return;
                }
                mSingleAccountApp.signIn(SignIn.this, null, SCOPES, getAuthInteractiveCallback());
            }
        });

        //Sign out user
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSingleAccountApp == null){
                    return;
                }
                mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
                    @Override
                    public void onSignOut() {
                        updateUI(null);
                        performOperationOnSignOut();
                    }
                    @Override
                    public void onError(@NonNull MsalException exception){
                        displayError(exception);
                    }
                });
            }
        });
    }
    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated");
                mictoken = authenticationResult.getAccount().getIdToken();

                Log.d(TAG, "ID  "+authenticationResult.getAccount().getIdToken());
                gettoken();


//                Intent intent =new Intent(SignIn.this,Main.class);
//                startActivity(intent);

                /* Update UI */
                updateUI(authenticationResult.getAccount());
                /* call graph */
             //   callGraphAPI(authenticationResult);
            }

            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());
                displayError(exception);
            }
            @Override
            public void onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }



    public void changeStatusBarTextColor(Window window, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags = 0;
            if (isBlack) {
                //Change the text color to dark black
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            else {
                //Change text color to light
                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decor.setSystemUiVisibility(flags);
        }
    }
    private SilentAuthenticationCallback getAuthSilentCallback() {
        return new SilentAuthenticationCallback() {
            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                Log.d(TAG, "Successfully authenticated");
               // callGraphAPI(authenticationResult);
            }
            @Override
            public void onError(MsalException exception) {
                Log.d(TAG, "Authentication failed: " + exception.toString());
                displayError(exception);
            }
        };
    }

    private void updateUI(@Nullable final IAccount account) {
        if (account != null) {
            signInButton.setEnabled(false);
            signOutButton.setEnabled(true);

//            currentUserTextView.setText(account.getUsername());
        } else {
            signInButton.setEnabled(true);
            signOutButton.setEnabled(false);
//            currentUserTextView.setText("");
        }
    }
    private void displayError(@NonNull final Exception exception) {
        Toast.makeText(this, "Something Wrong Happend", Toast.LENGTH_SHORT).show();
    }

    private void performOperationOnSignOut() {
        final String signOutText = "Signed Out.";
//        currentUserTextView.setText("");
        Toast.makeText(getApplicationContext(), signOutText, Toast.LENGTH_SHORT)
                .show();
    }





    private void UserLogin() {
        try {
                final ProgressDialog dialog;
                dialog = new ProgressDialog(SignIn.this);
                dialog.setMessage("Loading...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                Call<UserModel> userModelCall = ApiHandler.getApiInterface().getUser(ApiMapJson());

           // Call<UserModel> registerCall = ApiHandler.getApiInterface().getUser(ApiMapJson());
            userModelCall.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> userModelCall, Response<UserModel> response ) {

                        try {

                            if (response.isSuccessful())
                            {
                                dialog.dismiss();
                                int status = response.body().getMeta().getStatus();
                                if (status==200)
                                {
                                   access_token = response.body().getData().getAccessToken();
                                   session.createLoginSession(access_token);
                                   session.saveToken(access_token);
                                    GetProfile(access_token);

                                    String msg = response.body().getMeta().getMessage();
                                //    Toast.makeText(SignIn.this, ""+msg, Toast.LENGTH_SHORT).show();

                                }
                                else if (status==400)
                                {
                                    Toast.makeText(SignIn.this, "Server is not responding at that time", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();


                                }



                            }

                            else
                            {
                                Toast.makeText(SignIn.this, ""+response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                                dialog.dismiss();


                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                Log.e("Tag", "error=" + e.toString());
//                                Toast.makeText(SignIn.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
                            } catch (Resources.NotFoundException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t)
                    {
                        try {
                            Log.e("Tag", "error" + t.toString());
                            Toast.makeText(SignIn.this, "Internal Server Error", Toast.LENGTH_SHORT).show();

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



    private void gettoken() {
        try {
            Call<TokenModel> tokenModelCall = ApiHandler.getApiInterface().getToken(ApiJson());

            // Call<UserModel> registerCall = ApiHandler.getApiInterface().getUser(ApiMapJson());
            tokenModelCall.enqueue(new Callback<TokenModel>() {
                @Override
                public void onResponse(Call<TokenModel> userModelCall, Response<TokenModel> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                access_token = response.body().getData().getToken();
                                session.createLoginSession(access_token);
                                session.saveToken(access_token);
                                GetProfile(access_token);


                            }

//                            String msg = response.body().getMeta().getMessage();
//                            Toast.makeText(SignIn.this, ""+msg, Toast.LENGTH_SHORT).show();

                        }

                        else
                        {
                            Toast.makeText(SignIn.this, "Error", Toast.LENGTH_SHORT).show();


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
                public void onFailure(Call<TokenModel> call, Throwable t)
                {
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



    private void GetProfile(final String access_token) {
        try {

            Call<UserDataModel> userDataModelCall = ApiHandler.getApiInterface().getUserDetail("Bearer "+access_token);

            // Call<UserModel> registerCall = ApiHandler.getApiInterface().getUser(ApiMapJson());
            userDataModelCall.enqueue(new Callback<UserDataModel>() {
                @Override
                public void onResponse(Call<UserDataModel> userDataModelCall1, Response<UserDataModel> response ) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {

                                reportingTo=response.body().getData().getResponse().getReportingTo();
//                                designation= response.body().getData().getResponse().getDesignation();
//
//                                String desg=designation.get(0).toString();
//

                                String name = response.body().getData().getResponse().getName();
                                String cnic = response.body().getData().getResponse().getCnic();
                                String email = response.body().getData().getResponse().getEmail();
                                String dob = response.body().getData().getResponse().getDob();
                                String joiningDate = response.body().getData().getResponse().getJoiningDate();
                                String department = response.body().getData().getResponse().getDepartment();
                                String reportto = response.body().getData().getResponse().getReportingTo().getName();
                                String profimg = response.body().getData().getResponse().getProfilePicture();
                                String empcode = response.body().getData().getResponse().getEmployeeCode();


                                String phone =response.body().getData().getResponse().getUserContacts().get(0).getPhoneNumber();

                                session.saveFirstName(name);
                                session.saveCnicNumber(cnic);
                                session.saveUserEmail(email);
                                session.saveOfdob(dob);
                                session.saveUserDateJoined(joiningDate);
                                session.saveDepartment(department);
                                session.saveReprtTo(reportto);
                                session.saveProfImage(profimg);
                                session.saveEmpcode(empcode);
                                session.saveUserPhoneNumber(phone);


                                getmember(access_token);
                                getsidemenu(access_token);
                                getgender(access_token);
                                getbulid(access_token);
                                getUser(access_token);
                                getsalutation(access_token);

                                Intent i = new Intent(SignIn.this, MainActivity.class);
                                //  i.putExtra("bundle", userDataModel.getData().getResponse().getName());
                                startActivity(i);
                                finish();




//                                String msg = response.body().getMeta().getMessage();
//                                Toast.makeText(SignIn.this, ""+msg, Toast.LENGTH_SHORT).show();

                            }


                        }
                        else
                        {

                            Toast.makeText(SignIn.this, "Invalid Token or Id", Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<UserDataModel> call, Throwable t) {
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

   

    private JsonObject ApiJson() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("token", mictoken);


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



    private JsonObject ApiMapJson() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("email", semail);
            jsonObj_.put("password", spassword);


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
    // validation
    private boolean validate()
    {

        boolean valid = true;
        semail = binding.etemail.getText().toString().trim();
        spassword = binding.etpassword.getText().toString().trim();

        if (semail.isEmpty()) {
            binding.etpassword.setError("Please Enter Email");

            valid = false;
            return valid;
        } else {
            if (semail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                binding.etpassword.setError(null);
            } else {
                binding.etpassword.setError("Please Enter Valid Email");
                valid = false;
                return valid;
            }


        }
        if (spassword.isEmpty() || spassword.length() < 4) {
            binding.etpassword.setError("Password length must be at least 4");
            valid = false;
            return valid;
        } else {
            binding.etpassword.setError(null);
        }


        return valid;

    }
    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
               // Toast.makeText(pContext, ""+hashKey, Toast.LENGTH_SHORT).show();
                Log.d("key", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("key", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("key", "printHashKey()", e);
        }
    }


    public void getmember(final String access_token) {
        try {

//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(SignIn.this);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();

            Call<Members> membersCall = ApiHandler.getApiInterface().getlistMember("Bearer " + access_token);
            Log.e("Tag", "response" + membersCall.toString());

            membersCall.enqueue(new Callback<Members>() {
                @Override
                public void onResponse(Call<Members> membersCall1, Response<Members> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                memberResponses = response.body().getData().getResponse();

                                Log.e("Tag", "respone" +memberResponses.toString());

                                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(memberResponses);
                                editor.putString("member", json);
                                editor.apply();
                               // Toast.makeText(SignIn.this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();

                                //  dialog.dismiss();
                            }


                        }
                        else {

                            Toast.makeText(SignIn.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                            //dialog.dismiss();
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
                public void onFailure(Call<Members> call, Throwable t) {
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

    public void getsidemenu(final String access_token)
    {
        try {

//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(SignIn.this);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();

            Call<Getsidemenu> getsidemenuCall = ApiHandler.getApiInterface().getsidemenu("Bearer " + access_token);
            Log.e("Tag", "response" + getsidemenuCall.toString());

            getsidemenuCall.enqueue(new Callback<Getsidemenu>() {
                @Override
                public void onResponse(Call<Getsidemenu> getsidemenuCall1, Response<Getsidemenu> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                getscreenlist = response.body().getData().getMenu();

                                Log.e("Tag", "respone" +getscreenlist.toString());

                                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(getscreenlist);
                                editor.putString("sidemenu", json);
                                editor.apply();
                                // Toast.makeText(SignIn.this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();

                                //  dialog.dismiss();
                            }


                        }
                        else {

                            Toast.makeText(SignIn.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                            //dialog.dismiss();
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
                public void onFailure(Call<Getsidemenu> call, Throwable t) {
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



    public void getsalutation(final String access_token) {
        try {

//            final ProgressDialog progressDialog;
//            progressDialog = new ProgressDialog(SignIn.this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();

            Call<GetSalutation> getSalutationCall = ApiHandler.getApiInterface().getSalu("Bearer " + access_token);
            Log.e("Tag", "response" + getSalutationCall.toString());

            getSalutationCall.enqueue(new Callback<GetSalutation>() {
                @Override
                public void onResponse(Call<GetSalutation> getSalutationCall1, Response<GetSalutation> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) 
                            {
                                if (status==200)
                                {
                                    getSalutationDataList = response.body().getData().getResponse();

                                    Log.e("Tag", "respone" +getSalutationDataList.toString());

                                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(getSalutationDataList);
                                    editor.putString("salutation", json);
                                    editor.apply();
                                   // Toast.makeText(SignIn.this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();

                                    //  dialog.dismiss();


                                }
                            }


                        } else {

                            Toast.makeText(SignIn.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
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
                public void onFailure(Call<GetSalutation> call, Throwable t) {
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

    public void getgender(final String access_token)
    {
        try {

//            final ProgressDialog progressDialog;
//            progressDialog = new ProgressDialog(SignIn.this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();

            Call<GetGender> getGenderCall = ApiHandler.getApiInterface().getGender("Bearer " + access_token);
            Log.e("Tag", "response" + getGenderCall.toString());

            getGenderCall.enqueue(new Callback<GetGender>() {
                @Override
                public void onResponse(Call<GetGender> getGenderCall1, Response<GetGender> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                getGenderDataList = response.body().getData().getResponse();

                                Log.e("Tag", "respone" +getGenderDataList.toString());

                                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(getGenderDataList);
                                editor.putString("gender", json);
                                editor.apply();

                            }


                        } else {

                            Toast.makeText(SignIn.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                           // progressDialog.dismiss();
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
                public void onFailure(Call<GetGender> call, Throwable t) {
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
    public void getbulid(final String access_token)
    {
        try {

            Call<GetBuildingList> getBuildingListCall = ApiHandler.getApiInterface().getbuilding("Bearer " + access_token);
           // Log.e("Tag", "response" + getBuildingListCall.toString());

            getBuildingListCall.enqueue(new Callback<GetBuildingList>() {
                @Override
                public void onResponse(Call<GetBuildingList> getBuildingListCall1, Response<GetBuildingList> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                getBuildingDataListList = response.body().getData().getResponse();

                                Log.e("Tag", "respone" +getBuildingDataListList.toString());

                                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(getBuildingDataListList);
                                editor.putString("bulid", json);
                                editor.apply();

                            }


                        }
                        else
                        {

                            Toast.makeText(SignIn.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<GetBuildingList> call, Throwable t) {
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


    private void getUser(String access_token)
    {
        try {

            Call<GetUser> getUserCall = ApiHandler.getApiInterface().getuser("Bearer " + access_token);
            Log.e("Tag", "response" + getUserCall.toString());

            getUserCall.enqueue(new Callback<GetUser>() {
                @Override
                public void onResponse(Call<GetUser> getUserCall1, Response<GetUser> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                getUserData =response.body().getData().getResponse();

//                                getUserDataList = response.body().getData().getResponse();

                                //    Log.e("Tag", "respone" +getUserDataList.toString());


//                                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                Gson gson = new Gson();
//                                String json = gson.toJson(getUserData);
//                                Toast.makeText(SignIn.this, ""+json, Toast.LENGTH_SHORT).show();
//                                editor.putString("user", json);
//                                editor.apply();
//                                Toast.makeText(SignIn.this, "data add", Toast.LENGTH_SHORT).show();

                                SharedPreferences mPrefs = getSharedPreferences("shared preferences",Context.MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(getUserData);
                                prefsEditor.putString("userprof", json);
                                prefsEditor.commit();

                            }


                        } else {

                            Toast.makeText(SignIn.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<GetUser> call, Throwable t) {
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




//    public JsonObject login()
//    {
//        JsonObject gsonObject = new JsonObject();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ApiInterface.URL_BASE)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//
//        // prepare call in Retrofit 2.0
//        try {
//            JSONObject paramObject = new JSONObject();
//            paramObject.put("email", semail);
//            paramObject.put("password", spassword);
//
//            Call<UserModel> userCall = apiInterface.getUser(paramObject.toString());
//            userCall.enqueue(this);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return gsonObject;
//    }
//
//    @Override
//    public void onResponse(Call<UserModel> call, Response<UserModel> response)
//    {
//
//        if (response.isSuccessful())
//        {
//
//            Toast.makeText(this, "ashdahsdabshda", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public void onFailure(Call<UserModel> call, Throwable t) {
//
//    }
//
////    @Override
////    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
////
////          //  Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
////            if (response.isSuccessful())
////            {
////                try {
////                    JSONObject json = new JSONObject(response.body().toString());
////                    JSONObject jsonArrays = json.getJSONObject("meta");
////                    int status = jsonArrays.getInt("status");
////
////                    if (status==200)
////                    {
////                        Toast.makeText(this, ""+jsonArrays.getString("message"), Toast.LENGTH_SHORT).show();
////
////
////                    }
////                }
////                catch (JSONException e)
////
////                {
////                    e.printStackTrace();
////                }
////
////                // dialog.dismiss();
////
//////                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//////                finish();
//////                startActivity(intent);
////                Toast.makeText(this, "okkkkkkkk", Toast.LENGTH_SHORT).show();
////
////
////            } else {
////               // dialog.dismiss();
////                Toast.makeText(this, "non", Toast.LENGTH_SHORT).show();
////
////
////        }
////
////
////
////
////    }
////
////    @Override
////    public void onFailure(Call<UserModel> call, Throwable t) {
////        Toast.makeText(this, "nononono", Toast.LENGTH_SHORT).show();
////    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }
    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
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

}