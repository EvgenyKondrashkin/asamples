package com.example.asamles.app.charts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetForecastTask extends AsyncTask<Void, Void, Bundle> {

    private static final String RESULT_RESPONSE = "RESULT_RESPONSE";
    private static final String RESULT_ERROR = "RESULT_ERROR";
    private long lat;
    private long lon;
    private GetForecastTaskListener callback;

    public interface GetForecastTaskListener {
        public void onGetForecastTaskComplete(OpenWeatherMapForecastResponse response);
        public void onGetForecastTaskError(String response);
    }

    public GetForecastTask(long lat, long lon, GetForecastTaskListener callback){
        this.lat = lat;
        this.lon = lon;
        this.callback = callback;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bundle doInBackground(Void... params) {
        Bundle result = new Bundle();

        String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=54.868541&lon=83.091134&cnt=10&mode=json&units=metric";
        URL url = null;
        String response = null;
        try {
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = connection.getInputStream();
            response = streamToString(inputStream);
            Gson gson = new Gson();
            OpenWeatherMapForecastResponse responseObject = gson.fromJson(response, OpenWeatherMapForecastResponse.class);
            result.putParcelable(RESULT_RESPONSE, responseObject);
        } catch (Exception e) {
            result.putString(RESULT_ERROR, e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(Bundle result) {
        super.onPostExecute(result);
        OpenWeatherMapForecastResponse forecast = result.getParcelable(RESULT_RESPONSE);
        if(forecast != null) {
            callback.onGetForecastTaskComplete(forecast);
        } else {
            callback.onGetForecastTaskError(result.getString(RESULT_ERROR));
        }
    }

    public String streamToString(InputStream p_is) {
        try {
            BufferedReader m_br;
            StringBuilder m_outString = new StringBuilder();
            m_br = new BufferedReader(new InputStreamReader(p_is));
            String m_read = m_br.readLine();
            while(m_read != null) {
                m_outString.append(m_read);
                m_read =m_br.readLine();
            }
            return m_outString.toString();
        }
        catch (Exception p_ex) {
            p_ex.printStackTrace();
            return null;
        }
    }
}
