package com.example.nba_api;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText nmId;
    private TextView nmPlayer;
    private TextView posPlayer;
    private TextView heiPlayer;
    private TextView weiPlayer;
    private Button ButtonSave;
    private Button ButtonList;
    private playerDAO dao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nmId = findViewById(R.id.edid);
        nmPlayer = findViewById(R.id.txtname);
        posPlayer = findViewById(R.id.txtposi);
        heiPlayer = findViewById(R.id.txtheight);
        weiPlayer = findViewById(R.id.txtweight);
        ButtonSave =    findViewById(R.id.btnsave);
        ButtonList =    findViewById(R.id.btnlist);
        dao = new playerDAO(this);


        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void salvarDados(View view){
        com.example.nba_api.Player a = new com.example.nba_api.Player();
        a.setNmId(nmId.getText().toString());
        a.setNmPlayer(nmPlayer.getText().toString());
        a.setPosPlayer(posPlayer.getText().toString());
        a.setHeiPlayer(heiPlayer.getText().toString());
        a.setWeiPlayer(weiPlayer.getText().toString());
        long nmId = dao.inserir(a);
        Toast.makeText(this, "Player inserido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void consultarDados(View view){
        Intent intent = new Intent(this, ListarPlayerActivity.class);
        startActivity(intent);
    }

    public void buscarPlayer(View view) {
        // Recupera a string de busca.
        String queryString = nmId.getText().toString();
        // esconde o teclado qdo o botão é clicado
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Verifica o status da conexão de rede
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        /* Se a rede estiver disponivel e o campo de busca não estiver vazio
         iniciar o Loader CarregaLivros */
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            nmPlayer.setText(R.string.loading);
        }
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (queryString.length() == 0) {

                nmPlayer.setText(R.string.no_search_term);
            } else {

                nmPlayer.setText(R.string.no_network);
            }
        }
    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new BuscarJogador(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);

            String name = null;
            String lastname = null;
            String position = null;
            String height_ft = null;
            String height_in = null;
            String weight = null;

            // Procura pro resultados nos itens do array

            try {
                name = jsonObject.getString("first_name");
                lastname = jsonObject.getString("last_name");
                position = jsonObject.getString("position");
                height_ft = jsonObject.getString("height_feet");
                height_in = jsonObject.getString("height_inches");

                weight = jsonObject.getString("weight_pounds");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // move para a proxima linha

            //mostra o resultado qdo possivel.
            if (name != null && lastname != null) {
                nmPlayer.setText("Nome: " + name + " " + lastname);
                posPlayer.setText("Posição: "+ position);
                heiPlayer.setText("Altura: "+ height_ft +" ft "+ height_in + " in");

                weiPlayer.setText("Peso: " + weight + " libras");


                //nmLivro.setText(R.string.str_empty);
            } else {
                // If none are found, update the UI to show failed results.
                nmPlayer.setText(R.string.no_results);

            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
            nmPlayer.setText(R.string.no_results);

            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}