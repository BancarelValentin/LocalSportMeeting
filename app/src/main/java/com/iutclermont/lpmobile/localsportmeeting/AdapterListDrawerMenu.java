package com.iutclermont.lpmobile.localsportmeeting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iutclermont.lpmobile.localsportmeeting.backend.sportApi.model.Sport;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderSport;

import java.util.List;



public class AdapterListDrawerMenu extends RecyclerView.Adapter<AdapterListDrawerMenu.ViewHolder> {

    private Context context = null;

    private MainFragment mainFragment = null;

    private List<Sport> listSport = null;

    private int posSport = 0;

    private View itemSelected = null;

    public AdapterListDrawerMenu(MainFragment mainFragment, List<Sport> listSports) {
        this.mainFragment = mainFragment;
        this.listSport = listSports;
    }

    @Override
    public AdapterListDrawerMenu.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (posSport > 1) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_menu_item, viewGroup, false);
            v.setTag(listSport.get(posSport - 2).getId());
            posSport++;
            v.setOnClickListener(mainFragment);
            return new ViewHolder(v);
        } else if (posSport == 1) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_menu_item, viewGroup, false);
            int color = context.getResources().getColor(R.color.colorDrawerMenuItemSelected);
            v.setBackgroundColor(color);
            itemSelected = v;
            v.setTag(-1);
            posSport++;
            v.setOnClickListener(mainFragment);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.first_item_drawer_menu, viewGroup, false);
            posSport++;
            return new ViewHolderFirst(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (i > 1) {
           char[] lib = listSport.get(i-2).getLibelle().toCharArray();
           lib[0] = Character.toUpperCase(lib[0]);
            String libelle = new String(lib);
            viewHolder.textSport.setText(libelle);
        }
        else if (i == 1) {
            viewHolder.textSport.setText("Tous les sports");
            viewHolder.view.setBackgroundColor(context.getResources().getColor(R.color.colorBackgroundWhite));
        }
        else {
            ((ViewHolderFirst)viewHolder).titre.setText("Sports");
        }
    }

    @Override
    public int getItemCount() {
        return listSport.size() + 2;
    }

    public void changeSelectedItem (View newItem) {
        if (itemSelected != null) {
            itemSelected.setBackgroundColor(context.getResources().getColor(R.color.colorBackgroundWhite));
        }
        itemSelected = newItem;
    }

    public void setListSports(List<Sport> listSports) {
        this.listSport = listSports;
    }

    public void setMainActibity(MainActivity activity) {
        this.context = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textSport;
        public View view;


        public ViewHolder(View v) {
            super(v);
            textSport = (TextView)v.findViewById(R.id.title);
            view = (View) v.findViewById(R.id.barreDecorationMenu);
        }
    }

    public static class ViewHolderFirst extends AdapterListDrawerMenu.ViewHolder {
        public TextView titre;

        public ViewHolderFirst(View v) {
            super(v);
            titre = (TextView) v.findViewById(R.id.titreSportDrawerMenu);
        }

    }
}
