package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwayloader.farePOJO.Data;
import com.mukul.onnwayloader.farePOJO.fareBean;
import com.mukul.onnwayloader.materialtype.MaterialActivity;
import com.mukul.onnwayloader.networking.AppController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Shipment extends AppCompatActivity {


    TextView orderid , orderdate , truck , source , destination , material , weight , details;
    TextView grand , tnc;
    CheckBox insurance;
    Button confirm , request;
    ProgressBar progress;

    float fr = 0, ot = 0 , cg = 0 , sg = 0 , in = 0;
    float gr = 0;

    boolean ins = false;

    String src , des , tid , dat , wei , mid , loa;

    TextView discountterms;

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

        grand = findViewById(R.id.textView38);
        insurance = findViewById(R.id.checkBox);
        progress = findViewById(R.id.progressBar);


        String text = "Read T&C and Cancellation Policy";
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                String url = "https://www.onnway.com/terms-n-condition.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                String url = "https://www.onnway.com/privacy_policy.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        };

        spannableString.setSpan(clickableSpan1, 5,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan2, 13,32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tnc.setText(spannableString);
        tnc.setMovementMethod(LinkMovementMethod.getInstance());


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        //Call<fareBean> call = cr.getFare(src , des , tid , mid , wei);
        Call<fareBean> call = cr.getFare("Delhi" , "Mumbai" , "1" , "1" , "10000");

        call.enqueue(new Callback<fareBean>() {
            @Override
            public void onResponse(Call<fareBean> call, Response<fareBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    final Data item = response.body().getData();
                    truck.setText(item.getTruckId());
                    source.setText(item.getSource());
                    destination.setText(item.getDestination());
                    material.setText(item.getMaterial());
                    weight.setText(item.getWeight());
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

                    updateSummary();

                    details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(Shipment.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(true);
                            dialog.setContentView(R.layout.fare_breakdown_dialog);
                            dialog.show();

                            TextView frr = dialog.findViewById(R.id.textView117);
                            TextView oth = dialog.findViewById(R.id.textView118);
                            TextView cgs = dialog.findViewById(R.id.textView119);
                            TextView sgs = dialog.findViewById(R.id.textView120);

                            frr.setText("\u20B9" + item.getFreight());
                            oth.setText("\u20B9" + item.getOtherCharges());
                            cgs.setText("\u20B9" + item.getCgst());
                            sgs.setText("\u20B9" + item.getSgst());

                        }
                    });

                }
                else
                {

                    Dialog dialog = new Dialog(Shipment.this);
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

                            if (SharePreferenceUtils.getInstance().getString("name").length() > 0)
                            {
                                Intent intent = new Intent(Shipment.this , Address2.class);
                                intent.putExtra("src" , src);
                                intent.putExtra("des" , des);
                                intent.putExtra("tid" , tid);
                                intent.putExtra("dat" , dat);
                                intent.putExtra("wei" , wei);
                                intent.putExtra("mid" , mid);
                                intent.putExtra("loa" , loa);
                                intent.putExtra("freight" , String.valueOf(fr));
                                intent.putExtra("other_charges" , "" + ot);
                                intent.putExtra("cgst" , "" + cg);
                                intent.putExtra("sgst" , "" + sg);
                                intent.putExtra("insurance" , "" + in);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Shipment.this, "Please complete your profile to continue", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Shipment.this , Profile.class);
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

                if (SharePreferenceUtils.getInstance().getString("name").length() > 0)
                {
                    Intent intent = new Intent(Shipment.this , Address1.class);
                    intent.putExtra("src" , src);
                    intent.putExtra("des" , des);
                    intent.putExtra("tid" , tid);
                    intent.putExtra("dat" , dat);
                    intent.putExtra("wei" , wei);
                    intent.putExtra("mid" , mid);
                    intent.putExtra("loa" , loa);
                    intent.putExtra("freight" , String.valueOf(fr));
                    intent.putExtra("other_charges" , "" + ot);
                    intent.putExtra("cgst" , "" + cg);
                    intent.putExtra("sgst" , "" + sg);
                    intent.putExtra("insurance" , "" + in);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Shipment.this, "Please complete your profile to continue", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Shipment.this , Profile.class);
                    startActivity(intent);
                }



            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharePreferenceUtils.getInstance().getString("name").length() > 0)
                {
                    Intent intent = new Intent(Shipment.this , Address2.class);
                    intent.putExtra("src" , src);
                    intent.putExtra("des" , des);
                    intent.putExtra("tid" , tid);
                    intent.putExtra("dat" , dat);
                    intent.putExtra("wei" , wei);
                    intent.putExtra("mid" , mid);
                    intent.putExtra("loa" , loa);
                    intent.putExtra("freight" , String.valueOf(fr));
                    intent.putExtra("other_charges" , "" + ot);
                    intent.putExtra("cgst" , "" + cg);
                    intent.putExtra("sgst" , "" + sg);
                    intent.putExtra("insurance" , "" + in);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Shipment.this, "Please complete your profile to continue", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Shipment.this , Profile.class);
                    startActivity(intent);
                }


            }
        });


    }

    void updateSummary()
    {

        if (ins)
        {
            gr = fr + ot + cg + sg + in;
            grand.setText("\u20B9" + gr);
        }
        else
        {
            gr = fr + ot + cg + sg;
            grand.setText("\u20B9" + gr);
        }

    }


}
