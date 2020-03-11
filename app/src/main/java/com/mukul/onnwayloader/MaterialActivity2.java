package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwayloader.materialtype.MaterialActivity;
import com.mukul.onnwayloader.networking.AppController;
import com.mukul.onnwayloader.truckTypePOJO.truckTypeBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MaterialActivity2 extends AppCompatActivity {

    Spinner material , weight;
    EditText length , width , height , quantity;
    TextView total , grand;
    ProgressBar progress;
    Button next;

    List<String> mat , weis;
    List<String> mids;
    String src , des , dat , loa;
    String mid , wei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material2);

        mat = new ArrayList<>();
        weis = new ArrayList<>();
        mids = new ArrayList<>();

        src = getIntent().getStringExtra("source");
        des = getIntent().getStringExtra("destination");
        dat = getIntent().getStringExtra("date");
        loa = getIntent().getStringExtra("loadtype");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Booking Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progress = findViewById(R.id.progressBar);
        material = findViewById(R.id.material);
        weight = findViewById(R.id.weight);
        length = findViewById(R.id.editText5);
        width = findViewById(R.id.editText6);
        height = findViewById(R.id.editText7);
        quantity= findViewById(R.id.editText8);
        total= findViewById(R.id.textView54);
        grand= findViewById(R.id.textView57);
        next= findViewById(R.id.button);

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getMaterial();

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                mat.clear();
                mids.clear();

                for (int i = 0; i < response.body().size(); i++) {
                    mat.add(response.body().get(i).getTitle());
                    mids.add(response.body().get(i).getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MaterialActivity2.this,
                        android.R.layout.simple_list_item_1,mat);

                material.setAdapter(adapter);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        weis.add("50 - 100 KG");
        weis.add("101 - 200 KG");
        weis.add("201 - 300 KG");
        weis.add("301 - 400 KG");
        weis.add("401 - 500 KG");
        weis.add("501 - 600 KG");
        weis.add("601 - 700 KG");
        weis.add("701 - 800 KG");
        weis.add("801 - 900 KG");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,weis);

        weight.setAdapter(adapter2);


        material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mid = mids.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                wei = weis.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (quantity.getText().toString().length() > 0)
                {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0)
                    {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());
                        float qq = Float.parseFloat(quantity.getText().toString());

                        grand.setText(String.valueOf((ll * ww * hh) * qq));
                        total.setText(String.valueOf(ll * ww * hh));

                    }
                }
                else
                {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0)
                    {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());

                        total.setText(String.valueOf(ll * ww * hh));

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (quantity.getText().toString().length() > 0)
                {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0)
                    {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());
                        float qq = Float.parseFloat(quantity.getText().toString());

                        grand.setText(String.valueOf((ll * ww * hh) * qq));
                        total.setText(String.valueOf(ll * ww * hh));

                    }
                }
                else
                {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0)
                    {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());

                        total.setText(String.valueOf(ll * ww * hh));

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (quantity.getText().toString().length() > 0)
                {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0)
                    {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());
                        float qq = Float.parseFloat(quantity.getText().toString());

                        grand.setText(String.valueOf((ll * ww * hh) * qq));
                        total.setText(String.valueOf(ll * ww * hh));

                    }
                }
                else
                {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0)
                    {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());

                        total.setText(String.valueOf(ll * ww * hh));

                    }
                }



            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0)
                {

                    float ll = Float.parseFloat(length.getText().toString());
                    float ww = Float.parseFloat(width.getText().toString());
                    float hh = Float.parseFloat(height.getText().toString());
                    float qq = Float.parseFloat(quantity.getText().toString());

                    grand.setText(String.valueOf((ll * ww * hh) * qq));
                    total.setText(String.valueOf(ll * ww * hh));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0)
                {
                    Intent intent = new Intent(MaterialActivity2.this , Address3.class);
                    intent.putExtra("src" , src);
                    intent.putExtra("des" , des);
                    intent.putExtra("dat" , dat);
                    intent.putExtra("wei" , wei);
                    intent.putExtra("mid" , mid);
                    intent.putExtra("loa" , loa);
                    intent.putExtra("len" , length.getText().toString());
                    intent.putExtra("wid" , width.getText().toString());
                    intent.putExtra("hei" , height.getText().toString());
                    intent.putExtra("qua" , quantity.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MaterialActivity2.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
