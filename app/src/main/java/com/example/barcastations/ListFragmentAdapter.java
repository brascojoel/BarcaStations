package com.example.barcastations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.MyViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(Station item);
    }
    private  OnItemClickListener listener = null;
    private List<Station> data;
    private Context context;
    private View itemView;

    public ListFragmentAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_fragment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.bind(data.get(position), listener);



    }

    public ListFragmentAdapter(List<Station> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public ListFragmentAdapter(List<Station> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView lines;
        ImageView arrow;




        public MyViewHolder(View itemView) {
            super(itemView);

            this.lines = (TextView) itemView.findViewById(R.id.id_lines);
            this.arrow = (ImageView) itemView.findViewById(R.id.id_arrow_forward);


        }

        public void bind(final Station station, final OnItemClickListener listener) {


          //  TextView textViewName = holder.lines;
           // ImageView arrow_image = holder.arrow;



            int x = station.getBuses().split("-").length;
            String  line = "line";

            if(x>1){
                line = "lines";
            }

            this.lines.setText(x+" - "+line);
            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(station);
                }
            });
          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(station);
                }
            }); */
        }
    }
}
