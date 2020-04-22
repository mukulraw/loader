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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwayloader.AllApiIneterface;
import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.SharePreferenceUtils;
import com.mukul.onnwayloader.loginBean;
import com.mukul.onnwayloader.networking.AppController;
import com.mukul.onnwayloader.networking.Post;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EnterNumberActivity extends AppCompatActivity {

    /*
     *This activity is created to get the mobile number input from the user.
     * after taking the input, you will go to the OtpActivity of the com.sumit.onnwayloader.otp package
     */

    public static String mCurrentMobileNumber;

    private TextView getOtpTv;
    private CardView getOtpCard;
    private EditText userMobile;

    ProgressBar progress;

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
        progress = findViewById(R.id.progressBar3);
        getOtpCard = findViewById(R.id.request_otp_card);

        userMobile = findViewById(R.id.user_mobile_et);

        getOtpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentMobileNumber = userMobile.getText().toString();

                //new Post().doPost(EnterNumberActivity.this, userMobile.getText().toString());

                sendOTP();

            }
        });

        getOtpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentMobileNumber = userMobile.getText().toString();

                sendOTP();

            }
        });
    }

    void sendOTP() {

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

        Call<loginBean> call = cr.login(mCurrentMobileNumber, SharePreferenceUtils.getInstance().getString("token"));

        call.enqueue(new Callback<loginBean>() {
            @Override
            public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                if (response.body().getStatus().equals("1")) {


                    SharePreferenceUtils.getInstance().saveString("phone" , response.body().getPhone());
                    SharePreferenceUtils.getInstance().saveString("name" , response.body().getName());
                    SharePreferenceUtils.getInstance().saveString("email" , response.body().getEmail());
                    SharePreferenceUtils.getInstance().saveString("gst" , response.body().getGst());
                    SharePreferenceUtils.getInstance().saveString("image" , response.body().getImage());

                    Intent intent = new Intent(EnterNumberActivity.this, OtpActivity.class);
                    intent.putExtra("phone", mCurrentMobileNumber);
                    intent.putExtra("otp", response.body().getOtp());
                    intent.putExtra("id", response.body().getUserId());
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(EnterNumberActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

}

