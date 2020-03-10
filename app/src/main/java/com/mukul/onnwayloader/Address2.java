package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwayloader.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwayloader.networking.AppController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Address2 extends AppCompatActivity {
    ProgressBar progress;
    EditText paddress , pcity , ppincode , pmobile;
    EditText daddress , dcity , dpincode , dmobile;

    Button confirm;

    String src , des , tid , dat , wei , mid , loa;
    String freight , other_charges , cgst , sgst , insurance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address1);

        src = getIntent().getStringExtra("src");
        des = getIntent().getStringExtra("des");
        tid = getIntent().getStringExtra("tid");
        dat = getIntent().getStringExtra("dat");
        wei = getIntent().getStringExtra("wei");
        mid = getIntent().getStringExtra("mid");
        loa = getIntent().getStringExtra("loa");
        freight = getIntent().getStringExtra("freight");
        other_charges = getIntent().getStringExtra("other_charges");
        cgst = getIntent().getStringExtra("cgst");
        sgst = getIntent().getStringExtra("sgst");
        insurance = getIntent().getStringExtra("insurance");

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

                if (padd.length() > 0)
                {
                    if (pcit.length() > 0)
                    {
                        if (ppin.length() > 0)
                        {
                            if (pmob.length() == 10)
                            {
                                if (dadd.length() > 0)
                                {
                                    if (dcit.length() > 0)
                                    {
                                        if (dpin.length() > 0)
                                        {
                                            if (dmob.length() == 10)
                                            {

                                                progress.setVisibility(View.VISIBLE);

                                                AppController b = (AppController) getApplicationContext();

                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(b.baseurl)
                                                        .addConverterFactory(ScalarsConverterFactory.create())
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();

                                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);


                                                Call<confirm_full_bean> call = cr.quote_full_load(
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
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        ""
                                                );

                                                call.enqueue(new Callback<confirm_full_bean>() {
                                                    @Override
                                                    public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                                                        if (response.body().getStatus().equals("1"))
                                                        {

                                                            Dialog dialog = new Dialog(Address2.this);
                                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                            dialog.setCancelable(true);
                                                            dialog.setContentView(R.layout.booking_qoute_dialog);
                                                            dialog.show();

                                                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                @Override
                                                                public void onCancel(DialogInterface dialog) {

                                                                    Intent intent = new Intent(Address2.this , MainActivity.class);
                                                                    startActivity(intent);
                                                                    finishAffinity();

                                                                }
                                                            });

                                                            Toast.makeText(Address2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(Address2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }


                                                        progress.setVisibility(View.GONE);

                                                    }

                                                    @Override
                                                    public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                                                        progress.setVisibility(View.GONE);
                                                    }
                                                });

                                            }
                                            else
                                            {
                                                Toast.makeText(Address2.this, "Invalid drop mobile", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(Address2.this, "Invalid drop PIN code", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(Address2.this, "Invalid drop city", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Address2.this, "Invalid drop address", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Address2.this, "Invalid pickup mobile", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Address2.this, "Invalid pickup PIN code", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Address2.this, "Invalid pickup city", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Address2.this, "Invalid pickup address", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
