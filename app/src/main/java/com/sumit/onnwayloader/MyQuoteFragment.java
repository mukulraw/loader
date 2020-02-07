package com.sumit.onnwayloader;

import android.content.Intent;
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


public class MyQuoteFragment extends Fragment {

    RecyclerView recyclerView;

    public MyQuoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_quote, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_my_quote);

        if(MyQuoteList.myQuoteLists == null) {
            new Post().myQuotes(getContext(),recyclerView);
        }else {
            setRecyclerBid();
        }
        // Inflate the layout for this fragment
        return view;
    }

    public void setRecyclerBid() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(MyQuoteList.myQuoteLists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }

}
