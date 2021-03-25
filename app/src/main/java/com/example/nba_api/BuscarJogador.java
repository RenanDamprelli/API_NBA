package com.example.nba_api;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BuscarJogador extends AsyncTaskLoader<String> {
    private String mQueryString;
    BuscarJogador(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return com.example.nba_api.Conexao.buscarPlayerinfo(mQueryString);
    }
}
