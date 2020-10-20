package com.mukul.onnwayloader;

import com.mukul.onnwayloader.checkPromoPOJO.checkPromoBean;
import com.mukul.onnwayloader.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwayloader.farePOJO.fareBean;
import com.mukul.onnwayloader.orderHistoryPOJO.orderHistoryBean;
import com.mukul.onnwayloader.profilePOJO.profileBean;
import com.mukul.onnwayloader.truckTypePOJO.truckTypeBean;
import com.mukul.onnwayloader.updateProfilePOJO.updateProfileBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

;

public interface AllApiIneterface {

    @Multipart
    @POST("android/getTrucks.php")
    Call<List<truckTypeBean>> getTrucks(
            @Part("type") String type
    );

    @Multipart
    @POST("android/login.php")
    Call<loginBean> login(
            @Part("phone") String phone,
            @Part("token") String token
    );

    @GET("android/getMaterial.php")
    Call<List<truckTypeBean>> getMaterial();

    @GET("android/getWeight.php")
    Call<List<truckTypeBean>> getWeight();

    @GET("android/getSubject.php")
    Call<List<truckTypeBean>> getSubject();

    @Multipart
    @POST("android/getFare.php")
    Call<fareBean> getFare(
            @Part("user_id") String user_id,
            @Part("source") String source,
            @Part("destination") String destination,
            @Part("truck_id") String truck_id,
            @Part("mid") String mid,
            @Part("weight") String weight,
            @Part("schedule") String schedule,
            @Part("sourceLAT") String sourceLAT,
            @Part("sourceLNG") String sourceLNG,
            @Part("destinationLAT") String destinationLAT,
            @Part("destinationLNG") String destinationLNG
    );

    @Multipart
    @POST("android/confirm_full_load.php")
    Call<confirm_full_bean> confirm_full_load(
            @Part("user_id") String user_id,
            @Part("laod_type") String laod_type,
            @Part("source") String source,
            @Part("destination") String destination,
            @Part("truck_type") String truck_type,
            @Part("schedule") String schedule,
            @Part("weight") String weight,
            @Part("material") String material,
            @Part("freight") String freight,
            @Part("other_charges") String other_charges,
            @Part("cgst") String cgst,
            @Part("sgst") String sgst,
            @Part("insurance") String insurance,
            @Part("paid_percent") String paid_percent,
            @Part("paid_amount") String paid_amount,
            @Part("pickup_address") String pickup_address,
            @Part("pickup_city") String pickup_city,
            @Part("pickup_pincode") String pickup_pincode,
            @Part("pickup_phone") String pickup_phone,
            @Part("drop_address") String drop_address,
            @Part("drop_city") String drop_city,
            @Part("drop_pincode") String drop_pincode,
            @Part("drop_phone") String drop_phone,
            @Part("remarks") String remarks,
            @Part("length") String length,
            @Part("width") String width,
            @Part("height") String height,
            @Part("quantity") String quantity,
            @Part("pvalue") String pvalue,
            @Part("pid") String pid,
            @Part("sourceLAT") String sourceLAT,
            @Part("sourceLNG") String sourceLNG,
            @Part("destinationLAT") String destinationLAT,
            @Part("destinationLNG") String destinationLNG
    );

    @Multipart
    @POST("android/submitFeedback.php")
    Call<confirm_full_bean> submitFeedback(
            @Part("user_id") String user_id,
            @Part("subject") String subject,
            @Part("mesage") String mesage
    );

    @Multipart
    @POST("android/submitLoaderRating.php")
    Call<confirm_full_bean> submitLoaderRating(
            @Part("id") String id,
            @Part("loader_rating") String loader_rating
    );

    @Multipart
    @POST("android/checkLoaderRating.php")
    Call<confirm_full_bean> checkLoaderRating(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getLoaderCount.php")
    Call<loaderCountBean> getLoaderCount(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/quote_full_load.php")
    Call<confirm_full_bean> quote_full_load(
            @Part("user_id") String user_id,
            @Part("laod_type") String laod_type,
            @Part("source") String source,
            @Part("destination") String destination,
            @Part("truck_type") String truck_type,
            @Part("schedule") String schedule,
            @Part("weight") String weight,
            @Part("material") String material,
            @Part("freight") String freight,
            @Part("other_charges") String other_charges,
            @Part("cgst") String cgst,
            @Part("sgst") String sgst,
            @Part("insurance") String insurance,
            @Part("paid_percent") String paid_percent,
            @Part("paid_amount") String paid_amount,
            @Part("pickup_address") String pickup_address,
            @Part("pickup_city") String pickup_city,
            @Part("pickup_pincode") String pickup_pincode,
            @Part("pickup_phone") String pickup_phone,
            @Part("drop_address") String drop_address,
            @Part("drop_city") String drop_city,
            @Part("drop_pincode") String drop_pincode,
            @Part("drop_phone") String drop_phone,
            @Part("remarks") String remarks,
            @Part("length") String length,
            @Part("width") String width,
            @Part("height") String height,
            @Part("quantity") String quantity,
            @Part("sourceLAT") String sourceLAT,
            @Part("sourceLNG") String sourceLNG,
            @Part("destinationLAT") String destinationLAT,
            @Part("destinationLNG") String destinationLNG,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/update_loader_profile.php")
    Call<updateProfileBean> update_loader_profile(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("email") String email,
            @Part("city") String city,
            @Part("type") String type,
            @Part("company") String company,
            @Part("gst") String gst
    );

    @Multipart
    @POST("android/update_loader_profile2.php")
    Call<updateProfileBean> update_loader_profile2(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("email") String email,
            @Part("city") String city,
            @Part("type") String type,
            @Part("company") String company,
            @Part("gst") String gst
    );

    @Multipart
    @POST("android/getOngoingOrders.php")
    Call<orderHistoryBean> getOngoingOrders(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/pay/test.php")
    Call<String> test(
            @Part("id") String id,
            @Part("amount") String amount
    );

    @Multipart
    @POST("android/update_order.php")
    Call<updateProfileBean> update_order(
            @Part("id") String id,
            @Part("status") String status,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getQuotes.php")
    Call<orderHistoryBean> getQuotes(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getWaitingTrucks.php")
    Call<orderHistoryBean> getWaitingTrucks(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getPastOrders.php")
    Call<orderHistoryBean> getPastOrders(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/getOrderDetails.php")
    Call<confirm_full_bean> getOrderDetails(
            @Part("id") String id
    );

    @Multipart
    @POST("android/getOrderDetails2.php")
    Call<confirm_full_bean> getOrderDetails2(
            @Part("id") String id
    );

    @Multipart
    @POST("android/uploadInvoice.php")
    Call<confirm_full_bean> uploadDocuments(
            @Part("assign_id") String assign_id,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/uploadPOD.php")
    Call<confirm_full_bean> uploadPOD(
            @Part("assign_id") String assign_id,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/cancel_order_loader.php")
    Call<updateProfileBean> cancel_order_loader(
            @Part("id") String id
    );

    @Multipart
    @POST("android/getLoaderProfile.php")
    Call<profileBean> getLoaderProfile(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("android/updateImage.php")
    Call<confirm_full_bean> updateImage(
            @Part("user_id") String user_id,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/updateLoaderKyc.php")
    Call<confirm_full_bean> updateLoaderKyc(
            @Part("user_id") String user_id,
            @Part("type") String type,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/checkPromo.php")
    Call<checkPromoBean> checkPromo(
            @Part("promo") String promo,
            @Part("user_id") String user_id
    );


    @Multipart
    @POST("android/uploadReceipt.php")
    Call<confirm_full_bean> uploadReceipt(
            @Part("order_id") String order_id,
            @Part("user_id") String user_id,
            @Part("type") String type,
            @Part("pid") String pid,
            @Part("pvalue") String pvalue,
            @Part("insused") String insused,
            @Part("inin") String inin,
            @Part("isinsurance") String isinsurance,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("android/pay.php")
    Call<confirm_full_bean> pay(
            @Part("order_id") String order_id,
            @Part("user_id") String user_id,
            @Part("type") String type,
            @Part("pid") String pid,
            @Part("pvalue") String pvalue,
            @Part("insused") String insused,
            @Part("inin") String inin,
            @Part("isinsurance") String isinsurance,
            @Part("amount") String amount
    );

    @Multipart
    @POST("android/getLogs.php")
    Call<trackBean> getLogs(
            @Part("order_id") String order_id
    );

    @Multipart
    @POST("android/getCall.php")
    Call<confirm_full_bean> getCall(
            @Part("order_id") String order_id,
            @Part("user_id") String user_id
    );

}
