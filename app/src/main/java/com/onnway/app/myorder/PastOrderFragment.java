package com.onnway.app.myorder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onnway.app.AllApiIneterface;
import com.onnway.app.MainActivity;
import com.onnway.app.OrderDetails2;
import com.onnway.app.R;
import com.onnway.app.SharePreferenceUtils;
import com.onnway.app.myorder.pastorder.PastOrderList;
import com.onnway.app.myorder.pastorder.RecyclerAdapter;
import com.onnway.app.networking.AppController;
import com.onnway.app.orderHistoryPOJO.Datum;
import com.onnway.app.orderHistoryPOJO.orderHistoryBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastOrderFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progress;
    List<Datum> list;
    OrderAdapter adapter;
    GridLayoutManager manager;
    LinearLayout hide;
    Button order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing_order, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_ongoing_order);
        progress = view.findViewById(R.id.progress);
        hide = view.findViewById(R.id.hide);
        order = view.findViewById(R.id.order);
        list = new ArrayList<>();

        adapter = new OrderAdapter(getContext(), list);
        manager = new GridLayoutManager(getContext(), 1);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.bottom_nav_find_truck);

            }
        });


        return view;
    }

    public void setRecyclerBid() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(PastOrderList.pastOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
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

        Call<orderHistoryBean> call = cr.getPastOrders(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<orderHistoryBean>() {
            @Override
            public void onResponse(Call<orderHistoryBean> call, Response<orderHistoryBean> response) {


                if (response.body().getData().size() > 0) {
                    hide.setVisibility(View.GONE);
                } else {
                    hide.setVisibility(View.VISIBLE);
                }
                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<orderHistoryBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    static class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

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
            View view = inflater.inflate(R.layout.order_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Datum item = list.get(position);

            holder.type.setText(item.getLaodType());
            holder.orderid.setText("Order #" + item.getId());
            holder.date.setText(item.getCreated());
            holder.source.setText(item.getSource());
            holder.destination.setText(item.getDestination());
            holder.material.setText(item.getMaterial());
            holder.weight.setText(item.getWeight());
            holder.truck.setText(item.getTruckType());
            holder.status.setText(item.getStatus());
            holder.schedule.setText(item.getSchedule());

            float fr = Float.parseFloat(item.getFreight());
            float ot = 0;
            float cg = 0;
            float sg = 0;
            float in = 0;

            try {
                ot = Float.parseFloat(item.getOtherCharges());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                cg = Float.parseFloat(item.getCgst());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                sg = Float.parseFloat(item.getSgst());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                in = Float.parseFloat(item.getInsurance());
            } catch (Exception e) {
                e.printStackTrace();
            }

            float gr = fr + ot + cg + sg + in;
            holder.freight.setText("\u20B9" + gr);

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
                    Intent intent = new Intent(context, OrderDetails2.class);
                    intent.putExtra("id", item.getId());
                    context.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            TextView type, orderid, date, source, destination, material, weight, freight, truck, status, schedule;
            ImageView truckType;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                truckType = itemView.findViewById(R.id.imageView8);
                type = itemView.findViewById(R.id.textView65);
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

            }
        }
    }

}
