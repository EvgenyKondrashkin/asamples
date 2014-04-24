package com.example.asamles.app.paint;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.asamles.app.R;
import com.example.asamles.app.paint.utils.PaintGalleryItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryImageAdapter extends BaseAdapter {
    Activity context;
    ArrayList<PaintGalleryItem> images = new ArrayList<PaintGalleryItem>();

    public GalleryImageAdapter(Activity context, ArrayList<PaintGalleryItem> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
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
        ViewHolder holder;
        View gridItemView = convertView;
        if (gridItemView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            gridItemView = inflater.inflate(R.layout.paint_gallery_item, null, true);
            holder = new ViewHolder();
            if (gridItemView != null) {
                holder.imageView = (ImageView) gridItemView.findViewById(R.id.image);
                gridItemView.setTag(holder);
            }
        } else {
            holder = (ViewHolder) gridItemView.getTag();
        }
        Picasso.with(context)
                .load(images.get(position).getImageFullName())
                .into(holder.imageView);
        return gridItemView;
    }
}