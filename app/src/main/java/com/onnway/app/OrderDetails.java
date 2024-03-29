package com.onnway.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onnway.app.checkPromoPOJO.checkPromoBean;
import com.onnway.app.confirm_full_POJO.Data;
import com.onnway.app.confirm_full_POJO.Invoice;
import com.onnway.app.confirm_full_POJO.Lr;
import com.onnway.app.confirm_full_POJO.Pod;
import com.onnway.app.confirm_full_POJO.confirm_full_bean;
import com.onnway.app.networking.AppController;
import com.onnway.app.updateProfilePOJO.updateProfileBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OrderDetails extends AppCompatActivity {

    TextView orderid, orderdate, truck, source, destination, material, weight, date, status, loadtype, details, read;
    TextView grand, tnc, request;
    CheckBox insurance;
    Button confirm;
    ProgressBar progress;

    TextView vehiclenumber, drivernumber, pending, pending2;

    Button add, upload1, upload2, apply;

    RecyclerView pod, documents, lrdownload;

    Button pay80, pay100;


    float fr = 0, dv = 0, ot = 0, cg = 0, sg = 0, in = 0;
    float gr = 0;
    float pa = 0;

    boolean ins = false;

    private Uri uri1;
    private File f1;

    TextView discountterms;

    String id, assign_id;

    CardView truckcard;

    float pvalue = 0;
    String pid = "";

    EditText promo, decs;

    String insused = "no";

    FloatingActionButton track;

    TextView drivernote, balance, insuranceheading;
    ImageView truckType;

    Button downloadlr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

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

        downloadlr = findViewById(R.id.button20);
        truckType = findViewById(R.id.imageView5);
        drivernote = findViewById(R.id.textView46);
        insuranceheading = findViewById(R.id.textView33);
        balance = findViewById(R.id.textView110);
        tnc = findViewById(R.id.textView41);
        details = findViewById(R.id.textView14);
        orderid = findViewById(R.id.textView16);
        confirm = findViewById(R.id.button);
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
        pending = findViewById(R.id.textView88);
        truckcard = findViewById(R.id.truckcard);
        lrdownload = findViewById(R.id.textView87);
        pending2 = findViewById(R.id.textView89);
        pay80 = findViewById(R.id.button2);
        pay100 = findViewById(R.id.button3);
        decs = findViewById(R.id.editText14);
        grand = findViewById(R.id.textView381);
        insurance = findViewById(R.id.checkBox);
        progress = findViewById(R.id.progressBar);
        discountterms = findViewById(R.id.textView111);
        apply = findViewById(R.id.button11);
        vehiclenumber = findViewById(R.id.textView291);
        drivernumber = findViewById(R.id.textView351);
        promo = findViewById(R.id.editText13);
        upload1 = findViewById(R.id.button5);
        upload2 = findViewById(R.id.button6);
        track = findViewById(R.id.floatingActionButton);
        read = findViewById(R.id.textView112);

        pod = findViewById(R.id.pod);
        documents = findViewById(R.id.recyclerView);


        decs.setHint("");
        decs.setFocusable(false);


        /*insurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ins = isChecked;
                updateSummary();

            }
        });*/


        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetails.this, R.style.MyDialogTheme);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f1 = new File(file);
                            try {
                                f1.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(OrderDetails.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });
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
                Dialog dialog = new Dialog(OrderDetails.this, R.style.MyDialogTheme);
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

                Dialog dialog = new Dialog(OrderDetails.this, R.style.MyDialogTheme);
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
                TextView sdis = dialog.findViewById(R.id.textView155);
                TextView sdistitle = dialog.findViewById(R.id.textView154);

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

                if (dv > 0) {
                    sdis.setText("\u20B9" + dv);
                    sdis.setVisibility(View.VISIBLE);
                    sdistitle.setVisibility(View.VISIBLE);
                } else {
                    sdis.setVisibility(View.GONE);
                    sdistitle.setVisibility(View.GONE);
                }

            }
        });

        pay80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float ammm = (90 * gr) / 100;

                Log.d("amount90", String.valueOf(ammm));

                Intent intent = new Intent(OrderDetails.this, PayNow.class);
                intent.putExtra("percent", "90");
                intent.putExtra("pid", pid);
                intent.putExtra("amount", ammm);
                intent.putExtra("pvalue", pvalue);
                intent.putExtra("insused", insused);
                intent.putExtra("insurance", in);
                intent.putExtra("isinsurance", ins);
                intent.putExtra("oid", id);
                startActivity(intent);

            }
        });

        pay100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float ammm = gr - pa;

                Intent intent = new Intent(OrderDetails.this, PayNow.class);
                intent.putExtra("percent", "100");
                intent.putExtra("pid", pid);
                intent.putExtra("amount", ammm);
                intent.putExtra("pvalue", pvalue);
                intent.putExtra("insused", insused);
                intent.putExtra("insurance", in);
                intent.putExtra("isinsurance", ins);
                intent.putExtra("oid", id);
                startActivity(intent);

            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(OrderDetails.this, R.style.MyDialogTheme)
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
                                            Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            finish();
                                        } else {
                                            Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

                                gr = gr - pvalue;

                                grand.setText("₹ " + gr);

                                pid = response.body().getData().getPid();

                                Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                updateSummary();

                            } else {
                                Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(OrderDetails.this, "Invalid PROMO code", Toast.LENGTH_SHORT).show();
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
                if (isChecked) {
                    insused = "yes";
                } else {
                    insused = "no";
                }
                updateSummary();

                balance.setText("Balance - ₹ " + (gr - pa));

            }
        });

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderDetails.this, MapsActivity.class);
                intent.putExtra("order", id);
                startActivity(intent);

            }
        });


    }

    void updateSummary() {

        if (ins) {
            gr = fr + ot + cg + sg + in - dv - pvalue;
            grand.setText("\u20B9" + gr);
        } else {
            gr = fr + ot + cg + sg - dv - pvalue;
            grand.setText("\u20B9" + gr);
        }

            /*gr = fr + ot + cg + sg + in;
            grand.setText("\u20B9" + gr);*/
        balance.setText("Balance - ₹ " + (gr - pa));

    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);

        insurance.setEnabled(false);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<confirm_full_bean> call = cr.getOrderDetails(id);

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
                drivernote.setText(item.getRemarks2());
                decs.setText(item.getRemarks());

                insused = item.getInsurance_used();

                if (item.getTruckType2().equals("open truck")) {
                    truckType.setImageDrawable(getDrawable(R.drawable.open));
                } else if (item.getTruckType2().equals("trailer")) {
                    truckType.setImageDrawable(getDrawable(R.drawable.trailer));
                } else {
                    truckType.setImageDrawable(getDrawable(R.drawable.container));
                }



                if (item.getAssign_id() != null) {

                    assign_id = item.getAssign_id();

                    if (item.getVehicleNumber() != null) {
                        vehiclenumber.setText(item.getVehicleNumber());
                        drivernumber.setText(item.getDriverNumber());
                    } else {
                        vehiclenumber.setText("Not Available");
                        drivernumber.setText("Not Available");
                    }

                    if (response.body().getData().getPod().size() > 0) {
                        pending.setVisibility(View.GONE);
                    } else {
                        pending.setVisibility(View.VISIBLE);
                    }

                    PODAdapter adapter = new PODAdapter(OrderDetails.this, item.getPod());
                    GridLayoutManager manager = new GridLayoutManager(OrderDetails.this, 2);
                    pod.setAdapter(adapter);
                    pod.setLayoutManager(manager);

                    DocAdapter adapter2 = new DocAdapter(OrderDetails.this, item.getInvoice());
                    GridLayoutManager manager2 = new GridLayoutManager(OrderDetails.this, 2);
                    documents.setAdapter(adapter2);
                    documents.setLayoutManager(manager2);
                    truckcard.setVisibility(View.VISIBLE);

                    LRAdapter adapter3 = new LRAdapter(OrderDetails.this, item.getLr());
                    GridLayoutManager manager3 = new GridLayoutManager(OrderDetails.this, 1);
                    lrdownload.setAdapter(adapter3);
                    lrdownload.setLayoutManager(manager3);


                    Log.d("lrcount", item.getLrcount());
                    int lrc = Integer.parseInt(item.getLrcount());
                    if (lrc > 0) {
                        pending2.setVisibility(View.GONE);
                        downloadlr.setVisibility(View.VISIBLE);
                        downloadlr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String url = "https://www.onnway.com/admin/print/lr3.php?id=" + id;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);

                            }
                        });

                    } else {
                        pending2.setVisibility(View.VISIBLE);
                        downloadlr.setVisibility(View.GONE);
                    }


                } else {
                    truckcard.setVisibility(View.GONE);
                }


                insurance.setText("\u20B9" + item.getInsurance());

                fr = Float.parseFloat(item.getFreight());

                try {
                    dv = Float.parseFloat(item.getDiscvalue());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    ot = Float.parseFloat(item.getOtherCharges());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    cg = Float.parseFloat(item.getCgst());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    sg = Float.parseFloat(item.getSgst());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    in = Float.parseFloat(item.getInsurance());
                } catch (Exception e) {
                    e.printStackTrace();
                }


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


                promo.setText(item.getPromo_code());

                gr = gr - pvalue;

                grand.setText("₹ " + gr);
                if (item.getPaidAmount().length() > 0) {
                    pa = Float.parseFloat(item.getPaidAmount());
                } else {
                    pa = 0;
                }


                updateSummary();

                if (pvalue > 0) {
                    apply.setEnabled(false);
                    apply.setClickable(false);

                    promo.setEnabled(false);
                    promo.setClickable(false);
                } else {
                    apply.setEnabled(true);
                    apply.setClickable(true);

                    promo.setEnabled(true);
                    promo.setClickable(true);
                }

                if (item.getPer80().equals("pending")) {
                    pay80.setText("Pay 90%\n(After Loading)");
                    pay80.setEnabled(true);
                } else if (item.getPer80().equals("processing")) {
                    pay80.setText("PROCESSING...");
                    pay80.setEnabled(false);
                } else if (item.getPer80().equals("rejected")) {
                    pay80.setText("Rejected\nPAY NOW");
                    pay80.setEnabled(true);
                } else {
                    pay80.setText("Paid\n₹ " + item.getPaidAmount());
                    pay80.setEnabled(false);
                    promo.setEnabled(false);
                    promo.setClickable(false);
                }

                if (item.getPaidPercent().equals("80")) {
                    pay80.setText("Paid\n₹ " + item.getPaidAmount());
                    pay80.setEnabled(false);
                    promo.setEnabled(false);
                    promo.setClickable(false);
                }


                if (item.getPer100().equals("pending")) {
                    pay100.setText("Pay 100%\n(After Loading)");
                    pay100.setEnabled(true);
                } else if (item.getPer100().equals("processing")) {
                    pay100.setText("PROCESSING...");
                    pay100.setEnabled(false);
                } else if (item.getPer100().equals("rejected")) {
                    pay100.setText("REJECTED\nPAY NOW");
                    pay100.setEnabled(true);
                } else {
                    pay100.setText("100%\n" + item.getPaidAmount());
                    pay100.setEnabled(false);
                    promo.setEnabled(false);
                    promo.setClickable(false);
                }

                if (item.getPaidPercent().equals("100")) {
                    pay80.setText("Paid");
                    pay80.setEnabled(false);
                    promo.setEnabled(false);
                    promo.setClickable(false);
                    pay100.setText("100%\n" + item.getPaidAmount());
                    pay100.setEnabled(false);

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    class PODAdapter extends RecyclerView.Adapter<PODAdapter.ViewHolder> {

        List<Pod> list = new ArrayList<>();
        Context context;

        public PODAdapter(Context context, List<Pod> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Pod item = list.get(position);

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            final ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName(), holder.image, options);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    //      WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.zoom_dialog);
                    dialog.setCancelable(true);
                    dialog.show();

                    ImageView img = dialog.findViewById(R.id.image);
                    loader.displayImage(item.getName(), img, options);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder> {

        List<Invoice> list = new ArrayList<>();
        Context context;

        public DocAdapter(Context context, List<Invoice> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Invoice item = list.get(position);

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            final ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName(), holder.image, options);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    //      WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.zoom_dialog);
                    dialog.setCancelable(true);
                    dialog.show();

                    ImageView img = dialog.findViewById(R.id.image);
                    loader.displayImage(item.getName(), img, options);

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    class LRAdapter extends RecyclerView.Adapter<LRAdapter.ViewHolder> {

        List<Lr> list = new ArrayList<>();
        Context context;

        public LRAdapter(Context context, List<Lr> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Lr item = list.get(position);


            holder.image.setText(item.getName2());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                            .setReadTimeout(30_000)
                            .setConnectTimeout(30_000)
                            .build();
                    PRDownloader.initialize(getApplicationContext(), config);

                    int downloadId = PRDownloader.download(item.getName(), Utils.getRootDirPath(getApplicationContext()), item.getName2())
                            .build()
                            .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                @Override
                                public void onStartOrResume() {
                                    progress.setVisibility(View.VISIBLE);
                                }
                            })
                            .start(new OnDownloadListener() {
                                @Override
                                public void onDownloadComplete() {

                                    progress.setVisibility(View.GONE);
                                    Toast.makeText(context, "Downloaded successfully", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onError(Error error) {

                                }

                            });

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(OrderDetails.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            progress.setVisibility(View.VISIBLE);

            AppController b = (AppController) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<confirm_full_bean> call = cr.uploadDocuments(assign_id, body);

            call.enqueue(new Callback<confirm_full_bean>() {
                @Override
                public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                    Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            progress.setVisibility(View.VISIBLE);

            AppController b = (AppController) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<confirm_full_bean> call = cr.uploadDocuments(assign_id, body);

            call.enqueue(new Callback<confirm_full_bean>() {
                @Override
                public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                    Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        }


    }

    private static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }


}
