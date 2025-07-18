package com.example.beckenduteqpagweb;

import org.json.JSONException;

public class Noticia {
    private static String titulo;
    private static String categoria;
    private static String fecha;
    private static String urlImagen;

    public static String getTitulo() {
        return titulo;
    }

    public static void setTitulo(String titulo) {
        Noticia.titulo = titulo;
    }

    public static String getCategoria() {
        return categoria;
    }

    public static void setCategoria(String categoria) {
        Noticia.categoria = categoria;
    }

    public static String getFecha() {
        return fecha;
    }

    public static void setFecha(String fecha) {
        Noticia.fecha = fecha;
    }

    public static String getUrlImagen() {
        return urlImagen;
    }

    public static void setUrlImagen(String urlImagen) {
        Noticia.urlImagen = urlImagen;
    }

    public static String getUrlNoticia() {
        return urlNoticia;
    }

    public static void setUrlNoticia(String urlNoticia) {
        Noticia.urlNoticia = urlNoticia;
    }

    private static String urlNoticia;

    public Noticia(Noticia a) throws JSONException {
        titulo = a.getString("ntTitular").toString();
        categoria = a.getString("gtTitular").toString();
        fecha = a.getString("ntFecha").toString();
        urlImagen = a.getString("ntUrlPortada").toString();
        urlNoticia = a.getString("ntUrlNoticia").toString();
    }

    private Object getString(String ntTitular) {
        return null;
    }
}

