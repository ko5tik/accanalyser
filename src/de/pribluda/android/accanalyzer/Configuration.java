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
    int sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;
    @InjectPreference
    int windowSize = 128;
    @InjectPreference
    int updateRate = 1000;

    private static Configuration instance;

    public Configuration(Context context) {
        try {
            PreferenceInjector.inject(this, context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE));
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "exception while reading configuration", e);
        }
    }


    public static Configuration getInstance(Context context) {

        if (null == instance) {
            instance = new Configuration(context);
        }

        return instance;
    }


    /**
     * save preferences
     */
    public void save(Context context) {
        try {
            PreferenceInjector.eject(this, context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE));
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "exception while saving configuration", e);
        }
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


}
