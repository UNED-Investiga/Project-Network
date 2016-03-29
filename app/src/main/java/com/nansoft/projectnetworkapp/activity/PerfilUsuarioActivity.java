package com.nansoft.projectnetworkapp.activity;

import android.app.ActionBar;
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
import com.nansoft.projectnetworkapp.model.Area;
import com.nansoft.projectnetworkapp.model.Proyecto;
import com.nansoft.projectnetworkapp.model.Usuario;
import com.nansoft.projectnetworkapp.model.UsuarioProyecto;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class PerfilUsuarioActivity extends ActionBarActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    String idUsuario;
    ProyectoAdapter adapter;
    ImageView imgvSad;
    TextView txtvSad;

    View headerListView;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        // Set up action bar.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);

        idUsuario = getIntent().getStringExtra("idUsuario");

        View includedLayout = findViewById(R.id.sindatos);
        imgvSad = (ImageView) includedLayout.findViewById(R.id.imgvInfoProblema);
        txtvSad = (TextView) includedLayout.findViewById(R.id.txtvInfoProblema);

        txtvSad.setText(getResources().getString(R.string.noconnection));

        ListView lstvProyectos = (ListView) findViewById(R.id.lstvProyectosUsuario);
        headerListView = getLayoutInflater().inflate(R.layout.header_user, null);
        lstvProyectos.addHeaderView(headerListView);
        adapter = new ProyectoAdapter(this,R.layout.item_general);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swprlPerfilUsuario);




        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary_dark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatosUsuario();
            }
        });



        lstvProyectos.setAdapter(adapter);

        lstvProyectos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(view.getContext(), ProjectDescriptionActivity.class);
                intent.putExtra("idProyecto",adapter.getItem(position-1).id);
                startActivity(intent);
            }
        });


        cargarDatosUsuario();
    }

    private void cargarDatosUsuario()
    {
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
            MobileServiceTable<Area> mAreaTable;
            Proyecto objProyecto;
            Usuario objUsuario;
            Area objArea;
            ArrayList<Proyecto> lstProyectos;

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
                mAreaTable = mClient.getTable("Area",Area.class);

                lstProyectos = new ArrayList<Proyecto>();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    objUsuario = mUsuarioTable.lookUp(idUsuario).get();


                    final MobileServiceList<UsuarioProyecto> result = mUsuarioProyectoTable.where().field("idusuario").eq(objUsuario.id).select("idproyecto","idcargo").execute().get();


                    for(UsuarioProyecto item : result)
                    {
                        objProyecto = new Proyecto();
                        objProyecto = mProyectoTable.lookUp(item.idProyecto).get();

                        objArea = new Area();
                        objArea = mAreaTable.lookUp(objProyecto.idArea).get();
                        objProyecto.nombreArea = objArea.nombre;
                        objProyecto.idCargoAux = item.idCargo;
                        lstProyectos.add(objProyecto);
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            for(Proyecto item : lstProyectos)
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
                    cargarVista(objUsuario);
                }
            }

            @Override
            protected void onCancelled()
            {
                super.onCancelled();
            }
        }.execute();
    }

    private void cargarVista(final Usuario pObjUsuario)
    {
        TextView txtvNombreUsuario = (TextView) headerListView.findViewById(R.id.txtvNombreUsuario);
        TextView txtvBiografiaUsuario = (TextView) headerListView.findViewById(R.id.txtvBiografiaUsuario);
        ImageView imgvEmailUsuario = (ImageView) headerListView.findViewById(R.id.imgvEmailUsuario);
        ImageView imgvTelefonoUsuario = (ImageView) headerListView.findViewById(R.id.imgvTelefonoUsuario);
        ImageView imgvPerfilUsuario = (ImageView) headerListView.findViewById(R.id.imgvPerfilUsuario);

        imgvEmailUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String [] pPara = {pObjUsuario.email};
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

        imgvTelefonoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent Llamada = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + pObjUsuario.telefono));
                    startActivity(Llamada);

                } catch (ActivityNotFoundException activityException) {

                    Toast.makeText(getBaseContext(), "Error al realizar la llamada, intente más tarde", Toast.LENGTH_SHORT).show();

                }
            }
        });

        txtvNombreUsuario.setText(pObjUsuario.nombre + " " + pObjUsuario.primerApellido + " " + pObjUsuario.segundoApellido);

        txtvBiografiaUsuario.setText(pObjUsuario.biografia);

        Glide.with(this)
                .load(pObjUsuario.urlImagen.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(imgvPerfilUsuario);


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
        getMenuInflater().inflate(R.menu.menu_perfil_usuario, menu);
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

                cargarDatosUsuario();
                return true;

            case android.R.id.home:
                super.onBackPressed();
                break;

        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    */
}
