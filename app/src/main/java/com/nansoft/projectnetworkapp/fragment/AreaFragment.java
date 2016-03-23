package com.nansoft.projectnetworkapp.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.activity.ProyectoActivity;
import com.nansoft.projectnetworkapp.activity.ProyectoAreaActivity;
import com.nansoft.projectnetworkapp.adapter.AreaAdapter;
import com.nansoft.projectnetworkapp.model.Area;
import com.nansoft.projectnetworkapp.model.Proyecto;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by User on 7/8/2015.
 */
public class AreaFragment extends Fragment
{
    SwipeRefreshLayout mSwipeRefreshLayout;
    AreaAdapter adapter;
    ImageView imgvSad;
    TextView txtvSad;
    ArrayList <Area> areasList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //This layout contains your list view
        View view = inflater.inflate(R.layout.fragment_general, container, false);

        getActivity().setTitle(getString(R.string.areas_activity));

        areasList = new ArrayList<Area>();

        View includedLayout = view.findViewById(R.id.sindatos);
        imgvSad = (ImageView) includedLayout.findViewById(R.id.imgvInfoProblema);
        txtvSad = (TextView) includedLayout.findViewById(R.id.txtvInfoProblema);

        txtvSad.setText(getResources().getString(R.string.noconnection));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lstvGeneral);

        adapter = new AreaAdapter(view.getContext(),areasList);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swprlGeneral);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary_dark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarAreas(getActivity());
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                try {
                    Intent intent = new Intent(view.getContext(), ProyectoAreaActivity.class);
                    intent.putExtra("idArea", adapter.getItem(position).id);
                    intent.putExtra("nombreArea",adapter.getItem(position).nombre);
                    startActivity(intent);
                } catch (Exception e) {

                }
            }
        });
*/
        cargarAreas(getActivity());
        return view;
    }

    public void cargarAreas(final FragmentActivity activity) {
        estadoAdapter(false);
        mSwipeRefreshLayout.setEnabled(false);
        new AsyncTask<Void, Void, Boolean>() {

            MobileServiceClient mClient;
            MobileServiceTable<Area> mAreaTable;

            @Override
            protected void onPreExecute()
            {
                try {

                    mClient = new MobileServiceClient(
                            "https://msprojectnetworkjs.azure-mobile.net/",
                            "gSewfUQpGFAVMRajseDOZwqCCRUwwD62",
                            activity.getApplicationContext()
                    );

                } catch (MalformedURLException e) {

                }
                mAreaTable = mClient.getTable("Area",Area.class);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    areasList = mAreaTable.orderBy("nombre", QueryOrder.Ascending).execute().get();

                    adapter.setData(areasList);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
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

                estadoAdapter(success);
                mSwipeRefreshLayout.setEnabled(true);
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
        if(areasList.isEmpty() && pEstadoError)
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
