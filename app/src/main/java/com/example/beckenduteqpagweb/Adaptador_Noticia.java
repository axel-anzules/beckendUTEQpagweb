package com.example.beckenduteqpagweb;

import android.content.Context;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptador_Noticia extends ArrayAdapter<Noticia> {
    public Adaptador_Noticia(Context context, List<Noticia> noticias) {
        super(context, 0, noticias);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Noticia noticia = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.noti_item, parent, false);
        }

        TextView tvTitulo = convertView.findViewById(R.id.txtTitulont);
        TextView tvCategoria = convertView.findViewById(R.id.txtTitular);
        TextView tvFecha = convertView.findViewById(R.id.txtFecha);
        TextView tvEnlace = convertView.findViewById(R.id.txtURLnt);
        ImageView imgNoticia = convertView.findViewById(R.id.ntCover);

        tvTitulo.setText(getItem(position).getTitulo());
        tvCategoria.setText(getItem(position).getCategoria());
        tvFecha.setText("Publicada el: " + getItem(position).getFecha());
        tvEnlace.setText(getItem(position).getUrlNoticia());
        Linkify.addLinks(tvEnlace, Linkify.WEB_URLS);

        Glide.with(getContext())
                .load(getItem(position).getUrlImagen())
                .centerCrop()
                .into(imgNoticia);

        return convertView;
    }
}

