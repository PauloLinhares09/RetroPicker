package br.com.packapps.retropicker.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.packapps.retropicker.callback.CallbackPicker;

/**
 * Created by paulo.linhares on 08/02/18.
 */

public class Retropicker extends AppCompatActivity {

    private String packageApp;
    private int actionType;
    private String imageName;
    private Activity context;
    private Throwable throwable;
    public static final int CAMERA_PICKER = 0;

    private static final int REQUEST_TAKE_PHOTO = 100;
    private String mCurrentPhotoPath;
    private CallbackPicker callbackPicker;


    private Retropicker(Activity context, String packageApp, int actionType, String imageName) {
        this.context = context;
        this.packageApp = packageApp;
        this.actionType = actionType;
        this.imageName  = imageName;

        throwable = isValidate();

    }



    public void enquee(CallbackPicker callbackPicker) {

        if (throwable != null){
            callbackPicker.onFailure(throwable);
        }else{
            //call
            switch (actionType){
                case CAMERA_PICKER:
                    this.callbackPicker = callbackPicker;
                    callCameraIntent();
                    break;
            }
        }




    }

    private static Throwable isValidate() {

        return null;
    }

    public static Retropicker createAction(Activity context, String packageName, int photoPicker, String imageName) {
        return new Retropicker(context, packageName, photoPicker, imageName);
    }


    //open intent at camera
    private void callCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(context, "Erro ao abrir a câmera. Por favor tente novamente.", Toast.LENGTH_SHORT).show();
                mCurrentPhotoPath = null;
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        context.getApplicationContext().getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                context.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //create file_paths for used with URI
    private File createImageFile() throws IOException {
        // Create an image file_paths name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file_paths: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //###Camera
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            //getting photo for mCurrentImage
            //set image pic
            Bitmap bitmap = getPicBitmap();
            callbackPicker.onSuccess(bitmap, mCurrentPhotoPath);

        }
    }


    private Bitmap getPicBitmap() {

	    /* Get the size of the ImageView */
//        int targetW = ivPhotoDoc.getWidth();
//        int targetH = ivPhotoDoc.getHeight();
//
        int targetW = 520;
        int targetH = 520;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);


        return bitmap;

    }
}
