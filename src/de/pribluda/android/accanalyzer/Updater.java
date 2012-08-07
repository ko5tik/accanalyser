package de.pribluda.android.accanalyzer;

import android.graphics.*;
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
            fieldCanvas.drawRGB(0,0,0);


            // draw individual spectral lines starting from the actual
            for (int i = 0; i < AMOUNT_SPECTRES; i++) {
                final int idx = (energyIndex + i + 1) % AMOUNT_SPECTRES;
                //    Log.d(LOG_TAG,"index: " + idx) ;

                final Sample sample = samples[idx];
                if (sample != null) {
                    double[] real = sample.getReal();
                    double[] imaginary = sample.getImaginary();

                    double energy[] = new double[real.length / 2];
                    double phase[] = new double[real.length / 2];

                    //calculate energy    and phase
                    for (int j = 0; j < energy.length; j++) {
                        int resultIndex = real.length - j - 1;
                        // energy
                        energy[j] = Math.sqrt(real[resultIndex] * real[resultIndex] + imaginary[resultIndex] * imaginary[resultIndex]);
                        // phase
                        phase[j] = Math.atan2(real[resultIndex], imaginary[resultIndex]);
                    }

                    int offset = (AMOUNT_SPECTRES - i - 1) * BASE_OFFSET;
                    float step = ((float) width - (AMOUNT_SPECTRES - 1) * BASE_OFFSET) / energy.length;


                    Path path = createPath(step, energy, offset);

                    int[] colors = new int[phase.length];

                    float  hsv[] = new float[3];
                    hsv[1] = 1;
                    hsv[2] = 1;
                    for (int j = 0; j < colors.length; j++) {
                        hsv[0] = (float) ((phase[j] + Math.PI/2) * 360 / Math.PI);
                        colors[j] = Color.HSVToColor(0xf0,hsv);
                    }

                    energyFill.setShader(new LinearGradient(offset,height,offset + step * colors.length,height,colors,null, Shader.TileMode.CLAMP));

                    fieldCanvas.drawPath(path, energyFill);
                    fieldCanvas.drawPath(path, energyLine);
                }
            }


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
    private Path createPath(float step, double[] energy, int offset) {
        Path path;
        path = new Path();

        // move to start position
        path.moveTo(offset, height - offset);

        // iterate over energies
        for (int j = 0; j < energy.length; j++) {
            float x = j * step + offset;
            float y = height - (float) (energy[j] + offset);

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
        Log.d(LOG_TAG, "sample received, rate:" + sample.getSampleRate());
        samples[energyIndex] = sample;
        updateState();
        energyIndex++;
        energyIndex %= AMOUNT_SPECTRES;
    }
}
