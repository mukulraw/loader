package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

public class Checkout extends AppCompatActivity {

    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        pay = findViewById(R.id.button19);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mid = "OQyoJy00054286990314";
                String amount = "100.00";
                String txnToken = "1d4aa9f00ce448818928a40d32b5d39a1600409710857";
                String orderid = "ORDERID_001";
                String callbackurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderid;

                PaytmOrder paytmOrder = new PaytmOrder(orderid, mid, txnToken, amount, callbackurl);
                TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
                    @Override
                    public void onTransactionResponse(Bundle bundle) {
                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + bundle.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() {
                        Toast.makeText(getApplicationContext(), "networkNotAvailable ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErrorProceed(String s) {
                        Toast.makeText(getApplicationContext(), "onErrorProceed " + s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void clientAuthenticationFailed(String s) {
                        Toast.makeText(getApplicationContext(), "clientAuthenticationFailed " + s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void someUIErrorOccurred(String s) {
                        Toast.makeText(getApplicationContext(), "someUIErrorOccurred " + s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int i, String s, String s1) {
                        Toast.makeText(getApplicationContext(), "onErrorLoadingWebPage " + s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(getApplicationContext(), "onBackPressedCancelTransaction ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String s, Bundle bundle) {
                        Toast.makeText(getApplicationContext(), "onTransactionCancel " + bundle.toString(), Toast.LENGTH_LONG).show();
                    }
                });

                transactionManager.startTransaction(Checkout.this, 123);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && data != null) {
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }


}