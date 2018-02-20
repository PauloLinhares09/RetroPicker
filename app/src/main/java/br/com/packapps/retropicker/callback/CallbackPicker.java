package br.com.packapps.retropicker.callback;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by paulo.linhares on 08/02/18.
 */

public interface CallbackPicker {

    void onSuccess(Bitmap bitmap, String ImagePath);

    void onFailure(Throwable error);
}
