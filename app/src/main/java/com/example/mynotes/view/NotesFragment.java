package com.example.mynotes.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Adapters.NoteListAdapter;
import com.example.mynotes.R;
import com.example.mynotes.model.Note;
import com.example.mynotes.model.NoteServiceAuth;
import com.example.mynotes.viewModel.NotesViewModel;
import com.example.mynotes.viewModel.NotesViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private NoteListAdapter noteListAdapter;
    private FirebaseUser fUser;
    private FirebaseAuth fAuth;
    private ArrayList<Note> noteList;
    private NotesViewModel notesViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = view.findViewById(R.id.recycleView1);
        floatingActionButton = view.findViewById(R.id.addBtn);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        fAuth = FirebaseAuth.getInstance();
        notesViewModel = new ViewModelProvider(this, new NotesViewModelFactory(new NoteServiceAuth())).get(NotesViewModel.class);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new AddNotesFragment()).commit();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseFirestore = FirebaseFirestore.getInstance();
        noteList = new ArrayList<>();
        noteListAdapter = new NoteListAdapter(getContext(), noteList);
        recyclerView.setAdapter(noteListAdapter);
        fetchNotesFromFireStore();
        return view;
    }

    private void fetchNotesFromFireStore(){
        notesViewModel.readNote();
        notesViewModel.getNoteUserData.observe(getViewLifecycleOwner(), status -> {
            if(status.getStatus() && !status.getNoteList().isEmpty()){
                Toast.makeText(getActivity(), status.getMsg(), Toast.LENGTH_SHORT).show();
                noteListAdapter.setNoteList(status.getNoteList());
            }else {
                Toast.makeText(getActivity(), status.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
