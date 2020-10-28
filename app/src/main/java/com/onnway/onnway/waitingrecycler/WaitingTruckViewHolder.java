package com.onnway.onnway.waitingrecycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.onnway.onnway.R;

public class WaitingTruckViewHolder extends RecyclerView.ViewHolder {
    public TextView tvOrderId, tvOrderDate, tvSrcAddr, tvDestAddr, tvMaterialType, tvMaterialWeight, load1, load2, load3, load4;
    public ImageView orderStatus;
    public CardView truckCard;

    public WaitingTruckViewHolder(View view) {
        super(view);
        tvOrderId = view.findViewById(R.id.full_order_id_tv);
        tvOrderDate = view.findViewById(R.id.full_order_date);
        tvSrcAddr = view.findViewById(R.id.full_order_source_addr);
        tvDestAddr = view.findViewById(R.id.full_order_destination_addr);
        tvMaterialType = view.findViewById(R.id.full_order_material_type);
        tvMaterialWeight = view.findViewById(R.id.full_order_material_weight);
        orderStatus = view.findViewById(R.id.full_order_status_iv);
    }
}
