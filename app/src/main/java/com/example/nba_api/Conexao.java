package com.example.nba_api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conexao {
    private static final String LOG_TAG = com.example.nba_api.Conexao.class.getSimpleName();
    // Constantes utilizadas pela API
    // URL para a API de Livros do Google.


    static String buscarPlayerinfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        try {
            // Construção da URI de Busca


            // Converte a URI para a URL.
            URL requestURL = new URL("https://free-nba.p.rapidapi.com/players/" + queryString);
            // Abre a conexão de rede
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("x-rapidapi-host","free-nba.p.rapidapi.com");
            urlConnection.setRequestProperty("x-rapidapi-key","39a9ab5be5mshf040cf4dd465cd8p115a6fjsn1e1f6da7bf51");
            urlConnection.connect();

            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Cria o buffer para o input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Usa o StringBuilder para receber a resposta.
            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Adiciona a linha a string.
                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                // se o stream estiver vazio não faz nada
                return null;
            }

            bookJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // escreve o Json no log
        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }
}