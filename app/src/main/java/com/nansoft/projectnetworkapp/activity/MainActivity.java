package com.nansoft.projectnetworkapp.activity;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Pair;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.adapter.FragmentPageAdapter;
import com.nansoft.projectnetworkapp.animation.ZoomOutPageTransformer;
import com.nansoft.projectnetworkapp.fragment.AreaFragment;
import com.nansoft.projectnetworkapp.fragment.PerfilFragment;
import com.nansoft.projectnetworkapp.fragment.ProyectoFragment;

import java.util.ArrayList;
import java.util.List;

import com.nansoft.projectnetworkapp.helper.CustomMobileService;
import com.nansoft.projectnetworkapp.model.FacebookUser;
import com.nansoft.projectnetworkapp.model.Usuario;

public class MainActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener
{

/**
 * The pager widget, which handles animation and allows swiping horizontally
 * to access previous and next pages.
 */


/**
 * The pager adapter, which provides the pages to the view pager widget.
 */


    private PagerSlidingTabStrip tabs;
    private ViewPager pager = null;
    private FragmentPageAdapter adapter;
    public static CustomMobileService customClient;

    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        this.setContentView(R.layout.activity_main);

        customClient = new CustomMobileService(this);

        try{
            // cargamos el token
            customClient.loadUserTokenCache();

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"er " + e.toString(),Toast.LENGTH_SHORT).show();

        }

        // cargamos la información de usuario
        cargarUsuario();



        // Instantiate a ViewPager
        this.pager = (ViewPager) this.findViewById(R.id.pager);
        this.pager.setPageTransformer(true, new ZoomOutPageTransformer());

        // Create an adapter with the fragments we show on the ViewPager
        adapter = new FragmentPageAdapter(
                getSupportFragmentManager(), this);

        adapter.addFragment(new ProyectoFragment());
        adapter.addFragment(new AreaFragment());
        adapter.addFragment(new PerfilFragment());

        this.pager.setAdapter(adapter);

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);


        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);

        tabs.setOnPageChangeListener(this);


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



                    }

                    @Override
                    protected void onCancelled() {
                        super.onCancelled();
                    }
                }.execute();

            }
        });


    }

    private void loadView()
    {

    }

    /*

    @Override
    public void onBackPressed() {

            // Return to previous page when we press back button
            if (this.pager.getCurrentItem() == 0)
            super.onBackPressed();
            else
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    //private int tabIcons[] = {R.drawable.news, R.drawable.handshake, R.drawable.categories};
    //private int tabIconsActive[] = {R.drawable.news_active, R.drawable.handshake_active, R.drawable.categories_active};
    private int tabIcons[] = {R.drawable.news, R.drawable.categories,R.drawable.handshake};
    private int tabIconsActive[] = {R.drawable.news_active, R.drawable.categories_active,R.drawable.handshake_active};
    @Override
    public void onPageSelected(int position) {
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        LinearLayout view = (LinearLayout) tabStrip.getChildAt(0);
        ImageView imageView;
        int idImagen;
        for(int i=0;i<tabIconsActive.length;i++)
        {
            imageView = (ImageView) view.getChildAt(i);
            if(i == position)
            {
                idImagen = tabIconsActive[position];
            }
            else
            {
                idImagen = tabIcons[i];
            }
            imageView.setImageResource(idImagen);
        }

        //Toast.makeText(getApplicationContext(),"position -> " + String.valueOf(position),Toast.LENGTH_LONG ).show();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
