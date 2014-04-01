package com.example.asamles.app.paint;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PaintMain extends Fragment {
    private SmoothLine drawView;

	public static PaintMain newInstance() {
        PaintMain fragment = new PaintMain();
        return fragment;
    }

    public PaintMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
							 
		drawView = new SmoothLine(this, null);
        drawView.requestFocus();
		
        return drawView;
    }
}