package com.sumit.onnwayloader.otp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.sumit.onnwayloader.R;
import com.sumit.onnwayloader.networking.Post;

public class EnterNumberActivity extends AppCompatActivity {

    /*
    *This activity is created to get the mobile number input from the user.
    * after taking the input, you will go to the OtpActivity of the com.sumit.onnwayloader.otp package
    */

    public static String mCurrentMobileNumber;

    private TextView getOtpTv;
    private CardView getOtpCard;
    private EditText userMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_enter_number);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.backimagered);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getOtpTv = findViewById(R.id.request_otp_tv);
        getOtpCard = findViewById(R.id.request_otp_card);

        userMobile = findViewById(R.id.user_mobile_et);

        getOtpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentMobileNumber = userMobile.getText().toString();

                new Post().doPost(EnterNumberActivity.this, userMobile.getText().toString());
                Intent intent = new Intent(EnterNumberActivity.this, OtpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getOtpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentMobileNumber = userMobile.getText().toString();

                new Post().doPost(EnterNumberActivity.this, userMobile.getText().toString());
                Intent intent = new Intent(EnterNumberActivity.this, OtpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

