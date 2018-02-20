package br.com.packapps.retropicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.config.Retropicker;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void btActionImage(View view) {

        Retropicker retropicker = Retropicker.createAction(this, getPackageName(), Retropicker.CAMERA_PICKER, "first_image.jpg");

        retropicker.enquee(new CallbackPicker() {
            @Override
            public void onSuccess(Bitmap bitmap, String imagePath) {
                Toast.makeText(MainActivity.this, "bitmap: " + bitmap, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "imagePath: " + imagePath, Toast.LENGTH_SHORT).show();
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Throwable error) {
                Toast.makeText(MainActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
