package com.example.asamles.app.paint;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryImageAdapter extends BaseAdapter {
    Activity context;
    ArrayList<String> imgs = new ArrayList<String>();

    public GalleryImageAdapter(Activity context, ArrayList<String> imgs) {
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

    static class ViewHolder {
        public ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder holder;
        View gridItemView = convertView;
        if (gridItemView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            gridItemView = inflater.inflate(R.layout.paint_gallery_item, null, true);
            holder = new ViewHolder();
            holder.imageView = (ImageView) gridItemView.findViewById(R.id.image);
            gridItemView.setTag(holder);
        } else {
            holder = (ViewHolder) gridItemView.getTag();
        }
//        View gridItemView = LayoutInflater.from(context).inflate(R.layout.list_row, null, true);
//        ImageView imageView;
//        if (convertView == null) {
//
//            imageView = (ImageView) gridItemView.findViewById(R.id.image);
//        } else {
//            imageView = (ImageView) convertView;
//        }
        Picasso.with(context)
                .load(imgs.get(position))
                .into(holder.imageView);
        return gridItemView;
    }
}