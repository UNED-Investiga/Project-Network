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
    public String id;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("primerapellido")
    public String primerApellido;

    @SerializedName("segundoapellido")
    public String segundoApellido;

    @SerializedName("email")
    public String email;

    @SerializedName("telefono")
    public String telefono;

    @SerializedName("urlimagen")
    public String urlImagen;

    public String idCargo;

    public String fechaCreado;

    @SerializedName("biografia")
    public String biografia;

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


}
