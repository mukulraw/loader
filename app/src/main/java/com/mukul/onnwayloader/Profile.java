package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toolbar;

public class Profile extends AppCompatActivity {

    EditText name , email , city , company;
    RadioGroup type;
    Button submit;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Short Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name = findViewById(R.id.editText9);
        email = findViewById(R.id.editText10);
        city = findViewById(R.id.editText11);
        company = findViewById(R.id.editText12);
        type = findViewById(R.id.textView63);
        submit = findViewById(R.id.button);
        progress = findViewById(R.id.progressBar);









    }
}
