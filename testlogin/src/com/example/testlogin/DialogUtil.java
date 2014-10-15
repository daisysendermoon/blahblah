package com.example.testlogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class DialogUtil
{
    public static void showDialog(final Context ctx, String msg , boolean closeSelf)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);
        if(closeSelf)
        {
            builder.setPositiveButton("Confirm", new OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ((Activity) ctx).finish();
                }
            });       
        }
        else
        {
            builder.setPositiveButton("Confirm", null);
        }
        builder.create().show();
    }   
    public static void showDialog(Context ctx , View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setView(view).setCancelable(false).setPositiveButton("Confirm", null);
        builder.create().show();
    }
}