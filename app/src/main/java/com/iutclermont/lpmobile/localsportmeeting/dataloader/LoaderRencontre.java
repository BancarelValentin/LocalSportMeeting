package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.os.AsyncTask;
import android.util.Log;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.RencontreApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class LoaderRencontre {

    private static RencontreApi myApiService;

    static {
        if(myApiService == null) {
            RencontreApi.Builder builder = new RencontreApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            myApiService = builder.build();
        }
    }
    public static void getAllRencontre (LoaderAllRencontre.LoaderRencontreCallback callback) {
        new LoaderAllRencontre(myApiService, callback).getAllRencontre();
    }


    public static Rencontre getOneById(Long id) {
        Rencontre r = LoaderRencontreByCompetition.getOneById(id);
        if (r == null) {
            r = LoaderAllRencontre.getOneById (id);
        }
        return r;
    }

    public static void getBySport (Sport sport, LoaderRencontreBySport.LoaderRencontreBySportCallback callback) {
        new LoaderRencontreBySport(myApiService, callback).getRencontreBySport(sport);
    }

    public static void getByCompetition(Competition competition, LoaderRencontreByCompetition.LoaderRencontreByCompetitionCallback callback) {
        new LoaderRencontreByCompetition(myApiService, callback).getRencontres(competition);
    }
}
