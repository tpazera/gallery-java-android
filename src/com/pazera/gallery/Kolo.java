package com.pazera.galery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Kolo extends View{

	private Canvas canvas;
	
	public Kolo(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int dpWidth = metrics.widthPixels;
		int dpHeight = metrics.heightPixels;
    	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	paint.setAntiAlias(true);
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setStrokeWidth(8);
    	paint.setColor(Color.argb(150, 255, 255, 255));
    	canvas.drawCircle((dpWidth / 2), (dpHeight / 2), (dpWidth / 3), paint);
	}

}
