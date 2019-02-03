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
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button stabilizeButton, motivateButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        stabilizeButton = findViewById(R.id.stabilizebutton);
        motivateButton = findViewById(R.id.boostbutton);

        stabilizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                dispatchTakePictureIntent(0);
            }
        });
        motivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(1);
            }
        });

    }

    private void dispatchTakePictureIntent(int dir) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra("direction",dir);
        Intent sendpic = new Intent(SettingActivity.this,testPhotoActivity.class);
        sendpic.putExtra("direction",dir);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            int direction = extras.getInt("direction");
//            Toast.makeText(this, String.valueOf(direction), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingActivity.this,testPhotoActivity.class);
//            extras.putInt("direction",direction);
            intent.putExtras(extras);
//            intent.putExtra("direction",direction);
            startActivity(intent);
            finish();

        }
    }

}
