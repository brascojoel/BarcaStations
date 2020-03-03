package com.example.barcastations;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends AndroidViewModel {


   // private StationRepository stationRepository;
    private RemoteDataRepository remoteDataRepository;

    private MutableLiveData<List<Station>> listStations;

    public MapViewModel(Application application){
        super(application);


       // stationRepository = new StationRepository(application);
       // listStations = stationRepository.getAllStations();
        remoteDataRepository = new RemoteDataRepository(application);
        listStations = remoteDataRepository.getMutableLiveData();


    }

   /* public Station getStation(String id){
        return stationRepository.getStation(id);
    }*/
    public MutableLiveData<List<Station>> getAllStations(){
        return listStations;
    }
}
