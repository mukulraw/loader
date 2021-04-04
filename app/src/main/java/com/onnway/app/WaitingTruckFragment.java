package com.onnway.app;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onnway.app.networking.AppController;
import com.onnway.app.orderHistoryPOJO.Datum;
import com.onnway.app.orderHistoryPOJO.orderHistoryBean;
import com.onnway.app.updateProfilePOJO.updateProfileBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingTruckFragment extends Fragment {

    RecyclerView recyclerView;
    static ProgressBar progress;
    List<Datum> list;
    OrderAdapter adapter;
    GridLayoutManager manager;
    LinearLayout hide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing_order, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_ongoing_order);
        progress = view.findViewById(R.id.progress);
        hide = view.findViewById(R.id.hide);
        list = new ArrayList<>();

        adapter = new OrderAdapter(getActivity(), list);
        manager = new GridLayoutManager(getActivity(), 1);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getActivity().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<orderHistoryBean> call = cr.getWaitingTrucks(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<orderHistoryBean>() {
            @Override
            public void onResponse(Call<orderHistoryBean> call, Response<orderHistoryBean> response) {

                if (response.body().getData().size() > 0) {
                    hide.setVisibility(View.GONE);
                } else {
                    hide.setVisibility(View.VISIBLE);
                }


                Intent registrationComplete = new Intent("count");

                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(registrationComplete);


                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<orderHistoryBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

        Context context;
        List<Datum> list = new ArrayList<>();

        OrderAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.order_list_model2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            holder.setIsRecyclable(false);

            final Datum item = list.get(position);

            holder.type.setText(item.getLaodType());
            holder.orderid.setText("Order #" + item.getId());
            holder.date.setText(item.getCreated());
            holder.source.setText(item.getSource());
            holder.destination.setText(item.getDestination());
            holder.material.setText(item.getMaterial());
            holder.weight.setText(item.getWeight());
            holder.truck.setText(item.getTruckType());
            holder.schedule.setText(item.getSchedule());

            if (item.getBid_amount().length() > 0)
            {

                float fr = 0, ot = 0, cg = 0, sg = 0, in = 0;

                try {
                    fr = Float.parseFloat(item.getFreight());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    ot = Float.parseFloat(item.getOtherCharges());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    cg = Float.parseFloat(item.getCgst());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    sg = Float.parseFloat(item.getSgst());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                try {
                    in = Float.parseFloat(item.getInsurance());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    float gr = fr + ot + cg + sg;
                    holder.bidamount.setText("₹ " + gr);
                } catch (Exception e) {
                    e.printStackTrace();
                    holder.status.setText(item.getStatus());
                }

                //holder.bidamount.setText("₹ " + item.getBid_amount());

                holder.bidamounttitle.setVisibility(View.VISIBLE);
                holder.bidamount.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.bidamounttitle.setVisibility(View.GONE);
                holder.bidamount.setVisibility(View.GONE);
            }

            if (item.getStatus().equals("accepted quote")) {

                float fr = 0, ot = 0, cg = 0, sg = 0, in = 0;

                try {
                    fr = Float.parseFloat(item.getFreight());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    ot = Float.parseFloat(item.getOtherCharges());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    cg = Float.parseFloat(item.getCgst());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    sg = Float.parseFloat(item.getSgst());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                try {
                    in = Float.parseFloat(item.getInsurance());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


                try {
                    float gr = fr + ot + cg + sg;
                    holder.status.setText(item.getStatus() + " (₹ " + gr + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                    holder.status.setText(item.getStatus());
                }


            }else if (item.getStatus().equals("requsted for quote"))
            {
                holder.status.setText("requested for quote");
            }else {
                holder.status.setText(item.getStatus());
            }


            String dateString = item.getCreated();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(dateString);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 1);

                Date date1 = sdf.parse(item.getCurrent());

                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date1);

                Date startDate = calendar.getTime();

                Date currentTime = calendar2.getTime();

                //long diffInMs = currentTime.getTime() - startDate.getTime();
                long diffInMs = startDate.getTime() - currentTime.getTime();


                if (diffInMs > 0) {

                    Log.d("ms1", String.valueOf(startDate.getTime()));
                    Log.d("ms", String.valueOf(currentTime.getTime()));


                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

                    CountDownTimer timer = new CountDownTimer(diffInMs, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            holder.freight.setText(convertSecondsToHMmSs(millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {

                        }
                    };

                    timer.start();
                } else {
                    holder.freight.setText("---");
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context, R.style.MyDialogTheme)
                            .setTitle("Cancel Booking")
                            .setMessage("Are you sure you want to cancel this booking?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int which) {


                                    progress.setVisibility(View.VISIBLE);

                                    AppController b = (AppController) context.getApplicationContext();

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(b.baseurl)
                                            .addConverterFactory(ScalarsConverterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                    Call<updateProfileBean> call = cr.cancel_order_loader(
                                            item.getId()
                                    );

                                    call.enqueue(new Callback<updateProfileBean>() {
                                        @Override
                                        public void onResponse(Call<updateProfileBean> call, Response<updateProfileBean> response) {

                                            if (response.body().getStatus().equals("1")) {
                                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                onResume();
                                            } else {
                                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }


                                            progress.setVisibility(View.GONE);

                                        }

                                        @Override
                                        public void onFailure(Call<updateProfileBean> call, Throwable t) {
                                            progress.setVisibility(View.GONE);
                                        }
                                    });


                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                }
            });

            try {
                if (item.getTruckType2().equals("open truck")) {
                    holder.truckType.setImageDrawable(context.getDrawable(R.drawable.open));
                } else if (item.getTruckType2().equals("trailer")) {
                    holder.truckType.setImageDrawable(context.getDrawable(R.drawable.trailer));
                } else {
                    holder.truckType.setImageDrawable(context.getDrawable(R.drawable.container));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getLaodType().equals("full")) {
                        Intent intent = new Intent(context, OrderDetails5.class);
                        intent.putExtra("id", item.getId());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, OrderDetails4.class);
                        intent.putExtra("id", item.getId());
                        context.startActivity(intent);
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public String convertSecondsToHMmSs(long seconds) {
            long s = seconds % 60;
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;
            return String.format("%d:%02d:%02d", h, m, s);
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView type, orderid, date, source, destination, material, weight, freight, truck, status, schedule, bidamount, bidamounttitle;

            ImageView truckType;
            Button cancel;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                type = itemView.findViewById(R.id.textView65);
                truckType = itemView.findViewById(R.id.imageView8);
                schedule = itemView.findViewById(R.id.textView145);
                orderid = itemView.findViewById(R.id.textView66);
                date = itemView.findViewById(R.id.textView67);
                source = itemView.findViewById(R.id.textView69);
                destination = itemView.findViewById(R.id.textView70);
                material = itemView.findViewById(R.id.textView72);
                weight = itemView.findViewById(R.id.textView74);
                freight = itemView.findViewById(R.id.textView76);
                truck = itemView.findViewById(R.id.textView64);
                status = itemView.findViewById(R.id.textView79);
                cancel = itemView.findViewById(R.id.button13);
                bidamount = itemView.findViewById(R.id.textView153);
                bidamounttitle = itemView.findViewById(R.id.textView152);

            }
        }
    }

}
