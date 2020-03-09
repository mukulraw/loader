package com.mukul.onnwayloader.waitingrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mukul.onnwayloader.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<WaitingTruckViewHolder> {
    private List<WaitingTruckDetails> waitingTruckDetails;
    Context context;

    public RecyclerAdapter(List<WaitingTruckDetails> waitingTruckDetails, Context context) {
        this.waitingTruckDetails = waitingTruckDetails;
        this.context = context;
    }

    public RecyclerAdapter(List<WaitingTruckDetails> waitingTruckDetails) {
        this.waitingTruckDetails = waitingTruckDetails;
    }

    @NonNull
    @Override
    public WaitingTruckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.waiting_load_recycler_item, parent, false);
        return new WaitingTruckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingTruckViewHolder holder, int position) {
        WaitingTruckDetails sampleWaitingTruck = waitingTruckDetails.get(position);

        holder.tvOrderId.setText(sampleWaitingTruck.orderId);
        holder.tvOrderDate.setText(sampleWaitingTruck.orderDate);
        holder.tvSrcAddr.setText(sampleWaitingTruck.sourceAddr);
        holder.tvDestAddr.setText(sampleWaitingTruck.destinationAddr);
        holder.tvMaterialType.setText(sampleWaitingTruck.materialType);
        holder.tvMaterialWeight.setText(sampleWaitingTruck.materialWeight);
        if (sampleWaitingTruck.loadType.equals("1")) {
//            Toast.makeText(context, "FullLoad", Toast.LENGTH_LONG).show();
//            holder.load1.setText("F");
//            holder.load2.setText("u");
//            holder.load3.setText("l");
//            holder.load4.setText("l");
        } else if(sampleWaitingTruck.loadType.equals("2")) {
//            holder.load1.setText("P");
//            holder.load2.setText("a");
//            holder.load3.setText("r");
//            holder.load4    .setText("t");
        }
    }

    @Override
    public int getItemCount() {
        return waitingTruckDetails.size();
    }
}
