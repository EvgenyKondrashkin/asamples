package com.example.asamles.app.db;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asamles.app.R;

import java.util.ArrayList;

public class DBMain extends Fragment implements DBToClass.DBToClassListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView list;
    private SwipeRefreshLayout pullToRefresh;
    public static DBMain newInstance() {
        return new DBMain();
    }

    public DBMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        pullToRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(this);
        // делаем повеселее
        pullToRefresh.setColorScheme(R.color.green, R.color.gallery_white,R.color.green,R.color.gallery_white);
        list = (ListView) rootView.findViewById(R.id.list);
//		list.setOnItemClickListener(this);

        DBToClass getDBToClass = new DBToClass(getActivity());
        getDBToClass.setDBToClassListener(this);
        getDBToClass.getDataFromDB();


        return rootView;
    }


    @Override
    public void onDBToClassListener(ArrayList<Animals> animals) {
        ListDBAdapter adapter = new ListDBAdapter(getActivity(), animals);
        list.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "start refresh", Toast.LENGTH_SHORT).show();
        pullToRefresh.setRefreshing(true);
        pullToRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefresh.setRefreshing(false);

            }
        }, 3000);
        Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
    }
}