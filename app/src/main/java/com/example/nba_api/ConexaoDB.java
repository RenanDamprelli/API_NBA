package com.example.nba_api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoDB extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;
    private static final String PLAYER = "player";
    private static final String TEAM = "team";

    public ConexaoDB(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
    String player = "CREATE TABLE"+ PLAYER +"(id varchar(50) primary key not null unique, nome varchar(50), posicao varchar(50), altura varchar(50), peso varchar(50)";
    String team = "CREATE TABLE"+ TEAM +"(id varchar(50) primary key not null unique, tmAbrev varchar(50), cidade varchar(50), conf varchar(50), tmNome varchar(50)";
    db.execSQL(player);
    db.execSQL(team);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}



