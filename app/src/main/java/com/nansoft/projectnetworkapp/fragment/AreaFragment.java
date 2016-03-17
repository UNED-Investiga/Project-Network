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

/**
 * Created by User on 7/8/2015.
 */
public class AreaFragment extends Fragment
{
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static AreaAdapter adapter;
    ImageView imgvSad;
    TextView txtvSad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //This layout contains your list view
        View view = inflater.inflate(R.layout.areas_layout, container, false);


        View includedLayout = view.findViewById(R.id.sindatos);
        imgvSad = (ImageView) includedLayout.findViewById(R.id.imgvInfoProblema);
        txtvSad = (TextView) includedLayout.findViewById(R.id.txtvInfoProblema);

        txtvSad.setText(getResources().getString(R.string.noconnection));

        //now you must initialize your list view
        GridView gridView =(GridView)view.findViewById(R.id.gridAreas);

        adapter = new AreaAdapter(view.getContext(),R.layout.area_item);


        gridView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swprlArea);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.android_darkorange, R.color.green, R.color.android_blue);


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
                    adapter.clear();
                } catch (MalformedURLException e) {

                }
                mAreaTable = mClient.getTable("Area",Area.class);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    final MobileServiceList<Area> result = mAreaTable.orderBy("nombre", QueryOrder.Ascending).execute().get();

                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (Area item : result)
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
