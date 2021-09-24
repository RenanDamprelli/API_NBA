package com.example.nba_api;

import android.util.Log;

import com.example.nba_api.http.HttpClient;
import com.example.nba_api.models.Usuario;
import com.google.gson.Gson;

import java.util.HashMap;


public class Conexao5 {
    private static final String LOG_TAG = com.example.nba_api.Conexao5.class.getSimpleName();
    // Constantes utilizadas pela API


    static Usuario logarUsuario(Usuario usuario) {
        HttpClient client = new HttpClient();
        String responseBody = client.doRequest("http://20.195.199.173/api/usuario/login", "POST", new HashMap<>(), usuario);
//        Log.d(LOG_TAG, responseBody);
        return new Gson().fromJson(responseBody, Usuario.class);
    }
}