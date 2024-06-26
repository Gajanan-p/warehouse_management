package com.ssinfomate.warehousemanagement.ui.dialog;


import android.app.AlertDialog;
import android.content.Context;


public class DialogHelper {

    public static AlertDialog getAlertWithMessage(String name, String message, Context context){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setTitle(name);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OKAY", null);

        AlertDialog alertDialog = builder1.create();

        return alertDialog;

    }
}

