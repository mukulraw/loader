package com.mukul.onnwayloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwayloader.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwayloader.networking.AppController;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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

public class PayNow extends AppCompatActivity {

    String percent, pid, insused, oid;
    float pvalue, in, ammm;
    boolean ins;

    ImageView image;
    Button upload, proceed;
    private Uri uri1;
    private File f1;

    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);

        percent = getIntent().getStringExtra("percent");
        pid = getIntent().getStringExtra("pid");
        insused = getIntent().getStringExtra("insused");
        oid = getIntent().getStringExtra("oid");
        pvalue = getIntent().getFloatExtra("pvalue", 0);
        in = getIntent().getFloatExtra("insurance", 0);
        ammm = getIntent().getFloatExtra("amount", 0);
        ins = getIntent().getBooleanExtra("isinsurance", false);

        image = findViewById(R.id.imageView14);
        proceed = findViewById(R.id.button14);
        upload = findViewById(R.id.button15);
        progress = findViewById(R.id.progressBar);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Pay Now");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PayNow.this);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(PayNow.this), getPackageName() + ".provider", f1);


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


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progress.setVisibility(View.VISIBLE);

                final AppController b = (AppController) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                final String txn = String.valueOf(System.currentTimeMillis());

                Call<String> call = cr.test(oid + "_" + txn, String.valueOf(ammm));

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {

                            Log.d("aasdda" , response.body());

                            JSONObject object = new JSONObject(response.body());

                            JSONObject body = object.getJSONObject("body");
                            String txnToken = body.getString("txnToken");


                            String mid = "OQyoJy00054286990314";
                            String amount = String.valueOf(ammm);
                            //String txnToken = txntoken;
                            String orderid = oid + "_" + txn;
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

                            transactionManager.startTransaction(PayNow.this, 123);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


            }
        });


    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(PayNow.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);

            MultipartBody.Part body = null;

            image.setImageURI(uri1);

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("image", f1.getName(), reqFile1);


                progress.setVisibility(View.VISIBLE);

                AppController b = (AppController) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<confirm_full_bean> call = cr.uploadReceipt(
                        oid,
                        SharePreferenceUtils.getInstance().getString("userId"),
                        percent,
                        pid,
                        String.valueOf(pvalue),
                        insused,
                        String.valueOf(in),
                        String.valueOf(ins),
                        body
                );

                call.enqueue(new Callback<confirm_full_bean>() {
                    @Override
                    public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                        Toast.makeText(PayNow.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


            } catch (Exception e1) {
                e1.printStackTrace();
            }


        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            MultipartBody.Part body = null;

            image.setImageURI(uri1);

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("image", f1.getName(), reqFile1);


                progress.setVisibility(View.VISIBLE);

                AppController b = (AppController) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<confirm_full_bean> call = cr.uploadReceipt(
                        oid,
                        SharePreferenceUtils.getInstance().getString("userId"),
                        percent,
                        pid,
                        String.valueOf(pvalue),
                        insused,
                        String.valueOf(in),
                        String.valueOf(ins),
                        body
                );

                call.enqueue(new Callback<confirm_full_bean>() {
                    @Override
                    public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                        Toast.makeText(PayNow.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

            } catch (Exception e1) {
                e1.printStackTrace();
            }


        }


        if (requestCode == 123 && data != null) {
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
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
