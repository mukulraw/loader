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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mukul.onnwayloader.AllApiIneterface;
import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.SharePreferenceUtils;
import com.mukul.onnwayloader.loginBean;
import com.mukul.onnwayloader.networking.AppController;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NumberActivity extends AppCompatActivity {

    /*
     * This activity is used to transfer the user to the EnterNumberActivity
     * to enter the mobile number by the user.
     * */

    private EditText cardView;
    Button send;
    String mCurrentMobileNumber;
    ProgressBar progress;

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

        cardView = findViewById(R.id.cardView);
        send = findViewById(R.id.button16);
        progress = findViewById(R.id.progressBar4);

        //starting the EnterNumberActivity
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCurrentMobileNumber = cardView.getText().toString();

                if (mCurrentMobileNumber.length() == 10)
                {
                    sendOTP();
                }
                else
                {
                    Toast.makeText(NumberActivity.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
                }



            }
        });




        //starting the EnterNumberActivity

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

                    Intent intent = new Intent(NumberActivity.this, OtpActivity.class);
                    intent.putExtra("phone", mCurrentMobileNumber);
                    intent.putExtra("otp", response.body().getOtp());
                    intent.putExtra("id", response.body().getUserId());
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(NumberActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
