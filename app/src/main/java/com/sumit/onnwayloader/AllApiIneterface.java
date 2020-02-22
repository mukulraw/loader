package com.sumit.onnwayloader;

import com.sumit.onnwayloader.farePOJO.fareBean;
import com.sumit.onnwayloader.truckTypePOJO.truckTypeBean;

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

}
