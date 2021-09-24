package com.example.nba_api;

import android.util.Log;

import com.example.nba_api.http.HttpClient;
import com.example.nba_api.models.Usuario;

import java.util.HashMap;


public class Conexao4 {
    private static final String LOG_TAG = com.example.nba_api.Conexao3.class.getSimpleName();
    // Constantes utilizadas pela API


    static String criarUsuario(Usuario usuario) {
        HttpClient client = new HttpClient();
        String responseBody = client.doRequest("http://20.195.199.173/api/usuario", "POST", new HashMap<>(), usuario);
//        Log.d(LOG_TAG, responseBody);
        return responseBody;
    }
}