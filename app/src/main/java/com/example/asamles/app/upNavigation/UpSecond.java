package com.example.asamles.app.upNavigation;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;

public class UpSecond extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_navigation_text);
        Button btn = (Button) findViewById(R.id.button);
        btn.setEnabled(false);
        TextView label = (TextView) findViewById(R.id.textView);
        label.setText("Second Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

}