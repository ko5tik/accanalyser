package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.CheckBox;
import android.widget.TextView;
import de.pribluda.android.accmeter.Sampler;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;

/**
 * activity displaying stroke count
 */
public class SpectralViewer extends Activity {



    public static final int[] sampleRateTexts = {R.string.sampleRateFastest, R.string.sampleRateGame,R.string.sampleRateUI,R.string.sampleRateNormal};


    public static final String LOG_TAG = "strokeCounter";
    SurfaceHolder field;


    Updater updater;
    private Sampler sampler;

    @InjectView(id = R.id.startStopButton)
    CheckBox recordButton;

    @InjectView(id = R.id.displayField)
    private SurfaceView surfaceView;

    @InjectView(id = R.id.windowSizeLabel)
    TextView windowSizeLabel;

    @InjectView(id = R.id.updateIntervalLabel)
    TextView updateIntervalLabel;

    @InjectView(id = R.id.sensorRateLabel)
    TextView sensorDelayLabel;

    private Recorder recorder;

    Configuration configuration;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configuration = Configuration.getInstance(this);

        setContentView(R.layout.stroke_counter);
        // inject views
        ViewInjector.startActivity(this);

        field = surfaceView.getHolder();

        sampler = ObjectFactory.getSampler(this);

        recorder = ObjectFactory.getRecorder(this);

        updater = new Updater(field);

        sampler.addSink(updater);


        recordButton.setChecked(recorder.isRecording());

    }


    @Override
    protected void onResume() {
        super.onResume();

        try {
            configuration.load(this);
        } catch (IllegalAccessException e) {
           Log.e(LOG_TAG,"shall not happen, something nasty went wrong");
        }

        Log.d(LOG_TAG,"confifuration: " + configuration);
        // set up labels for display
        windowSizeLabel.setText(""  + configuration.getWindowSize());
        updateIntervalLabel.setText("" + configuration.getUpdateRate());

        sensorDelayLabel.setText(sampleRateTexts[configuration.getSensorDelay()]);

        if (sampler.getWindowSize() != configuration.getWindowSize()
                || sampler.getSensorDelay() != configuration.getSensorDelay()
                || sampler.getUpdateDelay() != configuration.getUpdateRate()) {
            new Thread(new Runnable() {
                public void run() {
                    sampler.stop();
                    sampler.setSensorDelay(configuration.getSensorDelay());
                    sampler.setWindowSize(configuration.getWindowSize());
                    sampler.setUpdateDelay(configuration.getUpdateRate());

                    sampler.start();
                }
            }).start();

        } else {
            sampler.start();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!recorder.isRecording()) {
            sampler.stop();
        }
    }


    /**
     * start and stop recording service
     *
     * @param view
     */
    public void toggleRecord(View view) {
        Log.d(LOG_TAG, "button pressed");
        if (recordButton.isChecked()) {
            recorder.start(sampler);
        } else {
            recorder.stop();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.file_list_action:
                startActivity(new Intent(this, FileSelector.class));
                break;
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                break;
        }
        return false;
    }
}