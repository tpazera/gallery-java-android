package com.pazera.galery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class Miniatures extends ImageView {
	
	public int height;
	public int width;
	public Bitmap bmp;

	public Miniatures(Context context, Bitmap bmpSend, int widthSend, int heightSend) {
		super(context);
		// TODO Auto-generated constructor stub
		height = heightSend;
		width = widthSend;
		bmp = bmpSend;
		this.setLayoutParams(new LayoutParams((int) width, height));
		this.setLayoutParams(new LayoutParams((int) width, height));
		this.setScaleType(ScaleType.CENTER_CROP);
		this.setImageBitmap(bmp);
		//LayoutParams layoutParams = new LayoutParams(widthSend, widthSend);
		//this.setLayoutParams(layoutParams);
		//this.setImageBitmap(bmp);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(9);
		paint.setColor(Color.argb(150, 255, 255, 255));
		canvas.drawRect(0, 0, width, height, paint);
	}

}
