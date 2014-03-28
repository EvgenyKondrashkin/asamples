package com.example.asamles.app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import com.bugsense.trace.BugSenseHandler;

public class MainActivity extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener {
    private ShareActionProvider mShareActionProvider;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(this, "27e64652");
        setContentView(R.layout.activity_main);
		
		homeAsUpByBackStack();
		
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();//"UpNav", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged() {
		homeAsUpByBackStack();
    }
	private void homeAsUpByBackStack(){
		int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
		if (backStackEntryCount > 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
	}
}
