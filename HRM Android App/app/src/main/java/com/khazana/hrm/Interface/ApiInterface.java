package com.khazana.hrm.Interface;

import com.khazana.hrm.Model.AddAttendReq.AttenReqResponse;
import com.khazana.hrm.Model.AddEuipmentReq.AddEquipmentModel;
import com.khazana.hrm.Model.AddGeneralReq.AddGeneralModel;
import com.khazana.hrm.Model.AddLeaveRequestModel.AddLeaveReq;
import com.khazana.hrm.Model.Approval.AttendApproval.GetAttendApprolModel;
import com.khazana.hrm.Model.Approval.EquipmentApproval.AddEquiptReq;
import com.khazana.hrm.Model.Approval.GenaralApproval.AddGeneralReq;
import com.khazana.hrm.Model.Approval.LeavesApproval.AddLeaveApproval;
import com.khazana.hrm.Model.Approval.LeavesApproval.GetLeaveApprolModel;
import com.khazana.hrm.Model.Attendance.AttendancePost;
import com.khazana.hrm.Model.Attendance.AttendanceResponse;
import com.khazana.hrm.Model.BasicSetup.Department.GetDepartment;
import com.khazana.hrm.Model.BasicSetup.Designation.GetAllDesign.GetAllDesignation;
import com.khazana.hrm.Model.BasicSetup.Designation.GetDesig;
import com.khazana.hrm.Model.BasicSetup.Group.GetGroup;
import com.khazana.hrm.Model.BasicSetup.HolidayModel.TypeHoilday.GetHolidayList;
import com.khazana.hrm.Model.BasicSetup.Roles.GetRoles;
import com.khazana.hrm.Model.BasicSetup.Shifts.GetShift;
import com.khazana.hrm.Model.BasicSetup.Skills.AddSkill.Addskill;
import com.khazana.hrm.Model.BasicSetup.StaticDataModel.AddStatic.Addstatic;
import com.khazana.hrm.Model.Building.AddBuild.AddBuild;
import com.khazana.hrm.Model.Building.GetBuilding;
import com.khazana.hrm.Model.Building.TaskAdded.TaskAddedModel;
import com.khazana.hrm.Model.Calender.AddEvent.AddCalender;
import com.khazana.hrm.Model.Calender.AddEvent.AddEventCalender;
import com.khazana.hrm.Model.Calender.GetCalenderInfo;
import com.khazana.hrm.Model.CheckIn.CheckInModel;
import com.khazana.hrm.Model.ClientInfo.AddPresonalInfo;
import com.khazana.hrm.Model.ClientInfo.GetUserById;
import com.khazana.hrm.Model.ClientInfo.UserContacts.AddContact;
import com.khazana.hrm.Model.ClientInfo.UserExprience.AddExperience;
import com.khazana.hrm.Model.ClientInfo.UserExprience.AddExpr.AddExpById;
import com.khazana.hrm.Model.ClientInfo.UserQualification.AddQualification;
import com.khazana.hrm.Model.ClientInfo.UserQualification.Deletitem;
import com.khazana.hrm.Model.ClientInfo.UserSkills.AddUserSkills;
import com.khazana.hrm.Model.EmployeeManagement.WorkFlow.GetWorkFlow;
import com.khazana.hrm.Model.EquipementRequestModel.EquipmentListModel;
import com.khazana.hrm.Model.Evalution.GetEvaluation;
import com.khazana.hrm.Model.Evalution.Questions.GetEvalQuestion;
import com.khazana.hrm.Model.GeneralRequestModel.GeneralListModel;
import com.khazana.hrm.Model.GetAllTask.Alltask.AddTask;
import com.khazana.hrm.Model.GetAllTask.Alltask.GetAlltask;
import com.khazana.hrm.Model.BasicSetup.HolidayModel.GetHolidayModel;
import com.khazana.hrm.Model.GetAllTask.Alltask.MarkComplet.MarkComplet;
import com.khazana.hrm.Model.GetAllTask.Alltask.SevendayTask.GetSevenday;
import com.khazana.hrm.Model.GetAllTask.Alltask.Today.GetToday;
import com.khazana.hrm.Model.LeaveBalanceModel.Leavebalance;
import com.khazana.hrm.Model.LeaveRequestModel.LeavesRequest;
import com.khazana.hrm.Model.LeavesModel.AllLeavesModel;
import com.khazana.hrm.Model.LoginModel.UserModel;
import com.khazana.hrm.Model.Memberlist.Members;
import com.khazana.hrm.Model.MicLogin.TokenModel;
import com.khazana.hrm.Model.Organo.OrganoModel;
import com.khazana.hrm.Model.Payroll.EobiSlab.AddEobiSlabs.AddEobiSlab;
import com.khazana.hrm.Model.Payroll.EobiSlab.GetEobiSlab;
import com.khazana.hrm.Model.Payroll.HealthSlabs.AddHealthSlabs.AddHealthSlab;
import com.khazana.hrm.Model.Payroll.TaxSlab.GetTaxSlabs;
import com.khazana.hrm.Model.Payroll.HealthSlabs.GetHealthSlab;
import com.khazana.hrm.Model.ReqAttendList.RequestListModel;
import com.khazana.hrm.Model.BasicSetup.Skills.GetSkills;
import com.khazana.hrm.Model.SideMenu.Getsidemenu;
import com.khazana.hrm.Model.SpinerModel.SpinerLeaves;
import com.khazana.hrm.Model.BasicSetup.StaticDataModel.GetDataMember.GetStDataMemberModel;
import com.khazana.hrm.Model.BasicSetup.StaticDataModel.GetStaticDataModel;
import com.khazana.hrm.Model.UserEvaluations.post.AddUserEvaluations;
import com.khazana.hrm.Model.UserEvaluations.response.UserEvaluationsResponse;
import com.khazana.hrm.Model.UserProfileModel.Buildings.GetBuildingList;
import com.khazana.hrm.Model.UserProfileModel.EditProfile;
import com.khazana.hrm.Model.UserProfileModel.Gender.GetGender;
import com.khazana.hrm.Model.UserProfileModel.Salutation.GetSalutation;
import com.khazana.hrm.Model.UserProfileModel.UserData.EditProfile.EditProf;
import com.khazana.hrm.Model.UserProfileModel.UserData.GetUser;
import com.khazana.hrm.Model.UserProfileModel.UserDataModel;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("login/")
    Call<UserModel> getUser(@Body JsonObject jsonBody);

    @Headers("Content-Type: application/json")
    @POST("admin/addLeaveRequest")
    Call<LeavesRequest> postleave(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    @Headers("Content-Type: application/json")
    @POST("admin/validate_mobile_code")
    Call<TokenModel> getToken(@Body JsonObject jsonBody);

//    @Headers({ "Content-Type: application/json"})
//    @GET("admin/getProfile")
//    Call<UserDataModel> getUserDetail(@Header("Authorization") String auth);

    @Headers("Content-Type: application/json")
    @POST("admin/addStaticDataInfo")
    Call<Addstatic> addstatic(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    //add Skills
    @Headers("Content-Type: application/json")
    @POST("admin/addEditSkill")
    Call<Addskill> addskills(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    //add healthSlabs
    @Headers("Content-Type: application/json")
    @POST("admin/addHealthSlab")
    Call<AddHealthSlab> addHealth(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    //add EobiSlabs
    @Headers("Content-Type: application/json")
    @POST("admin/addEobiSlab")
    Call<AddEobiSlab> addEobi(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    //add Tax slab
    @Headers("Content-Type: application/json")
    @POST("admin/addTaxSlab")
    Call<Addskill> addtax(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    //mark as read
    @Headers("Content-Type: application/json")
    @POST("admin/markCompleteTask")
    Call<MarkComplet> addmark(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    //add Building
    @Headers("Content-Type: application/json")
    @POST("admin/addEditBuilding")
    Call<AddBuild> addbuild(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    //add holiday
    @Headers("Content-Type: application/json")
    @POST("admin/addEditHoliday")
    Call<GetHolidayModel> addhoilday(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    //add Shift
    @Headers("Content-Type: application/json")
    @POST("admin/addEditSkill")
    Call<Addskill> addshifts(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Get all Static Data Member
    @GET("admin/getAllDisplayMember")
    Call<GetStaticDataModel> getstaticdata(@Header("Authorization") String auth);


    // Get Member Selected Member
    @GET("admin/getStaticDataByDisplayMember")
    Call<GetStDataMemberModel> getSelectMember(@Header("Authorization") String auth,
                                               @Query("displayMember") String member);

    // https://devhris.khazanapk.com/api/admin/?displayMember=Holiday
    // Get all Static Data Member Hoilday data

    // Get all salutation
    @GET("admin/getStaticDataByDisplayMember?displayMember=Holiday")
    Call<GetHolidayList> getholiy(@Header("Authorization") String auth);


    // Get all Holiday
    @GET("admin/getHoliday")
    Call<GetHolidayModel> getHoliday(@Header("Authorization") String auth);


    // Get all Building
    @GET("admin/getallbuildings")
    Call<GetBuilding> getBuilding(@Header("Authorization") String auth);

    // Get all Evaluation
    @GET("admin/getEvaluations")
    Call<GetEvaluation> getEvalution(@Header("Authorization") String auth, @Query("status") boolean status);


    // Get all Evaluation Questions
    @GET("admin/getEvaluationQuestions")
    Call<GetEvalQuestion> getEvalQuest(@Header("Authorization") String auth);

    //    submit  all answers
    @Headers("Content-Type: application/json")
    @POST("admin/addUserEvaluations")
    Call<UserEvaluationsResponse> addUserEvaluations(@Header("Authorization") String auth, @Body AddUserEvaluations jsonBody);


    //    submit  all task
    @Headers("Content-Type: application/json")
    @POST("admin/addEditTask")
    Call<TaskAddedModel> addTask(@Header("Authorization") String auth, @Body AddTask jsonBody);

    //   Edit Profile
    @Headers("Content-Type: application/json")
    @POST("admin/editProfile")
    Call<EditProf> editprof(@Header("Authorization") String auth, @Body EditProfile jsonBody);


    // Get all calender
    @GET("admin/get_calendar_info_for_mobile_phone")
    Call<GetCalenderInfo> getCalenderinfo(@Header("Authorization") String auth);

    //    submit  all events
    @Headers("Content-Type: application/json")
    @POST("admin/add_calendar_event")
    Call<AddCalender> addeventcal(@Header("Authorization") String auth, @Body AddEventCalender jsonBody);


    // Get all Designation
    @GET("admin/getAllDesignation")
    Call<GetAllDesignation> getDesignation(@Header("Authorization") String auth);

    //add Designation
    @Headers("Content-Type: application/json")
    @POST("admin/addEditDesignation")
    Call<GetDesig> addDesignation(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Get all Department
    @GET("admin/getalldepartments")
    Call<GetDepartment> getDepart(@Header("Authorization") String auth);

    //add Department
    @Headers("Content-Type: application/json")
    @POST("admin/addEditDepartment")
    Call<AddBuild> addDepart(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Get all Roles
    @GET("admin/getallroles")
    Call<GetRoles> getroles(@Header("Authorization") String auth);

    // Get all Groups
    @GET("admin/getAllGroup")
    Call<GetGroup> getgroups(@Header("Authorization") String auth);


    //add Groups
    @Headers("Content-Type: application/json")
    @POST("admin/addEditGroup")
    Call<GetGroup> addgroups(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Get all Skills
    @GET("admin/getAllSkills")
    Call<GetSkills> getSkill(@Header("Authorization") String auth);


    // Employee management
    // Get all Workflow
    @GET("admin/getWorkFlows")
    Call<GetWorkFlow> getWorkflow(@Header("Authorization") String auth);


    // Get all
    @GET("admin/getallshifts")
    Call<GetShift> getShift(@Header("Authorization") String auth);


    // Approval Attendance List
    @GET("admin/getAttendanceRequestOfEmployeeByAdmin")
    Call<GetAttendApprolModel> getAttendApp(@Header("Authorization") String auth,
                                            @Query("employee_id") Integer emp,
                                            @Query("start") Integer date,
                                            @Query("sort") String sort,
                                            @Query("order") String order,
                                            @Query("search") String search);


    // Approval Leave List
    @GET("admin/getEmployeeLeaveRequest")
    Call<GetLeaveApprolModel> getLeaveApp(@Header("Authorization") String auth,
                                          @Query("employee_id") Integer emp,
                                          @Query("start") Integer date,
                                          @Query("sort") String sort,
                                          @Query("order") String order,
                                          @Query("search") String search);


    // Approval General List
    @GET("admin/getEmployeeLeaveRequest")
    Call<GetLeaveApprolModel> getGeneralApp(@Header("Authorization") String auth,
                                            @Query("employee_id") Integer emp,
                                            @Query("start") Integer date,
                                            @Query("sort") String sort,
                                            @Query("order") String order,
                                            @Query("search") String search);


    // Approval Equipment List
    @GET("admin/getEmployeeLeaveRequest")
    Call<GetLeaveApprolModel> getEquiptApp(@Header("Authorization") String auth,
                                           @Query("employee_id") Integer emp,
                                           @Query("start") Integer date,
                                           @Query("sort") String sort,
                                           @Query("order") String order,
                                           @Query("search") String search);


    @Headers({"Content-Type: application/json"})
    @GET("admin/getProfileInfoForDashboard")
    Call<UserDataModel> getUserDetail(@Header("Authorization") String auth);


    // start clock In
    @Headers({"Content-Type: application/json"})
    @GET("admin/getDashboardPersonalInfo")
    Call<CheckInModel> getClock(@Header("Authorization") String auth);

    // end clock In


//    Call<Leaf> getAllleaves(@QueryMap Map<String, String> params, Callback<String> callback);

    // Add Attendance
    @Headers("Content-Type: application/json")
    @POST("admin/addAttendanceRequest")
    Call<AttenReqResponse> AddAttendReq(@Header("Authorization") String auth, @Body JsonObject jsonBody);

    // Add Equipment request
    @Headers("Content-Type: application/json")
    @POST("admin/equipmentAddRequest")
    Call<AddEquipmentModel> AddEquiptReq(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    // Add General request
    @Headers("Content-Type: application/json")
    @POST("admin/generalAddRequest")
    Call<AddGeneralModel> AddGeneralReq(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    // Add leave Request
    @Headers("Content-Type: application/json")
    @POST("admin/addLeaveRequest")
    Call<AddLeaveReq> AddRequest(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    // get month days
    @Headers("Content-Type: application/json")
    @POST("admin/getAttendanceOfSingleEmployee")
    Call<AttendanceResponse> getattend(@Header("Authorization") String auth, @Body AttendancePost jsonBody);
    //Call<AttendanceResponse> getattend(@Header("Authorization") String auth, @Body AttendancePost attendance);

    // get month days
    @Headers("Content-Type: application/json")
    @POST("admin/getAttendanceOfSingleEmployee")
    Call<AttendanceResponse> getattenday(@Header("Authorization") String auth, @Body JsonObject jsonBody);


    // All Tags List
    @GET("admin/getAllTask?tag=null&status=null&employee_id=undefined")
    Call<GetAlltask> getAllList(@Header("Authorization") String auth);

    // Today List
    @GET("admin/getAllTask?tag=null&status=1&employee_id=undefined")
    Call<GetToday> getAllTodayList(@Header("Authorization") String auth);


    // Today side menu
    @GET("getSideMenuMobile")
    Call<Getsidemenu> getsidemenu(@Header("Authorization") String auth);


    // sevenday List
    @GET("admin/getAllTask?tag=null&status=2&employee_id=undefined")
    Call<GetSevenday> getSevenList(@Header("Authorization") String auth);

    // pending List
    @GET("admin/getAllTask?tag=null&status=4&employee_id=undefined")
    Call<GetAlltask> getpendingList(@Header("Authorization") String auth);

    // Completed List
    @GET("admin/getAllTask?tag=null&status=5&employee_id=undefined")
    Call<GetAlltask> getcompletList(@Header("Authorization") String auth);

    // High List
    @GET("admin/getAllTask?tag=2&status=5&employee_id=undefined")
    Call<GetAlltask> gethighList(@Header("Authorization") String auth);

    // Normal List
    @GET("admin/getAllTask?tag=1&status=5&employee_id=undefined")
    Call<GetAlltask> getnormalList(@Header("Authorization") String auth);

    // low List
    @GET("admin/getAllTask?tag=0&status=5&employee_id=undefined")
    Call<GetAlltask> getlowList(@Header("Authorization") String auth);


//    // Approval Equipment List
//    @GET("admin/getEmployeeLeaveRequest")
//    Call<GetAllTaskModel>gettaglist(@Header("Authorization") String auth);


    // All Attendance List
    @GET("admin/getAttendanceRequestOfEmployee?start=0&sort=&order=Desc&search=null")
    Call<RequestListModel> getAllAttendList(@Header("Authorization") String auth);

    // All Equipments List
    @GET("admin/equipmentGetRequest?start=0&sort=&order=Desc&search=null")
    Call<EquipmentListModel> getAllEquiptList(@Header("Authorization") String auth);

    // All Genaral List
    @GET("admin/generalGetRequest?start=0&sort=&order=Desc&search=null")
    Call<GeneralListModel>getAllGeneralList(@Header("Authorization") String auth);


    // All Leaves
    @GET("admin/getLeaveRequest?start=0&sort=&order=Desc&search=nullli")
    Call<AllLeavesModel> getAllleaves(@Header("Authorization") String auth);


    // All Leaves request
    @GET("admin/getLeaveRequest?start=0&sort=&order=Desc&search=null")
    Call<AllLeavesModel> getAllleavesreq(@Header("Authorization") String auth);

    // All Attendance
    @GET("admin/getLeaveRequest?start=0&sort=&order=Desc&search=nullli")
    Call<AllLeavesModel> getAllAttend(@Header("Authorization") String auth);

    // Approved leaves
    @GET("admin/getLeaveRequest?start=0&sort=&order=Desc&search=nullli&status=1")
    Call<AllLeavesModel> getapprov(@Header("Authorization") String auth);

    // Pending leaves
    @GET("admin/getLeaveRequest?start=0&sort=&order=Desc&search=nullli&status=0")
    Call<AllLeavesModel> getpend(@Header("Authorization") String auth);

    // Rejected leaves
    @GET("admin/getLeaveRequest?start=0&sort=&order=Desc&search=nullli&status=2")
    Call<AllLeavesModel> getrejected(@Header("Authorization") String auth);


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

    // Get all salutation
    @GET("admin/getStaticDataByDisplayMember?displayMember=Salutation")
    Call<GetSalutation>getSalu(@Header("Authorization") String auth);

    // Get all Gender
    @GET("admin/getStaticDataByDisplayMember?displayMember=Gender")
    Call<GetGender> getGender(@Header("Authorization") String auth);

    // Get all UserDAta
    @GET("admin/getProfile")
    Call<GetUser> getuser(@Header("Authorization") String auth);


    // Get all Building
    @GET("admin/getSystemBuildings")
    Call<GetBuildingList> getbuilding(@Header("Authorization") String auth);

    // Get organo
    @GET("admin/getOrganogramOfCompany")
    Call<OrganoModel> getorgano(@Header("Authorization") String auth);

    // All payroll Apis call
    // Get all TaxSlabs
    @GET("admin/getAllTaxSlabs")
    Call<GetTaxSlabs> gettax(@Header("Authorization") String auth);

    // Get all Health Slabs
    @GET("admin/getAllHealthSlabs")
    Call<GetHealthSlab> gethealth(@Header("Authorization") String auth);

    // Get all Eobi Slabs
    @GET("admin/getAllEobiSlabs")
    Call<GetEobiSlab> geteobi(@Header("Authorization") String auth);

    //new changings


//  personal info add personalinfo
    @Headers("Content-Type: application/json")
    @POST("admin/addEditUser")
    Call<GetUserById> addpersioninfo(@Header("Authorization") String auth, @Body AddPresonalInfo jsonBody);

    // personal info add qualify
    @Headers("Content-Type: application/json")
    @POST("admin/addEditQualification")
    Call<AddExpById> addqualify(@Header("Authorization") String auth, @Body AddQualification jsonBody);

    // personal info delete qualification
    @Headers("Content-Type: application/json")
    @POST("admin/deleteQualificationOfUser")
    Call<AddExpById> deletqualifyitem(@Header("Authorization") String auth, @Body Deletitem jsonBody);
    // personal info add expr
    @Headers("Content-Type: application/json")
    @POST("admin/addEditJobHistory")
    Call<AddExpById>addexpr(@Header("Authorization") String auth, @Body AddExperience jsonBody);
    // personal info delete expr
    @Headers("Content-Type: application/json")
    @POST("admin/deleteJobHistoryOfUser")
    Call<AddExpById>deletexpritem(@Header("Authorization") String auth, @Body Deletitem jsonBody);
    // personal info add contact
    @Headers("Content-Type: application/json")
    @POST("admin/addEditContact")
    Call<AddExpById>addcontact(@Header("Authorization") String auth, @Body AddContact jsonBody);
    // personal info delete contact
    @Headers("Content-Type: application/json")
    @POST("admin/deleteContactOfUser")
    Call<AddExpById>deletecontact(@Header("Authorization") String auth, @Body Deletitem jsonBody);

    // personal info delete skills
    @Headers("Content-Type: application/json")
    @POST("admin/deleteSkillOfUser")
    Call<AddExpById>deletskillitem(@Header("Authorization") String auth, @Body Deletitem jsonBody);
    // add general approval
    @Headers("Content-Type: application/json")
    @POST("admin/changeEmployeeGeneralRequest")
    Call<EditProf>addgeneral(@Header("Authorization") String auth, @Body AddGeneralReq jsonBody);
    // add general approval
    @Headers("Content-Type: application/json")
    @POST("admin/changeEmployeeGeneralRequest")
    Call<EditProf>rejectgeneral(@Header("Authorization") String auth, @Body AddGeneralReq jsonBody);

    // add equiptment approval
    @Headers("Content-Type: application/json")
    @POST("admin/changeEmployeeEquipmentRequest")
    Call<EditProf>addequiptmt(@Header("Authorization") String auth, @Body AddEquiptReq jsonBody);
    // add general approval
    @Headers("Content-Type: application/json")
    @POST("admin/changeEmployeeEquipmentRequest")
    Call<EditProf>rejectequiptmt(@Header("Authorization") String auth, @Body AddEquiptReq jsonBody);

    // approved leave approval
    @Headers("Content-Type: application/json")
    @POST("admin/changeEmployeeLeaveRequest")
    Call<EditProf>leaveapproval(@Header("Authorization") String auth, @Body AddLeaveApproval jsonBody);

    // reject leave approval
    @Headers("Content-Type: application/json")
    @POST("admin/changeEmployeeLeaveRequest")
    Call<EditProf>rejectleaveappr(@Header("Authorization") String auth, @Body AddLeaveApproval jsonBody);



    //    add user skill
    @Headers("Content-Type: application/json")
    @POST("admin/addEditUserSkills")
    Call<AddExpById>addskill(@Header("Authorization") String auth, @Body AddUserSkills jsonBody);

}

