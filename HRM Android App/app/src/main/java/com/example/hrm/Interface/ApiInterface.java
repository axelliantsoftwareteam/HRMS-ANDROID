package com.example.hrm.Interface;

import com.example.hrm.Model.CheckIn.CheckInModel;
import com.example.hrm.Model.LeaveRequestModel.LeavesRequest;
import com.example.hrm.Model.LeavesModel.AllLeavesModel;
import com.example.hrm.Model.LoginModel.UserModel;
import com.example.hrm.Model.MicLogin.TokenModel;
import com.example.hrm.Model.SpinerModel.SpinerLeaves;
import com.example.hrm.Model.UserProfileModel.UserDataModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface
{

    @Headers("Content-Type: application/json")
    @POST("login/")
    Call<UserModel> getUser(@Body JsonObject jsonBody);

    @Headers("Content-Type: application/json")
    @POST("admin/addLeaveRequest")
    Call<LeavesRequest> postleave(@Header("Authorization") String auth,@Body JsonObject jsonBody);

    @Headers("Content-Type: application/json")
    @POST("admin/validate_mobile_code")
    Call<TokenModel> getToken(@Body JsonObject jsonBody);

//    @Headers({ "Content-Type: application/json"})
//    @GET("admin/getProfile")
//    Call<UserDataModel> getUserDetail(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json"})
    @GET("admin/getProfileInfoForDashboard")
    Call<UserDataModel> getUserDetail(@Header("Authorization") String auth);


    // start clock In
    @Headers({ "Content-Type: application/json"})
    @GET("admin/getDashboardPersonalInfo")
    Call<CheckInModel> getClock(@Header("Authorization") String auth);

    // end clock In


//    Call<Leaf> getAllleaves(@QueryMap Map<String, String> params, Callback<String> callback);
    @GET("admin/getLeaveRequest?start=0&limit=5&sort=&order=Desc&search=nullli")
    Call<AllLeavesModel> getAllleaves(@Header("Authorization") String auth);

    @GET("admin/getStaticDataByDisplayMember?displayMember=Leaves")
    Call<SpinerLeaves> getspiner(@Header("Authorization") String auth);


}
