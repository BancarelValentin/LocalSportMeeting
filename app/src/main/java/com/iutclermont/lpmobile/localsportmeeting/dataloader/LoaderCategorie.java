package com.iutclermont.lpmobile.localsportmeeting.dataloader;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.iutclermont.lpmobile.localsportmeeting.backend.categorieApi.CategorieApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.categorieApi.model.Categorie;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by vabancarel on 14/11/2014.
 */
public class LoaderCategorie extends AsyncTask<Sport, Void, List<Categorie>> {

    private static CategorieApi myApiService = null;

    private static Map<Sport, List<Categorie>> mapListCategSport = new HashMap<Sport, List<Categorie>>();

    private LoaderCategorieCallback callback;

    public LoaderCategorie(LoaderCategorieCallback callback) {
        this.callback = callback;
    }

    public Categorie getOneById(Long id) {
        for (List<Categorie> l : mapListCategSport.values()) {
            for(Categorie c : l) {
                if (c.getId().equals(id)) {
                    return c;
                }
            }
        }
        return null;
    }

    public void getBySport(Sport sport) {
        if (!mapListCategSport.containsKey(sport)) {
            execute(sport);
        }
        else {
            if (callback != null) {
                callback.onCategorieLoaded(mapListCategSport.get(sport));
            }
        }
    }

    public static boolean isSportLoaded (Sport sport) {
        return mapListCategSport.containsKey(sport);
    }


    @Override
    protected List<Categorie> doInBackground(Sport... params) {
        List<Categorie> listCateg;
        if(myApiService == null) { // Only do this once
            CategorieApi.Builder builder = new CategorieApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            myApiService = builder.build();
        }
        try {
            Log.i("load", "LoaderCategorie: lancement de la requete!!");
            listCateg = myApiService.getBySport(params[0].getId()).execute().getItems();
            Log.i("load", "LoaderCategorie: fin de la requete!!");
            mapListCategSport.put(params[0], listCateg);
        } catch (IOException e){
            Log.i("load", "LoaderCategorie: erreur pour le sport: " + params[0].getLibelle() + "( " + params[0].getId() + ") ----- " + e.getMessage());
            mapListCategSport.put(params[0], new ArrayList<Categorie>());
            return new ArrayList<Categorie>();
        }
        return listCateg;
    }

    @Override
    protected void onPostExecute(List<Categorie> categories) {
        if (callback != null) {
            Log.i("load", "LoaderCategorie: appel du callback!!");
            callback.onCategorieLoaded(categories);
        }
        else {
            Log.i("load", "callback = null!!");
         }
    }

    public interface LoaderCategorieCallback {
        public void onCategorieLoaded (List<Categorie> categories);
    }
}
