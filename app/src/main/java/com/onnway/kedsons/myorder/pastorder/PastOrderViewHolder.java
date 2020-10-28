package com.onnway.kedsons.myorder.pastorder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.onnway.kedsons.R;

public class PastOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView orderId;
    public TextView orderDate;
    public TextView sourceAddr;
    public TextView destinationAddr;
    public TextView materialType;
    public TextView materialWeight;
    public TextView payableFreight;


    public CardView pastOrderCardDriver;

    public PastOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        pastOrderCardDriver = itemView.findViewById(R.id.past_order_recycler_card);
        orderId = itemView.findViewById(R.id.past_order_id_tv);
        orderDate = itemView.findViewById(R.id.past_order_date);
        sourceAddr = itemView.findViewById(R.id.past_order_source_addr);
        destinationAddr = itemView.findViewById(R.id.past_order_destination_addr);
        materialType = itemView.findViewById(R.id.past_order_material_type);
        materialWeight = itemView.findViewById(R.id.past_order_material_weight);
        payableFreight = itemView.findViewById(R.id.past_order_payable_freight);
    }
}
