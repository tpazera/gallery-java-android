package com.pazera.galery;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GalleryActivity extends Activity {
	
	private ImageView addGallery;
	private LinearLayout fileLayout;
	private LinearLayout myLayout;
	private LinearLayout row = null;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gallery);
		fileLayout = (LinearLayout) findViewById(R.id.filesLayout);
		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File dir = new File(file, "TomaszPazera");
		File[] files = dir.listFiles();
        int length = files.length;
        Log.d("L", "Wielkość " + length);
        row = new LinearLayout(this);
        fileLayout.addView(row);
		for (int i = 0; i < length; i++) {
			if (i%2 == 0) {
				row = new LinearLayout(this);
				fileLayout.addView(row);
			}
        	if (!files[i].isFile()) {
        		myLayout = new llFile(GalleryActivity.this, files[i].getName(), "folder");
        		row.addView(myLayout);
        	}
        	if (i%2 == 1 || i == length) {
        		
        	}
        }
		
		addGallery = (ImageView) findViewById(R.id.addGallery);
		addGallery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Add",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
							String folder = userInput.getText().toString();
							File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
							File dir = new File(file, "TomaszPazera");
				        	String a = dir.getPath();
				        	File tpFolder = new File(a); 
				        	String getDirectoryPath = tpFolder.getPath();
				        	File newFolder = new File(getDirectoryPath, folder);
				        	newFolder.mkdirs();
				        	finish();
				        	startActivity(getIntent());
					    }
					  })
					.setNegativeButton("Cancel",
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
		getMenuInflater().inflate(R.menu.gallery, menu);
		return true;
	}

}
