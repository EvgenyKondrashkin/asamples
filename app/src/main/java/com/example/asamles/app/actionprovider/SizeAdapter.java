package com.example.asamles.app.actionprovider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.asamles.app.R;

public class SizeAdapter {
    private SizeListener listener;

    public interface SizeListener {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser, int positionInList);

        public void onStartTrackingTouch(SeekBar seekBar, int positionInList);

        public void onStopTrackingTouch(SeekBar seekBar, int positionInList);
    }

    public SeekbarAdapter getAdapter(Context context, Bitmap pointIcon, SizeListener listener, int size) {
        this.listener = listener;
        return new SeekbarAdapter(context, pointIcon, size);
    }

    public void setSizeListener(SizeListener listener) {
        this.listener = listener;
    }

    public class SeekbarAdapter extends BaseAdapter implements SeekBar.OnSeekBarChangeListener {
        private LayoutInflater mInflater;
        //        private Context context;
        // private String item;
        private Bitmap pointIcon;
        private int size;
        private ViewHolder2 holder2;
        private ViewHolder holder;

        public SeekbarAdapter(Context context, Bitmap pointIcon, int size) {
            mInflater = LayoutInflater.from(context);
//            this.context = context;
            // this.item = item;
//            this.pointIcon = pointIcon;
            this.pointIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_brush);
            this.size = size;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class ViewHolder2 {
            ImageView icon;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                holder2 = new ViewHolder2();
                convertView = mInflater.inflate(R.layout.action_size_layout, null);
                holder2.icon = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(R.layout.action_size_layout, holder2);
            } else {
                holder2 = (ViewHolder2) convertView.getTag(R.layout.action_size_layout);
            }
            holder2.icon.setImageBitmap(Bitmap.createScaledBitmap(pointIcon, size + 2, size + 2, false));
            return convertView;
        }

        class ViewHolder {
            SeekBar seekbar;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.action_seekbar_dropdown, null);
                holder.seekbar = (SeekBar) convertView.findViewById(R.id.seekBar1);
                convertView.setTag(R.layout.action_seekbar_dropdown, holder);
            } else {
                holder = (ViewHolder) convertView.getTag(R.layout.action_seekbar_dropdown);
            }
            holder.seekbar.setOnSeekBarChangeListener(this);
            holder.seekbar.setTag(position);
            holder.seekbar.setProgress(size);
            return convertView;

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int position = (Integer) seekBar.getTag();
            if (progress < 2) {
                progress = 2;
            }
            if (listener != null) {
                size = progress;
                listener.onProgressChanged(seekBar, progress, fromUser, position);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            int position = (Integer) seekBar.getTag();
            if (listener != null) {
                listener.onStartTrackingTouch(seekBar, position);
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int position = (Integer) seekBar.getTag();
            if (listener != null) {
                listener.onStopTrackingTouch(seekBar, position);
            }
        }

    }

}
