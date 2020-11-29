package com.example.notesroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> noteList;
    final int REQUEST_CODE = 1;
    private AppDatabase database;
    DatabaseTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = App.instance.getDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        noteList = new ArrayList<>();

        Intent intentAppendActivity = new Intent(this, AppendActivity.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        NoteAdapter noteAdapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(noteAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intentAppendActivity, REQUEST_CODE);
            }
        });
        new DatabaseTask().execute("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            new DatabaseTask().execute(name);
        }
    }


    private class DatabaseTask extends AsyncTask<String, Void, List<Note>> {
        @Override
        protected List<Note> doInBackground(String... names) {
            String param = names[0];
            NoteDao noteDao = database.noteDao();
            if (!param.isEmpty()) {
                Note note = new Note(param);
                noteDao.insert(note);
            }
            return noteDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            noteList.clear();
            for (Note n : notes) {
                noteList.add(n.getName());
            }
        }
    }

}