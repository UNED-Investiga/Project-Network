package com.nansoft.projectnetworkapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 7/9/2015.
 */
public class UsuarioProyecto
{
    @SerializedName("id")
    public String id;

    @SerializedName("idusuario")
    public String idUsuario;

    @SerializedName("idproyecto")
    public String idProyecto;

    @SerializedName("idcargo")
    public String idCargo;

    @SerializedName("__createdAt")
    public String __createdAt;

    public UsuarioProyecto(String id, String idUsuario, String idProyecto, String idCargo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
        this.idCargo = idCargo;
    }

}
