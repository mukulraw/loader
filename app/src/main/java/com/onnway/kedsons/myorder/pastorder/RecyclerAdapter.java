package com.onnway.kedsons.myorder.pastorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onnway.kedsons.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<PastOrderViewHolder>{
    private List<PastOrderDetails> pastOrderDetails;
    public RecyclerAdapter(List<PastOrderDetails> pastOrderDetails) {
        this.pastOrderDetails = pastOrderDetails;
    }

    @NonNull
    @Override
    public PastOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_order_recycler_item,
                viewGroup, false);
        return new PastOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrderViewHolder pastOrderViewHolder, final int i) {
        PastOrderDetails samplePastOrder = pastOrderDetails.get(i);

        pastOrderViewHolder.orderId.setText(samplePastOrder.orderId);
        pastOrderViewHolder.orderDate.setText(samplePastOrder.orderDate);
        pastOrderViewHolder.sourceAddr.setText(samplePastOrder.sourceAddr);
        pastOrderViewHolder.destinationAddr.setText(samplePastOrder.destinationAddr);
        pastOrderViewHolder.materialWeight.setText(samplePastOrder.materialWeight);
        pastOrderViewHolder.materialType.setText(samplePastOrder.materialType);
        pastOrderViewHolder.payableFreight.setText(samplePastOrder.payableFreight);
    }

    @Override
    public int getItemCount() {
        return pastOrderDetails.size();
    }
}
