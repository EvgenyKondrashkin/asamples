package com.gink.samples.location;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gink.samples.R;

public class Location extends Activity implements LocationFound{

    Button btn, btn2, btn3;
    TextView label;
    private LocationListenerPlayServices locationListener;
    private LocationListenerStandart locationListener2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        label = (TextView) findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                btn.setText("working!");
                playServiceFind();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setText("working!");
                GPSNetworkFind();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn3.setText("working!");
                allFind();
            }
        });
    }
    private void playServiceFind()
    {
        locationListener = LocationListenerPlayServices.getInstance(this);
        if(locationListener.servicesConnected(this)) {
            locationListener.enableMyLocation();
            locationListener.setLocationFound(this);
        }
    }
    private void GPSNetworkFind() {
        locationListener2 = LocationListenerStandart.getInstance(this);
        locationListener2.setLocationFound(this);
        locationListener2.startLocation();
    }
    private void allFind()
    {
        locationListener = LocationListenerPlayServices.getInstance(this);
        locationListener2 = LocationListenerStandart.getInstance(this);
        if(locationListener.servicesConnected(this)) {
            locationListener.enableMyLocation();
            locationListener.setLocationFound(this);
        }
        if(locationListener2.providersEnabled(this)) {
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