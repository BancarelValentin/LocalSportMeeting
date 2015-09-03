package com.iutclermont.lpmobile.localsportmeeting;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderMainActivity;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderRencontre;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderSport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements FragmentProgressBar.ProgressBarCallback {


    private DrawerLayout myDrawerLayout = null;
    private ActionBarDrawerToggle myDrawerToggle = null;
    private MainFragment mainFrag = null;
    public boolean mainFragLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean networkAvaible = checkInternetConnection();
        FragmentProgressBar frag = new FragmentProgressBar();
        frag.setNetworkAvaible (networkAvaible, this);
        replaceFragment(frag, R.id.mainContent);
        if (networkAvaible) {
            new LoaderMainActivity(this);
        }
    }

    public void setDrawerMenuParams (DrawerLayout dl, ActionBarDrawerToggle dt) {
        myDrawerLayout = dl;
        myDrawerToggle = dt;
        myDrawerToggle.syncState();
    }

    public void replaceFragment (Fragment newFragment, int ressources) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(ressources, newFragment);
        transaction.commit();
    }

    public void setToolbar (Toolbar myToolbar) {
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (myDrawerToggle != null){
            myDrawerToggle.syncState();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myDrawerToggle != null) {
            myDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (myDrawerToggle !=  null) {
            myDrawerToggle.syncState();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkInternetConnection () {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onDataLoaded(List<Sport> sports, List<Rencontre> rencontres) {
        Log.i("load", "Fin du chargement des données de MainActivity!!");
        mainFrag = new MainFragment();
        mainFrag.setSports(sports);
        mainFrag.setRencontres(rencontres);
        replaceFragment(mainFrag, R.id.mainContent);
        mainFragLoaded = true;
    }


    public void retryConnection() {
        boolean networkAvaible = checkInternetConnection();
        FragmentProgressBar frag = new FragmentProgressBar();
        frag.setNetworkAvaible (networkAvaible, this);
        replaceFragment(frag, R.id.mainContent);
        if (networkAvaible) {
            new LoaderMainActivity(this);
        }
    }
}
