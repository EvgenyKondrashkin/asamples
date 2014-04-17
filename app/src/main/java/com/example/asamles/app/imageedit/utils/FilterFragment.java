package com.example.asamles.app.imageedit.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.ImageEditMain;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class FilterFragment extends Fragment {
    private String name;
    public static final String TYPE = "TYPE";
    public static final String VALUE = "VALUE";
    private ImageButton grayscaleButton;
    private ImageButton sepiaButton;
    private ImageButton invertButton;
    private ImageButton swapButton;
    private ImageButton polaroidButton;
    private ImageButton blackwhiteButton;
    private Bitmap mainBitmap;
    private Bitmap smallBitmap;
    private ImageButton oldframeButton;

    public static FilterFragment newInstance() {//String name) {
        FilterFragment fragment = new FilterFragment();
//        Bundle args = new Bundle();
//        args.putString(TYPE, name);
//        fragment.setArguments(args);
        return fragment;
    }
    public FilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        name = getArguments() != null ? getArguments().getString(TYPE) : null;
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.imageedit_filter_scroll, container, false);
        mainBitmap = ((BitmapDrawable) ImageEditMain.imageView.getDrawable()).getBitmap();
        smallBitmap = Bitmap.createScaledBitmap(mainBitmap, container.getHeight(), container.getHeight(), true);
        setButtons(rootView);



        return rootView;
    }
    private void setButtons(View view){
        grayscaleButton = (ImageButton) view.findViewById(R.id.grayscale);
        grayscaleButton.setImageBitmap(ImageEditior.doGrayscale(smallBitmap));
        grayscaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(ImageEditior.doGrayscale(mainBitmap));
            }
        });
        sepiaButton = (ImageButton) view.findViewById(R.id.sepia);
        sepiaButton.setImageBitmap(ImageEditior.doSepia(smallBitmap));
        sepiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(ImageEditior.doSepia(mainBitmap));
            }
        });
        invertButton = (ImageButton) view.findViewById(R.id.invert);
        invertButton.setImageBitmap(ImageEditior.doInvert(smallBitmap));
        invertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(ImageEditior.doInvert(mainBitmap));
            }
        });
        swapButton = (ImageButton) view.findViewById(R.id.swap);
        swapButton.setImageBitmap(ImageEditior.doSwap(smallBitmap));
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(ImageEditior.doSwap(mainBitmap));
            }
        });
		polaroidButton = (ImageButton) view.findViewById(R.id.polaroid);
        polaroidButton.setImageBitmap(ImageEditior.doPolaroid(smallBitmap));
        polaroidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(ImageEditior.doPolaroid(mainBitmap));
            }
        });
		blackwhiteButton = (ImageButton) view.findViewById(R.id.blackwhite);
        blackwhiteButton.setImageBitmap(ImageEditior.doBlackWhite(smallBitmap));
        blackwhiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(ImageEditior.doBlackWhite(mainBitmap));
            }
        });
        oldframeButton = (ImageButton) view.findViewById(R.id.oldframe);
        oldframeButton.setImageBitmap(ImageEditior.doOldPhoto(smallBitmap, getActivity()));
        oldframeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(ImageEditior.doOldPhoto(mainBitmap, getActivity()));
            }
        });
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done_menu, menu);
        menu.findItem(R.id.action_done).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_check)
                .colorRes(R.color.grey_light)
                .actionBarSize());

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateImage(Bitmap bitmap){
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }
}
