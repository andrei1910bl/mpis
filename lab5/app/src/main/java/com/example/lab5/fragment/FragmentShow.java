package com.example.lab5.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.lab5.NotesDatabaseHelper;
import com.example.lab5.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentShow extends Fragment {
    private ListView listView;
    private NotesDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        listView = view.findViewById(R.id.listView);
        dbHelper = new NotesDatabaseHelper(getContext());
        loadNotes();
        return view;
    }

    private void loadNotes() {
        Cursor cursor = dbHelper.getAllNotes();
        List<String> notesList = new ArrayList<>();
        while (cursor.moveToNext()) {
            notesList.add(cursor.getInt(0) + ". " + cursor.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(adapter);
    }
}
