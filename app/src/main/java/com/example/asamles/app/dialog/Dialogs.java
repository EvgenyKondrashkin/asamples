package com.example.asamles.app.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.example.asamles.app.seekbar.VerticalSeekBar;

import java.util.Calendar;

public class Dialogs extends Fragment implements BlurredAlertDialog.BlurredAlertDialogListener, BlurredProgressDialog.BlurredProgressDialogListener {
    private Button btn, btn2, btn3, btn4;
    private TextView label, label2, label3, vsProgress;
	VerticalSeekBar verticalSeekBar=null;
    public static Dialogs newInstance() {
        Dialogs fragment = new Dialogs();
        return fragment;
    }

    public Dialogs() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment, container, false);
//        rootView.setBackground(new BitmapDrawable(takeScreenShot(getActivity())));
        verticalSeekBar=(VerticalSeekBar)rootView.findViewById(R.id.vertical_Seekbar);
		vsProgress=(TextView)rootView.findViewById(R.id.vertical_sb_progresstext);
        verticalSeekBar.setProgress(100);
		verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				vsProgress.setText(progress+"");
				
			}
		});
		
        btn = (Button) rootView.findViewById(R.id.time);
        btn2 = (Button) rootView.findViewById(R.id.button);
        btn3 = (Button) rootView.findViewById(R.id.button2);
		btn4 = (Button) rootView.findViewById(R.id.button4);
        label = (TextView) rootView.findViewById(R.id.textView);
        label2 = (TextView) rootView.findViewById(R.id.textView2);
        label3 = (TextView) rootView.findViewById(R.id.textView3);

        label2.setText("" + System.currentTimeMillis());
        Calendar date = Calendar.getInstance();
        label3.setText("" + date.getTimeInMillis());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn.setText("working!");
                ADialogs.openTime(getActivity(), label);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn2.setText("working!");
                ADialogs.alert(getActivity(), "Wat");


            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn3.setText("working!");
                // Calendar date1 = Calendar.getInstance();
                // date1.set(Calendar.HOUR_OF_DAY, 0);
                // date1.set(Calendar.MINUTE, 0);
                // date1.set(Calendar.SECOND, 0);
                // date1.set(Calendar.MILLISECOND, 0);
                // label3.setText("" + date1.getTimeInMillis());
				showAlertDialogFragment();
            }
        });
		btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn3.setText("working!");
				showProgressDialogFragment();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void showAlertDialogFragment() {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredAlertDialog newFragment = BlurredAlertDialog.newInstance("Title", "Message");
        newFragment.setBlurredAlertDialogListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(1, newFragment).commit();
    }
	@Override
    public void onBlurredAlertDialogPositiveClick(DialogFragment dialog) {
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
	public void showProgressDialogFragment() {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredProgressDialog newFragment = BlurredProgressDialog.newInstance("Message");
        newFragment.setBlurredProgressDialogListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(1, newFragment).commit();
    }
	    @Override
    public void onBlurredProgressDialogCancel(DialogFragment dialog) {
        dialog.dismiss();
    }
}