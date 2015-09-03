package com.iutclermont.lpmobile.localsportmeeting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderParticipants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class AdapterRecyclerViewRencontre extends RecyclerView.Adapter<AdapterRecyclerViewRencontre.ViewHolder> implements View.OnClickListener,
        View.OnLongClickListener {

    private static Context sContext;
    private List<Rencontre> listeRencontreAAfficher;

    // Adapter's Constructor
    public AdapterRecyclerViewRencontre(Context context, List<Rencontre> myDataset) {
        listeRencontreAAfficher = myDataset;
        sContext = context;
    }

    // Create new views. This is invoked by the layout manager.
    @Override
    public AdapterRecyclerViewRencontre.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        // Create a new view by inflating the row item xml.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rencontre_cardview_layout, parent, false);

        // Set the view to the ViewHolder
        ViewHolder holder = new ViewHolder(v);
        holder.carte.setOnClickListener(AdapterRecyclerViewRencontre.this);
        holder.carte.setOnLongClickListener(AdapterRecyclerViewRencontre.this);
        holder.carte.setTag(holder);
        return holder;
    }

    // Replace the contents of a view. This is invoked by the layout manager.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtViewParticipants.setText(new LoaderParticipants().getOneById(listeRencontreAAfficher.get(position).getIdParticipant1()).getLibelle() + " - " + new LoaderParticipants().getOneById(listeRencontreAAfficher.get(position).getIdParticipant2()).getLibelle());
        holder.idRencontre = listeRencontreAAfficher.get(position).getId();
        Calendar calendar = Calendar.getInstance();
        Date d = new Date(listeRencontreAAfficher.get(position).getDate().getValue());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
        holder.txtViewDate.setText(formatter.format(d));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listeRencontreAAfficher.size();
    }

    // Implement OnClick listener. The clicked item text is displayed in a Toast message.
    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.carte.getId()) {
            Long idRencontre = holder.idRencontre;
            Intent myIntent = new Intent(sContext, ActivityRencontre.class);
            myIntent.putExtra("idRencontre", String.valueOf(idRencontre));
            sContext.startActivity(myIntent);
        }
    }

    // Implement OnLongClick listener. Long Clicked items is removed from list.
    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    // Create the ViewHolder class to keep references to your views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView carte;
        public TextView txtViewParticipants;
        public TextView txtViewDate;
        public Long idRencontre;

        /**
         * Constructor
         *
         * @param v The container view which holds the elements from the row item xml
         */
        public ViewHolder(View v) {
            super(v);

            txtViewParticipants = (TextView) v.findViewById(R.id.txtViewParticipants);
            txtViewDate = (TextView) v.findViewById(R.id.txtviewdate);
            carte = (CardView) v.findViewById(R.id.card_view);

        }
    }
}