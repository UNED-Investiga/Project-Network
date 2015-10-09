package com.nansoft.projectnetworkapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 7/9/2015.
 */
public class UsuarioProyecto
{
    @SerializedName("id")
    private String id;

    @SerializedName("idusuario")
    private String idUsuario;

    @SerializedName("idproyecto")
    private String idProyecto;

    @SerializedName("idcargo")
    private String idCargo;

    @SerializedName("__createdAt")
    private String __createdAt;

    public UsuarioProyecto(String id, String idUsuario, String idProyecto, String idCargo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
        this.idCargo = idCargo;
    }

    public String getId() {
        return id.trim();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario.trim();
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdProyecto() {
        return idProyecto.trim();
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getIdCargo() {
        return idCargo.trim();
    }

    public void setIdCargo(String idCargo) {
        this.idCargo = idCargo;
    }

    public String get__createdAt() {
        return __createdAt.trim();
    }

    public void set__createdAt(String __createdAt) {
        this.__createdAt = __createdAt;
    }
}
