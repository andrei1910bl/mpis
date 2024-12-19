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

public class FragmentAdd extends Fragment {
    private EditText editTextDescription;
    private NotesDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        dbHelper = new NotesDatabaseHelper(getContext());

        Button buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editTextDescription.getText().toString();
                dbHelper.addNote(description);
                editTextDescription.setText("");
                Toast.makeText(getContext(), "Note added", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
