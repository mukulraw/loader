package com.mukul.onnwayloader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mukul.onnwayloader.addprofiledetails.UserData;
import com.mukul.onnwayloader.networking.Post;
import com.mukul.onnwayloader.otp.CheckingPreRegistered;
import com.mukul.onnwayloader.otp.EnterNumberActivity;
import com.mukul.onnwayloader.splash.SplashActivity;
import com.mukul.onnwayloader.sqlite.GetSetUserData;
import com.mukul.onnwayloader.userprofile.ProfileActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //BottomNavigation and FrameLayout
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    //Fragments
    private FindTruckFragment findTruckFragment;
    private WaitingTruckFragment waitingTruckFragment;
    private MyOrderFragment myOrderFragment;
    private MyQuoteFragment myQuoteFragment;

    public CheckingPreRegistered checkingPreRegistered = new CheckingPreRegistered();

    public static int ifRegistered = -1;

    CircleImageView profileImageView;

    GetSetUserData getSetUserData;

    UserData userData;

    TextView phone, name;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the color of STATUS BAR of activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(Color.rgb(105, 105, 105));
        }
        //check if user is preregistered?
        checkingPreRegistered.entered_mobile = EnterNumberActivity.mCurrentMobileNumber;
        new Post().getIfUserRegistered(getApplication(), checkingPreRegistered);
        userData = new UserData();

        getSetUserData = new GetSetUserData(MainActivity.this);

        setTitle(null);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //drawerLayout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Bottom Navigation and FrameLayout
        bottomNavigationView = findViewById(R.id.bottom_nav);

        frameLayout = findViewById(R.id.main_frame);

        //constructor for the fragments
        findTruckFragment = new FindTruckFragment();
        waitingTruckFragment = new WaitingTruckFragment();
        myOrderFragment = new MyOrderFragment();
        myQuoteFragment = new MyQuoteFragment();

        //set the default fragment as findTruckFragment i.e once the app will be launched then this fragment will be opened
        setFragment(findTruckFragment);

        View view = navigationView.getHeaderView(0);


        //changing the fragment on bottom nav click
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.bottom_nav_find_truck:
                        //hideSoftKeyboard(MainActivity.this);
                        setFragment(findTruckFragment);
                        return true;
                    case R.id.bottom_nav_waiting_truck:
                        //hideSoftKeyboard(MainActivity.this);
                        setFragment(waitingTruckFragment);
                        return true;
                    case R.id.bottom_nav_my_orders:
                        //hideSoftKeyboard(MainActivity.this);
                        setFragment(myOrderFragment);
                        return true;
                    case R.id.bottom_nav_my_quotes:
                        //hideSoftKeyboard(MainActivity.this);
                        setFragment(myQuoteFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

        Cursor cursor = getSetUserData.getAllData();
        if (cursor.getCount() == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Post.cityName.equals("")) {
                        ifRegistered = 0;
                        userData.mobileNumber = EnterNumberActivity.mCurrentMobileNumber;
                        userData.userAddress = "Not Found";
                        userData.userCity = "Not Found";
                        userData.userEmail = "Not Found";
                        userData.userType = "Not Found";
                        userData.userName = "Not Found";
                        new Post().registerUser(getApplication(), userData);
                    } else if (Post.cityName.equals("Not Found")) {
                        addData();
                        ifRegistered = 2;
                    } else {
                        addData();
                        ifRegistered = 1;
                        Toast.makeText(MainActivity.this, "Found", Toast.LENGTH_LONG).show();
                    }
                }
            }, 3000);

        }
        profileImageView = view.findViewById(R.id.profile_image_view);
        phone = view.findViewById(R.id.phone);
        name = view.findViewById(R.id.name);





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        phone.setText("Ph. - " + SharePreferenceUtils.getInstance().getString("phone"));
        name.setText(SharePreferenceUtils.getInstance().getString("name"));

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).showImageForEmptyUri(R.drawable.ic_profile_image).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(SharePreferenceUtils.getInstance().getString("image") , profileImageView , options);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, GetProfile.class);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_payment_terms) {

        } else if (id == R.id.nav_security) {

        } else if (id == R.id.nav_terms_and_condition) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FirebaseInstanceId.getInstance().deleteInstanceId();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            SharePreferenceUtils.getInstance().deletePref();
            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(intent);
            finishAffinity();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    //method to set the fragment layout for the selected icon
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    //method to hide Keyboard if active
    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
        );
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void addData() {
        //function to add data in the local DB, to find the SQLite helper class, go to com.sumit.onnwayloader.sqlite.GetSetUserData
        boolean isInserted;
        if (ifRegistered == 0) {
            isInserted = getSetUserData.insertData(Post.mobileNumber, "Not Found", "Not Found", "Not Found", "Not Found", "Not Found");
        } else {
            isInserted = getSetUserData.insertData(Post.mobileNumber, Post.userType, Post.userName, Post.userAddress, Post.cityName, Post.userEmail);
        }

        if (isInserted) {
            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        }
    }
}
