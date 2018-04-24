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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private LinearLayout toImg;
	private LinearLayout toLay;
	private LinearLayout toGal;
	private LinearLayout toNet;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        toImg = (LinearLayout) findViewById(R.id.toImg);
        toLay = (LinearLayout) findViewById(R.id.toLay);
        toGal = (LinearLayout) findViewById(R.id.toGal);
        toNet = (LinearLayout) findViewById(R.id.toNet);
        
        toImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ImageActivity.class);
				startActivity(intent);
			}
		});
        toLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LayoutActivity.class);
				startActivity(intent);
			}
		});
        toGal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
				startActivity(intent);
			}
		});
        toNet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, NetworkActivity.class);
				startActivity(intent);
			}
		});
        
        //SPRAWDZAMY CZY FOLDER ISTNIEJE
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(file, "TomaszPazera");
        if (!dir.exists()) {
        	dir.mkdir();
        	String a = dir.getPath();
        	File tpFolder = new File(a); 
        	String getDirectoryPath = tpFolder.getPath();
        	File miejscaDir = new File(getDirectoryPath, "miejsca");
        	miejscaDir.mkdirs();
        	File przedmiotyDir = new File(getDirectoryPath, "przedmioty");
        	przedmiotyDir.mkdirs();
        	File osobyDir = new File(getDirectoryPath, "osoby");
        	osobyDir.mkdirs();
        }
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
