package com.example.asamles.app.picassogridimage;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.asamles.app.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImagePagerAdapter extends PagerAdapter {

    private String[] images;
    private LayoutInflater inflater;
    Activity activity;
    private ImageView imageView;

    ImagePagerAdapter(String[] images, LayoutInflater inflater, Activity activity) {
        this.images = images;
        this.inflater = inflater;
        this.activity = activity;
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
        imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
        spinner.setVisibility(View.VISIBLE);
        Picasso.with(activity)
                .load(images[position])
                .error(R.drawable.error)
                .into(imageView, new Callback() {

                    @Override
                    public void onSuccess() {
                        imageView.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        imageView.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.INVISIBLE);
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