package com.example.mynotes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.model.Note;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {
     Context context;
     ArrayList<Note> noteArrayList = new ArrayList<>();
     ArrayList<Note> listOfNotes;

    public NoteListAdapter(Context context, ArrayList<Note> noteArrayList) {
        this.context = context;
        this.noteArrayList = noteArrayList;
        this.listOfNotes = new ArrayList<>();
    }

    public void setNoteList(ArrayList<Note> noteList){
        this.noteArrayList = noteList;
        listOfNotes.addAll(noteList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.MyViewHolder holder, int position) {
            Note note = noteArrayList.get(position);
            holder.title.setText(note.getTitle());
            holder.note.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, note;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            note = itemView.findViewById(R.id.textView_notes);
        }
    }
}
