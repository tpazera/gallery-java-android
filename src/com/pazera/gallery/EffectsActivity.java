package com.pazera.galery;

import java.io.ByteArrayOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EffectsActivity extends Activity {
	
	static float[] normal_tab = {1.0f, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	static float[] red_tab = {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
	static float[] neg_tab = {-1, 0, 0, 1, 0, 0, -1, 0, 1, 0, 0, 0, -1, 1, 0, 0, 0, 0, 1, 0};
	static float[] blue_tab = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	static float[] green_tab = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	static float[] sea_tab = {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	static float[] yellow_tab = {2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
	private LinearLayout miniatures;
	private ImageView preview;
	private ImageView effectAccept;
	private ImageView effectDeny;
	private int use;
	private boolean modify = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_effects);
		miniatures = (LinearLayout) findViewById(R.id.miniatures);
		preview = (ImageView) findViewById(R.id.preview);
		effectAccept = (ImageView) findViewById(R.id.effectAccept);
		effectDeny = (ImageView) findViewById(R.id.effectDeny);
		byte[] bArray = getIntent().getByteArrayExtra("bArray");
		Bitmap bmp = BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
		preview.setImageBitmap(bmp);
		Bitmap b = bmp;
		for (int i = 0; i < 7; i++) {
			use = i;
			ColorMatrix cMatrix = new ColorMatrix();
			switch(i) {
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
		    ImageView miniature = new ImageView(this);
		    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(10, 10, 10, 10);
			lp.height = 140;
			lp.width = 140;
			miniature.setLayoutParams(lp);
		    miniature.setImageBitmap(b);
		    miniature.setId(i);
		    miniature.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					changeColor(v.getId());
					Toast.makeText(getApplicationContext(), "Zmiana koloru", Toast.LENGTH_SHORT).show();
				}
			});
		    miniatures.addView(miniature);
		    Log.d("DS","DZIAŁA");
		}
		effectAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modify = true;
				Intent intent = new Intent();
				Bitmap b = ((BitmapDrawable) preview.getDrawable()).getBitmap();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				b.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] bArray = stream.toByteArray();
				intent.putExtra("bArray", bArray);
				intent.putExtra("xmodify", "true");
				setResult(100, intent);        
				finish();
			}
		});
		effectDeny.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modify = false;
				Intent intent = new Intent();
				Bitmap b = ((BitmapDrawable) preview.getDrawable()).getBitmap();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				b.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] bArray = stream.toByteArray();
				intent.putExtra("bArray", bArray);
				intent.putExtra("xmodify", "false");
				setResult(100, intent);        
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.effects, menu);
		return true;
	}

	
	public void changeColor(int i) {
		byte[] bArray = getIntent().getByteArrayExtra("bArray");
		Bitmap bmp = BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
		Bitmap b = bmp;
		ColorMatrix cMatrix = new ColorMatrix();
		switch(i) {
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
		preview.setImageBitmap(b);
	}
}
