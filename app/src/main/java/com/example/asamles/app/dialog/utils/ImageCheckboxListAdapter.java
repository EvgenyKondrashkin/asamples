package com.example.asamles.app.dialog.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asamles.app.R;

import java.util.ArrayList;

public class ImageCheckboxListAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<ImageTextCheckbox> list;
    private ViewHolder holder;

    public ImageCheckboxListAdapter(Context context, ArrayList<ImageTextCheckbox> list) {
        this.context = (Activity) context;
        this.list = list;
    }

    static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public CheckBox checkBox;
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

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.dialog_custom_list_row, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.textView);
            holder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
            holder.checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
            holder.checkBox.setEnabled(false);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.textView.setText(list.get(position).getText());
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.checkBox.setChecked(list.get(position).getCheck());
        return rowView;
    }
}