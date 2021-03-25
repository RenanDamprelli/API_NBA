package com.example.nba_api;

import java.io.Serializable;

public class Player implements Serializable {

    private String nmId;
    private String nmPlayer;
    private String posPlayer;
    private String heiPlayer;
    private String weiPlayer;

    public String getNmId() {
        return nmId;
    }

    public void setNmId(String nmId) {
        this.nmId = nmId;
    }

    public String getNmPlayer() {
        return nmPlayer;
    }

    public void setNmPlayer(String nmPlayer) {
        this.nmPlayer = nmPlayer;
    }

    public String getPosPlayer() {
        return posPlayer;
    }

    public void setPosPlayer(String posPlayer) {
        this.posPlayer = posPlayer;
    }

    public String getHeiPlayer() {
        return heiPlayer;
    }

    public void setHeiPlayer(String heiPlayer) {
        this.heiPlayer = heiPlayer;
    }

    public String getWeiPlayer() {
        return weiPlayer;
    }

    public void setWeiPlayer(String weiPlayer) {
        this.weiPlayer = weiPlayer;
    }

    @Override
    public String toString(){
        return
                "\n ID: " + getNmId()
                + "\n" + getNmPlayer()
                + "\n" + getPosPlayer()
                + "\n" + getHeiPlayer()
                + "\n" + getWeiPlayer()
                +"\n";
    }
}
