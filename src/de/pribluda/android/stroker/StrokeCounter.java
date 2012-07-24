package de.pribluda.android.stroker;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * activity displaying stroke count
 */
public class StrokeCounter extends Activity {

    SurfaceHolder field;


    boolean surfaceReady = false;

    Updater updater;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stroke_counter);


        // rertieve game field
        final SurfaceView surfaceView = (SurfaceView) findViewById(R.id.displayField);
        field = surfaceView.getHolder();

        updater = new Updater(field);
        // add callback
        field.addCallback(updater);
    }


    @Override
    protected void onResume() {
        super.onResume();
        updater.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        updater.stop();
    }


    /**
     * start update process for graphs
     */
    private void startUpdating() {
    }


    private void stopUpdating() {
    }

}