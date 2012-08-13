package de.pribluda.android.accanalyzer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * service performing recording of samples in background
 *
 * @author Konstantin Pribluda
 */
public class RecordService extends Service {

    public static final String LOG_TAG = "strokeCounter.service";

    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * we were started
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
