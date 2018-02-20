package br.com.packapps.retropicker.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.packapps.retropicker.R;
import br.com.packapps.retropicker.Util.Const;

/**
 * @Author Paulo linhares 20/02/2018
 */
public class RetroPickerFragment extends Fragment {


    private static final int REQUEST_TAKE_PHOTO = 100;

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int type_action;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String mCurrentPhotoPath;

    public RetroPickerFragment() {
        // Required empty public constructor
    }


    public static RetroPickerFragment newInstance(int type_action, String param2) {
        RetroPickerFragment fragment = new RetroPickerFragment();
        Bundle args = new Bundle();
        args.putInt(Const.Params.TYPE_ACTION, type_action);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type_action = getArguments().getInt(Const.Params.TYPE_ACTION);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSuccessFragment(Bitmap bitmap, String imagePath);
        void onFailureFragment(Throwable e);
    }


    //open intent at camera
    public void callCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getContext(), "Erro ao abrir a câmera. Por favor tente novamente.", Toast.LENGTH_SHORT).show();
                mCurrentPhotoPath = null;
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        getActivity().getApplicationContext().getPackageName() + ".fileprovider",
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file_paths: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //###Camera
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == getActivity().RESULT_OK){
            //getting photo for mCurrentImage
            //set image pic
            Bitmap bitmap = getPicBitmap();
            mListener.onSuccessFragment(bitmap, mCurrentPhotoPath);
        }
    }
}
