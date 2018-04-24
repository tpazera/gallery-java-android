package com.pazera.gallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.R.bool;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Camera2Activity extends Activity {

	Camera camera;
    byte[] fdata;
    int cameraId = -1;
    private ImageView imagePreview;
	private LinearLayout layoutPreview;
	private Bitmap bmp;
	private boolean accept = false;
    CameraPreview _cameraPreview;
    FrameLayout _frameLayout;
    ImageView cameraAccept;
    ImageView cameraDeny;
    ImageView cameraTake;
    Button camBT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //camBT = (Button) findViewById(R.id.cambt01);
        //camBT.setVisibility(View.INVISIBLE);
     
        setContentView(R.layout.activity_camera2);
        initCamera();
        initPreview();
        cameraAccept = (ImageView) findViewById(R.id.cameraAccept);
        cameraDeny = (ImageView) findViewById(R.id.cameraDeny);
        cameraTake = (ImageView) findViewById(R.id.cameraTake);
        layoutPreview = (LinearLayout) findViewById(R.id.layoutPreview);
		imagePreview = (ImageView) findViewById(R.id.imagePreview);
		cameraAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (accept) {
					accept = false;
					layoutPreview.setVisibility(View.INVISIBLE);
					Intent intent = new Intent();
					intent.putExtra("xdata", fdata);
					setResult(777, intent);        
					finish();
				}
				
				
			}
		});
		cameraTake.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accept = true;
				DisplayMetrics displayMetrics = v.getResources().getDisplayMetrics();
		        //camBT.setVisibility(View.VISIBLE);
		        camera.takePicture(null, null, camPictureCallback);
			}
		});
		cameraDeny.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (accept) {
					layoutPreview.setVisibility(View.INVISIBLE);
					accept = false;
				}
				
				
			}
		});
	}



    private Camera.PictureCallback camPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            // zapisz dane zdjęcia w tablicy typu byte[]
            // do poźniejszego wykorzystania
            // poniewaz zapis zdjęcia w galerii powinien byc dopiero po akceptacji butonem

            fdata = data;
            // odswiez kamerę (zapobiega przycięciu się kamery po zrobieniu zdjęcia)

            camera.startPreview();
            accept = true;
    		bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
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
			
			imagePreview.setImageBitmap(bmp);
    		layoutPreview.setVisibility(View.VISIBLE);
        }
    };

    public void initPreview(){
        CameraPreview cameraPreview = new CameraPreview(Camera2Activity.this, camera);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.cameraFrame);
        frameLayout.addView(cameraPreview);
    }

    public void comeback(){
        Intent intent = new Intent();
        intent.putExtra("xdata", fdata);
        setResult(100, intent);
        finish();
    }

    public void initCamera(){
        boolean cam = getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
        if (!cam) {

            Log.d("DS", "Nie ma kamery");
            // uwaga - brak kamery, przetestuj przy wyłaczonej
            // kamerze w emulatorze

        } else {

            // wykorzystanie danych zwróconych przez kolejna funkcję getCameraId

            cameraId = getCameraId();
            // jest jakaś kamera!
            if (cameraId < 0) {
                // brak kamery z przodu!
            } else if (cameraId >= 0) {
                camera = Camera.open(cameraId);
                Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		        if(display.getRotation() == Surface.ROTATION_0)
		        {                       
		            camera.setDisplayOrientation(90);
		        }
		        if(display.getRotation() == Surface.ROTATION_270)
		        {
		            camera.setDisplayOrientation(180);
		        }
            } else {
                camera = Camera.open();
                Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		        if(display.getRotation() == Surface.ROTATION_0)
		        {                       
		            camera.setDisplayOrientation(90);
		        }
		        if(display.getRotation() == Surface.ROTATION_270)
		        {
		            camera.setDisplayOrientation(180);
		        }
            }

        }
    }

    public int getCameraId(){
        int camerasCount = Camera.getNumberOfCameras(); // pobranie referencji do kamer
        int cameraid = 3;
        for (int i = 0; i < camerasCount; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            //
            // 0 - back camera
            // 1 - front camera

            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // zwróć (return) z funkcji i -> czyli aktywną kamerę
                cameraid = 0;
                break;
            }

            else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // zwróć (return) z funkcji i -> czyli aktywną kamerę
                cameraid = 1;
                break;
            }
        }
        return cameraid;
        //Log.d("DS", cameraid + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
