package com.example.barcastations;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataRepository {

    private ArrayList<Station> stations = new ArrayList<>();
    private MutableLiveData<List<Station>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public RemoteDataRepository(Application application) {
        this.application = application;
        Log.d("RemoteDataRepository","Contructor");
    }

    public MutableLiveData<List<Station>> getMutableLiveData() {
        Log.d("RemoteDataRepository","MutableLiveData");
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<RemoteData> call = apiService.getData(41.3985182,2.1917991);
        call.enqueue(new Callback<RemoteData>() {
            @Override
            public void onResponse(Call<RemoteData> call, Response<RemoteData> response) {
                Log.d("onResponse","avant");
               // Log.d("errorBody","avant"+response.errorBody().string());

                RemoteData remoteData = response.body();
                Log.d("onResponse","apres");
                if (remoteData != null && remoteData.getTransport().getNearstations() != null) {
                    stations = (ArrayList<Station>) remoteData.getTransport().getNearstations();
                    Log.d("onResponse","stations "+stations.size());
                    mutableLiveData.setValue(stations);
                }
                else{
                    mutableLiveData.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<RemoteData> call, Throwable t) {
                if(t instanceof IOException){
                    mutableLiveData.setValue(null);
                    Log.d("onFailure","network");
                }else{
                    mutableLiveData.setValue(null);
                    Log.d("onFailure","conversion issue");

                }
            }
        });
        return mutableLiveData;
    }

}
