package com.example.asamles.app.upNavigation;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;

public class UpMain extends Activity {
	private Button btn, btn2;
	private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_navigation_main);
		btn = (Button) findViewById(R.id.button);
		btn2 = (Button) findViewById(R.id.button2);
		name = getIntent().getStringExtra("NAME");
//		getSupportActionBar().setTitle(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return false;
    }
    public void onUpActivity(View view) {
		Intent intent = new Intent(this, UpFirst.class);
		intent.putExtra("NAME", name);
		startActivity(intent);
	}
	
	public void onUpFragment(View view) {
		
	}
}
