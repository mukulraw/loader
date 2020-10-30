package com.onnway.onnway.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.onnway.onnway.AllApiIneterface;
import com.onnway.onnway.MainActivity;
import com.onnway.onnway.R;
import com.onnway.onnway.SharePreferenceUtils;
import com.onnway.onnway.loginBean;
import com.onnway.onnway.networking.AppController;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import swarajsaaj.smscodereader.interfaces.OTPListener;
import swarajsaaj.smscodereader.receivers.OtpReader;

public class OtpActivity extends AppCompatActivity implements OTPListener {

    /*
     * This activity is designed for getting the OTP from the mesaage provider
     * If the user is already registered, then we will store the data of the user in the local database
     * and if the user is not registered, then we will launch the FirstTimeProfileActivity
     * */

    private TextView mobileTv, resend;

    String phone, otp, id;

    private OtpView otpview;
    ProgressBar progress;

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
        resend = findViewById(R.id.textView5);
        progress = findViewById(R.id.progressBar4);

        mobileTv.setText(phone);
        //automatic OTP reader, library used: "swarajsaaj:otpreader:1.1"
        OtpReader.bind(OtpActivity.this, "SNDOTP");


        otpview.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String substr) {

                if (substr.equals(otp)) {

                    SharePreferenceUtils.getInstance().saveString("userId", id);

                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                    Toast.makeText(OtpActivity.this, "OTP Verified", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                }

            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress.setVisibility(View.VISIBLE);

                AppController b = (AppController) getApplicationContext();

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.HEADERS);
                logging.level(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .client(client)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<loginBean> call = cr.resend(phone);

                call.enqueue(new Callback<loginBean>() {
                    @Override
                    public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                        if (response.body().getStatus().equals("1")) {

                            SharePreferenceUtils.getInstance().saveString("phone", response.body().getPhone());
                            SharePreferenceUtils.getInstance().saveString("name", response.body().getName());
                            SharePreferenceUtils.getInstance().saveString("email", response.body().getEmail());
                            SharePreferenceUtils.getInstance().saveString("gst", response.body().getGst());
                            SharePreferenceUtils.getInstance().saveString("image", response.body().getImage());

                            otp = response.body().getOtp();

                        } else {
                            Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<loginBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        t.printStackTrace();
                    }
                });

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
