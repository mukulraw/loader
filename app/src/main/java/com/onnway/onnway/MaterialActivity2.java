package com.onnway.onnway;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.onnway.onnway.networking.AppController;
import com.onnway.onnway.truckTypePOJO.truckTypeBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class MaterialActivity2 extends AppCompatActivity {

    Spinner material, weight;
    EditText length, width, height, quantity, remarks;
    TextView total, grand;
    ProgressBar progress;
    Button next;

    List<String> mat, weis;
    List<String> mids;
    String src, des, dat, loa, tid;
    String mid, wei;
    Button upload;

    private Uri uri1;
    private File f1 = null;

    ImageView image;

    double sourceLAT, sourceLNG, destinationLAT, destinationLNG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material2);

        mat = new ArrayList<>();
        weis = new ArrayList<>();
        mids = new ArrayList<>();

        src = getIntent().getStringExtra("source");
        tid = getIntent().getStringExtra("tid");
        des = getIntent().getStringExtra("destination");
        dat = getIntent().getStringExtra("date");
        loa = getIntent().getStringExtra("loadtype");
        sourceLAT = getIntent().getDoubleExtra("sourceLAT", 0);
        sourceLNG = getIntent().getDoubleExtra("sourceLNG", 0);
        destinationLAT = getIntent().getDoubleExtra("destinationLAT", 0);
        destinationLNG = getIntent().getDoubleExtra("destinationLNG", 0);

        Toolbar mToolbar = findViewById(R.id.toolbar_activity_shipment);
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
        quantity = findViewById(R.id.editText8);
        total = findViewById(R.id.textView54);
        grand = findViewById(R.id.textView57);
        next = findViewById(R.id.button);
        remarks = findViewById(R.id.editText15);
        image = findViewById(R.id.imageView13);
        upload = findViewById(R.id.button12);

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
                        android.R.layout.simple_list_item_1, mat);

                material.setAdapter(adapter);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        Call<List<truckTypeBean>> call2 = cr.getWeight();

        call2.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                weis.clear();

                for (int i = 0; i < response.body().size(); i++) {
                    weis.add(response.body().get(i).getTitle());
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MaterialActivity2.this,
                        android.R.layout.simple_list_item_1, weis);

                weight.setAdapter(adapter2);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


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

                if (quantity.getText().toString().length() > 0) {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0) {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());
                        float qq = Float.parseFloat(quantity.getText().toString());

                        grand.setText(String.valueOf((ll * ww * hh) * qq));
                        total.setText(ll * ww * hh + " cu. ft.");

                    }
                } else {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());

                        total.setText(ll * ww * hh + " cu. ft.");

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MaterialActivity2.this, R.style.MyDialogTheme);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(MaterialActivity2.this), BuildConfig.APPLICATION_ID + ".provider", f1);


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

        width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (quantity.getText().toString().length() > 0) {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0) {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());
                        float qq = Float.parseFloat(quantity.getText().toString());

                        grand.setText(String.valueOf((ll * ww * hh) * qq));
                        total.setText(ll * ww * hh + " cu. ft.");

                    }
                } else {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());

                        total.setText(ll * ww * hh + " cu. ft.");

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

                if (quantity.getText().toString().length() > 0) {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0) {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());
                        float qq = Float.parseFloat(quantity.getText().toString());

                        grand.setText(String.valueOf((ll * ww * hh) * qq));
                        total.setText(ll * ww * hh + " cu. ft.");

                    }
                } else {
                    if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0) {

                        float ll = Float.parseFloat(length.getText().toString());
                        float ww = Float.parseFloat(width.getText().toString());
                        float hh = Float.parseFloat(height.getText().toString());

                        total.setText(ll * ww * hh + " cu. ft.");

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

                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0) {

                    float ll = Float.parseFloat(length.getText().toString());
                    float ww = Float.parseFloat(width.getText().toString());
                    float hh = Float.parseFloat(height.getText().toString());
                    float qq = Float.parseFloat(quantity.getText().toString());

                    grand.setText(String.valueOf((ll * ww * hh) * qq));
                    total.setText(ll * ww * hh + " cu. ft.");

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (length.getText().toString().length() > 0 && width.getText().toString().length() > 0 && height.getText().toString().length() > 0 && quantity.getText().toString().length() > 0) {

                    if (SharePreferenceUtils.getInstance().getString("name").length() > 0) {
                        if (f1 != null) {
                            Intent intent = new Intent(MaterialActivity2.this, Address3.class);
                            intent.putExtra("src", src);
                            intent.putExtra("des", des);
                            intent.putExtra("dat", dat);
                            intent.putExtra("wei", wei);
                            intent.putExtra("mid", mid);
                            intent.putExtra("loa", loa);
                            intent.putExtra("tid", tid);
                            intent.putExtra("image", f1.toString());
                            intent.putExtra("len", length.getText().toString());
                            intent.putExtra("desc", remarks.getText().toString());
                            intent.putExtra("wid", width.getText().toString());
                            intent.putExtra("hei", height.getText().toString());
                            intent.putExtra("qua", quantity.getText().toString());
                            intent.putExtra("sourceLAT", sourceLAT);
                            intent.putExtra("sourceLNG", sourceLNG);
                            intent.putExtra("destinationLAT", destinationLAT);
                            intent.putExtra("destinationLNG", destinationLNG);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MaterialActivity2.this, Address3.class);
                            intent.putExtra("src", src);
                            intent.putExtra("des", des);
                            intent.putExtra("dat", dat);
                            intent.putExtra("wei", wei);
                            intent.putExtra("mid", mid);
                            intent.putExtra("loa", loa);
                            intent.putExtra("tid", tid);
                            intent.putExtra("image", "");
                            intent.putExtra("len", length.getText().toString());
                            intent.putExtra("desc", remarks.getText().toString());
                            intent.putExtra("wid", width.getText().toString());
                            intent.putExtra("hei", height.getText().toString());
                            intent.putExtra("qua", quantity.getText().toString());
                            intent.putExtra("sourceLAT", sourceLAT);
                            intent.putExtra("sourceLNG", sourceLNG);
                            intent.putExtra("destinationLAT", destinationLAT);
                            intent.putExtra("destinationLNG", destinationLNG);
                            startActivity(intent);
                        }


                    } else {
                        Toast.makeText(MaterialActivity2.this, "Please complete your profile to continue", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MaterialActivity2.this, Profile.class);
                        startActivity(intent);
                    }


                } else {
                    Toast.makeText(MaterialActivity2.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(MaterialActivity2.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);

            MultipartBody.Part body = null;

            image.setImageURI(uri1);

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("image", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }


        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            MultipartBody.Part body = null;

            image.setImageURI(uri1);

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("image", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }


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
