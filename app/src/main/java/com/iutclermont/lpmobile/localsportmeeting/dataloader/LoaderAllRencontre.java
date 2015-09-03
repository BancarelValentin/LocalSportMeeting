package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.os.AsyncTask;
import android.util.Log;

import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.RencontreApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 09/12/2014.
 */
public class LoaderAllRencontre extends AsyncTask<Void, Void, List<Rencontre>> {

    private RencontreApi myApiService;

    private LoaderAllRencontre.LoaderRencontreCallback callback;

    private static List<Rencontre> listRencontreMainAc;

    public LoaderAllRencontre (RencontreApi myApiService, LoaderAllRencontre.LoaderRencontreCallback callback) {
        this.myApiService = myApiService;
        this.callback = callback;
    }

    public void getAllRencontre () {
        if (listRencontreMainAc == null) {
            execute();
        }
        else {
            onPostExecute(listRencontreMainAc);
        }
    }

    public static Rencontre getOneById (Long id) {
        for (Rencontre r : listRencontreMainAc) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    @Override
    protected List<Rencontre> doInBackground(Void... params) {
        try {
            Log.i("load", "LoaderRencontre getAll: lancement de la requete!!");
            List<Rencontre> tempList = myApiService.list().setLimit(10).execute().getItems();
            Log.i("load", "LoaderRencontre getAll: fin de la requete!!");
            if (tempList == null) {
                tempList = new ArrayList<Rencontre>();
            }
            return tempList;

        } catch (IOException e){
            Log.i("load", "LoaderAllRencontre:  Erreur ---- " + e.getMessage());
            return new ArrayList<Rencontre>();
        }
    }

    @Override
    protected void onPostExecute(List<Rencontre> rencontres) {
        listRencontreMainAc = rencontres;
        if (callback != null) {
            callback.onAllRencontreLoaded(rencontres);
        }
    }

    public interface LoaderRencontreCallback {
        public void onAllRencontreLoaded (List<Rencontre> rencontres);
    }
}
