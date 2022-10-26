package com.khazana.hrm.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.khazana.hrm.UI.SignIn;


public class SessionManager {
    // Shared Preferences
    private SharedPreferences pref;
    // Editor for Shared preferences
    private Editor editor;
    // Context
    private Context _context;
    // Shared pref mode
    private int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "UserPref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // email (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    // Name (make variable public to access from outside)
    public static final String KEY_FNAME = "firstname";
    public static final String KEY_LNAME = "lastname";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMPCODE = "empcode";
    public static final String KEY_USERID = "userid";
    public static final String KEY_USERDATEJOINED = "userdatejoined";
    public static final String KEY_USERIMAGE = "userimage";

    // User password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_CNIC = "nicNumber";
    public static final String KEY_Qualification = "qualification";
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_BUILDING = "building";
    public static final String KEY_HomeAddress = "homeAddress";
    public static final String KEY_OfficeAddress = "officeAddress";
    public static final String KEY_DOB = "dob";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_PROFIMG = "prof_img";

    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONENUMBER = "phone_num";
    public static final String KEY_CITY = "city";
    public static final String KEY_REPORTTO = "ReportTo";
    public static final String KEY_EXPIRE = "expiretime";



    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    /**
     * Create login session  KEY_ADMINNAME
     */

    public void createLoginSession(String username) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing password in pref
        // Storing name in pref
        editor.putString(KEY_USERNAME, username);
//        editor.putString(KEY_TYPE, role);
        // commit changes
        editor.commit();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public boolean checkLogin()
    {
        // Check login status
        if (this.isLoggedIn()) {
            return true;
        }
        return false;
    }



    /**
     * Clear session details
     */
    public boolean logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SignIn.class);
        // Closing all the Requests
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        // Add new Flag to start new Activity
        return true;
    }



    /**
     * Quick check for login
     **/
    // Get Login State
    private boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void saveUserID(String id) {
        editor.putString(KEY_USERID, id);
        editor.commit();
    }

    public void saveUserDateJoined(String date) {
        editor.putString(KEY_USERDATEJOINED, date);
        editor.commit();
    }



    public void saveUserName(String name) {
        editor.putString(KEY_USERNAME, name);
        editor.commit();
    }

    public void saveFirstName(String fname) {
        editor.putString(KEY_FNAME, fname);
        editor.commit();

    }
    public void saveEmpcode(String empcode) {
        editor.putString(KEY_EMPCODE, empcode);
        editor.commit();

    }


    public void saveLastName(String lname) {
        editor.putString(KEY_LNAME, lname);
        editor.commit();
    }

    public void savecontact(String contact) {
        editor.putString(KEY_CONTACT, contact);
        editor.commit();
    }

    public void saveCnicNumber(String cnicNumber) {
        editor.putString(KEY_CNIC, cnicNumber);
        editor.commit();
    }
    public void saveQualification(String qualification) {
        editor.putString(KEY_Qualification, qualification);
        editor.commit();
    }
    public void saveDepartment(String department) {
        editor.putString(KEY_DEPARTMENT, department);
        editor.commit();
    }
    public void saveBuilding(String buliding) {
        editor.putString(KEY_BUILDING, buliding);
        editor.commit();
    }


    public void saveHomeAddress(String homeAddress) {
        editor.putString(KEY_HomeAddress, homeAddress);
        editor.commit();
    }
    public void saveOfficeAddress(String officeAddress) {
        editor.putString(KEY_OfficeAddress, officeAddress);
        editor.commit();
    }

    public void saveOfdob(String officeAddress) {
        editor.putString(KEY_DOB, officeAddress);
        editor.commit();
    }

    public void saveOfexpire(String expiretime) {
        editor.putString(KEY_EXPIRE, expiretime);
        editor.commit();
    }

    public void saveUserImage(String image) {
        editor.putString(KEY_USERIMAGE, image);
        editor.commit();
    }

    public void saveUserAddress(String address) {
        editor.putString(KEY_ADDRESS, address);
        editor.commit();
    }


    public void saveProfImage(String homeAddress) {
        editor.putString(KEY_PROFIMG, homeAddress);
        editor.commit();
    }

    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public void savePassword(String password) {
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public void saveUserEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }


    public void saveUserPhoneNumber(String phonenumber) {
        editor.putString(KEY_PHONENUMBER, phonenumber);
        editor.commit();
    }
    public void saveReprtTo(String reportto) {
        editor.putString(KEY_REPORTTO, reportto);
        editor.commit();
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, null);
    }

    public String getUserID() {
        return pref.getString(KEY_USERID, null);
    }


    public String getUserName() {
        return pref.getString(KEY_USERNAME, null);
    }

    public String getUserAddress() {
        return pref.getString(KEY_ADDRESS, null);
    }
    public String getUserimage() {return pref.getString(KEY_USERIMAGE, null);}
    public String getDob() {return pref.getString(KEY_DOB, null);}

    public String getUserPhonenumber() {return pref.getString(KEY_PHONENUMBER, null);}
    public String getexpiretime() {return pref.getString(KEY_EXPIRE, null);}
    public String getUserDateJoined() {return pref.getString(KEY_USERDATEJOINED, null);}
    public String getNo() {
        return pref.getString(KEY_CONTACT, null);
    }
    public String getProfimg() {
        return pref.getString(KEY_PROFIMG, null);
    }
    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }
    public String getHomeaddr()    { return pref.getString(KEY_HomeAddress, null); }
    public String getCinic()    { return pref.getString(KEY_CNIC, null); }
    public String getFirstName() {return pref.getString(KEY_FNAME, null);}
    public String getLastName() {return pref.getString(KEY_LNAME, null);}
    public String getEmpcode() {return pref.getString(KEY_EMPCODE, null);}

    public String getDepartment() {return pref.getString(KEY_DEPARTMENT, null);}
    public String getBuilding() {return pref.getString(KEY_BUILDING, null);}
    public String getReportTo() {return pref.getString(KEY_REPORTTO, null);}

}