package com.nansoft.projectnetworkapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.nansoft.projectnetworkapp.fragment.AreaFragment;
import com.nansoft.projectnetworkapp.fragment.PerfilFragment;
import com.nansoft.projectnetworkapp.fragment.ProyectoFragment;
import com.nansoft.projectnetworkapp.helper.CircularImageView;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.helper.CustomMobileService;
import com.nansoft.projectnetworkapp.model.Area;
import com.nansoft.projectnetworkapp.model.FacebookUser;
import com.nansoft.projectnetworkapp.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        cargarUsuario();

        Fragment fragment = new ProyectoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        // se cambia el fragment
        fragmentManager.beginTransaction()
                .replace(R.id.your_placeholder, fragment)
                .commit();


    }

    public void cargarUsuario()
    {

        List<Pair<String, String> > lp = new ArrayList<Pair<String, String> >();
        lp.add(new Pair("id", CustomMobileService.mClient.getCurrentUser().getUserId()));
        ListenableFuture<FacebookUser> result = CustomMobileService.mClient.invokeApi("user", "GET", null, FacebookUser.class);

        Futures.addCallback(result, new FutureCallback<FacebookUser>() {
            @Override
            public void onFailure(Throwable exc) {
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error al intentar conectar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(final FacebookUser objUsuarioFacebook) {


                new AsyncTask<Void, Void, Boolean>() {


                    MobileServiceTable<Usuario> mUserTable;

                    @Override
                    protected void onPreExecute() {

                        mUserTable = CustomMobileService.mClient.getTable("usuario", Usuario.class);
                    }

                    @Override
                    protected Boolean doInBackground(Void... params) {
                        try {


                            // buscamos por el usuario
                            CustomMobileService.USUARIO_LOGUEADO = mUserTable.lookUp(objUsuarioFacebook.id).get();


                            return true;
                        } catch (final Exception exception) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "error in async " + exception.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });


                            // se verifica si el usuario es null

                            try {
                                // debemos de insertar el registro

                                // establecemos primero los atributos
                                CustomMobileService.USUARIO_LOGUEADO.id = objUsuarioFacebook.id;
                                CustomMobileService.USUARIO_LOGUEADO.nombre = objUsuarioFacebook.name;
                                objUsuarioFacebook.data.PictureURL.PictureURL = "http://graph.facebook.com/" + objUsuarioFacebook.id + "/picture?type=large";
                                CustomMobileService.USUARIO_LOGUEADO.urlImagen = objUsuarioFacebook.data.PictureURL.PictureURL;

                                if (objUsuarioFacebook.cover != null) {
                                    CustomMobileService.USUARIO_LOGUEADO.coverPicture = objUsuarioFacebook.cover.PictureURL;
                                }


                                // agregamos el registro
                                mUserTable.insert(CustomMobileService.USUARIO_LOGUEADO);
                            } catch (final Exception exception2) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "error in sign in " + exception2.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        } finally {
                            // obtenemos la imagen del usuario en caso que la haya cambiado
                            CustomMobileService.USUARIO_LOGUEADO.urlImagen = "http://graph.facebook.com/" + CustomMobileService.USUARIO_LOGUEADO.id + "/picture?type=large";
                            //CustomMobileService.USUARIO_LOGUEADO.setCover_picture(objUsuarioFacebook.cover.PictureURL);
                            if (objUsuarioFacebook.cover != null) {
                                CustomMobileService.USUARIO_LOGUEADO.coverPicture = objUsuarioFacebook.cover.PictureURL;
                            }

                            try {
                                mUserTable.update(CustomMobileService.USUARIO_LOGUEADO).get();
                            } catch (final Exception exception) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "error in update  " + exception.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }

                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean success) {

                        LoadUserInformation();

                    }

                    @Override
                    protected void onCancelled() {
                        super.onCancelled();
                    }
                }.execute();

            }
        });




    }

    private void LoadUserInformation()
    {
        TextView txtvNombreUsuario = (TextView) findViewById(R.id.txtvUserProfileName);
        txtvNombreUsuario.setText(CustomMobileService.USUARIO_LOGUEADO.nombre);

        CircularImageView imgvPerfilUsuario = (CircularImageView) findViewById(R.id.imgvUserProfilePhoto);

        Glide
            .with(this)
            .load(CustomMobileService.USUARIO_LOGUEADO.urlImagen)
            .centerCrop()
            .placeholder(R.drawable.picture)
            .error(R.drawable.picture_removed)
            .into(imgvPerfilUsuario);

        ImageView imgvCover = (ImageView) findViewById(R.id.imgvUserProfileCover);

        Glide
            .with(this)
            .load(CustomMobileService.USUARIO_LOGUEADO.coverPicture)
            .centerCrop()
            .placeholder(R.drawable.picture)
            .error(R.drawable.picture_removed)
            .into(imgvCover);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = new ProyectoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId())
        {
            case R.id.nav_recent:
                fragment = new ProyectoFragment();
                break;

            case R.id.nav_search:
                fragment = new AreaFragment();
                break;
        }

        // se cambia el fragment
        fragmentManager.beginTransaction()
                .replace(R.id.your_placeholder, fragment)
                .commit();

        // establece la navegación al inicio
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void selectItem(String title) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Replace the contents of the container with the new fragment
        ft.replace(R.id.your_placeholder, new AreaFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());

        // Add this transaction to the back stack
        // Append this transaction to the backstack
        //ft.addToBackStack("optional tag");

        // Complete the changes added above
        ft.commit();

    }
}
