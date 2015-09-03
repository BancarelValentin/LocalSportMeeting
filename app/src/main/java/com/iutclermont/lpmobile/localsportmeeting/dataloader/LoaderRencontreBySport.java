package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.os.AsyncTask;
import android.util.Log;

import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.RencontreApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 09/12/2014.
 */
public class LoaderRencontreBySport extends AsyncTask<Sport, Void, List<Rencontre>> {

    private RencontreApi myApiService;

    private static Map<Sport, List<Rencontre>> mapSportRen = new HashMap<Sport, List<Rencontre>>();

    private LoaderRencontreBySportCallback callback;

    public LoaderRencontreBySport(RencontreApi myApiService, LoaderRencontreBySportCallback callback) {
        this.myApiService = myApiService;
        this.callback = callback;
    }

    public void getRencontreBySport (Sport sport) {
        if (!mapSportRen.containsKey(sport)) {
            execute(sport);
        }
        else {
            onPostExecute(mapSportRen.get(sport));
        }
    }

    @Override
    protected List<Rencontre> doInBackground(Sport... params) {
        try {
            Log.i("load", "LoaderRencontreBySport: lancement de la requete pour le sport: " + params[0].getLibelle());
            List<Rencontre> listRen = myApiService.getRencontreBySport(params[0].getId()).execute().getItems();
            Log.i("load", "LoaderRencontreBySport: fin de la requete pour le sport: " + params[0].getLibelle());
            mapSportRen.put(params[0], listRen);
            return listRen;
        } catch (IOException e){
            Log.i("load", "LoaderRencontreBySport: Erreur de la requete pour le sport: " + params[0].getLibelle() + "( " + params[0].getId() + " ) ----- " + e.getMessage());
            mapSportRen.put(params[0], new ArrayList<Rencontre>());
            return new ArrayList<Rencontre>();
        }
    }

    @Override
    protected void onPostExecute(List<Rencontre> rencontres) {
        if (callback != null) {
            callback.onLoadRencontreBySport(rencontres);
        }
        else {
            Log.i("load", "LoaderRencontreBySport: callback = null !!!");
        }
    }

    public interface LoaderRencontreBySportCallback {
        public void onLoadRencontreBySport (List<Rencontre> rencontres);
    }

}
