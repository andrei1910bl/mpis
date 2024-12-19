package com.example.lab5.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lab5.NotesDatabaseHelper;
import com.example.lab5.R;

public class FragmentDel extends Fragment {
    private EditText editTextId;
    private NotesDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_del, container, false);
        editTextId = view.findViewById(R.id.editTextId);
        dbHelper = new NotesDatabaseHelper(getContext());

        Button buttonDel = view.findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(editTextId.getText().toString());
                dbHelper.deleteNote(id);
                editTextId.setText("");
                Toast.makeText(getContext(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
