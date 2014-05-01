package com.example.asamles.app.card;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
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
import com.squareup.picasso.Picasso;

public class SocialCard extends RelativeLayout {
    private TextView name;
	private TextView birthday;
	private TextView contact;
	public IconButton connect;
	public IconButton share;
    private ImageView image;
	private ImageView divider;
	private LinearLayout buttonLayout;
	private int darkColor;
	private int textColor;
	private int color;
    private int buttonTextColor;
	private int profileImage;
    private Context context;

	public SocialCard(Context context, AttributeSet st) {
        super(context, st);
        this.context = context;

		TypedArray typedAttrs = context.obtainStyledAttributes(st,
        R.styleable.social_card, 0, 0);
		color = typedAttrs.getColor(R.styleable.social_card_color,
        getResources().getColor(R.color.green));
		textColor = typedAttrs.getColor(R.styleable.social_card_text_color,
        getResources().getColor(R.color.green));
		darkColor = typedAttrs.getColor(R.styleable.social_card_dark_color,
        getResources().getColor(R.color.grey_light));
		buttonTextColor = typedAttrs.getColor(R.styleable.social_card_button_text_color,
        getResources().getColor(R.color.gallery_white));
		profileImage = typedAttrs.getResourceId(R.styleable.social_card_profile_image,
        R.drawable.user);
//		typedAttrs.recycle();
        init(context);
    }

    private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.card_social, this);
		name = (TextView) findViewById(R.id.name);
        birthday = (TextView) findViewById(R.id.birthday);
        contact = (TextView) findViewById(R.id.contact);
		connect = (IconButton) findViewById(R.id.connect);
		share = (IconButton) findViewById(R.id.share);
        image = (ImageView) findViewById(R.id.image);
		divider = (ImageView) findViewById(R.id.divider);
		buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
		
		name.setTextColor(textColor);
		divider.setBackgroundColor(color);
		buttonLayout.setBackgroundColor(darkColor);
		connect.setBackgroundColor(color);
		share.setBackgroundColor(color);
		connect.setTextColor(buttonTextColor);
		share.setTextColor(buttonTextColor);
		image.setBackgroundColor(color);
		image.setImageResource(profileImage);
	}
	
	public void setName(String nameText){
		name.setText(nameText);
	}
	
	public void setBirthday(String birthdayText){
		birthday.setText(birthdayText);
	}
	
	public void setContact(String contactText){
		contact.setText(contactText);
	}
	
	public void setConnectButtonText(String connectText){
		connect.setText(connectText);
	}
	
	public void setShareButtonText(String shareText){
		contact.setText(shareText);
	}
	
	public void setImage(String uri, int placeholder, int error){
		Picasso.with(context)
            .load(uri)
            .placeholder(placeholder)
            .error(error)
            .into(image);
	}
    public void setImageResource(int imageRes){
        image.setImageResource(imageRes);
    }

}