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

public class FragmentUpdate extends Fragment {
    private EditText editTextId, editTextDescription;
    private NotesDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        editTextId = view.findViewById(R.id.editTextId);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        dbHelper = new NotesDatabaseHelper(getContext());

        Button buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(editTextId.getText().toString());
                String description = editTextDescription.getText().toString();
                dbHelper.updateNote(id, description);
                editTextId.setText("");
                editTextDescription.setText("");
                Toast.makeText(getContext(), "Note updated", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
