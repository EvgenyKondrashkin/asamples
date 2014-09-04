package com.example.asamles.app.gson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asamles.app.R;
import com.example.asamles.app.json.JsonFromAssets;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class GsonMain extends Fragment {

    private ListView list;
    public static GsonMain newInstance() {
        return new GsonMain();
    }

    public GsonMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        String MARKER_TYPES = "marker_types.json";
        String MARKERS = "markers.json";

        String markerTypesString = null;
        String markersString = null;
        String markersArrayString = null;

        JSONArray JsonMarkerTypes = null;
        JSONObject jsonMarkers = null;
        try {
            markerTypesString = JsonFromAssets.resFromAsset(MARKER_TYPES, getActivity());
            JsonMarkerTypes = (JSONArray) new JSONTokener(markerTypesString).nextValue();

            markersString = JsonFromAssets.resFromAsset(MARKERS, getActivity());
            jsonMarkers = (JSONObject) new JSONTokener(markersString).nextValue();
            markersArrayString = jsonMarkers.getJSONArray("markers").toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        MarkerType[] markerTypes = gson.fromJson(JsonMarkerTypes.toString(), MarkerType[].class);
        Marker[] markers = gson.fromJson(markersArrayString, Marker[].class);

        ListAdapter adapter = new ListAdapter(getActivity(), markers, markerTypes);
        list.setAdapter(adapter);
        return rootView;
    }
}
