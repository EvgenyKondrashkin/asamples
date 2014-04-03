package com.example.asamles.app.share;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.R;


public class CheckBoxActionProvider extends ActionProvider implements MenuItem.OnMenuItemClickListener {
 
	/** Context for accessing resources. */
	private final Context mContext;
 
	public CheckBoxActionProvider(Context context) {
		super(context);
		mContext = context;
	}

    @Override
    public View onCreateActionView(){

        return null;
    }

    @Override
    public boolean hasSubMenu(){
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu){
        subMenu.clear();

        subMenu.add("Sort by name").setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        Toast.makeText(mContext, "I was clicked!", Toast.LENGTH_SHORT).show();
        return true;
    }
 
}