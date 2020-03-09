package com.mukul.onnwayloader.myorder;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.myorder.ongoingorder.OngoingOrderList;
import com.mukul.onnwayloader.myorder.ongoingorder.RecyclerAdapter;
import com.mukul.onnwayloader.networking.Post;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngoingOrderFragment extends Fragment {

    RecyclerView recyclerView;
    public BottomSheetBehavior sheetBehavior;

    public static FragmentManager fragmentManager;

    public OngoingOrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ongoing_order, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_ongoing_order);

        LinearLayout linearLayout = view.findViewById(R.id.hello_bottom);
        sheetBehavior = BottomSheetBehavior.from(linearLayout);

        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        if (OngoingOrderList.ongoingOrderLists == null) {
            new Post().doUpcoming(getContext(), recyclerView);
        } else {
//            new Post().doUpcoming(getContext(), recyclerView);
            setRecyclerBid();
        }
        fragmentManager = getFragmentManager();
        return  view;
    }
    public void setRecyclerBid() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(OngoingOrderList.ongoingOrderLists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public static void enableBottomSheet(final String bid, String totalAmount, String orderStatus, String truckNumber, String deliveryAddress, String orderStatusButton) {

        BottomSheet bottomSheetFragment = new BottomSheet();

        Bundle args = new Bundle();
        args.putString("totalAmt", totalAmount);
        args.putString("delvAdd", deliveryAddress);

        if(orderStatus.equals("0")) {
            args.putString("truckNo", "Not Assigned");
            args.putString("ordrStat", "Truck not assigned");
            args.putString("btn", "Add Truck");
//            bId = bid;
        } else if (orderStatus.equals("1")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "Waiting for Shipment");
            args.putString("btn", "Close");
        } else if (orderStatus.equals("2")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "Order on the way");
            args.putString("btn", "Track Order");
        } else if (orderStatus.equals("3")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "Order delivered");
            args.putString("btn", "Close");
        } else if (orderStatus.equals("4")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "POD pending");
            args.putString("btn", "Upload POD");
        } else if (orderStatus.equals("5")) {
            args.putString("truckNo", truckNumber);
            args.putString("ordrStat", "POD done");
            args.putString("btn", "Close");
        }
        bottomSheetFragment.setArguments(args);
        bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());

    }
}
