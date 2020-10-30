package com.onnway.onnway;

import androidx.appcompat.app.AlertDialog;
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
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onnway.onnway.confirm_full_POJO.Data;
import com.onnway.onnway.confirm_full_POJO.confirm_full_bean;
import com.onnway.onnway.networking.AppController;
import com.onnway.onnway.updateProfilePOJO.updateProfileBean;

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

    TextView grand, read, tnc, insuranceheading, text;
    CheckBox insurance;
    String id;

    float pvalue = 0;

    String insused = "no";

    FloatingActionButton track;

    TextView drivernote;

    ImageView truckType;

    float fr = 0, ot = 0, cg = 0, sg = 0, in = 0;
    float gr = 0;
    float pa = 0;

    boolean ins = false;

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


        insurance = findViewById(R.id.checkBox);
        insuranceheading = findViewById(R.id.textView33);
        text = findViewById(R.id.textView40);
        grand = findViewById(R.id.textView38);
        read = findViewById(R.id.textView112);
        tnc = findViewById(R.id.textView41);
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
                Dialog dialog = new Dialog(OrderDetails5.this, R.style.MyDialogTheme);
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

                Dialog dialog = new Dialog(OrderDetails5.this, R.style.MyDialogTheme);
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

        insurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ins = isChecked;
                if (isChecked) {
                    insused = "yes";
                } else {
                    insused = "no";
                }
                updateSummary();


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

                try {
                    fr = Float.parseFloat(item.getFreight());
                    ot = Float.parseFloat(item.getOtherCharges());
                    cg = Float.parseFloat(item.getCgst());
                    sg = Float.parseFloat(item.getSgst());
                    in = Float.parseFloat(item.getInsurance());

                    if (in > 0) {

                        if (item.getInsurance_used().equals("yes")) {
                            insurance.setChecked(true);
                            insurance.setEnabled(false);
                        } else {
                            insurance.setEnabled(true);
                            insurance.setChecked(false);
                        }

                        insuranceheading.setVisibility(View.VISIBLE);
                        insurance.setVisibility(View.VISIBLE);

                    } else {
                        insurance.setEnabled(false);
                        insuranceheading.setVisibility(View.GONE);
                        insurance.setVisibility(View.GONE);
                    }

                    updateSummary();

                    try {
                        pvalue = Float.parseFloat(response.body().getData().getPvalue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    gr = gr - pvalue;

                    grand.setText("₹ " + gr);
                    if (item.getPaidAmount().length() > 0) {
                        pa = Float.parseFloat(item.getPaidAmount());
                    } else {
                        pa = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    insurance.setEnabled(false);
                    insuranceheading.setVisibility(View.GONE);
                    insurance.setVisibility(View.GONE);
                    details.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    grand.setText("NOT ASSIGNED");
                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                progress.setVisibility(View.GONE);
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

            /*gr = fr + ot + cg + sg + in;
            grand.setText("\u20B9" + gr);*/


    }

}