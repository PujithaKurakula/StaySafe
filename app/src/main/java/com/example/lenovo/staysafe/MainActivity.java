package com.example.lenovo.staysafe;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1000;
    private static final int REQUEST_PHONE_CALL =1001 ;
    public boolean up, down;
    int i = 0;

    static Context context;
    String[] phoneNumber = {"9440706589","8985901127","9030833398"};
    String message = "Hi! I am in Danger...Plz,help me!!!";
    EditText con1,con2,con3;
    Button button;
    TextView tv;
    ImageView iv;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         con1=findViewById(R.id.con1);
         con2=findViewById(R.id.con2);
         con3=findViewById(R.id.con3);
         button = findViewById(R.id.done);
         iv=findViewById(R.id.image);
        builder = new AlertDialog.Builder(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Press Volume buttons for 3 times to make call and send  messages", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        builder.setMessage(phoneNumber[0]+phoneNumber[1]+phoneNumber[2]) .setTitle("Contacts");

        //Setting message manually and performing action on button click
        builder.setMessage("Press Volume button for 3 times to start call"+
        "\n"+"you can change contacts in settings")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Go to settings to change contacts",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle("Contacts");
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            con1.setText(phoneNumber[0]);
            con2.setText(phoneNumber[1]);
            con3.setText(phoneNumber[2]);

            con1.setVisibility(View.VISIBLE);
            con2.setVisibility(View.VISIBLE);
            con3.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);
            return true;
        }


        if (id == R.id.action_share) {

            Toast.makeText(this, "Sharing  message to WHATSAPP", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT,message);
            i.setPackage("com.whatsapp");
            startActivity(i);



            return true;
        }

        if (id == R.id.action_about) {

            Toast.makeText(this, "About Stay Secure!!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,About.class);
            startActivity(intent);


            return true;
        }

        if (id==R.id.action_hos){
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=hospitals");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

            return  true;

        }

        if (id==R.id.action_pol){
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=policestations");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

            return  true;

        }




        return super.onOptionsItemSelected(item);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            down = true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            up = true;
        }
        if (up && down) {
            i++;
        }


        if (i == 3 ) {
                i = 0;

                Toast.makeText(getApplicationContext(), "Sharing Emergency Messages", Toast.LENGTH_SHORT).show();
                sendSMSMessage();
                MakeAPhone();
            }


            return true;

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

    public void Submit(View view) {

        String ph1=con1.getText().toString();
        String ph2=con2.getText().toString();
        String ph3=con3.getText().toString();
        if(ph1!=null && ph2!=null && ph3!=null){
            phoneNumber[0]=ph1;
            phoneNumber[1]=ph2;
            phoneNumber[2]=ph3;
        }
            else{
            Toast.makeText(MainActivity.this, "You Need to give 3 numbers", Toast.LENGTH_LONG).show();
        }
        con1.setVisibility(View.INVISIBLE);
        con2.setVisibility(View.INVISIBLE);
        con3.setVisibility(View.INVISIBLE);
        iv.setVisibility(View.VISIBLE);

        button.setVisibility(View.INVISIBLE);


    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            down = false;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            up = false;
        }
        return true;
    }



}

