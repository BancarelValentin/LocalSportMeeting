package com.iutclermont.lpmobile.localsportmeeting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.iutclermont.lpmobile.localsportmeeting.backend.categorieApi.model.Categorie;
import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.CompetitionApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderCategorie;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderSport;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderTabFragment;

import java.util.List;
import java.util.Map;


/**
 * Created by Thomas on 28/11/2014.
 */
public class MainFragment extends Fragment implements View.OnClickListener, FragmentProgressBar.ProgressBarCallback {

    private Toolbar myToolbar = null;
    private DrawerLayout myDrawerLayout = null;
    private ActionBarDrawerToggle myDrawerToggle = null;
    private AdapterListDrawerMenu adapterList = null;
    private RecyclerViewRencontreFragment fragment = null;
    private Sport sportClicked;
    private List<Sport> listSport;
    private List<Rencontre> rencontres;

    public MainFragment() {
        fragment = new RecyclerViewRencontreFragment();
    }

    public void setSports(List<Sport> sports) {
        this.listSport = sports;
        adapterList = new AdapterListDrawerMenu(this, listSport);
    }

    public void setRencontres (List<Rencontre> rencontres) {
        this.rencontres = rencontres;
        fragment = new RecyclerViewRencontreFragment();
        fragment.setRencontres(rencontres);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterList.setMainActibity ((MainActivity)getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setToolbar(myToolbar = (Toolbar) getActivity().findViewById(R.id.toolbarMain));

        //Thomas -> DrawerNav
        initDrawerLayout();
        updateListRencontre();
        ((MainActivity)getActivity()).setDrawerMenuParams(myDrawerLayout, myDrawerToggle);
    }

    private void initDrawerLayout() {
        myDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawerLayout);
        myDrawerToggle = new ActionBarDrawerToggle(getActivity(), myDrawerLayout, myToolbar, R.string.drawerOpen, R.string.drawerClose);
        myDrawerLayout.setDrawerListener(myDrawerToggle);

        initRecylcerViewDrawer();
    }

    private void initRecylcerViewDrawer() {
        RecyclerView mListDrawerMenu = (RecyclerView) getActivity().findViewById(R.id.recylcerViewDrawerMenu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mListDrawerMenu.setLayoutManager(layoutManager);


        mListDrawerMenu.setAdapter(adapterList);
    }

    public void updateListRencontre() {
        MultipleSelectionSpinner spinner = (MultipleSelectionSpinner)getActivity().findViewById(R.id.spinnerCateg);
        spinner.setVisibility(View.GONE);
        ((MainActivity)getActivity()).replaceFragment(fragment, R.id.sample_content_fragment);
    }

    //Demande la mise a jour du layout en lui passant une liste de toutes les rencontres correspondant au sport passer en parametre
    public void updateListRencontre(Sport sport) {
        FragmentProgressBar fragment = new FragmentProgressBar();
        boolean networkConnection = ((MainActivity)getActivity()).checkInternetConnection();
        fragment.setNetworkAvaible(networkConnection,  this);
        ((MainActivity)getActivity()).replaceFragment(fragment, R.id.sample_content_fragment);
        sportClicked = sport;
        boolean sportIsLoaded = sportIsLoaded(sportClicked);
        if (networkConnection || sportIsLoaded) {
            new LoaderTabFragment(this, sportClicked);
        }
    }

    public void closeDrawerMenu() {
        myDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void onClick(View v) {
        adapterList.changeSelectedItem(v);
        try {
            int tag = ((Integer) v.getTag());
            closeDrawerMenu();
            updateListRencontre();
            v.setBackgroundColor(getResources().getColor(R.color.colorDrawerMenuItemSelected));
        } catch (ClassCastException ex) {
            Sport sportCliked = new LoaderSport().getOneById((Long) v.getTag());
            closeDrawerMenu();
            updateListRencontre(sportCliked);
            v.setBackgroundColor(getResources().getColor(R.color.colorDrawerMenuItemSelected));
        }
    }

    public void OnDataLoaded (List<Categorie> listCateg, Map<Categorie, List<Competition>> mapCategComp, Map<Competition, List<Rencontre>> mapCompRenc, List<Rencontre> listBySport) {
        Log.i("load", "Fin du chargement de MainFragment!!!");
        SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
        fragment.setData(listCateg, mapCategComp,  mapCompRenc);
        ((MainActivity)getActivity()).replaceFragment(fragment, R.id.sample_content_fragment);
    }

    @Override
    public void retryConnection() {
        FragmentProgressBar fragment = new FragmentProgressBar();
        boolean networkConnection = ((MainActivity)getActivity()).checkInternetConnection();
        fragment.setNetworkAvaible(networkConnection, this);
        ((MainActivity)getActivity()).replaceFragment(fragment, R.id.sample_content_fragment);
        boolean sportIsLoaded = sportIsLoaded(sportClicked);
        if (networkConnection || sportIsLoaded) {
            new LoaderTabFragment(this, sportClicked);
        }
    }

    private boolean sportIsLoaded (Sport sport) {
        return LoaderCategorie.isSportLoaded(sport);
    }
}
