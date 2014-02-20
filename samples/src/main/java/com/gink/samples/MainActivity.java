package com.gink.samples;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import com.gink.samples.gridimage.GridImages;
import com.gink.samples.location.Location;
import com.gink.samples.dialog.Dialogs;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

	private ListView listView;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
		list.add("Location");
		list.add("Dialog");
        list.add("Image grid");
        listView = (ListView)findViewById(R.id.list);
		listView.setOnItemClickListener(this);		
    }
	@Override
	protected void onResume() {
		super.onResume();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);    
	}
	@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = null;
		switch(position)
		{
            case 0:
                intent = new Intent(this,Location.class);
            break;
		    case 1:
				intent = new Intent(this,Dialogs.class);
            break;
            case 2:
                intent = new Intent(this, GridImages.class);
            break;
		}
		startActivity(intent);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }
    
}