package com.example.mynotes.model;

import java.util.ArrayList;

public interface NoteAuthListener {
    void onAuthComplete(boolean status, String msg, ArrayList<Note> noteList);
}
