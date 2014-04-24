package com.example.asamles.app.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.R;

public class ShareMain extends Fragment implements SeekbarAdapter.SeekBarListener {

    private Button btn, btn2, btn3;
    private TextView label;
    private ShareActionProvider mShareActionProvider;

    public static ShareMain newInstance() {
        return new ShareMain();
    }

    public ShareMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share, container, false);
        btn = (Button) rootView.findViewById(R.id.button);
        btn2 = (Button) rootView.findViewById(R.id.button2);
        btn3 = (Button) rootView.findViewById(R.id.button3);
        label = (TextView) rootView.findViewById(R.id.textView);

//		ShareActionProvider mShareActionProvider = (ShareActionProvider) btn3.getActionProvider();

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                btn.setText("working!");
                String tweetUrl = "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url="
                        + "https://www.google.com";
                Uri uri = Uri.parse(tweetUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setText("working!");
                Intent i = new Intent(android.content.Intent.ACTION_SEND);

                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Wat");
                i.putExtra(Intent.EXTRA_TEXT, "WAT IS IT?");

                startActivity(Intent.createChooser(i, "Share it!"));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn3.setText("working!");

            }
        });
        return rootView;
    }

    // @Override
    // public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    // inflater.inflate(R.menu.main, menu);
    // MenuItem shareItem = menu.findItem(R.id.action_share);
    // mShareActionProvider = (ShareActionProvider)
    // MenuItemCompat.getActionProvider(shareItem);
    // mShareActionProvider.setShareIntent(getDefaultIntent());

    // super.onCreateOptionsMenu(menu, inflater);
    // }

    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    // switch (item.getItemId()) {
    // case R.id.action_share2:
    // this.startActivity(getDefaultIntent());
    // return true;

    // default:
    // return super.onOptionsItemSelected(item);
    // }
    // }

    // private Intent getDefaultIntent() {
    // Intent intent = new Intent(Intent.ACTION_SEND);
    // intent.setType("text/plain");
    // intent.putExtra(Intent.EXTRA_SUBJECT, "Theme");
    // intent.putExtra(Intent.EXTRA_TEXT, "Message");
    // return intent;
    // }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        MenuItem seekbarItem = menu.findItem(R.id.action_seekbar);
        CheckBoxActionProvider seekbarActionProvider = (CheckBoxActionProvider) MenuItemCompat.getActionProvider(seekbarItem);
        seekbarActionProvider.setCheckBoxActionProvider(getActivity(), this);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser, int positionInList) {
        label.setText("" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar, int positionInList) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar, int positionInList) {

    }
}