package com.example.mynotes.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mynotes.model.Indicate;
import com.example.mynotes.model.NoteServiceAuth;
import com.example.mynotes.model.Note;
import com.example.mynotes.model.Status;

public class NotesViewModel extends ViewModel {

    private NoteServiceAuth noteService;
    private MutableLiveData<Status> _noteUser = new MutableLiveData<>();
    public LiveData<Status> noteUserData = (LiveData<Status>) _noteUser;

    private MutableLiveData<Indicate> _getNoteUser = new MutableLiveData<>();
    public LiveData<Indicate> getNoteUserData = (LiveData<Indicate>) _getNoteUser;

    public NotesViewModel(NoteServiceAuth noteService){
        this.noteService = noteService;
    }

    public void saveNote(Note note){
        noteService.storeNoteInFirebaseStorage(note, (Status, msg) -> _noteUser.setValue(new Status(Status, msg)));
    }

    public void readNote(){
        noteService.readNoteFromFirebaseStorage((status, msg, noteList) -> _getNoteUser.setValue(new Indicate(status,msg,noteList)));
    }
}
