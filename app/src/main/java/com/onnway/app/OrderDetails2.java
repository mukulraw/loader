package com.onnway.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.onnway.app.confirm_full_POJO.Data;
import com.onnway.app.confirm_full_POJO.Invoice;
import com.onnway.app.confirm_full_POJO.Lr;
import com.onnway.app.confirm_full_POJO.Pod;
import com.onnway.app.confirm_full_POJO.confirm_full_bean;
import com.onnway.app.networking.AppController;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OrderDetails2 extends AppCompatActivity {

    TextView orderid, orderdate, truck, source, destination, material, weight, date, status, loadtype;
    TextView freight, other, cgst, sgst, grand;
    CheckBox insurance;
    Button confirm, request;
    ProgressBar progress;

    TextView vehiclenumber, drivernumber, pending, pending2;

    Button add, upload1, upload2;

    RecyclerView pod, documents, lrdownload;


    float fr = 0, ot = 0, cg = 0, sg = 0, in = 0;
    float gr = 0;

    boolean ins = false;

    private Uri uri1;
    private File f1;

    String id, assign_id;

    CardView truckcard;

    ImageView truckType;

    Button downloadlr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);

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

        downloadlr = findViewById(R.id.button20);

        truckType = findViewById(R.id.imageView5);
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
        pending = findViewById(R.id.textView88);
        truckcard = findViewById(R.id.truckcard);
        lrdownload = findViewById(R.id.textView87);
        pending2 = findViewById(R.id.textView89);

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



    }

    void updateSummary() {

        gr = fr + ot + cg + sg + in;
        grand.setText("\u20B9" + gr);


    }


    @Override
    protected void onResume() {
        super.onResume();
        progress.setVisibility(View.VISIBLE);

        insurance.setEnabled(false);

        AppController b = (AppController) getApplicationContext();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.HEADERS);
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .client(client)
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
                source.setText(item.getPickupAddress() + ", " + item.getPickupPincode() + ", " + item.getPickupPhone() + ", " + item.getPickupCity());
                destination.setText(item.getDropAddress() + ", " + item.getDropPincode() + ", " + item.getDropPhone() + ", " + item.getDropCity());
                material.setText(item.getMaterial());
                weight.setText(item.getWeight());
                date.setText(item.getSchedule());
                status.setText(item.getStatus());
                loadtype.setText(item.getLaodType());

                if (item.getTruckType2().equals("open truck")) {
                    truckType.setImageDrawable(getDrawable(R.drawable.open));
                } else if (item.getTruckType2().equals("trailer")) {
                    truckType.setImageDrawable(getDrawable(R.drawable.trailer));
                } else {
                    truckType.setImageDrawable(getDrawable(R.drawable.container));
                }

                if (item.getAssign_id() != null) {

                    assign_id = item.getAssign_id();

                    if (item.getVehicleNumber() != null) {
                        vehiclenumber.setText(item.getVehicleNumber());
                        drivernumber.setText(item.getDriverNumber());
                    } else {
                        vehiclenumber.setText("Not Available");
                        drivernumber.setText("Not Available");
                    }

                    if (response.body().getData().getPod().size() > 0) {
                        pending.setVisibility(View.GONE);
                    } else {
                        pending.setVisibility(View.VISIBLE);
                    }

                    PODAdapter adapter = new PODAdapter(OrderDetails2.this, item.getPod());
                    GridLayoutManager manager = new GridLayoutManager(OrderDetails2.this, 2);
                    pod.setAdapter(adapter);
                    pod.setLayoutManager(manager);

                    DocAdapter adapter2 = new DocAdapter(OrderDetails2.this, item.getInvoice());
                    GridLayoutManager manager2 = new GridLayoutManager(OrderDetails2.this, 2);
                    documents.setAdapter(adapter2);
                    documents.setLayoutManager(manager2);
                    truckcard.setVisibility(View.VISIBLE);

                    LRAdapter adapter3 = new LRAdapter(OrderDetails2.this, item.getLr());
                    GridLayoutManager manager3 = new GridLayoutManager(OrderDetails2.this, 1);
                    lrdownload.setAdapter(adapter3);
                    lrdownload.setLayoutManager(manager3);


                    Log.d("lrcount", item.getLrcount());
                    int lrc = Integer.parseInt(item.getLrcount());
                    if (lrc > 0) {
                        pending2.setVisibility(View.GONE);
                        downloadlr.setVisibility(View.VISIBLE);
                        downloadlr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String url = "https://www.onnway.com/admin/print/lr3.php?id=" + id;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);

                            }
                        });

                    } else {
                        pending2.setVisibility(View.VISIBLE);
                        downloadlr.setVisibility(View.GONE);
                    }


                } else {
                    truckcard.setVisibility(View.GONE);
                }


                freight.setText("\u20B9" + item.getFreight());
                other.setText("\u20B9" + item.getOtherCharges());
                cgst.setText("\u20B9" + item.getCgst());
                sgst.setText("\u20B9" + item.getSgst());
                insurance.setText("\u20B9" + item.getInsurance());


                try {
                    fr = Float.parseFloat(item.getFreight());
                    ot = Float.parseFloat(item.getOtherCharges());
                    cg = Float.parseFloat(item.getCgst());
                    sg = Float.parseFloat(item.getSgst());
                    in = Float.parseFloat(item.getInsurance());

                    if (in > 0) {
                        insurance.setChecked(true);
                    } else {
                        insurance.setChecked(false);
                    }


                    updateSummary();

                } catch (Exception e) {
                    e.printStackTrace();
                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<confirm_full_bean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    class PODAdapter extends RecyclerView.Adapter<PODAdapter.ViewHolder> {

        List<Pod> list = new ArrayList<>();
        Context context;

        public PODAdapter(Context context, List<Pod> list) {
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

            final Pod item = list.get(position);

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            final ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName(), holder.image, options);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    //      WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.zoom_dialog);
                    dialog.setCancelable(true);
                    dialog.show();

                    ImageView img = dialog.findViewById(R.id.image);
                    loader.displayImage(item.getName(), img, options);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder> {

        List<Invoice> list = new ArrayList<>();
        Context context;

        public DocAdapter(Context context, List<Invoice> list) {
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

            final Invoice item = list.get(position);

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            final ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getName(), holder.image, options);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    //      WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.zoom_dialog);
                    dialog.setCancelable(true);
                    dialog.show();

                    ImageView img = dialog.findViewById(R.id.image);
                    loader.displayImage(item.getName(), img, options);

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

    class LRAdapter extends RecyclerView.Adapter<LRAdapter.ViewHolder> {

        List<Lr> list = new ArrayList<>();
        Context context;

        public LRAdapter(Context context, List<Lr> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_list_model2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Lr item = list.get(position);


            holder.image.setText(item.getName2());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                            .setReadTimeout(30_000)
                            .setConnectTimeout(30_000)
                            .build();
                    PRDownloader.initialize(getApplicationContext(), config);

                    int downloadId = PRDownloader.download(item.getName(), Utils.getRootDirPath(getApplicationContext()), item.getName2())
                            .build()
                            .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                @Override
                                public void onStartOrResume() {
                                    progress.setVisibility(View.VISIBLE);
                                }
                            })
                            .start(new OnDownloadListener() {
                                @Override
                                public void onDownloadComplete() {

                                    progress.setVisibility(View.GONE);
                                    Toast.makeText(context, "Downloaded successfully", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onError(Error error) {

                                }

                            });

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

}
