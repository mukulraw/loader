package com.mukul.onnwayloader.myquotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.shippingaddress.ShippingAddress;

import java.util.List;

public class RecyclerAdapter  extends RecyclerView.Adapter<MyQuoteViewHolder> {

    private List<MyQuoteDetails> myQuoteDetails;

    Context context;
//    public RecyclerAdapter(List<MyQuoteDetails> myQuoteDetails, Context context) {
//        this.myQuoteDetails = myQuoteDetails;
//        this.context = context;
//    }

    public RecyclerAdapter(List<MyQuoteDetails> myQuoteLists) {
        this.myQuoteDetails = myQuoteLists;
    }

    @NonNull
    @Override
    public MyQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_quote_recycler_adapter, parent,false);
        return new MyQuoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyQuoteViewHolder holder, final int position) {
        MyQuoteDetails sampleMyQuote = myQuoteDetails.get(position);

        holder.quoteId.setText(sampleMyQuote.quoteId);
        holder.quoteDate.setText(sampleMyQuote.quoteDate);
        holder.sourceAddress.setText(sampleMyQuote.sourceAddress);
        holder.destinationAddress.setText(sampleMyQuote.destinationAddress);
        holder.materialType.setText(sampleMyQuote.materialType);
        holder.materialWeight.setText(sampleMyQuote.materialWeight);
        holder.payableFreight.setText(sampleMyQuote.payableFreight);

        holder.confirmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShippingAddress.isQuote = myQuoteDetails.get(position).quoteId;
                Intent myIntent = new Intent(view.getContext(), ShippingAddress.class);
                view.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myQuoteDetails.size();
    }
}
