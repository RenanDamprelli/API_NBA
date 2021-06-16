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
        db.execSQL("create table player(id varchar(50) primary key not null unique, " +
                "nome varchar(50), posicao varchar(50), altura varchar(50), peso varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}



