package com.pazera.galery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class Collage extends Activity {

	private RelativeLayout main;
	private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
	private ImageView clicked;
	private ImageView collageAccept;
	private ImageView collageColor;
	private ImageView collageMirror;
	private ImageView collageCycle;
	final CharSequence options[] = new CharSequence[] {"Kamera", "Anuluj"};
	private int z = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_collage);
		collageAccept = (ImageView) findViewById(R.id.collageAccept);
		collageColor = (ImageView) findViewById(R.id.collageColor);
		collageMirror = (ImageView) findViewById(R.id.collageMirror);
		collageCycle = (ImageView) findViewById(R.id.collageCycle);
		final Context thisC = getApplicationContext();
		
		ArrayList<HashMap<String, Integer>> list = (ArrayList<HashMap<String, Integer>>) getIntent().getSerializableExtra("list");
		main = (RelativeLayout) findViewById(R.id.here);
		for (int i = 0; i < list.size(); i++) {
			ImageView image = new ImageView(this);
			
			float x = list.get(i).get("x");
			float y = list.get(i).get("y");
			int width = list.get(i).get("width");
			int height = list.get(i).get("height");
			Log.d("DS", ""+list.size()+" "+i+" Height "+height+" Width "+width+" x "+x+" y "+y);
			image.setScaleType(ScaleType.CENTER);
			image.setX(x);
			image.setY(y);
			image.setLayoutParams(new LayoutParams(width, height));
			image.setImageResource(R.drawable.take);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					clicked = (ImageView) v;
					
					AlertDialog.Builder builder = new AlertDialog.Builder(Collage.this);
				    builder.setTitle("Co chcesz zrobić?");
				    builder.setItems(new CharSequence[]
				            {"Kamera", "Anuluj"},
				            new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int which) {
				                    // The 'which' argument contains the index position
				                    // of the selected item
				                    switch (which) {
				                        case 0:
				                            openCamera();
				                            break;
				                    }
				                }
				            });
				    builder.create().show();
					
				}
			});
			clicked = image;
			main.addView(image);
			
			collageCycle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bitmap bmp = ((BitmapDrawable) clicked.getDrawable()).getBitmap();
					Bitmap b = ImageEdition.changeSomething("cycle", bmp, "#ffffff", 0);
					clicked.setImageBitmap(b);
				}
			});
			
			collageMirror.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bitmap bmp = ((BitmapDrawable) clicked.getDrawable()).getBitmap();
					Bitmap b = ImageEdition.changeSomething("mirror", bmp, "#ffffff", z);
					z++;
					if (z == 3) {
						z = 0;
					}
					clicked.setImageBitmap(b);
				}
			});
			
			collageColor.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Collage.this, EffectsActivity.class);
					//clicked.setDrawingCacheEnabled(true);
					//clicked.buildDrawingCache();
					//Bitmap b = clicked.getDrawingCache();
					Bitmap b = ((BitmapDrawable) clicked.getDrawable()).getBitmap();
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					b.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] bArray = stream.toByteArray();
					intent.putExtra("bArray", bArray);
					startActivityForResult(intent, 444);
				}
			});
			
			collageAccept.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					main.setDrawingCacheEnabled(true);
					main.buildDrawingCache();
					Bitmap bmp = main.getDrawingCache();
		    		Random r = new Random();
					int il = (r.nextInt(999-100) + 100);
					SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String d = dFormat.format(new Date());
					String fileName = d + "_" + il;
					File fileFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			        File dir = new File(fileFolder, "TomaszPazera");
			        dir.mkdir();
		        	String a = dir.getPath();
		        	File tpFolder = new File(a); 
		        	String getDirectoryPath = tpFolder.getPath();
		        	File folderDir = new File(getDirectoryPath, "collage");
		        	folderDir.mkdirs();
					File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
					File myFoto = new File(file, "/TomaszPazera/collage/" + fileName + ".jpg");
					FileOutputStream fs = null;
					try {
						fs = new FileOutputStream(myFoto);
					    bmp.compress(Bitmap.CompressFormat.JPEG, 100, fs); // bmp is your Bitmap instance
					    // PNG is a lossless format, the compression factor (100) is ignored
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    try {
					        if (fs != null) {
					        	fs.close();
					        }
					    } catch (IOException e) {
					        e.printStackTrace();
					    }
					}
					Toast.makeText(getApplicationContext(), "Zapisano w folderze collage", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
	public void openCamera() {
		Intent intent = new Intent(Collage.this, Camera2Activity.class);
		//startActivity(intent);
		startActivityForResult(intent, 100);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collage, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 777) {
			Bundle extras = data.getExtras();
			byte[] xdata = (byte[]) extras.get("xdata");
			Bitmap bmp = BitmapFactory.decodeByteArray(xdata, 0, xdata.length);
			Bitmap mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
			Matrix matrix = new Matrix();
		    Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
			if(display.getRotation() == Surface.ROTATION_0)
	        {                       
				matrix.postRotate(90);
	        } 
			else if(display.getRotation() == Surface.ROTATION_270)
	        {
	        	matrix.postRotate(180);
	        } 
			else {
	        	matrix.postRotate(90);
	        }
			bmp = Bitmap.createBitmap(mutableBitmap, 0, 0, mutableBitmap.getWidth(),mutableBitmap.getHeight(), matrix, true);
			clicked.setImageBitmap(bmp);
			clicked.setScaleType(ScaleType.CENTER_CROP);
		} else {
			Bundle extras = data.getExtras();
			String xmodify = extras.getString("xmodify");
			byte[] bArray = (byte[]) extras.get("bArray");
			//if (xmodify == "true") {
				Bitmap bmp = BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
				clicked.setImageBitmap(bmp);
			//}
			
		}
		
	}

}
