package com.pazera.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button {

	public MyButton(Context context, String text) {
		super(context);
		this.setText(text);
		this.setBackgroundColor(0xffff00);
	}


}
