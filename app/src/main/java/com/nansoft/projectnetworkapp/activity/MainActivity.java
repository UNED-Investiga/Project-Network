package com.nansoft.projectnetworkapp.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.adapter.FragmentPageAdapter;
import com.nansoft.projectnetworkapp.animation.ZoomOutPageTransformer;
import com.nansoft.projectnetworkapp.fragment.AreaFragment;
import com.nansoft.projectnetworkapp.fragment.PerfilFragment;
import com.nansoft.projectnetworkapp.fragment.ProyectoFragment;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;

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


    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        this.setContentView(R.layout.main_activity);

        // Instantiate a ViewPager
        this.pager = (ViewPager) this.findViewById(R.id.pager);
        this.pager.setPageTransformer(true, new ZoomOutPageTransformer());

        // Create an adapter with the fragments we show on the ViewPager
        adapter = new FragmentPageAdapter(
                getSupportFragmentManager(), this);

        adapter.addFragment(new ProyectoFragment());
       // adapter.addFragment(new PerfilFragment());
        adapter.addFragment(new AreaFragment());

        this.pager.setAdapter(adapter);

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);


        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);

        tabs.setOnPageChangeListener(this);


    }

    private void authenticate() {
        // Login using the Google provider.
        MobileServiceClient mClient;
        try {
            mClient = new MobileServiceClient(
                    "https://msprojectnetworkjs.azure-mobile.net/",
                    "gSewfUQpGFAVMRajseDOZwqCCRUwwD62",
                    getApplicationContext()
            );
            ListenableFuture<MobileServiceUser> mLogin = mClient.login(MobileServiceAuthenticationProvider.Facebook);

            Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
                @Override
                public void onFailure(Throwable exc) {
                    Toast.makeText(getApplicationContext(),exc.toString(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MobileServiceUser user) {
                    Toast.makeText(getApplicationContext(),String.format( "You are now logged in - %1$2s",user.getUserId()) + "\nSuccess",Toast.LENGTH_SHORT).show();
                    loadView();
                }
            });
        } catch (MalformedURLException e) {

        }

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
    private int tabIcons[] = {R.drawable.news, R.drawable.categories};
    private int tabIconsActive[] = {R.drawable.news_active, R.drawable.categories_active};
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
