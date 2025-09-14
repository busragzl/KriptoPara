package com.example.kriptopara.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kriptopara.R;
import com.example.kriptopara.adapter.RecyclerViewAdapter;
import com.example.kriptopara.model.CryptoModel;
import com.example.kriptopara.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    private String baseURL = "https://raw.githubusercontent.com/";

    Retrofit retrofit;

    RecyclerView recyclerView ;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);


        //Retrofit JSON

        Gson gson = new GsonBuilder().setLenient().create();


        retrofit =  new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();


        loadData();





    }

    private void loadData(){

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io()) // Hangi thread üzerinde yapılacak //Network
                .observeOn(AndroidSchedulers.mainThread())   //Alınan sonuçların gösterilmesi
                .subscribe(this::handlerResponse)); //Yapılan işlemleri nerde ele alıcaz





       /* Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){
                   List<CryptoModel> cryptoModelList =  response.body();
                   cryptoModels = new ArrayList<>(cryptoModelList);

                   recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                   recyclerViewAdapter =  new RecyclerViewAdapter(cryptoModels);
                   recyclerView.setAdapter(recyclerViewAdapter);


                   for(CryptoModel cryptoModel : cryptoModelList){
                       System.out.println(cryptoModel.currency);
                   }


                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                    t.printStackTrace();
            }
        });
        */

    }
    private void handlerResponse(List<CryptoModel> cryptoModelList){

        cryptoModels = new ArrayList<>(cryptoModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter =  new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}