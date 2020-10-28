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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.onnway.onnway.confirm_full_POJO.confirm_full_bean;
import com.onnway.onnway.networking.AppController;
import com.onnway.onnway.profilePOJO.Data;
import com.onnway.onnway.profilePOJO.profileBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

public class KYC3 extends AppCompatActivity {

    ImageView pan, af, ab;
    Button upload1, upload2, upload3;
    ProgressBar progress;
    private Uri uri1;
    private File f1;
    String ty = "";

    ImageView pan_verify, af_verify, ab_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c3);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_shipment);
        mToolbar.setTitle("KYC");
        mToolbar.setNavigationIcon(R.drawable.ic_next_back);
        mToolbar.setTitleTextAppearance(this, R.style.monteserrat_semi_bold);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pan = findViewById(R.id.imageView10);
        af = findViewById(R.id.imageView11);
        ab = findViewById(R.id.imageView12);
        upload1 = findViewById(R.id.button8);
        upload2 = findViewById(R.id.button9);
        upload3 = findViewById(R.id.button10);
        progress = findViewById(R.id.progressBar);
        pan_verify = findViewById(R.id.imageView15);
        af_verify = findViewById(R.id.imageView16);
        ab_verify = findViewById(R.id.imageView17);


        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(KYC3.this, R.style.MyDialogTheme);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(KYC3.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            ty = "pan";

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            ty = "pan";
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

        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(KYC3.this, R.style.MyDialogTheme);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(KYC3.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            ty = "af";

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            ty = "af";
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

        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(KYC3.this, R.style.MyDialogTheme);
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

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(KYC3.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            ty = "ab";

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 1);
                        } else if (items[item].equals("Choose from Gallery")) {
                            ty = "ab";
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

    @Override
    protected void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Log.d("progie", SharePreferenceUtils.getInstance().getString("userId"));

        Call<profileBean> call = cr.getLoaderProfile(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<profileBean>() {
            @Override
            public void onResponse(Call<profileBean> call, Response<profileBean> response) {

                Data item = response.body().getData();

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).showImageForEmptyUri(R.drawable.ic_customer).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getPan(), pan, options);
                loader.displayImage(item.getAb(), ab, options);
                loader.displayImage(item.getAf(), af, options);

                if (item.getPan_verify().equals("verified")) {
                    //pan_verify.setVisibility(View.VISIBLE);
                    upload1.setEnabled(false);
                } else {
                    pan_verify.setVisibility(View.GONE);
                    upload1.setEnabled(true);
                }

                if (item.getAf_verify().equals("verified")) {
                    af_verify.setVisibility(View.VISIBLE);
                    upload2.setEnabled(false);
                } else {
                    af_verify.setVisibility(View.GONE);
                    upload2.setEnabled(true);
                }

                if (item.getAb_verify().equals("verified")) {
                    ab_verify.setVisibility(View.VISIBLE);
                    upload3.setEnabled(false);
                } else {
                    ab_verify.setVisibility(View.GONE);
                    upload3.setEnabled(true);
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<profileBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(KYC3.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);

            MultipartBody.Part body = null;

            try {

                RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                body = MultipartBody.Part.createFormData("image", f1.getName(), reqFile1);


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

            Call<confirm_full_bean> call = cr.updateLoaderKyc(SharePreferenceUtils.getInstance().getString("userId"), ty, body);

            call.enqueue(new Callback<confirm_full_bean>() {
                @Override
                public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                    Toast.makeText(KYC3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                body = MultipartBody.Part.createFormData("image", f1.getName(), reqFile1);


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

            Call<confirm_full_bean> call = cr.updateLoaderKyc(SharePreferenceUtils.getInstance().getString("userId"), ty, body);

            call.enqueue(new Callback<confirm_full_bean>() {
                @Override
                public void onResponse(Call<confirm_full_bean> call, Response<confirm_full_bean> response) {

                    Toast.makeText(KYC3.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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