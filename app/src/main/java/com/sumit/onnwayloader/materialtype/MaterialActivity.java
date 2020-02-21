package com.sumit.onnwayloader.materialtype;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sumit.onnwayloader.FindTruckFragment;
import com.sumit.onnwayloader.R;
import com.sumit.onnwayloader.getpricenetworking.GetPrice;
import com.sumit.onnwayloader.networking.Post;
import com.sumit.onnwayloader.otp.EnterNumberActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MaterialActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private GetPrice getPrice;
    private Button backBtn, findFareBtn;

    private TextView materailType1,materailType2, materailType3, materailType4, materailType5, materailType6, materailType7,
            materailType8, materailType9, materailType10, materailType11, materailType12, materailType13, materailType14, materailType15,
            materailType16, materailType17, materailType18;

    private ImageView materialImage1, materialImage2, materialImage3, materialImage4, materialImage5, materialImage6, materialImage7,
            materialImage8, materialImage9, materialImage10, materialImage11, materialImage12, materialImage13, materialImage14, materialImage15,
            materialImage16, materialImage17, materialImage18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle(getString(R.string.shipment_details));
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seekBar = findViewById(R.id.seekBar);
        backBtn = findViewById(R.id.back_btn);
        findFareBtn = findViewById(R.id.find_fare);




        materailType1 = findViewById(R.id.material_type_1);
        materailType2 = findViewById(R.id.material_type_2);
        materailType3 = findViewById(R.id.material_type_3);
        materailType4 = findViewById(R.id.material_type_4);
        materailType5 = findViewById(R.id.material_type_5);
        materailType6 = findViewById(R.id.material_type_6);
        materailType7 = findViewById(R.id.material_type_7);
        materailType8 = findViewById(R.id.material_type_8);
        materailType9 = findViewById(R.id.material_type_9);
        materailType10 = findViewById(R.id.material_type_10);
        materailType11 = findViewById(R.id.material_type_11);
        materailType12 = findViewById(R.id.material_type_12);
        materailType13 = findViewById(R.id.material_type_13);
        materailType14 = findViewById(R.id.material_type_14);
        materailType15 = findViewById(R.id.material_type_15);
        materailType16 = findViewById(R.id.material_type_16);
        materailType17 = findViewById(R.id.material_type_17);
        materailType18 = findViewById(R.id.material_type_18);

        materialImage1 = findViewById(R.id.material_image_1);
        materialImage2 = findViewById(R.id.material_image_2);
        materialImage3 = findViewById(R.id.material_image_3);
        materialImage4 = findViewById(R.id.material_image_4);
        materialImage5 = findViewById(R.id.material_image_5);
        materialImage6 = findViewById(R.id.material_image_6);
        materialImage7 = findViewById(R.id.material_image_7);
        materialImage8 = findViewById(R.id.material_image_8);
        materialImage9 = findViewById(R.id.material_image_9);
        materialImage10 = findViewById(R.id.material_image_10);
        materialImage11 = findViewById(R.id.material_image_11);
        materialImage12 = findViewById(R.id.material_image_12);
        materialImage13 = findViewById(R.id.material_image_13);
        materialImage14 = findViewById(R.id.material_image_14);
        materialImage15 = findViewById(R.id.material_image_15);
        materialImage16 = findViewById(R.id.material_image_16);
        materialImage17 = findViewById(R.id.material_image_17);
        materialImage18 = findViewById(R.id.material_image_18);

        materialImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#000000"));
                materailType1.setTextColor(Color.parseColor("#FFFFFF"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materailType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                materailType1.setBackgroundColor(Color.parseColor("#000000"));
                materailType1.setTextColor(Color.parseColor("#FFFFFF"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materialImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#000000"));
                materailType2.setTextColor(Color.parseColor("#FFFFFF"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materailType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#000000"));
                materailType2.setTextColor(Color.parseColor("#FFFFFF"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materialImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#000000"));
                materailType3.setTextColor(Color.parseColor("#FFFFFF"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materailType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#000000"));
                materailType3.setTextColor(Color.parseColor("#FFFFFF"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materialImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#000000"));
                materailType4.setTextColor(Color.parseColor("#FFFFFF"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materailType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#000000"));
                materailType4.setTextColor(Color.parseColor("#FFFFFF"));
                materailType5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType5.setTextColor(Color.parseColor("#444444"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materialImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#000000"));
                materailType5.setTextColor(Color.parseColor("#FFFFFF"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materailType5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materailType1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType1.setTextColor(Color.parseColor("#444444"));
                materailType2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType2.setTextColor(Color.parseColor("#444444"));
                materailType3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType3.setTextColor(Color.parseColor("#444444"));
                materailType4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType4.setTextColor(Color.parseColor("#444444"));
                materailType5.setBackgroundColor(Color.parseColor("#000000"));
                materailType5.setTextColor(Color.parseColor("#FFFFFF"));
                materailType6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType6.setTextColor(Color.parseColor("#444444"));
                materailType7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType7.setTextColor(Color.parseColor("#444444"));
                materailType8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType8.setTextColor(Color.parseColor("#444444"));
                materailType9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType9.setTextColor(Color.parseColor("#444444"));
                materailType10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType10.setTextColor(Color.parseColor("#444444"));
                materailType11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType11.setTextColor(Color.parseColor("#444444"));
                materailType12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType12.setTextColor(Color.parseColor("#444444"));
                materailType13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType13.setTextColor(Color.parseColor("#444444"));
                materailType14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType14.setTextColor(Color.parseColor("#444444"));
                materailType15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType15.setTextColor(Color.parseColor("#444444"));
                materailType16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType16.setTextColor(Color.parseColor("#444444"));
                materailType17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType17.setTextColor(Color.parseColor("#444444"));
                materailType18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                materailType18.setTextColor(Color.parseColor("#444444"));
            }
        });

        materialImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materialImage18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        materailType18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Toast.makeText(getApplicationContext(),"seekbar progress: "+ i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findFareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(MaterialActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                
                getPrice = new GetPrice();
                getPrice.currentMobile = EnterNumberActivity.mCurrentMobileNumber;
                getPrice.sourceAddress = FindTruckFragment.srcAddress;
                getPrice.destinationAddress = FindTruckFragment.destAddress;
                getPrice.truckType = FindTruckFragment.truckType;
                new Post().getPrice(MaterialActivity.this, getPrice);
            }
        });
    }
}
