package com.example.lab6;// MainActivity.java

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_SHOW_POPUP = "showPopup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean showPopup = settings.getBoolean(PREF_SHOW_POPUP, true);

        if (showPopup) {
            showPopupWindow();
        }
    }

    private void showPopupWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);

        Button btnOk = popupView.findViewById(R.id.ok_button);
        final CheckBox chkDontShow = popupView.findViewById(R.id.dont_show_again_checkbox);

        btnOk.setOnClickListener(v -> {
            if (chkDontShow.isChecked()) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(PREF_SHOW_POPUP, false);
                editor.apply();
            }
            popupWindow.dismiss();
        });

        popupWindow.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
    }
}
