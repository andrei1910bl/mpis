package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private EditText editMessage;
    private ImageView imageView;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editMessage = findViewById(R.id.edit_massage);
        imageView = findViewById(R.id.cat);
        Button btnDownload = findViewById(R.id.btn_download);
        Button btnOpenBrowser = findViewById(R.id.btn_open_browser);
        Button searchButton = findViewById(R.id.search_button);
        Button likeButton = findViewById(R.id.imageButton1);
        Button dislikeButton = findViewById(R.id.imageButton2);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editMessage.getText().toString();
                if (!query.isEmpty()) {
                    new ImageSearchTask().execute(query);
                }
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Изображение понравилось!", Toast.LENGTH_SHORT).show();
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Изображение не понравилось!", Toast.LENGTH_SHORT).show();
            }
        });


        // Обработчик кнопки "Скачать"
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(imageUrl);
            }
        });

        // Обработчик кнопки "Открыть в браузере"
        btnOpenBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(imageUrl);
            }
        });

        // Запустите вашу задачу поиска изображений
        new ImageSearchTask().execute("cats"); // Замените на нужный запрос
    }

    private void downloadImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Код для скачивания изображения
            new DownloadImageTask().execute(imageUrl);
        } else {
            Toast.makeText(this, "Нет доступного изображения для скачивания.", Toast.LENGTH_SHORT).show();
        }
    }

    private class ImageSearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String query = params[0];
            String result = null;
            try {

                String accessKey = "rquNqaqjaizHlbHnqRwScd3UJO-uduUrCtellCjfcHM";
                String urlString = "https://api.unsplash.com/search/photos?query=" + query + "&client_id=" + accessKey;
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuilder response = new StringBuilder();
                int data;
                while ((data = inputStream.read()) != -1) {
                    response.append((char) data);
                }


                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray results = jsonResponse.getJSONArray("results");
                if (results.length() > 0) {

                    JSONObject urls = results.getJSONObject(0).getJSONObject("urls");
                    result = urls.getString("regular"); // Получаем URL маленького изображения
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                imageUrl = result;
                Picasso.get().load(imageUrl).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWebPage(imageUrl);
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "Не удалось найти изображение.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String imageUrl = urls[0];
            try {
                // Скачивание изображения
                InputStream inputStream = new URL(imageUrl).openStream();
                // Здесь можно сохранить изображение в хранилище устройства
                // Для упрощения примера возвращаем URL
                return imageUrl;
            } catch (Exception e) {
                Log.e("Download Error", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(MainActivity.this, "Изображение скачано: " + result, Toast.LENGTH_SHORT).show();
                // Здесь можно добавить код для сохранения изображения в хранилище
            } else {
                Toast.makeText(MainActivity.this, "Ошибка при скачивании изображения.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openWebPage(String url) {
        if (url != null && !url.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Нет доступного URL.", Toast.LENGTH_SHORT).show();
        }
    }
}

