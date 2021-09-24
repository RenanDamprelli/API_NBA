package com.example.nba_api;

import android.util.Log;

import com.example.nba_api.http.HttpClient;


public class Conexao3 {
    private static final String LOG_TAG = Conexao3.class.getSimpleName();
    // Constantes utilizadas pela API


    static String buscarArenainfo(String queryString) {
        HttpClient client = new HttpClient();
        String responseBody = client.doRequest("http://20.195.199.173/api/arena/" + queryString, "GET");
        Log.d(LOG_TAG, responseBody);
        return responseBody;
    }
}