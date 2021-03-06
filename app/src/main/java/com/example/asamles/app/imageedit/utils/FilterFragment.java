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

import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.ImageEditMain;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class FilterFragment extends Fragment implements View.OnClickListener {
    private Bitmap finalBitmap;
    private Bitmap mainBitmap;
    private Bitmap smallBitmap;
    private ImageButton tintButton;
    private ImageEditor imageEditor;
    private OkFragmentListener doneListener = null;

    public void setOkFragmentListener(OkFragmentListener doneListener) {
        this.doneListener = doneListener;
    }

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    public FilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.imageedit_filter_scroll, container, false);
        finalBitmap = ((BitmapDrawable) ImageEditMain.imageView.getDrawable()).getBitmap();
        mainBitmap = finalBitmap;
        smallBitmap = ImageEditor.getResizedBitmap(finalBitmap, container.getHeight(), container.getHeight());//Bitmap.createScaledBitmap(finalBitmap, container.getHeight(), container.getHeight(), true);
        setButtons(rootView);
        imageEditor = new ImageEditor(mainBitmap);

        return rootView;
    }

    private void setButtons(View view) {
        ImageEditor.loadFrames(getActivity());
        ImageButton grayscaleButton = (ImageButton) view.findViewById(R.id.grayscale);
        grayscaleButton.setImageBitmap(ImageEditor.doGrayscale(smallBitmap));
        grayscaleButton.setOnClickListener(this);
        ImageButton sepiaButton = (ImageButton) view.findViewById(R.id.sepia);
        sepiaButton.setImageBitmap(ImageEditor.doSepia(smallBitmap));
        sepiaButton.setOnClickListener(this);
        ImageButton invertButton = (ImageButton) view.findViewById(R.id.invert);
        invertButton.setImageBitmap(ImageEditor.doInvert(smallBitmap));
        invertButton.setOnClickListener(this);
        ImageButton swapButton = (ImageButton) view.findViewById(R.id.swap);
        swapButton.setImageBitmap(ImageEditor.doSwap(smallBitmap));
        swapButton.setOnClickListener(this);
        ImageButton polaroidButton = (ImageButton) view.findViewById(R.id.polaroid);
        polaroidButton.setImageBitmap(ImageEditor.doPolaroid(smallBitmap));
        polaroidButton.setOnClickListener(this);
        ImageButton blackwhiteButton = (ImageButton) view.findViewById(R.id.blackwhite);
        blackwhiteButton.setImageBitmap(ImageEditor.doBlackWhite(smallBitmap));
        blackwhiteButton.setOnClickListener(this);
        ImageButton oldframeButton = (ImageButton) view.findViewById(R.id.oldframe);
        oldframeButton.setImageBitmap(ImageEditor.doOldPhoto(smallBitmap));
        oldframeButton.setOnClickListener(this);
        ImageButton bokehButton = (ImageButton) view.findViewById(R.id.bokeh);
        bokehButton.setImageBitmap(ImageEditor.doBokehPhoto(smallBitmap));
        bokehButton.setOnClickListener(this);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done_menu, menu);
        menu.findItem(R.id.action_done).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.icon_done)
                .colorRes(R.color.grey_light)
                .actionBarSize());
        menu.setGroupVisible(R.id.menu_group_imageedit, false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                finalBitmap = ((BitmapDrawable) ImageEditMain.imageView.getDrawable()).getBitmap();
                if (doneListener != null) {
                    doneListener.onDone(getTag());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateImage(Bitmap bitmap) {
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }

    @Override
    public void onClick(View v) {
        mainBitmap = finalBitmap;
        switch (v.getId()) {
            case R.id.grayscale:
                mainBitmap = imageEditor.doGrayscale(mainBitmap);
//                mainBitmap = ImageEditor.doGrayscale(mainBitmap);
                break;
            case R.id.sepia:
                mainBitmap = imageEditor.doSepia(mainBitmap);
//                mainBitmap = ImageEditor.doSepia(mainBitmap);
                break;
            case R.id.invert:
                mainBitmap = imageEditor.doInvert(mainBitmap);
//                mainBitmap = ImageEditor.doInvert(mainBitmap);
                break;
            case R.id.swap:
                mainBitmap = imageEditor.doSwap(mainBitmap);
//                mainBitmap = ImageEditor.doSwap(mainBitmap);
                break;
            case R.id.polaroid:
                mainBitmap = imageEditor.doPolaroid(mainBitmap);
//                mainBitmap = ImageEditor.doPolaroid(mainBitmap);
                break;
            case R.id.blackwhite:
                mainBitmap = imageEditor.doBlackWhite(mainBitmap);
//                mainBitmap = ImageEditor.doBlackWhite(mainBitmap);
                break;
            case R.id.oldframe:
                mainBitmap = imageEditor.doOldPhoto(mainBitmap);
//                mainBitmap = ImageEditor.doOldPhoto(mainBitmap, getActivity());
                break;
            case R.id.bokeh:
                mainBitmap = imageEditor.doBokehPhoto(mainBitmap);
//                mainBitmap = ImageEditor.doBokehPhoto(mainBitmap, getActivity());
                break;
        }
        updateImage(mainBitmap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateImage(finalBitmap);
        if (mainBitmap != null) {
            mainBitmap = null;
        }
        if (finalBitmap != null) {
            finalBitmap = null;
        }
        if (smallBitmap != null) {
            smallBitmap = null;
        }
    }
}
