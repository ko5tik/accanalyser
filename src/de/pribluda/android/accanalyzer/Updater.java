package de.pribluda.android.accanalyzer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.SurfaceHolder;
import de.pribluda.android.accmeter.Sample;
import de.pribluda.android.accmeter.SampleSink;

/**
 * receives processed samples and updates graphichs
 */
public class Updater implements SurfaceHolder.Callback, SampleSink {
    public static final String LOG_TAG = "strokeCounter.updater";

    // amount of spectral lines to display
    public static final int AMOUNT_SPECTRES = 25;
    public static final int BASE_OFFSET = 3;
    final SurfaceHolder surfaceHolder;

    // size of surface holder
    private int width;
    private int height;

    boolean haveSurface = false;
    private Bitmap field;
    private Canvas fieldCanvas;

    private int energyIndex;
    private Sample[] samples = new Sample[AMOUNT_SPECTRES];

    private final Paint energyLine;
    private final Paint energyFill;


    public Updater(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;


        energyLine = new Paint();
        energyLine.setColor(0xffffffff);
        energyLine.setStrokeWidth(2);
        energyLine.setStyle(Paint.Style.STROKE);

        energyFill = new Paint();
        energyFill.setColor(0x60808080);
        energyFill.setStrokeWidth(2);
        energyFill.setStyle(Paint.Style.FILL);

    }


    /**
     * update display state
     */
    public void updateState() {
        if (haveSurface) {


            // draw all the stuff  and post on surface

            // clear canvas
            fieldCanvas.drawRGB(0, 0, 0);


            // draw individual spectral lines starting from the actual
            for (int i = 0; i < AMOUNT_SPECTRES; i++) {
                final Sample sample = samples[(i + energyIndex) % AMOUNT_SPECTRES];
                if (sample != null) {
                    double[] real = sample.getReal();
                    double[] imaginary = sample.getImaginary();

                    double energy[] = new double[real.length / 2];

                    //calculate energy
                    for (int j = 0; j < energy.length; j++) {
                        int resultIndex = real.length - j - 1;
                        energy[j] = Math.sqrt(real[resultIndex] * real[resultIndex] + imaginary[resultIndex] * imaginary[resultIndex]);
                    }

                    int offset = (AMOUNT_SPECTRES - i) * BASE_OFFSET;
                    int step = (width - offset * AMOUNT_SPECTRES) / energy.length;


                    Path path = createPath(step, energy, offset);


                    fieldCanvas.drawPath(path, energyFill);
                    fieldCanvas.drawPath(path, energyLine);
                }
            }

            Log.d(LOG_TAG, "updating state");

            // field is prepared  , draw it
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(field, 0, 0, null);
            surfaceHolder.unlockCanvasAndPost(canvas);

            Log.d(LOG_TAG, "field drawn");
        }
    }

    /**
     * create closed path from energy and values
     *
     * @param step   step for one sample
     * @param energy energy values
     * @param offset sample offset
     * @return
     */
    private Path createPath(int step, double[] energy, int offset) {
        Path path;
        path = new Path();

        // move to start position
        path.moveTo(offset, height - offset);

        // iterate over energies
        for (int j = 0; j < energy.length; j++) {
            int x = j * step + offset;
            float y = height - (float) (energy[j] + offset);
            //    stringBuffer.append(" " + j + ": (" + x + ":" + y + ")");
            path.lineTo(x, y);
        }
        path.lineTo(energy.length * step + offset, height - offset);

        path.setFillType(Path.FillType.EVEN_ODD);


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

        haveSurface = false;
    }


    /**
     * receive sample for display
     *
     * @param sample
     */
    public void put(Sample sample) {
        Log.d(LOG_TAG, "sample received");
        samples[energyIndex] = sample;
        updateState();
        energyIndex++;
        energyIndex %= AMOUNT_SPECTRES;
    }
}
