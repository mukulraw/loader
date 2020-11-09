package com.onnway.app.addprofiledetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.onnway.app.MainActivity;
import com.onnway.app.R;
import com.onnway.app.networking.Post;
import com.onnway.app.sqlite.GetSetUserData;

import java.util.Arrays;
import java.util.List;

public class FirstTimeProfileActivity extends AppCompatActivity {

    /*
     * This activity is used to get the user details if then are using the app for the very first time
     * The main aim of this app is to take the user type of the user i.e. "individual" or "company"
     * After clicking the next button, we will move to
     * */

    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static ProgressDialog progressDialog;

    GetSetUserData getSetUserData;

    private EditText userName, userEmail, userAddress, userCity;
    private RadioGroup userType;
    private Button nextButton;

    //storing data entered by the user
    public static String name, email, mobileNumber, type, address, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_profile);

        //setting the color of STATUS BAR of activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        getSetUserData = new GetSetUserData(FirstTimeProfileActivity.this);

        //EditTexts
        userName = findViewById(R.id.input_name_et);
        userEmail = findViewById(R.id.input_email_et);
        userAddress = findViewById(R.id.input_address_et);
        userCity = findViewById(R.id.input_city_et);

        //Radio Group
        userType = findViewById(R.id.user_type_radio_group);

        //Buttons
        nextButton = findViewById(R.id.nextBtn);

        userCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstTimeProfileActivity.this, "Hello", Toast.LENGTH_LONG).show();
                findCity();
            }
        });
        //onclick listener for the nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(FirstTimeProfileActivity.this);
                progressDialog.setTitle("Creating Profile");
                progressDialog.setMessage("Please wait, while we create your profile");
                progressDialog.show();
                progressDialog.setCancelable(false);
                checkData();
            }
        });
    }

    public void checkData() {
        //function to check if data is entered in all the fields or not
        int selectedId = userType.getCheckedRadioButtonId();
        if (selectedId == R.id.individual_radio_btn) {
            type = "individual";
        } else {
            type = "company";
        }
        //getting the values from the EditText
        name = userName.getText().toString();
        email = userEmail.getText().toString();
        address = userAddress.getText().toString();
        city = userCity.getText().toString();
        addData();

        UserData userData = new UserData();
//        userData.mobileNumber = NumberActivity.enteredPhone;
        userData.userName = name;
        userData.userEmail = email;
        userData.userAddress = address;
        userData.userCity = city;
        userData.userType = type;

        new Post().registerUser(this, userData);

        //starting the UserDetailsActivity
        Intent intent = new Intent(FirstTimeProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void addData() {
        //function to add data in the local DB, to find the SQLite helper class, go to com.sumit.onnwayloader.sqlite.GetSetUserData
//        boolean isInserted = getSetUserData.insertData(NumberActivity.enteredPhone, type, name, address, city);
//        if (isInserted) {
//            Toast.makeText(FirstTimeProfileActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
//        }
    }

    public void findCity() {
        Places.initialize(FirstTimeProfileActivity.this, "AIzaSyDD5e-SJP_E8SDLOHYz79IR79pVy6YQOgg");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(FirstTimeProfileActivity.this);

        // Set the fields to specify which types of place data to return.
        List<com.google.android.libraries.places.api.model.Place.Field> fields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME, com.google.android.libraries.places.api.model.Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("ind")
                .setTypeFilter(TypeFilter.CITIES)
                .build(FirstTimeProfileActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                com.google.android.libraries.places.api.model.Place place = Autocomplete.getPlaceFromIntent(data);
                userCity.setText(place.getName());
                Toast.makeText(FirstTimeProfileActivity.this, place.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(FirstTimeProfileActivity.this, status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
