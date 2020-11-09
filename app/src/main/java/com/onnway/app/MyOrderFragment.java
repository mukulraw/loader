package com.onnway.app;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.onnway.app.myorder.OngoingOrderFragment;
import com.onnway.app.myorder.PastOrderFragment;
import com.onnway.app.myorder.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {

    /*
    * This fragment is the main fragment of the MyOrder fragment
    * Here, we are having TabLayout with two options i.e. the Ongoing order option and the PastOrder option
    * You can find the code of the Ongoing and Past fragment in the com.sumit.onnwayloader.myorder.OngoingOrderFragment
    * and com.sumit.onnwayloader.myorder.PastOrderFragment
    * */

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        //for view pager
        viewPager = view.findViewById(R.id.my_order_view_pager);
        viewPagerAdapter.addFragment(new OngoingOrderFragment(), "Ongoing");
        viewPagerAdapter.addFragment(new PastOrderFragment(), "Past");
        viewPager.setAdapter(viewPagerAdapter);

        //for tablayout
        tabLayout = view.findViewById(R.id.my_order_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#2E2E2E"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#2E2E2E"), Color.parseColor("#444444"));
        return view;
    }


}
