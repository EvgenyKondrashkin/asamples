package com.example.asamles.app.paint;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
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

import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.dialog.BlurredAlertDialog;
import com.example.asamles.app.paint.utils.PaintGalleryItem;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

public class PaintGallery extends Fragment {

    public static final String IMAGES = "images";
    private DoneFragmentListener doneListener = null;
	private ArrayList<Integer> selectedList = new ArrayList<Integer>();
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
        String[] imgs = getArguments().getStringArray(IMAGES);
		imageList = toPaintGalleryItem(imgs);
		vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.paint_gallery, container, false);
        if (rootView != null) {
            gridView = (GridView) rootView.findViewById(R.id.gridView);
        }
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
                    selectedList.remove(Integer.valueOf(position));
				} else {
                    imageList.get(position).setSelected(true);
					selected++;
                    image.setVisibility(View.VISIBLE);
					selectedList.add(position);
                }
                return true;
            }
        });
        return rootView;
    }
	public ArrayList<PaintGalleryItem> toPaintGalleryItem(String[] imgs){
		ArrayList<PaintGalleryItem> imageList = new ArrayList<PaintGalleryItem>();
		PaintGalleryItem item;
        for (String img : imgs) {
            item = new PaintGalleryItem(img);
            imageList.add(item);
//            item = null;
        }
		return imageList;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.paint_gallery_menu, menu);
        MenuItem delete = menu.findItem(R.id.action_delete);
        if (delete != null) {
            delete.setIcon(new IconDrawable(getActivity(), Iconify.IconValue.icon_trash)
                    .colorRes(R.color.grey_light)
                    .actionBarSize());
        }
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
                Toast.makeText(getActivity(), "Removed " + selected + " images", Toast.LENGTH_LONG).show();
                removeLoadFiles(selectedList);
                selectedList.clear();
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
	public void removeLoadFiles(ArrayList<Integer> list){
        selected = 0;
        for (Integer i : list) {
            SaveLoadFile.removeFileFromPublicGallery(Constants.PAINT_GALLERY, imageList.get(i).getImageName());
        }
		String[] images = SaveLoadFile.loadAllPublicFiles(Constants.PAINT_GALLERY);
        imageList = toPaintGalleryItem(images);
        adapter = new GalleryImageAdapter(getActivity(), imageList);
        adapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
	}

}