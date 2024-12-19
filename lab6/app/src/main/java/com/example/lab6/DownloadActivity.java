package com.example.lab6;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {
    private EditText etJournalId;
    private Button btnDownload, btnView, btnDelete;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        etJournalId = findViewById(R.id.etJournalId);
        btnDownload = findViewById(R.id.btnDownload);
        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);
        progressBar = findViewById(R.id.progressBar);

        btnDownload.setOnClickListener(v -> downloadFile());
        btnView.setOnClickListener(v -> viewFile());
        btnDelete.setOnClickListener(v -> deleteFile());
    }

    private void downloadFile() {
        String journalId = etJournalId.getText().toString();
        String url = "http://ntv.ifmo.ru/file/journal/" + journalId + ".pdf";
        new DownloadFileTask().execute(url);
    }

    private class DownloadFileTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            btnDownload.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getContentType().equals("application/pdf")) {
                    InputStream input = connection.getInputStream();
                    File file = new File(getExternalFilesDir(null), "journal.pdf");
                    FileOutputStream output = new FileOutputStream(file);

                    byte[] data = new byte[4096];
                    int count;
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressBar.setVisibility(View.GONE);
            btnDownload.setEnabled(true);
            if (result) {
                btnView.setEnabled(true);
                btnDelete.setEnabled(true);
                Toast.makeText(DownloadActivity.this, "Файл загружен", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DownloadActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void viewFile() {
        File file = new File(getExternalFilesDir(null), "journal.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Нет приложения для просмотра PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile() {
        File file = new File(getExternalFilesDir(null), "journal.pdf");
        if (file.delete()) {
            btnView.setEnabled(false);
            btnDelete.setEnabled(false);
            Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ошибка удаления файла", Toast.LENGTH_SHORT).show();
        }
    }
}