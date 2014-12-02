package com.example.asamles.app.charts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ChartMain extends Fragment implements GetForecastTask.GetForecastTaskListener {
    private LineChart chart;

    public static ChartMain newInstance() {
        return new ChartMain();
    }

    public ChartMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        chart = (LineChart) rootView.findViewById(R.id.chart);
        chart.setDrawLegend(false);
        chart.animateX(3000);
        chart.setDescription("");
        chart.setDrawVerticalGrid(false);
        chart.setDrawHorizontalGrid(false);
        chart.setDrawYLabels(false);
        chart.setNoDataTextDescription("Loading...");
        chart.setDrawBorder(false);

        GetForecastTask mt = new GetForecastTask(0, 0, this);
        mt.execute();

        return rootView;
    }


    @Override
    public void onGetForecastTaskComplete(OpenWeatherMapForecastResponse response) {
        ArrayList<Entry> valsDayTemp = new ArrayList<Entry>();
        ArrayList<String> xValsDay = new ArrayList<String>();
        Entry day = null;
        long time = 0;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        for(int i = 0; i<response.forecast.length; i++) {
            day = new Entry(response.forecast[i].temp.day, i);
            valsDayTemp.add(day);
            time = response.forecast[i].dt;
            calendar.setTimeInMillis(time*1000);
//            Date df = new java.util.Date(time);
            String vv = new SimpleDateFormat("dd.MM").format(calendar.getTime());
            xValsDay.add(vv);
        }

        LineDataSet dayTemp = new LineDataSet(valsDayTemp, "Akademgorodok");
        LineData dayWeatherData = new LineData(xValsDay, dayTemp);
        dayTemp.setLineWidth(4f);
        dayTemp.setCircleSize(8f);
        chart.setData(dayWeatherData);
    }

    @Override
    public void onGetForecastTaskError(String response) {
        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
    }
}
