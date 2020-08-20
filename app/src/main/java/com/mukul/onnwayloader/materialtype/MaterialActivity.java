package com.mukul.onnwayloader.materialtype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar;
import com.mukul.onnwayloader.AllApiIneterface;
import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.Shipment;
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

public class MaterialActivity extends AppCompatActivity {

    EditText weight;
    TextView weighttitle;
    RecyclerView grid;
    Button checkFare;
    String mid = "";
    ProgressBar progress;

    String src, des, tid, dat, loa, max;

    double sourceLAT , sourceLNG , destinationLAT , destinationLNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        src = getIntent().getStringExtra("source");
        des = getIntent().getStringExtra("destination");
        tid = getIntent().getStringExtra("tid");
        dat = getIntent().getStringExtra("date");
        loa = getIntent().getStringExtra("loadtype");
        max = getIntent().getStringExtra("max");
        sourceLAT = getIntent().getDoubleExtra("sourceLAT" , 0);
        sourceLNG = getIntent().getDoubleExtra("sourceLNG" , 0);
        destinationLAT = getIntent().getDoubleExtra("destinationLAT" , 0);
        destinationLNG = getIntent().getDoubleExtra("destinationLNG" , 0);

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Select Material");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        weight = findViewById(R.id.seekBar);
        grid = findViewById(R.id.grid);
        checkFare = findViewById(R.id.button);
        progress = findViewById(R.id.progressBar);
        weighttitle = findViewById(R.id.textView11);

        weighttitle.setText("Enter Weight (Max. load capacity - " + max + ")");


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

                TruckAdapter adapter = new TruckAdapter(MaterialActivity.this, response.body(), "container");
                GridLayoutManager manager = new GridLayoutManager(MaterialActivity.this, 1);

                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        checkFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String w = weight.getText().toString();

                if (w.length() > 0) {

                    float we = Float.parseFloat(w);
                    float ma = Float.parseFloat(max);

                    if (we <= ma) {
                        if (mid.length() > 0) {


                            Intent intent = new Intent(MaterialActivity.this, Shipment.class);
                            intent.putExtra("src", src);
                            intent.putExtra("des", des);
                            intent.putExtra("tid", tid);
                            intent.putExtra("dat", dat);
                            intent.putExtra("wei", weight.getText().toString() + " Kg");
                            intent.putExtra("mid", mid);
                            intent.putExtra("loa", loa);
                            intent.putExtra("sourceLAT", sourceLAT);
                            intent.putExtra("sourceLNG", sourceLNG);
                            intent.putExtra("destinationLAT", destinationLAT);
                            intent.putExtra("destinationLNG", destinationLNG);
                            startActivity(intent);

                        } else {
                            Toast.makeText(MaterialActivity.this, "Please select a material type", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MaterialActivity.this, "Entered weight can not be greater than max. load capacity", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MaterialActivity.this, "Please enter a weight", Toast.LENGTH_SHORT).show();
                }


                /*
                getPrice.currentMobile = EnterNumberActivity.mCurrentMobileNumber;
                getPrice.sourceAddress = FindTruckFragment.srcAddress;
                getPrice.destinationAddress = FindTruckFragment.destAddress;
                getPrice.truckType = FindTruckFragment.truckType;
                new Post().getPrice(MaterialActivity.this, getPrice);*/
            }
        });
    }


    class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.ViewHolder> {
        Context context;
        List<truckTypeBean> list = new ArrayList<>();
        String type;


        TruckAdapter(Context context, List<truckTypeBean> list, String type) {
            this.context = context;
            this.list = list;
            this.type = type;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.truck_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final truckTypeBean item = list.get(position);

            if (mid.equals(item.getId())) {
                holder.card.setCardBackgroundColor(Color.parseColor("#F5DEDE"));
            } else {
                holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }
/*
            if (type.equals("open truck"))
            {
                holder.image.setImageResource(R.drawable.ic_truck);
            }
            else if (type.equals("container"))
            {
                holder.image.setImageResource(R.drawable.ic_container);
            }
            else
            {
                holder.image.setImageResource(R.drawable.ic_trailer);
            }*/

            holder.text.setText(item.getTitle());

            holder.text.setGravity(Gravity.CENTER_VERTICAL);

            holder.image.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mid = item.getId();
                    notifyDataSetChanged();

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView text;
            CardView card;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);
                text = itemView.findViewById(R.id.text);
                card = itemView.findViewById(R.id.card);

            }
        }
    }


}
