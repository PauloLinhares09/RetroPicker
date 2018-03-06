package br.com.packapps.retropicker.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.packapps.retropicker.Util.Const;
import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.config.Retropicker;
import br.com.packapps.retropicker.throwables.TypeActionRetroPickerException;

/**
 * @Author Paulo linhares 20/02/2018
 */
public class RetroPickerFragment extends Fragment {

    private static final int REQUEST_TAKE_PHOTO = 100;
    private static final int REQUEST_OPEN_GALLERY = 101;


    private int actionType;
    private String mParam2;

    private String mCurrentPhotoPath;
    private CallbackPicker callbackPicker;
    private Activity activity;
    private boolean checkpermission;

    public RetroPickerFragment() {
        // Required empty public constructor
    }


    public static RetroPickerFragment newInstance(int type_action, boolean checkpermission) {
        RetroPickerFragment fragment = new RetroPickerFragment();
        Bundle args = new Bundle();
        args.putInt(Const.Params.TYPE_ACTION, type_action);
        args.putBoolean(Const.Params.CHECK_PERMISSION, checkpermission);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "onCreate");
        if (getArguments() != null) {
            actionType = getArguments().getInt(Const.Params.TYPE_ACTION);
            checkpermission = getArguments().getBoolean(Const.Params.CHECK_PERMISSION);
        }

        //### axecute action
        executeAction(checkpermission);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("TAG", "onAttach");
        this.activity = (Activity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "onResume");

    }

    private void executeAction(boolean checkPermission) {
        switch (actionType){
            case Retropicker.CAMERA_PICKER:
                if (!checkPermission)
                    callCameraIntent();
                else
                    mCheckPermission(Retropicker.CAMERA_PICKER);

                break;
            case Retropicker.GALLERY_PICKER:
                if (!checkPermission)
                    openGallery();
                else
                    mCheckPermission(Retropicker.GALLERY_PICKER);

                break;
        }
    }

    private void mCheckPermission(int actionType) {
        switch (actionType) {
            case Retropicker.CAMERA_PICKER:

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA)) {

                        // TODO: provider this option layout info to the user
                        requestActionPermissionToUser(Manifest.permission.CAMERA, Const.MY_PERMISSIONS_REQUEST_CAMERA);

                    } else {

                        requestActionPermissionToUser(Manifest.permission.CAMERA, Const.MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                } else {
                    executeAction(false);
                }

                break;

            case Retropicker.GALLERY_PICKER:

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // TODO: provider this option layout info to the user
                        requestActionPermissionToUser(Manifest.permission.READ_EXTERNAL_STORAGE, Const.MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);

                    } else {

                        requestActionPermissionToUser(Manifest.permission.READ_EXTERNAL_STORAGE, Const.MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);

                    }
                } else {
                    executeAction(false);
                }

                break;
        }

    }

    private void requestActionPermissionToUser(String permissionType, int requestCodeType) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permissionType},
                requestCodeType);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_OPEN_GALLERY);
    }

    //open intent at camera
    private void callCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(activity, "Erro ao abrir a câmera. Por favor tente novamente.", Toast.LENGTH_SHORT).show();
                mCurrentPhotoPath = null;
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        activity.getApplicationContext().getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //create file_paths for used with URI
    private File createImageFile() throws IOException {
        // Create an image file_paths name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file_paths: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private Bitmap getPicBitmap() throws IOException {

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

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                activity.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //###Camera
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == activity.RESULT_OK){
            //getting photo for mCurrentImage
            //set image pic
            executeActionResult(Retropicker.CAMERA_PICKER, data);

        }else if (requestCode == REQUEST_OPEN_GALLERY && resultCode == activity.RESULT_OK){

            executeActionResult(Retropicker.GALLERY_PICKER, data);
        }
    }

    private void executeActionResult(int typeAction, Intent data) {

        switch (typeAction) {
            case Retropicker.CAMERA_PICKER:
                Bitmap bitmap = null;
                try {
                    bitmap = getPicBitmap();
                    callbackPicker.onSuccess(bitmap, mCurrentPhotoPath);
                } catch (IOException e) {
                    callbackPicker.onFailure(e);
                    e.printStackTrace();
                }
                break;

            case Retropicker.GALLERY_PICKER:
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                    mCurrentPhotoPath = uri.toString();
                    try {
                        bitmap = getBitmapFromUri(uri);
                        callbackPicker.onSuccess(bitmap, mCurrentPhotoPath);
                    } catch (IOException e) {
                        callbackPicker.onFailure(e);
                        e.printStackTrace();
                    }

                }
                break;
            default:
                callbackPicker.onFailure(
                        new TypeActionRetroPickerException("typeAction=" + typeAction + " is not type value valid. Try RetroPicker.CAMERA_PICKER or RetroPicker.GALERY_PICKER. " +
                        "Example: builder.setTypeAction(RetroPicker.CAMERA_PICKER)", typeAction));


        }
    }

    public void setCallBack(CallbackPicker callbackPicker) {
        this.callbackPicker = callbackPicker;
    }



    public void myOnRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Const.MY_PERMISSIONS_REQUEST_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    executeAction(false);
                } else {
                    //TODO what to do?
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                break;

            case Const.MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    executeAction(false);
                } else {
                    //TODO what to do?
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                break;
        }
    }

}
