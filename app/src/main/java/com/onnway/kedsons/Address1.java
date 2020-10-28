package com.onnway.kedsons;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.onnway.kedsons.confirm_full_POJO.confirm_full_bean;
import com.onnway.kedsons.networking.AppController;
import com.shivtechs.maplocationpicker.MapUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Address1 extends AppCompatActivity {
    private static final String TAG = "Address1";
    ProgressBar progress;
    EditText paddress, pcity, ppincode, pmobile;
    EditText daddress, dcity, dpincode, dmobile;

    Button confirm;

    String src, des, tid, dat, wei, mid, loa, desc;
    String freight, other_charges, cgst, sgst, insurance;

    float pvalue = 0;
    String pid;

    double sourceLAT, sourceLNG, destinationLAT, destinationLNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address1);

        MapUtility.apiKey = getResources().getString(R.string.google_maps_key);

        src = getIntent().getStringExtra("src");
        des = getIntent().getStringExtra("des");
        tid = getIntent().getStringExtra("tid");
        dat = getIntent().getStringExtra("dat");
        wei = getIntent().getStringExtra("wei");
        mid = getIntent().getStringExtra("mid");
        loa = getIntent().getStringExtra("loa");
        pid = getIntent().getStringExtra("pid");
        desc = getIntent().getStringExtra("desc");
        pvalue = getIntent().getFloatExtra("pvalue", 0);
        freight = getIntent().getStringExtra("freight");
        other_charges = getIntent().getStringExtra("other_charges");
        cgst = getIntent().getStringExtra("cgst");
        sgst = getIntent().getStringExtra("sgst");
        insurance = getIntent().getStringExtra("insurance");
        sourceLAT = getIntent().getDoubleExtra("sourceLAT", 0);
        sourceLNG = getIntent().getDoubleExtra("sourceLNG", 0);
        destinationLAT = getIntent().getDoubleExtra("destinationLAT", 0);
        destinationLNG = getIntent().getDoubleExtra("destinationLNG", 0);

        Toolbar mToolbar = findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Address");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progress = findViewById(R.id.progressBar);
        paddress = findViewById(R.id.editText);
        pcity = findViewById(R.id.editText2);
        ppincode = findViewById(R.id.editText3);
        pmobile = findViewById(R.id.editText4);

        daddress = findViewById(R.id.address);
        dcity = findViewById(R.id.city);
        dpincode = findViewById(R.id.piun);
        dmobile = findViewById(R.id.mobile);
        confirm = findViewById(R.id.button);

        pcity.setText(src);
        dcity.setText(des);

        /*pcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Address1.this, LocationPickerActivity.class);
                intent.putExtra(MapUtility.LATITUDE, sourceLAT);
                intent.putExtra(MapUtility.LONGITUDE, sourceLNG);
                startActivityForResult(intent, 12);

                *//*List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(Collections.singletonList("IN"))
                        .setTypeFilter(TypeFilter.REGIONS)
                        .build(Address1.this);
                startActivityForResult(intent, 12);*//*

            }
        });

        dcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Address1.this, LocationPickerActivity.class);
                intent.putExtra(MapUtility.LATITUDE, destinationLAT);
                intent.putExtra(MapUtility.LONGITUDE, destinationLNG);
                startActivityForResult(intent, 14);

                *//*List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(Collections.singletonList("IN"))
                        .setTypeFilter(TypeFilter.REGIONS)
                        .build(Address1.this);
                startActivityForResult(intent, 14);*//*

            }
        });*/


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String padd = paddress.getText().toString();
                String pcit = pcity.getText().toString();
                String ppin = ppincode.getText().toString();
                String pmob = pmobile.getText().toString();
                String dadd = daddress.getText().toString();
                String dcit = dcity.getText().toString();
                String dpin = dpincode.getText().toString();
                String dmob = dmobile.getText().toString();

                if (padd.length() > 0) {
                    if (pcit.length() > 0) {
                        if (ppin.length() == 6) {
                            if (dadd.length() > 0) {
                                if (dcit.length() > 0) {
                                    if (dpin.length() == 6) {

                                        progress.setVisibility(View.VISIBLE);

                                        AppController b = (AppController) getApplicationContext();

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                        Call<confirm_full_bean> call = cr.confirm_full_load(
                                                SharePreferenceUtils.getInstance().getString("userId"),
                                                loa,
                                                src,
                                                des,
                                                tid,
                                                dat,
                                                wei,
                                                mid,
                                                freight,
                                                other_charges,
                                                cgst,
                                                sgst,
                                                insurance,
                                                "",
                                                "",
                                                padd,
                                                pcit,
                                                ppin,
                                                pmob,
                                                dadd,
                                                dcit,
                                                dpin,
                                                dmob,
                                                desc,
                                                "",
                                                "",
                                                "",
                                                "",
                                                String.valueOf(pvalue),
                                                pid,
                                                String.valueOf(sourceLAT),
                                                String.valueOf(sourceLNG),
                                                String.valueOf(destinationLAT),
                                                String.valueOf(destinationLNG)
                                        );

                                        call.enqueue(new Callback<confirm_full_bean>() {
                                            @Override
                                            public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                                                if (response.body().getStatus().equals("1")) {

                                                    Dialog dialog = new Dialog(Address1.this, R.style.MyDialogTheme);
                                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog.setCancelable(true);
                                                    dialog.setContentView(R.layout.booking_qoute_dialog);
                                                    dialog.show();

                                                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                        @Override
                                                        public void onCancel(DialogInterface dialog) {

                                                            Intent intent = new Intent(Address1.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finishAffinity();

                                                        }
                                                    });

                                                    Toast.makeText(Address1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(Address1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                }


                                                progress.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                                                progress.setVisibility(View.GONE);
                                            }
                                        });

                                    } else {
                                        Toast.makeText(Address1.this, "Invalid drop PIN code", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Address1.this, "Invalid drop city", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Address1.this, "Invalid drop address", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Address1.this, "Invalid pickup PIN code", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Address1.this, "Invalid pickup city", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Address1.this, "Invalid pickup address", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 12) {
            if (resultCode == RESULT_OK) {

                try {
                    if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {

                        sourceLAT = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                        sourceLNG = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);

                        String srcAddress = data.getStringExtra("city");
                        pcity.setText(srcAddress);

                        Log.d("loc1", String.valueOf(sourceLAT));
                        Log.d("loc1", String.valueOf(sourceLNG));


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

/*
                Place place = Autocomplete.getPlaceFromIntent(data);

                Geocoder geocoder = new Geocoder(Address1.this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    Log.d("addresss", String.valueOf(addresses.get(0)));
                    String cii = place.getName();
                    String stat = addresses.get(0).getAdminArea();

                    pcity.setText(cii);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


        if (requestCode == 14) {
            if (resultCode == RESULT_OK) {

                try {
                    if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {

                        destinationLAT = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                        destinationLNG = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);

                        String srcAddress = data.getStringExtra("city");
                        dcity.setText(srcAddress);

                        Log.d("loc1", String.valueOf(destinationLAT));
                        Log.d("loc1", String.valueOf(destinationLNG));


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                /*Place place = Autocomplete.getPlaceFromIntent(data);


                Geocoder geocoder = new Geocoder(Address1.this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    String cii = place.getName();
                    String stat = addresses.get(0).getAdminArea();
                    dcity.setText(cii);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

}
