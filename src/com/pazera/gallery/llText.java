package com.pazera.galery;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class llText extends TextView {	
	public llText(Context context, String name) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setText(name);
		this.setTextColor(Color.parseColor("#ffffff"));
		this.setGravity(Gravity.CENTER | Gravity.VERTICAL_GRAVITY_MASK);
	}

}
