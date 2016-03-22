package com.nansoft.projectnetworkapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.adapter.ProyectoAdapter;
import com.nansoft.projectnetworkapp.model.Proyecto;

import java.net.MalformedURLException;

public class ProyectoAreaActivity extends ActionBarActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ProyectoAdapter adapter;

    ImageView imgvSad;
    TextView txtvSad;

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proyectoarea_activity);


        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);

        final String idArea = getIntent().getStringExtra("idArea");
        final String nombreArea = getIntent().getStringExtra("nombreArea");

        ListView listview = (ListView) findViewById(R.id.lstvProyectosArea);

        View includedLayout = findViewById(R.id.sindatos);
        imgvSad = (ImageView) includedLayout.findViewById(R.id.imgvInfoProblema);
        txtvSad = (TextView) includedLayout.findViewById(R.id.txtvInfoProblema);

        txtvSad.setText(getResources().getString(R.string.nodata));
        adapter = new ProyectoAdapter(this, R.layout.general_item);


        listview.setAdapter(adapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swprlProyectosArea);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary_dark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarProyectos(idArea,nombreArea);
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

        cargarProyectos(idArea,nombreArea);
    }

    public void cargarProyectos(final String pIdArea,final String pNombreArea) {
        estadoAdapter(false);
        mSwipeRefreshLayout.setEnabled(false);
        new AsyncTask<Void, Void, Boolean>() {

            MobileServiceClient mClient;
            MobileServiceTable<Proyecto> mProyectoTable;

            @Override
            protected void onPreExecute()
            {
                try {

                    mClient = new MobileServiceClient(
                            "https://msprojectnetworkjs.azure-mobile.net/",
                            "gSewfUQpGFAVMRajseDOZwqCCRUwwD62",
                            getApplicationContext()
                    );
                    adapter.clear();
                } catch (MalformedURLException e) {

                }
                mProyectoTable = mClient.getTable("Proyecto", Proyecto.class);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {

                    final MobileServiceList<Proyecto> result = mProyectoTable.where().field("idarea").eq(pIdArea).select("id","nombre","urlimagen","fechacreacion","__createdAt","idarea").orderBy("fechacreacion",QueryOrder.Descending).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (Proyecto item : result)
                            {
                                item.nombreArea = pNombreArea;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proyecto_area, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId())
        {

            case android.R.id.home:
                super.onBackPressed();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
*/
}
