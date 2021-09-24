package com.example.nba_api;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BuscarArena extends AsyncTaskLoader<String> {
    private String arenaQueryString;
    BuscarArena(Context context, String queryString) {
        super(context);
        arenaQueryString = queryString;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return Conexao3.buscarArenainfo(arenaQueryString);
    }
}
