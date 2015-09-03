package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.iutclermont.lpmobile.localsportmeeting.backend.categorieApi.model.Categorie;
import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.CompetitionApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vabancarel on 14/11/2014.
 */
public final class LoaderCompetition extends AsyncTask<Categorie, Void, LoaderCompetition.ResultCompetition> {

    private static Map<Categorie, List<Competition>>mapCategCompt = new HashMap<Categorie, List<Competition>>();

    private static CompetitionApi myApiService = null;

    private LoaderCompetitionCallback callback;

    public LoaderCompetition(LoaderCompetitionCallback callback) {
        this.callback = callback;
    }

    public Competition getOneById(Long id) {
        for (List<Competition> l : mapCategCompt.values()) {
            for(Competition c : l) {
                if (c.getId().equals(id)) {
                    return c;
                }
            }
        }
        return null;
    }

    public void getByCategorie(Categorie categorie) {
        if (!mapCategCompt.containsKey(categorie)){
            execute(categorie);
        }
        else {
            if (callback != null) {
                callback.OnCompetitionLoaded(new ResultCompetition(categorie, mapCategCompt.get(categorie)));
            }
        }
    }

    @Override
    protected ResultCompetition doInBackground(Categorie... params) {
        if(myApiService == null) { // Only do this once
            CompetitionApi.Builder builder = new CompetitionApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            myApiService = builder.build();
        }
        try {
            Log.i("load", "LoaderCompetition: lancement de la requete pour les competitions de :" + params[0].getLibelle());
            List<Competition> listCompt = myApiService.getByCategorie(params[0].getId()).execute().getItems();
            Log.i("load", "LoaderCompetition: fin de la requete pour les competitions de :" + params[0].getLibelle());
            mapCategCompt.put(params[0], listCompt);
            return new ResultCompetition(params[0], listCompt);
        } catch (IOException e){
            Log.i("load", "LoaderCompetition: erreur de la requete pour les competitions de :" + params[0].getLibelle() + "( " + params[0].getId() + " ) ---- " + e.getMessage());
            mapCategCompt.put(params[0], new ArrayList<Competition>());
            return new ResultCompetition(params[0], new ArrayList<Competition>());
        }
    }

    @Override
    protected void onPostExecute(ResultCompetition competitions) {
        if (callback != null) {
            Log.i("load", "LoaderCompetition: appel du callback!!!");
            callback.OnCompetitionLoaded(competitions);
        }
        else {
            Log.i("load", "LoaderComeptition: callback = null!!");
        }
    }

    public interface LoaderCompetitionCallback {

        public void OnCompetitionLoaded (ResultCompetition result);
    }

    public class ResultCompetition {

        private Categorie categ;

        private List<Competition> competitions;

        public ResultCompetition (Categorie categ, List<Competition> competitions) {
            this.categ = categ;
            this.competitions = competitions;
        }

        public Categorie getCateg() {
            return categ;
        }

        public List<Competition> getCompetitions() {
            return competitions;
        }
    }
}
