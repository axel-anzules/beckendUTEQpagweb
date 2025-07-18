package com.example.beckenduteqpagweb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Adaptador_Noticia adapter;
    ArrayList<Noticia> listaNoticias = new ArrayList<>();

    String apiUrl = "https://apiws.uteq.edu.ec/h6RPoSoRaah0Y4Bah28eew/functions/information/entity/1";
    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJfeDF1c2VyZGV2IiwiaWF0IjoxNzUyODY4NjIwLCJleHAiOjE3NTI5NTUwMjB9.tZPdC3XwEOhgHZz_QFk5TQa2SrFXimlgVSFa6cwhiwY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lstnoticia);
        listView.setAdapter(adapter);
        new ObtenerNoticiasTask().execute();
    }

    class ObtenerNoticiasTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", token);

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                reader.close();
                return json.toString();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                try {
                    JSONArray arr = new JSONArray(json);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        String titulo = obj.getString("ntTitulo");
                        String categoria = obj.getString("ctNombre");
                        String fecha = obj.getString("ntFechaPublicacion");
                        String imagen = "https://uteq.edu.ec/assets/images/news/pagina/" + obj.getString("ntUrlPortada");
                        String enlace = "https://uteq.edu.ec/es/comunicacion/noticia/" + obj.getString("ntUrlNoticia");

                        listView.setAdapter(new Adaptador_Noticia(MainActivity.this, listaNoticias));
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
