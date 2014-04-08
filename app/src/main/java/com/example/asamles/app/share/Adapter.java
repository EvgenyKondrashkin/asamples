package com.example.asamles.app.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.example.asamles.app.seekbar.VerticalSeekBar;

import java.util.ArrayList;

public class Adapter {
    private SeekBarListener mListener;

    public interface SeekBarListener {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);

        public void onStartTrackingTouch(SeekBar seekBar);

        public void onStopTrackingTouch(SeekBar seekBar);
    }

    public listAdapter getAdapter(Context context, ArrayList<String> list, String title) {
        return new listAdapter(context, list, title);
    }

    public void setSeekBarListener(SeekBarListener listener) {
        mListener = listener;
    }

    public class listAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private onSeekbarChange mSeekListener;
        private ArrayList<String> itemsList;
        private String title;

        public listAdapter(Context context, ArrayList<String> list, String title) {
            mInflater = LayoutInflater.from(context);
            if (mSeekListener == null) {
                mSeekListener = new onSeekbarChange();
            }
            this.itemsList = list;
            this.title = title;
        }

        @Override
        public int getCount() {
            return itemsList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder2 holder;

            if (convertView == null) {
                holder = new ViewHolder2();
                convertView = mInflater.inflate(R.layout.baseadapter_layout, null);
                holder.text_title = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(R.layout.baseadapter_layout, holder);
            } else {
                holder = (ViewHolder2) convertView.getTag(R.layout.baseadapter_layout);
            }
            holder.text_title.setText(title);
            return convertView;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.action_bar_dropdown, null);

                holder.verticalSeekBar = (VerticalSeekBar) convertView.findViewById(R.id.vertical_seekbar);
                convertView.setTag(R.layout.action_bar_dropdown, holder);
            } else {
                holder = (ViewHolder) convertView.getTag(R.layout.action_bar_dropdown);
            }
            holder.verticalSeekBar.setOnSeekBarChangeListener(mSeekListener);
//            holder.verticalSeekBar.setProgress(100);
            holder.verticalSeekBar.setTag(position);
            return convertView;

        }

    }

    static class ViewHolder {
        VerticalSeekBar verticalSeekBar;
    }

    static class ViewHolder2 {
        TextView text_title;
    }


    public class onSeekbarChange implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mListener != null) {
                mListener.onProgressChanged(seekBar, progress, fromUser);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
//            int position = (Integer) seekBar.getTag();
            if (mListener != null) {
                mListener.onStartTrackingTouch(seekBar);
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mListener != null) {
                mListener.onStopTrackingTouch(seekBar);
            }
        }

    }
}
