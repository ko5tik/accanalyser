package de.pribluda.android.accanalyzer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.pribluda.android.accmeter.Sampler;

/**
 * service performing recording of samples in background
 *
 * @author Konstantin Pribluda
 */
public class RecordService extends Service {

    public static final String LOG_TAG = "strokeCounter.service";
    private Sampler sampler;
    private Recorder recorder;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sampler = Sampler.getInstance(this);
        recorder = Recorder.getInstance(this, sampler);
    }

    /**
     * we were started
     *
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(LOG_TAG, "service start");


        recorder.start(sampler);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "service end");

        recorder.stop();
    }
}
