package com.cashii.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cashii.cashii.R;

public class SimpleItem extends DrawerItem<SimpleItem.viewHolder>{

    private int selectedItemIconTint;
    private int selectedItemTextTint;

    private int normalItemIconTint;
    private int normalItemTextTint;

    private Drawable icon;
    private String title;

    public SimpleItem(Drawable icon, String title){
        this.icon = icon;
        this.title = title;
    }


    @Override
    public viewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_option,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void bindViewHolder(viewHolder holder) {
         holder.title.setText(title);
         holder.icon.setImageDrawable(icon);

//         holder.title.setText(isChecked ? selectedItemTextTint: normalItemTextTint);
//         holder.icon.setColorFilter(isChecked? selectedItemIconTint : normalItemIconTint);
    }

    public SimpleItem withSelectedIconTint(int selectedItemIconTint){
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }

    public SimpleItem withSelectedTextTint(int selectedItemTextTint){
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

    public SimpleItem withIconTint (int normalItemIconTint){
        this.normalItemIconTint = normalItemIconTint;
        return this;
    }
    public SimpleItem withIextTint (int normalItemTextTint){
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }




    static class viewHolder extends DrawerAdapter.ViewHolder{
         private ImageView icon;
         private TextView title;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.option);
            title = itemView.findViewById(R.id.option_title);
        }
    }
}
