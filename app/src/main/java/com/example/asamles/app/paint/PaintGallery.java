package com.example.asamles.app.paint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Vibrator;

import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.dialog.BlurredAlertDialog;
import com.example.asamles.app.paint.utils.PaintGalleryItem;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.io.File;
import java.util.ArrayList;

public class PaintGallery extends Fragment {

    public static final String IMAGES = "images";
    private String[] imgs;
    private DoneFragmentListener doneListener = null;
	private int selected;
	private ArrayList<PaintGalleryItem> imageList= new ArrayList<PaintGalleryItem>();
	private Vibrator vibrator;
	private GridView gridView;
	private GalleryImageAdapter adapter;
    public interface DoneFragmentListener {
        void onDone(String tag, String image);
    }

    public void setOkFragmentListener(DoneFragmentListener doneListener) {
        this.doneListener = doneListener;
    }

    public static PaintGallery newInstance(String[] imgs) {
        PaintGallery fragment = new PaintGallery();
        Bundle args = new Bundle();
        args.putStringArray(IMAGES, imgs);
        fragment.setArguments(args);
        return fragment;
    }

    public PaintGallery() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imgs = getArguments().getStringArray(IMAGES);
		imageList = toPaintGalleryItem(imgs);
		vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.paint_gallery, container, false);
		gridView = (GridView) rootView.findViewById(R.id.gridView);
		adapter = new GalleryImageAdapter(getActivity(), imageList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (doneListener != null) {
                    doneListener.onDone(getTag(), imageList.get(position).getImageName());
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				vibrator.vibrate(50);
                ImageView image = (ImageView) view.findViewById(R.id.image_overflow);
                if(imageList.get(position).getSelected()) {
                    imageList.get(position).setSelected(false);
					selected--;
                    image.setVisibility(View.INVISIBLE);
				} else {
                    imageList.get(position).setSelected(true);
					selected++;
                    image.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        return rootView;
    }
	public ArrayList<PaintGalleryItem> toPaintGalleryItem(String[] imgs){
		ArrayList<PaintGalleryItem> imageList = new ArrayList<PaintGalleryItem>();
		PaintGalleryItem item = null;
		for (int i = 0; i < imgs.length; i++) {
			item = new PaintGalleryItem(imgs[i]);
			imageList.add(item);
			item = null;
		}
		return imageList;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.paint_gallery_menu, menu);
        menu.findItem(R.id.action_delete).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_trash_o)
                .colorRes(R.color.grey_light)
                .actionBarSize());
        // menu.setGroupVisible(R.id.menu_group_paint, false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
				if(selected == 0){
					Toast.makeText(getActivity(), getActivity().getString(R.string.nothing_to_remove), Toast.LENGTH_LONG).show();
				} else {
					checkDialog(getActivity().getString(R.string.paint_gallery_remove_title), String.format(getActivity().getString(R.string.paint_gallery_remove_message),"" + selected));
				}
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	public void checkDialog(String title, String message) {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredAlertDialog newFragment = BlurredAlertDialog.newInstance(title, message);
        newFragment.setBlurredAlertDialogListener(new BlurredAlertDialog.BlurredAlertDialogListener() {
            @Override
            public void onBlurredAlertDialogPositiveClick(DialogFragment dialog) {
                Toast.makeText(getActivity(), "Selected = " + selected, Toast.LENGTH_LONG).show();
                removeLoadFiles(imageList.get(0).getImageName());
                dialog.dismiss();
            }

            @Override
            public void onBlurredAlertDialogNegativeClick(DialogFragment dialog) {
                dialog.dismiss();
            }

            @Override
            public void onBlurredAlertDialogCancel(DialogFragment dialog) {
                dialog.dismiss();
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }
	public void removeLoadFiles(String name){
        selected = 0;
        SaveLoadFile.removeFileFromPublicGallery(Constants.PAINT_GALLERY, name);
		String[] images = SaveLoadFile.loadAllPublicFiles(getActivity(), Constants.PAINT_GALLERY);
        imageList = toPaintGalleryItem(images);
        adapter = new GalleryImageAdapter(getActivity(), imageList);
        adapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
	}

}