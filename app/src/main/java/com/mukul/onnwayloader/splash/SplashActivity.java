package com.mukul.onnwayloader.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mukul.onnwayloader.SharePreferenceUtils;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions.Options;
import com.mukul.onnwayloader.GettingStarted;
import com.mukul.onnwayloader.MainActivity;
import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.sqlite.GetSetUserData;

import java.util.ArrayList;

import static com.nabinbhandari.android.permissions.Permissions.check;

public class SplashActivity extends AppCompatActivity {

    /*
     * This activity is used to dosplay the splash screen of the application, whenever the user opens the application
     * Also, all the permissions are take from the user in this activity only.
     * */

    //creating object of GetSetUserData class, this is used to check if the user is already logged in or not
    GetSetUserData getSetUserData;

    //for displaying progress bar
    ProgressBar progressBar;

    public static String mActiveUserMobile;
    public static String mActiveUserName;
    public static String mActiveUserEmail;
    public static String mActiveUserAddress;
    public static String mActiveUserCity;
    public static String mActiveUserType;

    //taking bad dangerous permission from the user at runntime
    String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //setting the color of STATUS BAR of activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        progressBar = findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.VISIBLE);

        getSetUserData = new GetSetUserData(SplashActivity.this);

        //here we are using the "com.nabinbhandari.android:permissions:3.8" dependency to take permission from user
        String rationale = "Please allow all permissions to continue";
        Options options = new Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                //if the permission is granted then do your task
                new Handler().postDelayed(
                        //delay of 1 seconds i.e. progress bar will be visible for 1 second
                        new Runnable() {
                            @Override
                            public void run() {

                                String userId = SharePreferenceUtils.getInstance().getString("userId");

                                if (userId.length() > 0) {
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    Intent intent = new Intent(SplashActivity.this, GettingStarted.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }


                            }
                        }, 1000);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                //if the permission is denied, then display the message
                Toast.makeText(context, "Please allow all permissions to continue", Toast.LENGTH_SHORT).show();
            }
        });
    }
}