package com.nansoft.projectnetworkapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

/**
 * Created by User on 7/8/2015.
 */
public class ProyectoAdapter extends RecyclerView.Adapter<ProyectoAdapter.ViewHolder>
{
    Context mContext;
    int mLayoutResourceId;

    // Store a member variable for the contacts
    private List<Proyecto> proyects;



    public ProyectoAdapter(Context pContext,List <Proyecto> pProyects)
    {
        mContext = pContext;
        proyects = pProyects;
    }

    public void setData(List <Proyecto> pProyects)
    {
        proyects = pProyects;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ProyectoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.project_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ProyectoAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Proyecto proyect = proyects.get(position);

        // Set item views based on the data model
        viewHolder.txtvTitulo.setText(proyect.nombre);
        viewHolder.txtvSubtitulo.setText(proyect.nombreArea);
        viewHolder.txtvUerName.setText(proyect.nombreUsuario);

        Glide.with(mContext)
                .load(proyect.urlImagen.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(viewHolder.imgLogo);


        Glide.with(mContext)
                .load(proyect.urlImagenUsuario.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(viewHolder.imgUserImage);
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return proyects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView imgLogo;
        public ImageView imgUserImage;
        public TextView txtvUerName;
        public TextView txtvTitulo;
        public TextView txtvSubtitulo;
        public TextView txtvFecha;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txtvTitulo = (TextView) itemView.findViewById(R.id.txtvProjectName);
            txtvSubtitulo = (TextView) itemView.findViewById(R.id.txtvAreaName);
            //holder.txtvFecha = (TextView) row.findViewById(R.id.txtvDateCreated);
            imgLogo = (ImageView) itemView.findViewById(R.id.imgvProjectImage);
            imgUserImage = (ImageView) itemView.findViewById(R.id.imgvUserImage);
            txtvUerName = (TextView) itemView.findViewById(R.id.txtvUserName);


        }


    }
}
