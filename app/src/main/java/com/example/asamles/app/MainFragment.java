package com.example.asamles.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asamles.app.db.DBMain;
import com.example.asamles.app.dialog.Dialogs;
import com.example.asamles.app.gridimage.GridImages;
import com.example.asamles.app.location.Location;
import com.example.asamles.app.picassogridimage.PicassoGridImages;
import com.example.asamles.app.imageedit.ImageEdit;
import com.example.asamles.app.picassogridimage.PicassoMain;
import com.example.asamles.app.shake.ShakeMain;
import com.example.asamles.app.share.ShareMain;
import com.example.asamles.app.sms.SMSMain;
import com.example.asamles.app.striptab.StripTabMain;
import com.example.asamles.app.upNavigation.UpFragmentMain;
import com.example.asamles.app.upNavigation.UpNavigationMain;

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String[] list;
    //	private String[] menu = getActivity().getResources().getStringArray(R.array.main_list);
    private ArrayAdapter<String> adapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().getSupportFragmentManager().popBackStack();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));
        list = getActivity().getResources().getStringArray(R.array.main_list);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        Toast.makeText(getActivity(), ""+ShakeMain.class.getName(), Toast.LENGTH_LONG).show();
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
        String fragmentName = "com.example.asamles.app.shake.ShakeMain";
        Fragment shake;
        shake = Fragment.instantiate(getActivity(), fragmentName);



        switch (position) {
            case 0:
                ft.replace(R.id.container, Location.newInstance());
                break;
            case 1:
                ft.replace(R.id.container, Dialogs.newInstance());
                break;
            case 2:
                ft.replace(R.id.container, GridImages.newInstance());
                break;
            case 3:
                ft.replace(R.id.container, PicassoMain.newInstance());
                break;
			case 4:
                ft.replace(R.id.container, ImageEdit.newInstance());
                break;
            case 5:
                ft.replace(R.id.container, SMSMain.newInstance());
                break;
            case 6:
                ft.replace(R.id.container, ShareMain.newInstance());
                break;
            case 7:
                ft.replace(R.id.container, DBMain.newInstance());
                break;
            case 8:
                ft.replace(R.id.container, StripTabMain.newInstance());
                break;
            case 9:
                ft.replace(R.id.container, UpNavigationMain.newInstance());
                break;
			case 10:
//                ft.replace(R.id.container, ShakeMain.newInstance());
                ft.replace(R.id.container,shake);
                break;
        }
        ft.addToBackStack("firstlvl");
        ft.commit();
    }
}