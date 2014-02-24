package com.example.asamles.app.gridimage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;


public class ImagePager extends Fragment {

   private static final String STATE_POSITION = "STATE_POSITION";

   DisplayImageOptions options;
	ImageLoader imageLoader;
    private ArrayList<String> imageUrls = new ArrayList<String>();
    private int pagerPosition;
   ViewPager pager;
    public ImagePager(ArrayList<String> imageUrls, int pagerPosition) {
        this.imageUrls = imageUrls;
        this.pagerPosition = pagerPosition;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_pager, container, false);
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnFail(R.drawable.error)
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getApplicationContext()));
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        String[] ims = imageUrls.toArray(new String[imageUrls.size()]);
        pager.setAdapter(new ImagePagerAdapter(ims));
        pager.setCurrentItem(pagerPosition);
        return rootView;
   }

   @Override
   public void onSaveInstanceState(Bundle outState) {
       outState.putInt(STATE_POSITION, pager.getCurrentItem());
   }

   private class ImagePagerAdapter extends PagerAdapter {

       private String[] images;
       private LayoutInflater inflater;

       ImagePagerAdapter(String[] images) {
           this.images = images;
//           LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           inflater = getActivity().getLayoutInflater();
       }

       @Override
       public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object);
       }

       @Override
       public int getCount() {
           return images.length;
       }

       @Override
       public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.pager_item_img, view, false);
			assert imageLayout != null;
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

               @Override
               public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                   String message = null;
                   switch (failReason.getType()) {
                       case IO_ERROR:
                           message = "Input/Output error";
                           break;
                       case DECODING_ERROR:
                           message = "Image can't be decoded";
                           break;
                       case NETWORK_DENIED:
                           message = "Downloads are denied";
                           break;
                       case OUT_OF_MEMORY:
                           message = "Out Of Memory error";
                           break;
                       case UNKNOWN:
                           message = "Unknown error";
                           break;
                   }
                   Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                   spinner.setVisibility(View.GONE);
               }

               @Override
               public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                   spinner.setVisibility(View.GONE);
               }
           });

           view.addView(imageLayout, 0);
           return imageLayout;
       }

       @Override
       public boolean isViewFromObject(View view, Object object) {
           return view.equals(object);
       }

       @Override
       public void restoreState(Parcelable state, ClassLoader loader) {
       }

       @Override
       public Parcelable saveState() {
           return null;
       }
   }
}