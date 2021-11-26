package com.cashii.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class SpaceItems extends DrawerItem<SpaceItems.viewHolder>{

    private int spaceUp;

    public SpaceItems(int spaceUp) {
        this.spaceUp = spaceUp;
    }


    @Override
    public viewHolder createViewHolder(ViewGroup parent) {
        Context c = parent.getContext();
        View v = new View(c);
        int height = (int) (c.getResources().getDisplayMetrics().density*spaceUp);
        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height
        ));
        return new viewHolder(v);
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public void bindViewHolder(viewHolder holder) {

    }

    public class viewHolder extends DrawerAdapter.ViewHolder{

        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
