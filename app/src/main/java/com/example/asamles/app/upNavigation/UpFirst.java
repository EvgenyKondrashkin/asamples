package com.example.asamles.app.upNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;

public class UpFirst extends MainActivity {
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_navigation_text);
        TextView label = (TextView) findViewById(R.id.textView);
        label.setText("First Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = getIntent().getStringExtra("NAME");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onUpNext(View view) {
        Intent intent = new Intent(this, UpSecond.class);
        intent.putExtra("NAME", name);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}