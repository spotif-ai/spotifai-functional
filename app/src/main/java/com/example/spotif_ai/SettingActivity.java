package com.example.spotif_ai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
public class SettingActivity extends AppCompatActivity {

    Button boostbutton, changebutton;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        boostbutton = findViewById(R.id.boostbutton);
        changebutton = findViewById(R.id.changebutton);

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

        boostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                overridePendingTransition(R.anim.slide_up, R.anim.fade_out);
                finish();
            }
        });

        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                overridePendingTransition(R.anim.slide_up, R.anim.fade_out);
                finish();
            }
        });


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            Intent intent = new Intent(SettingActivity.this,testPhotoActivity.class);
//            intent.putExtras(extras);
//            startActivity(intent);
            finish();

        }
    }

    Button button;

    public void onClick(View v) {

        Drawable drc = getResources().getDrawable(R.drawable.change_button_pressed);
        Drawable dr = getResources().getDrawable(R.drawable.boost_button_pressed);
        dr.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);

        switch (v.getId()) {
            case R.id.changebutton:

                if (button == null) {
                    button = (Button) findViewById(v.getId());
                } else {
                    button.setBackgroundResource(R.drawable.change_button_pressed);
                    button = (Button) findViewById(v.getId());
                }
                button.setBackgroundDrawable(drc);

                break;

            case R.id.boostbutton:
                if (button == null) {
                    button = (Button) findViewById(v.getId());
                } else {
                    button.setBackgroundResource(R.drawable.boost_button_pressed);
                    button = (Button) findViewById(v.getId());
                }
                button.setBackgroundDrawable(dr);

                break;

            default:
                break;
        }
    }

}
