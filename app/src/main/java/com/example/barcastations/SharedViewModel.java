package com.example.barcastations;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Station> station = new MutableLiveData<Station>();
    private static SharedViewModel sharedViewModel;

    public static synchronized SharedViewModel getInstance() {
        if (sharedViewModel == null) {
            sharedViewModel = new SharedViewModel();
            return sharedViewModel;
        }

        return sharedViewModel;
    }

    public void select(Station item) {
        Log.d("select","select "+item.getId());
        station.setValue(item);
    }

    public LiveData<Station> getStation() {
        return station;
    }
}
