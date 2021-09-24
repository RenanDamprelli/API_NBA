package com.example.nba_api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoDB extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public ConexaoDB(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        String sqlCommand_player = "create table player(id int primary key not null unique,nome varchar(50), posicao varchar(50), altura varchar(50), peso varchar(50))";
        String sqlCommand_team = "create table team(id_team int primary key not null unique,nome_team varchar(50), abreviacao_team varchar(50), cidade_team varchar(50), conferencia_team varchar(50))";
        String sqlCommand_arena = "create table arena(id_arena int primary key not null unique,nome_arena varchar(50), cidade_arena varchar(50), estado_arena varchar(50), capacicade_arena varchar(50), id_team int, foreign key(id_team) references team(id_team))";


        db.execSQL(sqlCommand_player);
        db.execSQL(sqlCommand_team);
        db.execSQL(sqlCommand_arena);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}



