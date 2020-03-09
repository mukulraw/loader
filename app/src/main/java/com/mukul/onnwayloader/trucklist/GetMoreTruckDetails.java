package com.mukul.onnwayloader.trucklist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mukul.onnwayloader.R;

public class GetMoreTruckDetails extends AppCompatActivity {

    private LinearLayout truck_ll, container_ll, trailer_ll;
    private TextView displaySelectedTruck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_more_truck_details);

        //Linear Layout of truck types
        truck_ll = (LinearLayout) findViewById(R.id.truck_ll);
        container_ll = (LinearLayout) findViewById(R.id.container_ll);
        trailer_ll = (LinearLayout) findViewById(R.id.trailer_ll);

        displaySelectedTruck = (TextView) findViewById(R.id.display_selected_truck);

        //handling alertDialog for Truck
        truck_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                AlertDialog.Builder builder = new AlertDialog.Builder(GetMoreTruckDetails.this, R.style.MyDialogTheme);
                final String[] singleChoiceItems = getResources().getStringArray(R.array.truck_type);
                int itemSelected = -1; // no item selected
                final String[] selectedTruck = new String[1]; // selected item will be stored in this array
                builder.setTitle("Select the type of Truck")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            //when the item is clicked in the alertDialog
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedTruck[0] = singleChoiceItems[selectedIndex];
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            //after pressing the Ok button
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(GetMoreTruckDetails.this, selectedTruck[0], Toast.LENGTH_LONG).show();
                                displaySelectedTruck.setText("Selected Truck : " + selectedTruck[0]);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        //handling alertDialog for Container
        container_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                AlertDialog.Builder builder = new AlertDialog.Builder(GetMoreTruckDetails.this, R.style.MyDialogTheme);
                final String[] singleChoiceItems = getResources().getStringArray(R.array.container_type);
                int itemSelected = -1; // no item selected
                final String[] selectedTruck = new String[1]; // selected item will be stored in this array
                builder.setTitle("Select the type of Container")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            //when the item is clicked in the alertDialog
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedTruck[0] = singleChoiceItems[selectedIndex];
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            //after pressing the Ok button
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(GetMoreTruckDetails.this, selectedTruck[0], Toast.LENGTH_LONG).show();
                                displaySelectedTruck.setText("Selected Truck : " + selectedTruck[0]);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        //handling alertDialog for Trailer
        trailer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide keyboard if active
                AlertDialog.Builder builder = new AlertDialog.Builder(GetMoreTruckDetails.this, R.style.MyDialogTheme);
                final String[] singleChoiceItems = getResources().getStringArray(R.array.trailer_type);
                int itemSelected = -1;// no item selected
                final String[] selectedTruck = new String[1];// selected item will be stored in this array
                builder.setTitle("Select the type of Trailer")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            //when the item is clicked in the alertDialog
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                selectedTruck[0] = singleChoiceItems[selectedIndex];
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            //after pressing the Ok button
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(GetMoreTruckDetails.this, selectedTruck[0], Toast.LENGTH_LONG).show();
                                displaySelectedTruck.setText("Selected Truck : " + selectedTruck[0]);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }
}