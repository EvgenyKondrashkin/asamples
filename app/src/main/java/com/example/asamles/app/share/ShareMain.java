package com.example.asamles.app.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;

public class ShareMain extends Fragment {

    private Button btn, btn2, btn3;
    private TextView label;
    private String name;

    public static ShareMain newInstance(String name) {
        ShareMain fragment = new ShareMain();
        Bundle args = new Bundle();
        args.putString(Constants.NAME, name);
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.share_fragment, container, false);
        name = getArguments().getString(Constants.NAME);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(name);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.refresh, menu);
//        MenuItem shareItem = menu.findItem(R.id.action_refresh);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(getActivity().getApplicationContext(), "Refreshed", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}