package com.nansoft.projectnetworkapp.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.activity.ProyectoActivity;
import com.nansoft.projectnetworkapp.activity.ProyectoAreaActivity;
import com.nansoft.projectnetworkapp.adapter.AreaAdapter;
import com.nansoft.projectnetworkapp.adapter.ProyectoAdapter;
import com.nansoft.projectnetworkapp.helper.MobileServiceCustom;
import com.nansoft.projectnetworkapp.model.Area;
import com.nansoft.projectnetworkapp.model.Proyecto;

import org.w3c.dom.Text;

import java.net.MalformedURLException;

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
        txtvNombreUsuario.setText(MobileServiceCustom.USUARIO_LOGUEADO.getNombre());

        Glide.with(this)
                .load(MobileServiceCustom.USUARIO_LOGUEADO.getUrlImagen().trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into((ImageView)  view.findViewById(R.id.imgvPerfilUsuario));


        return view;
    }



}
