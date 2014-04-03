package com.example.asamles.app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String[] list;
    private String[] fragmentNames;
    private ArrayAdapter<String> adapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().getSupportFragmentManager().popBackStack();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));
        list = getActivity().getResources().getStringArray(R.array.main_list);
        fragmentNames = getActivity().getResources().getStringArray(R.array.fragment_names);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(list[position]);
        Fragment fragment = Fragment.instantiate(getActivity(), fragmentNames[position]);
        ft.replace(R.id.container, fragment);
        ft.addToBackStack("firstlvl");
        ft.commit();
    }
}