package com.example.mynotes.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynotes.model.NoteServiceAuth;

public class NotesViewModelFactory implements ViewModelProvider.Factory {

    private NoteServiceAuth noteService;

    public  NotesViewModelFactory(NoteServiceAuth noteService){
        this.noteService = noteService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesViewModel(noteService);
    }

}
