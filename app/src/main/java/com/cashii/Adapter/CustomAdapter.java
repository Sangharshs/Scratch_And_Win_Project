package com.cashii.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cashii.MainActivity;
import com.cashii.Model.Grid_Item;
import com.cashii.cashii.R;
import com.cashii.ScratchActivity;
import com.cashii.SpinActivity;
import com.cashii.VideosActivity;
import com.cashii.WatchActivity;

import java.util.List;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.viewHolder>{

    List<Grid_Item> grid_item_list;
    Intent intent ;
    public CustomAdapter(List<Grid_Item> grid_item_list) {
        this.grid_item_list = grid_item_list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.grid_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
         holder.name.setText(grid_item_list.get(position).getName());
         holder.cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(position == 0) {
                     if(MainActivity.rewardedVideoAd.isAdLoaded()){
                         MainActivity.rewardedVideoAd.show();
                         v.getContext().startActivity(new Intent(v.getContext(), SpinActivity.class));

                     }else {
                         v.getContext().startActivity(new Intent(v.getContext(), SpinActivity.class));
                     }
                 }
                 if(position == 1) {
                     if(MainActivity.rewardedVideoAd.isAdLoaded()){
                         MainActivity.rewardedVideoAd.show();
                         v.getContext().startActivity(new Intent(v.getContext(), WatchActivity.class));

                     }else {
                         v.getContext().startActivity(new Intent(v.getContext(), WatchActivity.class));
                     }
                 }
                 if(position == 2) {
                     if(MainActivity.rewardedVideoAd.isAdLoaded()){
                         MainActivity.rewardedVideoAd.show();
                         holder.itemView.getContext().startActivity(new Intent(v.getContext(), VideosActivity.class));

                     }else {
                         holder.itemView.getContext().startActivity(new Intent(v.getContext(), VideosActivity.class));

                     }
                 }
                 if(position == 3) {
                     if(MainActivity.rewardedVideoAd.isAdLoaded()){
                         MainActivity.rewardedVideoAd.show();
                         holder.itemView.getContext().startActivity(new Intent(v.getContext(), ScratchActivity.class));

                     }else {
                     holder.itemView.getContext().startActivity(new Intent(v.getContext(), ScratchActivity.class));
                 }
                 }


//                 Toast.makeText(holder.itemView.getContext(), "Po "+position, Toast.LENGTH_SHORT).show();
              }
         });

    }

    @Override
    public int getItemCount() {
        return grid_item_list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
         TextView name;
         LinearLayout linearLayout;
         CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text);
            linearLayout = itemView.findViewById(R.id.linearLL);
            cardView = itemView.findViewById(R.id.cardView);



        }
    }
}
