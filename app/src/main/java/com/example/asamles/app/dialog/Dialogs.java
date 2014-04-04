package com.example.asamles.app.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
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
    private Button btn, btn2, btn3;
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
        verticalSeekBar=(VerticalSeekBar)rootView.findViewById(R.id.vertical_Seekbar);
		vsProgress=(TextView)rootView.findViewById(R.id.vertical_sb_progresstext);
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
				showDialogFragment();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
	
    public void showDialogFragment1() {
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		CustomAlertDialog newFragment = new CustomAlertDialog();
		// The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, newFragment)
                   .addToBackStack(null).commit();
    }
    public void showDialogFragment() {
        DialogFragment dlg2 = new CustomSeekbarDialog();

        dlg2.show(getFragmentManager(), "dlg2");
    }

}