package com.example.asamles.app.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.asamles.app.R;
import com.example.asamles.app.seekbar.VerticalSeekBar;

import java.util.Calendar;

public class Dialogs extends Fragment {
    private Button btn, btn2, btn3, btn4 , btn5;
    private TextView label, label2, label3, vsProgress, label5;
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
        btn5 = (Button) rootView.findViewById(R.id.button3);
        label = (TextView) rootView.findViewById(R.id.textView);
        label2 = (TextView) rootView.findViewById(R.id.textView2);
        label3 = (TextView) rootView.findViewById(R.id.textView3);
        label5 = (TextView) rootView.findViewById(R.id.textView4);

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
                btn4.setText("working!");
				showProgressDialogFragment();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btn5.setText("working!");
                showColorpickerDialogFragment();
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
        newFragment.setBlurredAlertDialogListener(new BlurredAlertDialog.BlurredAlertDialogListener(){
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
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }

	public void showProgressDialogFragment() {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredProgressDialog newFragment = BlurredProgressDialog.newInstance("Message");
        newFragment.setBlurredProgressDialogListener(new BlurredProgressDialog.BlurredProgressDialogListener(){
            @Override
            public void onBlurredProgressDialogCancel(DialogFragment dialog) {
                dialog.dismiss();
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }
    private int oldColor = -16777216;
    public void showColorpickerDialogFragment() {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredColorPickerDialog newFragment = BlurredColorPickerDialog.newInstance("Title", oldColor);
        newFragment.setBlurredColorPickerDialogListener(new BlurredColorPickerDialog.BlurredColorPickerDialogListener() {
            @Override
            public void onBlurredAlertDialogPositiveClick(DialogFragment dialog, int color) {
                oldColor = color;
//                String hexColor = String.format("#%08X", (0xFFFFFFFF & color));
                label5.setText(""+color);
                btn5.setBackgroundColor(color);
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

}