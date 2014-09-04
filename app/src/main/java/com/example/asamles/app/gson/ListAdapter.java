package com.example.asamles.app.gson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asamles.app.R;

public class ListAdapter extends BaseAdapter {
    private ViewHolder holder;
    private Marker[] markers;
    private MarkerType[] markerTypes;
    private Context context;

    public ListAdapter(Context context, Marker[] markers, MarkerType[] markerTypes) {
        this.context = context;
        this.markers = markers;
        this.markerTypes = markerTypes;
    }

    static class ViewHolder {
        public TextView tag;
        public TextView x;
        public TextView y;
        public TextView imageRes;
    }

    @Override
    public int getCount() {
        return markers.length;
    }

    @Override
    public Object getItem(int i) {
        return markers[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.gson_row, null, true);
            holder = new ViewHolder();
            holder.tag = (TextView) convertView.findViewById(R.id.name);
            holder.x = (TextView) convertView.findViewById(R.id.x);
            holder.y = (TextView) convertView.findViewById(R.id.y);
            holder.imageRes = (TextView) convertView.findViewById(R.id.imageres);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.id.setText(animals.get(position).getId());
        for(MarkerType markerType: markerTypes){
            if(markerType.getType() == markers[position].getType()){
                holder.tag.setText(markerType.getTag());
                holder.imageRes.setText(markerType.getImage());//context.getResources().getIdentifier(markerType.getImage(), "drawable", context.getPackageName()));
            }
        }
        holder.x.setText("x: " + markers[position].getX());
        holder.y.setText("y: " + markers[position].getY());

        return convertView;
    }
}
