package com.example.barcastations;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback
{

    private MapViewModel mViewModel;
    private SharedViewModel sharedViewModel;
    private View view;
    private GoogleMap map;
    private MapView mapView;
    private List<Station> listStations;
    private Map<Marker, Station> mMarkerMap = new HashMap<>();
    private RecyclerView.LayoutManager layoutManager;
    Station station;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);

     }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mViewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);

        mViewModel.getAllStations().observe(getViewLifecycleOwner(), new Observer<List<Station>>() {
            @Override
            public void onChanged(List<Station> stations) {


                listStations = stations;
                if(listStations != null){
                    if(mapView != null){
                        mapView.onCreate(null);
                        mapView.onResume();
                        mapView.getMapAsync(MapFragment.this::onMapReady);


                    }
                }


            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        MapsInitializer.initialize(getContext());
        map = googleMap;
        stationOnMap(map);

    }

    public void stationOnMap(GoogleMap map){

        for (Station station : listStations) {


            LatLng latLng = new LatLng(Double.parseDouble(station.getLat()),Double.parseDouble(station.getLon()));
            Marker marker =  map.addMarker(new MarkerOptions().position(latLng).title(station.getStreet_name()));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            mMarkerMap.put(marker,station);
        }

        LatLng NewYork= new LatLng(41.3985182,2.1917991);
        CameraPosition camPos = new CameraPosition.Builder().target(NewYork).zoom(14).build();
        CameraUpdate cam = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(cam);
        map.setOnMarkerClickListener(this);

        map.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        ShareFactory shareFactory = new ShareFactory(SharedViewModel.getInstance());
        sharedViewModel = new ViewModelProvider(requireActivity(),shareFactory).get(SharedViewModel.class);

        station = mMarkerMap.get(marker);



        sharedViewModel.select(station);


        Intent intent = new Intent(getActivity(),DetailsActivity.class);
        startActivity(intent);

           return false;

    }
}
