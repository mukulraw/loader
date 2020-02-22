package com.sumit.onnwayloader;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sumit.onnwayloader.getpricenetworking.GetPrice;
import com.sumit.onnwayloader.materialtype.MaterialActivity;
import com.sumit.onnwayloader.materialtype.MaterialType;
import com.sumit.onnwayloader.networking.AppController;
import com.sumit.onnwayloader.networking.Post;
import com.sumit.onnwayloader.otp.EnterNumberActivity;
import com.sumit.onnwayloader.shipmentdetails.ShipmentActivity;
import com.sumit.onnwayloader.truckTypePOJO.truckTypeBean;
import com.sumit.onnwayloader.trucklist.GetMoreTruckDetails;
import com.sumit.onnwayloader.vehicletype.ContainerType;
import com.sumit.onnwayloader.vehicletype.OpenTruckType;
import com.sumit.onnwayloader.vehicletype.TrailerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindTruckFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OpenTruckType.DialogListener {

    /*
     * This fragment is used to load the findtruckfragment
     * Here, the loader can find the truck by entering the source and destination address
     * */


    //variables for the button, textview and card

    //CardView
    private ImageButton nextCard;

    Button partLoad, fullLoad;


    LinearLayout bottom;
    //ImageView


    //TextView
    private TextView sourceAddress, destinationAddress, schedulePickupDate;

    //Buttons
    private LinearLayout openTruckBtn, containerBtn, trailerBtn;


    //find current location
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;

    //places auto complete
    private int AUTOCOMPLETE_REQUEST_CODE = 1;


    //taking details
    public static String truckType;

    private String loadType = "1", srcAddress = "", destAddress = "",pickUpDate = "";

    private int addressTyp = 0;

    private GetPrice getPrice;

    public FindTruckFragment() {
        // Required empty public constructor
    }

    String tid = "";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_truck, container, false);

        //checking location permission
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //setting the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
        view.performClick();

        //cardView
        partLoad = view.findViewById(R.id.part_load_card);
        fullLoad = view.findViewById(R.id.full_load_card);
        nextCard = view.findViewById(R.id.next_card);

        //imageview


        //textView
        sourceAddress = view.findViewById(R.id.enter_source_tv);
        bottom = view.findViewById(R.id.bottom);
        destinationAddress = view.findViewById(R.id.enter_destination_tv);
        schedulePickupDate = view.findViewById(R.id.schedule_pick_up_date_tv);


        //Button
        openTruckBtn = view.findViewById(R.id.open_truck_btn);
        containerBtn = view.findViewById(R.id.container_btn);
        trailerBtn = view.findViewById(R.id.trailer_btn);


        sourceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressTyp = 1;
                getLocation();
            }
        });

        destinationAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressTyp = 2;
                getLocation();
            }
        });

        partLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadType = "2";
                partLoad.setTextColor(Color.parseColor("#ffffff"));
                partLoad.setBackgroundResource(R.drawable.black_back_round);
                fullLoad.setTextColor(Color.parseColor("#000000"));
                fullLoad.setBackgroundResource(R.drawable.white_back_round);
            }
        });

        fullLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadType = "1";
                fullLoad.setTextColor(Color.parseColor("#ffffff"));
                fullLoad.setBackgroundResource(R.drawable.black_back_round);
                partLoad.setTextColor(Color.parseColor("#000000"));
                partLoad.setBackgroundResource(R.drawable.white_back_round);
            }
        });

        openTruckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOpenTruckType();
//                openTruckBtn.setBackgroundColor(Color.parseColor("#FF1001"));
//                containerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                trailerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        containerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContainerType();
//                openTruckBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                containerBtn.setBackgroundColor(Color.parseColor("#FF1001"));
//                trailerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        trailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTrailerType();
//                openTruckBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                containerBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                trailerBtn.setBackgroundColor(Color.parseColor("#FF1001"));
            }
        });

        schedulePickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker;
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String dateFormat = (String) setDateFormat(dayOfMonth, (monthOfYear + 1), year);
                                schedulePickupDate.setText(dateFormat);
                                pickUpDate = schedulePickupDate.getText().toString();
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //Toast.makeText(getContext(), ""+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000 + (1000 * 60 * 60 * 24 * 4));
                picker.show();
            }
        });

        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (srcAddress.length() > 0)
                {
                    if (destAddress.length() > 0)
                    {
                        if (tid.length() > 0)
                        {
                            if (pickUpDate.length() > 0)
                            {


                                Intent intent = new Intent(getContext(), MaterialActivity.class);
                                intent.putExtra("source" , srcAddress);
                                intent.putExtra("destination" , destAddress);
                                intent.putExtra("tid" , tid);
                                intent.putExtra("date" , pickUpDate);
                                startActivity(intent);


                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Please select a pickup date", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Please select truck type", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please enter destination", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Please enter source", Toast.LENGTH_SHORT).show();
                }

                /*getPrice = new GetPrice();
                getPrice.currentMobile = EnterNumberActivity.mCurrentMobileNumber;
                getPrice.sourceAddress = srcAddress;
                getPrice.destinationAddress = destAddress;
                getPrice.truckType = truckType;
//                new Post().getPrice(getActivity(), getPrice);
                getMaterialType();
                Toast.makeText(getContext(), Post.price, Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getContext(), ShipmentActivity.class);
//                startActivity(intent);*/
            }
        });



        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null &&
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        final int[] h = {0};
        final int[] w = { 0 };

        bottom.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottom.invalidate();

                h[0] = bottom.getHeight();
                w[0] = bottom.getWidth();

                System.out.println("Height yourView: " + bottom.getHeight());
                System.out.println("Width yourView: " + bottom.getWidth());
            }
        }, 1);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setMinZoomPreference(15);


                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mGoogleMap.setPadding(0 , 0 , 0 , h[0] + 22);
                    }
                });


            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setMinZoomPreference(15);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mGoogleMap.setPadding(0 , 0 , 0 , h[0] + 22);
                }
            });
        }
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(20000);
        mLocationRequest.setSmallestDisplacement(5.0f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        /*Toast.makeText(getContext(), "Location Changed " + location.getLatitude()
                + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();*/

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        com.google.android.gms.maps.model.LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText((AppCompatActivity) getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((AppCompatActivity) getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions((AppCompatActivity) getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((AppCompatActivity) getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    private void getLocation() {

        //this method is used to get the places by places autocomplete
        Places.initialize(getActivity(), "AIzaSyDD5e-SJP_E8SDLOHYz79IR79pVy6YQOgg");

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("ind")
                .setTypeFilter(TypeFilter.CITIES)
                .build(getActivity());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (addressTyp == 1) {
                    srcAddress = place.getName();
                    sourceAddress.setText(place.getName());
                } else if (addressTyp == 2) {
                    destAddress = place.getName();
                    destinationAddress.setText(place.getName());
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getActivity(), status.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void getOpenTruckType() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.truck_type_dialog);
        dialog.show();

        TextView title = dialog.findViewById(R.id.textView10);
        final RecyclerView grid = dialog.findViewById(R.id.recyclerView);
        final ProgressBar progress = dialog.findViewById(R.id.progressBar2);

        title.setText("Open Truck Size");


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getTrucks("open truck");

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                TruckAdapter adapter = new TruckAdapter(getContext() , response.body() , "open truck" , dialog);
                GridLayoutManager manager = new GridLayoutManager(getContext() , 3);

                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        /*OpenTruckType openTruckType = new OpenTruckType();
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog", true);
        openTruckType.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        openTruckType.show(ft, "dialog");
        Toast.makeText(getActivity(), truckType, Toast.LENGTH_LONG).show();*/
    }

    private void getContainerType() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.truck_type_dialog);
        dialog.show();

        TextView title = dialog.findViewById(R.id.textView10);
        final RecyclerView grid = dialog.findViewById(R.id.recyclerView);
        final ProgressBar progress = dialog.findViewById(R.id.progressBar2);

        title.setText("Container Size");


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getTrucks("container");

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                TruckAdapter adapter = new TruckAdapter(getContext() , response.body() , "container" , dialog);
                GridLayoutManager manager = new GridLayoutManager(getContext() , 3);

                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void getTrailerType() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.truck_type_dialog);
        dialog.show();

        TextView title = dialog.findViewById(R.id.textView10);
        final RecyclerView grid = dialog.findViewById(R.id.recyclerView);
        final ProgressBar progress = dialog.findViewById(R.id.progressBar2);

        title.setText("Trailer Size");


        progress.setVisibility(View.VISIBLE);

        AppController b = (AppController) getContext().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<List<truckTypeBean>> call = cr.getTrucks("trailer");

        call.enqueue(new Callback<List<truckTypeBean>>() {
            @Override
            public void onResponse(Call<List<truckTypeBean>> call, Response<List<truckTypeBean>> response) {

                TruckAdapter adapter = new TruckAdapter(getContext() , response.body() , "trailer" , dialog);
                GridLayoutManager manager = new GridLayoutManager(getContext() , 3);

                grid.setAdapter(adapter);
                grid.setLayoutManager(manager);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<truckTypeBean>> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void getMaterialType() {
//        MaterialType materialType = new MaterialType();
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("notAlertDialog", true);
//        materialType.setArguments(bundle);
//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);
//        materialType.show(ft, "dialog");
        Intent intent = new Intent(getContext(), MaterialActivity.class);
        startActivity(intent);

        Toast.makeText(getContext(), "Move to step 2...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFinishEditDialog(String truckType) {
//        if (TextUtils.isEmpty(truckType)) {
////            textView.setText("Email was not entered");
//        } else {
//            truckType = truckType;
////            matTypeTv.setText(matTy);
//        }
////            materialType.setText("Email entered: " + inputText);
//        Toast.makeText(getActivity(), truckType, Toast.LENGTH_LONG).show();
    }

    private String setDateFormat(int day, int month, int year) {
        String finalDate = "";

        if (month == 1) {
            finalDate = day + " January " + year;
        } else if (month == 2) {
            finalDate = day + " February " + year;
        } else if (month == 3) {
            finalDate = day + " March " + year;
        } else if (month == 4) {
            finalDate = day + " April " + year;
        } else if (month == 5) {
            finalDate = day + " May " + year;
        } else if (month == 6) {
            finalDate = day + " June " + year;
        } else if (month == 7) {
            finalDate = day + " July " + year;
        } else if (month == 8) {
            finalDate = day + " August " + year;
        } else if (month == 9) {
            finalDate = day + " September" + year;
        } else if (month == 10) {
            finalDate = day + " October " + year;
        } else if (month == 11) {
            finalDate = day + " November " + year;
        } else if (month == 12) {
            finalDate = day + " December " + year;
        }
        return finalDate;
    }

    class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.ViewHolder>
    {
        Context context;
        List<truckTypeBean> list = new ArrayList<>();
        String type;
        Dialog dialog;

        TruckAdapter(Context context, List<truckTypeBean> list, String type , Dialog dialog)
        {
            this.context = context;
            this.list = list;
            this.type = type;
            this.dialog = dialog;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.truck_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final truckTypeBean item = list.get(position);

            if (tid.equals(item.getId()))
            {
                holder.card.setCardBackgroundColor(Color.parseColor("#F5DEDE"));
            }
            else
            {
                holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            if (type.equals("open truck"))
            {
                holder.image.setImageResource(R.drawable.ic_truck);
            }
            else if (type.equals("container"))
            {
                holder.image.setImageResource(R.drawable.ic_container);
            }
            else
            {
                holder.image.setImageResource(R.drawable.ic_trailer);
            }

            holder.text.setText(item.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checktruckType(item.getId() , item.getType());
                    dialog.dismiss();

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            ImageView image;
            TextView text;
            CardView card;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);
                text = itemView.findViewById(R.id.text);
                card = itemView.findViewById(R.id.card);

            }
        }
    }

    private void checktruckType(String id, String type)
    {
        this.tid = id;

        if (type.equals("open truck"))
        {
            openTruckBtn.setBackgroundResource(R.drawable.red_back_round);
            containerBtn.setBackgroundResource(R.drawable.white_back_round);
            trailerBtn.setBackgroundResource(R.drawable.white_back_round);
        }
        else if (type.equals("container"))
        {
            openTruckBtn.setBackgroundResource(R.drawable.white_back_round);
            containerBtn.setBackgroundResource(R.drawable.red_back_round);
            trailerBtn.setBackgroundResource(R.drawable.white_back_round);
        }
        else
        {
            openTruckBtn.setBackgroundResource(R.drawable.white_back_round);
            containerBtn.setBackgroundResource(R.drawable.white_back_round);
            trailerBtn.setBackgroundResource(R.drawable.red_back_round);
        }
    }

}
