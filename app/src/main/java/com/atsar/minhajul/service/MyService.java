package com.atsar.minhajul.service;


import com.atsar.minhajul.model.ListNotif;
import com.atsar.minhajul.model.ListRadio;
import com.atsar.minhajul.model.ModelFcm;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MyService {

    @GET("radio")
    Call<ListRadio> getRadio();

    @GET("notif")
    Call<ListNotif> getNotif();

    @FormUrlEncoded
    @POST("Fcm")
    @Headers({
            "Accept: application/vnd.yourapi.v1.full+json",
            "User-Agent: Android = Fcm"
    })
    Call<ModelFcm> getPostFcm(@Field("idfcm") String idfcm);

}
