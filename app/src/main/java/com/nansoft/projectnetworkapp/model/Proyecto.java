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
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("email")
    private String email;

    @SerializedName("website")
    private String webSite;

    @SerializedName("cantidadsocios")
    private int cantidadSocios;

    @SerializedName("fechacreacion")
    private String fechaCreacion;

    @SerializedName("__createdAt")
    private String __createdAt;

    @SerializedName("urlimagen")
    private String urlImagen;

    @SerializedName("idencargado")
    private String idEncargado;

    @SerializedName("idestado")
    private String idEstado;

    @SerializedName("idarea")
    private String idArea;

    private String idCargoAux;

    private String nombreAux;

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

    public String getDescripcion() {
        return descripcion.trim();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmail() {
        return email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite.trim();
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public int getCantidadSocios() {
        return cantidadSocios;
    }

    public void setCantidadSocios(int cantidadSocios) {
        this.cantidadSocios = cantidadSocios;
    }

    public String getUrlImagen() {

        if (urlImagen == null)
        {
            return "n/a";
        }
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getIdEncargado() {
        return idEncargado.trim();
    }

    public void setIdEncargado(String idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getIdEstado() {
        return idEstado.trim();
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public String getIdArea() {
        return idArea.trim();
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
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

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String get__createdAt() {
        return __createdAt;
    }

    public void set__createdAt(String __createdAt) {
        this.__createdAt = __createdAt;
    }

    public String getDiferenciaFecha()
    {
        // Crear 2 instancias de Calendar

        Calendar cal1 = Calendar.getInstance();

        Calendar cal2 = Calendar.getInstance();


        // Establecer las fechas

        cal2.set(Integer.parseInt(fechaCreacion.substring(0, 4)), Integer.parseInt(fechaCreacion.substring(5, 7)), Integer.parseInt(fechaCreacion.substring(8, 10)));


        // conseguir la representacion de la fecha en milisegundos

        long milis1 = cal1.getTimeInMillis();

        long milis2 = cal2.getTimeInMillis();


        // calcular la diferencia en milisengundos

        long diff = milis2 - milis1;


        long diffSeconds = diff / 1000;


        // calcular la diferencia en minutos

        long diffMinutes = diff / (60 * 1000);


        // calcular la diferencia en horas

        long diffHours = diff / (60 * 60 * 1000);
        // calcular la diferencia en dias
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return String.valueOf(diffDays);
    }

    public String getIdCargoAux() {
        return idCargoAux;
    }

    public void setIdCargoAux(String idCargoAux) {
        this.idCargoAux = idCargoAux;
    }

    public String getNombreAux() {
        return nombreAux.trim();
    }

    public void setNombreAux(String nombreAux) {
        this.nombreAux = nombreAux;
    }
}
