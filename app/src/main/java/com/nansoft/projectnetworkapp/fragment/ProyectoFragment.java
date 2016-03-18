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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.activity.ProyectoActivity;
import com.nansoft.projectnetworkapp.adapter.ProyectoAdapter;
import com.nansoft.projectnetworkapp.model.Area;
import com.nansoft.projectnetworkapp.model.Proyecto;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/8/2015.
 */
public class ProyectoFragment  extends Fragment
{
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProyectoAdapter adapter;
    ImageView imgvSad;
    TextView txtvSad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //This layout contains your list view
        View view = inflater.inflate(R.layout.proyecto_activity, container, false);



        View includedLayout = view.findViewById(R.id.sindatos);
        imgvSad = (ImageView) includedLayout.findViewById(R.id.imgvInfoProblema);
        txtvSad = (TextView) includedLayout.findViewById(R.id.txtvInfoProblema);
        txtvSad.setText(getResources().getString(R.string.noconnection));
        //now you must initialize your list view
        ListView listview = (ListView) view.findViewById(R.id.lstvGeneral);

        adapter = new ProyectoAdapter(view.getContext(), R.layout.general_item);


        listview.setAdapter(adapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swprlUltimosProyectos);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary_dark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarProyectos(getActivity());
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                try {
                    Intent intent = new Intent(view.getContext(), ProyectoActivity.class);
                    intent.putExtra("idProyecto", adapter.getItem(position).id);
                    startActivity(intent);
                } catch (Exception e) {

                }
            }
        });

        cargarProyectos(getActivity());
        return view;
    }

    public void cargarProyectos(final FragmentActivity activity) {
        estadoAdapter(false);
        mSwipeRefreshLayout.setEnabled(false);
        new AsyncTask<Void, Void, Boolean>() {

            MobileServiceClient mClient;
            MobileServiceTable<Proyecto> mProyectoTable;
            MobileServiceTable<Area> mAreaTable;
            Area objArea;

            @Override
            protected void onPreExecute()
            {
                try {

                    mClient = new MobileServiceClient(
                            "https://msprojectnetworkjs.azure-mobile.net/",
                            "gSewfUQpGFAVMRajseDOZwqCCRUwwD62",
                            activity.getApplicationContext()
                    );
                    adapter.clear();
                } catch (MalformedURLException e) {

                }
                mProyectoTable = mClient.getTable("Proyecto", Proyecto.class);
                mAreaTable = mClient.getTable("Area",Area.class);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    final MobileServiceList<Proyecto> result = mProyectoTable.where().select("id","nombre","urlimagen","fechacreacion","__createdAt","idarea").orderBy("fechacreacion",QueryOrder.Descending).top(15).execute().get();

                    for (Proyecto item : result)
                    {
                        objArea = new Area();
                        objArea = mAreaTable.lookUp(item.idArea).get();

                        item.nombreAux = objArea.nombre;

                    }

                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (Proyecto item : result)
                            {

                                adapter.add(item);
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });
                    return true;
                } catch (Exception exception) {

                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success)
            {

                mSwipeRefreshLayout.setRefreshing(false);

                mSwipeRefreshLayout.setEnabled(true);

                estadoAdapter(success);
            }

            @Override
            protected void onCancelled()
            {
                super.onCancelled();
            }
        }.execute();
    }

    private void estadoAdapter(boolean pEstadoError)
    {
        if(adapter.isEmpty() && pEstadoError)
        {
            imgvSad.setVisibility(View.VISIBLE);
            txtvSad.setVisibility(View.VISIBLE);


        }
        else
        {
            imgvSad.setVisibility(View.INVISIBLE);
            txtvSad.setVisibility(View.INVISIBLE);
        }
    }
}