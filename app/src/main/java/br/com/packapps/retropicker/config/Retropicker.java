package br.com.packapps.retropicker.config;

import br.com.packapps.retropicker.action.PhotoPicker;

/**
 * Created by paulo.linhares on 08/02/18.
 */

public class Retropicker {

    public static final int PHOTO_PICKER = 0;

    public static PhotoPicker createAction(String packageApp, int actionType, String imageName) {
        if (isValidate()) {


            return null;
        }
    }

    private static boolean isValidate() {

        return true;
    }

}
