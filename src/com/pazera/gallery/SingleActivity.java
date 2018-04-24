package com.pazera.gallery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class SingleActivity extends Activity {

	private ImageView singlePicture;
	private int z = 0;
	private ImageView settingsImage;
	private ImageView deleteImage;
	private ImageView singleText;
	private ImageView singleCycle;
	private ImageView singleMirror;
	private ImageView singlePencil;
	private ImageView singleContrast;
	private ImageView singleAccept;
	private SeekBar contrastBar;
	private SeekBar saturationBar;
	private SeekBar brightnessBar;
	private LinearLayout bars;
	private int k = 0;
	private int l = 0;
	private int m = 0;
	private RelativeLayout singlePreview;
	private Typeface tf;
	private int startx;
	private int starty;
	private int stx;
	private int sty;
	private Bitmap bmp2 = null;
	private Bitmap basic;
	private Bitmap basic2;
	private boolean visible = false;
	final Context context = this;
	private ProgressDialog pDialog; // kontrolka wyswietlana podczas długotrwałych operacji
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_single);
		Bundle bundle = getIntent().getExtras();
		final String path = bundle.getString("path").toString();
		singlePicture = (ImageView) findViewById(R.id.singlePicture);
		singlePreview = (RelativeLayout) findViewById(R.id.singlePreview);
		singleCycle = (ImageView) findViewById(R.id.singleCycle);
		singleMirror = (ImageView) findViewById(R.id.singleMirror);
		bars = (LinearLayout) findViewById(R.id.bars);
		singlePencil = (ImageView) findViewById(R.id.singlePencil);
		singleContrast = (ImageView) findViewById(R.id.singleContrast);
		singleAccept = (ImageView) findViewById(R.id.singleAccept);
		contrastBar = (SeekBar) findViewById(R.id.contrastBar);
		brightnessBar = (SeekBar) findViewById(R.id.brightnessBar);
		saturationBar = (SeekBar) findViewById(R.id.saturationBar);
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		basic = BitmapFactory.decodeFile(path);
		singlePicture.setImageBitmap(bitmap);
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("komunikat");
		pDialog.setCancelable(false);
		singlePicture.setScaleType(ScaleType.CENTER_CROP);
		singlePreview.setGravity(Gravity.CENTER);
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        final float dpWidth = displayMetrics.widthPixels;
        final float dpHeight = displayMetrics.heightPixels;
        /*singlePicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				singlePicture.requestLayout();
				
				Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
				if(display.getRotation() == Surface.ROTATION_0)
		        {                       
					if (z == 0) {
						singlePicture.getLayoutParams().width = (int) (dpWidth * 1);
	        	    	z = 1;
	        	    } else if (z == 1){
	        	    	singlePicture.getLayoutParams().width = (int) (dpWidth * 0.85);
	        	    	z = 2;
	        	    } else if (z == 2) {
	        	    	singlePicture.getLayoutParams().width = (int) (dpWidth * 0.7);
	        	    	z = 3;
	        	    } else if (z == 3) {
	        	    	singlePicture.getLayoutParams().width = (int) (dpWidth * 0.55);
	        	    	z = 0;
	        	    }
		        } else if(display.getRotation() == Surface.ROTATION_270)
		        {
		        	if (z == 0) {
						singlePicture.getLayoutParams().height = (int) (dpHeight * 1);
	        	    	z = 1;
	        	    } else if (z == 1){
	        	    	singlePicture.getLayoutParams().height = (int) (dpHeight * 0.85);
	        	    	z = 2;
	        	    } else if (z == 2) {
	        	    	singlePicture.getLayoutParams().height = (int) (dpHeight * 0.7);
	        	    	z = 3;
	        	    } else if (z == 3) {
	        	    	singlePicture.getLayoutParams().height = (int) (dpHeight * 0.55);
	        	    	z = 0;
	        	    }
		        } else {
		        	if (z == 0) {
						singlePicture.getLayoutParams().width = (int) (dpWidth * 1);
	        	    	z = 1;
	        	    } else if (z == 1){
	        	    	singlePicture.getLayoutParams().width = (int) (dpWidth * 0.85);
	        	    	z = 2;
	        	    } else if (z == 2) {
	        	    	singlePicture.getLayoutParams().width = (int) (dpWidth * 0.7);
	        	    	z = 3;
	        	    } else if (z == 3) {
	        	    	singlePicture.getLayoutParams().width = (int) (dpWidth * 0.55);
	        	    	z = 0;
	        	    }
		        }
				
				singlePicture.requestLayout();
				
			}
		});*/
		AssetManager assetManager = getAssets();
		try {
			String[] fontslist = assetManager.list("fonts");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tf=Typeface.createFromAsset(getAssets(),"fonts/arial.ttf");
		
		deleteImage = (ImageView) findViewById(R.id.deleteImage);
		deleteImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.yon2, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				alertDialogBuilder.setView(promptsView);

				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Yes",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
							File toDelete = new File(path); 
							toDelete.delete();
							Intent intent = new Intent(SingleActivity.this, GalleryActivity.class);
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
		
		singleText = (ImageView) findViewById(R.id.singleText);
		singleText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SingleActivity.this, SingletextActivity.class);
				//startActivity(intent);
				startActivityForResult(intent, 100);
			}
		});
		singleCycle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap bmp = ((BitmapDrawable) singlePicture.getDrawable()).getBitmap();
				Bitmap b = ImageEdition.changeSomething("cycle", bmp, "#ffffff", 0);
				singlePicture.setImageBitmap(b);
			}
		});
		singleMirror.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap bmp = ((BitmapDrawable) singlePicture.getDrawable()).getBitmap();
				Bitmap b = ImageEdition.changeSomething("mirror", bmp, "#ffffff", z);
				z++;
				if (z == 3) {
					z = 0;
				}
				singlePicture.setImageBitmap(b);
			}
		});
		final CharSequence colorsArray[] = new CharSequence[] {"Normalny", "Czerwony", "Negatyw", "Niebieski", "Zielony", "Morski", "Żółty"};
		singlePencil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("Kolor:");
				alert.setItems(colorsArray, new DialogInterface.OnClickListener() {                
				    public void onClick(DialogInterface dialog, int which) { 
				    	String value = colorsArray[which].toString();
				    	Bitmap bmp = BitmapFactory.decodeFile(path);
				    	Bitmap b = ImageEdition.changeColor(bmp, which);
				    	basic = b;
				    	singlePicture.setImageBitmap(b);
				    	k = 0;
				    	l = 0;
				    	m = 0;
				    	Toast.makeText(getApplicationContext(), "Zmiana koloru", Toast.LENGTH_SHORT).show();
				    }
				});
				alert.show();
			}
		});
		singleContrast.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (visible) {
					bars.setVisibility(View.INVISIBLE);
					visible = false;
				} else {
					bars.setVisibility(View.VISIBLE);
					visible = true;
				}
				
			}
		});
		saturationBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
				if (k == 0) {
					bmp2 = ((BitmapDrawable) singlePicture.getDrawable()).getBitmap();
					basic2 = bmp2;
					k++;
				} else {
					bmp2 = basic2;
				}
				int progressSat = saturationBar.getProgress();
				float sat = (float) progressSat / 256;
				Bitmap b = ImageEdition.changeSaturation(bmp2, sat);
				singlePicture.setImageBitmap(b);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				
			}
		});
		contrastBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (k == 0) {
					bmp2 = ((BitmapDrawable) singlePicture.getDrawable()).getBitmap();
					basic2 = bmp2;
					k++;
				} else {
					bmp2 = basic2;
				}
				int progressCon = contrastBar.getProgress();
				float con = (float) progressCon / 10;
				Bitmap b = ImageEdition.changeContrast(bmp2, con);
				singlePicture.setImageBitmap(b);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
		
		brightnessBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (k == 0) {
					bmp2 = ((BitmapDrawable) singlePicture.getDrawable()).getBitmap();
					basic2 = bmp2;
					k++;
				} else {
					bmp2 = basic2;
				}
				int progressBr = brightnessBar.getProgress();
				float br = progressBr - 255;
				Bitmap b = ImageEdition.changeBrightness(bmp2, br);
				singlePicture.setImageBitmap(b);
			}
		});
		
		singleAccept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SingleActivity.this);
			    builder.setTitle("Co chcesz zrobić?");
			    builder.setItems(new CharSequence[]
			            {"Zapisz na telefonie", "Zapisz na serwerze", "Udostępnij", "Anuluj"},
			            new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int which) {
			                    // The 'which' argument contains the index position
			                    // of the selected item
			                    switch (which) {
			                        case 0:
			                        	singlePreview.setDrawingCacheEnabled(true);
			            				singlePreview.buildDrawingCache();
			            				Bitmap bmp = singlePreview.getDrawingCache();
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
			            	        	File folderDir = new File(getDirectoryPath, "edytowane");
			            	        	folderDir.mkdirs();
			            				File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			            				File myFoto = new File(file, "/TomaszPazera/edytowane/" + fileName + ".jpg");
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
			            				Toast.makeText(getApplicationContext(), "Zapisano w folderze edytowane", Toast.LENGTH_SHORT).show();
			                            break;
			                        case 1:
			                        	if(Networking.checkInternet(context)) {
			                        		Log.d("DS", "Jest internet");
			                        		new UploadFoto().execute();
			                        		
			                            } else {
			                            	Log.d("DS", "Nie ma internetów");
			                            }
			                        	break;
			                        case 2:
			                        	Intent share = new Intent(Intent.ACTION_SEND);
			                        	share.setType("image/jpeg");
			                        	Random r2 = new Random();
			            				int il2 = (r.nextInt(999-100) + 100);
			            				SimpleDateFormat dFormat2 = new SimpleDateFormat("yyyyMMdd_HHmmss");
			            				String d = dFormat.format(new Date());
			            				String tempFileName = d + "_" + il;

			                        	//teraz utwórz plik File
			                        	//wpisz do niego przekonwertowana na byte[] bitmapę pobraną ze zdjęcia
			                        	//zapisz plik na dysku na karcie SD w znanej lokalizacji

			                        	//pobierz plik z karty SD i podziel się nim:
			                        	share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/tymczasowy.jpg"));
			                        	//pokazanie okna share
			                        	startActivity(Intent.createChooser(share, "Podziel się plikiem!"));
			                        	break;
			                    }
			                }
			            });
			    builder.create().show();

				
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bundle extras = data.getExtras();
		//byte[] xmodify = (byte[]) extras.getString("xmodify");
		String xmodify = extras.getString("xmodify");
		int xfont = extras.getInt("xfont");
		AssetManager assetManager = getAssets();
		String[] fontslist;
		try {
			fontslist = assetManager.list("fonts");
			tf=Typeface.createFromAsset(getAssets(),"fonts/" + fontslist[xfont]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int xborder = extras.getInt("xborder");
		int xfill = extras.getInt("xfill");
		String xtext = extras.getString("xtext");
		final PreviewText2 newText = new PreviewText2(getApplicationContext(), tf, xfill, xborder, xtext, 5, 65);
		singlePreview.addView(newText);
		newText.setOnTouchListener(new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {

		    	switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                	newText.setLeft((int) (event.getRawX() - startx + stx));
                	newText.setTop((int) (event.getRawY() - starty + sty));
                    break;
                case MotionEvent.ACTION_DOWN:
                    //start
                    startx = (int)event.getRawX();
                    starty = (int)event.getRawY();
                    stx = newText.getLeft();
                    sty = newText.getTop();
                    break;
                case MotionEvent.ACTION_UP:
                    //koniec
                    break;
	            }
	            return true;

		    }
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single, menu);
		return true;
	}

	private class UploadFoto extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			singlePreview.setDrawingCacheEnabled(true);
			singlePreview.buildDrawingCache();
			Bitmap b = singlePreview.getDrawingCache();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			b.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] fotoData = stream.toByteArray();
			HttpPost httpPost = new HttpPost("http://4ib2.spec.pl.hostingasp.pl/Pazera_Tomasz/Save.aspx");
			httpPost.setEntity(new ByteArrayEntity(fotoData)); // fotoData - nasze zdjęcie przekonwertowane na 
			DefaultHttpClient httpClient = new DefaultHttpClient(); // klient http
			HttpResponse httpResponse = null; // obiekt odpowiedzi z serwera
			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // wykonanie wysłania
			try {
				String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog.show();
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
		}
		
	}
	
		
}
