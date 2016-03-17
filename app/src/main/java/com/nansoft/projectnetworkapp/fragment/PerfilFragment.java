package com.nansoft.projectnetworkapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.adapter.ProyectoAdapter;
import com.nansoft.projectnetworkapp.helper.CustomMobileService;

/**
 * Created by User on 7/8/2015.
 */
public class PerfilFragment extends Fragment
{
    SwipeRefreshLayout mSwipeRefreshLayout;
    String idUsuario;
    ProyectoAdapter adapter;
    ImageView imgvSad;
    TextView txtvSad;

    View headerListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //This layout contains your list view
        View view = inflater.inflate(R.layout.header_user, container, false);

        TextView txtvNombreUsuario = (TextView)  view.findViewById(R.id.txtvNombreUsuario);
        txtvNombreUsuario.setText(CustomMobileService.USUARIO_LOGUEADO.getNombre());

        Glide.with(this)
                .load(CustomMobileService.USUARIO_LOGUEADO.getUrlImagen().trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into((ImageView)  view.findViewById(R.id.imgvPerfilUsuario));


        return view;
    }



}
