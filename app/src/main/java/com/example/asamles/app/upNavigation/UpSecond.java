package com.example.asamles.app.upNavigation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;

public class UpSecond extends MainActivity {
	private Button btn;
	private TextView label;
	private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_navigation_text);
		btn = (Button) findViewById(R.id.button);
		btn.setEnabled(false);
		label = (TextView) findViewById(R.id.textView);
		label.setText("Second");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		name = getIntent().getStringExtra("NAME");
		getSupportActionBar().setTitle(name);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    public void onUpSecond(View view) {
		
	}
	
}