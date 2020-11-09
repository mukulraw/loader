package com.onnway.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.onnway.app.networking.AppController;
import com.onnway.app.profilePOJO.Data;
import com.onnway.app.profilePOJO.profileBean;
import com.onnway.app.updateProfilePOJO.updateProfileBean;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EditProfile extends AppCompatActivity {

    EditText name , email , city , company , gst;
    RadioGroup type;
    Button submit;
    ProgressBar progress;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    boolean comp = true;
    String t = "";

    RadioButton compp , indi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
        gst = findViewById(R.id.editText16);
        compp = findViewById(R.id.company);
        indi = findViewById(R.id.indi);




        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("ind")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(EditProfile.this);
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
                String g = gst.getText().toString();

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
                                        update(n , e , c , t , co , g);
                                    }
                                    else
                                    {
                                        Toast.makeText(EditProfile.this, "Invalid company name", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    update(n , e , c , t , "" , g);
                                }
                            }
                            else
                            {
                                Toast.makeText(EditProfile.this, "Please choose type", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(EditProfile.this, "Invalid address", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(EditProfile.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(EditProfile.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setPrevious();

    }

    void setPrevious()
    {
        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Log.d("progie" , SharePreferenceUtils.getInstance().getString("userId"));

        Call<profileBean> call = cr.getLoaderProfile(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<profileBean>() {
            @Override
            public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                Data item = response.body().getData();

                name.setText(item.getName());
                if (item.getType().equals("Individual"))
                {
                    indi.setChecked(true);
                    //company.setVisibility(View.GONE);
                    //companytitle.setVisibility(View.GONE);
                    company.setText("---");
                }
                else
                {
                    compp.setChecked(true);
                    //company.setVisibility(View.VISIBLE);
                    //companytitle.setVisibility(View.VISIBLE);
                    company.setText(item.getCompany());
                }





                email.setText(item.getEmail());
                city.setText(item.getCity());
                gst.setText(item.getGst());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<profileBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    void update(String n , String e , String c , String t , String co , String g)
    {
        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<updateProfileBean> call = cr.update_loader_profile2(
                SharePreferenceUtils.getInstance().getString("userId"),
                n,
                e,
                c,
                t,
                co,
                g
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

                    Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditProfile.this, status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
