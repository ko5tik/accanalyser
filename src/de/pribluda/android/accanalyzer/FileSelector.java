package de.pribluda.android.accanalyzer;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * activity displaying file selector
 *
 * @author Konstantin Pribluda
 */
public class FileSelector extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.id.file_list);
        setListAdapter(new SampleFileAdapter());
    }



    public static class SampleFileAdapter extends BaseAdapter {

        public int getCount() {
            return 0;
        }

        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}