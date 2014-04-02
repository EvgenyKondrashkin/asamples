package com.example.asamles.app.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
 
import com.actionbarsherlock.view.ActionProvider;
import com.psrivastava.deviceframegenerator.R;
 
public class CheckBoxActionProvider extends ActionProvider implements
		OnCheckedChangeListener {
 
	/** Context for accessing resources. */
	private final Context mContext;
 
	public CheckBoxActionProvider(Context context) {
		super(context);
		mContext = context;
	}
 
	@Override
	public View onCreateActionView() {
		// Inflate the action view to be shown on the action bar.
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		View view = layoutInflater.inflate(R.layout.checkbox_action_provider,
				null);
		TextView textview = (TextView) view.findViewById(R.id.text);
		textview.setText(getActionText());
 
		CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox);
		checkbox.setOnCheckedChangeListener(this);
 
		return view;
	}
 
	public String getActionText() {
		return "Checkbox";
	}
 
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkbox:
			Toast.makeText(mContext,
					isChecked ? "checked" : "unchecked",
					Toast.LENGTH_SHORT).show();
		}
 
	}
 
}