package com.example.nba_api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nba_api.models.Arena;

import java.util.ArrayList;
import java.util.List;

public class arenaDAO {

    private ConexaoDB conexaodb;
    private SQLiteDatabase banco;

    public arenaDAO(Context context){
        conexaodb = new ConexaoDB(context);
        banco = conexaodb.getWritableDatabase();
    }

    public long inserir(Arena arena){
        ContentValues values = new ContentValues();
        values.put("id_arena", arena.getarId());
        values.put("nome_arena", arena.getnomeArena());
        values.put("cidade_arena", arena.getcidadeArena());
        values.put("estado_arena", arena.getestadoArena());
        values.put("capacidade_arena", arena.getcapacidadeArena());
        values.put("id_equipe", arena.gettmId());

        return banco.insert("arena", null, values);


    }

    public List<Arena> obterTodos(){
        List<Arena> arenas = new ArrayList<>();
        Cursor cursor = banco.query("team", new String[]{"id_arena", "nome_arena", "cidade_arena", "estado_arena", "capacidade_arena", "id_equipe"},
        null, null, null, null, null);
        while(cursor.moveToNext()){
            Arena b = new Arena();
            b.setarId(cursor.getString(0));
            b.setnomeArena(cursor.getString(1));
            b.setcidadeArena(cursor.getString(2));
            b.setestadoArena(cursor.getString(3));
            b.setcapacidadeArena(cursor.getString(4));


            arenas.add(b);
        }
        return arenas;
    }

}
