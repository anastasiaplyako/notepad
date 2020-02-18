package com.example.notepad;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

public class EditAct extends AppCompatActivity {
    private EditText inputNote;
    private NotesDao dao;
    private Note temp;
    public static final String NOTE_EXTRA_Key = "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);
        Toolbar toolbar = findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar);
      /*  Button bBold = findViewById(R.id.bBold);
        Button bItalics = findViewById(R.id.bItalics);*/

        /*bBold.setOnClickListener(this);
        bItalics.setOnClickListener(this);*/
        inputNote = findViewById(R.id.edit_note);
        dao = NotesDB.getInstance(this).notesDao();
        if (getIntent().getExtras() != null) {
            int id = getIntent().getExtras().getInt(NOTE_EXTRA_Key, 0);
            temp = dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
        } else {
            inputNote.setFocusable(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edite_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note)
            save();
        return super.onOptionsItemSelected(item);
    }

  /*  private int flag = 0;
    //Spannable str;
    @Override*/
    /*public void onClick(View v){
        flag = 1;
        switch (v.getId()) {
            case R.id.bBold: {
                int lastCursorPosition = inputNote.getSelectionStart();
                int endOfString = inputNote.getText().toString().length();
                StyleSpan ss = new StyleSpan(Typeface.BOLD);
                SpannableStringBuilder text = (SpannableStringBuilder) inputNote.getText();
                //temp.setNoteText1(text, ss, lastCursorPosition, endOfString);
                //dao.updateNote(temp);
                //text.setSpan(ss, lastCursorPosition, endOfString, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                inputNote.getText().setSpan(ss, lastCursorPosition, endOfString, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //inputNote.setTextColor(Color.rgb(200,0,0));
            }
            case R.id.bItalics: {
                int lastCursorPosition = inputNote.getSelectionStart();
                int endOfString = inputNote.getText().toString().length();
                StyleSpan ss = new StyleSpan(Typeface.ITALIC);
                inputNote.getText().setSpan(ss, lastCursorPosition, endOfString, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }*/

    private void save() {
        String text;
        text = inputNote.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (!text.isEmpty()) {
                    long date = new Date().getTime();

                    if (temp == null) {
                        temp = new Note(text, date);
                        dao.insertNote(temp);
                    } else {
                        temp.setNoteText(text);
                        temp.setNoteDate(date);
                        dao.updateNote(temp);
                    }
                finish();
            }
        }

    }
}
