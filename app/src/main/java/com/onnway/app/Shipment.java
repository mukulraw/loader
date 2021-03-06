package com.onnway.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
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

import com.onnway.app.checkPromoPOJO.checkPromoBean;
import com.onnway.app.farePOJO.Data;
import com.onnway.app.farePOJO.fareBean;
import com.onnway.app.networking.AppController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Shipment extends AppCompatActivity {


    TextView orderid, orderdate, truck, source, destination, material, weight, details, schedule, status, statustitle, loadtype, read;
    TextView grand, tnc, request;
    CheckBox insurance;
    Button confirm, apply;
    ProgressBar progress;

    Button pay80, pay100;

    float fr = 0, ot = 0, cg = 0, sg = 0, in = 0;
    float gr = 0;

    boolean ins = false;
    EditText promo, decs;

    String src, des, tid, dat, wei, mid, loa;

    TextView discountterms;

    float pvalue = 0;
    String pid = "";

    ImageView truckType;

    double sourceLAT, sourceLNG, destinationLAT, destinationLNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment2);

        src = getIntent().getStringExtra("src");
        des = getIntent().getStringExtra("des");
        tid = getIntent().getStringExtra("tid");
        dat = getIntent().getStringExtra("dat");
        wei = getIntent().getStringExtra("wei");
        mid = getIntent().getStringExtra("mid");
        loa = getIntent().getStringExtra("loa");
        sourceLAT = getIntent().getDoubleExtra("sourceLAT", 0);
        sourceLNG = getIntent().getDoubleExtra("sourceLNG", 0);
        destinationLAT = getIntent().getDoubleExtra("destinationLAT", 0);
        destinationLNG = getIntent().getDoubleExtra("destinationLNG", 0);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Shipment Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        orderid = findViewById(R.id.textView16);
        read = findViewById(R.id.textView112);
        truckType = findViewById(R.id.imageView5);
        confirm = findViewById(R.id.button);
        request = findViewById(R.id.button4);
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);
        tnc = findViewById(R.id.textView41);
        discountterms = findViewById(R.id.textView111);
        details = findViewById(R.id.textView14);
        promo = findViewById(R.id.editText13);
        apply = findViewById(R.id.button11);
        schedule = findViewById(R.id.textView81);
        status = findViewById(R.id.textView83);
        statustitle = findViewById(R.id.textView82);
        loadtype = findViewById(R.id.textView85);
        pay80 = findViewById(R.id.button2);
        pay100 = findViewById(R.id.button3);
        decs = findViewById(R.id.editText14);


        status.setVisibility(View.GONE);
        statustitle.setVisibility(View.GONE);

        schedule.setText(dat);
        loadtype.setText("FULL LOAD TRUCK");

        grand = findViewById(R.id.textView38);
        insurance = findViewById(R.id.checkBox);
        progress = findViewById(R.id.progressBar);


        String text = "Cancellation Policy";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                String url = "https://www.onnway.com/privacy_policy.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        };

        //spannableString.setSpan(clickableSpan1, 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan2, 0, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tnc.setText(spannableString);
        tnc.setMovementMethod(LinkMovementMethod.getInstance());

        String text2 = "PLEASE READ";
        SpannableString spannableString2 = new SpannableString(text2);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Dialog dialog = new Dialog(Shipment.this, R.style.MyDialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.read);
                dialog.show();

            }
        };

        spannableString2.setSpan(clickableSpan1, 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        read.setText(spannableString2);
        read.setMovementMethod(LinkMovementMethod.getInstance());

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(Shipment.this, R.style.MyDialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.fare_breakdown_dialog);
                dialog.show();

                TextView frr = dialog.findViewById(R.id.textView117);
                TextView frrtitle = dialog.findViewById(R.id.textView113);
                TextView oth = dialog.findViewById(R.id.textView118);
                TextView othtitle = dialog.findViewById(R.id.textView114);
                TextView cgs = dialog.findViewById(R.id.textView119);
                TextView cgstitle = dialog.findViewById(R.id.textView115);
                TextView sgs = dialog.findViewById(R.id.textView120);
                TextView sgstitle = dialog.findViewById(R.id.textView116);
                TextView pdis = dialog.findViewById(R.id.textView122);
                TextView pdistitle = dialog.findViewById(R.id.textView121);

                if (fr > 0) {
                    frr.setText("\u20B9" + fr);
                    frr.setVisibility(View.VISIBLE);
                    frrtitle.setVisibility(View.VISIBLE);
                } else {
                    frr.setVisibility(View.GONE);
                    frrtitle.setVisibility(View.GONE);
                }

                if (ot > 0) {
                    oth.setText("\u20B9" + ot);
                    oth.setVisibility(View.VISIBLE);
                    othtitle.setVisibility(View.VISIBLE);
                } else {
                    oth.setVisibility(View.GONE);
                    othtitle.setVisibility(View.GONE);
                }


                if (cg > 0) {
                    cgs.setText("\u20B9" + cg);
                    cgs.setVisibility(View.VISIBLE);
                    cgstitle.setVisibility(View.VISIBLE);
                } else {
                    cgs.setVisibility(View.GONE);
                    cgstitle.setVisibility(View.GONE);
                }

                if (sg > 0) {
                    sgs.setText("\u20B9" + sg);
                    sgs.setVisibility(View.VISIBLE);
                    sgstitle.setVisibility(View.VISIBLE);
                } else {
                    sgs.setVisibility(View.GONE);
                    sgstitle.setVisibility(View.GONE);
                }

                if (pvalue > 0) {
                    pdis.setText("\u20B9" + pvalue);
                    pdis.setVisibility(View.VISIBLE);
                    pdistitle.setVisibility(View.VISIBLE);
                } else {
                    pdis.setVisibility(View.GONE);
                    pdistitle.setVisibility(View.GONE);
                }


            }
        });

        pay80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Shipment.this, "Please confirm booking before payment", Toast.LENGTH_SHORT).show();

            }
        });

        pay100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Shipment.this, "Please confirm booking before payment", Toast.LENGTH_SHORT).show();

            }
        });


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<fareBean> call = cr.getFare(
                SharePreferenceUtils.getInstance().getString("userId"),
                src,
                des,
                tid,
                mid,
                wei,
                dat,
                String.valueOf(sourceLAT),
                String.valueOf(sourceLNG),
                String.valueOf(destinationLAT),
                String.valueOf(destinationLNG)
        );
        //Call<fareBean> call = cr.getFare("Delhi" , "Mumbai" , "1" , "1" , "10000");

        call.enqueue(new Callback<fareBean>() {
            @Override
            public void onResponse(Call<fareBean> call, Response<fareBean> response) {

                if (response.body().getStatus().equals("1")) {
                    final Data item = response.body().getData();
                    truck.setText(item.getTruckId());
                    source.setText(item.getSource());
                    destination.setText(item.getDestination());
                    material.setText(item.getMaterial());
                    weight.setText(item.getWeight());
                    Log.d("trucktyope", item.getTruckType());
                    if (item.getTruckType().equals("open truck")) {
                        truckType.setImageDrawable(getDrawable(R.drawable.open));
                    } else if (item.getTruckType().equals("trailer")) {
                        truckType.setImageDrawable(getDrawable(R.drawable.trailer));
                    } else {
                        truckType.setImageDrawable(getDrawable(R.drawable.container));
                    }

/*
                    freight.setText("\u20B9" + item.getFreight());
                    other.setText("\u20B9" + item.getOtherCharges());
                    cgst.setText("\u20B9" + item.getCgst());
                    sgst.setText("\u20B9" + item.getSgst());*/


                    insurance.setText("\u20B9" + item.getInsurance());

                    fr = Float.parseFloat(item.getFreight());
                    ot = Float.parseFloat(item.getOtherCharges());
                    cg = Float.parseFloat(item.getCgst());
                    sg = Float.parseFloat(item.getSgst());
                    in = Float.parseFloat(item.getInsurance());

                    if (in > 0) {
                        insurance.setEnabled(true);
                    } else {
                        insurance.setEnabled(false);
                    }

                    updateSummary();


                } else {

                    Dialog dialog = new Dialog(Shipment.this, R.style.MyDialogTheme);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.no_fare_dialog);
                    dialog.show();

                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    });

                    Button req = dialog.findViewById(R.id.button5);

                    req.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (SharePreferenceUtils.getInstance().getString("name").length() > 0) {
                                Intent intent = new Intent(Shipment.this, Address2.class);
                                intent.putExtra("src", src);
                                intent.putExtra("des", des);
                                intent.putExtra("tid", tid);
                                intent.putExtra("dat", dat);
                                intent.putExtra("wei", wei);
                                intent.putExtra("mid", mid);
                                intent.putExtra("loa", loa);
                                intent.putExtra("desc", decs.getText().toString());
                                intent.putExtra("freight", String.valueOf(fr));
                                intent.putExtra("other_charges", "" + ot);
                                intent.putExtra("cgst", "" + cg);
                                intent.putExtra("sgst", "" + sg);
                                intent.putExtra("insurance", "" + in);
                                intent.putExtra("sourceLAT", sourceLAT);
                                intent.putExtra("sourceLNG", sourceLNG);
                                intent.putExtra("destinationLAT", destinationLAT);
                                intent.putExtra("destinationLNG", destinationLNG);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Shipment.this, "Please complete your profile to continue", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Shipment.this, Profile.class);
                                startActivity(intent);
                            }


                        }
                    });

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<fareBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pc = promo.getText().toString();

                if (pc.length() > 0) {

                    apply.setEnabled(false);
                    apply.setClickable(false);

                    promo.setEnabled(false);
                    promo.setClickable(false);

                    progress.setVisibility(View.VISIBLE);

                    AppController b = (AppController) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<checkPromoBean> call = cr.checkPromo(pc, SharePreferenceUtils.getInstance().getString("userId"));

                    call.enqueue(new Callback<checkPromoBean>() {
                        @Override
                        public void onResponse(Call<checkPromoBean> call, Response<checkPromoBean> response) {

                            if (response.body().getStatus().equals("1")) {

                                pvalue = Float.parseFloat(response.body().getData().getDiscount());

                                float na = gr - pvalue;

                                grand.setText("₹ " + na);

                                pid = response.body().getData().getPid();

                                Toast.makeText(Shipment.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Shipment.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                apply.setEnabled(true);
                                apply.setClickable(true);

                                promo.setEnabled(true);
                                promo.setClickable(true);
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<checkPromoBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            apply.setEnabled(true);
                            apply.setClickable(true);

                            promo.setEnabled(true);
                            promo.setClickable(true);
                        }
                    });

                } else {
                    Toast.makeText(Shipment.this, "Invalid PROMO code", Toast.LENGTH_SHORT).show();
                }

            }
        });

        discountterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.onnway.com/payment_terms.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        insurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ins = isChecked;
                updateSummary();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharePreferenceUtils.getInstance().getString("name").length() > 0) {
                    Intent intent = new Intent(Shipment.this, Address1.class);
                    intent.putExtra("src", src);
                    intent.putExtra("des", des);
                    intent.putExtra("tid", tid);
                    intent.putExtra("dat", dat);
                    intent.putExtra("wei", wei);
                    intent.putExtra("mid", mid);
                    intent.putExtra("loa", loa);
                    intent.putExtra("desc", decs.getText().toString());
                    intent.putExtra("pvalue", pvalue);
                    intent.putExtra("pid", pid);
                    intent.putExtra("freight", String.valueOf(fr));
                    intent.putExtra("other_charges", "" + ot);
                    intent.putExtra("cgst", "" + cg);
                    intent.putExtra("sgst", "" + sg);
                    intent.putExtra("insurance", "" + in);
                    intent.putExtra("sourceLAT", sourceLAT);
                    intent.putExtra("sourceLNG", sourceLNG);
                    intent.putExtra("destinationLAT", destinationLAT);
                    intent.putExtra("destinationLNG", destinationLNG);
                    startActivity(intent);
                } else {
                    Toast.makeText(Shipment.this, "Please complete your profile to continue", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Shipment.this, Profile.class);
                    startActivity(intent);
                }


            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharePreferenceUtils.getInstance().getString("name").length() > 0) {
                    Intent intent = new Intent(Shipment.this, Address2.class);
                    intent.putExtra("src", src);
                    intent.putExtra("des", des);
                    intent.putExtra("tid", tid);
                    intent.putExtra("dat", dat);
                    intent.putExtra("wei", wei);
                    intent.putExtra("mid", mid);
                    intent.putExtra("desc", decs.getText().toString());
                    intent.putExtra("loa", loa);
                    intent.putExtra("freight", String.valueOf(fr));
                    intent.putExtra("other_charges", "" + ot);
                    intent.putExtra("cgst", "" + cg);
                    intent.putExtra("sgst", "" + sg);
                    intent.putExtra("insurance", "" + in);
                    intent.putExtra("sourceLAT", sourceLAT);
                    intent.putExtra("sourceLNG", sourceLNG);
                    intent.putExtra("destinationLAT", destinationLAT);
                    intent.putExtra("destinationLNG", destinationLNG);
                    startActivity(intent);
                } else {
                    Toast.makeText(Shipment.this, "Please complete your profile to continue", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Shipment.this, Profile.class);
                    startActivity(intent);
                }


            }
        });


    }

    void updateSummary() {

        if (ins) {
            gr = fr + ot + cg + sg + in;
            grand.setText("\u20B9" + gr);
        } else {
            gr = fr + ot + cg + sg;
            grand.setText("\u20B9" + gr);
        }

    }


}
