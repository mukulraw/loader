package com.mukul.onnwayloader.materialtype;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mukul.onnwayloader.R;
import com.mukul.onnwayloader.getpricenetworking.GetPrice;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialType extends DialogFragment {

    LinearLayout ll1, ll2,ll3, ll4, ll5, ll6,
            ll7, ll8, ll9,ll10, ll11, ll12, ll13,
            ll14, ll15, ll16,ll17, ll18;
    Button backBtn, checkFare;
    SeekBar seekBar;
    TextView textView;

    private GetPrice getPrice;
    public MaterialType() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            if (getArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert Dialog");
        builder.setMessage("Alert Dialog inside DialogFragment");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_material_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seekBar = view.findViewById(R.id.seekBar);
        textView = view.findViewById(R.id.count);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarProgress = i;
                textView.setText("Weight: " + seekBarProgress + " / " + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Weight: " + seekBarProgress + " / " + seekBar.getMax());
            }
        });
        ll1 = view.findViewById(R.id.ll1);
        ll2 = view.findViewById(R.id.ll2);
        ll3 = view.findViewById(R.id.ll3);
        ll4 = view.findViewById(R.id.ll4);
        ll5 = view.findViewById(R.id.ll5);
        ll6 = view.findViewById(R.id.ll6);
        ll7 = view.findViewById(R.id.ll7);
        ll8 = view.findViewById(R.id.ll8);
        ll9 = view.findViewById(R.id.ll9);
        ll10 = view.findViewById(R.id.ll10);
        ll11 = view.findViewById(R.id.ll11);
        ll12 = view.findViewById(R.id.ll12);
        ll13 = view.findViewById(R.id.ll13);
        ll14 = view.findViewById(R.id.ll14);
        ll15 = view.findViewById(R.id.ll15);
        ll16 = view.findViewById(R.id.ll16);
        ll17 = view.findViewById(R.id.ll17);
        ll18 = view.findViewById(R.id.ll18);

        backBtn = view.findViewById(R.id.backBtn);
        checkFare = view.findViewById(R.id.checkFare);


        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        checkFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*getPrice = new GetPrice();
                getPrice.currentMobile = EnterNumberActivity.mCurrentMobileNumber;
                getPrice.sourceAddress = FindTruckFragment.srcAddress;
                getPrice.destinationAddress = FindTruckFragment.destAddress;
                getPrice.truckType = FindTruckFragment.truckType;
                new Post().getPrice(getActivity(), getPrice);
                dismiss();*/
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
