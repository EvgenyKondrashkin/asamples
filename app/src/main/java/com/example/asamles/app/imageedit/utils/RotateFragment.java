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


public class RotateFragment extends Fragment implements View.OnClickListener {
    private String name;
    public static final String TYPE = "TYPE";
    public static final String VALUE = "VALUE";
    private Bitmap finalBitmap;
    private Bitmap bitmap;
    private ImageButton rotateRightButton;
    private ImageButton rotateLeftButton;
    private ImageButton flipVerticalButton;
    private ImageButton flipHorizontalButton;
    private Bitmap mainBitmap;
    private OkFragmentListener doneListener = null;

    public void setOkFragmentListener(OkFragmentListener doneListener) {
        this.doneListener = doneListener;
    }

    public static RotateFragment newInstance() {
        RotateFragment fragment = new RotateFragment();
        return fragment;
    }
    public RotateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.imageedit_rotate_scroll, container, false);
        finalBitmap = ((BitmapDrawable) ImageEditMain.imageView.getDrawable()).getBitmap();
        setButtons(rootView);
        return rootView;
    }
    private void setButtons(View view){
        rotateRightButton = (ImageButton) view.findViewById(R.id.rotate_right);
		rotateRightButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_repeat)
              .colorRes(R.color.grey_light).sizeDp(32));
        rotateRightButton.setOnClickListener(this);

        rotateLeftButton = (ImageButton) view.findViewById(R.id.rotate_left);
		rotateLeftButton.setImageDrawable(new IconDrawable(getActivity(), Iconify.IconValue.fa_undo)
              .colorRes(R.color.grey_light).sizeDp(32));
        rotateLeftButton.setOnClickListener(this);

        flipVerticalButton = (ImageButton) view.findViewById(R.id.flip_vertical);
        flipVerticalButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.flip_vertical));
        flipVerticalButton.setOnClickListener(this);

        flipHorizontalButton = (ImageButton) view.findViewById(R.id.flip_horizontal);
        flipHorizontalButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.flip_horizontal));
        flipHorizontalButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        mainBitmap = ((BitmapDrawable)ImageEditMain.imageView.getDrawable()).getBitmap();
        switch (v.getId()){
            case R.id.rotate_right:
                bitmap = ImageEditor.doRotate(mainBitmap, 1);
                break;
            case R.id.rotate_left:
                bitmap = ImageEditor.doRotate(mainBitmap, -1);
                break;
            case R.id.flip_vertical:
                bitmap = ImageEditor.doFlip(mainBitmap, 1);
                break;
            case R.id.flip_horizontal:
                bitmap = ImageEditor.doFlip(mainBitmap, 2);
                break;
        }
        updateImage(bitmap);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done_menu, menu);
        menu.findItem(R.id.action_done).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_check)
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
                if(doneListener != null){
                    doneListener.onDone(getTag());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateImage(Bitmap bitmap){
        ImageEditMain.imageView.setImageBitmap(bitmap);
        ImageEditMain.mAttacher.update();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        updateImage(finalBitmap);
        if(mainBitmap != null) {
            mainBitmap = null;}
        if(finalBitmap != null) {
            finalBitmap = null;}
        if(bitmap != null) {
            bitmap = null;}
    }
}
