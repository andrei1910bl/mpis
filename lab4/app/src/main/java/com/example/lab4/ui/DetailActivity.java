package com.example.lab4.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab4.R;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_element);

        TextView nameText = findViewById(R.id.nameText);
        TextView usernameText = findViewById(R.id.usernameText);
        TextView emailText = findViewById(R.id.emailText);
        TextView phoneText = findViewById(R.id.phoneText);

        // Получаем данные из Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nameText.setText(extras.getString("name"));
            usernameText.setText("Username: " + extras.getString("username"));
            emailText.setText("Email: " + extras.getString("email"));
            phoneText.setText("Phone: " + extras.getString("phone"));
        }
    }
}
