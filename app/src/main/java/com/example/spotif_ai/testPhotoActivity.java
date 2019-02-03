package com.example.spotif_ai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.FaceDetector;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.protobuf.ByteString;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.CompressFormat.JPEG;

public class testPhotoActivity extends AppCompatActivity {
    ImageView mapImage;
    Button smilingProb;
    private DatabaseReference databaseReference;

    TextView smileprobability;
    TextView heartrate, artistView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        smileprobability = findViewById(R.id.smileprobabilityvalue);
        heartrate = findViewById(R.id.heartratevalue);

        setContentView(R.layout.activity_test_photo);
        Intent data = getIntent();
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        //mapImage = findViewById(R.id.map_image);
        //mapImage.setImageBitmap(imageBitmap);
        final int direction = extras.getInt("direction");

        smilingProb = findViewById(R.id.songlink);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        artistView = findViewById(R.id.artistView);

        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(highAccuracyOpts);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {
                                        FirebaseVisionFace face = faces.get(0);
                                        float smilingProbi = 0;
                                        if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                            smilingProbi = face.getSmilingProbability();
                                        }
                                        double emotion = getSmileEmotion(smilingProbi);

                                        final int songIndex = getSongVal(emotion, direction);
                                        Song test = new Song("1","1","1",1);

                                        DatabaseReference note = databaseReference.push();
                                        note.setValue(test);

                                        getSong(songIndex);

                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
        smilingProb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("https://www.youtube.com/results?search_query=" + smilingProb.getText()));
                startActivity(openURL);
                overridePendingTransition(R.anim.slide_up, R.anim.fade_out);
                finish();
            }
        });

    }

    public double getSmileEmotion(double smile){

        double x = Math.log(smile + 0.6189);
        return (x / Math.log(1.6189));

    }
    public int getSongVal(double emotion, int direction){
        smileprobability = findViewById(R.id.smileprobabilityvalue);

        smileprobability.setText(String.valueOf(emotion));
        int index = (int)(emotion * 1009) + 1009;
        if(direction == 0){
            index = index/2;
        }
        if(index > 2016){
            index = 2016;
        }
        return 2016 - index;
    }
    public void getSong(final int index){
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://spotif-ai-3c5c6.firebaseio.com/");
        smilingProb = findViewById(R.id.songlink);
        artistView = findViewById(R.id.artistView);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals(String.valueOf(index))){
                        Song song = dataSnapshot1.getValue(Song.class);
                        smilingProb.setText(song.getTitle());
                        artistView.setText("by " + song.getArtist());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public String extractTitle(String whole){
        int x = whole.indexOf("title");
        int end = whole.indexOf("}}");
        String n = whole.substring(x+6,end+1);
        return n;

    }


}
