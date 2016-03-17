package com.nansoft.projectnetworkapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.adapter.ProyectoAdapter;
import com.nansoft.projectnetworkapp.adapter.UsuarioAdapter;
import com.nansoft.projectnetworkapp.model.Proyecto;
import com.nansoft.projectnetworkapp.model.Usuario;
import com.nansoft.projectnetworkapp.model.UsuarioProyecto;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class ProyectoActivity extends ActionBarActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    String idProyecto;
    UsuarioAdapter adapter;
    ImageView imgvSad;
    TextView txtvSad;

    ListView lstvUsuarios;
    View headerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoproyecto_activity);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);

        View includedLayout = findViewById(R.id.sindatos);
        imgvSad = (ImageView) includedLayout.findViewById(R.id.imgvInfoProblema);
        txtvSad = (TextView) includedLayout.findViewById(R.id.txtvInfoProblema);

        txtvSad.setText(getResources().getString(R.string.noconnection));

        try {
            idProyecto = getIntent().getStringExtra("idProyecto");
        }
        catch(Exception e)
        {

        }
         headerListView = getLayoutInflater().inflate(R.layout.header_project, null);

        lstvUsuarios = (ListView) findViewById(R.id.lstvUsuarios);
        lstvUsuarios.addHeaderView(headerListView);
        adapter = new UsuarioAdapter(this,R.layout.general_item);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swprlInfoProyecto);




        mSwipeRefreshLayout.setColorSchemeResources(R.color.android_darkorange, R.color.green, R.color.android_blue);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarProyecto(idProyecto);
            }
        });



        lstvUsuarios.setAdapter(adapter);

        lstvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(view.getContext(), PerfilUsuarioActivity.class);
                intent.putExtra("idUsuario",adapter.getItem(position-1).id);
                startActivity(intent);
            }
        });


        cargarProyecto(idProyecto);

    }

    public void cargarProyecto(final String pIdProyecto) {
        estadoAdapter(false);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        mSwipeRefreshLayout.setEnabled(false);
        new AsyncTask<Void, Void, Boolean>() {

            MobileServiceClient mClient;
            MobileServiceTable<Proyecto> mProyectoTable;
            MobileServiceTable<UsuarioProyecto> mUsuarioProyectoTable;
            MobileServiceTable<Usuario> mUsuarioTable;
            Proyecto objProyecto;
            Usuario objUsuario;
            ArrayList<Usuario> lstUsuarios;

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
                mUsuarioProyectoTable = mClient.getTable("UsuarioProyecto",UsuarioProyecto.class);
                mUsuarioTable = mClient.getTable("Usuario",Usuario.class);

                lstUsuarios = new ArrayList<Usuario>();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    objProyecto = mProyectoTable.lookUp(pIdProyecto).get();


                    final MobileServiceList<UsuarioProyecto> result = mUsuarioProyectoTable.where().field("idproyecto").eq(objProyecto.id).select("idusuario","idcargo").execute().get();

                    // agregamos el encargado del proyecto
                    objUsuario = new Usuario();
                    objUsuario = mUsuarioTable.lookUp(objProyecto.idEncargado).get();
                    objUsuario.idCargo = "1";
                    objUsuario.fechaCreado = objProyecto.__createdAt;
                    lstUsuarios.add(objUsuario);


                    for(UsuarioProyecto item : result)
                    {
                        objUsuario = new Usuario();
                        objUsuario = mUsuarioTable.lookUp(item.idUsuario).get();
                        objUsuario.idCargo = item.idCargo;
                        objUsuario.fechaCreado = item.__createdAt;
                        lstUsuarios.add(objUsuario);
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        for(Usuario item : lstUsuarios)
                        {
                            adapter.add(item);
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
                if (success)
                {

                    cargarVista(objProyecto);

                }

            }

            @Override
            protected void onCancelled()
            {
                super.onCancelled();
            }
        }.execute();
    }

    private void cargarVista(final Proyecto pObjProyecto)
    {

        TextView txtvNombreProyecto = (TextView) headerListView.findViewById(R.id.txtvNombreProyecto);
        ImageView imgvEstadoProyecto = (ImageView) headerListView.findViewById(R.id.imgvEstadoProyecto);
        ImageView imgvCorreoProyecto = (ImageView) headerListView.findViewById(R.id.imgvEmailProyecto);
        ImageView imgvWebProyecto = (ImageView) headerListView.findViewById(R.id.imgvWebProyecto);
        TextView txtvDescripcioProyecto = (TextView) headerListView.findViewById(R.id.txtvDescripcionProyecto);
        ImageView imgvLogoProyecto = (ImageView) headerListView.findViewById(R.id.imgvProyecto);

        imgvCorreoProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String [] pPara = {pObjProyecto.email};
                    Intent Correo = new Intent(Intent.ACTION_SEND);
                    Correo.setData(Uri.parse("mailto:"));
                    Correo.putExtra(Intent.EXTRA_EMAIL,pPara);
                    Correo.putExtra(Intent.EXTRA_CC, "");
                    Correo.putExtra(Intent.EXTRA_SUBJECT, "Consulta");
                    Correo.putExtra(Intent.EXTRA_TEXT, "");
                    Correo.setType("message/rfc822");

                    startActivity(Intent.createChooser(Correo, "Email "));
                } catch (ActivityNotFoundException activityException) {

                    Toast.makeText(getApplicationContext(), "Error verifique que tenga una aplicación de correo", Toast.LENGTH_SHORT).show();

                }
            }
        });

        imgvWebProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pObjProyecto.webSite));
                    startActivity(intent);
                } catch (ActivityNotFoundException activityException) {

                    Toast.makeText(getApplicationContext(), "Error verifique que tenga un navegador instalado", Toast.LENGTH_SHORT).show();

                }
            }
        });

        txtvNombreProyecto.setText(pObjProyecto.nombre);

        txtvDescripcioProyecto.setText(pObjProyecto.descripcion);

        Glide.with(this)
                .load(pObjProyecto.urlImagen.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(imgvLogoProyecto);

        switch (pObjProyecto.idEstado)
        {
            case "1":
                imgvEstadoProyecto.setImageResource(R.drawable.active);
                break;

            case "2":
                imgvEstadoProyecto.setImageResource(R.drawable.sleep);
                break;

            case "3":
                imgvEstadoProyecto.setImageResource(R.drawable.idea);
                break;
        }



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
        getMenuInflater().inflate(R.menu.menu_proyecto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case R.id.action_update_user:
                cargarProyecto(idProyecto);
                return true;

            case android.R.id.home:
                super.onBackPressed();
                break;

        }



        return super.onOptionsItemSelected(item);
    }
}
