package br.com.packapps.retropicker.config;

import android.app.Activity;

import br.com.packapps.retropicker.callback.CallbackPicker;

/**
 * Created by paulo.linhares on 21/02/18.
 */

class RetropickerBuilder {
    private Activity activity;
    private String packageName;
    private int typeAction;
    private String imageName;
    private CallbackPicker callbackPicker;
    private Throwable throwable;
    private boolean checkPermission;

    public RetropickerBuilder(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(int typeAction) {
        this.typeAction = typeAction;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public CallbackPicker getCallbackPicker() {
        return callbackPicker;
    }

    public void setCallbackPicker(CallbackPicker callbackPicker) {
        this.callbackPicker = callbackPicker;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setCheckPermission(boolean checkPermission) {
        this.checkPermission = checkPermission;
    }

    public boolean isCheckPermission() {
        return checkPermission;
    }
}
