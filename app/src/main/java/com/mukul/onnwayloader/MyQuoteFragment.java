package com.mukul.onnwayloader;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mukul.onnwayloader.myorder.OngoingOrderFragment;
import com.mukul.onnwayloader.myquotes.MyQuoteList;
import com.mukul.onnwayloader.myquotes.RecyclerAdapter;
import com.mukul.onnwayloader.networking.AppController;
import com.mukul.onnwayloader.networking.Post;
import com.mukul.onnwayloader.orderHistoryPOJO.Datum;
import com.mukul.onnwayloader.orderHistoryPOJO.orderHistoryBean;
import com.mukul.onnwayloader.updateProfilePOJO.updateProfileBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MyQuoteFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progress;
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

        adapter = new OrderAdapter(getContext(), list);
        manager = new GridLayoutManager(getContext(), 1);

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

        Call<orderHistoryBean> call = cr.getQuotes(SharePreferenceUtils.getInstance().getString("userId"));

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
        public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.order_list_model3, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
            final Datum item = list.get(position);

            holder.type.setText(item.getLaodType());
            holder.orderid.setText("Order #" + item.getId());
            holder.date.setText(item.getCreated());
            holder.source.setText(item.getSource());
            holder.destination.setText(item.getDestination());
            holder.material.setText(item.getMaterial());
            holder.weight.setText(item.getWeight());
            holder.truck.setText(item.getTruckType());
            try {
                float fr = Float.parseFloat(item.getFreight());
                float ot = Float.parseFloat(item.getOtherCharges());
                float cg = Float.parseFloat(item.getCgst());
                float sg = Float.parseFloat(item.getSgst());
                float in = Float.parseFloat(item.getInsurance());

                float gr = fr + ot + cg + sg + in;
                holder.freight.setText("\u20B9" + gr);
            } catch (Exception e) {
                e.printStackTrace();
                holder.freight.setText("\u20B9" + "0");
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(context , OrderDetails.class);
                    intent.putExtra("id" , item.getId());
                    context.startActivity(intent);*/
                }
            });


            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress.setVisibility(View.VISIBLE);

                    AppController b = (AppController) context.getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<updateProfileBean> call = cr.update_order(
                            item.getId(),
                            "placed"
                    );

                    call.enqueue(new Callback<updateProfileBean>() {
                        @Override
                        public void onResponse(Call<updateProfileBean> call, Response<updateProfileBean> response) {

                            if (response.body().getStatus().equals("1")) {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                onResume();
                            } else {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<updateProfileBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
            });


            holder.getcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, Web.class);
                    intent.putExtra("title", "Contact Us");
                    intent.putExtra("url", "https://www.onnway.com/contactonnway.php");
                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView type, orderid, date, source, destination, material, weight, freight, truck;
            Button confirm, getcall;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                type = itemView.findViewById(R.id.textView65);
                orderid = itemView.findViewById(R.id.textView66);
                date = itemView.findViewById(R.id.textView67);
                source = itemView.findViewById(R.id.textView69);
                destination = itemView.findViewById(R.id.textView70);
                material = itemView.findViewById(R.id.textView72);
                weight = itemView.findViewById(R.id.textView74);
                freight = itemView.findViewById(R.id.textView76);
                truck = itemView.findViewById(R.id.textView64);
                confirm = itemView.findViewById(R.id.button6);
                getcall = itemView.findViewById(R.id.button7);

            }
        }
    }

}
