package com.pazera.galery;

import android.content.Context;
import android.content.Intent;
import android.gesture.OrientedBoundingBox;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class llFile extends LinearLayout {
	
	private TextView myText;
	private ImageView myImage;
	
	public llFile(final Context context, final String name, final String type) {
		super(context);
		// TODO Auto-generated constructor stub
		myImage = new llImage(context, type);
		this.addView(myImage);
		myText = new llText(context, name);
		this.addView(myText);
		this.setOrientation(VERTICAL);
		this.setGravity(Gravity.CENTER);
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels;
        float dpWidth = displayMetrics.widthPixels;
		this.setLayoutParams(new LayoutParams((int) (dpWidth/2), LayoutParams.WRAP_CONTENT));
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, PicturesActivity.class);
				intent.putExtra("path", name);
				intent.putExtra("type", type);
				context.startActivity(intent);
			}
		});
	}

}
