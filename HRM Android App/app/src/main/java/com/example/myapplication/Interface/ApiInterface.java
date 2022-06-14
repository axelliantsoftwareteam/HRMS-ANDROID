package com.example.myapplication.Interface;

import com.example.myapplication.Model.CheckIn.CheckInModel;
import com.example.myapplication.Model.LeavesModel.AllLeavesModel;
import com.example.myapplication.Model.LoginModel.UserModel;
import com.example.myapplication.Model.UserProfileModel.UserDataModel;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiInterface
{

    @Headers("Content-Type: application/json")
    @POST("login/")
    Call<UserModel> getUser(@Body JsonObject jsonBody);


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
}
