package com.example.asamles.app.shake;

import android.app.Activity;
import android.content.Context;
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
    private String[] shakeMessage;

    public static ShakeMain newInstance() {
        return new ShakeMain();
    }

    public ShakeMain() {
    }

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shake, container, false);
        context = getActivity();
        TextView label = (TextView) rootView.findViewById(R.id.textView);
        shakeMessage = getResources().getStringArray(R.array.shake_list);
        SensorManager sensorManager = (SensorManager) this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
        return rootView;
    }

    public void hearShake() {
        int random = new Random().nextInt(shakeMessage.length);
        Toast.makeText(context, shakeMessage[random], Toast.LENGTH_SHORT).show();
    }

}