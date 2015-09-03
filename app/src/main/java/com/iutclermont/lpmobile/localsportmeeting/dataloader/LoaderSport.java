package com.iutclermont.lpmobile.localsportmeeting.dataloader;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.SportApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LoaderSport extends AsyncTask<Void, Void, Boolean> {

    private static SportApi myApiService = null;


    private static List<Sport> listSports;

    private LoaderSportCallBack callbak;

    public LoaderSport() {
    }

    public LoaderSport(LoaderSportCallBack callbak) {
        this.callbak = callbak;
    }

    public Sport getOneById(Long id) {
        for (Sport s : listSports) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }


    protected Boolean doInBackground(Void... params) {
        if(myApiService == null) { // Only do this once
            SportApi.Builder builder = new SportApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            myApiService = builder.build();
        }
        try {
            Log.i("load", "LoaderSport: lancement de la requete pour les sports!!");
            List<Sport> listSports = myApiService.list().execute().getItems();
            Log.i("load", "LoaderSport: fin de la requete pour les sports!!");
            this.listSports = listSports;
            return true;
        } catch (IOException e){
            this.listSports = new ArrayList<Sport>();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (callbak != null) {
            Log.i("load", "LoaderSport: Appel du callback!!");
            callbak.OnSportLoaded(listSports);
        }
        else {
            Log.i("load", "LoaderSport: Callback = null!!");
        }
    }

    public void getAll () {
        if (listSports == null) {
            execute();
        }
    }

    public interface LoaderSportCallBack {

        public void OnSportLoaded (List<Sport> sports);
    }
}
