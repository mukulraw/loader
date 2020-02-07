package com.sumit.onnwayloader;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.onnwayloader.myquotes.MyQuoteList;
import com.sumit.onnwayloader.myquotes.RecyclerAdapter;
import com.sumit.onnwayloader.networking.Post;
import com.sumit.onnwayloader.waitingrecycler.WaitingTruckList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingTruckFragment extends Fragment {

    RecyclerView recyclerView;

    public WaitingTruckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_truck, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_waiting_truck);
        if(WaitingTruckList.waitingTruckDetailsList == null) {
            new Post().myWaitingOrder(getContext(), recyclerView);
        }else {
            setRecyclerBid();
        }
        // Inflate the layout for this fragment
        return view;
    }

    public void setRecyclerBid() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        com.sumit.onnwayloader.waitingrecycler.RecyclerAdapter recyclerAdapter = new com.sumit.onnwayloader.waitingrecycler.RecyclerAdapter(WaitingTruckList.waitingTruckDetailsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

    }

}
