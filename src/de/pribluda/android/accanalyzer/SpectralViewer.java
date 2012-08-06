package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import de.pribluda.android.accmeter.Sampler;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;

/**
 * activity displaying stroke count
 */
public class SpectralViewer extends Activity {

    public static final String LOG_TAG = "strokeCounter";
    SurfaceHolder field;


    boolean surfaceReady = false;

    Updater updater;
    private Sampler detector;


    @InjectView(id = R.id.displayField)
    private SurfaceView surfaceView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stroke_counter);
        // inject views
        ViewInjector.startActivity(this);

        field = surfaceView.getHolder();

        detector = new Sampler(this);
        detector.setWindowSize(64);

        updater = new Updater(field);

        detector.addSink(updater);
        // add callback
        field.addCallback(updater);
    }


    @Override
    protected void onResume() {
        super.onResume();
        detector.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        detector.stop();
    }


}