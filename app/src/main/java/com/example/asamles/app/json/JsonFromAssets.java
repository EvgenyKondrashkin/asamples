package com.example.asamles.app.json;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonFromAssets {
    public static final String JSON_ARRAY = "img";
    private String jsonString;
    private Context context;

    public JsonFromAssets(String jsonString, Context context) {
        this.context = context;
        this.jsonString = jsonString;
    }

    public ArrayList<String> getFromJson() {
        ArrayList<String> list;
        try {
            String res = resFromAsset(jsonString);
            list = jsonParse(res);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public String resFromAsset(String ASSETS_FILE) throws IOException {
        String json;
        try {
            InputStream is = context.getAssets().open(ASSETS_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<String> jsonParse(String res) throws JSONException {
        JSONObject jsonResponse = new JSONObject(res);
        JSONArray jsonImgs = jsonResponse.getJSONArray(JSON_ARRAY);
        ArrayList<String> imgs = new ArrayList<String>();
        for (int i = 0; i < jsonImgs.length(); i++) {
            try {
                String item = jsonImgs.getString(i);
                imgs.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return imgs;
    }
}

