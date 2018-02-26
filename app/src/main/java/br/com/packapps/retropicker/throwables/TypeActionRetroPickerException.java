package br.com.packapps.retropicker.throwables;

/**
 * Created by paulo.linhares on 26/02/18.
 */

public class TypeActionRetroPickerException extends Exception {
    private int typeAction;

    public TypeActionRetroPickerException(String message, int typeAction) {
        super(message);
        this.typeAction = typeAction;
    }

    public int getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(int typeAction) {
        this.typeAction = typeAction;
    }

    @Override
    public String toString() {
        return "TypeActionRetroPickerException{" +
                "typeAction=" + typeAction + " is not type value valid. Try RetroPicker.CAMERA_PICKER or RetroPicker.GALERY_PICKER. Example: builder.setTypeAction(RetroPicker.CAMERA_PICKER)" +
                '}';
    }
}
