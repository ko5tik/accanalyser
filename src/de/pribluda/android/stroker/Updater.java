package de.pribluda.android.stroker;

import android.view.Surface;
import android.view.SurfaceHolder;

import static de.pribluda.android.stroker.UpdaterState.*;

/**
 * performs graphics update
 */
public class Updater implements SurfaceHolder.Callback {
    final SurfaceHolder surfaceHolder;
    UpdaterState state;

    private int width;
    private int height;

    boolean haveSurface = false;

    public Updater(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }


    public void updateState() {
        while (RUNNING == state) {
            if(RUNNING == state && haveSurface ) {
                // draw all the stuff  and post on surface
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // just ignore
            }
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

        this.width = width;
        this.height = height;

        haveSurface = true;

    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        state = STOPPED;
        haveSurface = false;
    }


    private void changeState(UpdaterState newState) {
        switch (newState) {
            //  start thread ASAP
            case START:
                state = RUNNING;
                (new Thread(new Runnable() {
                    public void run() {
                        updateState();
                    }
                })).start();
                break;
            // signal thread to stop
            case STOPPED:
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
