package com.example.asamles.app.share;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.example.asamles.app.R;

import java.util.ArrayList;


public class CheckBoxActionProvider extends ActionProvider {

    /**
     * Context for accessing resources.
     */
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
    public View onCreateActionView() {

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