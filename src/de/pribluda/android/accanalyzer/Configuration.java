package de.pribluda.android.accanalyzer;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;
import de.pribluda.android.andject.InjectPreference;
import de.pribluda.android.andject.PreferenceInjector;

/**
 * system configuration.  context based singleton
 *
 * @author Konstantin Pribluda
 */
public class Configuration {
    public static final String LOG_TAG = "strokeCounter.configuration";
    private static final String PREFERENCE_TAG = Configuration.class.getCanonicalName();

    @InjectPreference
    private int sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;
    @InjectPreference
    private int windowSize = 128;
    @InjectPreference
    private int updateRate = 1000;

    private static Configuration instance;

    public Configuration(Context context) {
        try {
            load(context);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "exception while reading configuration", e);
        }
    }

    public void load(Context context) throws IllegalAccessException {
        PreferenceInjector.inject(this, context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE));
    }


    public static Configuration getInstance(Context context) {

        if (null == instance) {
            instance = new Configuration(context);
        }

        return instance;
    }


    public int getSensorDelay() {
        return sensorDelay;
    }

    public void setSensorDelay(int sensorDelay) {
        this.sensorDelay = sensorDelay;
    }

    public int getUpdateRate() {
        return updateRate;
    }

    public void setUpdateRate(int updateRate) {
        this.updateRate = updateRate;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "sensorDelay=" + sensorDelay +
                ", windowSize=" + windowSize +
                ", updateRate=" + updateRate +
                '}';
    }
}
