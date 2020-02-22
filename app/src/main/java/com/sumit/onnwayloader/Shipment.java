package com.sumit.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sumit.onnwayloader.farePOJO.Data;
import com.sumit.onnwayloader.farePOJO.fareBean;
import com.sumit.onnwayloader.networking.AppController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Shipment extends AppCompatActivity {


    TextView orderid , orderdate , truck , source , destination , material , weight;
    TextView freight , other , cgst , sgst , grand;
    CheckBox insurance;
    Button confirm , request;
    ProgressBar progress;

    float fr = 0, ot = 0 , cg = 0 , sg = 0 , in = 0;
    float gr = 0;

    boolean ins = false;

    String src , des , tid , dat , wei , mid;

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
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);

        freight = findViewById(R.id.textView29);
        other = findViewById(R.id.textView35);
        cgst = findViewById(R.id.textView36);
        sgst = findViewById(R.id.textView37);
        grand = findViewById(R.id.textView38);
        insurance = findViewById(R.id.checkBox);
        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<fareBean> call = cr.getFare(src , des , tid , mid , wei);

        call.enqueue(new Callback<fareBean>() {
            @Override
            public void onResponse(Call<fareBean> call, Response<fareBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    Data item = response.body().getData();
                    truck.setText(item.getTruckId());
                    source.setText(item.getSource());
                    destination.setText(item.getDestination());
                    material.setText(item.getMaterial());
                    weight.setText(item.getWeight());

                    freight.setText("\u20B9" + item.getFreight());
                    other.setText("\u20B9" + item.getOtherCharges());
                    cgst.setText("\u20B9" + item.getCgst());
                    sgst.setText("\u20B9" + item.getSgst());
                    insurance.setText("\u20B9" + item.getInsurance());

                    fr = Float.parseFloat(item.getFreight());
                    ot = Float.parseFloat(item.getOtherCharges());
                    cg = Float.parseFloat(item.getCgst());
                    sg = Float.parseFloat(item.getSgst());
                    in = Float.parseFloat(item.getInsurance());

                    updateSummary();

                }
                else
                {
                    Toast.makeText(Shipment.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<fareBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        insurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ins = isChecked;
                updateSummary();

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
