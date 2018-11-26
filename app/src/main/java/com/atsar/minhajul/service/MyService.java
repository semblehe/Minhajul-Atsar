package com.atsar.minhajul.service;


import com.atsar.minhajul.model.ListRadio;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyService {

    @GET("radio")
    Call<ListRadio> getRadio();

}
