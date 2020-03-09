package com.mukul.onnwayloader.myorder.ongoingorder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mukul.onnwayloader.R;

public class OngoingOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView orderId;
    public TextView orderDate;
    public TextView sourceAddr;
    public TextView destinationAddr;
    public TextView materialType;
    public TextView materialWeight;
    public TextView payableFreight;
    public TextView advanceFreight;

    public CardView upcomingOrderCardDriver;

    public OngoingOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        upcomingOrderCardDriver = itemView.findViewById(R.id.ongoing_order_recycler_card);
        orderId = itemView.findViewById(R.id.ongoing_order_id_tv);
        orderDate = itemView.findViewById(R.id.ongoing_order_date);
        sourceAddr = itemView.findViewById(R.id.ongoing_order_source_addr);
        destinationAddr = itemView.findViewById(R.id.ongoing_order_destination_addr);
        materialType = itemView.findViewById(R.id.ongoing_order_material_type);
        materialWeight = itemView.findViewById(R.id.ongoing_order_material_weight);
        payableFreight = itemView.findViewById(R.id.ongoing_order_payable_freight);
        advanceFreight = itemView.findViewById(R.id.ongoing_order_adv_payment);
    }
}
