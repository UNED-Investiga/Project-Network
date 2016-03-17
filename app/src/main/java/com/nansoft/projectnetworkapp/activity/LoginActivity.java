package com.nansoft.projectnetworkapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.helper.CustomMobileService;

public class LoginActivity extends AppCompatActivity
{
    CustomMobileService customClient;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        //getSupportActionBar().hide();
        customClient = new CustomMobileService(this);
        findViewById(R.id.btnFb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
            }
        });

        // se verifica si el usuario ya está registrado
        if(checkUser())
        {
            // si es así se inicia la acitivuty principal
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);

            // se finaliza para que no pueda volver acá
            finish();
        }

    }

    private boolean checkUser()
    {
        // We first try to load a token cache if one exists.
       return customClient.loadUserTokenCache();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authenticate, menu);
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

    private void authenticate() {

        final ProgressDialog progress = ProgressDialog.show(this, "Cargando",
                "Espere un momemento...", true);

        // We first try to load a token cache if one exists.
        if (customClient.loadUserTokenCache())
        {
            // si es así se inicia la acitivuty principal
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);

            // se finaliza para que no pueda volver acá
            finish();
        }
        // If we failed to load a token cache, login and create a token cache
        else
        {
            // Login using the Google provider.
            ListenableFuture<MobileServiceUser> mLogin = CustomMobileService.mClient.login(MobileServiceAuthenticationProvider.Facebook);

            Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
                @Override
                public void onFailure(Throwable exc) {
                    createAndShowDialog("You must log in. Login Required", "Error");
                }

                @Override
                public void onSuccess(MobileServiceUser user) {

                    customClient.cacheUserToken(CustomMobileService.mClient.getCurrentUser());
                    // si es así se inicia la acitivuty principal
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    // se finaliza para que no pueda volver acá
                    finish();
                }
            });
        }



    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

}
