package com.jec.covid19;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;

    LoadingDialog(Activity myactivity){

        activity=myactivity;

    }

    void startLoading(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.customdialog, null));
        builder.setCancelable(false);

        alertDialog=builder.create();
        alertDialog.show();
    }

    void endDialog(){
        alertDialog.dismiss();
    }
}
