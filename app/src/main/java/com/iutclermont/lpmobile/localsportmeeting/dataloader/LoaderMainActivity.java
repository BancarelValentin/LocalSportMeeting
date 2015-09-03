package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.util.Log;

import com.iutclermont.lpmobile.localsportmeeting.MainActivity;
import com.iutclermont.lpmobile.localsportmeeting.backend.participantApi.model.Participant;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;

import java.util.List;


public class LoaderMainActivity implements LoaderSport.LoaderSportCallBack, LoaderAllRencontre.LoaderRencontreCallback, LoaderParticipants.LoaderParticipantCallback {

    private List<Sport> sports;

    private List<Rencontre> rencontres;

    private Boolean loaded = false;

    private Boolean loadedPart = false;

    private MainActivity mainAct;

    public LoaderMainActivity (MainActivity mainAct) {
        this.mainAct = mainAct;
        new LoaderSport(this).getAll();
        Log.i("load", "Lancement du chargment des sports...");
        new LoaderRencontre().getAllRencontre(this);
        Log.i("load", "Lancement du chargment des rencontre...");
        new LoaderParticipants().getAll(this);

    }

    @Override
    public void OnSportLoaded(List<Sport> sports) {
        Log.i("load", "Fin du chargement des sports!!");
        this.sports = sports;
        if (!loaded) {
            loaded = true;
        }
        else if (loadedPart) {
            mainAct.onDataLoaded(sports, rencontres);
        }
    }

    @Override
    public void onAllRencontreLoaded(List<Rencontre> rencontres) {
        Log.i("load", "Fin du chargement des rencontres!!");
        this.rencontres = rencontres;
        if (!loaded) {
            loaded = true;
        }
        else if (loadedPart) {
            mainAct.onDataLoaded(sports, rencontres);
        }
    }

    @Override
    public void onAllParticipantLoaded(List<Participant> participants) {
        loadedPart = true;
        if (loaded) {
            mainAct.onDataLoaded(sports, rencontres);
        }
    }
}
