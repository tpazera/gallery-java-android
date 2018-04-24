package com.pazera.gallery;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class PicturesActivity extends Activity {

	private LinearLayout pictureLayout;
	private ImageView myLayout;
	private LinearLayout row = null;
	final Context context = this;
	private ImageView addImage;
	private ImageView deleteGallery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pictures);
		Bundle bundle = getIntent().getExtras();
		String type = bundle.getString("type").toString();
		final String path = bundle.getString("path").toString();
		pictureLayout = (LinearLayout) findViewById(R.id.picturesLayout);
		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File dir = new File(file, "/TomaszPazera/" + path);
		File[] files = dir.listFiles();
        int length = files.length;
        Log.d("L", "Wielkość " + length);
        row = new LinearLayout(this);
        pictureLayout.addView(row);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels;
        float dpWidth = displayMetrics.widthPixels;
        int z = 0;
		for (int i = 0; i < length; i++) {
			if (i%2 == 0) {
				row = new LinearLayout(this);
				pictureLayout.addView(row);
			}
        	if (files[i].isFile()) {
        		myLayout = new llPicture(PicturesActivity.this);
        		myLayout.setScaleType(ScaleType.CENTER_CROP);
        		if (z == 0) {
        			myLayout.setLayoutParams(new LayoutParams((int) (dpWidth * 0.333), 400));
        	    	z = 1;
        	    } else if (z == 1){
        	    	myLayout.setLayoutParams(new LayoutParams((int) (dpWidth * 0.666), 400));
        	    	z = 2;
        	    } else if (z == 2) {
        	    	myLayout.setLayoutParams(new LayoutParams((int) (dpWidth * 0.666), 400));
        	    	z = 3;
        	    } else if (z == 3) {
        	    	myLayout.setLayoutParams(new LayoutParams((int) (dpWidth * 0.333), 400));
        	    	z = 0;
        	    }
        		String filePath = files[i].getAbsolutePath();
        	    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        	    final String pathFile = files[i].getAbsolutePath();
        	    myLayout.setImageBitmap(bitmap);
        	    myLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(PicturesActivity.this, SingleActivity.class);
						intent.putExtra("path", pathFile);
						startActivity(intent);
					}
				});
        		row.addView(myLayout);
        	}
        }
		
		addImage = (ImageView) findViewById(R.id.addImage);
		addImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PicturesActivity.this, CameraActivity.class);
				intent.putExtra("name",path);
				startActivity(intent);
			}
		});
		
		deleteGallery = (ImageView) findViewById(R.id.deleteGallery);
		deleteGallery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.yon, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				alertDialogBuilder.setView(promptsView);

				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Yes",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
							File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
							File dir = new File(file, "TomaszPazera");
							File toDelete = new File(dir, path); 
							if (toDelete.isDirectory()) 
							{
							    String[] children = toDelete.list();
							    for (int i = 0; i < children.length; i++)
							    {
							       new File(toDelete, children[i]).delete();
							    }
							    toDelete.delete();
							}
							Intent intent = new Intent(PicturesActivity.this, MainActivity.class);
							startActivity(intent);
					    }
					  })
					.setNegativeButton("No",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pictures, menu);
		return true;
	}

}
