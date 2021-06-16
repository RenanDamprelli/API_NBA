package com.example.nba_api;


import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONException;
import org.json.JSONObject;
public class SecondActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;


    private TextView instructions;
    private EditText tmId;
    private TextView Abreviation;
    private TextView City;
    private TextView Conference;
    private TextView FullName;
    private Button ButtonSave;
    private Button ButtonList;
    private ProgressBar load;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        root = findViewById(R.id.root);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        instructions = findViewById(R.id.instructions);
        tmId = findViewById(R.id.edid);
        Abreviation = findViewById(R.id.txtname);
        City = findViewById(R.id.txtposi);
        Conference = findViewById(R.id.txtheight);
        FullName = findViewById(R.id.txtweight);
        ButtonSave =    findViewById(R.id.btnsave);
        ButtonList =    findViewById(R.id.btnlist);
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
                    tmId.setTextColor(Color.rgb(255, 255, 255));
                    Abreviation.setTextColor(Color.rgb(255, 255, 255));
                    City.setTextColor(Color.rgb(255, 255, 255));
                    Conference.setTextColor(Color.rgb(255, 255, 255));
                    FullName.setTextColor(Color.rgb(255, 255, 255));
                }
                if(value>27578) {
                    instructions.setTextColor(Color.rgb(112, 112, 112));
                    tmId.setTextColor(Color.rgb(112, 112, 112));
                    Abreviation.setTextColor(Color.rgb(112, 112, 112));
                    City.setTextColor(Color.rgb(112, 112, 112));
                    Conference.setTextColor(Color.rgb(112, 112, 112));
                    FullName.setTextColor(Color.rgb(112, 112, 112));
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


    public void buscarPlayer(View view) {


        // Recupera a string de busca.
        String queryString = tmId.getText().toString();
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

            Abreviation.setText(R.string.loading);
        }
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (queryString.length() == 0) {

                Abreviation.setText(R.string.no_search_term);
            } else {

                Abreviation.setText(R.string.no_network);
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
        return new BuscarTime(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);

            String abrev = null;
            String city = null;
            String conf = null;
            String full = null;


            // Procura pro resultados nos itens do array

            try {
                load.setVisibility(View.GONE);
                Thread.sleep(500);
                abrev = jsonObject.getString("abbreviation");
                city = jsonObject.getString("city");
                conf = jsonObject.getString("conference");
                full = jsonObject.getString("full_name");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            // move para a proxima linha

            //mostra o resultado qdo possivel.
            if (abrev != null && city != null) {
                FullName.setText("Nome: " + full);
                Abreviation.setText("Abreviação: " + abrev);
                City.setText("Cidade: "+ city);
                Conference.setText("Conferência: "+ conf);


                //nmLivro.setText(R.string.str_empty);
            } else {
                // If none are found, update the UI to show failed results.
                Abreviation.setText(R.string.no_results);

            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
            Abreviation.setText(R.string.no_results);

            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}