package com.onnway.kedsons.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onnway.kedsons.MainActivity;
import com.onnway.kedsons.addprofiledetails.FirstTimeProfileActivity;
import com.onnway.kedsons.addprofiledetails.UserData;
import com.onnway.kedsons.getpricenetworking.GetPrice;
import com.onnway.kedsons.myorder.ongoingorder.OngoingOrderDetails;
import com.onnway.kedsons.myorder.ongoingorder.OngoingOrderList;
import com.onnway.kedsons.myorder.pastorder.PastOrderDetails;
import com.onnway.kedsons.myorder.pastorder.PastOrderList;
import com.onnway.kedsons.myquotes.MyQuoteDetails;
import com.onnway.kedsons.myquotes.MyQuoteList;
import com.onnway.kedsons.myquotes.RecyclerAdapter;
import com.onnway.kedsons.otp.CheckingPreRegistered;
import com.onnway.kedsons.otp.EnterNumberActivity;
import com.onnway.kedsons.shipmentdetails.ShipmentActivity;
import com.onnway.kedsons.shipmentdetails.ShipmentDetails;
import com.onnway.kedsons.waitingrecycler.WaitingTruckDetails;
import com.onnway.kedsons.waitingrecycler.WaitingTruckList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {

    public String arrMyquoteId[], arrMyquoteDate[],
            arrMyquoteSourceAddress[], arrMyquoteDestinationAddress[],
            arrMyquoteMaterialType[], arrMyquoteMaterialWeight[], arrMyquotePayableFreight[];

    public String arrLoadType[], arrLoadStatus[],
            arrOrderId[], arrOrderDate[], arrSourceAddr[],
            arrDestinationAddr[], arrMatType[], arrMatWeight[];

    public String arrupComingOrderId[], arrupComingOrderDate[],
            arrupComingSourceAddress[], arrupComingDestinationAddress[],
            arrupComingMaterialType[], arrupComingMaterialWeight[],
            arrupComingPayableFreight[], arrupComingAdvanceFreight[];

    public String arrPastOrderId[], arrPastOrderDate[],
            arrPastSourceAddress[], arrPastDestinationAddress[],
            arrPastMaterialType[], arrPastMaterialWeight[],
            arrPastPayableFreight[];

    //user data
    public static String userName = "";
    public static String cityName = "";
    public static String userType = "";
    public static String userAddress = "";
    public static String mobileNumber = "";
    public static String userEmail = "";

    public static String price = "";

    public void doPost(final Context context, String mPhoneNo) {

        //method to get the otp from the server
        String url = "https://www.onnway.com/android/otpverification.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", mPhoneNo);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void getIfUserRegistered(final Context context, CheckingPreRegistered checkingPreRegistered) {

        //method to check if the user is already registered or not
        String url = "https://www.onnway.com/android/loader_check_login.php";
        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", EnterNumberActivity.mCurrentMobileNumber);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray userDetails = response.getJSONArray("users");


                    for (int i = 0; i < 1; i++) {

                        JSONObject details = userDetails.getJSONObject(i);
                        userName = details.getString("name");
                        cityName = details.getString("city");
                        userType = details.getString("type");
                        userAddress = details.getString("address");
                        mobileNumber = details.getString("mobile_no");
                        userEmail = details.getString("user_email");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void registerUser(final Context context, UserData userData) {

        //method to register the user if not registered

        String url = "https://www.onnway.com/android/loader_profile.php";
        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", userData.mobileNumber);
        params.put("user_name", userData.userName);
        params.put("user_email", userData.userEmail);
        params.put("user_address", userData.userAddress);
        params.put("user_city", userData.userCity);
        params.put("user_type", userData.userType);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                FirstTimeProfileActivity.progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
//                FirstTimeProfileActivity.progressDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void updateUser(final Context context, UserData userData) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Finding Price");
        progressDialog.setMessage("Please wait, while we find price for you");
        progressDialog.show();
        progressDialog.setCancelable(false);
        //method to register the user if not registered
        String url = "https://www.onnway.com/android/updateloaderprofile.php";
        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", "7049458212");//
        params.put("user_name", "Sumit");//
        params.put("user_email", "mishrasumit.3452@gmail.com");//
        params.put("user_address", "Minal");//
        params.put("user_city", "Bhopal");//
        params.put("user_type", "1");//
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
//                FirstTimeProfileActivity.progressDialog.dismiss();/
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
//                FirstTimeProfileActivity.progressDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void getPrice(final Context context, GetPrice priceget) {
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setTitle("Finding Price");
//        progressDialog.setMessage("Please wait, while we find price for you");
//        progressDialog.show();
//        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/loadercheckprice.php";
        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", "7049458212");
        params.put("source", priceget.sourceAddress);
        params.put("destination", priceget.destinationAddress);
        params.put("truck_type", priceget.truckType);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray userDetails = response.getJSONArray("users");
                    for (int i = 0; i < 1; i++) {
                        JSONObject details = userDetails.getJSONObject(i);
                        price = details.getString("price");
                    }
//                    progressDialog.dismiss();
                    if(price.equals("")) {

                    } else {
                        Intent intent = new Intent(context, ShipmentActivity.class);
                        context.startActivity(intent);
                    }

//                    Toast.makeText(Pos, price, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void postOrder(final Context context, ShipmentDetails shipmentDetails) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Confirming Loading");
        progressDialog.setMessage("Please wait, while we confirm your load");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = "https://www.onnway.com/android/loaderinsertdetails.php";
        Map<String, String> params = new HashMap<>();
        params.put("mobile_no", shipmentDetails.mobileNumber);
//        params.put("source", shipmentDetails.loadType);
        params.put("source", shipmentDetails.sourceAddress);
        params.put("destination", shipmentDetails.destinationAddress);
        params.put("truck_type", shipmentDetails.truckType);
        params.put("sch_date", shipmentDetails.scheduleDate);
        params.put("weight", shipmentDetails.loadingWeight);
        params.put("material_type", shipmentDetails.materialType);
        params.put("pickup_street", shipmentDetails.pickupStreet);
        params.put("pickup_pincode", shipmentDetails.pickupPincode);
        params.put("drop_street", shipmentDetails.dropStreet);
        params.put("drop_pincode", shipmentDetails.dropPincode);
        params.put("price", "0");
        params.put("quote_id", shipmentDetails.quoteId);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
//                FirstTimeProfileActivity.progressDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void myQuotes(final Context context, final RecyclerView recyclerView) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading Quotes");
        progressDialog.setMessage("Please wait, while we load your quotes");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "https://www.onnway.com/android/loaderquotes.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", "7049458212");

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {
                    int count = 0;
                    JSONArray jsonArray = response.getJSONArray("users");
                    int len = jsonArray.length();
                    arrMyquoteId = new String[len];
                    arrMyquoteDate = new String[len];
                    arrMyquoteSourceAddress = new String[len];
                    arrMyquoteDestinationAddress = new String[len];
                    arrMyquoteMaterialType = new String[len];
                    arrMyquoteMaterialWeight = new String[len];
                    arrMyquotePayableFreight = new String[len];

                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrMyquoteId[count] = jsonObject.getString("quoteId");
                        arrMyquoteDate[count] = jsonObject.getString("quoteDate");
                        arrMyquoteSourceAddress[count] = jsonObject.getString("sourceAddress");
                        arrMyquoteDestinationAddress[count] = jsonObject.getString("destinationAddress");
                        arrMyquoteMaterialType[count] = jsonObject.getString("materialType");
                        arrMyquoteMaterialWeight[count] = jsonObject.getString("materialWeight");
                        arrMyquotePayableFreight[count] = jsonObject.getString("payableFreight");
                        count++;
                    }

                    MyQuoteList.myQuoteLists = new ArrayList<>();
                    for (int i = 0; i < arrMyquoteId.length; i++) {
                        MyQuoteDetails myQuoteDetails = new MyQuoteDetails();
                        myQuoteDetails.quoteId = arrMyquoteId[i];
                        myQuoteDetails.quoteDate = arrMyquoteDate[i];
                        myQuoteDetails.sourceAddress = arrMyquoteSourceAddress[i];
                        myQuoteDetails.destinationAddress = arrMyquoteDestinationAddress[i];
                        myQuoteDetails.materialType = arrMyquoteMaterialType[i];
                        myQuoteDetails.materialWeight = arrMyquoteMaterialWeight[i];
                        myQuoteDetails.payableFreight = arrMyquotePayableFreight[i];

                        MyQuoteList.myQuoteLists.add(myQuoteDetails);
                    }
                    progressDialog.dismiss();
                    RecyclerAdapter upcomingRecyclerAdapter = new RecyclerAdapter(MyQuoteList.myQuoteLists);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(upcomingRecyclerAdapter);

                } catch (Exception ex) {
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    public void myWaitingOrder(final Context context, final RecyclerView recyclerView) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Hold on! We are getting your waiting orders");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "https://www.onnway.com/android/loaderwaiting.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", "7049458212");

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {
                    int count = 0;
                    JSONArray jsonArray = response.getJSONArray("users");
                    int len = jsonArray.length();
                    arrLoadType = new String[len];
                    arrLoadStatus = new String[len];
                    arrOrderId = new String[len];
                    arrOrderDate = new String[len];
                    arrSourceAddr = new String[len];
                    arrDestinationAddr = new String[len];
                    arrMatType = new String[len];
                    arrMatWeight = new String[len];

                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrOrderId[count] = jsonObject.getString("orderId");
                        arrLoadType[count] = jsonObject.getString("loadType");
                        arrLoadStatus[count] = jsonObject.getString("loadStatus");
                        arrOrderDate[count] = jsonObject.getString("orderDate");
                        arrSourceAddr[count] = jsonObject.getString("sourceAddr");
                        arrDestinationAddr[count] = jsonObject.getString("destinationAddr");
                        arrMatType[count] = jsonObject.getString("materialType");
                        arrMatWeight[count] = jsonObject.getString("materialWeight");
                        count++;
                    }

                    WaitingTruckList.waitingTruckDetailsList = new ArrayList<>();
                    for (int i = 0; i < arrLoadType.length; i++) {
                        WaitingTruckDetails waitingTruckDetails = new WaitingTruckDetails();
                        waitingTruckDetails.loadType = arrLoadType[i];
                        waitingTruckDetails.loadStatus = arrLoadStatus[i];
                        waitingTruckDetails.orderId = arrOrderId[i];
                        waitingTruckDetails.orderDate = arrOrderDate[i];
                        waitingTruckDetails.sourceAddr = arrSourceAddr[i];
                        waitingTruckDetails.destinationAddr = arrDestinationAddr[i];
                        waitingTruckDetails.materialType = arrMatType[i];
                        waitingTruckDetails.materialWeight = arrMatWeight[i];

                        WaitingTruckList.waitingTruckDetailsList.add(waitingTruckDetails);
                    }
                    progressDialog.dismiss();
                    com.onnway.kedsons.waitingrecycler.RecyclerAdapter waitingRecyclerAdapter = new com.onnway.kedsons.waitingrecycler.RecyclerAdapter(WaitingTruckList.waitingTruckDetailsList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(waitingRecyclerAdapter);

                } catch (Exception ex) {
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    //upcoming order
    public void doUpcoming(final Context context,final RecyclerView recyclerView) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Hold on! We are getting your waiting orders");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "https://www.onnway.com/android/loaderongoing.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", "7049458212");

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray=response.getJSONArray("users");
                    int len=jsonArray.length();

                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrupComingOrderId = new String[jsonArray.length()];
                    arrupComingOrderDate = new String[jsonArray.length()];
                    arrupComingSourceAddress = new String[jsonArray.length()];
                    arrupComingDestinationAddress = new String[jsonArray.length()];
                    arrupComingMaterialType = new String[jsonArray.length()];
                    arrupComingMaterialWeight = new String[jsonArray.length()];
                    arrupComingPayableFreight = new String[jsonArray.length()];
                    arrupComingAdvanceFreight = new String[jsonArray.length()];

                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrupComingOrderId[count] = jsonObject.getString("orderId");//
                        arrupComingOrderDate[count] = jsonObject.getString("orderDate");//
                        arrupComingSourceAddress[count] = jsonObject.getString("sourceAddr");//
                        arrupComingDestinationAddress[count] = jsonObject.getString("destinationAddr");//
                        arrupComingMaterialType[count] = jsonObject.getString("materialType");//
                        arrupComingMaterialWeight[count] = jsonObject.getString("materialWeight");//
                        arrupComingPayableFreight[count] = jsonObject.getString("payableFreight");//
                        arrupComingAdvanceFreight[count] = jsonObject.getString("advanceFreight");//
                        count++;
                    }

                    OngoingOrderList.ongoingOrderLists=new ArrayList<>();
                    for(int i = 0; i < arrupComingAdvanceFreight.length; ++i) {
                        OngoingOrderDetails ongoingOrderDetails = new OngoingOrderDetails();
                        ongoingOrderDetails.orderId = arrupComingOrderId[i];
                        ongoingOrderDetails.orderDate = arrupComingOrderDate[i];
                        ongoingOrderDetails.sourceAddr = arrupComingSourceAddress[i];
                        ongoingOrderDetails.destinationAddr = arrupComingDestinationAddress[i];
                        ongoingOrderDetails.materialType = arrupComingMaterialType[i];
                        ongoingOrderDetails.materialWeight = arrupComingMaterialWeight[i];
                        ongoingOrderDetails.payableFreight = arrupComingPayableFreight[i];
                        ongoingOrderDetails.advanceFreight = arrupComingAdvanceFreight[i];
                        OngoingOrderList.ongoingOrderLists.add(ongoingOrderDetails);
                    }
                    progressDialog.dismiss();
                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    com.onnway.kedsons.myorder.ongoingorder.RecyclerAdapter recyclerAdapter =new com.onnway.kedsons.myorder.ongoingorder.RecyclerAdapter(OngoingOrderList.ongoingOrderLists);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(recyclerAdapter);

                }
                catch (Exception ex) {

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    //past order
    public void pastOrder(final Context context,final RecyclerView recyclerView) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Hold on! We are getting your waiting orders");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = "https://www.onnway.com/android/loaderpast.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile_no", "7049458212");

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());

                try {

                    int count = 0;
                    JSONArray jsonArray=response.getJSONArray("users");
                    int len=jsonArray.length();

                    //Toast.makeText(context, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                    arrPastOrderId = new String[jsonArray.length()];
                    arrPastOrderDate = new String[jsonArray.length()];
                    arrPastSourceAddress = new String[jsonArray.length()];
                    arrPastDestinationAddress = new String[jsonArray.length()];
                    arrPastMaterialType = new String[jsonArray.length()];
                    arrPastMaterialWeight = new String[jsonArray.length()];
                    arrPastPayableFreight = new String[jsonArray.length()];

                    while (count < jsonArray.length()) {
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        arrPastOrderId[count] = jsonObject.getString("orderId");
                        arrPastOrderDate[count] = jsonObject.getString("orderDate");
                        arrPastSourceAddress[count] = jsonObject.getString("sourceAddr");
                        arrPastDestinationAddress[count] = jsonObject.getString("destinationAddr");
                        arrPastMaterialType[count] = jsonObject.getString("materialType");
                        arrPastMaterialWeight[count] = jsonObject.getString("materialWeight");
                        arrPastPayableFreight[count] = jsonObject.getString("payableFreight");
                        count++;
                    }

                    PastOrderList.pastOrderList=new ArrayList<>();
                    for(int i = 0; i < arrPastPayableFreight.length; ++i) {
                        PastOrderDetails pastOrderDetails = new PastOrderDetails();
                        pastOrderDetails.orderId = arrPastOrderId[i];
                        pastOrderDetails.orderDate = arrPastOrderDate[i];
                        pastOrderDetails.sourceAddr = arrPastSourceAddress[i];
                        pastOrderDetails.destinationAddr = arrPastDestinationAddress[i];
                        pastOrderDetails.materialType = arrPastMaterialType[i];
                        pastOrderDetails.materialWeight = arrPastMaterialWeight[i];
                        pastOrderDetails.payableFreight = arrPastPayableFreight[i];

                        PastOrderList.pastOrderList.add(pastOrderDetails);
                    }
                    progressDialog.dismiss();
                    // Toast.makeText(context, ""+arrpostedTruckDate[0]+arrpostedTruckDate[1], Toast.LENGTH_SHORT).show();
                    com.onnway.kedsons.myorder.pastorder.RecyclerAdapter recyclerAdapter =new com.onnway.kedsons.myorder.pastorder.RecyclerAdapter(PastOrderList.pastOrderList);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(recyclerAdapter);

                }
                catch (Exception ex) {

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}



