package com.pazera.galery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

public class ImageEdition extends View {
	
	private Bitmap bmp;
	private String what;
	private String color;
	static float[] normal_tab = {1.0f, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	static float[] red_tab = {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
	static float[] neg_tab = {-1, 0, 0, 1, 0, 0, -1, 0, 1, 0, 0, 0, -1, 1, 0, 0, 0, 0, 1, 0};
	static float[] blue_tab = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	static float[] green_tab = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	static float[] sea_tab = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0};
	static float[] yellow_tab = {2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	


	public ImageEdition(Context context, Bitmap obrazek, String co, String kolor) {
		super(context);
		// TODO Auto-generated constructor stub
		bmp = obrazek;
		what = co;
		color = kolor;
		changeSomething(what, bmp, color, 0);
	}
	
	public static Bitmap changeSomething(String what, Bitmap bmp, String color, int z) {        
	    Bitmap b = bmp;
	    if (what == "cycle") {
	    	Matrix matrix = new Matrix();
	    	matrix.postRotate(90);
	    	b = Bitmap.createBitmap(b, 0, 0,b.getWidth(), b.getHeight(),matrix, true);
	    } else if (what == "mirror") {
	    	
	    	Matrix matrix = new Matrix();
	    	if (z == 0) {
	    		matrix.postScale(-1.0f, 1.0f);
	    	} else if (z == 1) {
	    		matrix.postScale(-1.0f, -1.0f);
	    	} else if (z == 2) {
	    		matrix.postScale(-1.0f, 1.0f);
	    	} else if (z == 3) {
	    		matrix.postScale(-1.0f, -1.0f);
	    	}
	    	b = Bitmap.createBitmap(b, 0, 0,b.getWidth(), b.getHeight(),matrix, true);
	    }
	    return b;
	}
	
	public static Bitmap changeSaturation(Bitmap bmp, float z) {        
	    Bitmap b = bmp;
	    ColorMatrix cMatrix = new ColorMatrix();
	    cMatrix.setSaturation(z);
	    Paint paint = new Paint();
		b = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
		Canvas canvas = new Canvas(b);
		canvas.drawBitmap(bmp, 0, 0, paint);
	    return b;
	}
	
	public static Bitmap changeContrast(Bitmap bmp, float z) {        
		Bitmap b = bmp;
		ColorMatrix cMatrix = new ColorMatrix();
		float[] con_tab = { z, 0, 0, 0, 0, 0, z, 0, 0, 0, 0, 0, z, 0, 0, 0, 0, 0, 1, 0 };
		cMatrix.set(con_tab);
		Paint paint = new Paint();
		b = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
		Canvas canvas = new Canvas(b);
		canvas.drawBitmap(bmp, 0, 0, paint);
	    return b;
	}
	
	public static Bitmap changeBrightness(Bitmap bmp, float z) {        
		Bitmap b = bmp;
		ColorMatrix cMatrix = new ColorMatrix();
		float[] br_tab = { 1, 0, 0, 0, z, 0, 1, 0, 0, z, 0, 0, 1, 0, z, 0, 0, 0, 1, 0 };
		cMatrix.set(br_tab);
		Paint paint = new Paint();
		b = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
		Canvas canvas = new Canvas(b);
		canvas.drawBitmap(bmp, 0, 0, paint);
	    return b;
	}
	
	public static Bitmap changeColor(Bitmap bmp, int color) {
		Bitmap b = bmp;
		ColorMatrix cMatrix = new ColorMatrix();
		switch(color) {
		case 0:
			cMatrix.set(normal_tab);
			break;
		case 1:
			cMatrix.set(red_tab);
			break;
		case 2:
			cMatrix.set(neg_tab);
			break;
		case 3:
			cMatrix.set(blue_tab);
			break;
		case 4:
			cMatrix.set(green_tab);
			break;
		case 5:
			cMatrix.set(sea_tab);
			break;
		case 6:
			cMatrix.set(yellow_tab);
			break;
		default:
			cMatrix.set(normal_tab);
			break;
		}
		Paint paint = new Paint();
		b = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
		Canvas canvas = new Canvas(b);
		canvas.drawBitmap(bmp, 0, 0, paint);
	    return b;
	}

}
