package com.example.nba_api;

import android.util.Log;

import com.example.nba_api.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Conexao {
    private static final String LOG_TAG = com.example.nba_api.Conexao.class.getSimpleName();
    // Constantes utilizadas pela API
    // URL para a API de NBA.


    static String buscarPlayerinfo(String queryString) {
        HttpClient client = new HttpClient();
        Map<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-host","free-nba.p.rapidapi.com");
        headers.put("x-rapidapi-key","39a9ab5be5mshf040cf4dd465cd8p115a6fjsn1e1f6da7bf51");

        String responseBody = client.doRequest("https://free-nba.p.rapidapi.com/players/" + queryString, "GET", headers);
        Log.d(LOG_TAG, responseBody);
        return responseBody;
    }
}