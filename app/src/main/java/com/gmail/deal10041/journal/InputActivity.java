package com.gmail.deal10041.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    public void addEntry(View v) {

        // get all entry values
        EditText text = findViewById(R.id.inputTitle);
        String title = text.getText().toString();
        text = findViewById(R.id.inputContent);
        String content = text.getText().toString();
        text = findViewById(R.id.inputMood);
        String mood = text.getText().toString();

        // create new entry
        JournalEntry entry = new JournalEntry(title, content, mood);

        // insert entry into database
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
        db.insert(entry);

        // launch main activity
        Intent intent = new Intent(InputActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
