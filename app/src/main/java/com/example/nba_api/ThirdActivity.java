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

import com.example.nba_api.models.Arena;

import org.json.JSONException;
import org.json.JSONObject;

public class ThirdActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {


    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;


    private TextView instructions;
    private EditText arId;
    private TextView nomeArena;
    private TextView cidadeArena;
    private TextView estadoArena;
    private TextView capacidadeArena;
    private TextView tmId;
    private Button ButtonSave;
    private Button ButtonList;
    private arenaDAO dao;
    private ProgressBar load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        root = findViewById(R.id.root);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        instructions = findViewById(R.id.instructions);
        arId = findViewById(R.id.edid);
        nomeArena = findViewById(R.id.txtnome);
        cidadeArena = findViewById(R.id.txtcidade);
        estadoArena = findViewById(R.id.txtestado);
        capacidadeArena = findViewById(R.id.txtcapacidade);
        /*tmId = findViewById(R.id.txtweight);*/
        ButtonSave = findViewById(R.id.btnsave);
        ButtonList = findViewById(R.id.btnlist);
        dao = new arenaDAO(this);
        load = findViewById(R.id.progressBar2);

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

                if (value <= 27578) {
                    instructions.setTextColor(Color.rgb(255, 255, 255));
                    arId.setTextColor(Color.rgb(255, 255, 255));
                    nomeArena.setTextColor(Color.rgb(255, 255, 255));
                    cidadeArena.setTextColor(Color.rgb(255, 255, 255));
                    estadoArena.setTextColor(Color.rgb(255, 255, 255));
                    capacidadeArena.setTextColor(Color.rgb(255, 255, 255));
                }
                if (value > 27578) {
                    instructions.setTextColor(Color.rgb(112, 112, 112));
                    arId.setTextColor(Color.rgb(112, 112, 112));
                    nomeArena.setTextColor(Color.rgb(112, 112, 112));
                    cidadeArena.setTextColor(Color.rgb(112, 112, 112));
                    estadoArena.setTextColor(Color.rgb(112, 112, 112));
                    capacidadeArena.setTextColor(Color.rgb(112, 112, 112));
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
            Arena b = new Arena();
            b.setarId(arId.getText().toString());
            b.setnomeArena(nomeArena.getText().toString());
            b.setcidadeArena(cidadeArena.getText().toString());
            b.setestadoArena(estadoArena.getText().toString());
            b.setcapacidadeArena(capacidadeArena.getText().toString());
            long arId = dao.inserir(b);
            Toast.makeText(this, "Player inserido com sucesso!", Toast.LENGTH_SHORT).show();
        }

        public void consultarDados(View view){
            Intent intent = new Intent(this, ListarPlayerActivity.class);
            startActivity(intent);
        }

    public void buscarArena(View view) {


        // Recupera a string de busca.
        String queryString = arId.getText().toString();
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

            nomeArena.setText(R.string.loading);
        }
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (queryString.length() == 0) {

                nomeArena.setText(R.string.no_search_term);
            } else {

                nomeArena.setText(R.string.no_network);
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
        return new BuscarArena(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);

            String name = null;
            String city = null;
            String state = null;
            String capacity = null;


            // Procura pro resultados nos itens do array

            try {
                load.setVisibility(View.GONE);
                Thread.sleep(500);
                name = jsonObject.getString("nomeArena");
                city = jsonObject.getString("cidadeArena");
                state = jsonObject.getString("estadoArena");
                capacity = jsonObject.getString("capacidadeArena");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            // move para a proxima linha

            //mostra o resultado qdo possivel.
            if (name != null) {
                nomeArena.setText("Nome: " + name);
                cidadeArena.setText("Cidade: " + city);
                estadoArena.setText("Estado: " + state);
                capacidadeArena.setText("Capacidade: " + capacity);


                //nmLivro.setText(R.string.str_empty);
            } else {
                // If none are found, update the UI to show failed results.
                nomeArena.setText(R.string.no_results);

            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
            nomeArena.setText(R.string.no_results);

            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}
