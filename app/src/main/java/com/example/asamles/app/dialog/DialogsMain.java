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

public class DialogsMain extends Fragment {
    private Button button;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
    private TextView label;
	private TextView vsProgress;
	private TextView label5;
	private boolean cancelable = true;
    private VerticalSeekBar verticalSeekBar = null;
	private int oldColor = -16777216;

    public static DialogsMain newInstance() {
        DialogsMain fragment = new DialogsMain();
        return fragment;
    }

    public DialogsMain() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dialog, container, false);
//---------------------------------------------------------------------------------------
        verticalSeekBar = (VerticalSeekBar) rootView.findViewById(R.id.vertical_Seekbar);
        vsProgress = (TextView) rootView.findViewById(R.id.vertical_sb_progresstext);
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
                vsProgress.setText(progress + "");
            }
        });
//---------------------------------------------------------------------------------------
        button = (Button) rootView.findViewById(R.id.time);
        button2 = (Button) rootView.findViewById(R.id.button);
        button3 = (Button) rootView.findViewById(R.id.button2);
        button4 = (Button) rootView.findViewById(R.id.button4);
        button5 = (Button) rootView.findViewById(R.id.button3);
		button6 = (Button) rootView.findViewById(R.id.button5);
		button7 = (Button) rootView.findViewById(R.id.button6);
		button8 = (Button) rootView.findViewById(R.id.button7);
		button9 = (Button) rootView.findViewById(R.id.button8);
        label = (TextView) rootView.findViewById(R.id.textView);
        label5 = (TextView) rootView.findViewById(R.id.textView4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                button.setText("working!");
                ADialogs.openTime(getActivity(), label, cancelable, "Title", "Set", "Cancel");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ADialogs.alert(getActivity(), cancelable, "Title", "Message", "Yes", null);
            }
        });
		button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ADialogs.seekbar(getActivity(), cancelable, "Title", "Set", "Cancel");
            }
        });
		button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ADialogs.progress(getActivity(), cancelable, "Progress...");
            }
        });
		button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // ADialogs.progress(getActivity(),cancelable, "Progress...");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                button3.setText("working!");
                showAlertDialogFragment();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                button4.setText("working!");
                showProgressDialogFragment();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                button5.setText("working!");
                showColorpickerDialogFragment();
            }
        });
		button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                button6.setText("working!");
                showCustomDialogFragment();
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
        newFragment.setBlurredAlertDialogListener(new BlurredAlertDialog.BlurredAlertDialogListener() {
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
        BlurredProgressDialog newFragment = BlurredProgressDialog.newInstance("Message", true);
        newFragment.setBlurredProgressDialogListener(new BlurredProgressDialog.BlurredProgressDialogListener() {
            @Override
            public void onBlurredProgressDialogCancel(DialogFragment dialog) {
                dialog.dismiss();
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }

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
                label5.setText("" + color);
                button5.setBackgroundColor(color);
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
    public void showCustomDialogFragment() {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredCustomViewDialog newFragment = new BlurredCustomViewDialog();
		AlertDialog.Builder ad = newFragment.build(getActivity(), cancelable, null, null, "Yes", "No");
		View seekLayout = setCustomView(ad, R.layout.dialog_seekbar);
		SeekBar seekBar = (SeekBar) seekLayout.findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if(newFragment.set){
					button6.setText("working! " + progress);
				}
            }
        });
		newFragment.setBlurredCustomAlertDialogListener(new BlurredCustomAlertDialog.BlurredCustomAlertDialogListener() {
            @Override
            public void onBlurredCustomAlertDialogPositiveClick(DialogFragment dialog, boolean set) {
                
				dialog.dismiss();
            }

            @Override
            public void onBlurredCustomAlertDialogNegativeClick(DialogFragment dialog) {
                dialog.dismiss();
            }

            @Override
            public void onBlurredCustomAlertDialogCancel(DialogFragment dialog) {
                dialog.dismiss();
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }
}