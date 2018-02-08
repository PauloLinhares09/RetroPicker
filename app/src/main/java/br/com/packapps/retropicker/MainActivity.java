package br.com.packapps.retropicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.com.packapps.retropicker.action.PhotoPicker;
import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.config.Retropicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btActionImage(View view) {
        PhotoPicker photoPicker = Retropicker.createAction(getPackageName(), Retropicker.PHOTO_PICKER, "first_image.jpg");
        photoPicker.enquee(new CallbackPicker() {
            @Override
            public void onSuccess(Bitmap bitmap, Intent data) {
                Toast.makeText(MainActivity.this, "bitmap: " + bitmap, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable error) {
                Toast.makeText(MainActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
