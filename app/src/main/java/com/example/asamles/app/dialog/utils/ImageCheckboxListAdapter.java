package com.example.asamles.app.dialog.utils;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.squareup.picasso.Picasso;


public class ImageCheckboxListAdapter extends BaseAdapter {
    private Activity context;
    private String[] list;
    private TypedArray images;

    public ImageCheckboxListAdapter(Activity context, int listRes, int imgsRes) {
        if (context != null) {
            this.context = context;
            if (listRes != 0) {
                this.list = context.getResources().getStringArray(listRes);
            }
            if (imgsRes != 0) {
                this.images = context.getResources().obtainTypedArray(imgsRes);
            }
        }
    }

    public ImageCheckboxListAdapter(Activity context, int listRes) {
        this.context = context;
        if (context != null) {
            if (listRes != 0) {
                this.list = context.getResources().getStringArray(listRes);
            }
        }
    }

    static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return list[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
//            rowView = inflater.inflate(R.layout.list_row, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.label);
            Typeface type = Typeface.createFromAsset(context.getAssets(), "Georgia.ttf");
            holder.textView.setTypeface(type);
            holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.textView.setText(list[position]);
        Picasso.with(context)
                .load(images.getResourceId(position, -1))
                .error(R.drawable.error)
                .into(holder.imageView);
        return rowView;
    }

}