package com.nansoft.projectnetworkapp.model;


import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 6/19/2015.
 */
public class Proyecto
{
    @SerializedName("id")
    public String id;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("email")
    public String email;

    @SerializedName("website")
    public String webSite;

    @SerializedName("cantidadsocios")
    public int cantidadSocios;

    @SerializedName("fechacreacion")
    public String fechaCreacion;

    @SerializedName("__createdAt")
    public String __createdAt;

    @SerializedName("urlimagen")
    public String urlImagen;

    @SerializedName("idencargado")
    public String idEncargado;

    @SerializedName("idestado")
    public String idEstado;

    @SerializedName("idarea")
    public String idArea;

    public String idCargoAux;

    @SerializedName("nombre_area")
    public String nombreArea;

    @SerializedName("id_usuario")
    public String idUsuario;

    @SerializedName("primerapellido_usuario")
    public String primerApellidoUsuario;

    @SerializedName("segundoapellido_usuario")
    public String segundoApellidoUsuario;

    @SerializedName("nombre_usuario")
    public String nombreUsuario;

    @SerializedName("urlimagen_usuario")
    public String urlImagenUsuario;

    public Proyecto() {
    }

    public Proyecto(String id, String nombre, String descripcion, String email, String webSite, int cantidadSocios, String fechaInicio, String urlImagen, String idEncargado, String idEstado, String idArea,String fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.email = email;
        this.webSite = webSite;
        this.cantidadSocios = cantidadSocios;
        this.urlImagen = urlImagen;
        this.idEncargado = idEncargado;
        this.idEstado = idEstado;
        this.idArea = idArea;
        this.fechaCreacion = fechaCreacion;
    }

    public Proyecto(String id, String nombre, String idArea, String fechaCreacion, String urlImagen) {
        this.id = id;
        this.nombre = nombre;
        this.idArea = idArea;
        this.fechaCreacion = fechaCreacion;
        this.urlImagen = urlImagen;
    }

    public String getFechaCreacion() {

        //return getDiferenciaFecha();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = "Sin definir";
        try {

            fecha = myFormat.format(fromUser.parse(__createdAt.substring(0,10)));
        } catch (ParseException e) {

        }
        return fecha;
    }

}
