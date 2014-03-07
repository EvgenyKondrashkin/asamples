package com.example.asamles.app.db;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.asamles.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class ListDBAdapter extends BaseAdapter {
    private final Activity context;
	private ArrayList<Animals> animals;
    private boolean net_err;
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
        View rowView =null;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.animal_row, null, true);
            holder = new ViewHolder();
//            holder.id = (TextView) rowView.findViewById(R.id.id);
            holder.name = (TextView) rowView.findViewById(R.id.name);
//			holder.content = (TextView) rowView.findViewById(R.id.content);
			holder.type = (TextView) rowView.findViewById(R.id.type);
			holder.img = (TextView) rowView.findViewById(R.id.img);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
//        holder.id.setText(animals.get(position).getId());
        holder.name.setText(animals.get(position).getName());
		holder.type.setText(""+animals.get(position).getType());
		holder.img.setText(animals.get(position).getImg());
		
        return rowView;
    }
}
