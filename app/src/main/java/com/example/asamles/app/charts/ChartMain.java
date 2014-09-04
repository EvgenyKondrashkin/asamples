package com.example.asamles.app.charts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asamles.app.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ChartMain extends Fragment {


    public static ChartMain newInstance() {
        return new ChartMain();
    }

    public ChartMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        LineChart chart = (LineChart) rootView.findViewById(R.id.chart);
        String[] x = {"2","5","3","7"};
        String[] dataset = {"1","2","3","4"};
        ArrayList<String> data = new ArrayList<String>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");


        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        Entry c1e1 = new Entry(100, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50, 1);
        Entry c1e3 = new Entry(120,3);
        valsComp1.add(c1e2);
        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q"); xVals.add("2.Q"); xVals.add("3.Q"); xVals.add("4.Q");


        LineData lineData = new LineData(xVals,setComp1);

        chart.setData(lineData);
        return rootView;
    }
}
