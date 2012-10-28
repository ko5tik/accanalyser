package de.pribluda.android.accanalyzer;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;

/**
 * configuration of application settings
 *
 * @author Konstantin Pribluda
 */
public class Settings extends Activity {

    @InjectView(id = R.id.sampleRate)
    private SeekBar sensorDelayBar;
    @InjectView(id = R.id.selectedSampleRateValue)
    private TextView sensorDelayLabel;

    @InjectView(id = R.id.windowSize)
    private SeekBar windowSizeBar;
    @InjectView(id = R.id.selectedWindowSizeValue)
    private TextView windowSizeLabel;

    @InjectView(id = R.id.updateRate)
    private SeekBar updateRateBar;
    @InjectView(id = R.id.selectedUpdateRateValue)
    private TextView updateRateLabel;

    private static final int[] windowSizes = {16, 32, 64, 128, 256, 512, 1024};

    public static final int[] sampleRates = {SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI, SensorManager.SENSOR_DELAY_GAME, SensorManager.SENSOR_DELAY_FASTEST};
    public static final int[] sampleRateTexts = {R.string.sampleRateNormal, R.string.sampleRateUI, R.string.sampleRateGame, R.string.sampleRateFastest};

    private Configuration configuration;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configuration = Configuration.getInstance(this);

        setContentView(R.layout.settings);

        //  wire views
        ViewInjector.startActivity(this);


        sensorDelayBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                // only if originating from user
                if (fromUser) {
                    updateSampleRate(i);
                }
                // update sample rate label
                sensorDelayLabel.setText(sampleRateTexts[i]);

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        updateRateBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser) {
                    updateUpdateRate(i);
                }
                updateRateLabel.setText("" + i);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        windowSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser) {
                    updateWindowSize(windowSizes[i]);
                }
                sensorDelayLabel.setText("" + windowSizes[i]);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /**
     * set sliders from configuration
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * set configuration values and save to preferences
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    private void updateWindowSize(int windowSize) {
        configuration.setWindowSize(windowSize);
    }

    private void updateUpdateRate(int updateRate) {
        configuration.setUpdateRate(updateRate * 1000);
    }

    /**
     * update sample rate.  choose proper value from array
     *
     * @param selectedSampleRate
     */
    private void updateSampleRate(int selectedSampleRate) {
        configuration.setSampleRate(sampleRates[selectedSampleRate]);
    }


}