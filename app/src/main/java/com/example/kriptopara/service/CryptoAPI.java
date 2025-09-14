package com.example.kriptopara.service;

import com.example.kriptopara.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //GET,POST,UPDATE,DELETE

    //URL BASE -> www.sdkfkwe.com
    //GET -> price?key=xxx

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

   @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //Call<List<CryptoModel>> getData();
    //Gözlemlenebilir obje oluşturuyoruz rxjava ile
    Observable<List<CryptoModel>> getData();
}
