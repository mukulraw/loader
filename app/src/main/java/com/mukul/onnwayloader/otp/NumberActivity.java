package com.mukul.onnwayloader.otp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mukul.onnwayloader.R;

public class NumberActivity extends AppCompatActivity {

    /*
     * This activity is used to transfer the user to the EnterNumberActivity
     * to enter the mobile number by the user.
     * */

    TextView tvPhoneNumber;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        //setting the color of STATUS BAR of activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        tvPhoneNumber = findViewById(R.id.mPhoneNumber);
        cardView = findViewById(R.id.cardView);

        //starting the EnterNumberActivity
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NumberActivity.this, EnterNumberActivity.class);
                startActivity(intent);
            }
        });

        //starting the EnterNumberActivity
        tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NumberActivity.this, EnterNumberActivity.class);
                startActivity(intent);
            }
        });
    }

}
