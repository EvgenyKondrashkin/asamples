package com.example.asamles.app.actionprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.R;

import java.util.ArrayList;

public class SizeAdapter {
    private SizeListener listener;

    public interface SizeListener {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser, int positionInList);
        public void onStartTrackingTouch(SeekBar seekBar, int positionInList);
        public void onStopTrackingTouch(SeekBar seekBar, int positionInList);
    }

    public SeekbarAdapter getAdapter(Context context, Bitmap title, SizeListener listener, int size) {
        this.listener = listener;
        return new SeekbarAdapter(context, title, int size);
    }

    public void setSizeListener(SizeListener listener) {
        this.listener = listener;
    }

    public class SeekbarAdapter extends BaseAdapter implements SeekBar.OnSeekBarChangeListener{
        private LayoutInflater mInflater;
		private Context contex;
        // private String item;
        private Bitmap title;
		private int size;
		private ViewHolder2 holder2;
		private ViewHolder holder;
		
        public SeekbarAdapter(Context context, Bitmap title, int size) {
            mInflater = LayoutInflater.from(context);
			this.context = context;
            // this.item = item;
            this.title = title;
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

		static class ViewHolder2 {
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
            holder2.icon.setImageDrawable(new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(title, size, size, false)));
            return convertView;
        }
		
		static class ViewHolder {
			SeekBar seekbar;
		}
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.actionbar_seekbar_dropdown, null);
                holder.seekbar = (SeekBar) convertView.findViewById(R.id.seekBar1);
                convertView.setTag(R.layout.actionbar_seekbar_dropdown, holder);
            } else {
                holder = (ViewHolder) convertView.getTag(R.layout.actionbar_seekbar_dropdown);
            }
            holder.seekbar.setOnSeekBarChangeListener(this);
            holder.seekbar.setTag(position);
			holder.seekbar.setProgress(size);
            return convertView;

        }
		@Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int position = (Integer) seekBar.getTag();
            if (mListener != null) {
				holder2.icon.setImageDrawable(new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(title, progress, progress, false)));
                mListener.onProgressChanged(seekBar, progress, fromUser, position);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            int position = (Integer) seekBar.getTag();
            if (mListener != null) {
                mListener.onStartTrackingTouch(seekBar, position);
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int position = (Integer) seekBar.getTag();
            if (mListener != null) {
                mListener.onStopTrackingTouch(seekBar, position);
            }
        }

    }

}
