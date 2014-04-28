package com.example.asamles.app.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asamles.app.R;

public class SocialCard extends RelativeLayout {
    public TextView name;
	public TextView birthday;
	public TextView contact;
	public Button button;
	private View cardView;

    public SocialCard(Context context) {
        super(context, null);
//        init(context);
    }
	public SocialCard(Context context, AttributeSet st) {
        super(context, st);
        init(context);
    }


    private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cardView = inflater.inflate(R.layout.animal_row, this, true);
		name = (TextView) cardView.findViewById(R.id.name);
		button = (Button) cardView.findViewById(R.id.connect);
        addView(cardView);
	}
}