package com.example.nba_api;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BuscarTime extends AsyncTaskLoader<String> {
    private String teamQueryString;
    BuscarTime(Context context, String queryString) {
        super(context);
        teamQueryString = queryString;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return Conexao2.buscarTeaminfo(teamQueryString);
    }
}
