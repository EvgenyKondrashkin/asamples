package com.example.asamles.app.shake;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.squareup.seismic.ShakeDetector;

import java.util.Random;

public class ShakeMain extends Fragment implements ShakeDetector.Listener {
    private TextView label;
    private String name;
    private String[] shakeMessage;

    public static ShakeMain newInstance() {
        ShakeMain fragment = new ShakeMain();
        return fragment;
    }

    public ShakeMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shake_main, container, false);
        label = (TextView) rootView.findViewById(R.id.textView);
        shakeMessage = getResources().getStringArray(R.array.shake_list);
        SensorManager sensorManager = (SensorManager) this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
        return rootView;
    }

    public void hearShake() {
        int random = new Random().nextInt(shakeMessage.length);
        Toast.makeText(getActivity(), shakeMessage[random], Toast.LENGTH_SHORT).show();
    }

}