package com.vino.phe.dailyselfie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    File[] files ;

    private android.widget.GridView gridview;
    private gridViewAdaper gridAdapter;


    File storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES);
    String Daily = storageDir.getAbsolutePath()+"/Daily";

    static ArrayList<imageArray> arrayList;
    static final int REQUEST_TAKE_PHOTO = 1;

    private ArrayList<Integer> mThumbIdsFlowers = new ArrayList<Integer>(
            Arrays.asList(R.drawable.ic_camera, R.drawable.ic_refresh
                  ));


    protected static final String EXTRA_RES_ID = "POS";
    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;
    private AlarmManager mAlarmManager;
    private PendingIntent mSelfiePendingIntent;
    private Intent mSelfieNotificationIntent;
    private static final int THUMB_DIM = 100;
    private static final long TWO_MINUTES = 120 * 1000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.gridview);

        // Create a new ImageAdapter and set it as the Adapter for this GridView
        gridAdapter=new gridViewAdaper(this, getData());
        gridview.setAdapter(gridAdapter);

        // Set an setOnItemClickListener on the GridView
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Create an Intent to start the ImageViewActivity
                Intent intent = new Intent(MainActivity.this,
                        gridViewActivity.class);

                // Add the ID of the thumbnail to display as an Intent Extra
                intent.putExtra(EXTRA_RES_ID, position);

                // Start the ImageViewActivity
                startActivity(intent);
            }
        });

//        createSelfieReminders();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        android.support.v7.app.ActionBar mActionBar = this.getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        mActionBar.setTitle(R.string.actionbarTitle);
        mActionBar.setDisplayShowTitleEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_camera) {
            start_Picture();
        }
        else if(id == R.id.menu_refresh){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createSelfieReminders() {
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Broadcast the notification intent at specified intervals
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP
                , System.currentTimeMillis() + TWO_MINUTES
                , TWO_MINUTES
                , mSelfiePendingIntent);

        createPendingIntents();

    }

    private void createPendingIntents() {
        // Create the notification pending intent
        mSelfieNotificationIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        mSelfiePendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, mSelfieNotificationIntent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

//            Toast.makeText(this, "Image saved to:\n" +
//                    data.getData(), Toast.LENGTH_LONG).show();
//            Log.d("OnActivityResult", data.getData().toString());

//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            mImageView.setImageBitmap(imageBitmap);

//            To add pic to imageArray

            Log.d("OnActivityResult", "Camera image save!");

            imageArray imageFile = new imageArray(setPic(), mCurrentPhotoPath);

            arrayList.add(imageFile);
            gridAdapter.notifyDataSetChanged();


//            gridview.invalidate();
//            gridAdapter = new gridViewAdaper(this, getData());
//            gridview.setAdapter(gridAdapter);
        }
    }

    private void start_Picture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("start_Picture", "error");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Give a file path to save
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }



    private File createImageFile() throws IOException {
        File image;
        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + "Test" + "_";

        File f=new File(Daily);
        if(!f.exists()){
            f.mkdir();
        }


//       there is not the file so create it.
//        if(!storageDir.exists()) {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    f      /* directory */
            );


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =  image.getAbsolutePath();
        return image;
    }

    private ArrayList<imageArray> getData(){
        arrayList=new ArrayList<>();

        File sd =new File(Daily);
//            File sd = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(sd.exists()) {
            files = sd.listFiles();
        }

        if(files == null) {
            return arrayList;
        }

        try{
            for(int i = 0; i<files.length; i++) {
                mCurrentPhotoPath=files[i].getAbsolutePath();
                Log.d("File",files[i].getAbsolutePath());


                imageArray imageFile = new imageArray(setPic(), mCurrentPhotoPath);

                arrayList.add(imageFile);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }


        return arrayList;
    }

    private Bitmap setPic() {
        // Get the dimensions of the View
        int targetW = WIDTH;
        int targetH = HEIGHT;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        return bitmap;
    }

}
