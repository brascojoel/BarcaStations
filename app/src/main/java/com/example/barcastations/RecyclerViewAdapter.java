package com.example.barcastations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private List<Picture> data;
    private Context context;


    public RecyclerViewAdapter(Context context,List<Picture> data) {
        this.data = data;
        this.context = context;

    }

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView tv_title = holder.title;
        TextView tv_date = holder.date;
        ImageView imageView = holder.imageViewIcon;

        tv_title.setText(data.get(position).getTitle());
        tv_date.setText(data.get(position).getDate_picture());
        imageView.setImageBitmap(Utils.convertByteArrayToBitmap(data.get(position).getImage()));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Picture item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public List<Picture> getData() {
        return data;
    }

    public void setData(List<Picture> data) {
        this.data = data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        ImageView imageViewIcon;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.tv_title);
            this.date = (TextView) itemView.findViewById(R.id.tv_date);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }
}
