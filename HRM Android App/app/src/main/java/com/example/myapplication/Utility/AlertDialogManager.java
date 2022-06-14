package com.example.myapplication.Utility;

/**
 * Created by Abubakar on 21/08/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;


//for showing alert dialog
public class AlertDialogManager
{
    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     *                - pass null if you don't want icon
     */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        if ((Boolean) false != null)
            // Setting OK Button
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,
                    "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                        }
                    });
        // Showing Alert Message
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_NEUTRAL).setTextColor(Color.WHITE);// context.getResources().getColor(Color.WHITE));
    }


    //Showing alert if location is not enabled
    public void showLocationAlertDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(context.getResources().getString(R.string.gps_network_not_enabled));

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(myIntent);
                //get gps
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {

            }
        });

    }





    //Showing alert if account is deleted by admin
//    public void showAccountDeletedAlertDialog(Context context, String title, String message, Boolean status) {
//        final SessionManager session;
//        session = new SessionManager(context);
//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        // Setting Dialog Title
//        alertDialog.setTitle(title);
//        // Setting Dialog Message
//        alertDialog.setMessage(message);
//
//        if ((Boolean) false != null)
//            // Setting OK Button
//            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,
//                    "OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int which) {
//                            session.logoutUser();
//                        }
//                    });
//        // Showing Alert Message
//        alertDialog.show();
//        alertDialog.getButton(alertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.colorPrimary));
//    }
}