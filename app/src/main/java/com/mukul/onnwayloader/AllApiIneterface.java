package com.mukul.onnwayloader;

import com.mukul.onnwayloader.confirm_full_POJO.confirm_full_bean;
import com.mukul.onnwayloader.farePOJO.fareBean;
import com.mukul.onnwayloader.truckTypePOJO.truckTypeBean;

import java.util.List;

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

    @Multipart
    @POST("android/getFare.php")
    Call<fareBean> getFare(
            @Part("source") String source,
            @Part("destination") String destination,
            @Part("truck_id") String truck_id,
            @Part("mid") String mid,
            @Part("weight") String weight
    );

    @Multipart
    @POST("android/getFare.php")
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
            @Part("drop_address") String drop_address,
            @Part("drop_city") String drop_city,
            @Part("drop_pincode") String drop_pincode,
            @Part("drop_phone") String drop_phone,
            @Part("remarks") String remarks,
            @Part("length") String length,
            @Part("width") String width,
            @Part("height") String height,
            @Part("quantity") String quantity
    );

}
