package com.iutclermont.lpmobile.localsportmeeting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.iutclermont.lpmobile.localsportmeeting.backend.categorieApi.model.Categorie;
import com.iutclermont.lpmobile.localsportmeeting.backend.competitionApi.model.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderCategorie;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderCompetition;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderRencontre;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderSport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by romunuera on 18/11/2014.
 */
public class SlidingTabsBasicFragment extends Fragment {

    private ViewPager mViewPager = null;
    private SlidingTabLayout mSlidingTabLayout = null;
    private ExpandableListView expandableListViewCateg = null;
    private List<Categorie> listCategorie = null;
    Map<Categorie, List<Competition>> mapCategCompt;
    Map<Competition, List<Rencontre>> mapCompRen;
    private Sport sportSelected = null;
    private SamplePagerAdapter adapterViewPager;

    private List<Categorie> categSelected;


    public SlidingTabsBasicFragment() {
        super();
    }

    public void setData (List<Categorie> listCateg, Map<Categorie, List<Competition>> mapCategCompt, Map<Competition, List<Rencontre>> mapCompRen) {

        this.listCategorie = listCateg;
        this.mapCategCompt = mapCategCompt;
        this.mapCompRen = mapCompRen;
        adapterViewPager = new SamplePagerAdapter();
        adapterViewPager.setData(mapCategCompt, mapCompRen);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        adapterViewPager.setSlidingTabLayout(mSlidingTabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(adapterViewPager);


        mSlidingTabLayout.setViewPager(mViewPager);

        MultipleSelectionSpinner spinner = (MultipleSelectionSpinner)getActivity().findViewById(R.id.spinnerCateg);
        spinner.setVisibility(View.VISIBLE);
        spinner.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT, Gravity.TOP | Gravity.RIGHT));
        spinner.setItems(listCategorie);
        spinner.setTabsFragment(this);



    }

    public void setListCategSelection (List<Categorie> listCategorieSelected) {
        adapterViewPager = new SamplePagerAdapter();
        adapterViewPager.setSlidingTabLayout(mSlidingTabLayout);
        adapterViewPager.setData(mapCategCompt, mapCompRen);
        adapterViewPager.setCategorie(listCategorieSelected);
        mViewPager.setAdapter(adapterViewPager);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    class SamplePagerAdapter extends PagerAdapter {

        private int indexOfCateg = 0;

        private int indexOfCompt = 0;

        private List<Competition> listCompetitions = new ArrayList<Competition>();

        private List<Categorie> listCateg = new ArrayList<Categorie>();

        Map<Categorie, List<Competition>> mapCategCompt = new HashMap<Categorie, List<Competition>>();

        Map<Competition, List<Rencontre>> mapComptRen = new HashMap<Competition, List<Rencontre>>();

        private SlidingTabLayout mTabLayout = null;

        @Override
        public int getCount() {
            int size = 0;
            for (Categorie c : listCateg) {
                try {
                    size += mapCategCompt.get(c).size();
                }
                catch (NullPointerException e) {

                }
            }
            return size+1;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Competition compt = null;
            if (position == 0) {
                return "Tous";
            }
            else {
                try {
                    compt = mapCategCompt.get(listCateg.get(indexOfCateg)).get(indexOfCompt);
                } catch (IndexOutOfBoundsException ex) {
                    indexOfCateg++;
                    indexOfCompt = 0;
                    compt = mapCategCompt.get(listCateg.get(indexOfCateg)).get(indexOfCompt);
                }
                listCompetitions.add(compt);
                indexOfCompt++;
                return compt.getLibelle();
            }


        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ;
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_rencontres, container, false);
            // Add the newly created View to the ViewPager
            List<Rencontre> listRencontres;
            if (position == 0) {
                listRencontres = getRencontres();
            }
            else {
                listRencontres = getRecontresByCompetition(position - 1);
            }
            RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewRencontre);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (listRencontres.size() != 0 ) {
                recyclerView.setAdapter(new AdapterRecyclerViewRencontre(getActivity(), listRencontres));
            }
            else {
                view.findViewById(R.id.textNoRencontre).setVisibility(View.VISIBLE);
            }
            container.addView(view);
            return view;
        }

        private List<Rencontre> getRecontresByCompetition(int position) {
            return mapCompRen.get(listCompetitions.get(position));
        }

        private ArrayList<Rencontre> getRencontres() {
            ArrayList<Rencontre> listRencontres = new ArrayList<Rencontre>();
            for (Competition c : listCompetitions) {
                listRencontres.addAll(mapCompRen.get(c));
            }
            return listRencontres;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        public void setCategorie(List<Categorie> listCategorieSelected) {
            listCateg = listCategorieSelected;
            notifyDataSetChanged();

            List<Competition> listNewCompt = new ArrayList<Competition>();
            for (Categorie c : listCateg) {
                try {
                    listNewCompt.addAll(mapCategCompt.get(c));
                }
                catch (NullPointerException e) {

                }
            }

            indexOfCateg = 0;
            indexOfCompt = 0;
            listCompetitions = listNewCompt;
        }


        public void setData (Map<Categorie, List<Competition>> mapCategCompt, Map<Competition, List<Rencontre>> mapComptRen) {
            this.mapCategCompt = mapCategCompt;
            this.mapComptRen = mapComptRen;
        }

        public void setSlidingTabLayout (SlidingTabLayout tabLayout) {
            this.mTabLayout = tabLayout;
        }
    }


    private class MyTabColorizer implements SlidingTabLayout.TabColorizer{
        @Override
        public int getIndicatorColor(int position) {
            return getResources().getColor(R.color.colorPrimary);
        }

        @Override
        public int getDividerColor(int position) {
            return getResources().getColor(R.color.colorPrimary);
        }
    }
}
