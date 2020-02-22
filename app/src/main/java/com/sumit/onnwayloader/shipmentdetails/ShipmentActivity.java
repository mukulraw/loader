package com.sumit.onnwayloader.shipmentdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.sumit.onnwayloader.FindTruckFragment;
import com.sumit.onnwayloader.R;
import com.sumit.onnwayloader.networking.Post;
import com.sumit.onnwayloader.shippingaddress.ShippingAddress;
import com.sumit.onnwayloader.vehicletype.OpenTruckType;

public class ShipmentActivity extends AppCompatActivity {

    private TextView friegthDetailsTv, orderPriceTv;
    private Button confirmLoadingBtn;
    private TextView sourceAddress, destinationAddress, shipmentDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_shipment_details);
        mToolbar.setTitle(getString(R.string.shipment_details));
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        friegthDetailsTv = findViewById(R.id.freight_details_tv);

        orderPriceTv = findViewById(R.id.order_price_tv);
        orderPriceTv.setText("₹ " + Post.price);

        sourceAddress = findViewById(R.id.source_address);
        destinationAddress = findViewById(R.id.destination_address);
        shipmentDate = findViewById(R.id.shipment_date);

        /*sourceAddress.setText(FindTruckFragment.srcAddress);
        destinationAddress.setText(FindTruckFragment.destAddress);
        shipmentDate.setText(FindTruckFragment.pickUpDate);*/

        friegthDetailsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFreigthDetailsFragment();
            }
        });

        confirmLoadingBtn = findViewById(R.id.confirm_loading_btn);
        confirmLoadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShipmentActivity.this, ShippingAddress.class);
                startActivity(intent);
            }
        });
    }

    public void getFreigthDetailsFragment() {
        FreightDetails freightDetails = new FreightDetails();
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog", true);
        freightDetails.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        freightDetails.show(ft, "dialog");
    }
}
