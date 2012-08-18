package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.gson.stream.JsonReader;
import de.pribluda.android.accmeter.Sample;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;
import de.pribluda.android.jsonmarshaller.JSONUnmarshaller;

import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * displays recorded sample
 *
 * @author Konstantin Pribluda
 */
public class SampleDisplay extends Activity implements SeekBar.OnSeekBarChangeListener {
    public static final String LOG_TAG = "strokeCounter.sampleDisplay";
    @InjectView(id = R.id.seekBar)
    SeekBar seekBar;

    @InjectView(id = R.id.currentSample)
    TextView currentSample;

    @InjectView(id = R.id.sliderControl)
    View sliderControl;


    @InjectView(id = R.id.renderingSurface)
    private SurfaceView surfaceView;

    private List<Sample> samples;

    private int sampleIndex;
    private SurfaceHolder field;
    private Updater updater;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_viewer);

        // inject views
        ViewInjector.startActivity(this);


        seekBar.setOnSeekBarChangeListener(this);

        sliderControl.setEnabled(false);


        field = surfaceView.getHolder();
        updater = new Updater(field);
        field.addCallback(updater);



    }

    /**
     * resume and load from backend storage
     */
    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent = getIntent();
        Log.d(LOG_TAG, "started from intent:" + intent);

        final String fileName = intent.getStringExtra(FileSelector.FILE_TAG);

        final ProgressDialog dialog = ProgressDialog.show(this, "please wait", "loading from file " + fileName, true, false);

        // load in the separate thread
        new Thread(new Runnable() {
            public void run() {
                // load samples from file


                Log.d(LOG_TAG, "file intent:" + fileName);


                // read all the data

                try {
                    final JsonReader jsonReader = new JsonReader(new FileReader(new File(fileName)));

                    samples = JSONUnmarshaller.unmarshallArray(jsonReader, Sample.class);

                    setupUI();
                    Log.d(LOG_TAG, "read samples:" + samples.size());

                } catch (Exception e) {
                    Log.e(LOG_TAG, "error while reading json:", e);
                }

                dialog.dismiss();
            }
        }).start();

    }

    /**
     * setup user interface after load completion
     */
    private void setupUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                seekBar.setMax(samples.size() - 1);
                sliderControl.setEnabled(true);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        samples = null;
    }

    public void first(View item) {
        seekBar.setProgress(0);
    }


    public void last(View item) {
        seekBar.setProgress(samples.size() - 1);
    }


    public void next(View item) {
        if (sampleIndex < samples.size() - 1) {
            seekBar.setProgress(sampleIndex + 1);
        }
    }

    public void previous(View item) {
        if (sampleIndex > 0) {
            seekBar.setProgress(sampleIndex - 1);
        }
    }


    /**
     * react on selection
     *
     * @param seekBar
     * @param i
     * @param byUser
     */
    public void onProgressChanged(SeekBar seekBar, int i, boolean byUser) {
        sampleIndex = i;

        if(sampleIndex < 0 )
            sampleIndex = 0;

        if(sampleIndex > samples.size() -1)
            sampleIndex = samples.size() -1;

        currentSample.setText("" + sampleIndex);

        updater.put(samples.get(sampleIndex));

    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}