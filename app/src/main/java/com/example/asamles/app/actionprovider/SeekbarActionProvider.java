package com.example.asamles.app.actionprovider;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.example.asamles.app.R;

import java.util.ArrayList;


public class SeekbarActionProvider extends ActionProvider {
    private Context context;
    private SizeAdapter.SizeListener listener;
	private Bitmap icon;
	private int size;

    public SeekbarActionProvider(Context context) {
        super(context);
    }

    public void setSeekbarActionProvider(Context context, SizeAdapter.SizeListener listener, Bitmap icon, int size) {
        this.listener = listener;
		this.context = context;
		this.icon = icon;
		this.size = size;
    }

    @Override
    public View onCreateActionView() {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.actionbar_spiner,
                null);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        SizeAdapter adapter = new SizeAdapter();
        spinner.setAdapter(adapter.getAdapter(context, icon, listener, size));
        return view;
    }

}