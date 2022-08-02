package com.example.hrm.Interface;

import com.example.hrm.Model.AddAttendReq.AttenReqResponse;
import com.example.hrm.Model.AddEuipmentReq.AddEquipmentModel;
import com.example.hrm.Model.AddGeneralReq.AddGeneralModel;
import com.example.hrm.Model.AddLeaveRequestModel.AddLeaveReq;
import com.example.hrm.Model.Approval.AttendApproval.GetAttendApprolModel;
import com.example.hrm.Model.Approval.LeavesApproval.GetLeaveApprolModel;
import com.example.hrm.Model.Attendance.AttendanceResponse;
import com.example.hrm.Model.BasicSetup.Department.GetDepartment;
import com.example.hrm.Model.BasicSetup.Designation.GetAllDesign.GetAllDesignation;
import com.example.hrm.Model.BasicSetup.Designation.GetDesig;
import com.example.hrm.Model.BasicSetup.Group.GetGroup;
import com.example.hrm.Model.BasicSetup.Roles.GetRoles;
import com.example.hrm.Model.BasicSetup.Shifts.GetShift;
import com.example.hrm.Model.BasicSetup.Skills.AddSkill.Addskill;
import com.example.hrm.Model.BasicSetup.StaticDataModel.AddStatic.Addstatic;
import com.example.hrm.Model.Building.AddBuild.AddBuild;
import com.example.hrm.Model.Building.GetBuilding;
import com.example.hrm.Model.Calender.GetCalender;
import com.example.hrm.Model.CheckIn.CheckInModel;
import com.example.hrm.Model.EquipementRequestModel.EquipmentListModel;
import com.example.hrm.Model.Evalution.GetEvaluation;
import com.example.hrm.Model.Evalution.Questions.GetEvalQuestion;
import com.example.hrm.Model.GeneralRequestModel.GeneralListModel;
import com.example.hrm.Model.GetAllTask.GetAllTaskModel;
import com.example.hrm.Model.BasicSetup.HolidayModel.GetHolidayModel;
import com.example.hrm.Model.LeaveBalanceModel.Leavebalance;
import com.example.hrm.Model.LeaveRequestModel.LeavesRequest;
import com.example.hrm.Model.LeavesModel.AllLeavesModel;
import com.example.hrm.Model.LoginModel.UserModel;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Model.MicLogin.TokenModel;
import com.example.hrm.Model.Organo.OrganoModel;
import com.example.hrm.Model.ReqAttendList.RequestListModel;
import com.example.hrm.Model.BasicSetup.Skills.GetSkills;
import com.example.hrm.Model.SpinerModel.SpinerLeaves;
import com.example.hrm.Model.BasicSetup.StaticDataModel.GetDataMember.GetStDataMemberModel;
import com.example.hrm.Model.BasicSetup.StaticDataModel.GetStaticDataModel;
import com.example.hrm.Model.UserProfileModel.UserDataModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @Headers("Content-Type: application/json")
    @POST("admin/addStaticDataInfo")
    Call<Addstatic>addstatic(@Header("Authorization") String auth, @Body JsonObject jsonBody);

//add Skills
    @Headers("Content-Type: application/json")
    @POST("admin/addEditSkill")
    Call<Addskill>addskills(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    //add Building
    @Headers("Content-Type: application/json")
    @POST("admin/addEditBuilding")
    Call<AddBuild>addbuild(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    //add holiday
    @Headers("Content-Type: application/json")
    @POST("admin/addEditHoliday")
    Call<GetHolidayModel>addhoilday(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    //add Shift
    @Headers("Content-Type: application/json")
    @POST("admin/addEditSkill")
    Call<Addskill>addshifts(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Get all Static Data Member
    @GET("admin/getAllDisplayMember")
    Call<GetStaticDataModel> getstaticdata(@Header("Authorization") String auth);



    // Get Member Selected Member
    @GET("admin/getStaticDataByDisplayMember")
    Call<GetStDataMemberModel>getSelectMember(@Header("Authorization") String auth,
                                              @Query("displayMember") String member);

    // https://devhris.khazanapk.com/api/admin/?displayMember=Holiday
    // Get all Static Data Member Hoilday data

    @GET("admin/getStaticDataByDisplayMember")
    Call<GetStDataMemberModel>getHoliday(@Header("Authorization") String auth,
                                              @Query("displayMember") String member);


    // Get all Holiday
    @GET("admin/getHoliday")
    Call<GetHolidayModel> getHoliday(@Header("Authorization") String auth);


    // Get all Building
    @GET("admin/getallbuildings")
    Call<GetBuilding> getBuilding(@Header("Authorization") String auth);

    // Get all Evaluation
    @GET("admin/getEvaluations")
    Call<GetEvaluation>getEvalution(@Header("Authorization") String auth, @Query("status") boolean status);


    // Get all Evaluation Questions
    @GET("admin/getEvaluationQuestions")
    Call<GetEvalQuestion>getEvalQuest(@Header("Authorization") String auth);


    // Get all Designation
    @GET("admin/get_calendar_info")
    Call<GetCalender> getCalenderinfo(@Header("Authorization") String auth);


    // Get all Designation
    @GET("admin/getAllDesignation")
    Call<GetAllDesignation> getDesignation(@Header("Authorization") String auth);
    //add Designation
    @Headers("Content-Type: application/json")
    @POST("admin/addEditDesignation")
    Call<GetDesig>addDesignation(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Get all Department
    @GET("admin/getalldepartments")
    Call<GetDepartment> getDepart(@Header("Authorization") String auth);
    //add Department
    @Headers("Content-Type: application/json")
    @POST("admin/addEditDepartment")
    Call<AddBuild>addDepart(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Get all Roles
    @GET("admin/getallroles")
    Call<GetRoles>getroles(@Header("Authorization") String auth);

    // Get all Groups
    @GET("admin/getAllGroup")
    Call<GetGroup> getgroups(@Header("Authorization") String auth);


    //add Groups
    @Headers("Content-Type: application/json")
    @POST("admin/addEditGroup")
    Call<GetGroup>addgroups(@Header("Authorization") String auth, @Body JsonObject jsonBody);



    // Get all Skills
    @GET("admin/getAllSkills")
    Call<GetSkills> getSkill(@Header("Authorization") String auth);

    // Get all
    @GET("admin/getallshifts")
    Call<GetShift>getShift(@Header("Authorization") String auth);


    // Approval Attendance List
    @GET("admin/getAttendanceRequestOfEmployeeByAdmin")
    Call<GetAttendApprolModel>getAttendApp(@Header("Authorization") String auth,
                                           @Query("employee_id") Integer emp ,
                                           @Query("start") Integer date,
                                           @Query("limit") Integer limit,
                                           @Query("sort") String sort,
                                           @Query("order") String order,
                                           @Query("search") String search);


    // Approval Leave List
    @GET("admin/getEmployeeLeaveRequest")
    Call<GetLeaveApprolModel>getLeaveApp(@Header("Authorization") String auth,
                                         @Query("employee_id") Integer emp ,
                                         @Query("start") Integer date,
                                         @Query("limit") Integer limit,
                                         @Query("sort") String sort,
                                         @Query("order") String order,
                                         @Query("search") String search);


    // Approval General List
    @GET("admin/getEmployeeLeaveRequest")
    Call<GetLeaveApprolModel>getGeneralApp(@Header("Authorization") String auth,
                                         @Query("employee_id") Integer emp ,
                                         @Query("start") Integer date,
                                         @Query("limit") Integer limit,
                                         @Query("sort") String sort,
                                         @Query("order") String order,
                                         @Query("search") String search);


    // Approval Equipment List
    @GET("admin/getEmployeeLeaveRequest")
    Call<GetLeaveApprolModel>getEquiptApp(@Header("Authorization") String auth,
                                         @Query("employee_id") Integer emp ,
                                         @Query("start") Integer date,
                                         @Query("limit") Integer limit,
                                         @Query("sort") String sort,
                                         @Query("order") String order,
                                         @Query("search") String search);



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

    // Add Equipment request
    @Headers("Content-Type: application/json")
    @POST("admin/equipmentAddRequest")
    Call<AddEquipmentModel>AddEquiptReq(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    // Add General request
    @Headers("Content-Type: application/json")
    @POST("admin/generalAddRequest")
    Call<AddGeneralModel>AddGeneralReq(@Header("Authorization") String auth, @Body JsonObject jsonBody);


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




    // All Tags List
    @GET("admin/getAllTask/getAllTask?tag=null&status=null&employee_id=undefined")
    Call<GetAllTaskModel>getAllList(@Header("Authorization") String auth);

    // Today List
    @GET("admin/getAllTask/getAllTask?tag=null&status=1&employee_id=undefined")
    Call<GetAllTaskModel>getAllTodayList(@Header("Authorization") String auth);


    // sevenday List
    @GET("admin/getAllTask/getAllTask?tag=null&status=2&employee_id=undefined")
    Call<GetAllTaskModel>getSevenList(@Header("Authorization") String auth);
    // pending List
    @GET("admin/getAllTask/getAllTask?tag=null&status=4&employee_id=undefined")
    Call<GetAllTaskModel>getpendingList(@Header("Authorization") String auth);
    // Completed List
    @GET("admin/getAllTask/getAllTask?tag=null&status=5&employee_id=undefined")
    Call<GetAllTaskModel>getcompletList(@Header("Authorization") String auth);
    // High List
    @GET("admin/getAllTask/getAllTask?tag=2&status=5&employee_id=undefined")
    Call<GetAllTaskModel>gethighList(@Header("Authorization") String auth);
    // Normal List
    @GET("admin/getAllTask/getAllTask?tag=1&status=5&employee_id=undefined")
    Call<GetAllTaskModel>getnormalList(@Header("Authorization") String auth);
    // low List
    @GET("admin/getAllTask/getAllTask?tag=0&status=5&employee_id=undefined")
    Call<GetAllTaskModel>getlowList(@Header("Authorization") String auth);



    // Approval Equipment List
    @GET("admin/getEmployeeLeaveRequest")
    Call<GetAllTaskModel>gettaglist(@Header("Authorization") String auth);



    // All Attendance List
    @GET("admin/getAttendanceRequestOfEmployee?start=0&limit=5&sort=&order=Desc&search=null")
    Call<RequestListModel>getAllAttendList(@Header("Authorization") String auth);

    // All Equipments List
    @GET("admin/equipmentGetRequest?start=0&limit=5&sort=&order=Desc&search=null")
    Call<EquipmentListModel>getAllEquiptList(@Header("Authorization") String auth);
    // All Genaral List
    @GET("admin/generalGetRequest?start=0&limit=5&sort=&order=Desc&search=null")
    Call<GeneralListModel>getAllGeneralList(@Header("Authorization") String auth);


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
    Call<Members>getlistMember(@Header("Authorization") String auth);


    // Get organo
    @GET("admin/getOrganogramOfCompany")
    Call<OrganoModel>getorgano(@Header("Authorization") String auth);

}

