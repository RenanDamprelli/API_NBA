package com.example.nba_api.models;

import java.io.Serializable;

public class Arena implements Serializable {

    private String arId;
    private String nomeArena;
    private String cidadeArena;
    private String estadoArena;
    private String capacidadeArena;
    private String tmId;

    public String getarId() {
        return arId;
    }

    public void setarId(String arId) {
        this.arId = arId;
    }

    public String getnomeArena() {
        return nomeArena;
    }

    public void setnomeArena(String nomeArena) {
        this.nomeArena = nomeArena;
    }

    public String getcidadeArena() {
        return cidadeArena;
    }

    public void setcidadeArena(String cidadeArena) {
        this.cidadeArena = cidadeArena;
    }

    public String getestadoArena() {
        return estadoArena;
    }

    public void setestadoArena(String estadoArena) {
        this.estadoArena = estadoArena;
    }

    public String getcapacidadeArena() {
        return capacidadeArena;
    }

    public void setcapacidadeArena(String capacidadeArena) { this.capacidadeArena = capacidadeArena; }

    public String gettmId() {
        return tmId;
    }

    public void settmId(String tmId) {
        this.tmId = tmId;
    }

    @Override
    public String toString(){
        return
                "\n ID: " + getarId()
                + "\n" + getnomeArena()
                + "\n" + getcidadeArena()
                + "\n" + getestadoArena()
                + "\n" + getcapacidadeArena()
                + "\n" + gettmId()
                +"\n";
    }
}
