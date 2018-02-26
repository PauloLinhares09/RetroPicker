package br.com.packapps.retropicker.config;

import android.app.Activity;
import android.app.FragmentTransaction;

import br.com.packapps.retropicker.MainActivity;
import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.fragments.RetroPickerFragment;

/**
 * Created by paulo.linhares on 08/02/18.
 */

public class Retropicker {
    private RetropickerBuilder builder;

    private Throwable throwable;
    public static final int CAMERA_PICKER = 0;
    private RetroPickerFragment retroPickerFragment;

    public Retropicker(RetropickerBuilder builder) {
        this.builder = builder;
    }


    private void enquee() {

        if (throwable != null){
            builder.getCallbackPicker().onFailure(throwable);
        }else{
            //call
            retroPickerFragment.setCallBack(builder.getCallbackPicker());
        }

    }

    private static Throwable isValidate() {

        return null;
    }

    public void open() {
        throwable = isValidate();

        if (throwable == null){
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
        RetropickerBuilder rb;

        public Builder(Activity activity) {
            rb = new RetropickerBuilder(activity);
        }

        public Builder setPackageName(String packageName) {
            return this;
        }

        public Builder setTypeAction(int typeAction) {
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
