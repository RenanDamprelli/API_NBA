package com.example.nba_api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nba_api.models.Team;

import java.util.ArrayList;
import java.util.List;

public class teamDAO {

    private ConexaoDB conexaodb;
    private SQLiteDatabase banco;

    public teamDAO(Context context){
        conexaodb = new ConexaoDB(context);
        banco = conexaodb.getWritableDatabase();
    }

    public long inserir(Team team){
        ContentValues values = new ContentValues();
        values.put("id_team", team.gettmId());
        values.put("abreviacao_team", team.getAbreviation());
        values.put("cidade_team", team.getCity());
        values.put("conferencia_team", team.getConference());
        values.put("nome_team", team.getFullName());

        return banco.insert("team", null, values);


    }

    public List<Team> obterTodos(){
        List<Team> teams = new ArrayList<>();
        Cursor cursor = banco.query("team", new String[]{"id_team", "nome_team", "abreviacao_team", "cidade_team", "conferencia_team"},
        null, null, null, null, null);
        while(cursor.moveToNext()){
            Team b = new Team();
            b.settmId(cursor.getString(0));
            b.setFullName(cursor.getString(1));
            b.setAbreviation(cursor.getString(2));
            b.setCity(cursor.getString(3));
            b.setConference(cursor.getString(4));
            teams.add(b);
        }
        return teams;
    }

}
