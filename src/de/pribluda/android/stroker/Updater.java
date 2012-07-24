package de.pribluda.android.stroker;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import static de.pribluda.android.stroker.UpdaterState.*;

/**
 * performs graphics update
 */
public class Updater implements SurfaceHolder.Callback {
    public static final String LOG_TAG = "updater";
    final SurfaceHolder surfaceHolder;
    UpdaterState state;

    private int width;
    private int height;

    boolean haveSurface = false;
    private Bitmap field;

    public Updater(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }


    public void updateState() {
        while (RUNNING == state) {
            if(RUNNING == state && haveSurface ) {
                // draw all the stuff  and post on surface
                Log.d(LOG_TAG,"updating state");
            }
            try {
                Log.d(LOG_TAG,"sleeping");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // just ignore
            }
        }

        Log.d(LOG_TAG,"main loop ready");
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d(LOG_TAG,"surface available");
        this.width = width;
        this.height = height;

        haveSurface = true;

        // create bitmap with RGB 565, as this is background, we do not care about
        // transparency
        field = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        state = STOPPED;
        haveSurface = false;
    }


    private void changeState(UpdaterState newState) {
        switch (newState) {
            //  start thread ASAP
            case START:
                Log.d(LOG_TAG,"starting");
                state = RUNNING;
                (new Thread(new Runnable() {
                    public void run() {
                        updateState();
                    }
                })).start();
                break;
            // signal thread to stop
            case STOPPED:
                Log.d(LOG_TAG,"stopping");
                state = STOPPED;
                break;
        }
    }

    public void start() {
        changeState(START);
    }


    public void stop() {
        changeState(STOPPED);
    }
}
