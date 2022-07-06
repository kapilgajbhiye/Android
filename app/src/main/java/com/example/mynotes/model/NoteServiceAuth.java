package com.example.mynotes.model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Adapters.NoteListAdapter;
import com.example.mynotes.view.NotesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteServiceAuth {

    public static ArrayList<Note> noteList;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseUser fUser = fAuth.getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public void storeNoteInFirebaseStorage(Note note, AuthListener listener){

        String  userId = fAuth.getCurrentUser().getUid();
        DocumentReference docref = firebaseFirestore.collection("Users").document(userId).collection("note").document();
        Map<String, Object> mynote = new HashMap<>();
        mynote.put("title", note.getTitle());
        mynote.put("note", note.getNote());
        mynote.put("date",note.getDate());
        docref.set(mynote).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onAuthComplete(true, "Data added Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
           listener.onAuthComplete(false, "Failed");
            }
        });
    }

    public void readNoteFromFirebaseStorage(NoteAuthListener listener){
        String currentId = fUser.getUid();
        firebaseFirestore.collection("Users").document(currentId).collection("note").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                noteList = new ArrayList<>();
               if(task.isSuccessful() && task.getResult() != null ){
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        Note note = new Note(documentSnapshot.getString("title"),documentSnapshot.getString("note"),documentSnapshot.getDate("date"));
                        noteList.add(note);
                    //    Log.d("FetchData", "display date"+note.getDate());
                        listener.onAuthComplete(true, "Fetch notes successfully", noteList);
                    }
               }
            }
        });
    }
}
