package com.example.asamles.app.card;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.IconButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asamles.app.R;

public class SocialCard extends RelativeLayout {
    public TextView name;
	public TextView birthday;
	public TextView contact;
	public IconButton button;
    public ImageView image;

	public SocialCard(Context context, AttributeSet st) {
        super(context, st);
        init(context);
    }

    private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.card_social, this);
		name = (TextView) findViewById(R.id.name);
        birthday = (TextView) findViewById(R.id.birthday);
        contact = (TextView) findViewById(R.id.contact);
		button = (IconButton) findViewById(R.id.connect);
        image = (ImageView) findViewById(R.id.image);

	}


}