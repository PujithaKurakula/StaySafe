package com.example.lenovo.staysafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tv=findViewById(R.id.text);
        tv.setText("Stay Secure is android mobile application used to make emergency calls & messages when you are in danger.\n" +
                "The main focus of this app is to share their emergency environment in critical conditions with their mobile\n");
    }
}
