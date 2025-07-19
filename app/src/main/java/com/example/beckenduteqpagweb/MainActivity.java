package com.example.beckenduteqpagweb;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beckenduteqpagweb.WebServices.Asynchtask;
import com.example.beckenduteqpagweb.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    private static final String API_URL = "https://apiws.uteq.edu.ec/h6RPoSoRaah0Y4Bah28eew/functions/information/entity/1";
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJfeDF1c2VyZGV2IiwiaWF0IjoxNzUyODY4NjIwLCJleHAiOjE3NTI5NTUwMjB9.tZPdC3XwEOhgHZz_QFk5TQa2SrFXimlgVSFa6cwhiwY";

    private ListView lstNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajustes visuales para borde a borde
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar ListView
        lstNoticias = findViewById(R.id.lstnoticia);

        // Preparar datos vacíos (si fueran necesarios para la API)
        Map<String, String> datos = new HashMap<>();

        // Ejecutar el WebService con token Bearer
        WebService ws = new WebService(API_URL, datos, MainActivity.this, MainActivity.this);
        ws.execute("GET", "Bearer", BEARER_TOKEN);
    }

    @Override
    public void processFinish(String result) {
        ListView lstnoticia = findViewById(R.id.lstnoticia);

        try {
            if (result == null || result.isEmpty()) {
                Toast.makeText(this, "Error: No se recibió respuesta del servidor.", Toast.LENGTH_LONG).show();
                lstnoticia.setAdapter(null);
                return;
            }

            // Comprobar si el resultado inicia con '{' (objeto) o '[' (array)
            ArrayList<Noticia> ntArrayList;
            if (result.trim().startsWith("[")) {
                // La respuesta es un arreglo directo
                JSONArray jsonArray = new JSONArray(result);
                ntArrayList = Noticia.JsonObjectsBuild(jsonArray);
            } else {
                // La respuesta es un objeto que contiene el arreglo en la clave "data"
                JSONObject JSONlista = new JSONObject(result);

                if (!JSONlista.has("data")) {
                    Toast.makeText(this, "Error: respuesta sin campo 'data'.", Toast.LENGTH_LONG).show();
                    lstnoticia.setAdapter(null);
                    return;
                }

                JSONArray JSONListaNoticias = JSONlista.getJSONArray("data");
                ntArrayList = Noticia.JsonObjectsBuild(JSONListaNoticias);
            }

            if (ntArrayList.isEmpty()) {
                Toast.makeText(this, "No hay noticias para mostrar.", Toast.LENGTH_SHORT).show();
                lstnoticia.setAdapter(null);
            } else {
                Adaptador_Noticia adaptador = new Adaptador_Noticia(this, ntArrayList);
                lstnoticia.setAdapter(adaptador);
            }

        } catch (JSONException e) {
            Toast.makeText(this, "Error al analizar los datos JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            lstnoticia.setAdapter(null);
        } catch (Exception e) {
            Toast.makeText(this, "Ocurrió un error inesperado: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            lstnoticia.setAdapter(null);
        }
    }
}
