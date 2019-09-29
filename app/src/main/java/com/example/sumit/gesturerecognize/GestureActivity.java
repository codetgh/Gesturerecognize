package com.example.sumit.gesturerecognize;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class GestureActivity extends AppCompatActivity implements
        GestureOverlayView.OnGesturePerformedListener {

    private static final String TAG = "GestureActivity";
    private TextView textViewPredictedLetter;
    private ImageView drawImage;
    private GestureLibrary alphabetLibrary;
    private ArrayList<String> resImageLetter = new ArrayList<>();
    private ArrayList<GestureImage> gestureImageArrayList = new ArrayList<>(7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        textViewPredictedLetter = findViewById(R.id.textViewPredictedLetter);
        drawImage =  findViewById(R.id.drawLetterTv);

        setImageToDraw();

        alphabetLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures_shap);
        if (!alphabetLibrary.load()) {
            finish();
        }

        GestureOverlayView gestures =  findViewById(R.id.gesturesOverlay);
        gestures.addOnGesturePerformedListener(this);
        setHomeButtonClick();
    }

    private void setImageToDraw() {
        gestureImageArrayList.clear();
        ArrayList<String> gestureImageName = new ArrayList<>();
        gestureImageName.add("heart");
        gestureImageArrayList.add(new GestureImage(R.drawable.heart,
                gestureImageName));

        gestureImageName = new ArrayList<>();
        gestureImageName.add("circle");
        gestureImageArrayList.add(new GestureImage(R.drawable.circle,
                gestureImageName));

        gestureImageName = new ArrayList<>();
        gestureImageName.add("rectangle");
        gestureImageName.add("rectangle2");
        gestureImageArrayList.add(new GestureImage(R.drawable.rectangle,
                gestureImageName));

        gestureImageName = new ArrayList<>();
        gestureImageName.add("square");
        gestureImageName.add("square2");
        gestureImageArrayList.add(new GestureImage(R.drawable.square,
                gestureImageName));

        gestureImageName = new ArrayList<>();
        gestureImageName.add("triangle");
        gestureImageName.add("triangle2");
        gestureImageArrayList.add(new GestureImage(R.drawable.triangle,
                gestureImageName));

        setRandomeImageOnView();

    }

    private void setRandomeImageOnView() {
        Random rand = new Random();
        int index = rand.nextInt(gestureImageArrayList.size());
        drawImage.setImageResource(gestureImageArrayList.get(index).getDrawableImage());

        resImageLetter = (ArrayList<String>) gestureImageArrayList.get(index).getName();
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = alphabetLibrary.recognize(gesture);
        //get the first match
        Prediction prediction = predictions.get(0);
        //set the letter to the matched prediction
        String letter = prediction.name;
        textViewPredictedLetter.setText(letter);

        for (String drawImageName : resImageLetter){
            if (TextUtils.equals(letter, drawImageName)) {
                setRandomeImageOnView();
                Toast.makeText(this, "You are not a robot!", Toast.LENGTH_SHORT).show();
            }
        }

        //******************************
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (int i = 0; i < predictions.size(); i++) {
            prediction = predictions.get(i);
            Log.i(TAG, "Prediction: "
                    + prediction.name
                    + " Score: "
                    + formatter.format(prediction.score));
        }
        Log.i(TAG, "*************************");
    }

    private void setHomeButtonClick() {
        if (getActionBar() != null) {
            getActionBar().setTitle("Gesture");
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
