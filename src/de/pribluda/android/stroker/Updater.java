package de.pribluda.android.stroker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import static de.pribluda.android.stroker.UpdaterState.*;

/**
 * performs graphics update
 */
public class Updater implements SurfaceHolder.Callback {
    public static final String LOG_TAG = "strokeCounter.updater";
    public static final int AMOUNT_SPECTRES = 10;
    final SurfaceHolder surfaceHolder;
    UpdaterState state;

    private int width;
    private int height;

    boolean haveSurface = false;
    private Bitmap field;
    private Canvas fieldCanvas;

    // store some back values for energies in circular buffer
    private double[][] energies = new double[AMOUNT_SPECTRES][StrokeDetector.WINDOW_SIZE];
    private int energyIndex;
    private final Paint energyPaint;

    final StrokeDetector detector;
    private final FFT fft;

    public Updater(SurfaceHolder surfaceHolder, StrokeDetector detector) {
        this.surfaceHolder = surfaceHolder;
        this.detector = detector;

        energyPaint = new Paint();
        energyPaint.setColor(0xffffffff);
        energyPaint.setStrokeWidth(1);

        fft = new FFT(StrokeDetector.WINDOW_SIZE);

    }


    public void updateState() {
        while (RUNNING == state) {
            if (RUNNING == state && haveSurface) {

                // calculate fresh energies   and advance index

                fft.fft(detector.getBuffer(), energies[energyIndex]);
                energyIndex++;
                energyIndex %= AMOUNT_SPECTRES;

                // draw all the stuff  and post on surface

                // clear canvas
                fieldCanvas.drawRGB(0, 0, 0);
                // draw individual spectral lines starting from the next one

                int step = width / StrokeDetector.WINDOW_SIZE;

                for (int i = 0; i < AMOUNT_SPECTRES; i++) {
                    double[] energy = energies[(i + energyIndex) % AMOUNT_SPECTRES];
                    int offset = (AMOUNT_SPECTRES - i) * 5;
                    Path path = createPath(step, energy, offset);


                    fieldCanvas.drawPath(path, energyPaint);
                }

                Log.d(LOG_TAG, "updating state");

                // field is prepared  , draw it
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawBitmap(field, 0, 0, null);
                surfaceHolder.unlockCanvasAndPost(canvas);

                Log.d(LOG_TAG, "field drawn");
            }
            try {
                Log.d(LOG_TAG, "sleeping");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // just ignore
            }
        }

        Log.d(LOG_TAG, "main loop ready");
    }

    private Path createPath(int step, double[] energy, int offset) {
        Path path;
        path = new Path();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" path: (" + offset + ":" + offset + ")");
        path.moveTo(offset, offset);

        // iterate over energies
        for (int j = 0; j < energy.length; j++) {
            int x = j * step + offset;
            float y =  (float) (energy[j] + offset);
            stringBuffer.append(" " + j + ": (" + x + ":" + y + ")");
            path.lineTo(x, y);
        }


        Log.d(LOG_TAG, stringBuffer.toString());

        return path;
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d(LOG_TAG, "surface available w:" + width + " h: " + height);
        this.width = width;
        this.height = height;

        haveSurface = true;

        // create bitmap with RGB 565, as this is background, we do not care about
        // transparency
        field = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        fieldCanvas = new Canvas(field);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        state = STOPPED;
        haveSurface = false;
    }


    private void changeState(UpdaterState newState) {
        switch (newState) {
            //  start thread ASAP
            case START:
                Log.d(LOG_TAG, "starting");
                state = RUNNING;
                (new Thread(new Runnable() {
                    public void run() {
                        updateState();
                    }
                })).start();
                break;
            // signal thread to stop
            case STOPPED:
                Log.d(LOG_TAG, "stopping");
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
