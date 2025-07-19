package com.example.beckenduteqpagweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Noticia {

    private String categoria;
    private String titulo;
    private String urlimagen;
    private String fecha;
    private String urlnoticia;
    private String colorIdentificador;

    private static final String BASE_URL_IMAGEN = "https://uteq.edu.ec/assets/images/news/pagina/";
    private static final String BASE_URL_NOTICIA = "https://uteq.edu.ec/es/comunicacion/noticia/";

    // Getters y Setters
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getUrlimagen() { return urlimagen; }
    public void setUrlimagen(String urlimagen) { this.urlimagen = urlimagen; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getUrlnoticia() { return urlnoticia; }
    public void setUrlnoticia(String urlnoticia) { this.urlnoticia = urlnoticia; }

    public String getColorIdentificador() { return colorIdentificador; }
    public void setColorIdentificador(String colorIdentificador) { this.colorIdentificador = colorIdentificador; }

    // Constructor que recibe un objeto JSON
    public Noticia(JSONObject jsonObject) {
        this.titulo = jsonObject.optString("ntTitular", "Sin título");
        this.fecha = jsonObject.optString("ntFecha", "Sin fecha");

        // Obtener categoría si existe
        JSONObject categoriaObj = jsonObject.optJSONObject("objCategoriaNotc");
        this.categoria = (categoriaObj != null) ? categoriaObj.optString("gtTitular", "Sin categoría") : "Sin categoría";
        this.colorIdentificador = (categoriaObj != null) ? categoriaObj.optString("gtColorIdentf", "#FFFFFF") : "#FFFFFF";

        // Construcción segura de URLs
        String portada = jsonObject.optString("ntUrlPortada", "");
        this.urlimagen = BASE_URL_IMAGEN + portada;

        String slugNoticia = jsonObject.optString("ntUrlNoticia", "");
        this.urlnoticia = BASE_URL_NOTICIA + slugNoticia;
    }

    //Metodo para construir una lista de objetos Noticia desde un JSONArray
    public static ArrayList<Noticia> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Noticia> noticias = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            JSONObject noticiaObj = datos.getJSONObject(i);
            noticias.add(new Noticia(noticiaObj));
        }
        return noticias;
    }
}

