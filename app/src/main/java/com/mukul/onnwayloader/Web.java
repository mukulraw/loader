package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toolbar;

public class Web extends AppCompatActivity {
    WebView web;

    String title , url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        web = findViewById(R.id.web);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle(title);
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        web.loadUrl(url);

    }
}