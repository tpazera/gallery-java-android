package com.pazera.galery;

import android.content.Context;
import android.widget.ImageView;

public class llImage extends ImageView {
	public llImage(Context context, String type) {
		super(context);
		// TODO Auto-generated constructor stub
		if (type == "file") {
			this.setImageResource(R.drawable.file);
		} else {
			this.setImageResource(R.drawable.folder);
		}
		this.setPadding(10, 10, 10, 10);
	}
}
