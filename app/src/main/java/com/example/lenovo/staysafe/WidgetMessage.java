package com.example.lenovo.staysafe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class WidgetMessage extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int REQUEST_PHONE_CALL = 1;
    public boolean up, down;
    int i = 0;

    static Context context;
    static Activity activity;
    String[] phoneNumber = {"9440706589", "8985901127", "9030833398"};
    String message = "I am in Danger!!!Plz help me!!!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_message);
        sendSMSMessage();
        MakeAPhone();
        finish();
    }


    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

            }
        }

        else{
            SmsManager smsManager = SmsManager.getDefault();
            for(String number:phoneNumber) {
                smsManager.sendTextMessage(number, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.",
                        Toast.LENGTH_LONG).show();
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(context, ""+grantResults[0], Toast.LENGTH_SHORT).show();
                    SmsManager smsManager = SmsManager.getDefault();
                    for(String number:phoneNumber) {
                        smsManager.sendTextMessage(number, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

            }
            case REQUEST_PHONE_CALL:{

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Toast.makeText(context, "index call"+grantResults[0], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_CALL);
                    String ph =phoneNumber[0];
                    i.setData(Uri.parse("tel:"+ph));

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(i);
                    }
                }
            }
        }
    }
    public void MakeAPhone() {

        Intent i = new Intent(Intent.ACTION_CALL);
        String ph =phoneNumber[0];
        i.setData(Uri.parse("tel:"+ph));
            startActivity(i);
         }

}
