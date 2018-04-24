package com.pazera.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyColorPicker extends RelativeLayout {

	private ImageView obrazek;
	private ImageView acceptColor;
	private ImageView denyColor;
	private LinearLayout buttons;
	private int color;
	
	public MyColorPicker(final Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels;
        float dpWidth = displayMetrics.widthPixels;
		buttons = new LinearLayout(context);
		buttons.setOrientation(LinearLayout.HORIZONTAL);
		buttons.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 60));
		acceptColor = new ImageView(context);
		acceptColor.setLayoutParams(new LayoutParams((int) (dpWidth / 2), 60));
		denyColor = new ImageView(context);
		denyColor.setLayoutParams(new LayoutParams((int) (dpWidth / 2), 60));
		buttons.addView(acceptColor);
		buttons.addView(denyColor);
		obrazek = new ImageView(context);
		obrazek.setBackgroundResource(R.drawable.colorpicker);
		obrazek.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 340));
		obrazek.setDrawingCacheEnabled(true);
		obrazek.setOnTouchListener(new View.OnTouchListener() {

		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		                
		        //wykrycie odpowiedniego eventa (down, up, move)

		        switch(event.getAction()){                
		                    
		            case MotionEvent.ACTION_MOVE:
		                //pobranie Bitmapy z obrazka:
		            	Drawable myDrawable = getResources().getDrawable(R.drawable.colorpicker);
		        		Bitmap bmp = ((BitmapDrawable) myDrawable).getBitmap();
		                bmp = v.getDrawingCache();
		                color = bmp.getPixel((int)event.getX(), (int)event.getY());    
		                Toast.makeText(context, color, Toast.LENGTH_LONG).show();
		                
		                break;

		            case MotionEvent.ACTION_DOWN:
		                break;
		            case MotionEvent.ACTION_UP:                    
		                break;
		        }                

		        // ważne !!!

		        return true;
		    }
		});
		this.addView(buttons);
		this.addView(obrazek);
		
	}

}
