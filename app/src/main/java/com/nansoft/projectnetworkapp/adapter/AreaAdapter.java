package com.nansoft.projectnetworkapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.model.Area;

/**
 * Created by User on 7/8/2015.
 */
public class AreaAdapter extends ArrayAdapter<Area>
{

    int mLayoutResourceId;
    Context mContext;

    public AreaAdapter(Context context, int resource)
    {
        super(context, resource);
        mContext = context;
        mLayoutResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        final Area currentItem = getItem(position);

        // verificamos si la fila que se va dibujar no existe
        if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.txtvTitulo = (TextView) row.findViewById(R.id.txtvTituloArea);
            holder.imgArea = (ImageView) row.findViewById(R.id.imgvArea);
            row.setTag(holder);
        }
            // en caso contrario la recuperamos
        ViewHolder   holder = (ViewHolder) row.getTag();

        holder.txtvTitulo.setText(currentItem.nombre);
        Glide.with(mContext)
                .load(currentItem.urlImagen.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(holder.imgArea);

        return row;

    }

    static class ViewHolder
    {

        public ImageView imgArea;
        public TextView txtvTitulo;


    }
}
