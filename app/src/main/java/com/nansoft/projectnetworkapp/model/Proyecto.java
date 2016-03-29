package com.nansoft.projectnetworkapp.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 6/19/2015.
 */
public class Proyecto implements Parcelable {
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

    @SerializedName("favorito")
    public boolean favorito;

    @SerializedName("cantidadFavoritos")
    public int cantidadFavoritos;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.descripcion);
        dest.writeString(this.email);
        dest.writeString(this.webSite);
        dest.writeInt(this.cantidadSocios);
        dest.writeString(this.fechaCreacion);
        dest.writeString(this.__createdAt);
        dest.writeString(this.urlImagen);
        dest.writeString(this.idEncargado);
        dest.writeString(this.idEstado);
        dest.writeString(this.idArea);
        dest.writeString(this.idCargoAux);
        dest.writeString(this.nombreArea);
        dest.writeString(this.idUsuario);
        dest.writeString(this.primerApellidoUsuario);
        dest.writeString(this.segundoApellidoUsuario);
        dest.writeString(this.nombreUsuario);
        dest.writeString(this.urlImagenUsuario);
        dest.writeByte(favorito ? (byte) 1 : (byte) 0);
        dest.writeInt(this.cantidadFavoritos);
    }

    protected Proyecto(Parcel in) {
        this.id = in.readString();
        this.nombre = in.readString();
        this.descripcion = in.readString();
        this.email = in.readString();
        this.webSite = in.readString();
        this.cantidadSocios = in.readInt();
        this.fechaCreacion = in.readString();
        this.__createdAt = in.readString();
        this.urlImagen = in.readString();
        this.idEncargado = in.readString();
        this.idEstado = in.readString();
        this.idArea = in.readString();
        this.idCargoAux = in.readString();
        this.nombreArea = in.readString();
        this.idUsuario = in.readString();
        this.primerApellidoUsuario = in.readString();
        this.segundoApellidoUsuario = in.readString();
        this.nombreUsuario = in.readString();
        this.urlImagenUsuario = in.readString();
        this.favorito = in.readByte() != 0;
        this.cantidadFavoritos = in.readInt();
    }

    public static final Parcelable.Creator<Proyecto> CREATOR = new Parcelable.Creator<Proyecto>() {
        @Override
        public Proyecto createFromParcel(Parcel source) {
            return new Proyecto(source);
        }

        @Override
        public Proyecto[] newArray(int size) {
            return new Proyecto[size];
        }
    };
}
