package com.khazana.hrm.Utility;


public class Config {

//private static final String BASE_URL = "http://hrmapis.cresol.pk/";
private static final String BASE_URL = "https://hris.khazanapk.com/";

  // public static final String SIGNIN =BASE_URL+"hrm_apis/api/login";

    public static final String SIGNIN =BASE_URL+"api/login/";

    //https://devhris.khazanapk.com/api/admin/getProfile

    private static final String ADMIN_BASE_URL = "https://hris.khazanapk.com/api";

    public static final String TOKEN =ADMIN_BASE_URL+"/admin/getUserById?id=";
    public static final String QUALIFY =ADMIN_BASE_URL+"/admin/getAllQualificationOfUser?userId=";
    public static final String USR_EXP =ADMIN_BASE_URL+"/admin/getAllJobsOfUser?userId=";
    public static final String USR_CONT =ADMIN_BASE_URL+"/admin/getAllContactOfUser?userId=";
    public static final String USR_SKILL =ADMIN_BASE_URL+"/admin/getAllSkillsOfUser?userId=";
    public static final String ALL_SKILL =ADMIN_BASE_URL+"/admin/getAllSkills";
    public static final String ALL_GENERALL =ADMIN_BASE_URL+"/admin/getEmployeeGeneralRequest?employee_id=0&start=0&sort=&order=Desc&search=null";
    public static final String ALL_EQUIPT=ADMIN_BASE_URL+"/admin/getEmployeeEquipmentRequest?employee_id=0&start=0&sort=&order=Desc&search=null";

    public static final String UGET_LEAVES1 =BASE_URL+"hrm_apis/api/uleaves_1";
    public static final String UGET_LEAVES0 =BASE_URL+"hrm_apis/api/uleaves_0";
    public static final String GET_LEAVES =BASE_URL+"hrm_apis/api/userallleaves";
    public static final String GET_LEAVES1 =BASE_URL+"hrm_apis/api/leaves_1";
    public static final String GET_LEAVES0 =BASE_URL+"hrm_apis/api/leaves_0";
    public static final String ADD_LEAVE =BASE_URL+"hrm_apis/api/insert";
    public static final String APPR_LEAVE =BASE_URL+"hrm_apis/api/approved";
    public static final String GET_USER =BASE_URL+"hrm_apis/api/user";

    public static final String UGET_MLEAVES1 =BASE_URL+"hrm_apis/api/datee1";
    public static final String UGET_MLEAVES0 =BASE_URL+"hrm_apis/api/datee0";

}

//    public static final String GET_LEAVES =BASE_URL+"hrm_apis/api/get_leaves";
