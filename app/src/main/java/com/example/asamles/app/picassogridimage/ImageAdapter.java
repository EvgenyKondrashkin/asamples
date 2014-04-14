package com.example.asamles.app.picassogridimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.asamles.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> imgs = new ArrayList<String>();

    public ImageAdapter(Context context, ArrayList<String> imgs) {
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {
            imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.grid_images_item, parent, false);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(context)
                .load(imgs.get(position))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView);
        return imageView;
    }
}