package com.example.asamles.app.db;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asamles.app.R;

import java.util.ArrayList;


public class ListDBAdapter extends BaseAdapter {
    private final Activity context;
    private ArrayList<Animals> animals;
    private ViewHolder holder;

    public ListDBAdapter(Activity context, ArrayList<Animals> animals) {
        this.context = context;
        this.animals = animals;
    }

    static class ViewHolder {
        //        public TextView id;
        public TextView name;
        //		public TextView content;
        public TextView type;
        public TextView img;
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int i) {
        return animals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.animal_row, null, true);
            holder = new ViewHolder();
//            holder.id = (TextView) rowView.findViewById(R.id.id);
            holder.name = (TextView) convertView.findViewById(R.id.name);
//			holder.content = (TextView) rowView.findViewById(R.id.content);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.img = (TextView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.id.setText(animals.get(position).getId());
        holder.name.setText(animals.get(position).getName());
        holder.type.setText("" + animals.get(position).getType());
        holder.img.setText(animals.get(position).getImg());

        return convertView;
    }
}
