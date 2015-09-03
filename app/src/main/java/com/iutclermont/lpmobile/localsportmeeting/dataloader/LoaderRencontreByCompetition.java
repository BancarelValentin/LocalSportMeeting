package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.os.AsyncTask;
import android.util.Log;

import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.RencontreApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.RencontreApiRequest;
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
    public class LoaderRencontreByCompetition extends AsyncTask<Competition, Void, LoaderRencontreByCompetition.ResultRencontre> {

    private static Map<Competition, List<Rencontre>> mapComptRencontre = new HashMap<Competition, List<Rencontre>>();

    private RencontreApi myApiService;

    private LoaderRencontreByCompetitionCallback callback;

    public LoaderRencontreByCompetition(RencontreApi myApiService, LoaderRencontreByCompetitionCallback callback) {
        this.myApiService = myApiService;
        this.callback = callback;
    }

    @Override
    protected LoaderRencontreByCompetition.ResultRencontre doInBackground(Competition... params) {
        try {
            Log.i("load", "LoaderRencontreByCompetition: lancement de la requete pour la competition: " + params[0].getLibelle());
            List<Rencontre> listRen = myApiService.getRencontreByCompetition((params[0]).getId()).execute().getItems();
            Log.i("load", "LoaderRencontreByCompetition: fin de la requete pour la competition: " + params[0].getLibelle());
            mapComptRencontre.put(params[0], listRen);
            return new ResultRencontre(params[0], listRen);

        } catch (IOException e){
            Log.i("load", "LoaderRencontreByCompetition: erreur de la requete pour la competition: " + params[0].getLibelle() + "( " + params[0].getId() + " ) --- " + e.getMessage());
            mapComptRencontre.put(params[0], new ArrayList<Rencontre>());
            return new ResultRencontre(params[0], new ArrayList<Rencontre>());
        }
    }

    @Override
    protected void onPostExecute(LoaderRencontreByCompetition.ResultRencontre result) {
        if (callback != null) {
            callback.onRencontreLoadedByCompetition(result);
        }
        else {
            Log.i("load", "LoaderRencontreByCompetition: callback = null !!! ");
        }
    }

    public void getRencontres (Competition cp) {
        if (!mapComptRencontre.containsKey(cp)){
            execute(cp);
        }
        else {
            onPostExecute(new ResultRencontre(cp, mapComptRencontre.get(cp)));
        }
    }

    public static Rencontre getOneById (Long id) {
        for (List<Rencontre> lr : mapComptRencontre.values()) {
            for (Rencontre r : lr) {
                if (r.getId().equals(id)){
                    return r;
                }
            }
        }
        return null;
    }

    public interface LoaderRencontreByCompetitionCallback {

        public void onRencontreLoadedByCompetition (ResultRencontre result);
    }

    public class ResultRencontre {
        private Competition compt;

        private List<Rencontre> rencontres;

        public ResultRencontre(Competition compt, List<Rencontre> rencontres) {
            this.compt = compt;
            this.rencontres = rencontres;
        }

        public Competition getCompt() {
            return compt;
        }

        public List<Rencontre> getRencontres() {
            return rencontres;
        }
    }
}
