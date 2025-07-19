package com.example.beckenduteqpagweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class Adaptador_Noticia extends ArrayAdapter<Noticia> {

    private final LayoutInflater inflater;

    // Constructor
    public Adaptador_Noticia(Context context, ArrayList<Noticia> datos) {
        super(context, R.layout.noti_item, datos);
        inflater = LayoutInflater.from(context);
    }

    // ViewHolder pattern: mejora de rendimiento al evitar llamadas repetidas a findViewById
    static class ViewHolder {
        TextView txtCategoria;
        TextView txtTitulo;
        ImageView imgPortada;
        TextView txtFecha;
        TextView txtURL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.noti_item, parent, false);
            holder = new ViewHolder();
            holder.txtCategoria = convertView.findViewById(R.id.txtTitular);
            holder.txtTitulo = convertView.findViewById(R.id.txtTitulont);
            holder.imgPortada = convertView.findViewById(R.id.ntCover);
            holder.txtFecha = convertView.findViewById(R.id.txtFecha);
            holder.txtURL = convertView.findViewById(R.id.txtURLnt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener el objeto Noticia actual
        Noticia noticia = getItem(position);

        if (noticia != null) {
            holder.txtCategoria.setText(noticia.getCategoria());
            holder.txtTitulo.setText(noticia.getTitulo());
            holder.txtFecha.setText(noticia.getFecha());
            holder.txtURL.setText(noticia.getUrlnoticia());

            // Cargar imagen usando Glide
            Glide.with(getContext())
                    .load(noticia.getUrlimagen())
                    .placeholder(R.drawable.placeholder_image) // asegúrate de tener esta imagen
                    .diskCacheStrategy(DiskCacheStrategy.ALL)  // cache para mejorar rendimiento
                    .into(holder.imgPortada);
        }

        return convertView;
    }
}

