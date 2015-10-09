package com.nansoft.projectnetworkapp.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 16/05/2015.
 */
public class Area
{
    @SerializedName("id")
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("urlimagen")
    private String urlImagen;

    public Area() {

    }

    public Area(String id, String nombre, String urlImagen) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagen = urlImagen;
    }

    public String getId() {
        return id.trim();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen.trim();
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
