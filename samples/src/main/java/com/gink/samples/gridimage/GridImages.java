package com.gink.samples.gridimage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.gink.samples.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GridImages extends Activity {

    private ArrayList<String> imgs = new ArrayList<String>();
    DisplayImageOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_images_main);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        try {
            String jsonString = loadJSONFromAsset();
            imgs = jsonParse(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		boolean pauseOnScroll = false;
		boolean pauseOnFling = true;
		PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
		gridView.setOnScrollListener(listener);
        gridView.setAdapter(new ImageAdapter(this, imgs, options, imageLoader));
		Context context = this;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(GridImages.this, "Position = " + position, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(GridImages.this, ImagePager.class);
				String[] ims = imgs.toArray(new String[imgs.size()]);
				intent.putExtra("IMAGES", ims);
				intent.putExtra("POSITION", position);
				startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }
    public String loadJSONFromAsset() throws IOException {
        String json = null;
        try {
            InputStream is = getAssets().open("images.json");
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
        JSONArray jsonImgs = jsonResponse.getJSONArray("img");
        ArrayList<String> imgs = new ArrayList<String>();
        for (int i=0; i < jsonImgs.length(); i++)
        {
            try
            {
                String item = jsonImgs.getString(i);
                imgs.add(item);
            }
            catch (JSONException e)
            {
                alert();
                e.printStackTrace();
            }
        }
        return imgs;
    }

    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getString(R.string.error));
        builder.setCancelable(true);
        builder.setPositiveButton(this.getString(R.string.close),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }
}