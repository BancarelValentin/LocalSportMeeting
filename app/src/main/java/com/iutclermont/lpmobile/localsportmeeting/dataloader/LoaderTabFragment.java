package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.util.Log;

import com.iutclermont.lpmobile.localsportmeeting.MainFragment;
import com.iutclermont.lpmobile.localsportmeeting.backend.categorieApi.model.Categorie;
import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 05/12/2014.
 */
public class LoaderTabFragment implements LoaderRencontreBySport.LoaderRencontreBySportCallback,LoaderRencontreByCompetition.LoaderRencontreByCompetitionCallback, LoaderCompetition.LoaderCompetitionCallback, LoaderCategorie.LoaderCategorieCallback {

    private List<Categorie> listCateg;

    private List<Categorie> listCategLoaded = new ArrayList<Categorie>();

    private List<Competition> listCompt = new ArrayList<Competition>();

    private List<Competition> listComptLoaded = new ArrayList<Competition>();

    private List<Rencontre> listBySport = new ArrayList<Rencontre>();

    private Map<Categorie, List<Competition>> mapCategCompt = new HashMap<Categorie, List<Competition>>();

    private Map<Competition, List<Rencontre>> mapCompRenc = new HashMap<Competition, List<Rencontre>>();

    private MainFragment mainFrag;

    private int nbCategories = 0;

    private int nbCompetitions = 0;

    public LoaderTabFragment(MainFragment mainFrag, Sport sport) {
        this.mainFrag = mainFrag;
        Log.i("load", "lancement du chargement des categorie...");
        new LoaderCategorie(this).getBySport(sport);
        new LoaderRencontre().getBySport(sport, this);
    }

    @Override
    public void onCategorieLoaded(List<Categorie> categories) {
        Log.i("load", "Fin du chargement des categories");
        listCateg = categories;
        nbCategories = categories.size();
        Log.i("load", "Lancement du chargement des competitions...");
        if (categories.size() == 0) {
            mainFrag.OnDataLoaded(categories, mapCategCompt, mapCompRenc, listBySport);
            return;
        }
        for (Categorie c : categories) {
            new LoaderCompetition(this).getByCategorie(c);
        }
    }

    @Override
    public void OnCompetitionLoaded(LoaderCompetition.ResultCompetition result) {
        mapCategCompt.put(result.getCateg(), result.getCompetitions());
        nbCompetitions += result.getCompetitions().size();
        listCompt.addAll(result.getCompetitions());
        if (!listCategLoaded.contains(result.getCateg())){
            listCategLoaded.add(result.getCateg());
        }
        if (listCategLoaded.size() == nbCategories) {
            Log.i("load", "Fin du chargement des competitions");
            Log.i("load", "Lancement du chargement des rencontres...");
            for (Competition cp : listCompt) {
                new LoaderRencontre().getByCompetition(cp, this);
            }
        }
    }

    @Override
    public void onLoadRencontreBySport(List<Rencontre> rencontres) {
        listBySport = rencontres;
    }

    @Override
    public void onRencontreLoadedByCompetition(LoaderRencontreByCompetition.ResultRencontre result) {
        mapCompRenc.put(result.getCompt(), result.getRencontres());
        if (!listComptLoaded.contains(result.getCompt())){
            listComptLoaded.add(result.getCompt());
        }
        if (listComptLoaded.size() == nbCompetitions) {
            Log.i("load", "Fin du chargement des rencontres");
            mainFrag.OnDataLoaded(listCateg, mapCategCompt, mapCompRenc, listBySport);
        }
    }
}
