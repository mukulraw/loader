package com.onnway.kedsons.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.onnway.kedsons.R;
import com.onnway.kedsons.addprofiledetails.UserData;
import com.onnway.kedsons.networking.Post;
import com.onnway.kedsons.otp.EnterNumberActivity;
import com.onnway.kedsons.shippingaddress.ShippingAddress;
import com.onnway.kedsons.sqlite.GetSetUserData;

public class UserProfileActivity extends AppCompatActivity {

    Button updateProfileBtn;
    GetSetUserData getSetUserData;

    private EditText userName, userEmail, userAddress, userCity;
    private ImageView individual, company;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userAddress = findViewById(R.id.user_address);
        userCity = findViewById(R.id.user_city);
        individual = findViewById(R.id.individual_image);
        company = findViewById(R.id.company_image);

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userType = "Individual";
            }
        });
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userType = "Company";
            }
        });
        getSetUserData = new GetSetUserData(UserProfileActivity.this);
//        //adding toolbar
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_user_profile);
//        mToolbar.setTitle("My Account");
//        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        updateProfileBtn = findViewById(R.id.update_profile_btn);
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ShippingAddress.order == 1) {
                    new Post().updateUser(getApplication(), new UserData());
                }
                getSetUserData.updateData(EnterNumberActivity.mCurrentMobileNumber,
                        userName.getText().toString(), userEmail.getText().toString(),
                        userAddress.getText().toString(), userCity.getText().toString(), userType);
            }
        });
    }
}
