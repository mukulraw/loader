package com.mukul.onnwayloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mukul.onnwayloader.confirm_full_POJO.Data;
import com.mukul.onnwayloader.confirm_full_POJO.Invoice;
import com.mukul.onnwayloader.confirm_full_POJO.Pod;
import com.mukul.onnwayloader.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwayloader.networking.AppController;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

public class OrderDetails extends AppCompatActivity {

    TextView orderid , orderdate , truck , source , destination , material , weight , date , status , loadtype;
    TextView freight , other , cgst , sgst , grand;
    CheckBox insurance;
    Button confirm , request;
    ProgressBar progress;

    TextView vehiclenumber , drivernumber;

    Button add , upload1 , upload2;

    RecyclerView pod , documents;


    float fr = 0, ot = 0 , cg = 0 , sg = 0 , in = 0;
    float gr = 0;

    boolean ins = false;

    private Uri uri1;
    private File f1;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        id = getIntent().getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("Order Details");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        orderid = findViewById(R.id.textView16);
        confirm = findViewById(R.id.button);
        request = findViewById(R.id.button4);
        orderdate = findViewById(R.id.textView17);
        truck = findViewById(R.id.textView19);
        source = findViewById(R.id.textView20);
        destination = findViewById(R.id.textView21);
        material = findViewById(R.id.textView23);
        weight = findViewById(R.id.textView25);
        date = findViewById(R.id.textView81);
        status = findViewById(R.id.textView83);
        loadtype = findViewById(R.id.textView85);

        freight = findViewById(R.id.textView29);
        other = findViewById(R.id.textView35);
        cgst = findViewById(R.id.textView36);
        sgst = findViewById(R.id.textView37);
        grand = findViewById(R.id.textView38);
        insurance = findViewById(R.id.checkBox);
        progress = findViewById(R.id.progressBar);

        vehiclenumber = findViewById(R.id.textView291);
        drivernumber = findViewById(R.id.textView351);

        add = findViewById(R.id.button3);
        upload1 = findViewById(R.id.button5);
        upload2 = findViewById(R.id.button6);

        pod = findViewById(R.id.pod);
        documents = findViewById(R.id.recyclerView);




        /*insurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ins = isChecked;
                updateSummary();

            }
        });*/


        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetails.this);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(OrderDetails.this), BuildConfig.APPLICATION_ID + ".provider", f1);

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


    }

    void updateSummary()
    {

            gr = fr + ot + cg + sg + in;
            grand.setText("\u20B9" + gr);


    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);

        insurance.setEnabled(false);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<confirm_full_bean> call = cr.getOrderDetails(id);

        call.enqueue(new Callback<confirm_full_bean>() {
            @Override
            public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                Data item = response.body().getData();
                truck.setText(item.getTruckType());
                source.setText(item.getSource());
                destination.setText(item.getDestination());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());
                date.setText(item.getSchedule());
                status.setText(item.getStatus());
                loadtype.setText(item.getLaodType());

                freight.setText("\u20B9" + item.getFreight());
                other.setText("\u20B9" + item.getOtherCharges());
                cgst.setText("\u20B9" + item.getCgst());
                sgst.setText("\u20B9" + item.getSgst());
                insurance.setText("\u20B9" + item.getInsurance());

                vehiclenumber.setText(item.getVehicleNumber());
                drivernumber.setText(item.getDriverNumber());

                fr = Float.parseFloat(item.getFreight());
                ot = Float.parseFloat(item.getOtherCharges());
                cg = Float.parseFloat(item.getCgst());
                sg = Float.parseFloat(item.getSgst());
                in = Float.parseFloat(item.getInsurance());

                if (in > 0)
                {
                    insurance.setChecked(true);
                }
                else
                {
                    insurance.setChecked(false);
                }

                PODAdapter adapter = new PODAdapter(OrderDetails.this , item.getPod());
                GridLayoutManager manager = new GridLayoutManager(OrderDetails.this , 2);
                pod.setAdapter(adapter);
                pod.setLayoutManager(manager);

                DocAdapter adapter2 = new DocAdapter(OrderDetails.this , item.getInvoice());
                GridLayoutManager manager2 = new GridLayoutManager(OrderDetails.this , 2);
                documents.setAdapter(adapter2);
                documents.setLayoutManager(manager2);

                updateSummary();




                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    class PODAdapter extends RecyclerView.Adapter<PODAdapter.ViewHolder>
    {

        List<Pod> list = new ArrayList<>();
        Context context;

        public PODAdapter(Context context , List<Pod> list)
        {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Pod item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName() , holder.image , options);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView image;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder>
    {

        List<Invoice> list = new ArrayList<>();
        Context context;

        public DocAdapter(Context context , List<Invoice> list)
        {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Invoice item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName() , holder.image , options);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView image;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(OrderDetails.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            progress.setVisibility(View.VISIBLE);

            AppController b = (AppController) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<confirm_full_bean> call = cr.uploadDocuments(id , body);

            call.enqueue(new Callback<confirm_full_bean>() {
                @Override
                public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                    Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("name", f1.getName(), reqFile1);


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            progress.setVisibility(View.VISIBLE);

            AppController b = (AppController) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

            Call<confirm_full_bean> call = cr.uploadDocuments(id , body);

            call.enqueue(new Callback<confirm_full_bean>() {
                @Override
                public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                    Toast.makeText(OrderDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    onResume();

                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });

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
