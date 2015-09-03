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
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Utilisateur;

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
        name = "utilisateurApi",
        version = "v1",
        resource = "utilisateur",
        namespace = @ApiNamespace(
                ownerDomain = "backend.localsportmeeting.lpmobile.iutclermont.com",
                ownerName = "backend.localsportmeeting.lpmobile.iutclermont.com",
                packagePath = ""
        )
)
public class UtilisateurEndpoint {
    // Make sure to add this endpoint to your web.xml file if this is a web application.
    private static final Logger logger = Logger.getLogger(UtilisateurEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    @ApiMethod(
            name = "get",
            path = "user/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Utilisateur get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Utilisateur with ID: " + id);
        Utilisateur user = ofy().load().type(Utilisateur.class).id(id).now();
        if (user == null) {
            throw new NotFoundException("Could not find Utilisateur with ID: " + id);
        }
        return user;
    }

    @ApiMethod(
            name = "insert",
            path = "user",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Utilisateur insert(Utilisateur user) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that sport.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(user).now();
        logger.info("Created Utilisateur with login: " + user.getLogin());

        return ofy().load().entity(user).now();
    }

    @ApiMethod(
            name = "update",
            path = "user/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Utilisateur update(@Named("id") Long id, Utilisateur user) throws NotFoundException {
        checkExists(id);
        ofy().save().entity(user).now();
        logger.info("Updated Utilisateur: " + user);
        return ofy().load().entity(user).now();
    }

    @ApiMethod(
            name = "remove",
            path = "user/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Utilisateur.class).id(id).now();
        logger.info("Deleted Utilisateur with ID: " + id);
    }

    @ApiMethod(
            name = "list",
            path = "user",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Utilisateur> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Utilisateur> query = ofy().load().type(Utilisateur.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Utilisateur> queryIterator = query.iterator();
        List<Utilisateur> UserList = new ArrayList<Utilisateur>(limit);
        while (queryIterator.hasNext()) {
            UserList.add(queryIterator.next());
        }
        return CollectionResponse.<Utilisateur>builder().setItems(UserList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Sport.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Utilisateur with ID: " + id);
        }
    }
}