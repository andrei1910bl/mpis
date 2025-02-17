package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, new GestureListener());

        Button buttonSurname = findViewById(R.id.buttonSurname);
        Button aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
        buttonSurname.setOnClickListener(v -> showToast("Сделал: Рабченя Максим АС-64"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (diffX > 0) {
                    showToast("Swipe Right");
                } else {
                    showToast("Swipe Left");
                }
            } else {
                if (diffY > 0) {
                    showToast("Swipe Down");
                } else {
                    showToast("Swipe Up");
                }
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            showToast("Double Tap");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            showToast("Single Tap");
            return true;
        }
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        toast.show();

        new Handler().postDelayed(toast::cancel, 800);
    }
}