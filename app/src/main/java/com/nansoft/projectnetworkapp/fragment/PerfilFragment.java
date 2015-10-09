package com.nansoft.projectnetworkapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.activity.ProyectoActivity;
import com.nansoft.projectnetworkapp.adapter.ProyectoAdapter;
import com.nansoft.projectnetworkapp.model.Proyecto;

/**
 * Created by User on 7/8/2015.
 */
public class PerfilFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //This layout contains your list view
       View view = inflater.inflate(R.layout.activity_authenticate, container, false);
        /*
        //now you must initialize your list view
        ListView listview = (ListView) view.findViewById(R.id.lstvProyectosUsuario);

        ProyectoAdapter adapter = new ProyectoAdapter(view.getContext(), R.layout.general_item);


        listview.setAdapter(adapter);
        String j ;
        for (int i = 0; i < 30; i++) {
            //adapter.add(new Proyecto("a","nombre " + String.valueOf(i), "desc " + String.valueOf(i),"mail","www.contoso.com",50,"2015","url","a","a","a",));


        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ProyectoActivity.class);

                startActivity(intent);
            }
        });
        //EDITED Code
        */

        //To have custom list view use this : you must define CustomeAdapter class
        // listview.setadapter(new CustomeAdapter(getActivity()));
        //getActivty is used instead of Context
        return view;
    }
}