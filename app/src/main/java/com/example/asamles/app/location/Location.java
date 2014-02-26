package com.example.asamles.app.location;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.R;

public class Location extends Fragment implements LocationFound{

    private Button btn, btn2, btn3;
    private TextView label;
    private LocationListenerPlayServices locationListener;
    private LocationListenerStandart locationListener2;
	private String name;
	
	public static Location newInstance(String name) {
            Location fragment = new Location();
            Bundle args = new Bundle();
            args.putString(Constants.NAME, name);
            fragment.setArguments(args);
            return fragment;
    }
		
    public Location() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment, container, false);
		name = getArguments().getString(Constants.NAME);
		((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
        btn = (Button) rootView.findViewById(R.id.button);
        btn2 = (Button) rootView.findViewById(R.id.button2);
        btn3 = (Button) rootView.findViewById(R.id.button3);
        label = (TextView) rootView.findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                btn.setText("working!");
                playServiceFind(getActivity());

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setText("working!");
                GPSNetworkFind(getActivity());
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn3.setText("working!");
                allFind(getActivity());
            }
        });
        return rootView;
    }

    private void playServiceFind(Context context)
    {
        locationListener = LocationListenerPlayServices.getInstance(context);
        if(locationListener.servicesConnected(context)) {
            locationListener.enableMyLocation();
            locationListener.setLocationFound(this);
        }
    }
    private void GPSNetworkFind(Context context) {
        locationListener2 = LocationListenerStandart.getInstance(context);
        locationListener2.setLocationFound(this);
        locationListener2.startLocation();
    }
    private void allFind(Context context)
    {
        locationListener = LocationListenerPlayServices.getInstance(context);
        locationListener2 = LocationListenerStandart.getInstance(context);
        if(locationListener.servicesConnected(context)) {
            locationListener.enableMyLocation();
            locationListener.setLocationFound(this);
        }
        if(locationListener2.providersEnabled(context)) {
            locationListener2.setLocationFound(this);
            locationListener2.startLocation();
        }
    }

    @Override
    public void locationFound(android.location.Location location) {
        label.setText("Location: "+location.getLongitude()+"/"+location.getLatitude());
        if(locationListener != null && locationListener.clientConnected()){
            locationListener.disableMyLocation();
        }
        if(locationListener2 != null && locationListener2.request){
            locationListener2.disableLocationUpdates();
        }
    }
}