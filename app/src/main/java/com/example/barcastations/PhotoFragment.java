package com.example.barcastations;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


/**

 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab ;

    private SharedViewModel sharedViewModel;
    private ListViewModel listViewModel;
    private Station bus_station;
    private RecyclerView.LayoutManager layoutManager;
    View v;



    public PhotoFragment() {
        // Required empty public constructor
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Picture item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_photo, container, false);

        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        coordinatorLayout = (CoordinatorLayout)v.findViewById(R.id.coordinatorLayout);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getStation().observe(getViewLifecycleOwner(), new Observer<Station>() {
            @Override
            public void onChanged(Station station) {
                bus_station = station;




                if(bus_station!= null){
                    Log.d("photo",""+bus_station.getStreet_name());
                    listViewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
                    listViewModel.getPicturesById(bus_station.getId()).observe(getViewLifecycleOwner(), new Observer<List<Picture>>() {
                        @Override
                        public void onChanged(List<Picture> pictures) {

                            Log.d("getPicturesById","getPicturesById");
                            // use a linear layout manager


                            if(pictures!=null){
                                // use this setting to improve performance if you know that changes
                                // in content do not change the layout size of the RecyclerView


                                Log.d("getPicturesById","inside"+pictures.size());
                                // mAdapter = new RecyclerViewAdapter(getActivity(),pictures);
                                mAdapter.setData(pictures);
                               // recyclerView.setAdapter(mAdapter);

                                enableSwipeToDeleteAndUndo();
                            }

                            //

                        }
                    });
                }
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }
}
