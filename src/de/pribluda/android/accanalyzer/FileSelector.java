package de.pribluda.android.accanalyzer;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;

/**
 * activity displaying file selector
 *
 * @author Konstantin Pribluda
 */
public class FileSelector extends ListActivity {
    public static final String LOG_TAG = "strokeCounter.fileSelector";
    public static final String FILE_TAG = "file";
    private SampleFileAdapter fileAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.file_list);
        fileAdapter = new SampleFileAdapter(this);
        setListAdapter(fileAdapter);
    }


    /**
     * populate list with data
     */
    @Override
    protected void onResume() {
        super.onResume();
        final File[] files = ObjectFactory.getRecorder(this).listFiles();
        Log.d(LOG_TAG, "files:" + files);
        Arrays.sort(files);
        fileAdapter.setFileList(files);
    }

    public static class SampleFileAdapter extends BaseAdapter {

        private final Context context;
        private File[] files = new File[0];

        public SampleFileAdapter(Context context) {
            this.context = context;


        }

        public int getCount() {
            return files.length;
        }

        public Object getItem(int i) {
            return files[i];
        }

        /**
         * no row id applicable
         *
         * @param i
         * @return
         */
        public long getItemId(int i) {
            return 0;
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {


            View fileView = view;
            // create view if necessary
            if (null == fileView) {
                fileView = LayoutInflater.from(context).inflate(R.layout.file_entry, viewGroup, false);
            }

            // configure view
            final TextView fileNameView = (TextView) fileView.findViewById(R.id.file_name);

            final File item = (File) getItem(i);

            fileNameView.setText(item.getName());

            fileNameView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    final Intent intent = new Intent(context, SampleDisplay.class);
                    intent.putExtra(FILE_TAG, item.getAbsolutePath());
                    context.startActivity(intent);
                }
            });

            // configure remove button
            final View removeButton = fileView.findViewById(R.id.remove_button);
            removeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    (new AlertDialog.Builder(context)).setMessage(R.string.remove_question)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    removeItem(item);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .create().show();

                }
            });

            return fileView;
        }

        /**
         * set file list and notify of change
         *
         * @param files
         */
        public void setFileList(File[] files) {
            this.files = files;
            notifyDataSetChanged();
        }

        public void removeItem(File file) {
            Log.d(LOG_TAG, "removing item:" + file);

            file.delete();
            setFileList(ObjectFactory.getRecorder(context).listFiles());
        }
    }
}