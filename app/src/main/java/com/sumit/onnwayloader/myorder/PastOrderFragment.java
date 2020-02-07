package com.sumit.onnwayloader.myorder;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.onnwayloader.R;
import com.sumit.onnwayloader.myorder.pastorder.PastOrderList;
import com.sumit.onnwayloader.myorder.pastorder.RecyclerAdapter;
import com.sumit.onnwayloader.networking.Post;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastOrderFragment extends Fragment {

RecyclerView recyclerView;
    public PastOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_past_order, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_past_order);
        if (PastOrderList.pastOrderList == null) {
            new Post().pastOrder(getContext(), recyclerView);
        } else {
            setRecyclerBid();
        }
        return  view;
    }

    public void setRecyclerBid() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(PastOrderList.pastOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }

}
