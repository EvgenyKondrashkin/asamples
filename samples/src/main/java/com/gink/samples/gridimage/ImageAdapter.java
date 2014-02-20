package com.gink.samples.gridimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gink.samples.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> imgs = new ArrayList<String>();
    DisplayImageOptions options;
    ImageLoader imageLoader;
    public ImageAdapter(Context context, ArrayList<String> imgs, DisplayImageOptions options, ImageLoader imageLoader) {
        this.context = context;
        this.imgs = imgs;
        this.options = options;
        this.imageLoader = imageLoader;
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
//        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_grid_image, parent, false);
        } else {
            imageView = (ImageView) convertView;
        }

        imageLoader.displayImage(imgs.get(position), imageView, options);

        return imageView;
    }
}