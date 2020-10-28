package com.onnway.kedsons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.onnway.kedsons.networking.AppController;
import com.onnway.kedsons.updateProfilePOJO.updateProfileBean;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Profile extends AppCompatActivity {

    EditText name , email , city , company;
    RadioGroup type;
    Button submit;
    ProgressBar progress;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    boolean comp = true;
    String t = "";

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



        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Places.initialize(Profile.this, "AIzaSyDg928l41AL20avLOGqYVVHHYHyNTM3DMY");

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("ind")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(Profile.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });

        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton btn = type.findViewById(checkedId);

                if (btn.getText().toString().equals("Company"))
                {
                    comp = true;
                    company.setVisibility(View.VISIBLE);
                    t = btn.getText().toString();
                }
                else
                {
                    comp = false;
                    company.setVisibility(View.GONE);
                    t = btn.getText().toString();
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                String e = email.getText().toString();
                String c = city.getText().toString();
                String co = company.getText().toString();

                if (n.length() > 0)
                {
                    if (e.length() > 0 )
                    {
                        if (c.length() > 0)
                        {
                            int iidd = type.getCheckedRadioButtonId();
                            if (iidd > -1)
                            {
                                if (comp)
                                {
                                    if (co.length() > 0)
                                    {
                                        update(n , e , c , t , co);
                                    }
                                    else
                                    {
                                        Toast.makeText(Profile.this, "Invalid company name", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    update(n , e , c , t , "");
                                }
                            }
                            else
                            {
                                Toast.makeText(Profile.this, "Please choose type", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Profile.this, "Invalid address", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Profile.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Profile.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    void update(String n , String e , String c , String t , String co)
    {
        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<updateProfileBean> call = cr.update_loader_profile(
          SharePreferenceUtils.getInstance().getString("userId"),
                n,
                e,
                c,
                t,
                co,
                ""
        );

        call.enqueue(new Callback<updateProfileBean>() {
            @Override
            public void onResponse(Call<updateProfileBean> call, Response<updateProfileBean> response) {

                if (response.body().getStatus().equals("1"))
                {

                    SharePreferenceUtils.getInstance().saveString("name" , response.body().getData().getName());
                    SharePreferenceUtils.getInstance().saveString("email" , response.body().getData().getEmail());
                    SharePreferenceUtils.getInstance().saveString("city" , response.body().getData().getCity());
                    SharePreferenceUtils.getInstance().saveString("type" , response.body().getData().getType());
                    SharePreferenceUtils.getInstance().saveString("company" , response.body().getData().getCompany());

                    Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<updateProfileBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                city.setText(place.getName());

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(Profile.this, status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
