package com.nansoft.projectnetworkapp.helper;

/**
 * Created by Carlos on 30/10/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.nansoft.projectnetworkapp.model.Usuario;

import java.net.MalformedURLException;

/**
 * Created by Carlos on 01/10/2015.
 */
public class CustomMobileService
{
    private final String URL_MOBILE_SERVICES =  "https://msprojectnetworkjs.azure-mobile.net/";
    private final String KEY_MOBILE_SERVICES =  "gSewfUQpGFAVMRajseDOZwqCCRUwwD62";

    private Context contex;
    public static MobileServiceClient mClient;

    // variables para almacenar el token del usuario
    private static final String SHAREDPREFFILE = "temp";
    private static final String USERIDPREF = "uid";
    private static final String TOKENPREF = "tkn";
    public static Usuario USUARIO_LOGUEADO = new Usuario();

    public CustomMobileService(Context pContex)
    {
        contex = pContex;
        try {
            // Create the Mobile Service Client instance, using the provided
            // Mobile Service URL and key
            mClient = new MobileServiceClient(URL_MOBILE_SERVICES
                    ,KEY_MOBILE_SERVICES,
                    contex);

        } catch (MalformedURLException e) {
            Toast.makeText(contex,"Error al conectar con el mobile services",Toast.LENGTH_SHORT).show();
        }


    }


    // guarda el id de usuario y token con acceso privado
    public void cacheUserToken(MobileServiceUser user)
    {

        SharedPreferences prefs = contex.getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USERIDPREF, user.getUserId());
        editor.putString(TOKENPREF, user.getAuthenticationToken());
        editor.commit();
    }

    public boolean loadUserTokenCache()
    {
        SharedPreferences prefs = contex.getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(USERIDPREF, "undefined");
        if (userId == "undefined")
            return false;
        String token = prefs.getString(TOKENPREF, "undefined");
        if (token == "undefined")
            return false;

        MobileServiceUser user = new MobileServiceUser(userId);
        user.setAuthenticationToken(token);
        mClient.setCurrentUser(user);

        return true;
    }

    public void deleteUserToken()
    {

        SharedPreferences prefs = contex.getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();

    }




}
