package com.nansoft.projectnetworkapp.model;


import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by User on 6/19/2015.
 */
public class Usuario
{
    @SerializedName("id")
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("primerapellido")
    private String primerApellido;

    @SerializedName("segundoapellido")
    private String segundoApellido;

    @SerializedName("email")
    private String email;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("urlimagen")
    private String urlImagen;

    private String idCargo;

    private String fechaCreado;

    @SerializedName("biografia")
    private String biografia;

    public Usuario() {
        this.id = "Sin definir";
        this.nombre =  "Sin definir";
        this.primerApellido =  "Sin definir";
        this.segundoApellido =  "Sin definir";
        this.email =  "Sin definir";
        this.telefono =  "Sin definir";
        this.urlImagen =  "Sin definir";
        idCargo =  "Sin definir";
        fechaCreado =  "Sin definir";
    }

    public Usuario(String id, String nombre, String primerApellido, String segundoApellido, String email, String telefono, String urlImagen) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
        this.telefono = telefono;
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

    public String getPrimerApellido() {
        return primerApellido.trim();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido.trim();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEmail() {
        return email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono.trim();
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlImagen() {
        return urlImagen.trim();
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getIdCargo() {
        return idCargo.trim();
    }

    public void setIdCargo(String idCargo) {
        this.idCargo = idCargo;
    }

    public String getFechaCreado() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = "Sin definir";
        try {

            fecha = myFormat.format(fromUser.parse(fechaCreado.substring(0, 10)));
        } catch (ParseException e) {

        }
        return fecha;
    }

    public void setFechaCreado(String fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}
