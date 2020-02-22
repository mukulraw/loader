package com.sumit.onnwayloader.materialtype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.sumit.onnwayloader.AllApiIneterface;
import com.sumit.onnwayloader.R;
import com.sumit.onnwayloader.Shipment;
import com.sumit.onnwayloader.networking.AppController;
import com.sumit.onnwayloader.truckTypePOJO.truckTypeBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MaterialActivity extends AppCompatActivity {

    BubbleThumbSeekbar seekBar;
    TextView weight;
    RecyclerView grid;
    Button checkFare;
    String mid = "";
    ProgressBar progress;

    String src , des , tid , dat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        src = getIntent().getStringExtra("source");
        des = getIntent().getStringExtra("destination");
        tid = getIntent().getStringExtra("tid");
        dat = getIntent().getStringExtra("date");

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

        seekBar = findViewById(R.id.seekBar);
        weight = findViewById(R.id.textView14);
        grid = findViewById(R.id.grid);
        checkFare = findViewById(R.id.button);
        progress = findViewById(R.id.progressBar);

















        seekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number number) {
                weight.setText(number + " kg");
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

        Call<List<truckTypeBean>> call = cr.getMaterial();

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                TruckAdapter adapter = new TruckAdapter(MaterialActivity.this , response.body() , "container");
                GridLayoutManager manager = new GridLayoutManager(MaterialActivity.this , 3);

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


                if (mid.length() > 0)
                {

                    Intent intent = new Intent(MaterialActivity.this, Shipment.class);
                    intent.putExtra("src" , src);
                    intent.putExtra("des" , des);
                    intent.putExtra("tid" , tid);
                    intent.putExtra("dat" , dat);
                    intent.putExtra("wei" , weight.getText().toString());
                    intent.putExtra("mid" , mid);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(MaterialActivity.this, "Please select a material type", Toast.LENGTH_SHORT).show();
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


    class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.ViewHolder>
    {
        Context context;
        List<truckTypeBean> list = new ArrayList<>();
        String type;


        TruckAdapter(Context context, List<truckTypeBean> list, String type)
        {
            this.context = context;
            this.list = list;
            this.type = type;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.truck_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final truckTypeBean item = list.get(position);

            if (mid.equals(item.getId()))
            {
                holder.card.setCardBackgroundColor(Color.parseColor("#F5DEDE"));
            }
            else
            {
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

        class ViewHolder extends RecyclerView.ViewHolder
        {

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
