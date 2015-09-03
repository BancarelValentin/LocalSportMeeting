package com.iutclermont.lpmobile.localsportmeeting.backend.Endpoint;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService.ofy;


/**
 * WARNING: This generated code is intended as a sample for demonstrating the usage of
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "participantApi",
        version = "v1",
        resource = "participant",
        namespace = @ApiNamespace(
                ownerDomain = "backend.localsportmeeting.lpmobile.iutclermont.com",
                ownerName = "backend.localsportmeeting.lpmobile.iutclermont.com",
                packagePath = ""
        )
)
public class ParticipantEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.
    private static final Logger logger = Logger.getLogger(ParticipantEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;


    @ApiMethod(
            name = "get",
            path = "participant/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Participant get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Participant with ID: " + id);
        Participant participant = ofy().load().type(Participant.class).id(id).now();
        if (participant == null) {
            throw new NotFoundException("Could not find Participant with ID: " + id);
        }
        return participant;
    }

    @ApiMethod(
            name = "insert",
            path = "participant",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Participant insert(Participant participant) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that participant.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(participant).now();
        logger.info("Created Participant with ID: " + participant.getId());

        return ofy().load().entity(participant).now();
    }

    @ApiMethod(
            name = "update",
            path = "participant/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Participant update(@Named("id") Long id, Participant participant) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(participant).now();
        logger.info("Updated Participant: " + participant);
        return ofy().load().entity(participant).now();
    }

    @ApiMethod(
            name = "remove",
            path = "participant/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Participant.class).id(id).now();
        logger.info("Deleted Participant with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "participant",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Participant> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Participant> query = ofy().load().type(Participant.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Participant> queryIterator = query.iterator();
        List<Participant> ParticipantList = new ArrayList<Participant>(limit);
        while (queryIterator.hasNext()) {
            ParticipantList.add(queryIterator.next());
        }
        return CollectionResponse.<Participant>builder().setItems(ParticipantList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Participant.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Participant with ID: " + id);
        }
    }
}