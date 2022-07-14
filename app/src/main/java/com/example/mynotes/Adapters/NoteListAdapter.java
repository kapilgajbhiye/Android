package com.example.mynotes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.model.Note;
import com.example.mynotes.view.EditNoteFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> implements Filterable{
     Context context;
     ArrayList<Note> noteArrayList = new ArrayList<>();
     ArrayList<Note> listOfNotes;
     ArrayList<Note> backupList;

    public NoteListAdapter(Context context, ArrayList<Note> noteArrayList) {
        this.context = context;
        this.noteArrayList = noteArrayList;
        this.listOfNotes = new ArrayList<>();
//        backupList = noteArrayList;
//        Log.d("listData","NoteArrayData"+noteArrayList);
    }

    public NoteListAdapter() {
    }

    public void setNoteList(ArrayList<Note> noteList){
        this.noteArrayList = noteList;
        listOfNotes.addAll(noteList);
//        backupList = noteList;
//        Log.d("noteList","data"+backupList);
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
            Note notes = noteArrayList.get(position);
            holder.title.setText(notes.getTitle());
            holder.note.setText(notes.getNote());
            holder.date.setText(notes.getDate());

        holder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(context, holder.menuIcon);
                popup.inflate(R.menu.option_menu);

               popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()){
                           case R.id.editNote:
                               AppCompatActivity activity =(AppCompatActivity)view.getContext();
                               activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditNoteFragment(notes.getTitle(),notes.getNote(),notes.getNoteId())).commit();
                               break;
                           case R.id.delNote:
                               noteArrayList.remove(notes.getNoteId());
                               notifyDataSetChanged();

                               break;
                       }
                       return false;
                   }
               });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Note> filteredData =  new ArrayList<>();
            if(keyword.toString().isEmpty())
                filteredData.addAll(backupList);

            else{
                for(Note obj : backupList){
                    if(obj.getTitle().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filteredData.add(obj);
                    Log.d("filterData","data"+backupList);
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listOfNotes.clear();
            listOfNotes.addAll((Collection<Note>)results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, note, date;
        ImageView menuIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            note = itemView.findViewById(R.id.textView_notes);
            date = itemView.findViewById(R.id.textView_date);
            menuIcon = itemView.findViewById(R.id.imageView_pen);
        }
    }
}
