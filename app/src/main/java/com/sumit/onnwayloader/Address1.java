package com.sumit.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

public class Address1 extends AppCompatActivity {
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address1);

        Toolbar mToolbar = findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Address");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progress = findViewById(R.id.progressBar);

    }
}
