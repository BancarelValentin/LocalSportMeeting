package com.iutclermont.lpmobile.localsportmeeting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderRencontre;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by romunuera on 18/11/2014.
 */
public class RecyclerViewRencontreFragment extends Fragment {

    private List<Rencontre> listeRencontreAAfficher = null;

    public RecyclerViewRencontreFragment() {
    }

    public void setRencontres (List<Rencontre> rencontres) {
        listeRencontreAAfficher = rencontres;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        int sizeList;
        MainActivity mainActivity = (MainActivity) this.getActivity();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRencontre);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if (listeRencontreAAfficher.size() != 0) {
            recyclerView.setAdapter(new AdapterRecyclerViewRencontre(mainActivity, new ArrayList<Rencontre>(listeRencontreAAfficher)));
        }
        else {
            TextView textNoRencontre = (TextView)view.findViewById(R.id.textNoRencontre);
            float scale = getResources().getDisplayMetrics().density;
            int padding = (int)(20 * scale + 0.5f);
            textNoRencontre.setPadding(0, padding, 0, 0);
            textNoRencontre.setVisibility(TextView.VISIBLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rencontres, container, false);
    }
}
