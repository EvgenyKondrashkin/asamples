package com.example.asamles.app.upNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asamles.app.BaseActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;

public class UpMain extends BaseActivity {
    private Button btn;
    private TextView label;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_navigation_text);
        btn = (Button) findViewById(R.id.button);
        label = (TextView) findViewById(R.id.textView);
        label.setText("UpMain Activity");
        name = getIntent().getStringExtra(Constants.NAME);
        getSupportActionBar().setTitle(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public void onUpNext(View view) {
        Intent intent = new Intent(this, UpFirst.class);
        intent.putExtra("NAME", name);
        startActivity(intent);
    }

}
