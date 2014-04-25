package com.example.asamles.app.location;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationMain extends Fragment implements LocationFound {

    private Button btn, btn2, btn3, btn4;
    private TextView label;
    private LocationListenerPlayServices locationListener;
    private LocationListenerStandart locationListener2;
    private Location location;
    private int width;
    private int height;
    private MapView mapView;
    private GoogleMap map;
    private boolean btn4Clicked = false;

    public static LocationMain newInstance() {
        return new LocationMain();
    }

    public LocationMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        width = container.getWidth();
        height = container.getHeight();
        int size;
        // View view_instance = (View)rootView.findViewById(R.id.map_card);
        // ViewGroup.LayoutParams params=view_instance.getLayoutParams();
        // if(height > width){
            // size = width;
        // } else {size = height;}
        // params.height = size;
        // view_instance.setLayoutParams(params);
        btn = (Button) rootView.findViewById(R.id.button);
        btn2 = (Button) rootView.findViewById(R.id.button2);
        btn3 = (Button) rootView.findViewById(R.id.button3);
        label = (TextView) rootView.findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                btn.setTextColor(getActivity().getResources().getColor(R.color.green));
                playServiceFind(getActivity());

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setTextColor(getActivity().getResources().getColor(R.color.green));
                GPSNetworkFind(getActivity());
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				btn3.setTextColor(getActivity().getResources().getColor(R.color.green));
                allFind(getActivity());
            }
        });

        btn4 = (Button) rootView.findViewById(R.id.button4);
        if (btn4Clicked){
            btn4.setVisibility(View.INVISIBLE);
        }
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn4Clicked = true;
                btn4.setVisibility(View.INVISIBLE);
                try {
                    initilizeMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(location != null){
                    map(location.getLatitude(), location.getLongitude());
                }
            }
        });
        return rootView;
    }

    private void initilizeMap() {
        if (map == null) {
            map = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(
                    R.id.mapview)).getMap();
            if (map == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void map(double lat, double lng){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10);
        map.animateCamera(cameraUpdate);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("You are here!"));
    }
    private void playServiceFind(Context context) {
        locationListener = LocationListenerPlayServices.getInstance(context);
        if (locationListener.servicesConnected(context)) {
            locationListener.enableMyLocation();
            locationListener.setLocationFound(this);
        }
    }

    private void GPSNetworkFind(Context context) {
        locationListener2 = LocationListenerStandart.getInstance(context);
        locationListener2.setLocationFound(this);
        locationListener2.startLocation();
    }

    private void allFind(Context context) {
        locationListener = LocationListenerPlayServices.getInstance(context);
        locationListener2 = LocationListenerStandart.getInstance(context);
        if (locationListener.servicesConnected(context)) {
            locationListener.enableMyLocation();
            locationListener.setLocationFound(this);
        }
        if (locationListener2.providersEnabled()) {
            locationListener2.setLocationFound(this);
            locationListener2.startLocation();
        }
    }

    @Override
    public void locationFound(Location location) {
        this.location = location;
        label.setText("" + location.getLongitude() + " / " + location.getLatitude());
        if (locationListener != null && locationListener.clientConnected()) {
            locationListener.disableMyLocation();
        }
        if (locationListener2 != null && locationListener2.request) {
            locationListener2.disableLocationUpdates();
        }
        if(map != null){
            map(location.getLatitude(), location.getLongitude());
        }
    }



    public void onDestroyView()
    {
        super.onDestroyView();
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.mapview));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }
}