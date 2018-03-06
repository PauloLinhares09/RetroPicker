package br.com.packapps.retropicker.config;

import android.app.Activity;
import android.app.FragmentTransaction;

import br.com.packapps.retropicker.Util.Const;
import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.controller.ControllerThroable;
import br.com.packapps.retropicker.fragments.RetroPickerFragment;

/**
 * Created by paulo.linhares on 08/02/18.
 */

public class Retropicker {
    public static final int CAMERA_PICKER = 900;
    public static final int GALLERY_PICKER = 901;
    private RetropickerBuilder builder;
    private RetroPickerFragment retroPickerFragment;

    public Retropicker(RetropickerBuilder builder) {
        this.builder = builder;
    }


    private void enquee() {

        if (builder.getThrowable() != null){
            builder.getCallbackPicker().onFailure(builder.getThrowable());
        }else{
            //call
            retroPickerFragment.setCallBack(builder.getCallbackPicker());
        }

    }


    public void open() {

        if (builder.getThrowable() == null){
            if (retroPickerFragment == null) {
                //TODO without support fragment. Pending
                retroPickerFragment = RetroPickerFragment.newInstance(builder.getTypeAction(), builder.isCheckPermission());
                FragmentTransaction ft = builder.getActivity().getFragmentManager().beginTransaction();
                ft.add(retroPickerFragment, Const.RETROPICKER_FRAGMENT_TAG);
                ft.commit();
            }
        }

        enquee();

    }

    public void onRequesPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        retroPickerFragment.myOnRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * Class Builder for init data and configs at this Library
     */
    public static class Builder {
        private RetropickerBuilder rb;

        public Builder(Activity activity) {
            rb = new RetropickerBuilder(activity);
        }

//        public Builder setPackageName(String packageName) {
//            return this;
//        }

        public Builder setTypeAction(int typeAction) {
            rb.setThrowable(ControllerThroable.analiseTypeActionPicker(typeAction));
            rb.setTypeAction(typeAction);
            return this;
        }

        public Builder setImageName(String imageName) {
            return this;
        }

        public Builder enquee(CallbackPicker callbackPicker) {
            rb.setCallbackPicker(callbackPicker);
            return this;
        }

        public Retropicker create() {
            return new Retropicker(rb);
        }

        public Builder checkPermission(boolean checkPermission) {
            rb.setCheckPermission(checkPermission);
            return this;
        }
    }
}
