package com.nansoft.projectnetworkapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.model.Usuario;

/**
 * Created by User on 7/8/2015.
 */
public class UsuarioAdapter extends ArrayAdapter<Usuario>
{
    Context mContext;
    int mLayoutResourceId;
    String rutaImagen;
    Resources res;

    public UsuarioAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
        mLayoutResourceId = resource;
        res = this.mContext.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
       // final ViewHolder holder;
        final Usuario currentItem = getItem(position);

        // verificamos si la fila que se va dibujar no existe
        if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.imgLogo = (ImageView) row.findViewById(R.id.imgvLogoGeneral);
            holder.txtvTitulo = (TextView) row.findViewById(R.id.txtvTituloGeneral);
            holder.txtvSubtitulo = (TextView) row.findViewById(R.id.txtvSubtituloGeneral);
            holder.txtvFechaCreado = (TextView) row.findViewById(R.id.txtvFechaGeneral);
            row.setTag(holder);
        }
            // en caso contrario la recuperamos
        ViewHolder holder = (ViewHolder) row.getTag();

        rutaImagen = "picture";
        switch(currentItem.idCargo)
        {
            case "1":
                rutaImagen = "administrator";
                holder.txtvSubtitulo.setText("Administrador");
                break;

            case "2":
                rutaImagen = "assitant";
                holder.txtvSubtitulo.setText("Colaborador");
                break;

            default:
                break;
        }

        holder.imgLogo.setImageResource(res.getIdentifier(rutaImagen,
                "drawable", mContext.getPackageName()));

        holder.txtvTitulo.setText(currentItem.nombre + " " + currentItem.primerApellido + " " + currentItem.segundoApellido);
        holder.txtvFechaCreado.setText(currentItem.getFechaCreado());

        return row;

    }

    static class ViewHolder
    {

        public ImageView imgLogo;
        public TextView txtvTitulo;
        public TextView txtvSubtitulo;
        public TextView txtvFechaCreado;


    }
}
