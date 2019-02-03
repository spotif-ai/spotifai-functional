package com.example.spotif_ai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    Button stabilizeButton, motivateButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        stabilizeButton = findViewById(R.id.stabilize_button);
        motivateButton = findViewById(R.id.motivate_button);

//        stabilizeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//           ` public void onClick(View v) {
//                Intent stabilize = new Intent(SettingActivity.this, CameraActivity.class);
//                stabilize.putExtra("id", "stabilize");
//                startActivity(stabilize);
//                finish();
//
//            }
//        });
        motivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(SettingActivity.this,testPhotoActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
            finish();

        }
    }

}
