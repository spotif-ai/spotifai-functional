package com.example.spotif_ai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ContentActivity extends AppCompatActivity {

    Button songbutton;
    Button playlistbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_setting);

        songbutton =  findViewById(R.id.songlink);
        playlistbutton = findViewById(R.id.playlistlink);

        songbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://spotify.com"));
                startActivity(intent);
                finish();

            }
        });

        playlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://spotify.com"));
                startActivity(intent);
                finish();

            }
        });

    }
}
