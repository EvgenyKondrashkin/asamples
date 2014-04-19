package com.example.asamles.app.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.utils.ImageCheckboxListAdapter;
import com.example.asamles.app.dialog.utils.ImageTextCheckbox;
import com.example.asamles.app.seekbar.VerticalSeekBar;

import java.util.ArrayList;

public class DialogsMain extends Fragment {
    private Button timeDialogButton;
    private Button alertDialogButton;
    private Button blurredAlertDialogButton;
    private Button blurredProgressDialogButton;
    private Button blurredColorpickerDialogButton;
    private Button blurredCustomDialogButton;
    private Button seekbarDialogButton;
    private Button progressDialogButton;
    private Button customDialogButton;
    private TextView timeLabel;
    private TextView vsProgress;
    private TextView colorLabel;
    private boolean cancelable = true;
    private VerticalSeekBar verticalSeekBar = null;
    private int oldColor = Color.BLACK;
	private int progress = 50;

    public static DialogsMain newInstance() {
        DialogsMain fragment = new DialogsMain();
        return fragment;
    }

    public DialogsMain() {
    }

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
        timeDialogButton = (Button) rootView.findViewById(R.id.time_dialog_button);
        alertDialogButton = (Button) rootView.findViewById(R.id.alert_dialog_button);
        blurredAlertDialogButton = (Button) rootView.findViewById(R.id.blurred_alert_dialog_button);
        blurredProgressDialogButton = (Button) rootView.findViewById(R.id.blurred_progress_dialog_button);
        blurredColorpickerDialogButton = (Button) rootView.findViewById(R.id.blurred_colorpicker_dialog_button);
        blurredCustomDialogButton = (Button) rootView.findViewById(R.id.blurred_custom_dialog_button);
        seekbarDialogButton = (Button) rootView.findViewById(R.id.seekbar_dialog_button);
        progressDialogButton = (Button) rootView.findViewById(R.id.progress_dialog_button);
        customDialogButton = (Button) rootView.findViewById(R.id.custom_dialog_button);
        timeLabel = (TextView) rootView.findViewById(R.id.textView);
        colorLabel = (TextView) rootView.findViewById(R.id.textView4);

        timeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				ADialogs timeDialog = new ADialogs(getActivity());
                timeDialog.openTime(cancelable, getActivity().getString(R.string.title), getActivity().getString(R.string.set), getActivity().getString(R.string.cancel));
                timeDialog.setADialogsTimeListener(new ADialogs.ADialogsTimeListener() {
                    @Override
                    public void onADialogsTimePositiveClick(DialogInterface dialog, TimePicker tp, CheckBox active) {
                        Integer hour = tp.getCurrentHour();
                        Integer minute = tp.getCurrentMinute();
                        boolean activeIt = active.isChecked();
                        timeLabel.setText("Active:" + activeIt + "; Time" + hour + ":" + minute);
                        dialog.dismiss();
                    }

                    @Override
                    public void onADialogsTimeNegativeClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onADialogsTimeCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
        });
        alertDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				ADialogs alertDialog = new ADialogs(getActivity());
                alertDialog.alert(cancelable, getActivity().getString(R.string.title), getActivity().getString(R.string.message), getActivity().getString(R.string.ok), null);
            }
        });
        seekbarDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				ADialogs seekbarDialog = new ADialogs(getActivity());
                seekbarDialog.seekbar(cancelable, getActivity().getString(R.string.title), progress, getActivity().getString(R.string.set), getActivity().getString(R.string.cancel));
				seekbarDialog.setADialogsSeekBarListener(new ADialogs.ADialogsSeekBarListener() {
                    @Override
                    public void onADialogsSeekBarPositiveClick(DialogInterface dialog, SeekBar seekbar) {
						progress = seekbar.getProgress();
                        seekbarDialogButton.setText("" + progress);
                        dialog.dismiss();
                    }
                });
            }
        });
        progressDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
				ADialogs progressDialog = new ADialogs(getActivity());
                progressDialog.progress(cancelable, getActivity().getString(R.string.progress));
            }
        });
        customDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ArrayList<ImageTextCheckbox> list = new ArrayList<ImageTextCheckbox>();

                ADialogs alertDialog = new ADialogs(getActivity());
                alertDialog.customList(cancelable, getActivity().getString(R.string.title), list, getActivity().getString(R.string.ok), null);
            }
        });
        blurredAlertDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showAlertDialogFragment();
            }
        });
        blurredProgressDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showProgressDialogFragment();
            }
        });
        blurredColorpickerDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showColorpickerDialogFragment();
            }
        });
        blurredCustomDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
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
        BlurredAlertDialog newFragment = BlurredAlertDialog.newInstance(this.getString(R.string.title), this.getString(R.string.message));
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
        BlurredProgressDialog newFragment = BlurredProgressDialog.newInstance(this.getString(R.string.message), true);
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
        BlurredColorPickerDialog newFragment = BlurredColorPickerDialog.newInstance(this.getString(R.string.title), oldColor);
        newFragment.setBlurredColorPickerDialogListener(new BlurredColorPickerDialog.BlurredColorPickerDialogListener() {
            @Override
            public void onBlurredAlertDialogPositiveClick(DialogFragment dialog, int color) {
                oldColor = color;
//                String hexColor = String.format("#%08X", (0xFFFFFFFF & color));
                colorLabel.setText("" + color);
                blurredColorpickerDialogButton.setBackgroundColor(color);
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
        final BlurredCustomAlertDialog newFragment = new BlurredCustomAlertDialog();
        AlertDialog.Builder ad = newFragment.build(getActivity(), cancelable, null, null, this.getString(R.string.ok), this.getString(R.string.cancel));
        View seekLayout = newFragment.setCustomView(ad, R.layout.dialog_seekbar);
        SeekBar seekBar = (SeekBar) seekLayout.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (newFragment.set) {
                    blurredCustomDialogButton.setText("working! " + progress);
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
        newFragment.customShow(ad);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }
}