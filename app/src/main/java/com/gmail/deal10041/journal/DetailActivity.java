package com.gmail.deal10041.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get clicked entry
        Intent intent = getIntent();
        JournalEntry entry = (JournalEntry) intent.getSerializableExtra("clicked_entry");

        // set title
        TextView title = findViewById(R.id.title);
        title.setText(entry.getTitle());

        // set date
        TextView date = findViewById(R.id.date);
        date.setText(entry.getTimestamp());

        // set content
        TextView content = findViewById(R.id.content);
        content.setText(entry.getContent());

        // set mood
        TextView mood = findViewById(R.id.mood);
        mood.setText(entry.getMood());
    }
}
