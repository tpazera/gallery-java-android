package com.pazera.galery;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ImageActivity extends Activity {

	private LinearLayout foldersLayout;
	private LinearLayout myLayout;
	private Button startCamera;
	private EditText chosenFolder;
	private String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_image);
		chosenFolder = (EditText) findViewById(R.id.chosenFolder);
		foldersLayout = (LinearLayout) findViewById(R.id.foldersLayout);
		startCamera = (Button) findViewById(R.id.startCamera);
		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File dir = new File(file, "TomaszPazera");
		File[] files = dir.listFiles();
        int length = files.length;
		for (int i = 0; i < length; i++) {
        	if (!files[i].isFile()) {
        		name = files[i].getName();
        		myLayout = new foldersList(ImageActivity.this, files[i].getName(), "folder");
        		foldersLayout.addView(myLayout);
        		myLayout.setTag(name);
        		myLayout.setOnClickListener(new OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
        				chosenFolder.setText(v.getTag().toString());
        				
        			}
        		});
        	}
        }
		
		startCamera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ImageActivity.this, CameraActivity.class);
				intent.putExtra("name",chosenFolder.getText().toString());
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

}
