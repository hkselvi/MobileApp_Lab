package com.example.week2;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

public class AnimalAdapter extends BaseAdapter {
    private List<Animal> animals;
    private LayoutInflater mInflater;

    public AnimalAdapter(Activity activity, List<Animal> animals) {
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.animals = animals;
    }

    //@Override
    //public int hashCode() {
    //    return super.hashCode();
    //}
    //
    //@Override
    //public boolean equals(@Nullable Object obj) {
    //    return super.equals(obj);
    //}
    //
    //protected void finalize() throws Throwable {
    //    super.finalize();
    //}

    public int getCount() {
        return animals.size();
    }

    public Object getItem(int position) {
        return animals.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = mInflater.inflate(R.layout.listview_row,null);
        TextView textView = rowView.findViewById(R.id.label);
        ImageView imageView = rowView.findViewById(R.id.pic);

        Animal animal = animals.get(position);

        textView.setText(animal.getType());
        imageView.setImageResource(animal.getPicId());

        return rowView;
    }
}
