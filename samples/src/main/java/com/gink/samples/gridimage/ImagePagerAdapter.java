//package com.gink.samples.gridimage;
//
//import android.content.Context;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.gink.samples.R;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//
//import java.util.ArrayList;
//
//public class ImagePagerAdapter extends PagerAdapter {
//		// Context context;
//    // ArrayList<String> imgs = new ArrayList<String>();
//    // DisplayImageOptions options;
//    // ImageLoader imageLoader;
//       private String[] images;
//       private LayoutInflater inflater;
//
//       ImagePagerAdapter(String[] images) {
//           this.images = images;
//           inflater = getLayoutInflater();
//       }
//
//       @Override
//       public void destroyItem(ViewGroup container, int position, Object object) {
//           container.removeView((View) object);
//       }
//
//       @Override
//       public int getCount() {
//           return images.length;
//       }
//
//       @Override
//       public Object instantiateItem(ViewGroup view, int position) {
//			View imageLayout = inflater.inflate(R.layout.pager_item_img, view, false);
//			assert imageLayout != null;
//			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
//			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
////			ImageLoader imageLoader = ImageLoader.getInstance();
////			imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
//			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
//				@Override
//				public void onLoadingStarted(String imageUri, View view) {
//					spinner.setVisibility(View.VISIBLE);
//				}
//
//               @Override
//               public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                   String message = null;
//                   switch (failReason.getType()) {
//                       case IO_ERROR:
//                           message = "Input/Output error";
//                           break;
//                       case DECODING_ERROR:
//                           message = "Image can't be decoded";
//                           break;
//                       case NETWORK_DENIED:
//                           message = "Downloads are denied";
//                           break;
//                       case OUT_OF_MEMORY:
//                           message = "Out Of Memory error";
//                           break;
//                       case UNKNOWN:
//                           message = "Unknown error";
//                           break;
//                   }
//                   Toast.makeText(ImagePager.this, message, Toast.LENGTH_SHORT).show();
//
//                   spinner.setVisibility(View.GONE);
//               }
//
//               @Override
//               public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                   spinner.setVisibility(View.GONE);
//               }
//           });
//
//           view.addView(imageLayout, 0);
//           return imageLayout;
//       }
//
//       @Override
//       public boolean isViewFromObject(View view, Object object) {
//           return view.equals(object);
//       }
//
//       @Override
//       public void restoreState(Parcelable state, ClassLoader loader) {
//       }
//
//       @Override
//       public Parcelable saveState() {
//           return null;
//       }
//   }