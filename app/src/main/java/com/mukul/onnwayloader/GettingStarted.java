package com.mukul.onnwayloader;

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

import com.mukul.onnwayloader.otp.NumberActivity;

public class GettingStarted extends AppCompatActivity {

    private CardView gettingStartedCard;
    private TextView gettingStartedTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        gettingStartedCard = findViewById(R.id.get_started_card);
        gettingStartedTv = findViewById(R.id.get_started_tv);

        gettingStartedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GettingStarted.this, NumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gettingStartedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GettingStarted.this, NumberActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
