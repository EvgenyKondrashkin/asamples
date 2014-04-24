package com.example.asamles.app.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

    public static LocationMain newInstance() {
        return new LocationMain();
    }

    public LocationMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        width = container.getWidth();
        height = container.getHeight();
        btn = (Button) rootView.findViewById(R.id.button);
        btn2 = (Button) rootView.findViewById(R.id.button2);
        btn3 = (Button) rootView.findViewById(R.id.button3);
        label = (TextView) rootView.findViewById(R.id.textView);
        map(rootView, savedInstanceState, 43.1, -87.9);
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

        btn4 = (Button) rootView.findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(location == null){
//                    map(rootView, savedInstanceState, 83.09f, 54.86f);
//                } else {
//                    map(rootView, savedInstanceState, (float) location.getLatitude(), (float) location.getLongitude());
//                }
                mapView.setVisibility(View.VISIBLE);
                btn4.setVisibility(View.INVISIBLE);
            }
        });
        return rootView;
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
    }


    Marker marker;
    private void map(View view, Bundle savedInstanceState, double lat, double lng){
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(false);
//        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        try {
            MapsInitializer.initialize(getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10);
        map.animateCamera(cameraUpdate);

//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(lat, lng))
//                .title("You are here!"));

    }

//    @Override
//    public void onResume() {
//        if (mapView != null) {
//            mapView.onResume();
//        }
//        super.onResume();
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mapView != null) {
//        mapView.onDestroy();
//        }
//    }

//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        if (mapView != null) {
//            mapView.onLowMemory();
//        }
//    }
}