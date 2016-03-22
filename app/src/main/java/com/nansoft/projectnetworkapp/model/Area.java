package com.nansoft.projectnetworkapp.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 16/05/2015.
 */
public class Area
{
    @SerializedName("id")
    public String id;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("urlimagen")
    public String urlImagen;

    public Area() {

    }

    public Area(String id, String nombre, String urlImagen) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagen = urlImagen;
    }


}
