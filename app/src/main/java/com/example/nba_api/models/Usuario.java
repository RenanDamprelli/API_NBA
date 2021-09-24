package com.example.nba_api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {

    @SerializedName(value = "IdUsuario", alternate = {"idUsuario"})
    private int idUser;
    @SerializedName(value = "EmailUsuario", alternate = {"emailUsuario"})
    private String emailUser;
    @SerializedName(value = "UserUsuario", alternate = {"userUsuario"})
    private String usuarioUser;
    @SerializedName(value = "SenhaUsuario", alternate = {"senhaUsuario"})
    private String senhaUser;

    public int getidUser() {
        return idUser;
    }

    public void setidUser(int idUser) {
        this.idUser = idUser;
    }

    public String getemailUser() {
        return emailUser;
    }

    public void setemailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getusuarioUser() {
        return usuarioUser;
    }

    public void setusuarioUser(String usuarioUser) {
        this.usuarioUser = usuarioUser;
    }

    public String getsenhaUser() {
        return senhaUser;
    }

    public void setsenhaUser(String senhaUser) {
        this.senhaUser = senhaUser;
    }

    @Override
    public String toString() {
        return
                "\n ID: " + getidUser()
                        + "\n" + getemailUser()
                        + "\n" + getusuarioUser()
                        + "\n" + getsenhaUser()
                        + "\n";
    }
}