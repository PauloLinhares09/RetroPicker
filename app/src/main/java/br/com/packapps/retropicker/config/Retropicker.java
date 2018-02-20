package br.com.packapps.retropicker.config;

import android.app.Activity;
import android.app.FragmentTransaction;

import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.fragments.RetroPickerFragment;

/**
 * Created by paulo.linhares on 08/02/18.
 */

public class Retropicker {

    private String packageApp;
    private int actionType;
    private String imageName;
    private Activity context;
    private Throwable throwable;
    public static final int CAMERA_PICKER = 0;

    private String mCurrentPhotoPath;
    private CallbackPicker callbackPicker;
    private RetroPickerFragment retroPickerFragment;


    private Retropicker(Activity context, String packageApp, int actionType, String imageName) {
        this.context = context;
        this.packageApp = packageApp;
        this.actionType = actionType;
        this.imageName  = imageName;

        throwable = isValidate();

        if (throwable == null){
            retroPickerFragment = RetroPickerFragment.newInstance(actionType, null);
            FragmentTransaction ft = context.getFragmentManager().beginTransaction();
            ft.add(retroPickerFragment, "RETROPICKER_FRAGMENT");
            ft.commit();
        }

    }



    public void enquee(CallbackPicker callbackPicker) {

        if (throwable != null){
            callbackPicker.onFailure(throwable);
        }else{
            //call
            retroPickerFragment.setCallBack(callbackPicker);
            retroPickerFragment.setContext(context);

//            switch (actionType){
//                case CAMERA_PICKER:
//                    this.callbackPicker = callbackPicker;
//                    try{
//                        retroPickerFragment.callCameraIntent();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    break;
//            }
        }

    }

    private static Throwable isValidate() {

        return null;
    }

    public static Retropicker createAction(Activity context, String packageName, int photoPicker, String imageName) {
        return new Retropicker(context, packageName, photoPicker, imageName);
    }



}
