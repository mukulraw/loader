package com.sumit.onnwayloader.shippingaddress;

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
import android.widget.Toast;
import android.widget.Toolbar;

import com.sumit.onnwayloader.FindTruckFragment;
import com.sumit.onnwayloader.MainActivity;
import com.sumit.onnwayloader.R;
import com.sumit.onnwayloader.bookingconfirmed.BookingConfirmedFragment;
import com.sumit.onnwayloader.networking.Post;
import com.sumit.onnwayloader.otp.EnterNumberActivity;
import com.sumit.onnwayloader.shipmentdetails.FreightDetails;
import com.sumit.onnwayloader.shipmentdetails.ShipmentActivity;
import com.sumit.onnwayloader.shipmentdetails.ShipmentDetails;
import com.sumit.onnwayloader.userprofile.UserProfileActivity;

public class ShippingAddress extends AppCompatActivity {

    private Button doneBtn;
    private ShipmentDetails shipmentDetails = new ShipmentDetails();
    public static int order = 0;
    public static String isQuote = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        //setting the color of STATUS BAR of SelectUserTYpe activity to #696969
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(105, 105, 105));
        }

        //adding toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_shipment_address);
        mToolbar.setTitle(getString(R.string.shipment_address));
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        doneBtn = findViewById(R.id.done_order_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ShippingAddress.this, "Hello1", Toast.LENGTH_LONG).show();
//                bookingConfirmedFragment();
                if(MainActivity.ifRegistered == 0 || MainActivity.ifRegistered == 2) {
                    order = 1;
                    Toast.makeText(ShippingAddress.this, "Hello2", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ShippingAddress.this, UserProfileActivity.class);
                    startActivity(intent);
                } else if (MainActivity.ifRegistered == 1) {
                    Toast.makeText(ShippingAddress.this, "Hello3", Toast.LENGTH_LONG).show();
                    shipmentDetails.mobileNumber = EnterNumberActivity.mCurrentMobileNumber;
                    shipmentDetails.loadType = FindTruckFragment.loadType;
                    shipmentDetails.sourceAddress = FindTruckFragment.srcAddress;
                    shipmentDetails.destinationAddress = FindTruckFragment.destAddress;
                    shipmentDetails.truckType = FindTruckFragment.truckType;
                    shipmentDetails.scheduleDate = FindTruckFragment.pickUpDate;
                    shipmentDetails.loadingWeight = "300";
                    shipmentDetails.materialType = "10";
                    shipmentDetails.pickupStreet = "F 12 Old Minal, Bhopal";
                    shipmentDetails.pickupPincode = "462023";
                    shipmentDetails.dropStreet = "F 12 Old Minal, Bhopal";
                    shipmentDetails.dropPincode = "462023";
                    shipmentDetails.quoteId = isQuote;
                    new Post().postOrder(ShippingAddress.this, shipmentDetails);
                }
            }
        });
    }

    public void bookingConfirmedFragment() {
        BookingConfirmedFragment bookingConfirmedFragment = new BookingConfirmedFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog", true);
        bookingConfirmedFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        bookingConfirmedFragment.show(ft, "dialog");
    }
}