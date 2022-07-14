package com.example.mynotes.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteServiceAuth {

    public static ArrayList<Note> noteList;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseUser fUser = fAuth.getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference docref;

    public void storeNoteInFirebaseStorage(Note note, AuthListener listener){

        String  userId = fAuth.getCurrentUser().getUid();
        docref = firebaseFirestore.collection("Users").document(userId).collection("note").document();
        Map<String, Object> mynote = new HashMap<>();
        mynote.put("title", note.getTitle());
        mynote.put("note", note.getNote());
        mynote.put("date",note.getDate());
        note.setNoteId(docref.getId());
        mynote.put("noteId",note.getNoteId());
      //  Log.d("NoteService","id"+docref.getId());

        docref.set(mynote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    note.setNoteId(docref.getId());
                    listener.onAuthComplete(true, "Data added Successfully");
                }
                else{
                    listener.onAuthComplete(false, "Failed");
                }

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
                        Note note = new Note(documentSnapshot.getString("title"),documentSnapshot.getString("note"),documentSnapshot.getString("date"),documentSnapshot.getString("noteId"));
                        noteList.add(note);
                     //   Log.d("FetchData", "display date"+note.getDate());
                        listener.onAuthComplete(true, "Fetch notes successfully", noteList);
                    }
               }
            }
        });
    }

    public void updateNoteOperation(Note note, AuthListener listener){
        String  userId = fAuth.getCurrentUser().getUid();
        Map<String, Object> notes = new HashMap<>();
        notes.put("title", note.getTitle());
        notes.put("note",note.getNote());
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId).collection("note").document(note.getNoteId());
        documentReference.update(notes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onAuthComplete(true, "note Updated Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onAuthComplete(false,"Failed");
            }
        });
    }
}
