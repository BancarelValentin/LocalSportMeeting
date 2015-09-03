package com.iutclermont.lpmobile.localsportmeeting.dataloader;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.iutclermont.lpmobile.localsportmeeting.backend.participantApi.ParticipantApi;
import com.iutclermont.lpmobile.localsportmeeting.backend.participantApi.ParticipantApiRequest;
import com.iutclermont.lpmobile.localsportmeeting.backend.participantApi.model.Participant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vabancarel on 14/11/2014.
 */
public class LoaderParticipants extends AsyncTask<Object, Void, List<Participant>> {

    private static ParticipantApi myApiService = null;

    private static List<Participant> listParticipant = new ArrayList<Participant>();

    private LoaderParticipantCallback callback;

    public Participant getOneById(Long id) {
        Participant part = null;
        boolean tour = true;
        int size = listParticipant.size();
        while (part == null) {
            for (Participant p : listParticipant) {
                if (p.getId().equals(id)) {
                    part = p;
                    break;
                }
            }
            if (tour && part == null) {
                execute(id);
                tour = false;
                while (listParticipant.size() == size);
            }
        }
        cancel(true);
        return part;
    }

    @Override
    protected List<Participant> doInBackground(Object... params) {
        if(myApiService == null) { // Only do this once
            ParticipantApi.Builder builder = new ParticipantApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);
            myApiService = builder.build();
        }
        try {
            if (params.length > 0) {
                Participant part = myApiService.get((Long)params[0]).execute();
                listParticipant.add(part);
            }
            else {
                listParticipant = myApiService.list().execute().getItems();
                return listParticipant;
            }
            return null;
        } catch (IOException e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Participant> participants) {
       if (participants != null) {
           if (callback != null) {
               callback.onAllParticipantLoaded(participants);
           }
       }
    }

    public void getAll (LoaderParticipantCallback callback) {
        this.callback = callback;
        if (listParticipant.size() == 0) {
            execute();
        }
        else {
            if (callback != null) {
                callback.onAllParticipantLoaded(listParticipant);
            }
        }
    }

    public interface LoaderParticipantCallback {
        public void onAllParticipantLoaded (List<Participant> participants);
    }
}
