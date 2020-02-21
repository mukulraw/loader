package com.sumit.onnwayloader.myorder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sumit.onnwayloader.R;


public class BottomSheet extends BottomSheetDialogFragment {
    public static TextView total_amount_tv, order_status_tv, truck_number_tv, delivery_address_tv, amt;
    public static Button order_status_btn;

    public BottomSheet() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        amt = view.findViewById(R.id.amt);

        total_amount_tv = view.findViewById(R.id.total_amount_tv);
        order_status_tv = view.findViewById(R.id.order_status_tv);
        truck_number_tv = view.findViewById(R.id.order_truck_number);
        delivery_address_tv = view.findViewById(R.id.order_deleivery_address);
        order_status_btn = view.findViewById(R.id.order_status_button);

        total_amount_tv.setText(getArguments().getString("totalAmt"));
        delivery_address_tv.setText(getArguments().getString("delvAdd"));
        truck_number_tv.setText(getArguments().getString("truckNo"));
        order_status_tv.setText(getArguments().getString("ordrStat"));
        order_status_btn.setText(getArguments().getString("btn"));
        order_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order_status_btn.getText().equals("Add Truck")) {
//                    Intent intent = new Intent(getContext(), AssignTruck.class);
//                    startActivity(intent);
                } else if (order_status_btn.getText().equals("Close")) {

                } else if (order_status_btn.getText().equals("Upload POD")) {
//                    Intent intent = new Intent(getContext(), UploadPOD.class);
//                    startActivity(intent);
                } else if (order_status_btn.getText().equals("POD done")) {

                } else if (order_status_btn.getText().equals("Track Order")) {
//                    Intent intent = new Intent(getContext(), UpdateMobileActivity.class);
//                    startActivity(intent);
                }
            }
        });
        return view;
    }
}