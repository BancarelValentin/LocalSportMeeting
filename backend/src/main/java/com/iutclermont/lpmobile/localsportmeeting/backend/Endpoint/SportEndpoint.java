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
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport;

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
        name = "sportApi",
        version = "v1",
        resource = "sport",
        namespace = @ApiNamespace(
                ownerDomain = "backend.localsportmeeting.lpmobile.iutclermont.com",
                ownerName = "backend.localsportmeeting.lpmobile.iutclermont.com",
                packagePath = ""
        )
)
public class SportEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.
    private static final Logger logger = Logger.getLogger(SportEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    @ApiMethod(
            name = "get",
            path = "sport/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Sport get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Sport with ID: " + id);
        Sport sport = ofy().load().type(Sport.class).id(id).now();
        if (sport == null) {
            throw new NotFoundException("Could not find Sport with ID: " + id);
        }
        return sport;
    }

    @ApiMethod(
            name = "insert",
            path = "sport",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Sport insert(Sport sport) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that sport.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(sport).now();
        logger.info("Created Sport with ID: " + sport.getId());

        return ofy().load().entity(sport).now();
    }

    @ApiMethod(
            name = "update",
            path = "sport/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Sport update(@Named("id") Long id, Sport sport) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(sport).now();
        logger.info("Updated Sport: " + sport);
        return ofy().load().entity(sport).now();
    }

    @ApiMethod(
            name = "remove",
            path = "sport/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Sport.class).id(id).now();
        logger.info("Deleted Sport with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "sport",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Sport> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Sport> query = ofy().load().type(Sport.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Sport> queryIterator = query.iterator();
        List<Sport> SportList = new ArrayList<Sport>(limit);
        while (queryIterator.hasNext()) {
            SportList.add(queryIterator.next());
        }
        return CollectionResponse.<Sport>builder().setItems(SportList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Sport.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Sport with ID: " + id);
        }
    }
}