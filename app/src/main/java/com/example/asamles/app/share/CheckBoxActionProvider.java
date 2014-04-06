package com.example.asamles.app.share;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.R;

import java.util.ArrayList;


public class CheckBoxActionProvider extends ActionProvider {
 
	/** Context for accessing resources. */
	private Context mContext;
    private SeekbarAdapter.SeekBarListener mListener;
	public CheckBoxActionProvider(Context context) {
		super(context);
//		mContext = context;
	}
    public void setCheckBoxActionProvider(Context context, SeekbarAdapter.SeekBarListener mListener) {
        mContext = context;
        this.mListener = mListener;

    }
    @Override
    public View onCreateActionView(){

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.actionbar_spiner,
                null);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        SeekbarAdapter adapter = new SeekbarAdapter();
        ArrayList<String> list = new ArrayList<String>();
        list.add("bar 0 ");
        spinner.setAdapter(adapter.getAdapter(mContext, list, "WAT", mListener));

        return view;
    }
 
}