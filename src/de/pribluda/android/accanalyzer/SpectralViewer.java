package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ToggleButton;
import de.pribluda.android.accmeter.Sampler;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;

/**
 * activity displaying stroke count
 */
public class SpectralViewer extends Activity {

    public static final String LOG_TAG = "strokeCounter";
    SurfaceHolder field;


    Updater updater;
    private Sampler sampler;

    @InjectView(id = R.id.startStopButton)
    ToggleButton recordButton;

    @InjectView(id = R.id.displayField)
    private SurfaceView surfaceView;

    private Recorder recorder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.stroke_counter);
        // inject views
        ViewInjector.startActivity(this);

        field = surfaceView.getHolder();

        sampler = new Sampler(this);
        sampler.setWindowSize(64);
        sampler.setSensorDelay(SensorManager.SENSOR_DELAY_FASTEST);

        recorder = Recorder.getInstance(this, sampler);
        updater = new Updater(field);

        sampler.addSink(updater);
        // add callback
        field.addCallback(updater);

        recordButton.setChecked(recorder.isRecording());

    }


    @Override
    protected void onResume() {
        super.onResume();
        sampler.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (recorder.isRecording()) {
            sampler.stop();
        }
    }


    public void toggleRecord(View view) {
        Log.d(LOG_TAG, "button pressed");
        if (recordButton.isChecked()) {
            recorder.start(sampler);
        } else {
            recorder.stop();
        }

    }
}