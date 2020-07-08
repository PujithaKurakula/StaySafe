package com.example.lenovo.staysafe;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class SoSWidget extends AppWidgetProvider {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;
    private static final int REQUEST_PHONE_CALL =1 ;
    public boolean up, down;
    int i = 0;
    String[] perms={"android.permission.CALL_PHONE"};

    static Context context;
    static  Activity activity;
    String[] phoneNumber = {"9440706589","8985901127","9030833398"};
    String message = "I am in danger!!!!!";
    private static final String MyOnClick3 = "myOnClickTag3";
    Button b;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);


        // Construct the RemoteViews object
        Intent intent=new Intent(context,WidgetMessage.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,2,intent,0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.so_swidget);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public void widget_call(){
    }


}

