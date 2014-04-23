package com.example.asamles.app.expandablelistanimation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.example.asamles.app.R;
import com.example.asamles.app.db.Animals;
import com.example.asamles.app.db.DBToClass;
import com.example.asamles.app.expandablelistanimation.widget.AnimatedExpandableListView;

import java.util.ArrayList;

public class ExpandableListAnimationMain extends Fragment implements DBToClass.DBToClassListener, OnGroupClickListener {
    private AnimatedExpandableListView listView;
    private ExpandableListAdapter adapter;
    private ArrayList<Animals> items = new ArrayList<Animals>();

    public static ExpandableListAnimationMain newInstance() {
        ExpandableListAnimationMain fragment = new ExpandableListAnimationMain();
        return fragment;
    }

    public ExpandableListAnimationMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expandable_listview, container, false);
        listView = (AnimatedExpandableListView) rootView.findViewById(R.id.listView);
        DBToClass getDBToClass = new DBToClass(getActivity());
        getDBToClass.setDBToClassListener(this);
        getDBToClass.getDataFromDB();


        return rootView;
    }

    @Override
    public void onDBToClassListener(ArrayList<Animals> items) {
        this.items = items;
        adapter = new ExpandableListAdapter(getActivity());
        adapter.setData(this.items);
        listView.setAdapter(adapter);
        listView.setChildDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setDividerHeight(0);
        listView.expandGroup(0);
        lenght = adapter.getGroupCount();
        listView.setOnGroupClickListener(this);
    }

    int lenght = 0;

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (listView.isGroupExpanded(groupPosition)) {
            listView.collapseGroupWithAnimation(groupPosition);
        } else {
            listView.expandGroupWithAnimation(groupPosition);
            for (int i = 0; i < lenght; i++) {
                if (i != groupPosition) {
                    listView.collapseGroupWithAnimation(i);
                }
            }
        }
        return true;
    }

}