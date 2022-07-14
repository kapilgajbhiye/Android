package com.example.mynotes.model;

public class Note {
   private String  title, note;
   String date;
   String noteId;

   public Note() {
    }

    public Note(String title, String note, String date) {
        this.title = title;
        this.note = note;
        this.date = date;
    }

    public  Note(String title, String note, String date, String noteId){
       this.title = title;
       this.note = note;
       this.date = date;
       this.noteId = noteId;
    }

    public Note(String title, String notesContent) {
        this.title = title;
        this.note = notesContent;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
