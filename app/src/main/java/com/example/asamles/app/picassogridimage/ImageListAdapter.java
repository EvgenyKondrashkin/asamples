package com.example.asamles.app.picassogridimage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageListAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> imgs = new ArrayList<String>();

    public ImageListAdapter(Activity context, ArrayList<String> list, ArrayList<String> imgs) {
        this.context = context;
        this.list = list;
        this.imgs = imgs;
    }

    static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
            rowView = inflater.inflate(R.layout.list_row, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.label);
            holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.textView.setText(list.get(position));
        Picasso.with(context)
                .load(imgs.get(position))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        return rowView;
    }

}