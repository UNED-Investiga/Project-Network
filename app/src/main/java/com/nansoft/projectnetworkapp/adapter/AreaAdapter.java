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

import com.bumptech.glide.Glide;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.model.Area;
import com.nansoft.projectnetworkapp.model.Proyecto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/8/2015.
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>
{
    List<Area> areas;
    Context mContext;

    public AreaAdapter(Context context,ArrayList<Area> pAreas)
    {
        mContext = context;
        areas = pAreas;
    }

    public void setData(List <Area> pAreas)
    {
        areas = pAreas;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.area_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final AreaAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Area item = areas.get(position);

        // se establecen las propiedades

        // título
        viewHolder.txtvTitulo.setText(item.nombre);

        // imagen
        Glide.with(mContext)
                .load(item.urlImagen.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(viewHolder.imgArea);

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return areas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView imgArea;
        public TextView txtvTitulo;

        public ViewHolder (View itemView)
        {
            super(itemView);

            // se enlazan los items

            imgArea = (ImageView) itemView.findViewById(R.id.imgvArea);
            txtvTitulo = (TextView) itemView.findViewById(R.id.txtvTituloArea);
        }

    }
}
