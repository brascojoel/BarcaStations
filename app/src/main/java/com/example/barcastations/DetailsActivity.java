package com.example.barcastations;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private SharedViewModel sharedViewModel;
    private ListViewModel listViewModel;
    private Station bus_station;
    private RecyclerView.LayoutManager layoutManager;
    private PictureRepository pictureRepository;
    private View v;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);


        pictureRepository = new PictureRepository(getApplication());
        recyclerView = (RecyclerView) findViewById(R.id.details);


        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.detailsLayout);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewAdapter(getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        ShareFactory shareFactory = new ShareFactory(SharedViewModel.getInstance());
        SharedViewModel model = new ViewModelProvider(this,shareFactory).get(SharedViewModel.class);
        model.getStation().observe(this, new Observer<Station>() {
            @Override
            public void onChanged(Station station) {
                bus_station = station;

                observeList(bus_station);


            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(DetailsActivity.this,FormActivity.class);
                startActivity(intent);

            }
        });
    }


    private void observeList(Station station){
        if(station!= null){

            listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
            listViewModel.getPicturesById(bus_station.getId()).observe(this, new Observer<List<Picture>>() {
                @Override
                public void onChanged(List<Picture> pictures) {



                    if(pictures!=null){

                        mAdapter.setData(pictures);

                        enableSwipeToDeleteAndUndo();
                    }



                }
            });
        }
    }
    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getApplication()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Picture item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);
                pictureRepository.delete(item);

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        pictureRepository.insert(item);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.action_settings :
                break;
            case R.id.action_home :
                Intent intent = new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_map :
                Intent intent1 = new Intent(DetailsActivity.this,FullCityActivity.class);
                startActivity(intent1);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
