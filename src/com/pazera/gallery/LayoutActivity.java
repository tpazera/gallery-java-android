package com.pazera.galery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LayoutActivity extends Activity {

	private LinearLayout ekran1;
    private LinearLayout ekran2;
    private LinearLayout ekran3;
    private LinearLayout ekran4;
    private LinearLayout ekran5;
    private LinearLayout ekran6;
    private ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String,Integer>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_layout);
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		final int dpWidth = metrics.widthPixels;
		final int dpHeight = metrics.heightPixels - 140;
		ekran1 = (LinearLayout) findViewById(R.id.ekran1);
		ekran1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				HashMap<String,Integer> map01 = new HashMap<String, Integer>();
				map01.put("x",0);
				map01.put("y",0);
				map01.put("width",(dpWidth / 2));
				map01.put("height",dpHeight);
				HashMap<String,Integer> map02 = new HashMap<String, Integer>();
				map02.put("x",(dpWidth / 2));
				map02.put("y",0);
				map02.put("width",(dpWidth / 2));
				map02.put("height",dpHeight);
				list.add(map01);
				list.add(map02);
				Intent intent = new Intent(LayoutActivity.this, Collage.class);
				intent.putExtra("list",list);
				startActivity(intent);
			}
		});
		ekran2 = (LinearLayout) findViewById(R.id.ekran2);
		ekran2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				HashMap<String,Integer> map01 = new HashMap<String, Integer>();
				map01.put("x",0);
				map01.put("y",0);
				map01.put("width",dpWidth);
				map01.put("height",(dpHeight / 2));
				HashMap<String,Integer> map02 = new HashMap<String, Integer>();
				map02.put("x",0);
				map02.put("y",(dpHeight / 2));
				map02.put("width",dpWidth);
				map02.put("height",(dpHeight / 2));
				list.add(map01);
				list.add(map02);
				Intent intent = new Intent(LayoutActivity.this, Collage.class);
				intent.putExtra("list",list);
				startActivity(intent);
			}
		});
		ekran5 = (LinearLayout) findViewById(R.id.ekran5);
		ekran5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				HashMap<String,Integer> map01 = new HashMap<String, Integer>();
				map01.put("x",0);
				map01.put("y",0);
				map01.put("width",(int) (dpWidth / 3));
				map01.put("height",(int) (dpHeight / 3));
				HashMap<String,Integer> map02 = new HashMap<String, Integer>();
				map02.put("x",(int) (dpWidth / 3));
				map02.put("y",0);
				map02.put("width",(int) (dpWidth / 3));
				map02.put("height",(int) (dpHeight / 3));
				HashMap<String,Integer> map03 = new HashMap<String, Integer>();
				map03.put("x",((int) (dpWidth / 3)) * 2);
				map03.put("y",0);
				map03.put("width",(int) (dpWidth / 3));
				map03.put("height",(int) (dpHeight / 3));
				HashMap<String,Integer> map04 = new HashMap<String, Integer>();
				map04.put("x",0);
				map04.put("y",(int) (dpHeight / 3));
				map04.put("width",dpWidth);
				map04.put("height",(int) (dpHeight / 1.5));
				list.add(map01);
				list.add(map02);
				list.add(map03);
				list.add(map04);
				Intent intent = new Intent(LayoutActivity.this, Collage.class);
				intent.putExtra("list",list);
				startActivity(intent);
			}
		});
		ekran4 = (LinearLayout) findViewById(R.id.ekran4);
		ekran4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				HashMap<String,Integer> map01 = new HashMap<String, Integer>();
				map01.put("x",0);
				map01.put("y",0);
				map01.put("width",(dpWidth / 2));
				map01.put("height",dpHeight);
				HashMap<String,Integer> map02 = new HashMap<String, Integer>();
				map02.put("x",(dpWidth / 2));
				map02.put("y",0);
				map02.put("width",(dpWidth / 2));
				map02.put("height",(int) (dpHeight / 3));
				HashMap<String,Integer> map03 = new HashMap<String, Integer>();
				map03.put("x",(dpWidth / 2));
				map03.put("y",(int) (dpHeight / 3));
				map03.put("width",(dpWidth / 2));
				map03.put("height",(int) (dpHeight / 3));
				HashMap<String,Integer> map04 = new HashMap<String, Integer>();
				map04.put("x",(dpWidth / 2));
				map04.put("y",(int) ((dpHeight / 3)*2));
				map04.put("width",(dpWidth / 2));
				map04.put("height",(int) (dpHeight / 3));
				list.add(map01);
				list.add(map02);
				list.add(map03);
				list.add(map04);
				Intent intent = new Intent(LayoutActivity.this, Collage.class);
				intent.putExtra("list",list);
				startActivity(intent);
			}
		});
		ekran3 = (LinearLayout) findViewById(R.id.ekran3);
		ekran3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				HashMap<String,Integer> map01 = new HashMap<String, Integer>();
				map01.put("x",0);
				map01.put("y",0);
				map01.put("width",(dpWidth / 2));
				map01.put("height",(int) (dpHeight / 1.5));
				HashMap<String,Integer> map02 = new HashMap<String, Integer>();
				map02.put("x",(dpWidth / 2));
				map02.put("y",0);
				map02.put("width",(dpWidth / 2));
				map02.put("height",(int) (dpHeight / 1.5));
				HashMap<String,Integer> map03 = new HashMap<String, Integer>();
				map03.put("x",0);
				map03.put("y",(int) (dpHeight / 1.5));
				map03.put("width",dpWidth);
				map03.put("height",(int) (dpHeight / 3));
				list.add(map01);
				list.add(map02);
				list.add(map03);
				Intent intent = new Intent(LayoutActivity.this, Collage.class);
				intent.putExtra("list",list);
				startActivity(intent);
			}
		});
		ekran6 = (LinearLayout) findViewById(R.id.ekran6);
		ekran6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				list.clear();
				HashMap<String,Integer> map01 = new HashMap<String, Integer>();
				map01.put("x",0);
				map01.put("y",0);
				map01.put("width",(int) (dpWidth / 1.5));
				map01.put("height",(int) (dpHeight / 1.33));
				HashMap<String,Integer> map02 = new HashMap<String, Integer>();
				map02.put("x",(int) (dpWidth / 1.5));
				map02.put("y",0);
				map02.put("width",(int) (dpWidth / 3));
				map02.put("height",(int) (dpHeight / 4));
				HashMap<String,Integer> map03 = new HashMap<String, Integer>();
				map03.put("x",(int) (dpWidth / 1.5));
				map03.put("y",(int) (dpHeight / 4));
				map03.put("width",(int) (dpWidth / 3));
				map03.put("height",(int) (dpHeight / 4));
				HashMap<String,Integer> map04 = new HashMap<String, Integer>();
				map04.put("x",(int) (dpWidth / 1.5));
				map04.put("y",(int) (dpHeight / 2));
				map04.put("width",(int) (dpWidth / 3));
				map04.put("height",(int) (dpHeight / 4));
				HashMap<String,Integer> map05 = new HashMap<String, Integer>();
				map05.put("x",0);
				map05.put("y",(int) (dpHeight / 1.33));
				map05.put("width",(int) (dpWidth / 3));
				map05.put("height",(int) (dpHeight / 4));
				HashMap<String,Integer> map06 = new HashMap<String, Integer>();
				map06.put("x",(int) (dpWidth / 3));
				map06.put("y",(int) (dpHeight / 1.33));
				map06.put("width",(int) (dpWidth / 3));
				map06.put("height",(int) (dpHeight / 4));
				HashMap<String,Integer> map07 = new HashMap<String, Integer>();
				map07.put("x",(int) (dpWidth / 3) * 2);
				map07.put("y",(int) (dpHeight / 1.33));
				map07.put("width",(int) (dpWidth / 3));
				map07.put("height",(int) (dpHeight / 4));
				list.add(map01);
				list.add(map02);
				list.add(map03);
				list.add(map04);
				list.add(map05);
				list.add(map06);
				list.add(map07);
				Intent intent = new Intent(LayoutActivity.this, Collage.class);
				intent.putExtra("list",list);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.layout, menu);
		return true;
	}

}
