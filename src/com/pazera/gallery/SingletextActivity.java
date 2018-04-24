package com.pazera.gallery;

import java.io.IOException;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SingletextActivity extends Activity {

	private ImageView accept;
	private ImageView deny;
	private ImageView acceptColor;
	private ImageView denyColor;
	private ImageView bordercolor;
	private ImageView fillcolor;
	private EditText textEdit;
	private LinearLayout fonts;
	private RelativeLayout preview;
	private boolean modify;
	private TextView textView = null;
	private Typeface chosenFont;
	private int chosenFontInt;
	private int fillString = -123456;
	private int borderString = -423125;
	private int color = -123456;
	private String text = "Napis";
	private RelativeLayout colorRelative;
	private ImageView colorPicker;
	private String which;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_singletext);
		accept = (ImageView) findViewById(R.id.acceptSingle);
        deny = (ImageView) findViewById(R.id.denySingle);
        acceptColor = (ImageView) findViewById(R.id.acceptColor);
        denyColor = (ImageView) findViewById(R.id.denyColor);
        bordercolor = (ImageView) findViewById(R.id.bordercolor);
        fillcolor = (ImageView) findViewById(R.id.fillcolor);
		textEdit = (EditText) findViewById(R.id.textSingle);
		preview = (RelativeLayout) findViewById(R.id.previewSingle);
		colorRelative = (RelativeLayout) findViewById(R.id.colorRelative);
		colorPicker = (ImageView) findViewById(R.id.colorPicker);
		fonts = (LinearLayout) findViewById(R.id.fonts);
		AssetManager assetManager = getAssets();
		try {
			String[] fontslist = assetManager.list("fonts");
			for (int i = 0; i < fontslist.length; i++) {
				textView = new TextView(this);
				textView.setText("Hello world!");
				textView.setTextSize(35);
				textView.setTextColor(Color.WHITE);
				final int x = i;
				final Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/" + fontslist[i]);
				textView.setTypeface(tf);
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						chosenFont = tf;
						chosenFontInt = x;
						preview.removeAllViews();
						preview.addView(new PreviewText(getApplicationContext(), chosenFont, fillString, borderString, text));
					}
				});
				fonts.addView(textView);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Typeface tf2=Typeface.createFromAsset(getAssets(),"fonts/arial.ttf");
		textView.setTypeface(tf2);
		chosenFont = tf2;
		preview.removeAllViews();
		preview.addView(new PreviewText(this, chosenFont, fillString, borderString, text));

		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				text = textEdit.getText().toString();
				preview.removeAllViews();
				preview.addView(new PreviewText(getApplicationContext(), chosenFont, fillString, borderString, text));
			}
		};
		
		textEdit.addTextChangedListener(textWatcher);
		colorPicker.setDrawingCacheEnabled(true);
		colorPicker.setOnTouchListener(new View.OnTouchListener() {

		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		                
		        //wykrycie odpowiedniego eventa (down, up, move)
		    
		        switch(event.getAction()){                
		                    
		            case MotionEvent.ACTION_MOVE:
		                //pobranie Bitmapy z obrazka:
		            	
		            	Bitmap bmp = v.getDrawingCache();
		                color = bmp.getPixel((int)event.getX(), (int)event.getY());
		                
		                break;
		            case MotionEvent.ACTION_DOWN:
		                break;
		            case MotionEvent.ACTION_UP:                    
		                break;
		                
		            
		        }                
		        return true;
		    }
		});
		
		fillcolor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				which = "fill";
				colorRelative.setVisibility(View.VISIBLE);
			}
		});
		
		bordercolor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				which = "border";
				colorRelative.setVisibility(View.VISIBLE);
			}
		});
		
		acceptColor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(which == "border") {
					borderString = color;
					preview.removeAllViews();
					preview.addView(new PreviewText(getApplicationContext(), chosenFont, fillString, borderString, text));
				} else if(which == "fill") {
					fillString = color;
					preview.removeAllViews();
					preview.addView(new PreviewText(getApplicationContext(), chosenFont, fillString, borderString, text));
				}
				colorRelative.setVisibility(View.INVISIBLE);
			}
		});
		denyColor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				colorRelative.setVisibility(View.INVISIBLE);
			}
		});
		
		accept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modify = true;
				Intent intent = new Intent();
				intent.putExtra("xmodify", modify);
				intent.putExtra("xfont", chosenFontInt);
				intent.putExtra("xborder", borderString);
				intent.putExtra("xfill", fillString);
				intent.putExtra("xtext", text);
				setResult(100, intent);        
				finish();
			}
		});
		deny.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modify = false;
				Intent intent = new Intent();
				intent.putExtra("xmodify", modify);
				intent.putExtra("xfont", chosenFontInt);
				intent.putExtra("xborder", borderString);
				intent.putExtra("xfill", fillString);
				intent.putExtra("xtext", text);
				setResult(100, intent);        
				finish();
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.singletext, menu);
		return true;
	}
}
