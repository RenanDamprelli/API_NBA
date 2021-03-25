package com.example.nba_api;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListarPlayerActivity extends AppCompatActivity {

    private ListView listView;
    private com.example.nba_api.playerDAO dao;
    private List<com.example.nba_api.Player> players;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_player);

        listView = findViewById(R.id.listar_players);

        dao = new com.example.nba_api.playerDAO(this);
        players = dao.obterTodos();
        ArrayAdapter<com.example.nba_api.Player> adaptador = new ArrayAdapter<com.example.nba_api.Player>(this, android.R.layout.simple_list_item_1, players);
        listView.setAdapter(adaptador);
    }
}