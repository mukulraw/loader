package com.sumit.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

public class Shipment extends AppCompatActivity {


    TextView orderid , orderdate , truck , source , destination , material , weight;
    TextView freight , other , cgst , sgst , grand;
    CheckBox insurance;
    Button confirm , request;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment2);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Shipment Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        orderid = findViewById(R.id.textView16);
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);

        freight = findViewById(R.id.textView29);
        other = findViewById(R.id.textView35);
        cgst = findViewById(R.id.textView36);
        sgst = findViewById(R.id.textView37);
        grand = findViewById(R.id.textView38);
        insurance = findViewById(R.id.checkBox);
        progress = findViewById(R.id.progressBar);


    }
}
