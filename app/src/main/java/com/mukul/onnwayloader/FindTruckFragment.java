package com.mukul.onnwayloader;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
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
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.mukul.onnwayloader.getpricenetworking.GetPrice;
import com.mukul.onnwayloader.materialtype.MaterialActivity;
import com.mukul.onnwayloader.networking.AppController;
import com.mukul.onnwayloader.truckTypePOJO.truckTypeBean;
import com.mukul.onnwayloader.vehicletype.OpenTruckType;
import com.shivtechs.maplocationpicker.MapUtility;

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
    private static final String TAG = "FindTruckFragment";

    /*
     * This fragment is used to load the findtruckfragment
     * Here, the loader can find the truck by entering the source and destination address
     * */


    //variables for the button, textview and card

    //CardView
    private ImageButton nextCard;

    Button partLoad, fullLoad;


    LinearLayout bottom, truck;
    //ImageView


    //TextView
    private TextView sourceAddress, destinationAddress, schedulePickupDate, sel_truck;

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

    private String loadType = "1", srcAddress = "", destAddress = "", pickUpDate = "";

    private int addressTyp = 0;

    private GetPrice getPrice;

    public FindTruckFragment() {
        // Required empty public constructor
    }

    String tid = "";
    String max = "";

    private FusedLocationProviderClient fusedLocationClient;

    double sourceLAT = 0, sourceLNG = 0, destinationLAT = 0, destinationLNG = 0;

    LocationSettingsRequest.Builder builder;
    LocationRequest locationRequest;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_truck, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        Places.initialize(getContext(), getString(R.string.google_maps_key));

        MapUtility.apiKey = getResources().getString(R.string.google_maps_key);

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
        truck = view.findViewById(R.id.truck);
        sel_truck = view.findViewById(R.id.sel_truck);


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

                truck.setVisibility(View.INVISIBLE);

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
                truck.setVisibility(View.VISIBLE);
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
                                String dateFormat = setDateFormat(dayOfMonth, (monthOfYear + 1), year);
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

                if (srcAddress.length() > 0) {
                    if (destAddress.length() > 0) {

                        if (loadType.equals("1")) {
                            if (tid.length() > 0) {
                                if (pickUpDate.length() > 0) {


                                    Intent intent = new Intent(getContext(), MaterialActivity.class);
                                    intent.putExtra("source", srcAddress);
                                    intent.putExtra("destination", destAddress);
                                    intent.putExtra("tid", tid);
                                    intent.putExtra("max", max);
                                    intent.putExtra("loadtype", loadType);
                                    intent.putExtra("date", pickUpDate);
                                    startActivity(intent);


                                } else {
                                    Toast.makeText(getActivity(), "Please select a pickup date", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Please select truck type", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (pickUpDate.length() > 0) {


                                Intent intent = new Intent(getContext(), MaterialActivity2.class);
                                intent.putExtra("source", srcAddress);
                                intent.putExtra("destination", destAddress);
                                intent.putExtra("loadtype", loadType);
                                intent.putExtra("date", pickUpDate);
                                startActivity(intent);


                            } else {
                                Toast.makeText(getActivity(), "Please select a pickup date", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } else {
                        Toast.makeText(getActivity(), "Please enter destination", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter source", Toast.LENGTH_SHORT).show();
                }

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
        final int[] w = {0};

        bottom.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottom.invalidate();

                h[0] = bottom.getHeight();
                w[0] = bottom.getWidth();

                System.out.println("Height yourView: " + bottom.getHeight());
                System.out.println("Width yourView: " + bottom.getWidth());

                mGoogleMap.setPadding(0, 0, 0, bottom.getHeight());

            }
        }, 1);

        createLocationRequest();

        //Initialize Google Play Services
        /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        }*/
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


    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
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
                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


    private void getLocation() {

        //this method is used to get the places by places autocomplete


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

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.i(TAG, "User agreed to make required location settings changes.");
                    getLocation2();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getActivity(), "Location is required for this app", Toast.LENGTH_LONG).show();
                    getActivity().finishAffinity();
                    break;
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

                TruckAdapter adapter = new TruckAdapter(getContext(), response.body(), "open truck", dialog);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 3);

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

                TruckAdapter adapter = new TruckAdapter(getContext(), response.body(), "container", dialog);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 3);

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

                TruckAdapter adapter = new TruckAdapter(getContext(), response.body(), "trailer", dialog);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 3);

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


    @Override
    public void onFinishEditDialog(String truckType) {
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

    class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.ViewHolder> {
        Context context;
        List<truckTypeBean> list = new ArrayList<>();
        String type;
        Dialog dialog;

        TruckAdapter(Context context, List<truckTypeBean> list, String type, Dialog dialog) {
            this.context = context;
            this.list = list;
            this.type = type;
            this.dialog = dialog;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.truck_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final truckTypeBean item = list.get(position);

            if (tid.equals(item.getId())) {
                holder.card.setCardBackgroundColor(Color.parseColor("#F5DEDE"));
            } else {
                holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            if (type.equals("open truck")) {
                holder.image.setImageResource(R.drawable.ic_truck);
            } else if (type.equals("container")) {
                holder.image.setImageResource(R.drawable.ic_container);
            } else {
                holder.image.setImageResource(R.drawable.ic_trailer);
            }

            holder.text.setText(item.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checktruckType(item.getId(), item.getType(), item.getTitle(), item.getMax_load());
                    dialog.dismiss();

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

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

    private void checktruckType(String id, String type, String title, String max) {
        this.tid = id;
        this.max = max;


        sel_truck.setText(type + " - " + title);
        sel_truck.setVisibility(View.VISIBLE);


        if (type.equals("open truck")) {
            openTruckBtn.setBackgroundResource(R.drawable.red_back_round);
            containerBtn.setBackgroundResource(0);
            trailerBtn.setBackgroundResource(0);
        } else if (type.equals("container")) {
            openTruckBtn.setBackgroundResource(0);
            containerBtn.setBackgroundResource(R.drawable.red_back_round);
            trailerBtn.setBackgroundResource(0);
        } else {
            openTruckBtn.setBackgroundResource(0);
            containerBtn.setBackgroundResource(0);
            trailerBtn.setBackgroundResource(R.drawable.red_back_round);
        }
    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getLocation2();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(),
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

    void getLocation2() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        //TODO: UI updates.
                        sourceLAT = location.getLatitude();
                        sourceLNG = location.getLongitude();

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

                        LocationServices.getFusedLocationProviderClient(getActivity()).removeLocationUpdates(this);

                    }
                }
            }
        };

        LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(locationRequest, mLocationCallback, null);

    }

}
