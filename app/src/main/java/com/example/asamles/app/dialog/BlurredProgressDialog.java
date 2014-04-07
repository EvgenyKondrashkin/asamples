package com.example.asamles.app.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.imageedit.blur.BlurTask;
import com.example.asamles.app.imageedit.blur.FastBlur;

public class BlurredProgressDialog extends DialogFragment implements BlurTask.BlurTaskListener {
	private BlurredProgressDialogListener listener;
	private String message;
    private ImageView background;
    private Bitmap map;

    public interface BlurredProgressDialogListener {
		public void onBlurredProgressDialogCancel(DialogFragment dialog);
    }
	
    public void setBlurredProgressDialogListener(BlurredProgressDialogListener listener){
        this.listener = listener;
    }

	public static BlurredProgressDialog newInstance(String message) {
        BlurredProgressDialog fragment = new BlurredProgressDialog();
        Bundle args = new Bundle();
		args.putString(Constants.MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		message = getArguments() != null ? getArguments().getString(Constants.MESSAGE) : null;
        View rootView = inflater.inflate(R.layout.blurred_dialog_fragment, container, false);
		background = (ImageView) rootView.findViewById(R.id.image);
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, statusBarHeight, 0, 0);
        background.setLayoutParams(params);

        map=takeScreenShot(getActivity());
        blur(map, background);
		
        ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(message);
		pd.setCancelable(true);
		pd.setCanceledOnTouchOutside(true);
		pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				listener.onBlurredProgressDialogCancel(BlurredProgressDialog.this);
			}
		});
		pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		pd.show();
        return rootView;
    }
    private void blur(Bitmap bkg, ImageView view) {
        BlurTask task = new BlurTask(bkg, view, this);
        task.execute();
    }

    @Override
    public void onBlurTaskComplete(Bitmap result) {
        if (result != null) {
            background.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, map.getWidth(), map.getHeight(), false)));
        } else {
            ADialogs.alert(getActivity(), getActivity().getString(R.string.error));
        }
    }
    private static Bitmap takeScreenShot(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  - statusBarHeight);
        view.setDrawingCacheEnabled(false);
        return b;
    }
}