package com.example.asamles.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.asamles.app.gridimage.GridImages;
import com.example.asamles.app.location.Location;
import com.example.asamles.app.dialog.Dialogs;
import com.example.asamles.app.sms.SMSMain;

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ArrayList<String> list;
//	private String[] menu = getActivity().getResources().getStringArray(R.array.main_list);
    private ArrayAdapter<String> adapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));
		list = new ArrayList<String>(Arrays.asList(getActivity().getResources().getStringArray(R.array.main_list)));
        listView = (ListView)rootView.findViewById(R.id.list);
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
        switch(position)
        {
            case 0:
				ft.replace(R.id.container, Location.newInstance(list.get(position)));
                break;
            case 1:
				ft.replace(R.id.container, Dialogs.newInstance(list.get(position)));
                break;
            case 2:
				ft.replace(R.id.container, GridImages.newInstance(list.get(position)));
                break;
			case 3:
				ft.replace(R.id.container, SMSMain.newInstance(list.get(position)));
                break;
        }
		ft.addToBackStack("firstlvl");
        ft.commit();
    }
}