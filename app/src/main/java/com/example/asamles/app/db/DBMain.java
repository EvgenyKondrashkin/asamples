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

//	@Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        PagerTextFragment fragment= PagerTextFragment.newInstance(news.get(position).getImgs(), news.get(position).getTitle(), news.get(position).getContent());
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.setCustomAnimations(R.anim.push_down_in,R.anim.push_down_out,R.anim.left_right_in,R.anim.left_right_out);
//        ft.replace(R.id.container, fragment);
//        ft.addToBackStack("news");
//        ft.commit();

//    }


    @Override
    public void onDBToClassListener(ArrayList<Animals> animals) {
        this.animals = animals;
        ListDBAdapter adapter = new ListDBAdapter(getActivity(), animals);
        list.setAdapter(adapter);
    }
}