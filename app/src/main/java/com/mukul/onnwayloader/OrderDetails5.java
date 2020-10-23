package com.mukul.onnwayloader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mukul.onnwayloader.checkPromoPOJO.checkPromoBean;
import com.mukul.onnwayloader.confirm_full_POJO.Data;
import com.mukul.onnwayloader.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwayloader.networking.AppController;
import com.mukul.onnwayloader.updateProfilePOJO.updateProfileBean;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OrderDetails5 extends AppCompatActivity {

    TextView orderid, orderdate, truck, source, destination, material, weight, date, status, loadtype, details;
    TextView request;
    ProgressBar progress;


    String id;

    float pvalue = 0;

    String insused = "no";

    FloatingActionButton track;

    TextView drivernote;

    ImageView truckType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details5);

        id = getIntent().getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Order Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        truckType = findViewById(R.id.imageView5);
        drivernote = findViewById(R.id.textView46);
        details = findViewById(R.id.textView14);
        orderid = findViewById(R.id.textView16);
        request = findViewById(R.id.button4);
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);
        date = findViewById(R.id.textView81);
        status = findViewById(R.id.textView83);
        loadtype = findViewById(R.id.textView85);
        progress = findViewById(R.id.progressBar);
        track = findViewById(R.id.floatingActionButton);


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(OrderDetails5.this, R.style.MyDialogTheme)
                        .setTitle("Cancel Booking")
                        .setMessage("Are you sure you want to cancel this booking?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {


                                progress.setVisibility(View.VISIBLE);

                                AppController b = (AppController) getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                Call<updateProfileBean> call = cr.cancel_order_loader(
                                        id
                                );

                                call.enqueue(new Callback<updateProfileBean>() {
                                    @Override
                                    public void onResponse(Call<updateProfileBean> call, Response<updateProfileBean> response) {

                                        if (response.body().getStatus().equals("1")) {
                                            Toast.makeText(OrderDetails5.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            finish();
                                        } else {
                                            Toast.makeText(OrderDetails5.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        progress.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<updateProfileBean> call, Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });


                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });


        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderDetails5.this, MapsActivity.class);
                intent.putExtra("order", id);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);


        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<confirm_full_bean> call = cr.getOrderDetails2(id);

        call.enqueue(new Callback<confirm_full_bean>() {
            @Override
            public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                Data item = response.body().getData();
                truck.setText(item.getTruckType());
                source.setText(item.getPickupAddress() + ", " + item.getPickupPincode() + ", " + item.getPickupPhone() + ", " + item.getPickupCity());
                destination.setText(item.getDropAddress() + ", " + item.getDropPincode() + ", " + item.getDropPhone() + ", " + item.getDropCity());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());
                date.setText(item.getSchedule());
                status.setText(item.getStatus());
                loadtype.setText(item.getLaodType());
                drivernote.setText(item.getRemarks());
                insused = item.getInsurance_used();

                if (item.getTruckType2().equals("open truck")) {
                    truckType.setImageDrawable(getDrawable(R.drawable.open));
                } else if (item.getTruckType2().equals("trailer")) {
                    truckType.setImageDrawable(getDrawable(R.drawable.trailer));
                } else {
                    truckType.setImageDrawable(getDrawable(R.drawable.container));
                }

                if (item.getStatus().equals("started")) {
                    track.setVisibility(View.VISIBLE);
                } else {
                    track.setVisibility(View.GONE);
                }


                try {
                    pvalue = Float.parseFloat(response.body().getData().getPvalue());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

}