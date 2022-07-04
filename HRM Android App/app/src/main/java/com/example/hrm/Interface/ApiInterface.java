package com.example.hrm.Interface;

import com.example.hrm.Model.CheckIn.AddAttendReq.AttenReqResponse;
import com.example.hrm.Model.AddLeaveRequestModel.AddLeaveReq;
import com.example.hrm.Model.Attendance.AttendanceResponse;
import com.example.hrm.Model.CheckIn.CheckInModel;
import com.example.hrm.Model.LeaveBalanceModel.Leavebalance;
import com.example.hrm.Model.LeaveRequestModel.LeavesRequest;
import com.example.hrm.Model.LeavesModel.AllLeavesModel;
import com.example.hrm.Model.LoginModel.UserModel;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Model.MicLogin.TokenModel;
import com.example.hrm.Model.SpinerModel.SpinerLeaves;
import com.example.hrm.Model.UserProfileModel.UserDataModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
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

// Add Attendance
    @Headers("Content-Type: application/json")
    @POST("admin/addAttendanceRequest")
    Call<AttenReqResponse>AddAttendReq(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Add leave Request
    @Headers("Content-Type: application/json")
    @POST("admin/addLeaveRequest")
    Call<AddLeaveReq>AddRequest(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    // get month days
    @Headers("Content-Type: application/json")
    @POST("admin/getAttendanceOfSingleEmployee")
    Call<AttendanceResponse> getattend(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    // get month days
    @Headers("Content-Type: application/json")
    @POST("admin/getAttendanceOfSingleEmployee")
    Call<AttendanceResponse> getattenday(@Header("Authorization") String auth, @Body JsonObject jsonBody);



    // All Leaves
    @GET("admin/getLeaveRequest?start=0&limit=5&sort=&order=Desc&search=nullli")
    Call<AllLeavesModel> getAllleaves(@Header("Authorization") String auth);


    // All Attendance
    @GET("admin/getLeaveRequest?start=0&limit=5&sort=&order=Desc&search=nullli")
    Call<AllLeavesModel> getAllAttend(@Header("Authorization") String auth);

// Approved leaves
    @GET("admin/getLeaveRequest?start=0&limit=5&sort=&order=Desc&search=nullli&status=1")
    Call<AllLeavesModel>getapprov(@Header("Authorization") String auth);

    // Pending leaves
    @GET("admin/getLeaveRequest?start=0&limit=5&sort=&order=Desc&search=nullli&status=0")
    Call<AllLeavesModel>getpend(@Header("Authorization") String auth);

    // Rejected leaves
    @GET("admin/getLeaveRequest?start=0&limit=5&sort=&order=Desc&search=nullli&status=2")
    Call<AllLeavesModel>getrejected(@Header("Authorization") String auth);



    // Leave balance
    @GET("admin/getEmployeeLeaveQuotaDetailOnDashboard")
    Call<Leavebalance> getleavebalance(@Header("Authorization") String auth);


    // Leave type
    @GET("admin/getStaticDataByDisplayMember?displayMember=Leaves")
    Call<SpinerLeaves> getspiner(@Header("Authorization") String auth);


    // Get all user to download  leaves file
    @GET("admin/downloadAttendanceReport?start_date=null&end_date=null&employee_list=null")
    Call<ResponseBody> getdownload(@Header("Authorization") String auth);


    // Get all user to find leaves
    @GET("admin/getEmployeeReportedUsers")
    Call<Members> getlistMember(@Header("Authorization") String auth);


}
