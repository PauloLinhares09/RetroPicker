package br.com.packapps.retropicker.config;

import android.app.Activity;
import android.app.FragmentTransaction;

import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.controller.ControllerThrowable;
import br.com.packapps.retropicker.fragments.RetroPickerFragment;
import br.com.packapps.retropicker.interfaces.RetroPickerContract;

/**
 * Created by paulo.linhares on 08/02/18.
 * Altered by gabrielnovaes on  01/10/18
 */

public class Retropicker  implements RetroPickerContract {
    public static final int CAMERA_PICKER = 900;
    public static final int GALLERY_PICKER = 901;

    private RetropickerBuilder builder;
    private RetroPickerFragment retroPickerFragment;

    public Retropicker(RetropickerBuilder builder) {
        this.builder = builder;
    }


    @Override
    public void enquee() {

        if (builder.getThrowable() != null){
            builder.getCallbackPicker().onFailure(builder.getThrowable());
        }else{
            //call
            retroPickerFragment.setCallBack(builder.getCallbackPicker());
        }
    }

    @Override
    public void open() {

        if (builder.getThrowable() == null){
            //TODO without support fragment. Pending
            retroPickerFragment = RetroPickerFragment.newInstance(builder.getTypeAction(), null);
            FragmentTransaction ft = builder.getActivity().getFragmentManager().beginTransaction();
            ft.add(retroPickerFragment, "RETROPICKER_FRAGMENT");
            ft.commit();
        }

        enquee();
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
            rb.setThrowable(ControllerThrowable.analiseTypeActionPicker(typeAction));
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
    }
}
