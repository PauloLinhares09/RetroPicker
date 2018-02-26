package br.com.packapps.retropicker.controller;

import br.com.packapps.retropicker.config.Retropicker;
import br.com.packapps.retropicker.throwables.TypeActionRetroPickerException;

/**
 * Created by paulo.linhares on 26/02/18.
 */

public class ControllerThroable {
    public static Throwable analiseTypeActionPicker(int typeAction) {
        if (typeAction == Retropicker.CAMERA_PICKER || typeAction == Retropicker.GALLERY_PICKER)
            return null;

        return new TypeActionRetroPickerException("typeAction=" + typeAction + " is not type value valid. Try RetroPicker.CAMERA_PICKER or RetroPicker.GALERY_PICKER", typeAction);
    }
}
