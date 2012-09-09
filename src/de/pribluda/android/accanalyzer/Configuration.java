package de.pribluda.android.accanalyzer;

import android.content.Context;
import android.util.Log;
import de.pribluda.android.andject.PreferenceInjector;

/**
 * system configuration.  context based singleton
 *
 * @author Konstantin Pribluda
 */
public class Configuration {
    public static final String LOG_TAG = "strokeCounter.configuration";
    private static final String PREFERENCE_TAG = Configuration.class.getCanonicalName() ;



    private static Configuration instance;

    public Configuration(Context context) {
        try {
            PreferenceInjector.inject(this, context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE));
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "exception while reading configuration", e);
        }
    }


    public Configuration getInstance(Context context) {

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


}
