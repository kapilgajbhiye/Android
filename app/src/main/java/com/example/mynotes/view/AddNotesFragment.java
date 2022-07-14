package com.example.mynotes.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.model.NoteServiceAuth;
import com.example.mynotes.model.Note;
import com.example.mynotes.viewModel.NotesViewModel;
import com.example.mynotes.viewModel.NotesViewModelFactory;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNotesFragment extends Fragment {
    private EditText editText_title, editText_notes;
    private ImageView imageView_save, imageView_Back;
    private NotesViewModel notesViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_notes, container, false);
        imageView_save = view.findViewById(R.id.imageView_saves);
        editText_title = view.findViewById(R.id.editText_Title);
        editText_notes = view.findViewById(R.id.editText_note);
        imageView_Back = view.findViewById(R.id.imageView_back);
        notesViewModel = new ViewModelProvider(this, new NotesViewModelFactory(new NoteServiceAuth())).get(NotesViewModel.class);

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), " Data save", Toast.LENGTH_SHORT).show();
                checkUserDataAddInFirestore();
                getFragmentManager().beginTransaction().replace(R.id.container, new Home()).commit();
            }
        });

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new Home()).commit();
            }
        });
        return view;
    }

    public void checkUserDataAddInFirestore(){
        String title = editText_title.getText().toString();
        String context = editText_notes.getText().toString();


        if(title.isEmpty() || context.isEmpty()){
            Toast.makeText(getActivity(), "please add some notes", Toast.LENGTH_SHORT).show();
            return;
        }
//        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MM yyyy HH:mm a");
//        Date date = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        notesViewModel.saveNote(new Note(title, context, date));
        notesViewModel.noteUserData.observe(AddNotesFragment.this, status -> {
            if(status.getStatus()){
                Toast.makeText(getActivity(), status.getMsg(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), status.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
