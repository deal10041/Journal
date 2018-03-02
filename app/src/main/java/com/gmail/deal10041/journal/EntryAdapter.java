package com.gmail.deal10041.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Dylan Wellner on 27-2-2018.
 * Implements an adapter for the listview of main activity
 */

public class EntryAdapter extends ResourceCursorAdapter{

    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entry_row, cursor, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // add title to entry
        int index = cursor.getColumnIndex("title");
        TextView title = view.findViewById(R.id.title);
        title.setText(cursor.getString(index));

        // add timestamp to entry
        index = cursor.getColumnIndex("timestamp");
        TextView timestamp = view.findViewById(R.id.timestamp);
        timestamp.setText(cursor.getString(index));

        // add content to entry
        index = cursor.getColumnIndex("content");
        TextView content = view.findViewById(R.id.content);
        content.setText(cursor.getString(index));

        // set star state
        index = cursor.getColumnIndex("starred");
        ToggleButton button = view.findViewById(R.id.star);
        button.setChecked(cursor.getInt(index) != 0);
    }
}
