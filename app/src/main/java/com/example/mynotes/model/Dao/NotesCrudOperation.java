package com.example.mynotes.model.Dao;

public interface NotesCrudOperation {

    public void storeNotes();
    public void readNotes();
    public void updatesNotes();
    public void deleteNotes();
}
