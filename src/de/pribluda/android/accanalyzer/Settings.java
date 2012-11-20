package de.pribluda.android.accanalyzer;

import android.R;
import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import de.pribluda.android.andject.InjectView;
import de.pribluda.android.andject.ViewInjector;

import java.util.HashMap;
import java.util.Map;

/**
 * configuration of application settings
 *
 * @author Konstantin Pribluda
 */
public class Settings extends PreferenceActivity {


    private static final int[] windowSizesLookup = {16, 32, 64, 128, 256, 512, 1024};
    private final static Map<Integer, Integer> reverseWindowSizesLookup = new HashMap<Integer, Integer>() {{
        put(16, 0);
        put(32, 1);
        put(64, 2);
        put(128, 3);
        put(256, 4);
        put(512, 5);
        put(1024, 6);
    }};

    public static final int[] sensorDelayLookup = {SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI, SensorManager.SENSOR_DELAY_GAME, SensorManager.SENSOR_DELAY_FASTEST};
    public static final Map<Integer, Integer> reverseSensorDelayLookup = new HashMap() {
        {
            put(SensorManager.SENSOR_DELAY_NORMAL, 0);
            put(SensorManager.SENSOR_DELAY_UI, 1);
            put(SensorManager.SENSOR_DELAY_GAME, 2);
            put(SensorManager.SENSOR_DELAY_FASTEST, 3);
        }
    };
    public static final int[] sampleRateTexts = {R.string.sampleRateNormal, R.string.sampleRateUI, R.string.sampleRateGame, R.string.sampleRateFastest};


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }
}