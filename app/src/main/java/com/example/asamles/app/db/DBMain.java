package com.example.asamles.app.db;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.asamles.app.R;

import java.util.ArrayList;

public class DBMain extends Fragment implements DBToClass.DBToClassListener {

    private Button btn;
    private ListView list;
    private ArrayList<Animals> animals;


    public static DBMain newInstance() {
        DBMain fragment = new DBMain();
        return fragment;
    }

    public DBMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);

        list = (ListView) rootView.findViewById(R.id.list);
//		list.setOnItemClickListener(this);

        DBToClass getDBToClass = new DBToClass(getActivity());
        getDBToClass.setDBToClassListener(this);
        getDBToClass.getDataFromDB();


        return rootView;
    }


    @Override
    public void onDBToClassListener(ArrayList<Animals> animals) {
        this.animals = animals;
        ListDBAdapter adapter = new ListDBAdapter(getActivity(), animals);
        list.setAdapter(adapter);
    }
}