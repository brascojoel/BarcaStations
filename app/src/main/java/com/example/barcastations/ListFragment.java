package com.example.barcastations;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListFragmentAdapter mAdapter;


    private MapViewModel mViewModel;
    private ListViewModel listViewModel;
    private Station bus_station;
    private RecyclerView.LayoutManager layoutManager;
    View v;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.list_fragment, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) v.findViewById(R.id.list_recyclerView);



        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mViewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);

        mViewModel.getAllStations().observe(getViewLifecycleOwner(), new Observer<List<Station>>() {
            @Override
            public void onChanged(List<Station> stations) {



                if(stations!=null){

                recyclerView.setAdapter(new ListFragmentAdapter(stations,new ListFragmentAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Station item) {

                        ShareFactory shareFactory = new ShareFactory(SharedViewModel.getInstance());

                        SharedViewModel  sharedViewModel = new ViewModelProvider(requireActivity(),shareFactory).get(SharedViewModel.class);
                        sharedViewModel.select(item);
                        Intent intent = new Intent(getActivity(),DetailsActivity.class);
                        startActivity(intent);
                       /* PhotoFragment fragment =new PhotoFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.viewPager, fragment)
                                .commit(); */

                        Toast.makeText(getActivity(),"ListFragment"+item.getBuses(), Toast.LENGTH_LONG).show();
                    }
                }));

                }
                else {
                    Toast.makeText(getActivity(),"pas de connexion", Toast.LENGTH_LONG).show();
                }

                // Log.d("Onchanged","onchanged modelview taille"+stations.size());


            }
        });
    }




}
