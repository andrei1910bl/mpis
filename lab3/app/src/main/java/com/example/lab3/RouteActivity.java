package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RouteActivity extends AppCompatActivity {

    private LinearLayout linearLayout; // This is the container for dynamically added EditTexts
    private Button okButton, lastNameButton, addRouteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        // Getting the layout where EditTexts are added
        linearLayout = findViewById(R.id.linearLayout); // Assuming your root layout has this ID

        // Find buttons
        okButton = findViewById(R.id.okButton);
        lastNameButton = findViewById(R.id.lastNameButton);
        addRouteButton = findViewById(R.id.addRouteButton);

        lastNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RouteActivity.this, "Рабченя Максим АС-64", Toast.LENGTH_SHORT).show();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder route = new StringBuilder();

                // Проходим через все дочерние элементы LinearLayout
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View child = linearLayout.getChildAt(i);

                    // Проверяем, что элемент - это EditText
                    if (child instanceof EditText) {
                        EditText editText = (EditText) child;
                        String text = editText.getText().toString().trim(); // Получаем текст и убираем пробелы

                        // Проверяем, что поле не пустое
                        if (!text.isEmpty()) {
                            route.append(text).append(", "); // Добавляем текст с разделителем
                        }
                    }
                }


                if (route.length() > 0) {
                    route.setLength(route.length() - 2);
                }


                Intent resultIntent = new Intent();
                resultIntent.putExtra("route", route.toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // Adding functionality to dynamically add EditText fields
        addRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newEditText = new EditText(RouteActivity.this);
                newEditText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                newEditText.setHint("New Route part");

                int buttonIndex = linearLayout.indexOfChild(addRouteButton);

                linearLayout.addView(newEditText, buttonIndex);
            }
        });
    }
}
