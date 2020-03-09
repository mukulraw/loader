package com.mukul.onnwayloader.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mukul.onnwayloader.MainActivity;
import com.mukul.onnwayloader.R;

import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class OtpActivity extends AppCompatActivity implements OTPListener{

    /*
    * This activity is designed for getting the OTP from the mesaage provider
    * If the user is already registered, then we will store the data of the user in the local database
    * and if the user is not registered, then we will launch the FirstTimeProfileActivity
    * */

    private EditText otp1, otp2, otp3, otp4;
    private TextView mobileTv;

    String phone , otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phone = getIntent().getStringExtra("phone");
        otp = getIntent().getStringExtra("otp");

        //setting the color of STATUS BAR of activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        otp1 = findViewById(R.id.otp_et1);
        otp2 = findViewById(R.id.otp_et2);
        otp3 = findViewById(R.id.otp_et3);
        otp4 = findViewById(R.id.otp_et4);

        mobileTv = findViewById(R.id.mobile_tv);
        mobileTv.setText(EnterNumberActivity.mCurrentMobileNumber);
        //automatic OTP reader, library used: "swarajsaaj:otpreader:1.1"
        OtpReader.bind(OtpActivity.this,"ONNWAY");
    }

    @Override
    public void otpReceived(String messageText) {

        //when otp received then this method called
        Log.i("otp", messageText);
        int length = messageText.length();
        Log.i("otp", "Length:" + length);
        String substr = messageText.substring(length - 4, length);

        String s1 = String.valueOf(substr.charAt(0));
        String s2 = String.valueOf(substr.charAt(1));
        String s3 = String.valueOf(substr.charAt(2));
        String s4 = String.valueOf(substr.charAt(3));
        otp1.setText(s1);
        otp2.setText(s2);
        otp3.setText(s3);
        otp4.setText(s4);

        if (substr.equals(otp))
        {
            Intent intent = new Intent(OtpActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
            Toast.makeText(OtpActivity.this, "Otp Received", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(OtpActivity.this, "Otp Received", Toast.LENGTH_LONG).show();
        }




    }
}
