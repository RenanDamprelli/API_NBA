package com.example.nba_api;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.nba_api.models.Player;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;


    private TextView instructions;
    private EditText nmId;
    private TextView nmPlayer;
    private TextView posPlayer;
    private TextView heiPlayer;
    private TextView weiPlayer;
    private Button ButtonSave;
    private Button ButtonList;
    private playerDAO dao;
    private ProgressBar load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        instructions = findViewById(R.id.instructions);
        nmId = findViewById(R.id.edid);
        nmPlayer = findViewById(R.id.txtname);
        posPlayer = findViewById(R.id.txtposi);
        heiPlayer = findViewById(R.id.txtheight);
        weiPlayer = findViewById(R.id.txtweight);
        ButtonSave =    findViewById(R.id.btnsave);
        ButtonList =    findViewById(R.id.btnlist);
        dao = new playerDAO(this);
        load =    findViewById(R.id.progressBar2);

        load.setVisibility(View.GONE);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        // max value for light sensor
        maxValue = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                // between 0 and 255
                int newValue = (int) (255f * value / maxValue);
                root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));

                if(value<=27578) {
                    instructions.setTextColor(Color.rgb(255, 255, 255));
                    nmId.setTextColor(Color.rgb(255, 255, 255));
                    nmPlayer.setTextColor(Color.rgb(255, 255, 255));
                    posPlayer.setTextColor(Color.rgb(255, 255, 255));
                    heiPlayer.setTextColor(Color.rgb(255, 255, 255));
                    weiPlayer.setTextColor(Color.rgb(255, 255, 255));
                }
                if(value>27578) {
                    instructions.setTextColor(Color.rgb(112, 112, 112));
                    nmId.setTextColor(Color.rgb(112, 112, 112));
                    nmPlayer.setTextColor(Color.rgb(112, 112, 112));
                    posPlayer.setTextColor(Color.rgb(112, 112, 112));
                    heiPlayer.setTextColor(Color.rgb(112, 112, 112));
                    weiPlayer.setTextColor(Color.rgb(112, 112, 112));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

    public void salvarDados(View view){
        Player a = new Player();
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
         iniciar o Loader API NBA*/
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            load.setVisibility(View.VISIBLE);
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
                load.setVisibility(View.GONE);
                Thread.sleep(500);
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