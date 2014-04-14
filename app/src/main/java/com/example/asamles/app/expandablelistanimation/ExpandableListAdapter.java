package com.example.asamles.app.expandablelistanimation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.example.asamles.app.db.Animals;
import com.example.asamles.app.expandablelistanimation.widget.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.Random;

public class ExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    private ArrayList<Animals> items;
    private int color;
    public ExpandableListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<Animals> items) {
        this.items = items;
    }
    private int groupColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
    @Override
    public Animals getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    static class ViewHolder {
        public TextView title;
        public TextView hint;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Animals item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.expandable_list_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textTitle);
            holder.hint = (TextView) convertView.findViewById(R.id.textHint);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText("id: " + item.getId());
        holder.hint.setText("image: " + item.getImg());
//        color = groupColor();
//        convertView.setBackgroundColor(color);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Animals getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    static class ViewHolder2 {
        public TextView title;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder2 holder;
        Animals item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new ViewHolder2();
            convertView = inflater.inflate(R.layout.expandable_group_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder2) convertView.getTag();
        }

        holder.title.setText(item.getName());
//        color = groupColor();
//        convertView.setBackgroundColor(color);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
}
