package com.onnway.app.myquotes;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onnway.app.R;

public class MyQuoteViewHolder extends RecyclerView.ViewHolder {
    TextView quoteId, quoteDate, sourceAddress, destinationAddress, materialType, materialWeight, payableFreight;
    Button confirmBookingBtn, getCallBtn;

    public MyQuoteViewHolder(@NonNull View itemView) {
        super(itemView);

        quoteId = itemView.findViewById(R.id.my_quote_id_tv);
        quoteDate = itemView.findViewById(R.id.my_quote_date);
        sourceAddress = itemView.findViewById(R.id.my_quote_source_addr);
        destinationAddress = itemView.findViewById(R.id.my_quote_destination_addr);
        materialType = itemView.findViewById(R.id.my_quote_material_type);
        materialWeight = itemView.findViewById(R.id.my_quote_material_weight);
        payableFreight = itemView.findViewById(R.id.my_quote_payable_freight);

        confirmBookingBtn = itemView.findViewById(R.id.my_quote_confirm_booking);
        getCallBtn = itemView.findViewById(R.id.my_quote_get_call);
    }
}
