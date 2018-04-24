package com.pazera.gallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class PreviewText2 extends View {

	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	int fillcolor;
	int bordercolor;
	String text;
	int getX;
	int getY;
	
	public PreviewText2(Context context, Typeface tf, int kolor_wypelnienia, int kolor_krawedzi, String tekst, int x, int y) {
		super(context);
		// TODO Auto-generated constructor stub
		paint.reset();            // czyszczenie
		paint.setAntiAlias(true);    // wygładzanie
		paint.setTextSize(65);        // wielkośc fonta
		paint.setTypeface(tf); // czcionka
		fillcolor = kolor_wypelnienia;
		bordercolor = kolor_krawedzi;
		text = tekst;
		getX = x;
		getY = y;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(bordercolor);
		canvas.drawText(text, 5, 65, paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		paint.setColor(fillcolor);
		canvas.drawText(text, 5, 65, paint);
	}
}
