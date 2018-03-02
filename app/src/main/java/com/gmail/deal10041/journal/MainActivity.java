package com.gmail.deal10041.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private EntryDatabase db;
    private EntryAdapter adapter;
    private boolean showFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set listeners to listview
        ListView list = findViewById(R.id.list);
        list.setOnItemClickListener(new ListItemClickListener());
        list.setOnItemLongClickListener(new ListLongClickListener());

        // fill listview
        db = EntryDatabase.getInstance(this);
        Cursor cursor = db.selectAll();
        adapter = new EntryAdapter(this, cursor);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        // set current state of showFavorites
        menu.findItem(R.id.showStars).setChecked(showFavorites);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // get first visible item position
        ListView list = findViewById(R.id.list);
        int pos = list.getFirstVisiblePosition();

        // save showFavorites
        outState.putInt("first_Position", pos);
        outState.putBoolean("show_Favorites", showFavorites);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // set first visible item position
        int pos = savedInstanceState.getInt("first_Position");
        ListView list = findViewById(R.id.list);
        list.setSelection(pos);

        // set showFavorites
        showFavorites = savedInstanceState.getBoolean("show_Favorites");

        // show favorite list
        if (showFavorites) {
            updateFavData();
        }
    }

    public void floatingButtonClicked(View v) {

        // launch input activity
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }

    public void starClicked(View v) {

        // get view position
        ListView list = findViewById(R.id.list);
        int pos = list.getPositionForView(v);

        // get entry id
        Cursor cursor = (Cursor) list.getItemAtPosition(pos);
        long id = cursor.getLong(cursor.getColumnIndex("_id"));

        // get starred boolean
        ToggleButton button = (ToggleButton) v;
        boolean starred = button.isChecked();

        // update database
        EntryDatabase.getInstance(getApplicationContext()).star(id, starred);

        // update favorites list if on
        if (showFavorites) {
            updateFavData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.isChecked()) {

            item.setChecked(false);
            showFavorites = false;
            updateData();
            return true;
        }
        else {

            item.setChecked(true);
            showFavorites = true;
            updateFavData();
            return true;
        }
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // get cursor
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);

            JournalEntry entry = makeEntry(cursor);

            // launch detail activity
            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtra("clicked_entry", entry);
            startActivity(intent);
        }
    }

    private class ListLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // get cursor
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);

            // get id of item
            int column = cursor.getColumnIndex("_id");
            long id = cursor.getLong(column);

            // remove item
            EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
            db.delete(id);

            // update the appropriate data
            if(showFavorites) {
                updateFavData();
            }
            else {
                updateData();
            }

            return true;

        }
    }

    private void updateData() {

        // update cursor
        Cursor cursor = db.selectAll();
        adapter.swapCursor(cursor);
    }

    private void updateFavData() {

        // update cursor
        Cursor cursor = db.selectStarred();
        adapter.swapCursor(cursor);
    }

    private JournalEntry makeEntry(Cursor cursor) {

        // get entry content
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String mood = cursor.getString(cursor.getColumnIndex("mood"));
        String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));

        // fill entry
        JournalEntry entry = new JournalEntry(title, content, mood);
        entry.setTimestamp(timestamp);

        return entry;
    }
}
