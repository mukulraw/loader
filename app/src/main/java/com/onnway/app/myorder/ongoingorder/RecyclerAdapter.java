package com.onnway.app.myorder.ongoingorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onnway.app.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<OngoingOrderViewHolder> {
    private List<OngoingOrderDetails> ongoingOrderDetails;

    public RecyclerAdapter(List<OngoingOrderDetails> ongoingOrderDetails) {
        this.ongoingOrderDetails = ongoingOrderDetails;
    }

    @NonNull
    @Override
    public OngoingOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ongoing_order_recycler_item,
                viewGroup, false);
        return new OngoingOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingOrderViewHolder ongoingOrderViewHolder, final int i) {
        OngoingOrderDetails sampleOngoingOrder = ongoingOrderDetails.get(i);

        ongoingOrderViewHolder.orderId.setText(sampleOngoingOrder.orderId);
        ongoingOrderViewHolder.orderDate.setText(sampleOngoingOrder.orderDate);
        ongoingOrderViewHolder.sourceAddr.setText(sampleOngoingOrder.sourceAddr);
        ongoingOrderViewHolder.destinationAddr.setText(sampleOngoingOrder.destinationAddr);
        ongoingOrderViewHolder.materialWeight.setText(sampleOngoingOrder.materialWeight);
        ongoingOrderViewHolder.materialType.setText(sampleOngoingOrder.materialType);
        ongoingOrderViewHolder.payableFreight.setText(sampleOngoingOrder.payableFreight);
        ongoingOrderViewHolder.advanceFreight.setText(sampleOngoingOrder.advanceFreight);

        ongoingOrderViewHolder.upcomingOrderCardDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OngoingOrderFragment.enableBottomSheet(ongoingOrderDetails.get(i).orderId,"₹ " + ongoingOrderDetails.get(i).payableFreight, ongoingOrderDetails.get(i).upComingStatus, ongoingOrderDetails.get(i).upComingTruckNumber, ongoingOrderDetails.get(i).upComingDeliveryAddress, "gds");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ongoingOrderDetails.size();
    }
}
