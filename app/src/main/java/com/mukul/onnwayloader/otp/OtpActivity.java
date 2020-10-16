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

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.mukul.onnwayloader.MainActivity;
import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.SharePreferenceUtils;

import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class OtpActivity extends AppCompatActivity implements OTPListener{

    /*
    * This activity is designed for getting the OTP from the mesaage provider
    * If the user is already registered, then we will store the data of the user in the local database
    * and if the user is not registered, then we will launch the FirstTimeProfileActivity
    * */

    private TextView mobileTv;

    String phone , otp , id;

    private OtpView otpview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phone = getIntent().getStringExtra("phone");
        otp = getIntent().getStringExtra("otp");
        id = getIntent().getStringExtra("id");

        //setting the color of STATUS BAR of activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        otpview = findViewById(R.id.linearLayout);
        mobileTv = findViewById(R.id.mobile_tv);
        mobileTv.setText(phone);
        //automatic OTP reader, library used: "swarajsaaj:otpreader:1.1"
        OtpReader.bind(OtpActivity.this,"SNDOTP");


        otpview.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String substr) {

                if (substr.equals(otp))
                {

                    SharePreferenceUtils.getInstance().saveString("userId" , id);

                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                    Toast.makeText(OtpActivity.this, "OTP Verified", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public void otpReceived(String messageText) {

        //when otp received then this method called
        Log.i("otp", messageText);
        int length = messageText.length();
        Log.i("otp", "Length:" + length);
        String substr = messageText.substring(length - 4, length);

        otpview.setText(substr);

    }
}
