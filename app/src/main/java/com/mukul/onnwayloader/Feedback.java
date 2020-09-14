package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwayloader.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwayloader.networking.AppController;
import com.mukul.onnwayloader.truckTypePOJO.truckTypeBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Feedback extends AppCompatActivity {

    ProgressBar progress;
    Spinner subject;
    EditText message;
    Button submit;
    List<String> sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        sub = new ArrayList<>();

        Toolbar mToolbar = findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Give Feedback");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progress = findViewById(R.id.progressBar);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.editTextTextPersonName);
        submit = findViewById(R.id.button17);

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getSubject();

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                sub.clear();

                for (int i = 0; i < response.body().size(); i++) {
                    sub.add(response.body().get(i).getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Feedback.this,
                        android.R.layout.simple_list_item_1, sub);

                subject.setAdapter(adapter);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = subject.getSelectedItem().toString();
                String m = message.getText().toString();

                if (s.length() > 0) {
                    if (m.length() > 0) {

                        progress.setVisibility(View.VISIBLE);

                        AppController b = (AppController) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                        Call<confirm_full_bean> call = cr.submitFeedback(
                                SharePreferenceUtils.getInstance().getString("userId"),
                                s,
                                m
                        );


                        call.enqueue(new Callback<confirm_full_bean>() {
                            @Override
                            public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {
                                if (response.body().getStatus().equals("1")) {
                                    Toast.makeText(Feedback.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(Feedback.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<confirm_full_bean> call, Throwable t) {

                            }
                        });


                    } else {
                        Toast.makeText(Feedback.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Feedback.this, "Please select a subject", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}