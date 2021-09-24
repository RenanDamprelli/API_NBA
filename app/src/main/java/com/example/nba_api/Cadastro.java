package com.example.nba_api;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nba_api.models.Usuario;

public class Cadastro extends AppCompatActivity {

    private EditText txtEmailCad;
    private EditText txtUserCad;
    private EditText txtSenhaCad;

   // private AcessoUsuario acesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void Cadastrar(View view) {
        txtEmailCad = findViewById(R.id.txtEmailCad);
        txtUserCad = findViewById(R.id.txtUserCad);
        txtSenhaCad = findViewById(R.id.txtSenhaCad);

        Usuario us = new Usuario();
        us.setemailUser(txtEmailCad.getText().toString());
        us.setusuarioUser(txtUserCad.getText().toString());
        us.setsenhaUser(txtSenhaCad.getText().toString());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Conexao4.criarUsuario(us);

        Toast.makeText(this, "Usuario cadastrado", Toast.LENGTH_SHORT).show();

        Intent intentEntrar = new Intent(getApplicationContext(), Login.class);
        startActivity(intentEntrar);
    }


}
