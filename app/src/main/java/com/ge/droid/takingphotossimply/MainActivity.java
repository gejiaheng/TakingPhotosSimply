package com.ge.droid.takingphotossimply;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = (Button) findViewById(R.id.test1);
        mButton2 = (Button) findViewById(R.id.test2);
        mButton3 = (Button) findViewById(R.id.test3);
        mButton4 = (Button) findViewById(R.id.test4);
        mImageView = (ImageView) findViewById(R.id.image);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, R.string.no_camera, Toast.LENGTH_SHORT).show();
            return;
        }

        mCurrentPhotoPath = null;
        switch (v.getId()) {
            // get a thumbnail
            case R.id.test1:
                dispatchTakePictureIntentTest1();
                break;
            // save the image file to private storage
            case R.id.test2:
                dispatchTakePictureIntentTest2();
                break;
            // save the image file to public storage and add it to gallery
            case R.id.test3:
                dispatchTakePictureIntentTest3();
                break;
            // save the image file to public storage and compress it
            case R.id.test4:
                dispatchTakePictureIntentTest4();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_TAKE_PICTURE_TEST_1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_TAKE_PICTURE_TEST_2) {
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        } else if (requestCode == REQUEST_TAKE_PICTURE_TEST_3) {
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
            galleryAddPic();
        } else if (requestCode == REQUEST_TAKE_PICTURE_TEST_4) {
            compressPic();
        }
    }

    private static final int REQUEST_TAKE_PICTURE_TEST_1 = 1;
    private static final int REQUEST_TAKE_PICTURE_TEST_2 = 2;
    private static final int REQUEST_TAKE_PICTURE_TEST_3 = 3;
    private static final int REQUEST_TAKE_PICTURE_TEST_4 = 4;

    private String mCurrentPhotoPath;

    private void dispatchTakePictureIntentTest1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE_TEST_1);
        }
    }

    private void dispatchTakePictureIntentTest2() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(true);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ge.droid.takingphotossimply.private.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE_TEST_2);
            }
        }
    }

    private void dispatchTakePictureIntentTest3() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(false);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ge.droid.takingphotossimply.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE_TEST_3);
            }
        }
    }

    private void dispatchTakePictureIntentTest4() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(false);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ge.droid.takingphotossimply.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE_TEST_4);
            }
        }
    }

    private File createImageFile(boolean privateStorage) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = privateStorage ? getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void compressPic() {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        // Determine how much to scale down the image
        int scaleFactor = 2;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

}
