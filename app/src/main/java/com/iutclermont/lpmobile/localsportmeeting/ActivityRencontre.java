package com.iutclermont.lpmobile.localsportmeeting;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iutclermont.lpmobile.localsportmeeting.backend.participantApi.model.Participant;
import com.iutclermont.lpmobile.localsportmeeting.backend.rencontreApi.model.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderParticipants;
import com.iutclermont.lpmobile.localsportmeeting.dataloader.LoaderRencontre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class ActivityRencontre extends ActionBarActivity {

    private Toolbar myToolbar = null;

    private Rencontre rencontre;

    private ShareActionProvider mShareActionProvider;

    private List<Participant> participants;

    private List<Participant> listPart = new ArrayList<Participant>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rencontre);

        //Definition de la toolbar
        myToolbar = (Toolbar) findViewById(R.id.toolbarRencontre);
        myToolbar.setClickable(true);
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_drawer));
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }


        //Recuperation de la rencontre concerné
        Intent myIntent = this.getIntent();
        Long idRencontre = Long.parseLong(myIntent.getStringExtra("idRencontre"));
        rencontre = new LoaderRencontre().getOneById(idRencontre);


        //Ajout des partiipants
        LinearLayout layoutPart = (LinearLayout) findViewById(R.id.layoutParticipantsRencontre);
        List<Participant> participants = new ArrayList<Participant>();
        Participant part = new LoaderParticipants().getOneById(rencontre.getIdParticipant1());
        participants.add(part);
        listPart.add(part);
        part = new LoaderParticipants().getOneById(rencontre.getIdParticipant2());
        participants.add(part);
        listPart.add(part);
        participants = new ArrayList<Participant>();
        participants.add(new LoaderParticipants().getOneById(rencontre.getIdParticipant1()));
        participants.add(new LoaderParticipants().getOneById(rencontre.getIdParticipant2()));
        Log.i("load", "taille liste part:" + participants.size());
        int index = 0;
        for (Participant p : participants) {

            index++;

            TextView tempTxtViewPart = new TextView(ActivityRencontre.this);
            tempTxtViewPart.setText(p.getLibelle().toUpperCase());
            tempTxtViewPart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tempTxtViewPart.setTextSize(getResources().getDimension(R.dimen.tailleTitre));
            tempTxtViewPart.setTextColor(getResources().getColor(R.color.textColorSecondaryLight));
            tempTxtViewPart.setTypeface(null, Typeface.BOLD);
            tempTxtViewPart.setOnClickListener((View.OnClickListener) new ListenerLabelParticipant());
            tempTxtViewPart.setTag(R.integer.TAG_PARTICIPANT, p);
            layoutPart.addView(tempTxtViewPart);

            if (index != participants.size()) {
                TextView tempTxtViewTire = new TextView(ActivityRencontre.this);
                tempTxtViewTire.setText(" - ");
                tempTxtViewTire.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tempTxtViewTire.setTextSize(getResources().getDimension(R.dimen.tailleTitre));
                tempTxtViewTire.setTextColor(getResources().getColor(R.color.textColorSecondaryLight));
                tempTxtViewTire.setTypeface(null, Typeface.BOLD);
                tempTxtViewTire.setPadding(layoutPart.getWidth(), 0, 0, 0);
                layoutPart.addView(tempTxtViewTire);
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
        TextView txtViewDateRencontre = (TextView) findViewById(R.id.dateRencontre);
        Date d = new Date(rencontre.getDate().getValue());
        txtViewDateRencontre.setText(formatter.format(d));




        //Ajout du lieu
        TextView txtViewLieuRencontre = (TextView) findViewById(R.id.lieuRencontre);
        txtViewLieuRencontre.setText(rencontre.getLieu());


        //Ajout de google maps
        LinearLayout layoutMap = (LinearLayout) findViewById(R.id.layoutMap);
        LatLng position = new LatLng(rencontre.getLongitude(), rencontre.getLatitude());
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapRencontre)).getMap();
        try {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f));
            map.addMarker(new MarkerOptions().position(position).title(rencontre.getLieu()));
            map.setMyLocationEnabled(true);

            int widthInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());


            //ViewGroup.LayoutParams params = layoutMap.getLayoutParams();
            // params.height = widthInPx;
            //layoutMap.setLayoutParams(params);


        } catch (NullPointerException ignored) {
            Log.e("Local sport meeting", "Google maps n'est pas disponible, installer Google Play");
        }

        //Floatting Button
        findViewById(R.id.floatingButtonStartRoute).setOnClickListener(new ListenerStartRoute());
        findViewById(R.id.floatingButtonAddCalendar).setOnClickListener(new ListenerAddCalendar());
    }

    public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((Participant) v.getTag(R.integer.TAG_PARTICIPANT)).getUrl()));
        startActivity(browserIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        final ShareActionProvider provider = new ShareActionProvider(this);
        Log.i("load", "coucou");
        Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, "https://local-sport-meeting-app.appspot.com/affichageRencontre.jsp?id=" + rencontre.getId());
        provider.setShareIntent(intent);

        MenuItem item = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "partager");
        MenuItemCompat.setActionProvider(item, provider);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        provider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener()
        {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent)
            {
                // Récupération du nom de l'application

                final String appName = intent.getComponent().getPackageName();
                Toast.makeText(ActivityRencontre.this, appName, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return true;

        /*MenuItem shareItem = menu.findItem(R.id.menu_share);

        // Now get the ShareActionProvider from the item
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("test/plain");
        intent.putExtra(Intent.EXTRA_TEXT, participants.get(0).getLibelle() + "-" + participants.get(1).getLibelle());

        mShareActionProvider.setShareIntent(intent);

        return true;*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_share) {
            return true;
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class ListenerLabelParticipant implements View.OnClickListener {
        public void onClick(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((Participant) v.getTag(R.integer.TAG_PARTICIPANT)).getUrl()));
            startActivity(browserIntent);
        }
    }

    public class ListenerStartRoute implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=" + rencontre.getLongitude() + "," + rencontre.getLatitude()));
            startActivity(intent);
        }
    }

    public class ListenerAddCalendar implements View.OnClickListener {
        public void onClick(View v) {
            List<Participant> listeParticipants = new ArrayList<Participant>();
            listeParticipants.add(new LoaderParticipants().getOneById(rencontre.getIdParticipant1()));
            listeParticipants.add(new LoaderParticipants().getOneById(rencontre.getIdParticipant2()));
            StringBuilder titre = new StringBuilder(listeParticipants.get(0).getLibelle());
            if (listeParticipants.size() > 1) {
                titre.append(" - ").append(listeParticipants.get(1).getLibelle());
            }
            Intent calIntent = new Intent(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI);

            calIntent.putExtra(CalendarContract.Events.TITLE, titre.toString());
            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, rencontre.getLieu());


            /*DatePerso date = new DatePerso();*/
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            Date tempDate = new Date(rencontre.getDate().getValue());
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(tempDate);
            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Début de la rencontre à " + formatter.format(calDate.getTime()));
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    calDate.getTimeInMillis());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                    calDate.getTimeInMillis());
            try {
                startActivity(calIntent);
            } catch (ActivityNotFoundException anfe) {
                Toast.makeText(getApplicationContext(), "Vous n'avez aucune application compatible, téléchargé google agenda.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

