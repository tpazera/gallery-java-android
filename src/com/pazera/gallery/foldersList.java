package com.pazera.galery;

import android.content.Context;
import android.content.Intent;
import android.gesture.OrientedBoundingBox;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class foldersList extends LinearLayout {
	
	private TextView myText;
	private ImageView myImage;
	
	
	public foldersList(final Context context, final String name, final String type) {
		super(context);
		// TODO Auto-generated constructor stub
		
		myImage = new llImage(context, type);
		this.addView(myImage);
		myText = new llText(context, name);
		this.addView(myText);
		this.setOrientation(HORIZONTAL);
		this.setGravity(Gravity.CENTER_VERTICAL);
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		
	}

}
