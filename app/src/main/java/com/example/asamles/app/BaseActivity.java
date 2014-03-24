package com.example.asamles.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends ActionBarActivity{// implements ShareActionProvider.OnShareTargetSelectedListener {
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(getDefaultIntent());
//        mShareActionProvider.setOnShareTargetSelectedListener(this);
        return true;
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Theme");
        intent.putExtra(Intent.EXTRA_TEXT, "Message");
        return intent;
    }
//    @Override
//    public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
//        // TODO Auto-generated method stub
//        this.startActivity(intent);
//        return true;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share2:
                this.startActivity(getDefaultIntent());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
