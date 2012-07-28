package de.pribluda.android.stroker;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * activity displaying stroke count
 */
public class StrokeCounter extends Activity {

    public static final String LOG_TAG = "strokeCounter";
    SurfaceHolder field;


    boolean surfaceReady = false;

    Updater updater;
    private StrokeDetector detector;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stroke_counter);


        // rertieve game field
        final SurfaceView surfaceView = (SurfaceView) findViewById(R.id.displayField);
        field = surfaceView.getHolder();

        detector = new StrokeDetector(this);

        updater = new Updater(field, detector);
        // add callback
        field.addCallback(updater);
    }


    @Override
    protected void onResume() {
        super.onResume();
        detector.start();
        updater.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        detector.stop();
        updater.stop();
    }



}