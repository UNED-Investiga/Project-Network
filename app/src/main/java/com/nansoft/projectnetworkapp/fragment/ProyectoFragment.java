package com.nansoft.projectnetworkapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.adapter.ProyectoAdapter;
import com.nansoft.projectnetworkapp.model.Proyecto;

import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;

/**
 * Created by User on 7/8/2015.
 */
public class ProyectoFragment  extends Fragment
{
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProyectoAdapter adapter;
    ImageView imgvSad;
    TextView txtvSad;
    ArrayList <Proyecto> lstProjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //This layout contains your list view
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        getActivity().setTitle(getString(R.string.last_projects_activity));

        // layout de error
        View includedLayout = view.findViewById(R.id.sindatos);
        imgvSad = (ImageView) includedLayout.findViewById(R.id.imgvInfoProblema);
        txtvSad = (TextView) includedLayout.findViewById(R.id.txtvInfoProblema);
        txtvSad.setText(getResources().getString(R.string.noconnection));

        lstProjects = new ArrayList<Proyecto>();

        //now you must initialize your list view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lstvGeneral);

        adapter = new ProyectoAdapter(view.getContext(),lstProjects);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swprlGeneral);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary_dark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // cargarProyectos(getActivity());
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        /*
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
*/

        cargarComentarios(getActivity());
        return view;
    }


    public void cargarComentarios(final FragmentActivity activity) {


        try {

            MobileServiceClient mClient = new MobileServiceClient(
                    "https://msprojectnetworkjs.azure-mobile.net/",
                    "gSewfUQpGFAVMRajseDOZwqCCRUwwD62",
                    activity.getApplicationContext()
            );


            List<Pair<String, String>> parameters = new ArrayList<Pair<String, String>>();
            //parameters.add(new Pair<String, String>("idnew",ID_NOTICIA));

            ListenableFuture<JsonElement> lst = mClient.invokeApi("ultimosproyectos", "GET", parameters);

            Futures.addCallback(lst, new FutureCallback<JsonElement>() {
                @Override
                public void onFailure(Throwable exc) {

                    estadoAdapter(true);
                }

                @Override
                public void onSuccess(JsonElement result) {

                    // se verifica si el resultado es un array Json
                    if (result.isJsonArray()) {
                        // obtenemos el resultado como un JsonArray
                        JsonArray jsonArray = result.getAsJsonArray();
                        Gson objGson = new Gson();


                        // se deserializa el array
                        Type collectionType = new TypeToken<List<Proyecto>>(){}.getType();

                        lstProjects = objGson.fromJson(jsonArray, collectionType);
                        adapter.setData(lstProjects);


                        adapter.notifyDataSetChanged();


                    }

                    /*
                    if (mAdapter.getItemCount() == 0) {
                        estadoAdapter(true);
                    } else {
                        estadoAdapter(false);
                    }
                    */
                    mSwipeRefreshLayout.setRefreshing(false);


                }
            });
            mSwipeRefreshLayout.setEnabled(true);
        }
        catch (Exception e )
        {
            estadoAdapter(true);
        }

    }

    private void estadoAdapter(boolean pEstadoError)
    {
        if(lstProjects.isEmpty() && pEstadoError)
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