package com.pazera.gallery;

import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sun.org.apache.xpath.internal.operations.Minus;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;

public class CameraActivity extends Activity {

	private ImageView imagePreview;
	private LinearLayout layoutPreview;
	private Camera camera;
	private int cameraId = -1;
	private int photosCount = 0;
	private CameraPreview cameraPreview;
	private FrameLayout frameLayout;
	private FrameLayout pictureFrame;
	private String folder = "przedmioty";
    private ImageView cameraTake;
    private ImageView cameraAccept;
    private ImageView cameraDeny;
    private ImageView cameraImg;
    private ImageView cameraFlash;
    private ImageView cameraBright;
    private ImageView cameraSize;
    private byte[] fdata;
    private Bitmap bmp;
    private Boolean setBackground = false;
    final Context context = this;
    private ArrayList<String> flashList = new ArrayList<String>();
    private ArrayList<String> brightList = new ArrayList<String>();
    private ArrayList<Camera.Size> sizeList = new ArrayList<Camera.Size>();
    private ArrayList<byte[]> photos = new ArrayList<byte[]>();
    private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
    private Camera.Parameters camParams; 
    private Canvas canvas;
    private int childSize;
    public int radius = 200;
    public float angle = 0;
    public float step = 0;
  

    private PictureCallback camPictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            fdata = data;
            if (photosCount == 0) {
            	pictureFrame.addView(new Kolo(context)) ;
            }
            deleteChilds();
            photos.add(data);
            Log.d("DS", ""+photos.size());
            angle = 0;
            step = (float) ((2*Math.PI) / photos.size());
            for (int i = 0; i < photos.size(); i++) {
            	bmp = BitmapFactory.decodeByteArray(photos.get(i), 0, photos.get(i).length);
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
    			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        		int dpWidth = metrics.widthPixels;
        		int dpHeight = metrics.heightPixels;
        		Bitmap miniature = Bitmap.createScaledBitmap(bmp, 100, 100, false);
        		ImageView image = new Miniatures(context, miniature, (dpWidth / 5), (dpWidth / 5));	
        		pictureFrame.addView(image);
        		imageViews.add(image);
        	    image.setId(i);
            	float x = (float) Math.round(dpWidth/2 + radius * Math.cos(angle) - ((dpWidth / 5)/2));
            	float y = (float) Math.round(dpHeight/2 + radius * Math.sin(angle) - ((dpHeight / 5)/2)+50);
        	    angle += step;
        	    image.setX(x);
        	    image.setY(y);
        	    final CharSequence options[] = new CharSequence[] {"Podgląd", "Usuń bieżące", "Usuń wszystkie", "Zapisz bieżące", "Zapisz wszystkie"};
        	    image.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(final View v) {
						//final String tag = (String) v.getTag();
						//final int getTag = Integer.parseInt(tag);
						
						AlertDialog.Builder alert = new AlertDialog.Builder(context);
						alert.setTitle("Options:");
						alert.setItems(options, new DialogInterface.OnClickListener() {                
						    public void onClick(DialogInterface dialog, int which) { 
						    	Log.d("DS", ""+v.getId());
						    	if (which == 0) {
						    		setBackground = true;
						    		bmp = BitmapFactory.decodeByteArray(photos.get(v.getId()), 0, photos.get(v.getId()).length);
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
						    	} else if (which == 1) {
						    		Log.d("DS", "Usuwam "+v.getId());
						    		//pictureFrame.removeViewAt(v.getId()+1);
						    		photos.remove(v.getId());
						    		Log.d("DS", "Działa "+photos.size());
						    		deleteChilds();
						    		refreshPictures();
						    	} else if (which == 2) {
						    		/*Log.d("DS", "Działa "+photos.size());
						    		for (int i = 0; i < photos.size() + 1; i++) {
						    			photos.remove(i);
						    			Log.d("DS", "Usuwam "+i+"Pozostało: "+photos.size());
						    		}
						    		Log.d("DS", "Działa "+photos.size());*/
						    		photos.clear();
						    		deleteChilds();
						    		refreshPictures();
						    	} else if (which == 3) {
						    		bmp = BitmapFactory.decodeByteArray(photos.get(v.getId()), 0, photos.get(v.getId()).length);
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
						        	File folderDir = new File(getDirectoryPath, folder);
						        	folderDir.mkdirs();
									File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
									File myFoto = new File(file, "/TomaszPazera/" + folder + "/" + fileName + ".jpg");
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
						    	} else if (which == 4) {
						    		for (int i = 0; i < photos.size(); i++) {
						    			bmp = BitmapFactory.decodeByteArray(photos.get(i), 0, photos.get(i).length);
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
							        	File folderDir = new File(getDirectoryPath, folder);
							        	folderDir.mkdirs();
										File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
										File myFoto = new File(file, "/TomaszPazera/" + folder + "/" + fileName + ".jpg");
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
						    		}
						    	}
						    }
						});
						alert.show();
						return false;
					}
				});
        	    
            }
            camera.startPreview();
    		
        }
    };
    
    public void deleteChilds(){
        boolean doBreak = false;
        while (!doBreak) {
            int childCount = pictureFrame.getChildCount();
            int i;
            for(i=0; i<childCount; i++) {
                View currentChild = pictureFrame.getChildAt(i);
                // Change ImageView with your disired type view
                if (currentChild instanceof ImageView) {
                	pictureFrame.removeView(currentChild);
                    break;
                }
            }
            if (i == childCount) {
                doBreak = true;
            }
        }
    }
    
    public void refreshPictures(){
    	angle = 0;
        step = (float) ((2*Math.PI) / photos.size());
    	for (int i = 0; i < photos.size(); i++) {
        	bmp = BitmapFactory.decodeByteArray(photos.get(i), 0, photos.get(i).length);
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
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    		int dpWidth = metrics.widthPixels;
    		int dpHeight = metrics.heightPixels;
    		Bitmap miniature = Bitmap.createBitmap(bmp, 0, 0, (dpWidth / 5), (dpWidth / 5));
    		ImageView image = new Miniatures(context, bmp, (dpWidth / 5), (dpWidth / 5));
    		pictureFrame.addView(image);
    		int step = (int) ((2*Math.PI) / photos.size());
    		int x = (int) Math.round(dpWidth/2 + radius * Math.cos(angle) - ((dpWidth / 5)/2));
    	    int y = (int) Math.round(dpHeight/2 + radius * Math.sin(angle) - ((dpHeight / 5)/2)+50);
    	    angle += step;
    	    image.setX(x);
    	    image.setY(y);
    	    image.setId(i);
    	    final CharSequence options[] = new CharSequence[] {"Podgląd", "Usuń bieżące", "Usuń wszystkie", "Zapisz bieżące", "Zapisz wszystkie"};
    	    image.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(final View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(context);
					alert.setTitle("Options:");
					alert.setItems(options, new DialogInterface.OnClickListener() {                
					    public void onClick(DialogInterface dialog, int which) { 
					    	if (which == 0) {
					    		setBackground = true;
					    		bmp = BitmapFactory.decodeByteArray(photos.get(v.getId()), 0, photos.get(v.getId()).length);
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
					    	} else if (which == 1) {
					    		Log.d("DS", "Usuwam "+v.getId());
					    		//pictureFrame.removeViewAt(v.getId()+1);
					    		photos.remove(v.getId());
					    		Log.d("DS", "Działa "+photos.size());
					    		deleteChilds();
					    		refreshPictures();
					    	} else if (which == 2) {
					    		Log.d("DS", "Działa "+photos.size());
					    		/*for (int i = 0; i < photos.size() + 1; i++) {
					    			photos.remove(i);
					    			Log.d("DS", "Usuwam "+i+"Pozostało: "+photos.size());
					    		}*/
					    		photos.clear();
					    		Log.d("DS", "Działa "+photos.size());
					    		deleteChilds();
					    		refreshPictures();
					    	} else if (which == 3) {
					    		bmp = BitmapFactory.decodeByteArray(photos.get((Integer) v.getTag()), 0, photos.get((Integer) v.getTag()).length);
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
					        	File folderDir = new File(getDirectoryPath, folder);
					        	folderDir.mkdirs();
								File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
								File myFoto = new File(file, "/TomaszPazera/" + folder + "/" + fileName + ".jpg");
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
					    	} else if (which == 4) {
					    		for (int i = 0; i < photos.size(); i++) {
					    			bmp = BitmapFactory.decodeByteArray(photos.get(i), 0, photos.get(i).length);
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
						        	File folderDir = new File(getDirectoryPath, folder);
						        	folderDir.mkdirs();
									File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
									File myFoto = new File(file, "/TomaszPazera/" + folder + "/" + fileName + ".jpg");
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
					    		}
					    	}
					    }
					});
					alert.show();
					return false;
				}
			});}
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_camera);		
		boolean cam = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
		if (!cam) {
			
		} else {
			int camerasCount = Camera.getNumberOfCameras(); // pobranie referencji do kamer
			for (int i = 0; i < camerasCount; i++) {
			    CameraInfo cameraInfo = new CameraInfo();
			    Camera.getCameraInfo(i, cameraInfo);
	            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
	            	cameraId = i;
	            	break;
	            }
	            else if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
	            	cameraId = i;
	            	break;
	            }
			}
		     if (cameraId < 0) {
		         Log.d("C", "Brak kamery");
		     } else if (cameraId >= 0) {
		         camera = Camera.open(cameraId);
		     } else {
		         camera = Camera.open();
		     }
		     Parameters parameters = camera.getParameters();
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
		cameraPreview = new CameraPreview(CameraActivity.this, camera);
		frameLayout = (FrameLayout) findViewById(R.id.cameraFrame);
		pictureFrame = (FrameLayout) findViewById(R.id.pictureFrame);
		frameLayout.addView(cameraPreview);
		layoutPreview = (LinearLayout) findViewById(R.id.layoutPreview);
		imagePreview = (ImageView) findViewById(R.id.imagePreview);
		
		cameraTake = (ImageView) findViewById(R.id.cameraTake);
		cameraTake.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
				
				camera.takePicture(null, null, camPictureCallback);
			}
			
		});
		Bundle extras = getIntent().getExtras(); 
		folder = extras.getString("name").toString();
		cameraAccept = (ImageView) findViewById(R.id.cameraAccept);
		cameraAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
	        	File folderDir = new File(getDirectoryPath, folder);
	        	folderDir.mkdirs();
				File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				File myFoto = new File(file, "/TomaszPazera/" + folder + "/" + fileName + ".jpg");
				FileOutputStream fs = null;
				Toast.makeText(getApplicationContext(), folder, Toast.LENGTH_SHORT).show();

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
					layoutPreview.setVisibility(View.INVISIBLE);
					setBackground = false;
				
			
			}
		});
		cameraDeny = (ImageView) findViewById(R.id.cameraDeny);
		cameraDeny.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutPreview.setVisibility(View.INVISIBLE);
				setBackground = false;
			}
		});
		cameraImg = (ImageView) findViewById(R.id.cameraImg);
		cameraImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (setBackground) {
					WallpaperManager wpManager = WallpaperManager.getInstance(context);
					try {
						wpManager.setBitmap(bmp);
						Toast.makeText(getApplicationContext(), "Wallpaper was changed", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Take a picture first", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		camParams = camera.getParameters();
		cameraFlash = (ImageView) findViewById(R.id.cameraFlash);
		Integer minCompensation = camParams.getMinExposureCompensation();
		Integer maxCompensation = camParams.getMaxExposureCompensation();
		do {
			flashList.add(minCompensation.toString());                                                 // dodanie do listy    
			minCompensation += 1;
		} while (minCompensation <= maxCompensation);
		final String[] flashArray = new String[flashList.size()];
		for (int i = 0; i < flashArray.length; i++) {
			flashArray[i] = flashList.get(i);
		}
		cameraFlash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Exposition:");
				alert.setItems(flashArray, new DialogInterface.OnClickListener() {                
				    public void onClick(DialogInterface dialog, int which) { 
				    	int value = Integer.parseInt(flashArray[which]);
				    	camParams.setExposureCompensation(value);
				    	camera.setParameters(camParams);
				    	Toast.makeText(getApplicationContext(), "Exposition was changed", Toast.LENGTH_SHORT).show();
				    }
				});
				alert.show();
				
			}
		});
		cameraBright = (ImageView) findViewById(R.id.cameraBright);
		final String[] brightArray = new String[camParams.getSupportedWhiteBalance().size()];
		for (int i = 0; i < brightArray.length; i++) {
			brightArray[i] = camParams.getSupportedWhiteBalance().get(i);
		}
		cameraBright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("White balance:");
				alert.setItems(brightArray, new DialogInterface.OnClickListener() {                
				    public void onClick(DialogInterface dialog, int which) { 
				    	String value = brightArray[which].toString();
				    	camParams.setWhiteBalance(value);
				    	camera.setParameters(camParams);
				    	Toast.makeText(getApplicationContext(), "White balance was changed", Toast.LENGTH_SHORT).show();
				    }
				});
				alert.show();
			}
		}); 
		cameraSize = (ImageView) findViewById(R.id.cameraSize);
		sizeList = (ArrayList<Size>) camParams.getSupportedPictureSizes();
		final Integer[] widthArray = new Integer[camParams.getSupportedPictureSizes().size()];
		final Integer[] heightArray = new Integer[camParams.getSupportedPictureSizes().size()];
		final String[] resolutionArray = new String[camParams.getSupportedPictureSizes().size()];
		Camera.Size result = null;
		    for (int i = 0; i < sizeList.size(); i++){
		        result = (Size) sizeList.get(i);
		        heightArray[i] = result.height;
		        widthArray[i] = result.width;
		        resolutionArray[i] = result.width + "x" + result.height;
		    }
	    cameraSize.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Picture size:");
				alert.setItems(resolutionArray, new DialogInterface.OnClickListener() {                
				    public void onClick(DialogInterface dialog, int which) { 
				    	camParams.setPictureSize(widthArray[which], heightArray[which]);
				    	camera.setParameters(camParams);
				    	Toast.makeText(getApplicationContext(), "Picture size was changed", Toast.LENGTH_SHORT).show();
				    }
				});
				alert.show();
				
			}
		}); 	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}
	

}
