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

                String mid = "njBGDm82462402663939";
                String amount = "1.00";
                String txnToken = "588de344e0514b06ac4795e5bf61021a1600316946342";
                String orderid = "ORDERID_98765";
                String callbackurl = "https://merchant.com/callback";

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