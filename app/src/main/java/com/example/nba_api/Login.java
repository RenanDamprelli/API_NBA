package com.example.nba_api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nba_api.models.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button btnlogar;
    EditText txtCampoUsuario;
    EditText txtCampoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogar = (Button) findViewById(R.id.btnlogar);
        txtCampoUsuario = (EditText) findViewById(R.id.txtCampoUsuario);
        txtCampoSenha = (EditText) findViewById(R.id.txtCampoSenha);
    }

    public void Cadastro(View view) {

        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    public void Logar(View view) {

        Usuario us = new Usuario();
        us.setusuarioUser(txtCampoUsuario.getText().toString());
        us.setsenhaUser(txtCampoSenha.getText().toString());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Usuario usuarioLogado = Conexao5.logarUsuario(us);

        if(usuarioLogado.getidUser() == 0 && usuarioLogado.getusuarioUser() == null) {
            Toast.makeText(this, "Usuario inválido", Toast.LENGTH_SHORT).show();
        } else {
            Intent intentEntrar = new Intent(getApplicationContext(), Menu.class);
            startActivity(intentEntrar);
        }

    }

}