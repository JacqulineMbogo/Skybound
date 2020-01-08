package com.example.skybound.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skybound.R;


public class AppUtilits {
        public static void displayMessage(Context mContext, String message){

            MessageDialog messageDialog = null;
            if (messageDialog == null)
                messageDialog = new MessageDialog(mContext, message);
            messageDialog.displayMessageShow();

        }


    public static AlertDialog createProgressBar(Context context)
    {
        LayoutInflater layoutInflater   =   (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view   =   layoutInflater.inflate(R.layout.progressbar, null, false);

        AlertDialog.Builder alertDialog =   new AlertDialog.Builder(context);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AlertDialog dialog  =   alertDialog.create();
        dialog.show();

        return dialog;
    }

    public void createToaster(Context context, String message, int duration, int icon)
    {
        LayoutInflater layoutInflater   =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view   =   layoutInflater.inflate(R.layout.toaster_layoru, null, false);
        Toast toast =   new Toast(context);
        toast.setView(view);

        ((TextView)view.findViewById(R.id.toaster_txt_message)).setText(message);
        ((ImageView)view.findViewById(R.id.toaster_icon)).setImageResource(icon);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.show();

    }

    public static void destroyDialog(AlertDialog dialog)
    {
        dialog.dismiss();
    }


}
