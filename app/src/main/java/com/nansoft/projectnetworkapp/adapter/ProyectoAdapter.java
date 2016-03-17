package com.nansoft.projectnetworkapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.model.Proyecto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 7/8/2015.
 */
public class ProyectoAdapter extends ArrayAdapter<Proyecto>
{
    Context mContext;
    int mLayoutResourceId;

    public ProyectoAdapter(Context context, int resource)
    {
        super(context, resource);
        mContext = context;
        mLayoutResourceId = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View row = convertView;
        final ViewHolder holder;
        final Proyecto currentItem = getItem(position);

        // verificamos si la fila que se va dibujar no existe
        if (row == null)
        {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.txtvTitulo = (TextView) row.findViewById(R.id.txtvTituloGeneral);
            holder.txtvSubtitulo = (TextView) row.findViewById(R.id.txtvSubtituloGeneral);
            holder.txtvFecha = (TextView) row.findViewById(R.id.txtvFechaGeneral);
            holder.imgLogo = (ImageView) row.findViewById(R.id.imgvLogoGeneral);
            row.setTag(holder);
        }
        else
        {
            // en caso contrario la recuperamos
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtvTitulo.setText(currentItem.nombre);
        holder.txtvSubtitulo.setText(currentItem.nombreAux);
        holder.txtvFecha.setText(currentItem.getFechaCreacion());
        Glide.with(mContext)
                .load(currentItem.urlImagen.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(holder.imgLogo);


        return row;

    }



    static class ViewHolder
    {

        public ImageView imgLogo;
        public TextView txtvTitulo;
        public TextView txtvSubtitulo;
        public TextView txtvFecha;


    }
}
